package nguyenkhanh.backend.services;

import java.util.List;

import nguyenkhanh.backend.entity.BookingRateEntity;

public interface IBookingRateService {
	// Create
	public void createBookingRate(BookingRateEntity bookingRateEntity);

	// Read
	public BookingRateEntity getBookingRateById(long id);

	public List<BookingRateEntity> getBookingRateAll();

	// Update
	public void updateBookingRate(BookingRateEntity bookingRateEntity);

	// Delete
	public void deleteBookingRateById(long id);

	// Check
	public boolean isBookingRateExitsById(long id);

}
