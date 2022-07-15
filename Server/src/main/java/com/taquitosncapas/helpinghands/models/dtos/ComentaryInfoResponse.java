package com.taquitosncapas.helpinghands.models.dtos;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComentaryInfoResponse {
    private String content;
    private String author;
    private Timestamp create_at;
}
