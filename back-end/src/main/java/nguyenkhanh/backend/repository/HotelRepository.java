package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.HotelEntity;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

}
