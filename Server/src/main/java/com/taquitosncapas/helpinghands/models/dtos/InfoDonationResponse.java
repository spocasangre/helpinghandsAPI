package com.taquitosncapas.helpinghands.models.dtos;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoDonationResponse {
   private String donator;
   private String project_name;
   private Integer value;
   private Timestamp createAt;

}
