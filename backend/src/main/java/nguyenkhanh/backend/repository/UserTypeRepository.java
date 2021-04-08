package nguyenkhanh.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.UserTypeEntity;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
	Optional<UserTypeEntity> findByUserTypeName(String userTypeName);

	UserTypeEntity findByUserTypeID(long id);
}
