package com.niels.homebanking.controller;

import com.niels.homebanking.config.exception.BaseException;
import com.niels.homebanking.config.exception.ValidationException;
import com.niels.homebanking.dto.error.ShowErrorTracking;
import com.niels.homebanking.dto.pagination.PageShowErrorTrackingDto;
import com.niels.homebanking.service.AuthenticationService;
import com.niels.homebanking.service.ErrorTrackingService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/errors")
public class ErrorTrackingController {

    @Autowired
    private ErrorTrackingService errorService;

    @Autowired
    private AuthenticationService authenticationService;

    // Read All
    @Operation(description = "Get all Errors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PageShowErrorTrackingDto.class))
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
    public ResponseEntity<PageShowErrorTrackingDto> showRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) throws Exception {
        page = Common.setPage(page);
        Page<ShowErrorTracking> pages = errorService.getErrors(Common.getPagination(page, size, sort));
        List<ShowErrorTracking> response = pages.getContent();
        return response.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new PageShowErrorTrackingDto(response, pages.getNumber(), pages.getTotalElements(), pages.getTotalPages()), HttpStatus.OK);

    }

}
