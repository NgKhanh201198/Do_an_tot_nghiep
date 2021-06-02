package nguyenkhanh.backend.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {
	public Optional<CityEntity> findByCityName(String cityName);

	public List<CityEntity> findTop5ByOrderByIdAsc();

	public CityEntity findById(long id);

	public Boolean existsByCityName(String cityname);

	@Transactional
	@Modifying
	@Query("UPDATE CityEntity c " + "SET c.image = ?2" + " WHERE c.id = ?1")
	public int updateImageCity(long id, String avatar);
}
