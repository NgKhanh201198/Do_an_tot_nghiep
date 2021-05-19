package nguyenkhanh.backend.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.HotelEntity;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
	public Optional<HotelEntity> findByHotelName(String hotelName);
	
	public HotelEntity findById(long id);

	public Boolean existsByHotelName(String hotelName);
	
	public Boolean existsByEmail(String email);

	public Boolean existsByPhoneNumber(String phoneNumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE HotelEntity h " + "SET h.image = ?2" + " WHERE h.id = ?1")
	public int updateImageHotel(long id, String image);
}
