package br.com.rhribeiro.baseprojectspringbatch.error.handler;

import br.com.rhribeiro.baseprojectspringbatch.error.ErrorDetails;
import br.com.rhribeiro.baseprojectspringbatch.error.ValidationErrorDetails;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.BadRequestErrorException;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.InternalServerErrorException;
import br.com.rhribeiro.baseprojectspringbatch.error.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Renan Ribeiro
 * @date 11/07/2021
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //Argument not valid by field
        Map<String, ValidationErrorDetails> mapValErrors = new HashMap<>();
        List<ValidationErrorDetails> listVal = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> mapValErrors.put("Field " + error.getField(), ValidationErrorDetails.builder().message(error.getDefaultMessage()).parameter(error.getRejectedValue()).build()))
                .collect(Collectors.toList());

        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Arguments are not valid")
                .timesTamp(new Date().getTime())
                .params(mapValErrors)
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(NotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handlerInternalServerErrorException(InternalServerErrorException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity<?> handlerBadRequestErrorException(BadRequestErrorException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timesTamp(new Date().getTime())
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}