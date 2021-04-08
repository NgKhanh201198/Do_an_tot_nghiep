package nguyenkhanh.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_type")
public class UserTypeEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usertypeid")
	private Long userTypeID;

	@Column(name = "usertypename")
	private String userTypeName;

	public UserTypeEntity() {
	}

	public UserTypeEntity(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public Long getUserTypeID() {
		return userTypeID;
	}

	public void setUserTypeID(Long userTypeID) {
		this.userTypeID = userTypeID;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

}
