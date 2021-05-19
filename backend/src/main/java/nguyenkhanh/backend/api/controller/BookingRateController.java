package nguyenkhanh.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.entity.BookingRateEntity;
import nguyenkhanh.backend.services.impl.BookingRateServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookingRateController {
	@Autowired
	BookingRateServiceImpl bookingRateServiceImpl;

	@GetMapping("/bookingrate")
	public ResponseEntity<?> getBookingRateAll() {
		List<BookingRateEntity> listBookingRate = bookingRateServiceImpl.getBookingRateAll();
		return new ResponseEntity<List<BookingRateEntity>>(listBookingRate, HttpStatus.OK);
	}

	@GetMapping("/bookingrate/{id}")
	public ResponseEntity<?> getBookingRateById(@PathVariable("id") long id) {
		BookingRateEntity bookingRate = bookingRateServiceImpl.getBookingRateById(id);
		return new ResponseEntity<BookingRateEntity>(bookingRate, HttpStatus.OK);
	}
}
