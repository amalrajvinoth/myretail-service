package in.amal.banking.util;

import in.amal.banking.model.CommonResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.invoke.MethodHandles;

public class Utils {
    private static final Logger LOG = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static ResponseEntity<CommonResponse> buildErrorResponse(String msg, Throwable t, HttpStatus httpStatus) {
        CommonResponse commonResponse = new CommonResponse();
        CommonResponse.Response response = new CommonResponse.Response();
        response.setCode(httpStatus.value());
        response.setStatus(httpStatus.getReasonPhrase());
        response.setMessage(msg);
        response.setCause(t.getMessage());
        commonResponse.setResponse(response);
        if (LOG.isInfoEnabled()) {
            LOG.error("Error occurred: {}", response);
        }

        return new ResponseEntity<>(commonResponse, httpStatus);
    }

    public static ResponseEntity<CommonResponse> buildSuccessResponse(String msg, Object data) {
        CommonResponse commonResponse = new CommonResponse();
        CommonResponse.Response response = new CommonResponse.Response();
        response.setCode(HttpStatus.OK.value());
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setMessage(msg);
        commonResponse.setResponse(response);
        commonResponse.setData(data);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponse> buildSuccessResponse(Object data) {
        return buildSuccessResponse("Success", data);
    }
}
