package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.RegisterLogEntity;

@Repository
public interface RegisterLogRepository extends JpaRepository<RegisterLogEntity, Long> {

}
