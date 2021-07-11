package br.com.rhribeiro.baseprojectspringbatch.dataprovider.database.postgresql;

import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

public interface LogRepository extends JpaRepository<LogEntity, Long> {

    public List<LogEntity> findLogModelsByIpIsContaining(String ip);

    public List<LogEntity> findLogModelsByStatus(Integer status);

    public List<LogEntity> findLogModelsByCreatedAtBetween(Date from, Date to);

}
