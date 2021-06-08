package nguyenkhanh.backend.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.BookingRoomEntity;
import nguyenkhanh.backend.entity.UserEntity;

@Repository
public interface BookingRoomRepository extends JpaRepository<BookingRoomEntity, Long> {
	public BookingRoomEntity findById(long id);

	public List<BookingRoomEntity> findByUserOrderByStatus(UserEntity userEntity);

	@Transactional
	@Modifying
	@Query("DELETE FROM RoomBookedEntity rb WHERE rb.id = ?1")
	void deleteRoomBookedByBookingRoomId(long bookingroomid);

	@Transactional
	@Modifying
	@Query("UPDATE BookingRoomEntity rb SET rb.status = ?2 WHERE rb.id = ?1")
	void cancelBookingRoomById(long id, String status);
}
