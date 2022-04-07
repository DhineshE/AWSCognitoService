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
public class SignUpDto {

    private String name;
    private String email;
    private String password;
	public String getName() {
		return name;
	}
}
