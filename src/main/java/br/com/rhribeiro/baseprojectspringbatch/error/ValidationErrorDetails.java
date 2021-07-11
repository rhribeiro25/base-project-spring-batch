package br.com.rhribeiro.baseprojectspringbatch.error;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Renan Ribeiro
 * @date 11/07/2021
 */

@Builder
@Getter
public class ValidationErrorDetails implements Serializable {
    private final String message;
    private final Object parameter;
}
