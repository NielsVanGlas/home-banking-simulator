package com.niels.homebanking.controller;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.authentication.*;
import com.niels.homebanking.dto.error.ErrorMessageDto;
import com.niels.homebanking.entity.UserAccount;
import com.niels.homebanking.service.UserAccountService;
import com.niels.homebanking.util.JwtToken;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAccountService userAccountService;

    @Operation(description = "Authenticate a User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Authentication completed", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationException.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            })
    })
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponseDto> createAuthenticationToken(
            @RequestBody AuthenticationRequestDto authenticationRequest
    ) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            return new ResponseEntity<>(new AuthenticationResponseDto("USER_DISABLED"), HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AuthenticationResponseDto("INVALID_CREDENTIALS"), HttpStatus.UNAUTHORIZED);
        }
        final UserAccount userAccount = (UserAccount) userAccountService.loadUser(authenticationRequest.getUsername());
        String accessToken = jwtToken.generateAccessToken(userAccount);
        String refreshToken = jwtToken.generateRefreshToken(userAccount);
        return new ResponseEntity<>(new AuthenticationResponseDto(accessToken, refreshToken, userAccount.getFullName()), HttpStatus.OK);
    }

    @Operation(description = "Enable an User if the Magic Link is valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification completed", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationException.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            })
    })
    @PostMapping(value = "/verification")
    public ResponseEntity<?> verifyRegistrationOtp(
            @RequestParam String token
    ) throws Exception {
        try {
            if (jwtToken.validateMagicLinkToken(token)) {
                Optional<UserAccount> optionalUserAccount = userAccountService.findById(jwtToken.getUserIdFromToken(token));
                if (optionalUserAccount.isPresent()) {
                    UserAccount userAccount = optionalUserAccount.get();
                    if (!userAccount.isEnabled()) {
                        userAccountService.enableAccount(userAccount);
                    }
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(new ErrorMessageDto("User does not exist"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(new ErrorMessageDto("The Magic Link is invalid"), HttpStatus.UNAUTHORIZED);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(new ErrorMessageDto("The Magic Link has Expired"), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(description = "Resend verification magic link if the older is expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification completed", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationException.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            })
    })
    @PostMapping(value = "/resend-verification")
    public ResponseEntity<?> resendVerification(
            @RequestParam String token
    ) throws Exception {
        try {
            jwtToken.validateMagicLinkToken(token);
        } catch (ExpiredJwtException e) {
            Optional<UserAccount> optionalUserAccount = userAccountService.findById(UUID.fromString(e.getClaims().getSubject()));
            if (optionalUserAccount.isPresent()) {
                UserAccount userAccount = optionalUserAccount.get();
                if (userAccount.isEnabled()) {
                    String otpToken = jwtToken.generateOtpToken(userAccount);
                    return new ResponseEntity<>(new OtpResponseDto(otpToken), HttpStatus.OK);
                }
                return new ResponseEntity<>(new ErrorMessageDto("User is not enabled"), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ErrorMessageDto("User not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ErrorMessageDto("Token is valid"), HttpStatus.FORBIDDEN);
    }

    @Operation(description = "Refresh Token for the User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Jwt Token generated correctly", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationException.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            })
    })
    @PostMapping(value = "/refresh")
    public ResponseEntity<RefreshResponseDto> refreshToken(
            @RequestBody RefreshRequestDto refreshRequestDto
    ) throws Exception {
        final UserAccount userAccount = (UserAccount) userAccountService.loadUser(jwtToken.getUsernameFromToken(refreshRequestDto.getRefreshToken()));
        final String accessToken = jwtToken.generateAccessToken(userAccount);
        return new ResponseEntity<>(new RefreshResponseDto(accessToken), HttpStatus.OK);
    }

}
