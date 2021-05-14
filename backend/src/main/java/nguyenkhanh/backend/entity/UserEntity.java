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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "phonenumber") })
public class UserEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private Long id;

	@Column(name = "email")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullName;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@Column(name = "dateofbirth")
	private Date dateOfBirth;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "gender")
	private String gender;

	@Column(name = "status")
	private String status;

	@OneToOne
	@JoinColumn(name = "usertypeid")
	private UserTypeEntity userType;

//	CascadeType.ALL Khi 1 xóa user -> dữ liệu theo user sẽ bị xóa 
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<BookingRoomEntity> bookingRoom = new HashSet<BookingRoomEntity>();

//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//	@JsonIgnore
//	private Set<HotelEntity> hotels = new HashSet<HotelEntity>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<PostEntity> posts = new HashSet<PostEntity>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
	private Set<RoleEntity> roles = new HashSet<RoleEntity>();

	public UserEntity() {
		super();
	}

	public UserEntity(String username, String password, String fullName, String phoneNumber, Date dateOfBirth,
			String avatar, String gender, String status, UserTypeEntity userType, Set<RoleEntity> roles) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.gender = gender;
		this.status = status;
		this.userType = userType;
		this.roles = roles;
	}
	public UserEntity(String username, String password, String fullName, String status, UserTypeEntity userType) {
		super();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.status = status;
		this.userType = userType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserTypeEntity getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEntity userType) {
		this.userType = userType;
	}

	public Set<BookingRoomEntity> getBookingRoom() {
		return bookingRoom;
	}

	public void setBookingRoom(Set<BookingRoomEntity> bookingRoom) {
		this.bookingRoom = bookingRoom;
	}

//	public Set<HotelEntity> getHotels() {
//		return hotels;
//	}
//
//	public void setHotels(Set<HotelEntity> hotels) {
//		this.hotels = hotels;
//	}

	public Set<PostEntity> getPosts() {
		return posts;
	}

	public void setPosts(Set<PostEntity> posts) {
		this.posts = posts;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
}