package br.com.rhribeiro.baseprojectspringbatch.utils.spring.batch;

import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.core.usecases.LogService;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */
@Log4j2
@Component
public class LogItemWriter implements ItemWriter<LogEntity> {

    @Autowired
    private LogService logService;

    @Override
    public void write(List<? extends LogEntity> logs) throws Exception {
        log.info("Data Saved for logs: " + logs);
        try {
            logService.saveAll(logs);
        } catch (InternalServerErrorException e) {
            log.error(e.getMessage());
        }
    }
}
