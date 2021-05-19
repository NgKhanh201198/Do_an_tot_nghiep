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

import nguyenkhanh.backend.dto.CityDTO;
import nguyenkhanh.backend.entity.CityEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.UploadFileService;
import nguyenkhanh.backend.services.impl.CityServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CityController {
	@Value("${system.baseUrl}")
	private String BASE_URL;

	@Autowired
	CityServiceImpl cityServiceImpl;

	@Autowired
	UploadFileService uploadFileService;

	@PostMapping("/city")
	public ResponseEntity<?> createCity(@RequestParam("image") MultipartFile image,
			@RequestParam("cityName") String cityName, @RequestParam("description") String description) {
		try {
			if (cityServiceImpl.isCityExitsByCityName(cityName)) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Tên thành phố này đã tồn tại!"));
			}
			CityEntity cityEntity = new CityEntity();

			// Set Igame
			String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

			if (!ArrayUtils.contains(allowedMimeTypes, image.getContentType().toLowerCase())) {
				throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
			}
			String fileName = image.getOriginalFilename().substring(image.getOriginalFilename().length() - 4,
					image.getOriginalFilename().length());

			String uuidImage = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.toLowerCase();
			String imageURL = BASE_URL + "api/files/" + uuidImage;
			uploadFileService.save(image, uuidImage);

			cityEntity.setImage(imageURL);
			cityEntity.setCityName(cityName);
			cityEntity.setDescription(description);

			cityServiceImpl.createCity(cityEntity);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm mới thành công!"));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
					"Bad Request", "Tài khoản đã được sử dụng. Vui lòng thử tài khoản khác!"));
		}
	}

	@GetMapping("/city")
	public ResponseEntity<?> getCityAll() {
		List<CityEntity> listCity = cityServiceImpl.getCityAll();
		return new ResponseEntity<List<CityEntity>>(listCity, HttpStatus.OK);
	}

	@GetMapping("/city/{id}")
	public ResponseEntity<?> getCityById(@PathVariable("id") long id) {
		if (cityServiceImpl.isCityExitsById(id) == false) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy thành phố có id=" + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			CityEntity cityEntity = cityServiceImpl.getCityById(id);
			return new ResponseEntity<CityEntity>(cityEntity, HttpStatus.OK);
		}
	}

	@PutMapping("/city/{id}")
	public ResponseEntity<?> updateCity(@PathVariable("id") long id, @RequestBody CityDTO cityDTO) {
		try {
			if (cityServiceImpl.isCityExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy thành phố có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				CityEntity oldCityEntity = cityServiceImpl.getCityById(id);

				oldCityEntity.setCityName(cityDTO.getCityName());
				oldCityEntity.setDescription(cityDTO.getDescription());

				cityServiceImpl.updateCity(oldCityEntity);
				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}

	@PutMapping("/city/updateImage/{id}")
	public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
		String response = "";
		try {
			if (cityServiceImpl.isCityExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy thành phố có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {

				String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

				if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
					throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
				}
				String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
						file.getOriginalFilename().length());

				String uuidImage = "image-" + UUID.randomUUID().toString().replaceAll("-", "")
						+ fileName.toLowerCase();
				String image = BASE_URL + "api/files/" + uuidImage;

				uploadFileService.save(file, uuidImage);
				cityServiceImpl.updateImage(id, image);

				response = "Đã tải tệp '" + file.getOriginalFilename() + "' lên thành công.";
				return ResponseEntity.status(HttpStatus.OK)
						.body(new MessageResponse(new Date(), HttpStatus.OK.value(), response));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	@DeleteMapping("/room/{id}")
	public ResponseEntity<?> deleteCity(@Valid @PathVariable("id") long id) {
		try {
			cityServiceImpl.deleteCityById(id);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Xóa thành công!"));
		} catch (Exception e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy thành phố có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
}
