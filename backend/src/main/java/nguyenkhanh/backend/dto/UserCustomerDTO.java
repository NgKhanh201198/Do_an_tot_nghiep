package nguyenkhanh.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.FullNameFormat;
import nguyenkhanh.backend.api.validation.PhoneNumberFormat;

public class UserCustomerDTO {

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

	@NotBlank(message = "{Gender.NotBlank}")
	private String gender;

	public UserCustomerDTO() {
		super();
	}

	public UserCustomerDTO(String fullName, String phoneNumber, String dateOfBirth,  String gender) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
