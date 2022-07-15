package com.taquitosncapas.helpinghands.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateComentaryRequest {
    private Long id_project;
    private String content;
    private Timestamp create_at = Timestamp.valueOf(LocalDateTime.now());
}
