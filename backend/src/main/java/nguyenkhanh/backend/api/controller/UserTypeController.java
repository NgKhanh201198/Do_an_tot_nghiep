package nguyenkhanh.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.entity.UserTypeEntity;
import nguyenkhanh.backend.services.impl.UserTypeServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserTypeController {

	@Autowired
	UserTypeServiceImpl userTypeServiceImpl;
	
	@GetMapping("/userType")
	public ResponseEntity<?> listUser() {
		List<UserTypeEntity> userTypeEntity = userTypeServiceImpl.getUserTypeAll();
		return new ResponseEntity<List<UserTypeEntity>>(userTypeEntity, HttpStatus.OK);
	}
}
