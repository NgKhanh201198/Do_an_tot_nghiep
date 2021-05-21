package nguyenkhanh.backend.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

public class BookingRateDTO {
	@NotBlank(message = "{CheckInDate.NotBlank}")
	private String checkInDate;

	@NotBlank(message = "{CheckOutDate.NotBlank}")
	private String checkOutDate;

	@NotBlank(message = "{User.NotBlank}")
	private String user;

	@NotBlank(message = "{Hotel.NotBlank}")
	private String hotel;

	private Set<String> rooms;

	public BookingRateDTO() {
		super();
	}

	public BookingRateDTO(String checkInDate, String checkOutDate, String user, String hotel, Set<String> rooms) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.user = user;
		this.hotel = hotel;
		this.rooms = rooms;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public Set<String> getRooms() {
		return rooms;
	}

	public void setRooms(Set<String> rooms) {
		this.rooms = rooms;
	}

}
