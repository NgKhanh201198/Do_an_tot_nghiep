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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.dto.CheckRoomEmptyDTO;
import nguyenkhanh.backend.entity.BookingRateEntity;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;
import nguyenkhanh.backend.services.impl.BookingRateServiceImpl;
import nguyenkhanh.backend.services.impl.HotelServiceImpl;
import nguyenkhanh.backend.services.impl.RoomServiceImpl;
import nguyenkhanh.backend.services.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AppController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	HotelServiceImpl hotelServiceImpl;

	@Autowired
	BookingRateServiceImpl bookingRateServiceImpl;

	@Autowired
	RoomServiceImpl roomServiceImpl;

	@PostMapping("/checkRoomEmpty")
	public ResponseEntity<?> checkRoomEmpty(@RequestBody @Valid CheckRoomEmptyDTO roomEmptyDTO) {

		Set<String> listRoomNameBookingrate = new HashSet<String>();
		Common common = new Common();
		Date checkInDate = common.stringToDate(roomEmptyDTO.getCheckInDate());

		List<BookingRateEntity> listBookingrate = bookingRateServiceImpl.getBookingRateAll();
		listBookingrate.forEach(item -> {
			if (checkInDate.before(item.getCheckOutDate())) {
				item.getRooms().forEach(room -> {
					if (room.getHotel().getHotelName().equals(roomEmptyDTO.getHotel())) {
						listRoomNameBookingrate.add(room.getRoomNumber());
					}
				});
			}
		});

		HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(roomEmptyDTO.getHotel()).orElseThrow(
				() -> new UsernameNotFoundException("Không tìm thấy khách sạn: " + roomEmptyDTO.getHotel()));

		List<RoomEntity> listRoomEtity = roomServiceImpl.getRoomByHotel(hotelEntity);

		for (int i = 0; i < listRoomEtity.size(); i++) {
			if (listRoomNameBookingrate.contains(listRoomEtity.get(i).getRoomNumber())) {
				listRoomEtity.remove(i);
				i--;
			}
		}

		return new ResponseEntity<>(listRoomEtity, HttpStatus.OK);
	}
}