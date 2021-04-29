package nguyenkhanh.backend.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberFormatValidator implements ConstraintValidator<PhoneNumberFormat, String> {

	public static final Pattern VALID_PHONE_ADDRESS_REGEX = Pattern.compile(
			"^(05[5|8|9]|08[1|2|3|4|5|8|6|9]|03[2|3|4|5|6|7|8|9]|07[0|9|7|6|8]|09[0|2|1|4|3|6|7|8|9]|01[2|9])+([0-9]{7})\\b$");

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext context) {
		if (phone == null || phone.isEmpty()) {
			return false;
		} else {
			Matcher matcher = VALID_PHONE_ADDRESS_REGEX.matcher(phone);
			if (matcher.matches()) {
				return true;
			} else {
				return false;
			}
		}
	}

}
