package com.taquitosncapas.helpinghands.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ManagerRegisterRequest {
    private String name;
    private String lastname;
    private String telephone_number;
    private String gender;
    private String birth_date;
    private String email;
    private String pass;
    private Long role = 3L;
}