package nguyenkhanh.backend.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "rooms")
public class RoomEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomid")
	private Long roomID;

	@Column(name = "roomnumber")
	private String roomNumber;

	@Column(name = "email")
	private String status;

	@Column(name = "image")
	private String image;

	@Column(name = "contents")
	private String contents;

	@Column(name = "numberofpeople")
	private Integer numberOfPeople;

	@Column(name = "roomcost")
	private Integer roomCost;

	@Column(name = "discount")
	private Integer discount;

	@ManyToOne
	@JoinColumn(name = "roomtypeid")
	private RoomTypeEntity roomType;

	@ManyToOne
	@JoinColumn(name = "hotelid")
	private HotelEntity hotel;

	@ManyToMany(mappedBy = "rooms")
	@JsonIgnore
	Set<BookingRoomEntity> bookingRoom;

	public RoomEntity() {
		super();
	}

	public RoomEntity(Long roomID, String roomNumber, String status, String image, String contents,
			Integer numberOfPeople, Integer roomCost, Integer discount, RoomTypeEntity roomType, HotelEntity hotel,
			Set<BookingRoomEntity> bookingRoom) {
		super();
		this.roomID = roomID;
		this.roomNumber = roomNumber;
		this.status = status;
		this.image = image;
		this.contents = contents;
		this.numberOfPeople = numberOfPeople;
		this.roomCost = roomCost;
		this.discount = discount;
		this.roomType = roomType;
		this.hotel = hotel;
		this.bookingRoom = bookingRoom;
	}

	public Long getRoomID() {
		return roomID;
	}

	public void setRoomID(Long roomID) {
		this.roomID = roomID;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

}
