package nguyenkhanh.backend.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;
import nguyenkhanh.backend.api.validation.FullNameFormat;
import nguyenkhanh.backend.api.validation.PhoneNumberFormat;

public class UserAccountDTO {

	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "{FullName.NotBlank}")
	@Size(min = 3, max = 25, message = "{FullName.Size}")
	@FullNameFormat(message = "{FullName.Format}")
	private String fullName;

	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

	@PhoneNumberFormat(message = "{PhoneNumber.PhoneNumberFormat}")
	private String phoneNumber;

	@NotBlank(message = "{Gender.NotBlank}")
	private String gender;

	@NotBlank(message = "{DateOfBirth.NotBlank}")
	private String dateOfBirth;

	private Set<String> roles;
	private String status;
	private String userType;

	public UserAccountDTO() {
		super();
	}

	public UserAccountDTO(String fullName, String username, String phoneNumber, String gender, String dateOfBirth,
			Set<String> roles, String status, String userType) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.roles = roles;
		this.status = status;
		this.userType = userType;
	}

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
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

}
