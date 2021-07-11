package br.com.rhribeiro.baseprojectspringbatch.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Constraint created to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 11/07/21
 */
@Constraint(validatedBy = IpConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.TYPE,
        ElementType.METHOD,
        ElementType.PARAMETER})
public @interface IpConstraint {
    String message() default "{message.error.ip.default}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean require() default true;
}
