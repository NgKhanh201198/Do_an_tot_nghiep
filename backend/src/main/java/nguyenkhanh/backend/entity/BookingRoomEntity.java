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

@Entity
@Table(name = "bookingroom")
public class BookingRoomEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookingroomid")
	private Long id;

	@Column(name = "checkindate")
	private Date checkInDate;

	@Column(name = "checkoutdate")
	private Date checkOutDate;

	@Column(name = "totalnumberofpeople")
	private Integer totalNumberOfPeople;

	@Column(name = "status")
	private String status;

	@ManyToOne
	@JoinColumn(name = "userid")
//	@JsonIgnore
	private UserEntity user;

//	CascadeType.ALL Khi 1 xóa user -> dữ liệu theo user sẽ bị xóa 
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "room_booked", joinColumns = @JoinColumn(name = "bookingroomid"), inverseJoinColumns = @JoinColumn(name = "roomid"))
//	@JsonIgnore
	private Set<RoomEntity> rooms = new HashSet<RoomEntity>();

	public BookingRoomEntity() {
		super();
	}

	public BookingRoomEntity(Long id, Date checkInDate, Date checkOutDate, Integer totalNumberOfPeople, String status,
			UserEntity user, Set<RoomEntity> rooms) {
		super();
		this.id = id;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalNumberOfPeople = totalNumberOfPeople;
		this.status = status;
		this.user = user;
		this.rooms = rooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Set<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(Set<RoomEntity> rooms) {
		this.rooms = rooms;
	}

}
