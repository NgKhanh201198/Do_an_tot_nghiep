package nguyenkhanh.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;
import nguyenkhanh.backend.repository.RoomRepository;
import nguyenkhanh.backend.services.IRoomService;

@Service
public class RoomServiceImpl implements IRoomService {
	@Autowired
	RoomRepository roomRepository;

	@Override
	public void createRoom(RoomEntity roomEntity) {
		roomRepository.save(roomEntity);
	}

	@Override
	public Optional<RoomEntity> findByRoomNumber(String roomNumber) {
		return roomRepository.findByRoomNumber(roomNumber);
	}

	@Override
	public RoomEntity getRoomById(long id) {
		return roomRepository.findById(id);
	}

	@Override
	public List<RoomEntity> getRoomAll() {
		return roomRepository.findAll(Sort.by("hotel"));
	}

	@Override
	public List<String> getListRoomNumber(HotelEntity hotelEntity) {
		return roomRepository.getListRoomNumber(hotelEntity);
	}

	@Override
	public void updateRoom(RoomEntity roomEntity) {
		roomRepository.save(roomEntity);
	}

	@Override
	public void updateImage(long id, String image) {
		roomRepository.updateImageRoom(id, image);
	}

	@Override
	public void deleteRoomById(long id) {
		roomRepository.deleteById(id);
	}

	@Override
	public boolean isRoomExitsById(long id) {
		return roomRepository.existsById(id);
	}

	@Override
	public boolean isRoomExitsByRoomNumber(String roomNumber) {
		return roomRepository.existsByRoomNumber(roomNumber);
	}

	@Override
	public boolean isRoomExitsByHotel(HotelEntity hotelEntity) {
		return roomRepository.existsByHotel(hotelEntity);
	}

}
