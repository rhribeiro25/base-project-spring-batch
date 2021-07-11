package br.com.rhribeiro.baseprojectspringbatch.error;

import br.com.rhribeiro.baseprojectspringbatch.utils.StaticContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Class Validation Error
 *
 * @author Renan Ribeiro
 * @since 11/07/2021
 */
@Slf4j
public class ValidationError {

    public static List<ParamErrorDetails> getParamErrorDetails(MethodArgumentNotValidException ex) {
        List<FieldError> allErrors = ex.getFieldErrors();
        List<ParamErrorDetails> params = new ArrayList<>();
        allErrors.forEach(x -> {
            params.add(ParamErrorDetails.builder()
                    .value(getValue(x))
                    .message(messageSource(x))
                    .property(x.getField())
                    .build());
        });
        return params;
    }

    private static String getValue(FieldError field) {
        return field.getRejectedValue() != null ? field.getRejectedValue().toString() : null;
    }

    private static String messageSource(FieldError x) {
        if (!x.getCode().contains("Constraint")) {
            String property = new StringBuffer("message.error.").
                    append(x.getField().replaceAll("\\[(.*?)\\]", "").toLowerCase())
                    .append(".")
                    .append(x.getCode().toLowerCase())
                    .toString();
            String msg = "";
            MessageSource messageSource = StaticContextUtils.getBean(MessageSource.class);
            try {
                msg = messageSource.getMessage(property, null, Locale.getDefault());
            } catch (NoSuchMessageException e) {
                msg = getMessageDefaultOrCustomized(x, messageSource);
            }
            return msg;
        } else return x.getDefaultMessage();
    }

    private static String getMessageDefaultOrCustomized(FieldError field, MessageSource messageSource) {
        String msg;
        if (field.getDefaultMessage().contains("{") && field.getDefaultMessage().contains("}")) {
            msg = messageSource.getMessage(field.getDefaultMessage().replaceAll("\\{", "").replaceAll("\\}", ""), null, Locale.getDefault());
        } else {
            msg = field.getDefaultMessage();
        }
        return msg;
    }
}
