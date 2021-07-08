package br.com.rhribeiro.baseprojectspringbatch.controller;

import br.com.rhribeiro.baseprojectspringbatch.model.LogEntity;
import br.com.rhribeiro.baseprojectspringbatch.service.LogService;
import br.com.rhribeiro.baseprojectspringbatch.utils.FileUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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

    @Value("${files.csv.path}")
    private String csvFilesPath;

    @PostMapping("/create-by-batch")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> saveLogsFromFile(@RequestParam MultipartFile log) {

        Long now = System.currentTimeMillis();
        String logName = "log_" + now + ".csv";
        String logFullPath = csvFilesPath + logName;

        try {
            // Save File
            FileUtils.salvar(csvFilesPath, logName, log);

            // Run Job
            BatchStatus status = logService.runBatch(now, logFullPath);

            if (status == BatchStatus.COMPLETED)
                return new ResponseEntity<>(status.toString(), HttpStatus.CREATED);
            else if (status == BatchStatus.FAILED)
                return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, status.toString()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            else
                return new ResponseEntity<>(new ErrorPage(HttpStatus.BAD_REQUEST, status.toString()),
                        HttpStatus.BAD_REQUEST);

        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | RuntimeException e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            LogEntity logModel = logService.findById(id);
            if (logModel == null) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to find Log!"),
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(logModel, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<Object> findAll() {
        try {
            List<LogEntity> logs = logService.findAll();
            if (logs == null || logs.size() == 0) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to find Logs!"),
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(logs, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-by-ip/{ip}")
    public ResponseEntity<Object> findByIp(@PathVariable("ip") String ip) {
        try {
            List<LogEntity> logs = logService.findByIp(ip);
            if (logs == null || logs.size() == 0) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to find Logs!"),
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(logs, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-by-status/{statusLog}")
    public ResponseEntity<Object> findByStatus(@PathVariable("statusLog") Integer statusLog) {
        try {
            List<LogEntity> logs = logService.findByStatus(statusLog);
            if (logs == null || logs.size() == 0) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to find Logs!"),
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(logs, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-by-createdat-between/{from}/{to}")
    public ResponseEntity<Object> findByCreatedAtBetween(@PathVariable("from") String from,
                                                         @PathVariable("to") String to) {
        try {
            List<LogEntity> logs = logService.findByCreatedAtBetween(from, to);
            if (logs == null || logs.size() == 0) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to find Logs!"),
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(logs, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> save(@RequestBody LogEntity logModel) {
        try {
            logModel.setCreatedAt(new Date());
            logService.saveOrUpdate(logModel);
            return new ResponseEntity<>(logModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> update(@RequestBody LogEntity logModel) {
        try {
            if (!logService.existsById(logModel.getId())) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to find Log!"),
                        HttpStatus.NOT_FOUND);
            } else {
                logService.saveOrUpdate(logModel);
                return new ResponseEntity<>(logModel, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            if (!logService.existsById(Long.parseLong(id))) {
                return new ResponseEntity<>(new ErrorPage(HttpStatus.NOT_FOUND, "Failed to delete, log not found!"),
                        HttpStatus.NOT_FOUND);
            } else {
                logService.delete(Long.parseLong(id));
                return new ResponseEntity<>("Successful to delet log!", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
