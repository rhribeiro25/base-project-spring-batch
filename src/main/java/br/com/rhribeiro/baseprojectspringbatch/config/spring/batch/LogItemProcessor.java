package br.com.rhribeiro.baseprojectspringbatch.config.spring.batch;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import com.sun.istack.logging.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Component
public class LogItemProcessor implements ItemProcessor<LogEntity, LogEntity> {

    private static Logger logger = Logger.getLogger(LogItemProcessor.class);

    @Override
    public LogEntity process(LogEntity log) throws Exception {
        LogEntity logConverted = new LogEntity(log.getCreatedAt(), log.getIp(), log.getRequest(), log.getStatus(),
                log.getUserAgent());
        logger.info("Log converting (" + log + ") into (" + logConverted + ")");
        return logConverted;
    }
}
