package nguyenkhanh.backend.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;
import nguyenkhanh.backend.api.validation.FullNameFormat;

public class UserDTO {
	@NotBlank(message = "{FullName.NotBlank}")
	@Size(min = 3, max = 25, message = "{FullName.Size}")
	@FullNameFormat(message = "{FullName.Format}")
	private String fullName;

	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

	@NotBlank(message = "{Password.NotBlank}")
	@Size(min = 8, max = 20, message = "{Password.Size}")
	private String password;

	@NotBlank(message = "{Gender.NotBlank}")
	private String gender;

	@NotBlank(message = "{DateOfBirth.NotBlank}")
	private String dateOfBirth;

	private Set<String> roles;
	private String userType;
	private String status;

	public UserDTO() {
		super();
	}

	public UserDTO(String fullName, String username, String password, String gender, String dateOfBirth,
			Set<String> roles, String userType, String status) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.roles = roles;
		this.userType = userType;
		this.status = status;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
