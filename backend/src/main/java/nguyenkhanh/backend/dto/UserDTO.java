package nguyenkhanh.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.FullNameFormat;
import nguyenkhanh.backend.api.validation.PhoneNumberFormat;
import nguyenkhanh.backend.entity.UserTypeEntity;


public class UserDTO {
	private String username;

	private String password;

	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "{FullName.NotBlank}")
	@Size(min = 3, max = 25, message = "{FullName.Size}")
	@FullNameFormat(message = "{FullName.Format}")
	private String fullName;

	@NotBlank(message = "{PhoneNumber.NotBlank}")
	@PhoneNumberFormat(message = "{PhoneNumber.PhoneNumberFormat}")
	private String phoneNumber;

	@NotBlank(message = "{DateOfBirth.NotBlank}")
	private String dateOfBirth;

	private String avatar;

	@NotBlank(message = "{Gender.NotBlank}")
	private String gender;

	private String status;

	private UserTypeEntity userType;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password, String fullName, String phoneNumber, String dateOfBirth,
			String avatar, String gender, String status, UserTypeEntity userType) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.gender = gender;
		this.status = status;
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserTypeEntity getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEntity userType) {
		this.userType = userType;
	}

}
