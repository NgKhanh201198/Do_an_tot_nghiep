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

	@Column(name = "keyname")
	private String keyName;

	public UserTypeEntity() {
	}

	public UserTypeEntity(Long userTypeID, String userTypeName, String keyName) {
		super();
		this.userTypeID = userTypeID;
		this.userTypeName = userTypeName;
		this.keyName = keyName;
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

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

}
