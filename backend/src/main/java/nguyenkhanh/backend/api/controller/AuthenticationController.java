package nguyenkhanh.backend.api.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.config.security.JwtTokenUtils;
import nguyenkhanh.backend.entity.ERoles;
import nguyenkhanh.backend.entity.EStatus;
import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.RoleEntity;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.entity.UserTypeEntity;
import nguyenkhanh.backend.exception.NotFoundException;
import nguyenkhanh.backend.request.LoginRequest;
import nguyenkhanh.backend.request.RegisterRequest;
import nguyenkhanh.backend.response.JwtResponse;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.UserDetailsImpl;
import nguyenkhanh.backend.services.impl.RegisterLogServiceImpl;
import nguyenkhanh.backend.services.impl.RoleServiceImpl;
import nguyenkhanh.backend.services.impl.UserServiceImpl;
import nguyenkhanh.backend.services.impl.UserTypeServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthenticationController {
	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	UserTypeServiceImpl userTypeServiceImpl;

	@Autowired
	RegisterLogServiceImpl registerLogServiceImpl;

	@Autowired
	RoleServiceImpl roleServiceImpl;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@Value("${system.baseUrl}")
	private String BASE_URL;

	@PostMapping(path = "/auth/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {

		try {
			if (userServiceImpl.isUserExitsByUsername(registerRequest.getUsername())) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Email is exist already! Please try other email!"));
			}
			if (userServiceImpl.isUserExitsByPhoneNumber(registerRequest.getPhoneNumber())) {
				return ResponseEntity.badRequest()
						.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
								HttpStatus.BAD_REQUEST.name(),
								"Phone number is exist already! Please try other Phone number!"));
			}
			if (registerRequest.getPassword() == null) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Password mustn't be null value!"));
			}

			UserEntity userEntity = new UserEntity();

			// Set DateOfBirth
			String dateOfBirth = registerRequest.getDateOfBirth();
			Date date;
			try {
				date = DateUtils.parseDate(dateOfBirth, new String[] { "dd/MM/yyyy", "yyyy/MM/dd HH:mm:ss" });
				userEntity.setDateOfBirth(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// Set User type
			UserTypeEntity userTypeEntity = userTypeServiceImpl.finByUserTypeName(registerRequest.getUserType())
					.orElseThrow(() -> new UsernameNotFoundException("User type not found!"));
			userEntity.setUserType(userTypeEntity);

			// Get Roles
			Set<String> roles = registerRequest.getRoles();
			Set<RoleEntity> roleEntity = new HashSet<RoleEntity>();
			if (roles == null) {
				RoleEntity userRole = roleServiceImpl.finByRoleName(ERoles.CUSTOMER.toString())
						.orElseThrow(() -> new UsernameNotFoundException("Role is not found"));
				roleEntity.add(userRole);
			} else {
				roles.forEach(role -> {
					switch (role) {
					case "MANAGER":
						RoleEntity managerRole = roleServiceImpl.finByRoleName(ERoles.MANAGER.toString())
								.orElseThrow(() -> new UsernameNotFoundException("Role is not found"));
						roleEntity.add(managerRole);
						break;
					case "ADMIN":
						RoleEntity adminRole = roleServiceImpl.finByRoleName(ERoles.ADMIN.toString())
								.orElseThrow(() -> new UsernameNotFoundException("Role is not found"));
						roleEntity.add(adminRole);
						break;
					default:
						RoleEntity userRole = roleServiceImpl.finByRoleName(ERoles.CUSTOMER.toString())
								.orElseThrow(() -> new UsernameNotFoundException("Role is not found"));
						roleEntity.add(userRole);
					}
				});
			}

			userEntity.setRoles(roleEntity);

			userEntity.setFullName(registerRequest.getFullName());
			userEntity.setUsername(registerRequest.getUsername());
			userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			userEntity.setPhoneNumber(registerRequest.getPhoneNumber());
			userEntity.setAvatar(registerRequest.getAvatar());
			userEntity.setGender(registerRequest.getGender());
			userEntity.setStatus(EStatus.INACTIVE.toString());

			// Save
			userServiceImpl.save(userEntity);

			return ResponseEntity
					.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Registered successfully!"));
		} catch (DataAccessException ex) {
//			System.out.println(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
					"Bad Request", "Account is already in use. Please try other account!"));
		}
	}

	@PostMapping(path = "/auth/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			// Xác thực username password
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			// Set thông tin authentication vào Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Tạo token
			String jwtToken = jwtTokenUtils.generateToken(authentication);

			// Truy xuất thông tin người dùng đang đặng nhập.
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

			if (userDetailsImpl == null) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						"Bad Request", "Error: User not found!"));
			}

			List<String> roles = new ArrayList<String>();
			userDetailsImpl.getRoles().forEach(role -> {
				roles.add(role.getRoleName());
			});

			return ResponseEntity.ok(new JwtResponse(userDetailsImpl.getId(), userDetailsImpl.getUsername(),
					userDetailsImpl.getPassword(), userDetailsImpl.getFullName(), userDetailsImpl.getPhoneNumber(),
					userDetailsImpl.getDateOfBirth(), userDetailsImpl.getAvatar(), userDetailsImpl.getGender(),
					userDetailsImpl.getStatus(), roles, userDetailsImpl.getUserType().getUserTypeName(), jwtToken));

		} catch (DisabledException ex) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
					"Your account is disabled!");
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		} catch (BadCredentialsException ex) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
					"Username or password is incorrect!");
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		} catch (LockedException ex) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
					"Your account is LOCKED!");
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		} catch (CredentialsExpiredException ex) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
					"Your account has not verified email!");
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping(path = "auth/verifyEmail")
	public ResponseEntity<?> verifyEmail(@RequestParam(required = false) String token) {

		RegisterLogEntity registerLogEntity = registerLogServiceImpl.getToken(token)
				.orElseThrow(() -> new NotFoundException("Token not found"));

		LocalDateTime dateActive = registerLogEntity.getDateActive();

		try {
			if (dateActive.isBefore(LocalDateTime.now())
					&& registerLogEntity.getStatus().equals(EStatus.INACTIVE.toString())) {
				throw new TimeoutException("Your token has expired.");
			}
		} catch (TimeoutException exception) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.REQUEST_TIMEOUT.value(),
					"Request timeout", exception.getMessage());
			return new ResponseEntity<>(message, HttpStatus.REQUEST_TIMEOUT);
		}

		if (registerLogServiceImpl.getStatus(token).equals(EStatus.ACTIVE.toString())) {
			return ResponseEntity
					.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Your account has been confirmed!"));
		} else {
			registerLogServiceImpl.updateStatus(token);
			userServiceImpl.updateStatus(registerLogEntity.getUser().getUsername());
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(),
					"Verified email address. Sign in to continue."));
		}
	}

}
