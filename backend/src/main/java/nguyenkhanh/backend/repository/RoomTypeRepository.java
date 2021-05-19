package nguyenkhanh.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.RoomTypeEntity;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {
	public Optional<RoomTypeEntity> findByRoomTypeName(String roomTypeName);

	public RoomTypeEntity findById(long id);
	
	public Boolean existsByRoomTypeName(String roomTypeName);

}
