package br.com.rhribeiro.baseprojectspringbatch.constraints;

import br.com.rhribeiro.baseprojectspringbatch.utils.StringUtils;

import javax.validation.ConstraintValidatorContext;

/**
 * Constraint Validator created to handle annotations of type {@link IpConstraint} to be able to enable custom validations
 *
 * @author Renan Henrique Ribeiro
 * @since 11/07/21
 */
public class IpConstraintValidator implements GeneticConstraint<IpConstraint, String> {

    private static final String IP_FORMAT = "\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b";
    private boolean REQUIRE;

    @Override
    public void initialize(IpConstraint constrain) {
        REQUIRE = constrain.require();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (validating(context, (REQUIRE && StringUtils.isNullOrBlank(value)), "{message.error.ip.notblank}")) return false;
        else if(StringUtils.isNotNullAndBlank(value)) {
            if (validating(context, value.trim().length() > 15, "{message.error.ip.max.size}")) return false;
            if (validating(context, !value.matches(IP_FORMAT), "{message.error.ip.pattern}")) return false;
        }
        return true;
    }

    public boolean validating(ConstraintValidatorContext context, boolean validation, String msg) {
        if (validation) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
            return true;
        }
        return false;
    }
}

