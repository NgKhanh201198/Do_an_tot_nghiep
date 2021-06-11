package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;

public interface IRoomService {
	// Create
	public void createRoom(RoomEntity roomEntity);

	// Read
	public Optional<RoomEntity> findByRoomNumber(String roomNumber);

	public Optional<RoomEntity> findByRoomNumberAndHotel(String roomNumber, HotelEntity hotelEntity);

	public RoomEntity getRoomById(long id);

	public List<RoomEntity> getRoomByHotel(HotelEntity hotelEntity);

	public List<RoomEntity> getRoomAll();

	public List<String> getListRoomNumber(HotelEntity hotelEntity);

	public long countRoomAll();

	// Update
	public void updateRoom(RoomEntity roomEntity);

	public void updateImage(long id, String image);

	// Delete
	public void deleteRoomById(long id);

	// Check
	public boolean isRoomExitsById(long id);

	public boolean isRoomExitsByRoomNumber(String roomNumber);

	public boolean isRoomExitsByHotel(HotelEntity hotelEntity);
}
