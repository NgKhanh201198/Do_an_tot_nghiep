package nguyenkhanh.backend.services;

import java.util.Optional;

import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.UserEntity;

public interface IRegisterLogService {
	public UserEntity findByUser(String token);

	public String getStatus(String token);

	public void save(RegisterLogEntity registerLogEntity);

	public void updateStatus(String token);

	public Optional<RegisterLogEntity> getToken(String token);
}
