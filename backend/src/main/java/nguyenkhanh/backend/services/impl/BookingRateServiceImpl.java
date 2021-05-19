package nguyenkhanh.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.BookingRateEntity;
import nguyenkhanh.backend.repository.BookingRateRepository;
import nguyenkhanh.backend.services.IBookingRateService;

@Service
public class BookingRateServiceImpl implements IBookingRateService {
	@Autowired
	BookingRateRepository bookingRateRepository;

	@Override
	public void createBookingRate(BookingRateEntity bookingRateEntity) {
		bookingRateRepository.save(bookingRateEntity);
	}

	@Override
	public BookingRateEntity getBookingRateById(long id) {
		// TODO Auto-generated method stub
		return bookingRateRepository.findById(id);
	}

	@Override
	public List<BookingRateEntity> getBookingRateAll() {
		return bookingRateRepository.findAll();
	}

	@Override
	public void updateBookingRate(BookingRateEntity bookingRateEntity) {
		bookingRateRepository.save(bookingRateEntity);
	}

	@Override
	public void deleteBookingRateById(long id) {
		bookingRateRepository.deleteById(id);
	}

}
