package br.com.rhribeiro.baseprojectspringbatch.core.usecases;

import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogCreateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogUpdateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.response.LogResponse;
import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import org.springframework.batch.core.BatchStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */
public interface LogService {

    List<LogResponse> findAll();

    LogResponse findById(Long id);

    LogResponse update(LogUpdateRequest logModel);

    LogResponse create(LogCreateRequest logModel);

    void delete(Long id);

    List<LogEntity> saveAll(List<? extends LogEntity> logs);

    List<LogResponse> findByCreatedAtBetween(String from, String to);

    List<LogResponse> findByIp(String ip);

    String saveLogFile(MultipartFile log, Long now);

    BatchStatus runBatch(Long now, String logFullPath);

    List<LogResponse> findByStatus(Integer status);

}
