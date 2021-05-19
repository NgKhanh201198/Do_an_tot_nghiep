package nguyenkhanh.backend.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bookingroom")
public class BookingRoomEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookingroomid")
	private Long id;

	@Column(name = "checkintime")
	private Date checkInTime;

	@Column(name = "checkouttime")
	private Date checkOutTime;

	@Column(name = "totalnumberofpeople")
	private Integer totalNumberOfPeople;

	@Column(name = "totalroomsbooked")
	private Integer totalRoomsBooked;

	@Column(name = "totalcost")
	private Integer totalCost;

	@Column(name = "status")
	private String status;

	@OneToOne
	@JoinColumn(name = "bookingrateid")
	private BookingRateEntity bookingrate;

	public BookingRoomEntity() {
		super();
	}

	public BookingRoomEntity(Long id, Date checkInTime, Date checkOutTime, Integer totalNumberOfPeople,
			Integer totalRoomsBooked, Integer totalCost, String status, BookingRateEntity bookingrate) {
		super();
		this.id = id;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.totalNumberOfPeople = totalNumberOfPeople;
		this.totalRoomsBooked = totalRoomsBooked;
		this.totalCost = totalCost;
		this.status = status;
		this.bookingrate = bookingrate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BookingRateEntity getBookingrate() {
		return bookingrate;
	}

	public void setBookingrate(BookingRateEntity bookingrate) {
		this.bookingrate = bookingrate;
	}

}
