package com.niels.homebanking.util;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.service.UserAccountService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static com.niels.homebanking.util.Constant.ERR_0003;
import static com.niels.homebanking.util.Constant.ERR_0012;

@Component
public class JwtToken {

    @Value("${app.jwt.access}")
    private long EXPIRE_DURATION_ACCESS;
    @Value("${app.jwt.refresh}")
    private long EXPIRE_DURATION_REFRESH;
    @Value("${app.jwt.otp}")
    private long EXPIRE_DURATION_OTP;
    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private JwtParser jwtParser;

    public String generateAccessToken(UserAccount user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer("HomeBanking")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION_ACCESS))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public String generateRefreshToken(UserAccount user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer("HomeBanking-REFRESH")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION_REFRESH))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public String generateOtpToken(UserAccount user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer("HomeBanking-OTP")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION_OTP))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public String getUsernameFromToken(String token) throws BaseException {
        return userAccountService.findById(getUserIdFromToken(token)).orElseThrow(() -> new BaseException(ERR_0012, HttpStatus.NOT_FOUND)).getUsername();
    }

    public UUID getUserIdFromToken(String token) {
        return UUID.fromString(jwtParser.parseClaimsJws(token).getBody().getSubject());
    }

    public Boolean validateMagicLinkToken(String token) throws ExpiredJwtException {
        boolean magikLinkIsValid = jwtParser.parseClaimsJws(token).getBody()
                .getIssuer().contains("OTP");
        boolean userIsPresent = userAccountService.findById(
                getUserIdFromToken(token)).isPresent();
        boolean expired = jwtParser.parseClaimsJws(token).getBody()
                .getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                .isBefore(ChronoLocalDate.from(ZonedDateTime.now()));
        return magikLinkIsValid && userIsPresent && !expired;
    }

}
