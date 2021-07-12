package br.com.rhribeiro.baseprojectspringbatch.core.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogResponse {

    private Long id;

    private Date createdAt;

    private String ip;

    private String request;

    private Integer status;

    private String userAgent;

}
