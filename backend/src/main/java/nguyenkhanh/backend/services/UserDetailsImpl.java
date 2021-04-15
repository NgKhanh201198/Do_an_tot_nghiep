package nguyenkhanh.backend.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nguyenkhanh.backend.entity.BookingRoomEntity;
import nguyenkhanh.backend.entity.EStatus;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.PostEntity;
import nguyenkhanh.backend.entity.RoleEntity;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.entity.UserTypeEntity;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	@JsonIgnore
	private String password;
	private String fullName;
	private String phoneNumber;
	private Date dateOfBirth;
	private String avatar;
	private String gender;
	private String status;
	private UserTypeEntity userType;
	@JsonIgnore
	private Set<BookingRoomEntity> bookingRoom;
	@JsonIgnore
	private Set<HotelEntity> hotels;
	@JsonIgnore
	private Set<PostEntity> posts;
	private Set<RoleEntity> roles;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(UserEntity userEntity) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		userEntity.getRoles().forEach(role -> {
			role.getPermissions().forEach(permission -> {
				authorities.add(new SimpleGrantedAuthority(permission.getPermissionKey()));
			});
		});

		return new UserDetailsImpl(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(),
				userEntity.getFullName(), userEntity.getPhoneNumber(), userEntity.getDateOfBirth(),
				userEntity.getAvatar(), userEntity.getGender(), userEntity.getStatus(), userEntity.getUserType(),
				userEntity.getRoles(), authorities);
	}

	public UserDetailsImpl(Long id, String username, String password, String fullName, String phoneNumber,
			Date dateOfBirth, String avatar, String gender, String status, UserTypeEntity userType,
			Set<RoleEntity> roles, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
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
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !(status.equals(EStatus.LOCKED.toString()));
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if (status.equals(EStatus.ACTIVE.toString())) {
			return true;
		}
		return !(status.equals(EStatus.INACTIVE.toString()));
	}

	// seter geter
	public String getFullName() {
		return fullName;
	}

	public Long getId() {
		return id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getGender() {
		return gender;
	}

	public String getStatus() {
		return status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserTypeEntity getUserType() {
		return userType;
	}

	public Set<BookingRoomEntity> getBookingRoom() {
		return bookingRoom;
	}

	public Set<HotelEntity> getHotels() {
		return hotels;
	}

	public Set<PostEntity> getPosts() {
		return posts;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

}
