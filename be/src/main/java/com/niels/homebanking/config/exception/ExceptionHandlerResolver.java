package com.niels.homebanking.config.exception;

import com.niels.homebanking.dto.error.ErrorMessageDto;
import com.niels.homebanking.entity.ErrorTracking;
import com.niels.homebanking.repository.ErrorTrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.time.format.DateTimeParseException;

@Component
@ControllerAdvice
public class ExceptionHandlerResolver {

    @Autowired
    private ErrorTrackingRepository errorTrackingRepository;

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerResolver.class);

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorMessageDto> handleAll(ValidationException ex) {
        logger.error(ex.getMessage(), ex);
        errorTrackingRepository.saveAndFlush(new ErrorTracking(ex.getMessage(), ex.getStatus()));
        return new ResponseEntity<ErrorMessageDto>(new ErrorMessageDto(ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorMessageDto> handleAll(BaseException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<ErrorMessageDto>(new ErrorMessageDto(ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<ErrorMessageDto> handleAll(EmptyResultDataAccessException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<ErrorMessageDto>(new ErrorMessageDto("The requested resource has NOT been found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<ErrorMessageDto> handleAll(DateTimeParseException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<ErrorMessageDto>(new ErrorMessageDto("The inserted date - " + ex.getParsedString() + " - is not valid"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpServerErrorException.class})
    public ResponseEntity<ErrorMessageDto> handleAll(HttpServerErrorException ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<ErrorMessageDto>(new ErrorMessageDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessageDto> handleAll(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<ErrorMessageDto>(new ErrorMessageDto(ex.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
