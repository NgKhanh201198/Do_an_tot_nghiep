package nguyenkhanh.backend.response;

import java.util.Date;
import java.util.List;

public class JwtResponse {

	private Long id;
	private String email;
	private String fullName;
	private String phoneNumber;
	private Date dateOfBirth;
	private String avatar;
	private String gender;
	private String status;
	private List<String> roles;
	private List<String> permissions;
	private String userTypeName;
	private String token;
	private String type = "Bearer";

	public JwtResponse() {
		super();
	}

	public JwtResponse(Long id, String email, String fullName, String phoneNumber, Date dateOfBirth, String avatar,
			String gender, String status, List<String> roles, List<String> permissions, String userTypeName,
			String token) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.gender = gender;
		this.status = status;
		this.roles = roles;
		this.permissions = permissions;
		this.userTypeName = userTypeName;
		this.token = token;
	}

	public JwtResponse(Long id, String email, String fullName, String phoneNumber,Date dateOfBirth, String avatar, String gender,
			String status, List<String> roles,List<String> permissions, String token) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.gender = gender;
		this.status = status;
		this.roles = roles;
		this.permissions = permissions;
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
