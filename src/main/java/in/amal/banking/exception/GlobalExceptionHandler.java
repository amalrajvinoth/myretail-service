package in.amal.banking.exception;

import in.amal.banking.model.CommonResponse;
import in.amal.banking.util.Utils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return Utils.buildErrorResponse("Invalid request parameters", exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponse> handleConstraintViolationException(
            ConstraintViolationException exception, HttpServletRequest request) throws URISyntaxException {
        StringWriter message = new StringWriter();
        List<NameValuePair> params = URLEncodedUtils.parse(request.getQueryString(), Charset.defaultCharset());

        if (!exception.getConstraintViolations().isEmpty()) {
            for (ConstraintViolation e : exception.getConstraintViolations()) {
                String paramName = e.getPropertyPath().toString();
                if (!params.isEmpty()) {
                    int i = Integer.parseInt(paramName.substring(paramName.length() - 1));
                    if (params.size() >= i) {
                        paramName = params.get(i).getName();
                        paramName = e.getPropertyPath().toString() + "(" + paramName + ")";
                    }
                }
                message.append(" Invalid Field: ").append(paramName).append(" -- ").append(e.getMessage()).append(".");
            }
        }
        return Utils.buildErrorResponse(message.toString(), exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseBody
    public ResponseEntity<CommonResponse> handleException(Throwable th, HttpServletRequest request) {
        return Utils.buildErrorResponse("Failure", th, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}