package br.com.rhribeiro.baseprojectspringbatch.core.usecases;

import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogCreateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogUpdateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.response.LogResponse;
import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro.baseprojectspringbatch.dataprovider.database.postgresql.LogRepository;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.BadRequestErrorException;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
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
    public LogResponse create(LogCreateRequest logRequest) {
        try {
            logRequest.setCreatedAt(new Date());
            LogEntity log = logRepository.save(GenericConverter.converterObjectToObject(logRequest, LogEntity.class));
            return GenericConverter.converterObjectToObject(log, LogResponse.class);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public LogResponse update(LogUpdateRequest logRequest) {
        this.existsById(logRequest.getId());
        LogEntity log = logRepository.save(GenericConverter.converterObjectToObject(logRequest, LogEntity.class));
        return GenericConverter.converterObjectToObject(log, LogResponse.class);
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
    public LogResponse findById(Long id) {
        LogEntity log = logRepository.findById(id).orElse(null);
        return GenericConverter.converterObjectToObject(log, LogResponse.class);
    }

    @Override
    public List<LogResponse> findAll() {
        List<LogEntity> logs = logRepository.findAll();
        return GenericConverter.converterListToList(logs, LogResponse.class);
    }

    @Override
    public List<LogResponse> findByIp(String ip) {
        List<LogEntity> logs = logRepository.findLogModelsByIpIsContaining(ip);
        return GenericConverter.converterListToList(logs, LogResponse.class);
    }

    @Override
    public List<LogResponse> findByStatus(Integer status) {
        List<LogEntity> logs = logRepository.findLogModelsByStatus(status);
        return GenericConverter.converterListToList(logs, LogResponse.class);
    }

    @Override
    public List<LogResponse> findByCreatedAtBetween(String from, String to) {
        Date dateFrom = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss(from);
        Date dateTo = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss(to);
        List<LogEntity> logs = logRepository.findLogModelsByCreatedAtBetween(dateFrom, dateTo);
        return GenericConverter.converterListToList(logs, LogResponse.class);
    }

    private void existsById(Long id) {
        if (!logRepository.existsById(id)) {
            String msg = messageSource.getMessage("message.bad.request.error.id", null, Locale.getDefault());
            log.error(msg);
            throw new BadRequestErrorException(msg);
        }
    }

}