package nguyenkhanh.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.RoleEntity;
import nguyenkhanh.backend.repository.RoleRepository;
import nguyenkhanh.backend.services.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<RoleEntity> finByKeyName(String keyName) {
		return roleRepository.findByKeyName(keyName);
	}

	@Override
	public List<RoleEntity> getRoleAll() {
		return roleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

}
