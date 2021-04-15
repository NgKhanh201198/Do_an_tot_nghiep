package nguyenkhanh.backend.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "permissions")
public class PermissionEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissionid")
	private Long id;

	@Column(name = "permissionname")
	private String permissionName;

	@Column(name = "permissionkey")
	private String permissionKey;

	@ManyToMany(mappedBy = "permissions")
	@JsonIgnore
	private Set<RoleEntity> roles;

	public PermissionEntity() {
		super();
	}

	public PermissionEntity(String permissionName, String permissionKey, Set<RoleEntity> roles) {
		super();
		this.permissionName = permissionName;
		this.permissionKey = permissionKey;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

}
