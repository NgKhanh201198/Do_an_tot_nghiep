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
	private Long hotelID;

	@Column(name = "hotelname")
	private String hotelName;

	@Column(name = "address")
	private String addrees;

	@Column(name = "image")
	private String image;

	@Column(name = "email")
	private String email;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@ManyToOne
	@JoinColumn(name = "userid")
	@JsonIgnore
	private UserEntity user;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private Set<RoomEntity> rooms = new HashSet<RoomEntity>();

	public Long getHotelID() {
		return hotelID;
	}

	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getAddrees() {
		return addrees;
	}

	public void setAddrees(String addrees) {
		this.addrees = addrees;
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

}
