package com.taquitosncapas.helpinghands.models.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditProjectRequest {
    private Long projectId;
}
