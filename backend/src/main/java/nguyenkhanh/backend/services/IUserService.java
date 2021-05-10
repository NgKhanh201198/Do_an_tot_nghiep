package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.dto.UserCustomerDTO;
import nguyenkhanh.backend.entity.UserEntity;

public interface IUserService {

	public Optional<UserEntity> findByUsername(String username);

	public void createAccount(UserEntity user);

	public void save(UserEntity user);

	public void savePassword(long id, String password);

	public void resetPassword(UserEntity user);

	public List<UserEntity> getUserAll();

	public UserCustomerDTO getOneUser(long id);

	public UserEntity getUserById(long id);

	public void update(UserEntity user);

	public void updateAccount(UserEntity user);

	public void updateAvatar(long id, String avatar);

	public void updateStatus(String username);

	public void deletes(long[] id);

	public void deleteAndRestore(long id);

	public boolean isUserExitsById(long id);

	public boolean isUserExitsByUsername(String username);

	public boolean isUserExitsByPhoneNumber(String phoneNumber);
}
