package nguyenkhanh.backend.services;

import java.util.List;

import nguyenkhanh.backend.entity.BookingRoomEntity;

public interface IBookingRoomService {
	// Create
		public void createBookingRoom(BookingRoomEntity bookingRoomEntity);

		// Read
//		public Optional<CityEntity> findByCityName(String cityName);

		public BookingRoomEntity getBookingRoomById(long id);

		public List<BookingRoomEntity> getBookingRoomAll();

		// Update
		public void updateBookingRoom(BookingRoomEntity bookingRoomEntity);

		// Delete
		public void deleteBookingRoomById(long id);

		// Check
		public boolean isBookingRoomExitsById(long id);

//		public boolean isCBookingRoomExitsByCityName(String cityName);
}
