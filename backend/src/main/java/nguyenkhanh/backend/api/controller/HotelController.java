package nguyenkhanh.backend.api.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import nguyenkhanh.backend.dto.HotelDTO;
import nguyenkhanh.backend.entity.CityEntity;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.UploadFileService;
import nguyenkhanh.backend.services.impl.CityServiceImpl;
import nguyenkhanh.backend.services.impl.HotelServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class HotelController {
	@Value("${system.baseUrl}")
	private String BASE_URL;

	@Autowired
	UploadFileService uploadFileService;

	@Autowired
	HotelServiceImpl hotelServiceImpl;

	@Autowired
	CityServiceImpl cityServiceImpl;

	@PostMapping("/hotel")
	public ResponseEntity<?> createCity(@RequestParam("image") MultipartFile image,
			@RequestParam("hotelName") String hotelName, @RequestParam("address") String address,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("city") String city) {
		try {
			if (hotelServiceImpl.isHotelExitsByHotelName(hotelName)) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Tên khách sạn này đã tồn tại!"));
			}
			if (hotelServiceImpl.isHotelExitsByEmail(email)) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Email này đã tồn tại, vui lòng chọn email khác!"));
			}
			if (hotelServiceImpl.isHotelExitsByPhoneNumber(phoneNumber)) {
				return ResponseEntity.badRequest()
						.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
								HttpStatus.BAD_REQUEST.name(),
								"Số điện thoại này đã được sử dụng, vui lòng chọn số điện thoại khác!"));
			}

			HotelEntity hotelEntity = new HotelEntity();

			// Set City
			CityEntity cityEntity = cityServiceImpl.findByCityName(city)
					.orElseThrow(() -> new UsernameNotFoundException("Không tìm thành phố này!"));

			hotelEntity.setCity(cityEntity);

			// Set Igame
			String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

			if (!ArrayUtils.contains(allowedMimeTypes, image.getContentType().toLowerCase())) {
				throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
			}
			String fileName = image.getOriginalFilename().substring(image.getOriginalFilename().length() - 4,
					image.getOriginalFilename().length());

			String uuidImage = "image-" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
			String imageURL = BASE_URL + "api/files/" + uuidImage;
			uploadFileService.save(image, uuidImage);
			hotelEntity.setImage(imageURL);

			hotelEntity.setHotelName(hotelName);
			hotelEntity.setAddress(address);
			hotelEntity.setEmail(email);
			hotelEntity.setPhoneNumber(phoneNumber);

			hotelServiceImpl.createHotel(hotelEntity);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm mới thành công!"));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	@GetMapping("/hotel")
	public ResponseEntity<?> getHotelAll() {
		List<HotelEntity> listHotel = hotelServiceImpl.getHotelAll();
		return new ResponseEntity<List<HotelEntity>>(listHotel, HttpStatus.OK);
	}

	@GetMapping("/hotel/{id}")
	public ResponseEntity<?> getHotelById(@PathVariable("id") long id) {
		if (hotelServiceImpl.isHotelExitsById(id) == false) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy khách sạn có id=" + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			HotelEntity hotelEntity = hotelServiceImpl.getHotelById(id);
			return new ResponseEntity<HotelEntity>(hotelEntity, HttpStatus.OK);
		}
	}

	@PutMapping("/hotel/{id}")
	public ResponseEntity<?> updateCity(@PathVariable("id") long id, @RequestBody @Valid HotelDTO hotelDTO) {
		try {
			if (hotelServiceImpl.isHotelExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy thành phố có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				HotelEntity oldHotelEntity = hotelServiceImpl.getHotelById(id);

				if (hotelServiceImpl.isHotelExitsByHotelName(hotelDTO.getHotelName())
						&& !(hotelDTO.getHotelName().equals(oldHotelEntity.getHotelName()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(), "Tên khách sạn này đã tồn tại!"));
				}
				if (hotelServiceImpl.isHotelExitsByEmail(hotelDTO.getEmail())
						&& !(hotelDTO.getEmail().equals(oldHotelEntity.getEmail()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(), "Email này đã tồn tại, vui lòng chọn email khác!"));
				}
				if (hotelServiceImpl.isHotelExitsByPhoneNumber(hotelDTO.getPhoneNumber())
						&& !(hotelDTO.getPhoneNumber().equals(oldHotelEntity.getPhoneNumber()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(),
									"Số điện thoại này đã được sử dụng, vui lòng chọn số điện thoại khác!"));
				}
				// Set City
				CityEntity cityEntity = cityServiceImpl.findByCityName(hotelDTO.getCity())
						.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy thành phố này!"));
				oldHotelEntity.setCity(cityEntity);

				oldHotelEntity.setHotelName(hotelDTO.getHotelName());
				oldHotelEntity.setEmail(hotelDTO.getEmail());
				oldHotelEntity.setPhoneNumber(hotelDTO.getPhoneNumber());
				oldHotelEntity.setAddress(hotelDTO.getAddress());

				hotelServiceImpl.updateHotel(oldHotelEntity);
				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}

	@PutMapping("/hotel/updateImage/{id}")
	public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
		String response = "";
		try {
			if (hotelServiceImpl.isHotelExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy thành phố có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
			} else {
				HotelEntity oldHotelEntity = hotelServiceImpl.getHotelById(id);

				String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

				if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
					throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
				}
				String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
						file.getOriginalFilename().length());

				String uuidImage = "image-" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
				String image = BASE_URL + "api/files/" + uuidImage;

				if (oldHotelEntity.getImage() != null) {
					uploadFileService.deleteByName(oldHotelEntity.getImage()
							.substring(oldHotelEntity.getImage().length() - uuidImage.length()));
				}

				uploadFileService.save(file, uuidImage);
				hotelServiceImpl.updateImage(id, image);

				response = "Đã tải tệp '" + file.getOriginalFilename() + "' lên thành công.";
				return ResponseEntity.status(HttpStatus.OK)
						.body(new MessageResponse(new Date(), HttpStatus.OK.value(), response));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	@DeleteMapping("/hotel/{id}")
	public ResponseEntity<?> deleteCity(@Valid @PathVariable("id") long id) {
		try {
			hotelServiceImpl.deleteHotelById(id);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Xóa thành công!"));
		} catch (Exception e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy khách sạn có id= s" + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
}
