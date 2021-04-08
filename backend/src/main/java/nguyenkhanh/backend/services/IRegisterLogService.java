package nguyenkhanh.backend.services;

import nguyenkhanh.backend.entity.RegisterLogEntity;

public interface IRegisterLogService {
	RegisterLogEntity findByUser(long userid);
}
