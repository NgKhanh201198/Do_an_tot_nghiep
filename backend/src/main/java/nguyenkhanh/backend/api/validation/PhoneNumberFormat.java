package nguyenkhanh.backend.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = PhoneNumberFormatValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberFormat {
	String message() default "Định dạng không hợp lệ";

	String pattern() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
