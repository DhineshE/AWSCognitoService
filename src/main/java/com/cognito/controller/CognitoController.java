package com.cognito.controller;

import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.cognito.constant.CognitoConstant;
import com.cognito.dto.*;
import com.cognito.exception.C2CCognitoServiceException;
import com.cognito.service.CognitoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 921452
 *
 */
@RestControllerAdvice
@RestController
public class CognitoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(
                    CognitoController.class);

    @Autowired
    private CognitoService cognitoService;

    @PostMapping(value = "/signup")
    public ResponseEntity<SignUpResult> signUp(
                    @RequestBody SignUpDto signUpDto) {
        try {
            SignUpResult result = cognitoService.signUp(signUpDto.getName(),
                            signUpDto.getEmail(), signUpDto.getPassword());
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (C2CCognitoServiceException exception) {
            LOGGER.error(CognitoConstant.PASSWORD_NOT_VALID, exception);
        }
        return new ResponseEntity(
                        "Password does not compliance with Standard Requirements",
                        HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/confirmEmail")
    public ResponseEntity<String> confirmSignIn(
                    @RequestBody CodeConfirmDto codeConfirmDto) {
        try {
            cognitoService.confirmSignUp(codeConfirmDto.getEmail(),
                            codeConfirmDto.getConfirmationCode());
            return new ResponseEntity("User is Succesfully Confirmed",
                            HttpStatus.ACCEPTED);
        } catch (C2CCognitoServiceException exception) {
            LOGGER.error(CognitoConstant.INVALID_CONFIRMATION_CODE, exception);
        }
        return new ResponseEntity("Invalid Confirmation Code",
                        HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<LoginResponseDto> login(
                    @RequestBody LoginRequestDto loginRequest) {
        try {
            LoginResponseDto loginResponse = cognitoService.login(
                            loginRequest.getUsername(), loginRequest.getPassword());
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        } catch (C2CCognitoServiceException exception) {
            LOGGER.error(CognitoConstant.INVALID_CREDENTIALS, exception);
        }
        return new ResponseEntity("Username/Password is not Valid",
                        HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<LoginResponseDto> refresh(
                    @RequestBody RefreshRequestDto refreshRequestDto) {
        try {
            LoginResponseDto loginResponse = cognitoService.getRefreshAccess(
                            refreshRequestDto);
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        } catch (C2CCognitoServiceException exception) {
            LOGGER.error(CognitoConstant.INVALID_CREDENTIALS, exception);
        }
        return new ResponseEntity("Username/Password is not Valid",
                        HttpStatus.UNAUTHORIZED);
    }

}
