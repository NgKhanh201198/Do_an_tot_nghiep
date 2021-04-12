package nguyenkhanh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nguyenkhanh.backend.entity.TemplateEntity;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long> {
	TemplateEntity findById(long id);
}
