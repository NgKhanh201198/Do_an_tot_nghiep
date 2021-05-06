package nguyenkhanh.backend.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;
import nguyenkhanh.backend.api.validation.FullNameFormat;

public class UserAccountDTO {

	@NotNull(message = "Name cannot be null")
	@NotBlank(message = "{FullName.NotBlank}")
	@Size(min = 3, max = 25, message = "{FullName.Size}")
	@FullNameFormat(message = "{FullName.Format}")
	private String fullName;

	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

	@NotBlank(message = "{Gender.NotBlank}")
	private String gender;

	private Set<String> roles;
	private String status;
	private String userType;

	public UserAccountDTO() {
		super();
	}

	public UserAccountDTO(String fullName, String username, String gender, Set<String> roles, String status,
			String userType) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.gender = gender;
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
