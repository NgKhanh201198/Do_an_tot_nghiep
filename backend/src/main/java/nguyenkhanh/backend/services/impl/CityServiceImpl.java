package nguyenkhanh.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nguyenkhanh.backend.entity.CityEntity;
import nguyenkhanh.backend.repository.CityRepository;
import nguyenkhanh.backend.services.ICityService;

public class CityServiceImpl implements ICityService {
	@Autowired
	CityRepository cityRepository;

	@Override
	public void createCity(CityEntity cityEntity) {
		cityRepository.save(cityEntity);
	}

	@Override
	public CityEntity getCityById(long id) {
		return cityRepository.getOne(id);
	}

	@Override
	public CityEntity updateCityById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CityEntity> getCityAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
