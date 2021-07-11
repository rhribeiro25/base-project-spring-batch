package br.com.rhribeiro.baseprojectspringbatch.config.spring.batch;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.service.LogService;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Component
public class LogItemWriter implements ItemWriter<LogEntity> {

    @Autowired
    private LogService logService;

    @Override
    public void write(List<? extends LogEntity> logs) throws Exception {
        System.out.println("Data Saved for logs: " + logs);
        try {
            logService.saveAll(logs);
        } catch (InternalServerErrorException e) {
            e.printStackTrace();
        }
    }
}
