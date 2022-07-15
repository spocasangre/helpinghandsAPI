package com.taquitosncapas.helpinghands.models.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ModifyProjectRequest {
    private Long projectId;
    private String title;
    private String description;
    private String place;
    private Date date;
    private String duration;
    private Long category;
}
