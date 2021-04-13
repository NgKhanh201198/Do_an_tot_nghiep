package nguyenkhanh.backend.services;

import java.util.Optional;

import nguyenkhanh.backend.entity.UserTypeEntity;

public interface IUserTypeService {
	Optional<UserTypeEntity> findByKeyName(String keyName);
}
