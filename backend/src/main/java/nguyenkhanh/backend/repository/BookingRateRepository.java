package nguyenkhanh.backend.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.BookingRateEntity;

@Repository
public interface BookingRateRepository extends JpaRepository<BookingRateEntity, Long> {
	public BookingRateEntity findById(long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM RoomBookedEntity rb where rb.bookingrateid = ?1")
	void deleteRoomBookedByBookingrateId(long bookingrateid);
}
