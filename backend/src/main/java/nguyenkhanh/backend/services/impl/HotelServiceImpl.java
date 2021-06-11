package nguyenkhanh.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.CityEntity;
import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.repository.HotelRepository;
import nguyenkhanh.backend.services.IHotelService;

@Service
public class HotelServiceImpl implements IHotelService {
	@Autowired
	HotelRepository hotelRepository;

	@Override
	public void createHotel(HotelEntity hotelEntity) {
		hotelRepository.save(hotelEntity);
	}

	@Override
	public HotelEntity getHotelById(long id) {
		return hotelRepository.findById(id);
	}

	@Override
	public List<HotelEntity> getHotelAll() {
		return hotelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public List<HotelEntity> getRoomByCity(CityEntity cityEntity) {
		return hotelRepository.findByCity(cityEntity);
	}

	@Override
	public long countHotelAll() {
		return hotelRepository.count();
	}

	@Override
	public void updateHotel(HotelEntity hotelEntity) {
		hotelRepository.save(hotelEntity);
	}

	@Override
	public Optional<HotelEntity> findByHotelName(String hotelName) {
		return hotelRepository.findByHotelName(hotelName);
	}

	@Override
	public void updateImage(long id, String image) {
		hotelRepository.updateImageHotel(id, image);
	}

	@Override
	public void deleteHotelById(long id) {
		hotelRepository.deleteById(id);
	}

	@Override
	public boolean isHotelExitsById(long id) {
		return hotelRepository.existsById(id);
	}

	@Override
	public boolean isHotelExitsByHotelName(String hotelName) {
		return hotelRepository.existsByHotelName(hotelName);
	}

	@Override
	public boolean isHotelExitsByEmail(String email) {
		return hotelRepository.existsByEmail(email);
	}

	@Override
	public boolean isHotelExitsByPhoneNumber(String phoneNumber) {
		return hotelRepository.existsByPhoneNumber(phoneNumber);
	}

}
