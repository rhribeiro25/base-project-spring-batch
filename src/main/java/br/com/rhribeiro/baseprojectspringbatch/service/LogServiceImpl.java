package br.com.rhribeiro.baseprojectspringbatch.service;

import br.com.rhribeiro.baseprojectspringbatch.error.exception.BadRequestErrorException;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.repository.LogRepository;
import br.com.rhribeiro.baseprojectspringbatch.utils.DateUtils;
import br.com.rhribeiro.baseprojectspringbatch.utils.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Service
@Log4j2
public class LogServiceImpl implements LogService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    MessageSource messageSource;

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
    public String saveLogFile(MultipartFile logFile, Long now) {
        try {
            String logName = "log_" + now + ".csv";
            FileUtils.salvar(csvFilesPath, logName, logFile);
            return csvFilesPath + logName;
        } catch (IOException e) {
            log.error(e.getMessage());
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
        this.existsById(logModel.getId());
        logRepository.save(logModel);
        return logModel;
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
        this.existsById(id);
        logRepository.deleteById(id);
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

    private void existsById(Long id) {
        if (!logRepository.existsById(id)) {
            String msg = messageSource.getMessage("message.bad.request.error.id", null, Locale.getDefault());
            log.error(msg);
            throw new BadRequestErrorException(msg);
        }
    }

}
