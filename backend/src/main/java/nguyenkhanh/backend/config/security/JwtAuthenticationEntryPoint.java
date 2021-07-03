package nguyenkhanh.backend.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import nguyenkhanh.backend.response.MessageResponse;

@Component
public final class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
//        response.se

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setTimestamp(new Date());
        messageResponse.setstatusCode(HttpStatus.UNAUTHORIZED.value());
        messageResponse.setError(HttpStatus.UNAUTHORIZED.name());
        messageResponse.setMessage("Xin lỗi, bạn cần xác thực để truy cập tài nguyên này");
        messageResponse.setPath(request.getRequestURI());
        Gson gson = new Gson();
        String errorMessage = gson.toJson(messageResponse);

        response.resetBuffer();
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(errorMessage);
        response.flushBuffer();
    }
}
