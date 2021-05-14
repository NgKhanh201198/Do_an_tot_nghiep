package nguyenkhanh.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return hotelRepository.findAll();
	}

	@Override
	public void updateHotel(HotelEntity hotelEntity) {
		hotelRepository.save(hotelEntity);
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
