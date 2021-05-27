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

import nguyenkhanh.backend.dto.RoomDTO;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;
import nguyenkhanh.backend.entity.RoomTypeEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.UploadFileService;
import nguyenkhanh.backend.services.impl.HotelServiceImpl;
import nguyenkhanh.backend.services.impl.RoomServiceImpl;
import nguyenkhanh.backend.services.impl.RoomTypeServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoomController {
	@Value("${system.baseUrl}")
	private String BASE_URL;

	@Autowired
	RoomServiceImpl roomServiceImpl;

	@Autowired
	RoomTypeServiceImpl roomTypeServiceImpl;

	@Autowired
	HotelServiceImpl hotelServiceImpl;

	@Autowired
	UploadFileService uploadFileService;

	@PostMapping("/room")
	public ResponseEntity<?> createRoom(@RequestParam("image") MultipartFile image,
			@RequestParam("roomNumber") String roomNumber, @RequestParam("contents") String contents,
			@RequestParam("roomCost") Integer roomCost, @RequestParam("discount") Integer discount,
			@RequestParam("numberOfPeople") Integer numberOfPeople, @RequestParam("roomType") String roomType,
			@RequestParam("hotel") String hotel, @RequestParam("status") String status) {
		try {
			RoomTypeEntity roomTypeEntity = roomTypeServiceImpl.findByRoomTypeName(roomType)
					.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy loại phòng này!"));

			HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(hotel)
					.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy khách sạn này!"));

			List<String> list = roomServiceImpl.getListRoomNumber(hotelEntity);
			if (roomServiceImpl.isRoomExitsByRoomNumber(roomNumber)) {
				if (list.contains(roomNumber)) {
					return ResponseEntity.badRequest().body(new MessageResponse(new Date(),
							HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
							"Khách sạn " + hotelEntity.getHotelName() + " đã có tên phòng: " + roomNumber + "!"));
				}
			}

			RoomEntity roomEntity = new RoomEntity();

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
			roomEntity.setImage(imageURL);

			roomEntity.setRoomNumber(roomNumber);
			roomEntity.setContents(contents);
			roomEntity.setRoomCost(roomCost);
			roomEntity.setDiscount(discount);
			roomEntity.setNumberOfPeople(numberOfPeople);
			roomEntity.setRoomType(roomTypeEntity);
			roomEntity.setHotel(hotelEntity);
			roomEntity.setStatus(status);

			roomServiceImpl.createRoom(roomEntity);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm mới thành công!"));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	@GetMapping("/room")
	public ResponseEntity<?> getRoomAll() {
		List<RoomEntity> listRoom = roomServiceImpl.getRoomAll();
		return new ResponseEntity<List<RoomEntity>>(listRoom, HttpStatus.OK);
	}

	@GetMapping("/room/{id}")
	public ResponseEntity<?> getRoomById(@PathVariable("id") long id) {
		if (roomServiceImpl.isRoomExitsById(id) == false) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy phòng có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		} else {
			RoomEntity roomEntity = roomServiceImpl.getRoomById(id);
			return new ResponseEntity<RoomEntity>(roomEntity, HttpStatus.OK);
		}
	}

	@GetMapping("/room/byHotel")
	public ResponseEntity<?> getRoomByHotel(
			@RequestParam(name = "hotelName", defaultValue = "") String hotelName) {
		HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(hotelName)
				.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy khách sạn này!"));
//		if (hotelServiceImpl.isHotelExitsById(id) == false) {
//			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(),
//					HttpStatus.NOT_FOUND.name(), "Không tìm thấy khách sạn có id=" + id);
//			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
//		} else {
//			HotelEntity hotelEntity = hotelServiceImpl.getHotelById(id);
			List<RoomEntity> listRoom = roomServiceImpl.getRoomByHotel(hotelEntity);
			return new ResponseEntity<List<RoomEntity>>(listRoom, HttpStatus.OK);
//		}
	}

	@PutMapping("/room/{id}")
	public ResponseEntity<?> updateRoom(@PathVariable("id") long id, @RequestBody RoomDTO roomDTO) {
		try {
			if (roomServiceImpl.isRoomExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy phòng có id= " + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				RoomEntity oldRoomEntity = roomServiceImpl.getRoomById(id);

				HotelEntity hotelEntity = hotelServiceImpl.findByHotelName(roomDTO.getHotel())
						.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy khách sạn này!"));
				List<String> list = roomServiceImpl.getListRoomNumber(hotelEntity);
				list.removeIf(element -> element.equals(oldRoomEntity.getRoomNumber()));
				if (roomServiceImpl.isRoomExitsByRoomNumber(roomDTO.getRoomNumber())
						&& !(roomDTO.getRoomNumber().equals(oldRoomEntity.getRoomNumber()))) {
					if (list.contains(roomDTO.getRoomNumber())) {
						return ResponseEntity.badRequest()
								.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
										HttpStatus.BAD_REQUEST.name(), "Khách sạn " + hotelEntity.getHotelName()
												+ " đã có tên phòng: " + roomDTO.getRoomNumber() + "!"));
					}
				}

				oldRoomEntity.setRoomNumber(roomDTO.getRoomNumber());
				oldRoomEntity.setContents(roomDTO.getContents());
				oldRoomEntity.setNumberOfPeople(roomDTO.getNumberOfPeople());
				oldRoomEntity.setRoomCost(roomDTO.getRoomCost());
				oldRoomEntity.setDiscount(roomDTO.getDiscount());

				RoomTypeEntity roomTypeEntity = roomTypeServiceImpl.findByRoomTypeName(roomDTO.getRoomType())
						.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy loại phòng này!"));
				oldRoomEntity.setRoomType(roomTypeEntity);

				oldRoomEntity.setHotel(hotelEntity);
				oldRoomEntity.setStatus(roomDTO.getStatus());

				roomServiceImpl.updateRoom(oldRoomEntity);
				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}

	@PutMapping("/room/updateImage/{id}")
	public ResponseEntity<?> updateAvatar(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
		String response = "";
		try {
			if (roomServiceImpl.isRoomExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy phòng có id=" + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				RoomEntity oldRoomEntity = roomServiceImpl.getRoomById(id);

				String[] allowedMimeTypes = new String[] { "image/gif", "image/png", "image/jpeg" };

				if (!ArrayUtils.contains(allowedMimeTypes, file.getContentType().toLowerCase())) {
					throw new BadRequestException("Tệp không hợp lệ, các tệp hợp lệ bao gồm: .jpg, .png, .gif");
				}
				String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4,
						file.getOriginalFilename().length());

				String uuidImage = "avatar-" + UUID.randomUUID().toString().replaceAll("-", "")
						+ fileName.toLowerCase();
				String image = BASE_URL + "api/files/" + uuidImage;

				if (oldRoomEntity.getImage() != null) {
					uploadFileService.deleteByName(
							oldRoomEntity.getImage().substring(oldRoomEntity.getImage().length() - uuidImage.length()));
				}

				uploadFileService.save(file, uuidImage);
				roomServiceImpl.updateImage(id, image);

				response = "Đã tải tệp '" + file.getOriginalFilename() + "' lên thành công.";
				return ResponseEntity.status(HttpStatus.OK)
						.body(new MessageResponse(new Date(), HttpStatus.OK.value(), response));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	@DeleteMapping("/city/{id}")
	public ResponseEntity<?> deleteCity(@Valid @PathVariable("id") long id) {
		try {
			roomServiceImpl.deleteRoomById(id);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Xóa thành công!"));
		} catch (Exception e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy thành phố có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
}
