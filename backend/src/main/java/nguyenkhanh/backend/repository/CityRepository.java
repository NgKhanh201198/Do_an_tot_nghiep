package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nguyenkhanh.backend.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Long> {

}
