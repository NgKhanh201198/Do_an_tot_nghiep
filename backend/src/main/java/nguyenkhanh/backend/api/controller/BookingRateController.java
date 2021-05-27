package nguyenkhanh.backend.api.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.dto.BookingRateDTO;
import nguyenkhanh.backend.entity.BookingRateEntity;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.impl.BookingRateServiceImpl;
import nguyenkhanh.backend.services.impl.HotelServiceImpl;
import nguyenkhanh.backend.services.impl.RoomServiceImpl;
import nguyenkhanh.backend.services.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookingRateController {

	@Autowired
	BookingRateServiceImpl bookingRateServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	RoomServiceImpl roomServiceImpl;

	@Autowired
	HotelServiceImpl hotelServiceImpl;

	@PostMapping("/bookingrate")
	public ResponseEntity<?> createBookingRate(@RequestBody @Valid BookingRateDTO bookingRateDTO) {
		try {
			BookingRateEntity bookingRateEntity = new BookingRateEntity();

			UserEntity userEntity = userServiceImpl.findByUsername(bookingRateDTO.getUser())
					.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng này!"));

			// Set Rooms
			Set<String> strRoom = bookingRateDTO.getRooms();
			Set<RoomEntity> setRoomEntity = new HashSet<RoomEntity>();

			HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(bookingRateDTO.getHotel())
					.orElseThrow(() -> new UsernameNotFoundException(
							"Không tìm thấy khách sạn" + bookingRateDTO.getHotel() + " này!"));

			if (strRoom == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
								HttpStatus.BAD_REQUEST.name(), "Phòng đặt không được để trống"));
			} else {
				strRoom.forEach(room -> {
					RoomEntity roomEntity = roomServiceImpl.findByRoomNumberAndHotel(room, hotelEntity)
							.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy " + room + " này!"));

					setRoomEntity.add(roomEntity);
				});
			}

			// Set CheckInDate
			String strCheckInDate = bookingRateDTO.getCheckInDate();
			Common controller1 = new Common();
			Date checkInDate = controller1.stringToDate(strCheckInDate);
			bookingRateEntity.setCheckInDate(checkInDate);

			// Set CheckOutDate
			String strCheckOutDate = bookingRateDTO.getCheckOutDate();
			Common controller2 = new Common();
			Date checkOutDate = controller2.stringToDate(strCheckOutDate);
			bookingRateEntity.setCheckOutDate(checkOutDate);

			bookingRateEntity.setStatus("Đã đặt");
			bookingRateEntity.setUser(userEntity);
			bookingRateEntity.setRooms(setRoomEntity);

			bookingRateServiceImpl.createBookingRate(bookingRateEntity);

			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm thành công!"));

		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}

	@GetMapping("/bookingrate")
	public ResponseEntity<?> getBookingRateAll() {
		List<BookingRateEntity> listBookingRate = bookingRateServiceImpl.getBookingRateAll();
		return new ResponseEntity<List<BookingRateEntity>>(listBookingRate, HttpStatus.OK);
	}

	@GetMapping("/bookingrate/{id}")
	public ResponseEntity<?> getBookingRateById(@PathVariable("id") long id) {

		if (bookingRateServiceImpl.isBookingRateExitsById(id) == false) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(),
					HttpStatus.NOT_FOUND.name(), "Không tìm thấy đơn đặt trước có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			BookingRateEntity bookingRate = bookingRateServiceImpl.getBookingRateById(id);
			return new ResponseEntity<BookingRateEntity>(bookingRate, HttpStatus.OK);
		}
	}

	@PutMapping("/bookingrate/{id}")
	public ResponseEntity<?> updateBookingRate(@PathVariable("id") long id,
			@RequestBody @Valid BookingRateDTO bookingRateDTO) {
		try {
			if (bookingRateServiceImpl.isBookingRateExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy đơn đặt trước có id= " + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				BookingRateEntity oldBookingRateEntity = bookingRateServiceImpl.getBookingRateById(id);

				UserEntity userEntity = userServiceImpl.getUserByFullName(bookingRateDTO.getUser())
						.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng này!"));

				// Set Rooms
				Set<String> strRoom = bookingRateDTO.getRooms();
				Set<RoomEntity> setRoomEntity = new HashSet<RoomEntity>();

				HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(bookingRateDTO.getHotel())
						.orElseThrow(() -> new UsernameNotFoundException(
								"Không tìm thấy khách sạn" + bookingRateDTO.getHotel() + " này!"));

				if (strRoom == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(), "Phòng đặt không được để trống"));
				} else {
					strRoom.forEach(room -> {
						RoomEntity roomEntity = roomServiceImpl.findByRoomNumberAndHotel(room, hotelEntity)
								.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy" + room + " này!"));

						setRoomEntity.add(roomEntity);
					});
				}

				// Set CheckInDate
				String strCheckInDate = bookingRateDTO.getCheckInDate();
				Common controller1 = new Common();
				Date checkInDate = controller1.stringToDate(strCheckInDate);
				oldBookingRateEntity.setCheckInDate(checkInDate);

				// Set CheckOutDate
				String strCheckOutDate = bookingRateDTO.getCheckOutDate();
				Common controller2 = new Common();
				Date checkOutDate = controller2.stringToDate(strCheckOutDate);
				oldBookingRateEntity.setCheckOutDate(checkOutDate);

				oldBookingRateEntity.setUser(userEntity);
				oldBookingRateEntity.setRooms(setRoomEntity);

				bookingRateServiceImpl.updateBookingRate(oldBookingRateEntity);
				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}
	
	@DeleteMapping("/bookingrate/{id}")
	public ResponseEntity<?> deleteBookingrate(@Valid @PathVariable("id") long id) {
		try {
			bookingRateServiceImpl.deleteBookingRateById(id);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Xóa thành công!"));
		} catch (Exception e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy đơn đặt trước có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
	
}
