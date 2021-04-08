package nguyenkhanh.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.services.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping(path = "/user")
//	@PreAuthorize("hasRole('GetUserAll')")
	public ResponseEntity<?> listUser() {
		List<UserEntity> userEntity = userServiceImpl.getUserAll();
		return new ResponseEntity<List<UserEntity>>(userEntity, HttpStatus.OK);
	}
}
