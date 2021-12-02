package nguyenkhanh.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.UserTypeEntity;
import nguyenkhanh.backend.repository.UserTypeRepository;
import nguyenkhanh.backend.services.IUserTypeService;

@Service
public class UserTypeServiceImpl implements IUserTypeService {
	@Autowired
	private UserTypeRepository userTypeRepository;

	@Override
	public Optional<UserTypeEntity> findByKeyName(String keyName) {
		return userTypeRepository.findByKeyName(keyName);
	}

	@Override
	public List<UserTypeEntity> getUserTypeAll() {
		return userTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

}
