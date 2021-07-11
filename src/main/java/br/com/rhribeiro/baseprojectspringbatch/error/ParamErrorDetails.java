package br.com.rhribeiro.baseprojectspringbatch.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Class Param Error Details
 *
 * @author Renan Ribeiro
 * @since 11/07/21
 */
@Slf4j
@Builder
@Getter
@AllArgsConstructor
public class ParamErrorDetails implements Serializable {
    private static final long serialVersionUID = -8579990290309623052L;
    private final String message;
    private final String property;
    private final String value;
}
