package nguyenkhanh.backend.dto;

public class CheckRoomEmptyDTO {
	private String checkInDate;

	private String checkOutDate;

	private String hotel;

	public CheckRoomEmptyDTO() {
	}

	public CheckRoomEmptyDTO(String checkInDate, String checkOutDate, String hotel) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.hotel = hotel;
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

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

}
