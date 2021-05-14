package nguyenkhanh.backend.api.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import nguyenkhanh.backend.config.security.JwtTokenUtils;
import nguyenkhanh.backend.dto.UserAccountDTO;
import nguyenkhanh.backend.dto.UserCustomerDTO;
import nguyenkhanh.backend.dto.UserDTO;
import nguyenkhanh.backend.entity.ERoles;
import nguyenkhanh.backend.entity.EStatus;
import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.RoleEntity;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.entity.UserTypeEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.exception.NotFoundException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.UploadFileService;
import nguyenkhanh.backend.services.impl.RegisterLogServiceImpl;
import nguyenkhanh.backend.services.impl.RoleServiceImpl;
import nguyenkhanh.backend.services.impl.UserServiceImpl;
import nguyenkhanh.backend.services.impl.UserTypeServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	@Value("${system.baseUrl}")
	private String BASE_URL;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	RoleServiceImpl roleServiceImpl;

	@Autowired
	UserTypeServiceImpl userTypeServiceImpl;

	@Autowired
	UploadFileService uploadFileService;

	@Autowired
	JwtTokenUtils jwtTokenUtils;

	@Autowired
	RegisterLogServiceImpl registerLogServiceImpl;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/account")
	public ResponseEntity<?> createAccount(@RequestBody @Valid UserDTO userDTO) {
		try {
			if (userServiceImpl.isUserExitsByUsername(userDTO.getUsername())) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Email này đã được sử dụng, vui lòng thử email khác!"));
			}

			UserEntity userEntity = new UserEntity();

			// Set User type
			UserTypeEntity userTypeEntity = userTypeServiceImpl.findByKeyName(userDTO.getUserType())
					.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy loại người dùng!"));
			userEntity.setUserType(userTypeEntity);

			// Set Roles
			Set<String> strRoles = userDTO.getRoles();
			Set<RoleEntity> roleEntity = new HashSet<RoleEntity>();

			if (strRoles == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
						HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Vai trò không được để trống"));
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "MANAGER":
						RoleEntity managerRole = roleServiceImpl.finByKeyName(ERoles.MANAGER.toString())
								.orElseThrow(() -> new RuntimeException("Không tìm thấy quyền này."));
						roleEntity.add(managerRole);
						break;
					case "ADMIN":
						RoleEntity adminRole = roleServiceImpl.finByKeyName(ERoles.ADMIN.toString())
								.orElseThrow(() -> new RuntimeException("Không tìm thấy quyền này."));
						roleEntity.add(adminRole);
						break;
					default:
						RoleEntity defaultRole = roleServiceImpl.finByKeyName(ERoles.CUSTOMER.toString())
								.orElseThrow(() -> new RuntimeException("Không tìm thấy quyền này."));
						roleEntity.add(defaultRole);
						break;
					}
				});
			}
			userEntity.setRoles(roleEntity);

			// Set DateOfBirth
			String dateOfBirth = userDTO.getDateOfBirth();
			Date date;
			if (isDateValid(dateOfBirth, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				date = formatter.parse(dateOfBirth);
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
				date = formatter.parse(dateOfBirth);
			}
			userEntity.setDateOfBirth(date);

			userEntity.setFullName(userDTO.getFullName());
			userEntity.setUsername(userDTO.getUsername());
			userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			userEntity.setGender(userDTO.getGender());
			userEntity.setStatus(userDTO.getStatus());
			userEntity.setStatus(userDTO.getStatus());

			// Save
			userServiceImpl.createAccount(userEntity);

			return ResponseEntity
					.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm tài khoản thành công!"));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
					"Bad Request", "Tài khoản đã được sử dụng. Vui lòng thử tài khoản khác!"));
		}
	}

	@GetMapping("/user")
