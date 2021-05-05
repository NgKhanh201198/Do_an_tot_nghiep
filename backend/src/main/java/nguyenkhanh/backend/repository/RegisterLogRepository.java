package nguyenkhanh.backend.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.UserEntity;

@Repository
public interface RegisterLogRepository extends JpaRepository<RegisterLogEntity, Long> {

	public Optional<RegisterLogEntity> findByToken(String token);

	public UserEntity findByUser(String token);

	@Transactional
	@Query("SELECT r.status FROM RegisterLogEntity r " + "WHERE r.token = ?1")
	public String findByStatus(String token);

	@Transactional
	@Modifying
	@Query("UPDATE RegisterLogEntity r " + "SET r.status = 'ACTIVE'" + "WHERE r.token = ?1")
	public int updateStatus(String token);

}
