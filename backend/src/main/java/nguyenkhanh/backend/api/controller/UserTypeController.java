package nguyenkhanh.backend.api.controller;

import java.util.List;

import nguyenkhanh.backend.services.IUserTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.entity.UserTypeEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserTypeController {
//	@Autowired
	private final IUserTypeService userTypeServiceImpl;

	public UserTypeController(IUserTypeService userTypeServiceImpl) {
		this.userTypeServiceImpl = userTypeServiceImpl;
	}

	@GetMapping("/userType")
	public ResponseEntity<?> listUser() {
		List<UserTypeEntity> userTypeEntity = userTypeServiceImpl.getUserTypeAll();
		return new ResponseEntity<>(userTypeEntity, HttpStatus.OK);
	}
}
