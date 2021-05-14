package nguyenkhanh.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import nguyenkhanh.backend.api.validation.EmailFormat;
import nguyenkhanh.backend.api.validation.PhoneNumberFormat;

public class HotelDTO {
	@NotBlank(message = "{HotelName.NotBlank}")
	@Size(max = 50, message = "{HotelName.Size}")
	private String hotelName;

	@NotBlank(message = "{Address.NotBlank}")
	@Size(max = 255, message = "{Address.Size}")
	private String address;

	@NotBlank(message = "{Email.NotBlank}")
	@EmailFormat(message = "{Email.EmailFormat}")
	private String email;

	@NotBlank(message = "{PhoneNumber.NotBlank}")
	@PhoneNumberFormat(message = "{PhoneNumber.EmailFormat}")
	private String phoneNumber;

	public HotelDTO() {
		super();
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
