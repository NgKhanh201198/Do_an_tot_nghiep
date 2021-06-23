package nguyenkhanh.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.entity.RoleEntity;
import nguyenkhanh.backend.services.impl.RoleServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoleController {
	@Autowired
	RoleServiceImpl roleServiceImpl ;
	
	@GetMapping("/role")
	public ResponseEntity<?> listUser() {
		List<RoleEntity> roleEntity= roleServiceImpl.getRoleAll();
		return new ResponseEntity<>(roleEntity, HttpStatus.OK);
	}
}
