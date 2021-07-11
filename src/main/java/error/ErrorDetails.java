package error;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Renan Ribeiro
 * @date 11/07/21
 */

@Builder
@Getter
public class ErrorDetails implements Serializable {
    private final String status;
    private final int code;
    private final String message;
    private final Long timesTamp;
    private final String objectName;
    private final Map<String, ValidationErrorDetails> params;
}

