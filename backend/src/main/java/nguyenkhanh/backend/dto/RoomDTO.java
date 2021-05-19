package nguyenkhanh.backend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomDTO {
	@NotBlank(message = "{RoomNumber.NotBlank}")
	@Size(max = 50, message = "{RoomNumber.Size}")
	private String roomNumber;

	@NotBlank(message = "{Contents.NotBlank}")
	@Size(max = 255, message = "{Contents.Size}")
	private String contents;

	@NotBlank(message = "{NumberOfPeople.NotBlank}")
	private Integer numberOfPeople;

	@NotBlank(message = "{RoomCost.NotBlank}")
	private Integer roomCost;

	@NotBlank(message = "{Discount.NotBlank}")
	private Integer discount;

	@NotBlank(message = "{RoomType.NotBlank}")
	private String roomType;

	@NotBlank(message = "{Hotel.NotBlank}")
	private String hotel;

	private String status;

	public RoomDTO() {
		super();
	}

	public RoomDTO(String roomNumber, String contents, Integer numberOfPeople, Integer roomCost, Integer discount,
			String roomType, String hotel, String status) {
		super();
		this.roomNumber = roomNumber;
		this.contents = contents;
		this.numberOfPeople = numberOfPeople;
		this.roomCost = roomCost;
		this.discount = discount;
		this.roomType = roomType;
		this.hotel = hotel;
		this.status = status;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public Integer getRoomCost() {
		return roomCost;
	}

	public void setRoomCost(Integer roomCost) {
		this.roomCost = roomCost;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
