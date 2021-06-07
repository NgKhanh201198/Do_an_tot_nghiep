package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.entity.CityEntity;
import nguyenkhanh.backend.entity.HotelEntity;

public interface IHotelService {
	// Create
	public void createHotel(HotelEntity hotelEntity);

	// Read
	public HotelEntity getHotelById(long id);

	public List<HotelEntity> getHotelAll();
	
	public List<HotelEntity> getRoomByCity(CityEntity cityEntity);

	public Optional<HotelEntity> findByHotelName(String hotelName);

	// Update
	public void updateHotel(HotelEntity hotelEntity);

	public void updateImage(long id, String image);

	// Delete
	public void deleteHotelById(long id);

	// Check
	public boolean isHotelExitsById(long id);

	public boolean isHotelExitsByHotelName(String hotelName);

	public boolean isHotelExitsByEmail(String email);

	public boolean isHotelExitsByPhoneNumber(String phoneNumber);
}
