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
	@PhoneNumberFormat(message = "{PhoneNumber.PhoneNumberFormat}")
	private String phoneNumber;

	private String description;

	private String city;

	public HotelDTO() {
		super();
	}

	public HotelDTO(String hotelName, String address, String email, String phoneNumber, String description,
			String city) {
		super();
		this.hotelName = hotelName;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.city = city;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
