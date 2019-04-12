package in.amal.retail.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommonResponse {
    private Response  response;
    private Object data;

    @lombok.Getter
    @lombok.Setter
    @ToString
    public static class Response {
        private int code;
        private String status;
        private String message;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String cause;
    }

}
