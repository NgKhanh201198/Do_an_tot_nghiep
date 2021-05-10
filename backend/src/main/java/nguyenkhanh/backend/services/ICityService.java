package nguyenkhanh.backend.services;

import java.util.List;

import nguyenkhanh.backend.entity.CityEntity;

public interface ICityService {
	public void createCity(CityEntity cityEntity);

	public CityEntity getCityById(long id);

	public List<CityEntity> getCityAll();

	public CityEntity updateCityById(long id);
}
