package br.com.rhribeiro.baseprojectspringbatch.service;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import org.springframework.batch.core.BatchStatus;
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

    List<LogEntity> findByIp(String ip);

    String saveLogFile(MultipartFile log, Long now);

    BatchStatus runBatch(Long now, String logFullPath);

    List<LogEntity> findByStatus(Integer status);

}
