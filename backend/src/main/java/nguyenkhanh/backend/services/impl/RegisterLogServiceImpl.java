package nguyenkhanh.backend.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.repository.RegisterLogRepository;
import nguyenkhanh.backend.services.IRegisterLogService;

@Service
public class RegisterLogServiceImpl implements IRegisterLogService {
	@Autowired
	RegisterLogRepository registerLogRepository;

	@Override
	public RegisterLogEntity findByUser(long userid) {
		return registerLogRepository.findByUser(userid);
	}

	@Override
	public void save(RegisterLogEntity registerLogEntity) {
		registerLogRepository.save(registerLogEntity);
	}

	@Override
	public Optional<RegisterLogEntity> getToken(String token) {
		return registerLogRepository.findByToken(token);
	}

	@Override
	public String getStatus(String token) {
		return registerLogRepository.findByStatus(token);
	}

	@Override
	public void updateStatus(String token) {
		registerLogRepository.updateStatus(token);
	}

}
