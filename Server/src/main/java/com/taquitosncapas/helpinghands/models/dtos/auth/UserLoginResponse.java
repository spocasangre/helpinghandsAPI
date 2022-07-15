package com.taquitosncapas.helpinghands.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private String message;
    private String token;
/*    private String refresh_token;
    private Instant expiresAt;*/
}
