package moduloGestionUsuarios.ScheduleManagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class JwtFilterTest {

    private JwtFilter jwtFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    private final String SECRET_KEY = "supersecretpassword1234567891011121314";

    @BeforeEach
    void setup() {
        jwtFilter = new JwtFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void shouldAllowOpenPathWithoutToken() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");
        when(request.getMethod()).thenReturn("GET");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    void shouldReturnOkForOptionsMethod() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("OPTIONS");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/somepath");
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderMalformed() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/somepath");
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
        verify(filterChain, never()).doFilter(any(), any());
    }


    @Test
    void shouldReturnUnauthorizedWhenRoleIsNotAdmin() throws ServletException, IOException {
        String token = JWT.create()
                .withClaim("role", "USER")
                .sign(Algorithm.HMAC256(SECRET_KEY));
        when(request.getRequestURI()).thenReturn("/somepath");
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void shouldAllowWhenRoleIsAdmin() throws ServletException, IOException {
        String token = JWT.create()
                .withClaim("role", "ADMIN")
                .sign(Algorithm.HMAC256(SECRET_KEY));
        when(request.getRequestURI()).thenReturn("/somepath");
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }
}

