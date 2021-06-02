package nguyenkhanh.backend.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.BookingRoomEntity;

@Repository
public interface BookingRoomRepository extends JpaRepository<BookingRoomEntity, Long> {
	public BookingRoomEntity findById(long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM RoomBookedEntity rb where rb.bookingroomid = ?1")
	void deleteRoomBookedByBookingRoomId(long bookingroomid);
}
