package br.com.rhribeiro.baseprojectspringbatch.service;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */
public interface LogService {

    List<LogEntity> findAll();

    LogEntity findById(Long id);

    LogEntity update(LogEntity logModel);

    LogEntity create(LogEntity logModel);

    void delete(Long id);

    List<LogEntity> saveAll(List<? extends LogEntity> logs);

    List<LogEntity> findByCreatedAtBetween(String from, String to);

    boolean existsById(Long id);

    List<LogEntity> findByIp(String ip);

    String createByBatch(MultipartFile log, Long now);

    BatchStatus runBatch(Long now, String logFullPath) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException;

    List<LogEntity> findByStatus(Integer status);

}
