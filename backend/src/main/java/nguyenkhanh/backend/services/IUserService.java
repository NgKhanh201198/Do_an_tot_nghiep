package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.dto.UserDTO;
import nguyenkhanh.backend.entity.UserEntity;

public interface IUserService {

	public Optional<UserEntity> findByUsername(String username);

	public void save(UserEntity user);

	public void resetPassword(UserEntity user);

	public List<UserEntity> getUserAll();

	public UserDTO getOneUser(long id);
	
	public UserEntity getUserById(long id);

	public void update(UserEntity user);

	public void updateAvatar(long id, String avatar);

	public void updateStatus(String username);

	public void deletes(long[] id);

	public void deleteAndRestore(long id);

	public boolean isUserExitsById(long id);

	public boolean isUserExitsByUsername(String username);

	public boolean isUserExitsByPhoneNumber(String phoneNumber);
}
