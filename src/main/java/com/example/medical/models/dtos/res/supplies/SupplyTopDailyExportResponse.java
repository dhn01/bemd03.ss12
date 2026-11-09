package com.example.medical.models.dtos.res.supplies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyTopDailyExportResponse {
    private Long supplyId;
    private String supplyName;
    private Long totalExportQuantity;
}
