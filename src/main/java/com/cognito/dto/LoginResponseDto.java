package com.cognito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 921452
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String idToken;
    private String accessToken;
    private String refreshToken;
    private int expiresIn;

}
