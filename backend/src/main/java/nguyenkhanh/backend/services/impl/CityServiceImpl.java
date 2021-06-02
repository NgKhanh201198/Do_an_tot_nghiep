package nguyenkhanh.backend.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.CityEntity;
import nguyenkhanh.backend.repository.CityRepository;
import nguyenkhanh.backend.services.ICityService;

@Service
public class CityServiceImpl implements ICityService {
	@Autowired
	CityRepository cityRepository;

	@Override
	public void createCity(CityEntity cityEntity) {
		cityRepository.save(cityEntity);
	}

	@Override
	public Optional<CityEntity> findByCityName(String cityName) {
		return cityRepository.findByCityName(cityName);
	}

	@Override
	public List<CityEntity> findTop5ById() {
		return cityRepository.findTop5ByOrderByIdAsc();
	}

	@Override
	public CityEntity getCityById(long id) {
		return cityRepository.findById(id);
	}

	@Override
	public List<CityEntity> getCityAll() {
		return cityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public void updateCity(CityEntity cityEntity) {
		cityRepository.save(cityEntity);
	}

	@Override
	public void deleteCityById(long id) {
		cityRepository.deleteById(id);
	}

	@Override
	public boolean isCityExitsById(long id) {
		return cityRepository.existsById(id);
	}

	@Override
	public boolean isCityExitsByCityName(String cityName) {
		return cityRepository.existsByCityName(cityName);
	}

	@Override
	public void updateImage(long id, String image) {
		cityRepository.updateImageCity(id, image);
	}

}
