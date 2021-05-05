package nguyenkhanh.backend.repository;

import java.util.Date;
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

	public UserEntity findById(long id);
	
	public Boolean existsByUsername(String username);

	public Boolean existsByPhoneNumber(String phoneNumber);

	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u " + "SET u.status = 'ACTIVE'" + " WHERE u.username = ?1")
	public int updateStatus(String username);

	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u " + "SET u.fullName = ?2, u.phoneNumber = ?3, u.dateOfBirth = ?4, u.gender = ?5"
			+ " WHERE u.id = ?1")
	public int updateUser(long id, String fullName, String phoneNumber, Date dateOfBirth, String gender);

	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u " + "SET u.avatar = ?2" + " WHERE u.id = ?1")
	public int updateImageUser(long id, String avatar);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u " + "SET u.password = ?2" + " WHERE u.id = ?1")
	public int savePassword(long id, String newPassword);
}
