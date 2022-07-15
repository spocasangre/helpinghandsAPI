package com.taquitosncapas.helpinghands.models.dtos.profile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditManagerRequest {
    private String name;
    private String lastname;
    private String telephone_number;
    private String gender;
    private String birth_date;
}
