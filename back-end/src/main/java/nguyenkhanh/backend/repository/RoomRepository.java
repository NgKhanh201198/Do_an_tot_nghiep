package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

}
