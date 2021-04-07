package nguyenkhanh.backend.services;

import java.util.List;

import nguyenkhanh.backend.entity.UserEntity;

public interface IUserService {

	public void save(UserEntity user);

	public List<UserEntity> getUserAll();

	public void update(UserEntity user);

	public void deletes(long[] id);

	public void deleteAndRestore(long id);

	public boolean isUserExitsByUsername(String username);

	public boolean isUserExitsByPhoneNumber(String phoneNumber);
}
