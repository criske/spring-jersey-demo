package pcf.crskdev.jersey.demo.common;

import java.util.Collections;
import java.util.Map;

public final class CommonException extends RuntimeException {

    private final int code;

    private final Map<String, Object> body;

    public CommonException(final int code, final Map<String, Object> body) {
        this.code = code;
        this.body = Collections.unmodifiableMap(body);
    }

    public CommonException(final int code, final String message) {
        this(code, Map.of("error", message));
    }

    public int getCode() {
        return code;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public static CommonException BadRequest(final String message) {
        return new CommonException(400, message);
    }
}
