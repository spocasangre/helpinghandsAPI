package com.taquitosncapas.helpinghands.models.dtos.auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
