package nguyenkhanh.backend.services;

import java.util.List;

import nguyenkhanh.backend.entity.BookingRoomEntity;
import nguyenkhanh.backend.entity.UserEntity;

public interface IBookingRoomService {
	// Create
	public void createBookingRoom(BookingRoomEntity bookingRoomEntity);

	// Read
	public BookingRoomEntity getBookingRoomById(long id);

	public List<BookingRoomEntity> getBookingRoomAll();

	public List<BookingRoomEntity> getBookingRoomByUser(UserEntity userEntity);
	
	public long countByStatus(String status);

	// Update
	public void updateBookingRoom(BookingRoomEntity bookingRoomEntity);

	public void updateStatusByID(long id, String status);

	// Delete
	public void deleteBookingRoomById(long id);

	// Check
	public boolean isBookingRoomExitsById(long id);
	
	// Test
	public String Hello();
}
