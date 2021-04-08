package nguyenkhanh.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.RoleEntity;
import nguyenkhanh.backend.repository.RoleRepository;
import nguyenkhanh.backend.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<RoleEntity> finByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

}
