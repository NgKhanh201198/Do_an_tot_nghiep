package nguyenkhanh.backend.services;

import java.util.Optional;

import nguyenkhanh.backend.entity.RegisterLogEntity;

public interface IRegisterLogService {
	public RegisterLogEntity findByUser(long userid);

	public String getStatus(String token);

	public void save(RegisterLogEntity registerLogEntity);

	public void updateStatus(String token);

	public Optional<RegisterLogEntity> getToken(String token);
}
