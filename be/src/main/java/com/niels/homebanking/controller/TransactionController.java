package com.niels.homebanking.controller;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.pagination.PageShowTransactionDto;
import com.niels.homebanking.dto.transaction.CreateTransactionDto;
import com.niels.homebanking.dto.transaction.ShowTransactionDto;
import com.niels.homebanking.dto.transaction.UpdateTransactionDto;
import com.niels.homebanking.service.AuthenticationService;
import com.niels.homebanking.service.TransactionService;
import com.niels.homebanking.util.Common;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService bankAccountService;

    @Autowired
    private AuthenticationService authenticationService;

    //CRUD
    // Create
    @Operation(description = "Create a new Transaction")
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
            @RequestBody CreateTransactionDto createTransactionDto,
            Authentication authentication
    ) throws Exception {
        UUID id = bankAccountService.createTransaction(createTransactionDto, authenticationService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Read All
    @Operation(description = "Get all Transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PageShowTransactionDto.class))
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
    public ResponseEntity<PageShowTransactionDto> showRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort,
            Authentication authentication
    ) throws Exception {
        page = Common.setPage(page);
        Page<ShowTransactionDto> pages = bankAccountService.getTransactions(Common.getPagination(page, size, sort), authenticationService.getAuthenticatedUser(authentication));
        List<ShowTransactionDto> response = pages.getContent();
        return response.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new PageShowTransactionDto(response, pages.getNumber(), pages.getTotalElements(), pages.getTotalPages()), HttpStatus.OK);

    }

    // Update
    @Operation(description = "Update a Transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ShowTransactionDto.class))
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
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateRecord(
            @RequestBody UpdateTransactionDto updateTransactionDto,
            @PathVariable UUID id,
            Authentication authentication
    ) throws Exception {
        bankAccountService.updateTransaction(id, updateTransactionDto, authenticationService.getAuthenticatedUser(authentication));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
