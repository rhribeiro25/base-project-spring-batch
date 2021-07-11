package br.com.rhribeiro.baseprojectspringbatch.utils.spring.batch;

import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.Date;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Log4j2
public class LogFieldSetMapper implements FieldSetMapper<LogEntity> {

    @Override
    public LogEntity mapFieldSet(FieldSet fieldSet) {
        LogEntity logItem = null;
        try {
            Date createdAt = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss(fieldSet.readString(0));
            String ip = fieldSet.readString(1);
            String request = fieldSet.readString(2);
            Integer status = fieldSet.readInt(3);
            String userAgent = fieldSet.readString(4);
            logItem = LogEntity.builder()
                    .createdAt(createdAt)
                    .ip(ip)
                    .request(request)
                    .status(status)
                    .userAgent(userAgent)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return logItem;
    }

}
