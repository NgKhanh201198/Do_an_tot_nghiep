package nguyenkhanh.backend.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.entity.RoomTypeEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoomTypeController {
	@GetMapping(path = "/roomtype/add")
	public ResponseEntity<?> add(@Valid @RequestBody RoomTypeEntity roomTypeEntity) {
		return ResponseEntity.ok(roomTypeEntity);
	}
}
