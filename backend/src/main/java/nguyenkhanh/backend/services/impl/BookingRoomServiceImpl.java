package nguyenkhanh.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.BookingRoomEntity;
import nguyenkhanh.backend.repository.BookingRoomRepository;
import nguyenkhanh.backend.services.IBookingRoomService;

@Service
public class BookingRoomServiceImpl implements IBookingRoomService {
	@Autowired
	BookingRoomRepository bookingRoomRepository;

	@Override
	public void createBookingRoom(BookingRoomEntity bookingRoomEntity) {
		bookingRoomRepository.save(bookingRoomEntity);
	}

	@Override
	public BookingRoomEntity getBookingRoomById(long id) {
		return bookingRoomRepository.findById(id);
	}

	@Override
	public List<BookingRoomEntity> getBookingRoomAll() {
		return bookingRoomRepository.findAll(Sort.by(Sort.Direction.DESC, "user").descending().and(Sort.by(Sort.Direction.DESC,"id")));
	}

	@Override
	public void updateBookingRoom(BookingRoomEntity bookingRoomEntity) {
		bookingRoomRepository.save(bookingRoomEntity);
	}

	@Override
	public void deleteBookingRoomById(long id) {
		bookingRoomRepository.deleteRoomBookedByBookingRoomId(id);
		bookingRoomRepository.deleteById(id);
	}

	@Override
	public boolean isBookingRoomExitsById(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
