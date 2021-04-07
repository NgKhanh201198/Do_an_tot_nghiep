package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.RoomTypeEntity;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {

}
