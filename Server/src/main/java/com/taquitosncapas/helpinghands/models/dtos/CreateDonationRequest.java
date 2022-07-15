package com.taquitosncapas.helpinghands.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDonationRequest {
   private Integer value;
   private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());
   private Long projectId;
}
