package br.com.rhribeiro.baseprojectspringbatch.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Renan Ribeiro
 * @date 07/07/21
 */

@Entity
@Table(name = "log")
public class LogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date createdAt;
	private String ip;
	private String request;
	private Integer status;
	private String userAgent;

	public LogEntity() {
	}

	public LogEntity(Long id, Date createdAt, String ip, String request, Integer status, String userAgent) {
		this.id = id;
		this.createdAt = createdAt;
		this.ip = ip;
		this.request = request;
		this.status = status;
		this.userAgent = userAgent;
	}

	public LogEntity(Date createdAt, String ip, String request, Integer status, String userAgent) {
		this.createdAt = createdAt;
		this.ip = ip;
		this.request = request;
		this.status = status;
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "LogModel [id=" + id + ", createdAt=" + createdAt + ", ip=" + ip + ", request=" + request + ", status="
				+ status + ", userAgent=" + userAgent + "]";
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
