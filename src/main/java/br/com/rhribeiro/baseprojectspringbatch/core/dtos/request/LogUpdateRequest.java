package br.com.rhribeiro.baseprojectspringbatch.core.dtos.request;

import br.com.rhribeiro.baseprojectspringbatch.core.constraints.IpConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogUpdateRequest {

    @NotNull
    private Long id;

    private Date createdAt;

    /************************ Exemplo de Constraint personalizada ************************/
    @IpConstraint(require = true)
    private String ip;

    @NotBlank
    @Size(min = 5, max = 23)
    private String request;

    @NotNull
    @Digits(integer = 3, fraction = 0)
    private Integer status;

    @NotBlank
    @Size(min = 15, max = 255)
    private String userAgent;

}
