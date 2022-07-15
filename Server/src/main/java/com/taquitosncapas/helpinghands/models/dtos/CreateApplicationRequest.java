package com.taquitosncapas.helpinghands.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateApplicationRequest {
    private String title;
    private String description;
    private String place;
    private Date date;
    private String duration;
    private Integer active = 0;
    private Integer isPending = 1;
    private Integer isFinished = 0;
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());
}
