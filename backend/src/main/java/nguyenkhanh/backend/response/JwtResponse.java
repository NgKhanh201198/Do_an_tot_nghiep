package nguyenkhanh.backend.response;

import java.util.Date;
import java.util.List;

public class JwtResponse {

	private Long userID;
	private String email;
	private String password;
	private String fullName;
	private String phoneNumber;
	private Date dateOfBirth;
	private String avatar;
	private String gender;
	private String status;
	private List<String> roles;
	private String userType;
	private String token;
	private String type = "Bearer";

	public JwtResponse(Long userID, String email, String password, String fullName, String phoneNumber,
			Date dateOfBirth, String avatar, String gender, String status, List<String> roles, String userType,
			String token) {
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.gender = gender;
		this.status = status;
		this.roles = roles;
		this.userType = userType;
		this.token = token;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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
