package nguyenkhanh.backend.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;
import nguyenkhanh.backend.entity.BaseEntity;

public class RegisterRequest extends BaseEntity {

	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

	@NotBlank(message = "{Password.NotBlank}")
	@Size(min = 8, max = 20, message = "{Password.Size}")
	private String password;

	@NotBlank(message = "{FullName.NotBlank}")
	@Size(min = 3, max = 100, message = "{FullName.Size}")
	private String fullName;

	public RegisterRequest() {
	}

	public RegisterRequest(String username, String password, String fullName) {
		this.username = username;
		this.password = password;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
