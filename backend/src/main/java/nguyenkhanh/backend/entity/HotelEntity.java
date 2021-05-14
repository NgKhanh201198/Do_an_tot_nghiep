package nguyenkhanh.backend.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "hotels")
public class HotelEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotelid")
	private Long id;

	@Column(name = "hotelname")
	private String hotelName;

	@Column(name = "address")
	private String address;

	@Column(name = "image")
	private String image;

	@Column(name = "email")
	private String email;

	@Column(name = "phonenumber")
	private String phoneNumber;

//	@ManyToOne
//	@JoinColumn(name = "userid")
//	@JsonIgnore
//	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "cityid")
	@JsonIgnore
	private CityEntity city;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<RoomEntity> rooms = new HashSet<RoomEntity>();

	public HotelEntity() {
		super();
	}

	public HotelEntity(String hotelName, String address, String image, String email, String phoneNumber,
			 CityEntity city, Set<RoomEntity> rooms) {
		super();
		this.hotelName = hotelName;
		this.address = address;
		this.image = image;
		this.email = email;
		this.phoneNumber = phoneNumber;
//		this.user = user;
		this.city = city;
		this.rooms = rooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

//	public UserEntity getUser() {
//		return user;
//	}
//
//	public void setUser(UserEntity user) {
//		this.user = user;
//	}

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}

	public Set<RoomEntity> getRooms() {
		return rooms;
	}

	public void setRooms(Set<RoomEntity> rooms) {
		this.rooms = rooms;
	}

}
