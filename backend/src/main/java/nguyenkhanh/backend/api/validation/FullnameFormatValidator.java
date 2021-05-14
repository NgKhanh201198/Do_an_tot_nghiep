package nguyenkhanh.backend.api.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FullnameFormatValidator  implements ConstraintValidator<FullNameFormat, String>{
	public static final Pattern VALID_FULLNAME_ADDRESS_REGEX = Pattern
			.compile("^[0-9a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{3,100}$");

	@Override
	public boolean isValid(String fullname, ConstraintValidatorContext context) {
		if (fullname == null || fullname.isEmpty()) {
			return false;
		} else {
			Matcher matcher = VALID_FULLNAME_ADDRESS_REGEX.matcher(fullname);
			if (matcher.matches()) {
				return true;
			} else {
				return false;
			}
		}
	}

}
