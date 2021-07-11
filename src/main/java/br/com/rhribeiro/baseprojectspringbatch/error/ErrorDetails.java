package br.com.rhribeiro.baseprojectspringbatch.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * @author Renan Ribeiro
 * @date 11/07/2021
 */

@Builder
@Getter
public class ErrorDetails implements Serializable {

    private final int statusCode;

    private final String status;

    private final Long timesTamp;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<ParamErrorDetails> params;
}

