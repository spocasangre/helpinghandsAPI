package com.taquitosncapas.helpinghands.models.dtos.project;

import com.taquitosncapas.helpinghands.models.entities.Project;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HandlerPage {
    private Long total;
    private List<?> content;
}
