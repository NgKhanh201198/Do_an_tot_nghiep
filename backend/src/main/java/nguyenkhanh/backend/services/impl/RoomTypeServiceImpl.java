package nguyenkhanh.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.RoomTypeEntity;
import nguyenkhanh.backend.repository.RoomTypeRepository;
import nguyenkhanh.backend.services.IRoomTypeService;

@Service
public class RoomTypeServiceImpl implements IRoomTypeService {
	@Autowired
	private RoomTypeRepository roomTypeRepository;

	@Override
	public void createRoomType(RoomTypeEntity roomTypeEntity) {
		roomTypeRepository.save(roomTypeEntity);
	}

	@Override
	public Optional<RoomTypeEntity> findByRoomTypeName(String roomTypeName) {
		return roomTypeRepository.findByRoomTypeName(roomTypeName);
	}

	@Override
	public RoomTypeEntity getRoomTypeById(long id) {
		return roomTypeRepository.findById(id);
	}

	@Override
	public List<RoomTypeEntity> getRoomTypeAll() {
		return roomTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public void updateRoomType(RoomTypeEntity roomTypeEntity) {
		roomTypeRepository.save(roomTypeEntity);
	}

	@Override
	public void deleteRoomTypeById(long id) {
		roomTypeRepository.deleteById(id);
	}

	@Override
	public boolean isRoomTypeExitsById(long id) {
		return roomTypeRepository.existsById(id);
	}

	@Override
	public boolean isRoomTypeExitsByRoomTypeName(String roomTypeName) {
		return roomTypeRepository.existsByRoomTypeName(roomTypeName);
	}

}
