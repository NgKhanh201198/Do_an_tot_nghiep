package nguyenkhanh.backend.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.RegisterLogEntity;

@Repository
public interface RegisterLogRepository extends JpaRepository<RegisterLogEntity, Long> {

	public Optional<RegisterLogEntity> findByToken(String token);

	public RegisterLogEntity findByUser(long userid);

	@Transactional
	@Query("SELECT r.status FROM RegisterLogEntity r " + "WHERE r.token = ?1")
	public String findByStatus(String token);

	@Transactional
	@Modifying
	@Query("UPDATE RegisterLogEntity r " + "SET r.status = 'ACTIVE'" + "WHERE r.token = ?1")
	public int updateStatus(String token);

}
