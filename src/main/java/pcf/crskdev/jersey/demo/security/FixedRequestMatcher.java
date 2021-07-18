package pcf.crskdev.jersey.demo.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public final class FixedRequestMatcher  implements RequestMatcher {

    private final String method;

    private final String path;

    public FixedRequestMatcher(final HttpMethod method, final String path) {
        this.method = Optional.of(method).map(Enum::name).orElse(null);
        this.path = path;
    }

    public FixedRequestMatcher(final String path){
        this(null, path);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        final String toMatch;
        if(request.getPathInfo() != null){
            toMatch = request.getPathInfo();
        }else{
            toMatch = request.getServletPath();
        }
        return (this.method == null || request.getMethod().equalsIgnoreCase(method))
            && this.path.equalsIgnoreCase(toMatch);
    }
}
