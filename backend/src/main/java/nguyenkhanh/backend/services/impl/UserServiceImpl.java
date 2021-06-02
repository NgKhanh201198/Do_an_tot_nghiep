package nguyenkhanh.backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import nguyenkhanh.backend.converter.UserConverter;
import nguyenkhanh.backend.dto.UserCustomerDTO;
import nguyenkhanh.backend.entity.EStatus;
import nguyenkhanh.backend.entity.RegisterLogEntity;
import nguyenkhanh.backend.entity.UserEntity;
import nguyenkhanh.backend.repository.TemplateRepository;
import nguyenkhanh.backend.repository.UserRepository;
import nguyenkhanh.backend.services.IUserService;
import nguyenkhanh.backend.services.SendEmailService;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	RegisterLogServiceImpl registerLogServiceImpl;

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	UserConverter userConverter;

	@Value("${dateExpiedSeconds}")
	private long DATE_EXPIED;

	@Value("${system.baseUrl}")
	private String BASE_URL;

	@Override
	public void createAccount(UserEntity user) {
		userRepository.save(user);
	}

	@Override
	public void save(UserEntity user) {
		userRepository.save(user);

		String token = UUID.randomUUID().toString();

		RegisterLogEntity registerLogEntity = new RegisterLogEntity(token, EStatus.INACTIVE.toString(),
				LocalDateTime.now().plusSeconds(DATE_EXPIED), user);
		registerLogServiceImpl.save(registerLogEntity);

		String link = BASE_URL + "api/auth/verifyEmail?token=" + token;

		sendEmailService.sendConfirmEmail(user.getUsername(), buildVerifyEmail(user.getFullName(), link));
	}

	@Override
	public void resetPassword(UserEntity user) {
//		String token = UUID.randomUUID().toString();

		RegisterLogEntity oldRegisterLogEntity = registerLogServiceImpl.findByUser(user);
		oldRegisterLogEntity.setStatus(EStatus.INACTIVE.toString());
		oldRegisterLogEntity.setDateActive(LocalDateTime.now().plusSeconds(DATE_EXPIED));

		registerLogServiceImpl.save(oldRegisterLogEntity);

		String link = "http://localhost:4200/user-reset-password?token=" + oldRegisterLogEntity.getToken();

		sendEmailService.sendResetPassword(user.getUsername(), buildResetPassword(user.getFullName(), link));
	}

	@Override
	public void savePassword(long id, String newPassword) {
		userRepository.savePassword(id, newPassword);
	}

	@Override
	public List<UserEntity> getUserAll() {
		return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@Override
	public void update(UserEntity user) {
		userRepository.updateUser(user.getId(), user.getFullName(), user.getPhoneNumber(), user.getDateOfBirth(),
				user.getGender());
	}

	@Override
	public void updateAccount(UserEntity user) {
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
	}

	@Override
	public boolean isUserExitsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean isUserExitsByPhoneNumber(String phoneNumber) {
		return userRepository.existsByPhoneNumber(phoneNumber);
	}

	public final String buildVerifyEmail(String username, String link) {
		String htmlSendEmail, s1, s2;
		s1 = templateRepository.findById(1l).getContents();
		s2 = s1.replace("hrefLink", link);
		htmlSendEmail = s2.replace("username", username);

		return htmlSendEmail;
	}

	public final String buildResetPassword(String username, String link) {
		String htmlSendEmail, s1, s2;
		s1 = templateRepository.findById(2l).getContents();
		s2 = s1.replace("hrefLink", link);
		htmlSendEmail = s2.replace("username", username);

		return htmlSendEmail;
	}

	@Override
	public void updateStatus(String username) {
		userRepository.updateStatus(username);
	}

	@Override
	public boolean isUserExitsById(long id) {
		return userRepository.existsById(id);
	}

	@Override
	public UserCustomerDTO getOneUser(long id) {
		UserEntity userEntity = userRepository.getOne(id);
		return userConverter.entityToDTO(userEntity);
	}

	@Override
	public void updateAvatar(long id, String avatar) {
		userRepository.updateImageUser(id, avatar);
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserEntity getUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<UserEntity> findUserByFullName(String fullName) {
		return userRepository.findByFullName(fullName);
	}

}
