package br.com.rhribeiro.baseprojectspringbatch.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Renan Ribeiro
 * @date 11/07/2021
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestErrorException extends RuntimeException {

	private static final long serialVersionUID = -769147155483245021L;

	public BadRequestErrorException(String msg) {
		super(msg);
	}

	public BadRequestErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestErrorException(Throwable cause) {
		super(cause);
	}

}
