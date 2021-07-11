package br.com.rhribeiro.baseprojectspringbatch.core.entity;

import br.com.rhribeiro.baseprojectspringbatch.core.constraints.IpConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Entity
@Table(name = "log")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Date createdAt;

    /************************ Exemplo de Constraint personalizada ************************/
    @IpConstraint(require = true)
    @Column(length = 15, nullable = false)
    private String ip;

    @NotBlank
    @Size(min = 5, max = 23)
    @Column(length = 23, nullable = false)
    private String request;

    @NotNull
    @Digits(integer = 3, fraction = 0)
    @Column(nullable = false)
    private Integer status;

    @NotBlank
    @Size(min = 15, max = 255)
    @Column(nullable = false)
    private String userAgent;

}
