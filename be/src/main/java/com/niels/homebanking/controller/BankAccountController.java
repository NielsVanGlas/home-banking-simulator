package com.niels.homebanking.controller;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.bankAccount.CreateBankAccountDto;
import com.niels.homebanking.dto.bankAccount.ShowBankAccountDto;
import com.niels.homebanking.dto.bankAccount.UpdateBankAccountDto;
import com.niels.homebanking.service.AuthenticationService;
import com.niels.homebanking.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bank")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private AuthenticationService authenticationService;

    //CRUD
    // Create
    @Operation(description = "Create a new Bank Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UUID.class))
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
    @PostMapping()
    public ResponseEntity<UUID> createRecord(
            @RequestBody CreateBankAccountDto createBankAccountDto,
            Authentication authentication
    ) throws Exception {
        UUID id = bankAccountService.createBankAccount(createBankAccountDto, authenticationService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Read One
    @Operation(description = "Get a Bank Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ShowBankAccountDto.class))
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
    @GetMapping()
    public ResponseEntity<ShowBankAccountDto> showRecord(
            Authentication authentication
    ) throws Exception {
        ShowBankAccountDto record = bankAccountService.getBankAccount(authenticationService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    // Update
    @Operation(description = "Update a Bank Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UUID.class))
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
    @PutMapping()
    public ResponseEntity<UUID> updateRecord(
            @RequestBody UpdateBankAccountDto updateBankAccountDto,
            Authentication authentication
    )  throws Exception {
        UUID id = bankAccountService.updateBankAccount(authenticationService.getAuthenticatedUser(authentication), updateBankAccountDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // Delete
    @Operation(description = "Delete a Bank Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted", content = {
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
    @DeleteMapping()
    public ResponseEntity<?> deleteRecord(
            Authentication authentication
    ) throws Exception {
        bankAccountService.deleteBankAccount(authenticationService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
