package nguyenkhanh.backend.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bookingroom")
public class BookingRoomEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookingroomid")
	private Long bookingRoomID;

	@Column(name = "bookingdate")
	private Date bookingDate;

	@Column(name = "checkindate")
	private Date checkInDate;

	@Column(name = "checkoutdate")
	private Date checkOutDate;

	@Column(name = "totalnumberofpeople")
	private Integer totalNumberOfPeople;

	@Column(name = "totalroomsbooked")
	private Integer totalRoomsBooked;

	@Column(name = "totalcost")
	private Integer totalCost;

	@ManyToOne
	@JoinColumn(name = "userid")
	@JsonIgnore
	private UserEntity user;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "room_booked", joinColumns = @JoinColumn(name = "bookingroomid"), inverseJoinColumns = @JoinColumn(name = "roomid"))
	Set<RoomEntity> rooms = new HashSet<RoomEntity>();

	public BookingRoomEntity() {
		super();
	}

	public BookingRoomEntity(Long bookingRoomID, Date bookingDate, Date checkInDate, Date checkOutDate,
			Integer totalNumberOfPeople, Integer totalRoomsBooked, Integer totalCost, UserEntity user,
			Set<RoomEntity> rooms) {
		super();
		this.bookingRoomID = bookingRoomID;
		this.bookingDate = bookingDate;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalNumberOfPeople = totalNumberOfPeople;
		this.totalRoomsBooked = totalRoomsBooked;
		this.totalCost = totalCost;
		this.user = user;
		this.rooms = rooms;
	}

	public Long getBookingRoomID() {
		return bookingRoomID;
	}

	public void setBookingRoomID(Long bookingRoomID) {
		this.bookingRoomID = bookingRoomID;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getTotalNumberOfPeople() {
		return totalNumberOfPeople;
	}

	public void setTotalNumberOfPeople(Integer totalNumberOfPeople) {
		this.totalNumberOfPeople = totalNumberOfPeople;
	}

	public Integer getTotalRoomsBooked() {
		return totalRoomsBooked;
	}

	public void setTotalRoomsBooked(Integer totalRoomsBooked) {
		this.totalRoomsBooked = totalRoomsBooked;
	}

	public Integer getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Integer totalCost) {
		this.totalCost = totalCost;
	}

}
