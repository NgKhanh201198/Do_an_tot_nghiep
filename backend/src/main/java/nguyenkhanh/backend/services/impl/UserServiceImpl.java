package nguyenkhanh.backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

	@Value("${email.dateExpiedSeconds}")
	private long DATE_EXPIED;

	@Value("${system.baseUrl}")
	private String BASE_URL;

	@Override
	public void save(UserEntity user) {
		userRepository.save(user);
		String token = UUID.randomUUID().toString();

		RegisterLogEntity registerLogEntity = new RegisterLogEntity(token, EStatus.INACTIVE.toString(),
				LocalDateTime.now().plusSeconds(DATE_EXPIED), user);
		registerLogServiceImpl.save(registerLogEntity);

		String link = BASE_URL + "api/auth/verifyEmail?token=" + token;

		sendEmailService.sendEMail(user.getUsername(), buildEmail(user.getFullName(), link));
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

	public final String buildEmail(String username, String link) {
		String htmlSendEmail, s1, s2;
		s1 = templateRepository.findById(1l).getContents();
		s2 = s1.replace("emailVerificationLink", link);
		htmlSendEmail = s2.replace("username", username);

		return htmlSendEmail;

//		return "<div style=\"width:60%;padding:20px 60px;margin:auto;background-color:#f1f1f1;\">\r\n"
//				+ "        <p style=\"font-family:'Segoe UI',Arial,sans-serif;font-size: 32px;font-weight: 400;margin: 20px 0px -5px 0px;\">Hello "
//				+ name + ",</p>\r\n"
//				+ "        <p style=\"display:block;font-size: 23px;font-weight: 100;font-family:'Segoe UI',Arial,sans-serif;text-align:left;color:#000000;margin-bottom:30px;\">To continue creating your account. Please click on the below link to activate your account:</p>\r\n"
//				+ "        <a href=\"" + link
//				+ "\" style=\"display:block;padding:15px 40px;text-decoration:none;font-family:'Segoe UI',Arial,sans-serif;font-weight: 100;background:linear-gradient(90deg,#3a9bed 25%,#235ecf 100%);border-radius:5px;text-transform:uppercase;letter-spacing:5px;color:#ffffff;width:40%;line-height:25px;text-align:center;margin:auto;font-size:18px;\">Verify mail</a>\r\n"
//				+ "        <p style=\"font-family: 'Segoe UI',Arial,sans-serif;font-size: 16px;font-weight: 400;padding-top: 20px;\"><b>Note:</b> This link will expire in 10 minutes!</p>\r\n"
//				+ "</div>";

//		return "<div style=\"max-width:640px;margin:0 auto;border-radius:4px;overflow:hidden\">\r\n" + 
//				"    <div style=\"margin:0px auto;max-width:640px;background:#ffffff\">\r\n" + 
//				"        <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-size:0px;width:100%;background:#ffffff\" align=\"center\" border=\"0\">\r\n" + 
//				"            <tbody>\r\n" + 
//				"                <tr>\r\n" + 
//				"                    <td style=\"text-align:center;vertical-align:top;direction:ltr;font-size:0px;padding:40px 50px\">\r\n" + 
//				"                        <div aria-labelledby=\"mj-column-per-100\" class=\"m_425914193671605140mj-column-per-100\" style=\"vertical-align:top;display:inline-block;direction:ltr;font-size:13px;text-align:left;width:100%\">\r\n" + 
//				"                            <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\">\r\n" + 
//				"                                <tbody>\r\n" + 
//				"                                    <tr>\r\n" + 
//				"                                        <td style=\"word-break:break-word;font-size:0px;padding:0px\" align=\"left\">\r\n" + 
//				"                                            <div style=\"color:#737f8d;font-family:Whitney,Helvetica Neue,Helvetica,Arial,Lucida Grande,sans-serif;font-size:16px;line-height:24px;text-align:left\">\r\n" + 
//				"                                                <h2 style=\"font-family:Whitney,Helvetica Neue,Helvetica,Arial,Lucida Grande,sans-serif;font-weight:500;font-size:25px;color:#4f545c;letter-spacing:0.27px\">Hello "+name+",</h2>\r\n" + 
//				"                                                <p style=\"display: block;font-size: 20px;font-weight: 100;font-family:'Segoe UI',Arial,sans-serif;text-align:left;color:#000000; margin-bottom:30px;line-height:28px;\">\r\n" + 
//				"                                                    Thanks for registering for an account on Discord! Before we get started, we just need to confirm that this is you. Click the button below to verify your email\r\n" + 
//				"                                                </p>\r\n" + 
//				"                                            </div>\r\n" + 
//				"                                        </td>\r\n" + 
//				"                                    </tr>\r\n" + 
//				"                                    <tr>\r\n" + 
//				"                                        <td style=\"word-break:break-word;font-size:0px;padding:10px 25px;padding-top:20px\" align=\"center\">\r\n" + 
//				"                                            <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:separate\" align=\"center\" border=\"0\">\r\n" + 
//				"                                                <tbody>\r\n" + 
//				"                                                    <tr>\r\n" + 
//				"                                                        <td>\r\n" + 
//				"                                                            <a href=\""+link+"\" style=\"display:block;padding: 12px 35px;text-decoration:none;font-family:'Segoe UI',Arial,sans-serif;font-weight: 100;background:linear-gradient(90deg,#3a9bed 0%,#235ecf 100%);border-radius:5px;text-transform:uppercase;letter-spacing:5px;color:#ffffff;width: 70%;line-height:25px;text-align:center;margin:auto;font-size:18px;\">Verify mail</a>\r\n" + 
//				"                                                        </td>\r\n" + 
//				"                                                    </tr>\r\n" + 
//				"                                                </tbody>\r\n" + 
//				"                                            </table>\r\n" + 
//				"                                        </td>\r\n" + 
//				"                                    </tr>\r\n" + 
//				"                                    <tr>\r\n" + 
//				"                                        <td style=\"word-break:break-word;font-size:0px;padding:30px 0px\">\r\n" + 
//				"                                            <p style=\"font-size:1px;margin:0px auto;border-top:1px solid #dcddde;width:100%\"></p>\r\n" + 
//				"                                        </td>\r\n" + 
//				"                                    </tr>\r\n" + 
//				"                                    <tr>\r\n" + 
//				"                                        <td style=\"word-break:break-word;font-size:0px;padding:0px\" align=\"left\">\r\n" + 
//				"                                            <p style=\"font-family: 'Segoe UI',Arial,sans-serif;font-size: 16px;font-weight: 400;\"><b>Note:</b> This link will expire in 10 minutes!</p>\r\n" + 
//				"                                        </td>\r\n" + 
//				"                                    </tr>\r\n" + 
//				"                                </tbody>\r\n" + 
//				"                            </table>\r\n" + 
//				"                        </div>\r\n" + 
//				"                    </td>\r\n" + 
//				"                </tr>\r\n" + 
//				"            </tbody>\r\n" + 
//				"        </table>\r\n" + 
//				"    </div>\r\n" + 
//				"</div>";
	}

	@Override
	public void updateStatus(String username) {
		userRepository.updateStatus(username);
	}
}
