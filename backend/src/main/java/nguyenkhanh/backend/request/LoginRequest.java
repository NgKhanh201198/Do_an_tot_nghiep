package nguyenkhanh.backend.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;

public class LoginRequest {
	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

	@NotBlank(message = "{Password.NotBlank}")
	@Size(min = 6, max = 20, message = "{Password.Size}")
	private String password;

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

}
