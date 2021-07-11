package br.com.rhribeiro.baseprojectspringbatch.core.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * Interface generic constraints
 *
 * @author Renan Henrique Ribeiro
 * @since 11/07/21
 */
public interface GeneticConstraint<A extends Annotation, T> extends ConstraintValidator<A, T> {
    boolean validating(ConstraintValidatorContext context, boolean validation, String msg);
}
