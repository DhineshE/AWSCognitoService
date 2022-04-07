package com.cognito.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.cognito.constant.CognitoConstant;
import com.cognito.dto.LoginResponseDto;
import com.cognito.exception.C2CCognitoServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 921452
 *
 */
@Service public class CognitoService {

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    @Value("${aws.cognito.client-id}")
    private String clientId;

    @Value("${aws.cognito.userpool-id}")
    private String userPoolId;

    public SignUpResult signUp(String name, String email, String password)
                    throws C2CCognitoServiceException {

        try {
            SignUpRequest request = new SignUpRequest().withClientId(clientId)
                            .withUsername(email).withPassword(password)
                            .withUserAttributes(
                                            new AttributeType().withName("name")
                                                            .withValue(name)
                                                            .withName("email")
                                                            .withValue(email));
            SignUpResult result = cognitoClient.signUp(request);
            return result;
        } catch (InvalidPasswordException exception) {
            throw new C2CCognitoServiceException(
                            CognitoConstant.PASSWORD_NOT_VALID,
                            exception.getMessage(), null);
        }

    }

    public ConfirmSignUpResult confirmSignUp(String email,
                    String confirmationCode) throws C2CCognitoServiceException {

       try{
            ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest().withClientId(
                                            clientId).withUsername(email)
                            .withConfirmationCode(confirmationCode);
            return cognitoClient.confirmSignUp(confirmSignUpRequest);
       } catch(CodeMismatchException codeMismatchException){
           throw new C2CCognitoServiceException(
                           CognitoConstant.INVALID_CONFIRMATION_CODE,
                           codeMismatchException.getMessage(), null);
       }
    }

    public LoginResponseDto login(String email, String password)
                    throws C2CCognitoServiceException {
        Map<String, String> authParams = new LinkedHashMap<String, String>() {{
            put("USERNAME", email);
            put("PASSWORD", password);
        }};

        LoginResponseDto loginResponse = new LoginResponseDto();
        try {
            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest().withAuthFlow(
                                            AuthFlowType.ADMIN_NO_SRP_AUTH).withUserPoolId(userPoolId)
                            .withClientId(clientId)
                            .withAuthParameters(authParams);

            AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(
                            authRequest);
            AuthenticationResultType resultType = authResult.getAuthenticationResult();
            System.out.println(resultType.getAccessToken());
            loginResponse.setIdToken(resultType.getIdToken());
            loginResponse.setAccessToken(resultType.getAccessToken());
            loginResponse.setRefreshToken(resultType.getRefreshToken());
            loginResponse.setMessage("Successfully login");

        } catch (NotAuthorizedException notAuthorizedException) {
            throw new C2CCognitoServiceException(
                            CognitoConstant.PASSWORD_NOT_VALID,
                            notAuthorizedException.getMessage(), null);
        }
        return loginResponse;
    }

    //Not needed....... Can be removed.
    public String createPool(String userPoolName) {
        try {
            CreateUserPoolResult response = cognitoClient.createUserPool(
                            new CreateUserPoolRequest().withPoolName(
                                            userPoolName));
            return response.getUserPool().getId();
        } catch (AWSCognitoIdentityProviderException awsCognitoIdentityProviderException) {
            awsCognitoIdentityProviderException.printStackTrace();
        }
        return "";
    }
}
