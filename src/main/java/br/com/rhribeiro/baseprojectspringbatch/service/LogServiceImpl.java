package br.com.rhribeiro.baseprojectspringbatch.service;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.repository.LogRepository;
import br.com.rhribeiro.baseprojectspringbatch.utils.DateUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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

    @Override
    public List<LogEntity> findAll() {
        List<LogEntity> products = new ArrayList<>();
        logRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public LogEntity findById(Long id) {
        return logRepository.findById(id).orElse(null);
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
    public LogEntity saveOrUpdate(LogEntity logModel) {
        logRepository.save(logModel);
        return logModel;
    }

    @Override
    public List<LogEntity> saveAll(List<? extends LogEntity> logs) {
        logRepository.saveAll(logs);
        return (List<LogEntity>) logs;
    }

    @Override
    public void delete(Long id) {
        logRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return logRepository.existsById(id);
    }

    @Override
    public BatchStatus runBatch(Long now, String logFullPath) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(now));
        maps.put("filePath", new JobParameter(logFullPath));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        BatchStatus status = jobExecution.getStatus();
        return status;
    }

}
