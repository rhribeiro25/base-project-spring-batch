package br.com.rhribeiro.baseprojectspringbatch.entrypoint.rest;

import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogCreateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogUpdateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.entity.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.dataprovider.adapter.generic.GenericConverter;
import br.com.rhribeiro.baseprojectspringbatch.dataprovider.database.postgresql.LogRepository;
import br.com.rhribeiro.baseprojectspringbatch.utils.DateUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Renan Ribeiro
 * @date 11/07/21
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @MockBean
    private GenericConverter genericConverter;

    @MockBean
    private LogRepository logRepository;

    private List<LogEntity> logs;
    private LogEntity log;
    private Date from, to;
    private HttpEntity responseEntityCreate;
    private HttpEntity responseEntityUpdate;
    private LogCreateRequest logCreateRequest;
    private LogUpdateRequest logUpdateRequest;

    @TestConfiguration
    static class SpringBootTestConfig {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("user", "smartLog2020");
        }
    }

    @Before
    public void setup() {
        logs = new ArrayList<LogEntity>();
        logs.add(new LogEntity(1L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:23.003"),
                "192.168.169.191", "GET / HTTP/1.1", 200,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(2L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-02 00:00:23.003"),
                "192.168.169.192", "GET / HTTP/1.1", 201,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(3L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-03 00:00:23.003"),
                "192.168.169.193", "GET / HTTP/1.1", 400,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(4L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-04 00:00:23.003"),
                "192.168.169.194", "GET / HTTP/1.1", 404,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(5L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-05 00:00:23.003"),
                "192.168.169.195", "GET / HTTP/1.1", 500,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(6L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-06 00:00:23.003"),
                "192.168.169.196", "GET / HTTP/1.1", 200,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(7L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-07 00:00:23.003"),
                "192.168.169.197", "GET / HTTP/1.1", 201,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(8L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-08 00:00:23.003"),
                "192.168.169.198", "GET / HTTP/1.1", 400,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(9L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-09 00:00:23.003"),
                "192.168.169.199", "GET / HTTP/1.1", 404,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        logs.add(new LogEntity(10L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-10 00:00:23.003"),
                "192.168.169.200", "GET / HTTP/1.1", 500,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393"));
        log = new LogEntity(11L, DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:23.003"),
                "192.168.169.194", "GET / HTTP/1.1", 200,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393");

        from = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-01 00:00:23.003");
        to = DateUtils.stringToDate_yyyy_MM_dd__HH_mm_ss("2020-01-05 00:00:23.003");

        responseEntityCreate = new ResponseEntity<>(log, HttpStatus.CREATED);
        responseEntityUpdate = new ResponseEntity<>(log, HttpStatus.OK);

        logCreateRequest = genericConverter.converterObjectToObject(log, LogCreateRequest.class);
        logUpdateRequest = genericConverter.converterObjectToObject(log, LogUpdateRequest.class);

        BDDMockito.when(genericConverter.converterObjectToObject(log, LogCreateRequest.class)).thenReturn(logCreateRequest);
        BDDMockito.when(genericConverter.converterObjectToObject(log, LogUpdateRequest.class)).thenReturn(logUpdateRequest);
    }

    /**
     * FindById
     */
    @Test
    public void findByIdHttpStatus200() {
        BDDMockito.when(logRepository.findById(11L)).thenReturn(Optional.of(log));
        ResponseEntity response = restTemplate.getForEntity("/api/logs/status/11", Object.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findByIdHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        BDDMockito.when(logRepository.findById(11L)).thenReturn(Optional.of(log));
        ResponseEntity<String> response = restTemplate.getForEntity("/api/logs/id/11", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findByIdHttpStatus404() {
        BDDMockito.when(logRepository.findById(11L)).thenReturn(Optional.of(log));
        ResponseEntity<String> response = restTemplate.getForEntity("/api/logs/id/2", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findByIdHttpStatus400() {
        BDDMockito.when(logRepository.findById(11L)).thenReturn(Optional.of(log));
        ResponseEntity<String> response = restTemplate.getForEntity("/api/logs/status/1a1a1a", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void findByIdHttpStatus405() {
        BDDMockito.when(logRepository.findAll()).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs/id", log, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * FindByAll
     */
    @Test
    public void findAllHttpStatus200() {
        BDDMockito.when(logRepository.findAll()).thenReturn(logs);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/logs/status/400", List.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findAllHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        BDDMockito.when(logRepository.findAll()).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/logs", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findAllHttpStatus404() {
        BDDMockito.when(logRepository.findAll()).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity("/log/find-all", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findAllHttpStatus405() {
        BDDMockito.when(logRepository.findAll()).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs/status/1", logs, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * FindByIp
     */
    @Test
    public void findByIpHttpStatus200() {
        String ip = "192.168.169.19";
        BDDMockito.when(logRepository.findByIpIsContaining(ip)).thenReturn(logs);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/logs/ip/" + ip, List.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findByIpHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        String ip = "192.168.169.19";
        BDDMockito.when(logRepository.findByIpIsContaining(ip)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/logs/ip/" + ip, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findByIpHttpStatus404() {
        String ip = "192.168.169.19";
        BDDMockito.when(logRepository.findByIpIsContaining(ip)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity("/log/find-by-ip/" + ip, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findByIpHttpStatus405() {
        String ip = "192.168.169.19";
        BDDMockito.when(logRepository.findByIpIsContaining(ip)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs/ip/" + ip, logs, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * FindByStatus
     */
    @Test
    public void findByStatusHttpStatus200() {
        Integer status = 200;
        BDDMockito.when(logRepository.findByStatus(status)).thenReturn(logs);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/logs/status/" + status, List.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findByStatusHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        Integer status = 200;
        BDDMockito.when(logRepository.findByStatus(status)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/logs/status/" + status, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findByStatusHttpStatus404() {
        Integer status = 200;
        BDDMockito.when(logRepository.findByStatus(status)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity("/log/status/" + status, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findByStatusHttpStatus405() {
        Integer status = 200;
        BDDMockito.when(logRepository.findByStatus(status)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs/status/" + status, logs, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * FindByBetween
     */
    @Test
    public void findByCreatedAtBetweenHttpStatus200() {
        BDDMockito.when(logRepository.findByCreatedAtBetween(from, to)).thenReturn(logs);
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/api/logs/created-at-between/2020-01-01 00:00:23.003/2020-01-05 00:00:23.003", List.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void findByCreatedAtBetweenHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "test");
        BDDMockito.when(logRepository.findByCreatedAtBetween(from, to)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/logs/created-at-between/2020-01-01 00:00:23.003/2020-01-05 00:00:23.003", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void findByCreatedAtBetweenHttpStatus404() {
        BDDMockito.when(logRepository.findByCreatedAtBetween(from, to)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/logs/created-at-between/2020-01-01 00:00:23.003/2020-01-05 00:00:23.003", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void findByCreatedAtBetweenHttpStatus405() {
        BDDMockito.when(logRepository.findByCreatedAtBetween(from, to)).thenReturn(logs);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/logs/created-at-between/2020-01-01 00:00:23.003/2020-01-05 00:00:23.003", logs, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * Create
     */
    @Test
    public void createHttpStatus201() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        ResponseEntity response = restTemplate.postForEntity("/api/logs", log, Object.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void createHttpStatus403() {
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs", responseEntityCreate, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void createHttpStatus404() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/log", responseEntityCreate, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void createHttpStatus405() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs/1", null, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(405);
    }

    @Test
    public void createHttpStatus415() {
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/logs", null, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(415);
    }

    /**
     * Update
     */
    @Test
    public void updateHttpStatus200() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<LogEntity> exchange = restTemplate.exchange("/api/logs", HttpMethod.PUT, responseEntityCreate,
                LogEntity.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void updateHttpStatus400() {
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<LogEntity> exchange = restTemplate.exchange("/api/logs", HttpMethod.PUT, null, LogEntity.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void updateHttpStatus403() {
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<LogEntity> exchange = restTemplate.exchange("/api/logs", HttpMethod.PUT, responseEntityCreate,
                LogEntity.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void updateHttpStatus404() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(false);
        ResponseEntity<LogEntity> exchange = restTemplate.exchange("/api/log", HttpMethod.PUT, responseEntityCreate,
                LogEntity.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void updateHttpStatus405() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.when(logRepository.save(log)).thenReturn(log);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<LogEntity> exchange = restTemplate.exchange("/api/logs/update", HttpMethod.POST, responseEntityCreate,
                LogEntity.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(405);
    }

    /**
     * Delete
     */
    @Test
    public void deleteHttpStatus200() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.doNothing().when(logRepository).deleteById(11L);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/logs/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteHttpStatus401() {
        restTemplate = restTemplate.withBasicAuth("test", "smartLog2020");
        BDDMockito.doNothing().when(logRepository).deleteById(11L);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/logs/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void deleteHttpStatus403() {
        BDDMockito.doNothing().when(logRepository).deleteById(11L);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/logs/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void deleteHttpStatus404() {
        restTemplate = restTemplate.withBasicAuth("admin", "smartLog2020");
        BDDMockito.doNothing().when(logRepository).deleteById(11L);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(false);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/log/11", HttpMethod.DELETE, null,
                String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteHttpStatus405() {
        restTemplate = restTemplate.withBasicAuth("user", "smartLog2020");
        BDDMockito.doNothing().when(logRepository).deleteById(11L);
        BDDMockito.when(logRepository.existsById(11L)).thenReturn(true);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/logs", HttpMethod.DELETE, null, String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(405);
    }

}
