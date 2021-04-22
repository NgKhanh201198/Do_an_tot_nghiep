package nguyenkhanh.backend.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;
import nguyenkhanh.backend.api.validation.PhoneNumberFormat;
import nguyenkhanh.backend.entity.BaseEntity;

public class RegisterRequest extends BaseEntity {

	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

	@NotBlank(message = "{Password.NotBlank}")
	@Size(min = 8, max = 20, message = "{Password.Size}")
	private String password;

	@NotBlank(message = "{FullName.NotBlank}")
	@Size(min = 3, max = 25, message = "{FullName.Size}")
	private String fullName;

	@NotBlank(message = "{PhoneNumber.NotBlank}")
	@PhoneNumberFormat(message = "{PhoneNumber.PhoneNumberFormat}")
	private String phoneNumber;

//	@NotBlank(message = "{DateOfBirth.NotBlank}")
	private String dateOfBirth;

	private String avatar;

//	@NotBlank(message = "{Gender.NotBlank}")
	private String gender;

	private String status;

//	@NotBlank(message = "{UserType.NotBlank}")
	private String userType;

	private Set<String> roles;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
