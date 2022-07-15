package com.taquitosncapas.helpinghands.models.dtos.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditOrgRequest {
    private String name_org;
    private String name;
    private String lastname;
    private String birth_date;
    private String gender;
    private String register_number;
    private String telephone_number;
    private String address;
    private String purpose;
    private String website;
}
