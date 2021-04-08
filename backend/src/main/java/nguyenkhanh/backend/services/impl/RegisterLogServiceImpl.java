package nguyenkhanh.backend.services.impl;

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

}
