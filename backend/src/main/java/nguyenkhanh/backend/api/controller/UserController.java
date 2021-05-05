package nguyenkhanh.backend.api.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import nguyenkhanh.backend.dto.UserDTO;
import nguyenkhanh.backend.entity.EStatus;
import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.UserEntity;
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

	@PutMapping("/user/savePassword")
	public ResponseEntity<?> savePassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
		RegisterLogEntity registerLogEntity = registerLogServiceImpl.getToken(token)
				.orElseThrow(() -> new NotFoundException("Token không hợp lệ."));
		UserEntity userEntity = userServiceImpl.getUserById(registerLogEntity.getUser().getId());
		userServiceImpl.savePassword(userEntity.getId(), passwordEncoder.encode(newPassword));
		return ResponseEntity.ok("Thay đổi mật khẩu thành công.");
	}

	@PostMapping("/user")
	public ResponseEntity<?> createUser() {
		return new ResponseEntity<>("create", HttpStatus.OK);
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
					"Not found ID = " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			UserEntity user = userServiceImpl.getUserById(id);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
		}
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@RequestBody @Valid UserDTO userDTO, @PathVariable("id") long id) {
		try {
			if (userServiceImpl.isUserExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Not found ID = " + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				UserDTO oldUser = userServiceImpl.getOneUser(id);

				if (userServiceImpl.isUserExitsByPhoneNumber(userDTO.getPhoneNumber())
						&& !(userDTO.getPhoneNumber().equals(oldUser.getPhoneNumber()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(),
									"Số điện thoại đã được sử dụng! Vui lòng thử số điện thoại khác!"));
				}

				UserEntity userEntity = new UserEntity();

				// Set DateOfBirth
				String dateOfBirth = userDTO.getDateOfBirth();
				Date date;
				try {
					date = DateUtils.parseDate(dateOfBirth, new String[] { "yyyy-MM-dd", "dd-MM-yyyy" });
					userEntity.setDateOfBirth(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				userEntity.setId(id);
				userEntity.setFullName(userDTO.getFullName());
				userEntity.setPhoneNumber(userDTO.getPhoneNumber());
				userEntity.setGender(userDTO.getGender());

				userServiceImpl.update(userEntity);

				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
							"Tài khoản này đã được sử dụng! Vui lòng thử tài khoản khác"));
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

	@PutMapping("/user/updateAvatar/{id}")
	public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

			if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
				throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
			}
			String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
					file.getOriginalFilename().length());

			String uuidImage = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
			String avatar = BASE_URL + "api/user/loadAvatar/" + uuidImage;

			uploadFileService.save(file, uuidImage);
			userServiceImpl.updateAvatar(id, avatar);

			message = "Đã tải tệp '" + file.getOriginalFilename() + "' lên thành công.";
			return ResponseEntity.status(HttpStatus.OK)
					.body(new MessageResponse(new Date(), HttpStatus.OK.value(), message));
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	// load image
	@GetMapping("/user/loadAvatar/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = uploadFileService.load(filename);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "Content-Disposition: inlines").body(file);
	}
}
