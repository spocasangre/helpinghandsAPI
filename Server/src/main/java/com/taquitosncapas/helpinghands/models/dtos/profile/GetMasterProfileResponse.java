package com.taquitosncapas.helpinghands.models.dtos.profile;

import com.taquitosncapas.helpinghands.models.entities.Master;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMasterProfileResponse {
    @NotBlank
    private String message;
    @NotBlank
    private Optional<Master> user;
}
