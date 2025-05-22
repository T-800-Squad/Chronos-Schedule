package moduloGestionUsuarios.ScheduleManagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;



/**
 * Custom JWT filter that extends {@link OncePerRequestFilter} to validate incoming requests.
 * This filter intercepts each request and:
 * - Skips validation for predefined open routes such as  Swagger documentation endpoints.
 * - Checks for the presence of a Bearer token in the Authorization header.
 * - Validates the JWT token using HMAC256 algorithm with a predefined secret key.
 * - Rejects the request with HTTP 401 if the token is missing, malformed, or expired.
 * The filter ensures that only requests with valid JWTs are processed further,
 * except for explicitly open endpoints.
 */
public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "supersecretpassword1234567891011121314";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String path = request.getRequestURI();
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        // Excluir las rutas de Swagger de la validación.
        List<String> openPaths = Arrays.asList(
                "/swagger-ui/index.html",
                "/swagger-ui/",
                "/v3/api-docs",
                "/swagger-resources/**",
                "/webjars/**",
                "/schedule",
                "/configuration/id"
        );

        // Si la ruta es una de las abiertas, continuar sin filtrar.
        if (openPaths.stream().anyMatch(path::startsWith)) {
            chain.doFilter(request, response);
            return;
        }

        // Si no hay token o no empieza con "Bearer ", rechazar la petición.
        if (header == null || !header.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
            return;
        }

        String token = header.substring(7); // Eliminar "Bearer ".
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
        } catch (JWTVerificationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tu sesión ya expiró");
        }
        String role = JWT.decode(token).getClaim("role").asString();
        if(!role.equals("ADMIN")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session error, no token");
            return;
        }
        chain.doFilter(request, response);
    }
}
