package br.com.rhribeiro.baseprojectspringbatch.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Renan Ribeiro
 * @date 11/07/2021
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = -769147155483245021L;

	public InternalServerErrorException(String msg) {
		super(msg);
	}

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}

}
