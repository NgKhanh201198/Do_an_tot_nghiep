package nguyenkhanh.backend.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.UserEntity;

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
	private String userTypeEntity;
	private RegisterLogEntity registerLogEntity;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(UserEntity userEntity) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		userEntity.getRoles().forEach(role -> {
			role.getPermissions().forEach(permission -> {
				authorities.add(new SimpleGrantedAuthority(permission.getPermissionKey()));
			});
		});

		return new UserDetailsImpl(userEntity.getUserID(), userEntity.getUsername(), userEntity.getFullName(),
				userEntity.getPhoneNumber(), userEntity.getDateOfBirth(), userEntity.getAvatar(),
				userEntity.getGender(), userEntity.getStatus(), authorities);
	}

	public UserDetailsImpl(Long id, String username, String fullName, String phoneNumber, Date dateOfBirth,
			String avatar, String gender, String status, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.avatar = avatar;
		this.gender = gender;
		this.status = status;
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
//		return !status.equals(EUserStatus.LOCKED.toString());
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
//		return status.equals(EUserStatus.ACTIVE.toString());
		return true;
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

	public String getUserTypeEntity() {
		return userTypeEntity;
	}

	public RegisterLogEntity getRegisterLogEntity() {
		return registerLogEntity;
	}

}
