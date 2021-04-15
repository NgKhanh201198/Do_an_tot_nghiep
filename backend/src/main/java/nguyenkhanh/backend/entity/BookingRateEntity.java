package nguyenkhanh.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bookingrate")
public class BookingRateEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookrateid")
	private Long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "email")
	private String email;

	@Column(name = "contents")
	private String contents;

	@Column(name = "citizenidentification")
	private String citizenIdentification;

	public BookingRateEntity() {
		super();
	}

	public BookingRateEntity(String userName, String email, String contents, String citizenIdentification) {
		super();
		this.userName = userName;
		this.email = email;
		this.contents = contents;
		this.citizenIdentification = citizenIdentification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCitizenIdentification() {
		return citizenIdentification;
	}

	public void setCitizenIdentification(String citizenIdentification) {
		this.citizenIdentification = citizenIdentification;
	}

}
