package com.cognito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author 921452
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeConfirmDto {

    private String email;
    private String confirmationCode;

}
