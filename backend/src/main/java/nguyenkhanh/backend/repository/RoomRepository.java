package nguyenkhanh.backend.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.HotelEntity;
import nguyenkhanh.backend.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
	public Optional<RoomEntity> findByRoomNumber(String roomNumber);

	public Optional<RoomEntity> findByRoomNumberAndHotel(String roomNumber, HotelEntity hotelEntity);

	public RoomEntity findById(long id);

	public List<RoomEntity> findByHotel(HotelEntity hotelEntity);

	public Boolean existsByRoomNumber(String roomNumber);

	public Boolean existsByHotel(HotelEntity hotelEntity);

	@Transactional
	@Query("SELECT r.roomNumber FROM RoomEntity r " + " WHERE r.hotel = ?1")
	public List<String> getListRoomNumber(HotelEntity hotelEntity);

	@Transactional
	@Modifying
	@Query("UPDATE RoomEntity r " + "SET r.image = ?2" + " WHERE r.id = ?1")
	public int updateImageRoom(long id, String avatar);
}
