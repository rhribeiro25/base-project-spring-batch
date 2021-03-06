package br.com.rhribeiro.baseprojectspringbatch.entrypoint.rest;

import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogCreateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.request.LogUpdateRequest;
import br.com.rhribeiro.baseprojectspringbatch.core.dtos.response.LogResponse;
import br.com.rhribeiro.baseprojectspringbatch.core.usecases.LogService;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Validated
@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/batch")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity saveLogsFromFile(@RequestParam MultipartFile log) {

        Long now = System.currentTimeMillis();
        String logFullPath = logService.saveLogFile(log, now);
        BatchStatus status = logService.runBatch(now, logFullPath);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> save(@RequestBody @Valid LogCreateRequest logModel) {
        LogResponse log = logService.create(logModel);
        return new ResponseEntity<>(log, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody @Valid LogUpdateRequest logModel) {
        LogResponse log = logService.update(logModel);
        return new ResponseEntity<>(log, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        logService.delete(id);
        return new ResponseEntity<>("Successful to delet log!", HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        LogResponse logModel = logService.findById(id);
        return new ResponseEntity<>(logModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<LogResponse> logs = logService.findAll();
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/ip/{ip}")
    public ResponseEntity<Object> findByIp(@PathVariable("ip") String ip) {
        List<LogResponse> logs = logService.findByIp(ip);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> findByStatus(@PathVariable("status") Integer status) {
        List<LogResponse> logs = logService.findByStatus(status);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("/created-at-between/{from}/{to}")
    public ResponseEntity<Object> findByCreatedAtBetween(@PathVariable("from") String from,
                                                         @PathVariable("to") String to) {
        List<LogResponse> logs = logService.findByCreatedAtBetween(from, to);
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }
}
