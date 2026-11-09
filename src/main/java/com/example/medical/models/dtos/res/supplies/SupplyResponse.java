package com.example.medical.models.dtos.res.supplies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplyResponse {
    private String name;
    private String provider;
    private String specification;
    private String unitOfMeasurement;
    private Integer quantity;
}
