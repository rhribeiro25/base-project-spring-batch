package br.com.rhribeiro.baseprojectspringbatch.error.handler;

import br.com.rhribeiro.baseprojectspringbatch.error.ErrorDetails;
import br.com.rhribeiro.baseprojectspringbatch.error.ParamErrorDetails;
import br.com.rhribeiro.baseprojectspringbatch.error.ValidationError;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.BadRequestErrorException;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Renan Ribeiro
 * @date 11/07/2021
 */

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ParamErrorDetails> params = ValidationError.getParamErrorDetails(ex);
        String msg =  messageSource.getMessage("message.bad.request.error.params", null, Locale.getDefault());

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(msg)
                .timesTamp(new Date().getTime())
                .params(params)
                .build();
        log.error(objectMapper.writeValueAsString(errorDetails));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @SneakyThrows
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .build();
        log.error(objectMapper.writeValueAsString(errorDetails));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @SneakyThrows
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handlerInternalServerErrorException(InternalServerErrorException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .build();
        log.error(objectMapper.writeValueAsString(errorDetails));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SneakyThrows
    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<?> handlerBadRequestErrorException(BadRequestErrorException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .build();
        log.error(objectMapper.writeValueAsString(errorDetails));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}