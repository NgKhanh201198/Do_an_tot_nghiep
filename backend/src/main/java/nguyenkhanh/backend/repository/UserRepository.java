package nguyenkhanh.backend.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByPhoneNumber(String phoneNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u " + "SET u.status = 'ACTIVE'" + "WHERE u.username = ?1")
	public int updateStatus(String username);

}
