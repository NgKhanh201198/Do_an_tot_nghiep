package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.BookingRoomEntity;

@Repository
public interface BookingRoomRepository extends JpaRepository<BookingRoomEntity, Long> {

}
