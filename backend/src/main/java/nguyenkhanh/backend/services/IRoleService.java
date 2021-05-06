package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.entity.RoleEntity;

public interface IRoleService {
	public Optional<RoleEntity> finByKeyName(String keyName);
	
	public List<RoleEntity> getRoleAll();
}
