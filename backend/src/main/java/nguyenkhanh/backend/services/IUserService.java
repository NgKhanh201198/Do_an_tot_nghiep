package nguyenkhanh.backend.services;

import java.util.List;
import java.util.Optional;

import nguyenkhanh.backend.dto.UserCustomerDTO;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.entity.UserTypeEntity;

public interface IUserService {

	// Create
	public void createAccount(UserEntity user);

	public void save(UserEntity user);

	public void savePassword(long id, String password);

	// Read
	public Optional<UserEntity> findByUsername(String username);

	public Optional<UserEntity> findUserByFullName(String fullName);

	public List<UserEntity> getUserAll();

	public UserCustomerDTO getOneUser(long id);

	public UserEntity getUserById(long id);

	public long countByUserType(UserTypeEntity userTypeEntity);

	// Update
	public void update(UserEntity user);

	public void updateAccount(UserEntity user);

	public void updateAvatar(long id, String avatar);

	public void updateStatus(String username);

	public void resetPassword(UserEntity user);
	
	public void registerToken(UserEntity user);

	// Delete
	public void deletes(long[] id);

	//Check
	public boolean isUserExitsById(long id);

	public boolean isUserExitsByUsername(String username);

	public boolean isUserExitsByPhoneNumber(String phoneNumber);
}
