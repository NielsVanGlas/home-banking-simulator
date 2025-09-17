package com.niels.homebanking.config.auth;

import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.service.UserAccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class RequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                Claims claims = jwtParser.parseClaimsJws(jwtToken).getBody();
                String subject = claims.getSubject();
                UUID userAccountId = UUID.fromString(subject);
                String userId = userAccountId.toString();
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userAccountService.loadUser(userId);
                    if (validateToken(jwtToken, userDetails, userAccountId)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
                chain.doFilter(request, response);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
            }
        }
    }

    private Boolean validateToken(String token, UserDetails userDetails, UUID userAccountId) throws ExpiredJwtException {
        Optional<UserAccount> optionalUserAccount = userAccountService.findById(userAccountId);
        if (optionalUserAccount.isPresent()) {
            String username = optionalUserAccount.get().getUsername();
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }
        return false;
    }

    private Boolean isTokenExpired(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Date subject = claims.getExpiration();
        LocalDate expiration = subject.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return expiration.isBefore(ChronoLocalDate.from(ZonedDateTime.now()));
    }

}