package com.niels.homebanking.config.auth;

import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.service.UserAccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

@Component
public class RequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private JwtParser jwtParser;

    // Define routes to skip JWT validation
    private static final List<String> SKIP_JWT_PATHS = Arrays.asList(
            "/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/user"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // Skip JWT validation for specified paths and methods
        return SKIP_JWT_PATHS.stream().anyMatch(pattern ->
                path.matches(pattern.replace("/**", ".*")) ||
                        (pattern.equals("/user") && method.equals("POST"))
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        logger.info("Processing request: {} {}", request.getMethod(), request.getRequestURI());

        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            logger.warn("Missing or invalid Authorization header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String jwtToken = requestTokenHeader.substring(7);
        try {
            Claims claims = jwtParser.parseClaimsJws(jwtToken).getBody();
            String subject = claims.getSubject();
            logger.info("JWT Claims subject: {}", subject);
            UUID userAccountId = UUID.fromString(subject);
            String userId = userAccountId.toString();

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userAccountService.loadUserByUsername(userId);
                logger.info("UserDetails loaded: {}", userDetails.getUsername());
                if (validateToken(jwtToken, userDetails, userAccountId)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentication set for user: {}", userId);
                } else {
                    logger.warn("Token validation failed for user: {}", userId);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            }
        } catch (MalformedJwtException e) {
            logger.warn("Malformed JWT token: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Malformed JWT token");
            return;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT Token has expired: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token expired");
            return;
        } catch (Exception e) {
            logger.warn("JWT parsing error: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT parsing error");
            return;
        }

        chain.doFilter(request, response);
        logger.info("Finished processing request: {} {}", request.getMethod(), request.getRequestURI());
    }

    private Boolean validateToken(String token, UserDetails userDetails, UUID userAccountId) {
        Optional<UserAccount> optionalUserAccount = userAccountService.findById(userAccountId);
        if (optionalUserAccount.isPresent()) {
            String username = optionalUserAccount.get().getUsername();
            logger.info("Validating token - UserDetails username: {}, UserAccount username: {}",
                    userDetails.getUsername(), username);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }
        logger.warn("UserAccount not found for ID: {}", userAccountId);
        return false;
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Date expiration = claims.getExpiration();
        logger.info("Token expiration: {}", expiration);
        return expiration.before(new Date());
    }

}