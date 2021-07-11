package br.com.rhribeiro.baseprojectspringbatch.controller;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.service.LogService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/create-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveLogsFromFile(@RequestParam MultipartFile log)
            throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        Long now = System.currentTimeMillis();
        String logFullPath = logService.createByBatch(log, now);
        BatchStatus status = logService.runBatch(now, logFullPath);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> save(@RequestBody LogEntity logModel) {
        LogEntity log = logService.create(logModel);
        return new ResponseEntity<>(log, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody LogEntity logModel) {
        LogEntity log = logService.update(logModel);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        logService.delete(id);
        return new ResponseEntity<>("Successful to delet log!", HttpStatus.OK);
    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        LogEntity logModel = logService.findById(id);
        return new ResponseEntity<>(logModel, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Object> findAll() {
        List<LogEntity> logs = logService.findAll();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/find-by-ip/{ip}")
    public ResponseEntity<Object> findByIp(@PathVariable("ip") String ip) {
        List<LogEntity> logs = logService.findByIp(ip);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/find-by-status/{statusLog}")
    public ResponseEntity<Object> findByStatus(@PathVariable("statusLog") Integer statusLog) {
        List<LogEntity> logs = logService.findByStatus(statusLog);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/find-by-createdat-between/{from}/{to}")
    public ResponseEntity<Object> findByCreatedAtBetween(@PathVariable("from") String from,
                                                         @PathVariable("to") String to) {
        List<LogEntity> logs = logService.findByCreatedAtBetween(from, to);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
