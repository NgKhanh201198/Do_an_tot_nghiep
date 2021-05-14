package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.entity.CityEntity;

public interface ICityService {

	// Create
	public void createCity(CityEntity cityEntity);

	// Read
	public Optional<CityEntity> findByCityName(String cityName);

	public CityEntity getCityById(long id);

	public List<CityEntity> getCityAll();

	// Update
	public void updateCity(CityEntity cityEntity);

	public void updateImage(long id, String image);

	// Delete
	public void deleteCityById(long id);

	// Check
	public boolean isCityExitsById(long id);

	public boolean isCityExitsByCityName(String cityName);
}
