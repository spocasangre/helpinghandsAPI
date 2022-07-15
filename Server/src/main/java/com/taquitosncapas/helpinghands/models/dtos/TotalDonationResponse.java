package com.taquitosncapas.helpinghands.models.dtos;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalDonationResponse {
   private Integer total;
   private Integer total_value;
}
