package com.taquitosncapas.helpinghands.models.dtos.project;

import com.taquitosncapas.helpinghands.models.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectInfoResponse {
    private String message;
    private Optional<Project> project;
    private String photo ;
}
