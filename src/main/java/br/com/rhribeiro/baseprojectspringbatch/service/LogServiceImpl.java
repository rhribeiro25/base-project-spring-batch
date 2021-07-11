package br.com.rhribeiro.baseprojectspringbatch.service;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.repository.LogRepository;
import br.com.rhribeiro.baseprojectspringbatch.utils.DateUtils;
import br.com.rhribeiro.baseprojectspringbatch.utils.FileUtils;
import error.exception.BadRequestException;
import error.exception.InternalServerErrorException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    private LogRepository logRepository;

    @Value("${files.csv.path}")
    private String csvFilesPath;

    @Override
    public BatchStatus runBatch(Long now, String logFullPath) {
        try {
            Map<String, JobParameter> maps = new HashMap<>();
            maps.put("time", new JobParameter(now));
            maps.put("filePath", new JobParameter(logFullPath));
            JobParameters parameters = new JobParameters(maps);
            JobExecution jobExecution = jobLauncher.run(job, parameters);
            BatchStatus status = jobExecution.getStatus();
            if (status != BatchStatus.COMPLETED)
                throw new InternalServerErrorException(status.toString());
            return status;

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | RuntimeException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public String createByBatch(MultipartFile log, Long now) {
        try {
            String logName = "log_" + now + ".csv";
            FileUtils.salvar(csvFilesPath, logName, log);
            return csvFilesPath + logName;
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public LogEntity create(LogEntity logModel) {
        try {
            logModel.setCreatedAt(new Date());
            logRepository.save(logModel);
            return logModel;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public LogEntity update(LogEntity logModel) {
        try {
            if (!this.existsById(logModel.getId()))
                throw new BadRequestException("Failed to update, log not found!");
            logRepository.save(logModel);
            return logModel;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<LogEntity> saveAll(List<? extends LogEntity> logs) {
        try {
            logRepository.saveAll(logs);
            return (List<LogEntity>) logs;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    @Override
    public void delete(Long id) {
        try {
            if (!this.existsById(id))
                throw new BadRequestException("Failed to delete, log not found!");
            logRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public LogEntity findById(Long id) {
        return logRepository.findById(id).orElse(null);
    }

    @Override
    public List<LogEntity> findAll() {
        return logRepository.findAll();
    }

    @Override
    public List<LogEntity> findByIp(String ip) {
        return logRepository.findLogModelsByIpIsContaining(ip);
    }

    @Override
    public List<LogEntity> findByStatus(Integer status) {
        return logRepository.findLogModelsByStatus(status);
    }

    @Override
    public List<LogEntity> findByCreatedAtBetween(String from, String to) {
        Date dateFrom = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss(from);
        Date dateTo = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss(to);
        return logRepository.findLogModelsByCreatedAtBetween(dateFrom, dateTo);
    }

    @Override
    public boolean existsById(Long id) {
        return logRepository.existsById(id);
    }

}
