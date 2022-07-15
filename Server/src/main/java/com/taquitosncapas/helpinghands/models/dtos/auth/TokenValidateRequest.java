package com.taquitosncapas.helpinghands.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidateRequest {
    @NotBlank
    private String validateToken;
    @NotBlank
    private String email;
}
