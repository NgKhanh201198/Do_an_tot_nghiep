package nguyenkhanh.backend.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

public class BookingRoomDTO {
	@NotBlank(message = "{CheckInDate.NotBlank}")
	private String checkInDate;

	@NotBlank(message = "{CheckOutDate.NotBlank}")
	private String checkOutDate;

	@NotBlank(message = "{User.NotBlank}")
	private String user;

	@NotBlank(message = "{Hotel.NotBlank}")
	private String hotel;

	private Integer totalNumberOfPeople;

	private Set<String> rooms;
	private String status;

	public BookingRoomDTO() {
		super();
	}

	public BookingRoomDTO(String checkInDate, String checkOutDate, String user, String hotel,
			Integer totalNumberOfPeople, Set<String> rooms, String status) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.user = user;
		this.hotel = hotel;
		this.totalNumberOfPeople = totalNumberOfPeople;
		this.rooms = rooms;
		this.status = status;
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

	public Integer getTotalNumberOfPeople() {
		return totalNumberOfPeople;
	}

	public void setTotalNumberOfPeople(Integer totalNumberOfPeople) {
		this.totalNumberOfPeople = totalNumberOfPeople;
	}

	public Set<String> getRooms() {
		return rooms;
	}

	public void setRooms(Set<String> rooms) {
		this.rooms = rooms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
