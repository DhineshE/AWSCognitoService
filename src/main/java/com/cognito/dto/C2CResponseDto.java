package com.cognito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class C2CResponseDto {

    private String responseCode;
    private String responseMessage;
    private LoginResponseDto responseData;
}
