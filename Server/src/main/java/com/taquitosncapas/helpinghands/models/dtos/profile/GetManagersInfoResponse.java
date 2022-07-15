package com.taquitosncapas.helpinghands.models.dtos.profile;

import com.taquitosncapas.helpinghands.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetManagersInfoResponse {
    @NotBlank
    private String message;
    @NotBlank
    private List<User> managers;
}
