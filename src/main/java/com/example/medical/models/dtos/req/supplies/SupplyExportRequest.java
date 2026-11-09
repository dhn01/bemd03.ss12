package com.example.medical.models.dtos.req.supplies;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyExportRequest {
    @NotNull(message = "Số lượng không được để trống")
    @Positive(message = "Số lượng phải là số nguyên dương")
    private Integer amount;
}
