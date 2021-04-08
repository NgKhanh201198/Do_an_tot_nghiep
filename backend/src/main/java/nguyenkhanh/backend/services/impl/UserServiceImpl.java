package nguyenkhanh.backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.entity.EStatus;
import nguyenkhanh.backend.repository.UserRepository;
import nguyenkhanh.backend.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public void save(UserEntity user) {
		userRepository.save(user);
	}

	@Override
	public List<UserEntity> getUserAll() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "userID"));
	}

	@Override
	public void update(UserEntity user) {
		userRepository.save(user);
	}

	@Override
	public void deletes(long[] ids) {
		for (long id : ids) {
			UserEntity oldUser = userRepository.getOne(id);
			oldUser.setStatus(EStatus.INACTIVE.toString());
			userRepository.save(oldUser);
		}

	}

	@Override
	public void deleteAndRestore(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUserExitsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean isUserExitsByPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumber(phoneNumber);
	}

}
