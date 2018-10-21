package payments.test.api.res;

import org.apache.http.HttpStatus;

public enum Result {
    OK(HttpStatus.SC_OK),
    CREATED(HttpStatus.SC_CREATED),
    UNAUTHORIZED(HttpStatus.SC_UNAUTHORIZED),
    INVALID(HttpStatus.SC_UNPROCESSABLE_ENTITY),
    ERROR(HttpStatus.SC_INTERNAL_SERVER_ERROR);

    public final int code;

    Result(int code) {
        this.code = code;
    }
}
