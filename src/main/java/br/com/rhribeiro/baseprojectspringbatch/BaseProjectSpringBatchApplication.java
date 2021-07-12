package br.com.rhribeiro.baseprojectspringbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@SpringBootApplication
@EntityScan(basePackages = {"br.com.rhribeiro.baseprojectspringbatch.core.entity"})
@EnableJpaRepositories(basePackages = {"br.com.rhribeiro.baseprojectspringbatch.dataprovider.database.postgresql"})
@Slf4j
public class BaseProjectSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseProjectSpringBatchApplication.class, args);
        log.info("🚀 Server ready at http://localhost:9090/api");
    }

}
