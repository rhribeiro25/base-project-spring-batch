package br.com.rhribeiro.baseprojectspringbatch.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private Date createdAt;

    @Column(length = 15, nullable = false)
    private String ip;

    @Column(length = 23, nullable = false)
    private String request;

    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private String userAgent;

}
