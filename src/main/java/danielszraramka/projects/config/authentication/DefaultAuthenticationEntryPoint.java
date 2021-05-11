package danielszraramka.projects.config.authentication;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Qualifier("defaultAuthenticationEntryPoint")
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON_VALUE);
        if (e instanceof BadCredentialsException) {
            httpServletResponse.getOutputStream().println("{\"errorMessage\":\"Incorrect mail or password\"}");
            httpServletResponse.setStatus(UNAUTHORIZED.value());
        } else {
            httpServletResponse.getOutputStream().println("{\"errorMessage\":\"INTERNAL SERVER ERROR\"}");
            httpServletResponse.setStatus(INTERNAL_SERVER_ERROR.value());
        }
    }
}
