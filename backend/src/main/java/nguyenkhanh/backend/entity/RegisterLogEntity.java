package nguyenkhanh.backend.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "register_log")
public class RegisterLogEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "token")
	private String token;

	@Column(name = "status")
	private String status;

	@Column(name = "date_active")
	private LocalDateTime dateActive;

	@OneToOne
	@JoinColumn(name = "userid")
	private UserEntity user;

	public RegisterLogEntity() {
		super();
	}

	public RegisterLogEntity(String token, String status, LocalDateTime dateActive, UserEntity user) {
		super();
		this.token = token;
		this.status = status;
		this.dateActive = dateActive;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDateActive() {
		return dateActive;
	}

	public void setDateActive(LocalDateTime dateActive) {
		this.dateActive = dateActive;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
