package nguyenkhanh.backend.request;

public class LoginRequest {
//	@NotBlank(message = "{Email.NotBlank}")
//	@EmailFormat(message = "{Email.EmailFormat}")
	private String username;

//	@NotBlank(message = "{Password.NotBlank}")
//	@Size(min = 8, max = 20, message = "{Password.Size}")
	private String password;

	public LoginRequest() {
		super();
	}

	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

}
