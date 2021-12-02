package nguyenkhanh.backend.api.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import nguyenkhanh.backend.services.IRoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nguyenkhanh.backend.dto.RoomTypeDTO;
import nguyenkhanh.backend.entity.RoomTypeEntity;
import nguyenkhanh.backend.exception.BadRequestException;
import nguyenkhanh.backend.response.MessageResponse;
import nguyenkhanh.backend.services.impl.RoomTypeServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RoomTypeController {
	@Autowired
	private IRoomTypeService roomTypeServiceImpl;

	@PostMapping("/roomtype")
	//	@PreAuthorize("hasRole('create_roomtype')")
	public ResponseEntity<?> createRoomType(@RequestBody @Valid RoomTypeDTO roomTypeDTO) {
		try {
			if(roomTypeServiceImpl.isRoomTypeExitsByRoomTypeName(roomTypeDTO.getRoomTypeName())) {
				return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name(), "Tên loại phòng này đã tồn tại!"));
			}
			RoomTypeEntity roomTypeEntity = new RoomTypeEntity();
			roomTypeEntity.setRoomTypeName(roomTypeDTO.getRoomTypeName());
			roomTypeEntity.setDescription(roomTypeDTO.getDescription());
			
			roomTypeServiceImpl.createRoomType(roomTypeEntity);

			return ResponseEntity
					.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Thêm loại phòng thành công!"));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}
	}

	@GetMapping(path = "/roomtype")
	//	@PreAuthorize("hasRole('list_roomtype')")
	public ResponseEntity<?> getRoomTypeAll() {
		List<RoomTypeEntity> listRoomType = roomTypeServiceImpl.getRoomTypeAll();
		return new ResponseEntity<List<RoomTypeEntity>>(listRoomType, HttpStatus.OK);
	}

	@GetMapping(path = "/roomtype/{id}")
	public ResponseEntity<?> getRoomTypeById(@PathVariable("id") long id) {
		RoomTypeEntity listRoomType = roomTypeServiceImpl.getRoomTypeById(id);
		return new ResponseEntity<RoomTypeEntity>(listRoomType, HttpStatus.OK);
	}

	@PutMapping("/roomtype/{id}")
	//	@PreAuthorize("hasRole('update_roomtype')")
	public ResponseEntity<?> updateRoomType(@PathVariable("id") long id, @RequestBody RoomTypeDTO roomTypeDTO) {
		try {
			if (roomTypeServiceImpl.isRoomTypeExitsById(id) == false) {
				MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
						"Không tìm thấy loại phòng có id= " + id);
				return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);

			} else {
				RoomTypeEntity oldRoomTypeEntity= roomTypeServiceImpl.getRoomTypeById(id);
				
				if (roomTypeServiceImpl.isRoomTypeExitsByRoomTypeName(roomTypeDTO.getRoomTypeName())
						&& !(roomTypeDTO.getRoomTypeName().equals(oldRoomTypeEntity.getRoomTypeName()))) {
					return ResponseEntity.badRequest()
							.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
									HttpStatus.BAD_REQUEST.name(), "Tên loại phòng này đã tồn tại!"));
				}

				oldRoomTypeEntity.setRoomTypeName(roomTypeDTO.getRoomTypeName());
				oldRoomTypeEntity.setDescription(roomTypeDTO.getDescription());

				roomTypeServiceImpl.updateRoomType(oldRoomTypeEntity);
				return ResponseEntity
						.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Cập nhật thành công!"));
			}
		} catch (BadRequestException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(new Date(),
					HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
		}

	}
	
	@DeleteMapping("/roomtype/{id}")
	//	@PreAuthorize("hasRole('delete_roomtype')")
	public ResponseEntity<?> deleteRoomType(@Valid @PathVariable("id") long id) {
		try {
			roomTypeServiceImpl.deleteRoomTypeById(id);
			return ResponseEntity.ok(new MessageResponse(new Date(), HttpStatus.OK.value(), "Xóa thành công!"));
		} catch (Exception e) {
			MessageResponse message = new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Not Found",
					"Không tìm thấy loại phòng có id= " + id);
			return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
		}
	}
}
