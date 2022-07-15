package com.taquitosncapas.helpinghands.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VolunteerRegisterRequest {
    private String name;
    private String lastname;
    private String telephone_number;
    private String gender;
    private String birth_date;
    private String college;
    private String career;
    private String email;
    private String pass;
    private Long role = 1L;
}