package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyenkhanh.backend.entity.BookingRateEntity;

@Repository
public interface BookingRateRepository extends JpaRepository<BookingRateEntity, Long>{

}
