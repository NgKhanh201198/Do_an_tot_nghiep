package nguyenkhanh.backend.services;

import java.util.Optional;

import nguyenkhanh.backend.entity.RoleEntity;

public interface IRoleService {
	Optional<RoleEntity> finByRoleName(String roleName);
}
