package br.com.rhribeiro.baseprojectspringbatch.service;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */
public interface LogService {

    public List<LogEntity> findAll();

    public LogEntity findById(Long id);

    public LogEntity saveOrUpdate(LogEntity logModel);

    public void delete(Long id);

    public List<LogEntity> saveAll(List<? extends LogEntity> logs);

    public List<LogEntity> findByCreatedAtBetween(String from, String to);

    public boolean existsById(Long id);

    public List<LogEntity> findByIp(String ip);

    public BatchStatus runBatch(Long now, String logFullPath) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException;

    public List<LogEntity> findByStatus(Integer status);

}
