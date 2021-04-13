package nguyenkhanh.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.UserTypeEntity;
import nguyenkhanh.backend.repository.UserTypeRepository;
import nguyenkhanh.backend.services.IUserTypeService;

@Service
public class UserTypeServiceImpl implements IUserTypeService {
	@Autowired
	UserTypeRepository userTypeRepository;

	@Override
	public Optional<UserTypeEntity> findByKeyName(String keyName) {
		return userTypeRepository.findByKeyName(keyName);
	}

}
