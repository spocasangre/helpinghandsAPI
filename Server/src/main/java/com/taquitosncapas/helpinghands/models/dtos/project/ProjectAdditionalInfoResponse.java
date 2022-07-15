package com.taquitosncapas.helpinghands.models.dtos.project;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectAdditionalInfoResponse {
    private Long orgId;
    private String orgName;
    private String categoryName;
    private String photo;
}
