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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.dto.BookingRoomDTO;
import nguyenkhanh.backend.entity.BookingRoomEntity;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.impl.BookingRoomServiceImpl;
import nguyenkhanh.backend.services.impl.HotelServiceImpl;
import nguyenkhanh.backend.services.impl.RoomServiceImpl;
import nguyenkhanh.backend.services.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookingRoomController {
	@Autowired
	BookingRoomServiceImpl bookingRoomServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	RoomServiceImpl roomServiceImpl;

	@Autowired
	HotelServiceImpl hotelServiceImpl;

	@PostMapping("/bookingRoom")
	public ResponseEntity<?> createBookingRoom(@RequestBody @Valid BookingRoomDTO bookingRoomDTO) {
		try {
			BookingRoomEntity bookingRoomEntity = new BookingRoomEntity();

			UserEntity userEntity = userServiceImpl.findByUsername(bookingRoomDTO.getUser())
					.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng này!"));

			// Set Rooms
			Set<String> strRoom = bookingRoomDTO.getRooms();
			Set<RoomEntity> setRoomEntity = new HashSet<RoomEntity>();

			HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(bookingRoomDTO.getHotel())
					.orElseThrow(() -> new UsernameNotFoundException(
							"Không tìm thấy khách sạn" + bookingRoomDTO.getHotel() + " này!"));

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
			String strCheckInDate = bookingRoomDTO.getCheckInDate();
			Common controller1 = new Common();
			Date checkInDate = controller1.stringToDate(strCheckInDate);
			bookingRoomEntity.setCheckInDate(checkInDate);

			// Set CheckOutDate
			String strCheckOutDate = bookingRoomDTO.getCheckOutDate();
			Common controller2 = new Common();
			Date checkOutDate = controller2.stringToDate(strCheckOutDate);
			bookingRoomEntity.setCheckOutDate(checkOutDate);

			bookingRoomEntity.setTotalNumberOfPeople(bookingRoomDTO.getTotalNumberOfPeople());
			bookingRoomEntity.setStatus("Đã đặt");
			bookingRoomEntity.setUser(userEntity);
			bookingRoomEntity.setRooms(setRoomEntity);

			bookingRoomServiceImpl.createBookingRoom(bookingRoomEntity);

			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm đơn thành công!"));

		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}

	@GetMapping("/bookingRoom")
	public ResponseEntity<?> getBookingRoomAll() {
		List<BookingRoomEntity> listBookingRoom = bookingRoomServiceImpl.getBookingRoomAll();
		return new ResponseEntity<List<BookingRoomEntity>>(listBookingRoom, HttpStatus.OK);
	}

	@GetMapping("/bookingRoom/{id}")
	public ResponseEntity<?> getBookingRoomById(@PathVariable("id") long id) {
		if (bookingRoomServiceImpl.isBookingRoomExitsById(id)) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(),
					HttpStatus.NOT_FOUND.name(), "Không tìm thấy đơn đặt phòng có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			BookingRoomEntity listBookingRoom = bookingRoomServiceImpl.getBookingRoomById(id);
			return new ResponseEntity<BookingRoomEntity>(listBookingRoom, HttpStatus.OK);
		}
	}

	@GetMapping("/bookingRoom/user")
	public ResponseEntity<?> getBookingRoomByUser(@RequestParam("name") String fullName) {
		UserEntity userEntity = userServiceImpl.findUserByFullName(fullName)
				.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng: " + fullName + " này!"));
		List<BookingRoomEntity> listBookingRoom = bookingRoomServiceImpl.getBookingRoomByUser(userEntity);
		return new ResponseEntity<List<BookingRoomEntity>>(listBookingRoom, HttpStatus.OK);
	}

	@PutMapping("/bookingRoom/cancelBookingRoom")
	public ResponseEntity<?> cancelBookingRoom(@RequestParam("id") long id) {
		bookingRoomServiceImpl.cancelBookingRoom(id, "Đã hủy");
		return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Đã hủy thành công!"));
	}

	@PutMapping("/bookingRoom/{id}")
	public ResponseEntity<?> updateBookingRoom(@PathVariable("id") long id,
			@RequestBody @Valid BookingRoomDTO bookingRoomDTO) {
		try {
			if (bookingRoomServiceImpl.isBookingRoomExitsById(id)) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(),
						HttpStatus.NOT_FOUND.name(), "Không tìm thấy đơn đặt phòng có id= " + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
			} else {
				BookingRoomEntity oldBookingRoomEntity = bookingRoomServiceImpl.getBookingRoomById(id);

				UserEntity userEntity = userServiceImpl.findByUsername(bookingRoomDTO.getUser())
						.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng này!"));

				// Set Rooms
				Set<String> strRoom = bookingRoomDTO.getRooms();
				Set<RoomEntity> setRoomEntity = new HashSet<RoomEntity>();

				HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(bookingRoomDTO.getHotel())
						.orElseThrow(() -> new UsernameNotFoundException(
								"Không tìm thấy khách sạn" + bookingRoomDTO.getHotel() + " này!"));

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
				String strCheckInDate = bookingRoomDTO.getCheckInDate();
				Common controller1 = new Common();
				Date checkInDate = controller1.stringToDate(strCheckInDate);
				oldBookingRoomEntity.setCheckInDate(checkInDate);

				// Set CheckOutDate
				String strCheckOutDate = bookingRoomDTO.getCheckOutDate();
				Common controller2 = new Common();
				Date checkOutDate = controller2.stringToDate(strCheckOutDate);
				oldBookingRoomEntity.setCheckOutDate(checkOutDate);

				oldBookingRoomEntity.setTotalNumberOfPeople(bookingRoomDTO.getTotalNumberOfPeople());
				oldBookingRoomEntity.setUser(userEntity);
				oldBookingRoomEntity.setRooms(setRoomEntity);
				oldBookingRoomEntity.setStatus(bookingRoomDTO.getStatus());

				bookingRoomServiceImpl.updateBookingRoom(oldBookingRoomEntity);
				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}

	@DeleteMapping("/bookingRoom/{id}")
	public ResponseEntity<?> deleteBookingRoom(@PathVariable("id") long id) {
		try {
			bookingRoomServiceImpl.deleteBookingRoomById(id);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Xóa đơn thành công!"));
		} catch (Exception e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy đơn đặt phòng có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
}
