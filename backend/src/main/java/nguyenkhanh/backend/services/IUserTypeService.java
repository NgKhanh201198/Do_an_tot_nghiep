package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;


import nguyenkhanh.backend.entity.UserTypeEntity;

public interface IUserTypeService {
	public Optional<UserTypeEntity> findByKeyName(String keyName);
	
	public List<UserTypeEntity> getUserTypeAll();
}
