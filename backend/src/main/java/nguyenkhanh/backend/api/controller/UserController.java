package nguyenkhanh.backend.api.controller;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import nguyenkhanh.backend.config.security.JwtTokenUtils;
import nguyenkhanh.backend.dto.UserDTO;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.UploadFileService;
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

	@GetMapping("/user")
//	@PreAuthorize("hasRole('GetUserAll')")
	public ResponseEntity<?> listUser() {
		List<UserEntity> userEntity = userServiceImpl.getUserAll();
		return new ResponseEntity<List<UserEntity>>(userEntity, HttpStatus.OK);
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
									"Phone number is exist already! Please try other Phone number!"));
				}

				UserEntity userEntity = new UserEntity();

				// Set DateOfBirth
				String dateOfBirth = userDTO.getDateOfBirth();
				Date date;
				try {
					date = DateUtils.parseDate(dateOfBirth, new String[] { "dd/MM/yyyy", "yyyy/MM/dd HH:mm:ss" });
					userEntity.setDateOfBirth(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				userEntity.setUserID(id);
				userEntity.setFullName(userDTO.getFullName());
				userEntity.setPhoneNumber(userDTO.getPhoneNumber());
				userEntity.setGender(userDTO.getGender());

				userServiceImpl.update(userEntity);

				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Updated successfully!"));
			}
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
							"Account is already in use. Please try other account!"));
		}
	}

	@PutMapping("/user/updatePassword/{id}")
	public ResponseEntity<?> updatePassword(@PathVariable("id") long id, @RequestBody @Valid String token) {

		try {
			if (!(jwtTokenUtils.validateJwtToken(token))) {
				throw new ExpiredJwtException(null, null, "ss");
			}
			// xu ly

			MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
					"TOken het han!");
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		} catch (ExpiredJwtException e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.UNAUTHORIZED.value(), "Unauthorized",
					"TOken het han!");
			return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/user/updateAvatar/{id}")
	public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

			if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
				throw new BadRequestException("Invalid file, valid files include: jpg, png, gif");
			}
			String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
					file.getOriginalFilename().length());

			String uuidImage = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
			String avatar = BASE_URL + "api/user/loadAvatar/" + uuidImage;

			uploadFileService.save(file, uuidImage);
			userServiceImpl.updateAvatar(id, avatar);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
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
