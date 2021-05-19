package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.entity.RoomTypeEntity;

public interface IRoomTypeService {

	// Create
	public void createRoomType(RoomTypeEntity roomTypeEntity);

	// Read
	public Optional<RoomTypeEntity> findByRoomTypeName(String roomTypeName);

	public RoomTypeEntity getRoomTypeById(long id);

	public List<RoomTypeEntity> getRoomTypeAll();

	// Update
	public void updateRoomType(RoomTypeEntity roomTypeEntity);

	// Delete
	public void deleteRoomTypeById(long id);

	// Check
	public boolean isRoomTypeExitsById(long id);

	public boolean isRoomTypeExitsByRoomTypeName(String roomTypeName);
}
