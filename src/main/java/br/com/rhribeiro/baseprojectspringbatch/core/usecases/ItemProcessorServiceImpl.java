package br.com.rhribeiro.baseprojectspringbatch.core.usecases;

import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Log4j2
@Service
public class ItemProcessorServiceImpl implements ItemProcessor<LogEntity, LogEntity> {

    @Override
    public LogEntity process(LogEntity logEntity) throws Exception {
        LogEntity logConverted = LogEntity.builder()
                .createdAt(logEntity.getCreatedAt())
                .ip(logEntity.getIp())
                .request(logEntity.getRequest())
                .status(logEntity.getStatus())
                .userAgent(logEntity.getUserAgent())
                .build();
        log.info("Log converting (" + logEntity + ") into (" + logConverted + ")");
        return logConverted;
    }
}