//	@PreAuthorize("hasRole('GetUserAll')")
	public ResponseEntity<?> listUser() {
		List<UserEntity> userEntity = userServiceImpl.getUserAll();
		return new ResponseEntity<List<UserEntity>>(userEntity, HttpStatus.OK);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@Valid @PathVariable("id") long id) {
		if (userServiceImpl.isUserExitsById(id) == false) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy người dùng có id=" + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			UserEntity user = userServiceImpl.getUserById(id);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
	}

	@PutMapping("/account/{id}")
	public ResponseEntity<?> updateAccount(@RequestBody @Valid UserAccountDTO userAccountDTO,
			@PathVariable("id") long id) {
		try {
			if (userServiceImpl.isUserExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Not found ID = " + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				UserEntity oldUserEntity = userServiceImpl.getUserById(id);

				if (userServiceImpl.isUserExitsByPhoneNumber(userAccountDTO.getPhoneNumber())
						&& !(userAccountDTO.getPhoneNumber().equals(oldUserEntity.getPhoneNumber()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(),
									"Số điện thoại đã được sử dụng! Vui lòng thử số điện thoại khác!"));
				}

				// Set Roles
				Set<String> strRoles = userAccountDTO.getRoles();
				Set<RoleEntity> roles = new HashSet<>();

				if (strRoles == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(), "Vai trò không được để trống"));
				} else {
					strRoles.forEach(role -> {
						switch (role) {
						case "MANAGER":
							RoleEntity managerRole = roleServiceImpl.finByKeyName(ERoles.MANAGER.toString())
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(managerRole);
							break;
						case "ADMIN":
							RoleEntity adminRole = roleServiceImpl.finByKeyName(ERoles.ADMIN.toString())
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(adminRole);
							break;
						default:
							RoleEntity defaultRole = roleServiceImpl.finByKeyName(ERoles.CUSTOMER.toString())
									.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
							roles.add(defaultRole);
							break;
						}
					});
				}
				oldUserEntity.setRoles(roles);

				// Set DateOfBirth
				String dateOfBirth = userAccountDTO.getDateOfBirth();
				Date date;
				if (isDateValid(dateOfBirth, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					date = formatter.parse(dateOfBirth);
				} else {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					date = formatter.parse(dateOfBirth);
				}
				oldUserEntity.setDateOfBirth(date);

				// Set UserType
				UserTypeEntity typeEntity = userTypeServiceImpl.findByKeyName(userAccountDTO.getUserType())
						.orElseThrow(() -> new UsernameNotFoundException("Loại người dùng này không tồn tại!"));
				oldUserEntity.setUserType(typeEntity);

				oldUserEntity.setId(id);
				oldUserEntity.setFullName(userAccountDTO.getFullName());
				oldUserEntity.setPhoneNumber(userAccountDTO.getPhoneNumber());
				oldUserEntity.setGender(userAccountDTO.getGender());
				oldUserEntity.setStatus(userAccountDTO.getStatus());

				userServiceImpl.updateAccount(oldUserEntity);

				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
							"Đã có lỗi xảy ra, vui lòng thử lại!"));
		}
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<?> updateCustomer(@RequestBody @Valid UserCustomerDTO userCustomerDTO,
			@PathVariable("id") long id) {
		try {
			if (userServiceImpl.isUserExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy người dùng có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				UserCustomerDTO oldUser = userServiceImpl.getOneUser(id);

				if (userServiceImpl.isUserExitsByPhoneNumber(userCustomerDTO.getPhoneNumber())
						&& !(userCustomerDTO.getPhoneNumber().equals(oldUser.getPhoneNumber()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(),
									"Số điện thoại đã được sử dụng! Vui lòng thử số điện thoại khác!"));
				}

				UserEntity userEntity = new UserEntity();

				// Set DateOfBirth
				String dateOfBirth = userCustomerDTO.getDateOfBirth();
				Date date;
				try {
					date = DateUtils.parseDate(dateOfBirth, new String[] { "yyyy-MM-dd", "dd-MM-yyyy" });
					userEntity.setDateOfBirth(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				userEntity.setId(id);
				userEntity.setFullName(userCustomerDTO.getFullName());
				userEntity.setPhoneNumber(userCustomerDTO.getPhoneNumber());
				userEntity.setGender(userCustomerDTO.getGender());

				userServiceImpl.update(userEntity);

				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
							"Đã có lỗi xảy ra, vui lòng thử lại!"));
		}
	}

	@PostMapping("/user/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestParam("email") String email) {

		UserEntity userEntity = userServiceImpl.findByUsername(email)
				.orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại trên hệ thống!"));

		userServiceImpl.resetPassword(userEntity);

		return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(),
				"Chúng tôi đã gửi một hướng dẫn đến " + email + ". Vui lòng kiểm tra email để đặt lại mật khẩu!"));
	}

	@PostMapping("/user/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestParam(required = false) String token) {

		RegisterLogEntity registerLogEntity = registerLogServiceImpl.getToken(token)
				.orElseThrow(() -> new NotFoundException("Mã xác nhận của bạn không hợp lệ."));

		LocalDateTime dateActive = registerLogEntity.getDateActive();

		if (registerLogServiceImpl.getStatus(token).equals(EStatus.ACTIVE.toString())
				|| (dateActive.isBefore(LocalDateTime.now())
						&& registerLogEntity.getStatus().equals(EStatus.INACTIVE.toString()))) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
							"Mã xác nhận của bạn đã hết hạn. Hoặc đã được sử dụng!"));
		} else {
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Mã xác nhận hợp lệ"));
		}
	}

	@PutMapping("/user/savePassword")
	public ResponseEntity<?> savePassword(@RequestParam("token") String token,
			@RequestParam("newPassword") String newPassword) {
		RegisterLogEntity registerLogEntity = registerLogServiceImpl.getToken(token)
				.orElseThrow(() -> new NotFoundException("Token không hợp lệ."));

		UserEntity userEntity = userServiceImpl.getUserById(registerLogEntity.getUser().getId());

		userServiceImpl.savePassword(userEntity.getId(), passwordEncoder.encode(newPassword));
		registerLogServiceImpl.updateStatus(token);

		return ResponseEntity
				.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thay đổi mật khẩu thành công."));
	}

	@PutMapping("/user/updateAvatar/{id}")
	public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
		String response = "";
		try {
			if (userServiceImpl.isUserExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy người dùng có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {

				String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

				if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
					throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
				}
				String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
						file.getOriginalFilename().length());

				String uuidImage = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "")
						+ fileName.toLowerCase();
				String avatar = BASE_URL + "api/files/" + uuidImage;

				uploadFileService.save(file, uuidImage);
				userServiceImpl.updateAvatar(id, avatar);

				response = "Đã tải tệp '" + file.getOriginalFilename() + "' lên thành công.";
				return ResponseEntity.status(HttpStatus.OK)
						.body(new MessageResponse(new Date(), HttpStatus.OK.value(), response));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	public static boolean isDateValid(String date, String date_format) {
		try {
			DateFormat df = new SimpleDateFormat(date_format);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
