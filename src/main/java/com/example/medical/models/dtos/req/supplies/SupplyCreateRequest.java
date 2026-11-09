package com.example.medical.models.dtos.req.supplies;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplyCreateRequest {
    @NotBlank(message = "Tên vật tư không được để trống")
    private String name;

    @NotBlank(message = "Tên hãng cung cấp không được để trống")
    private String provider;

    @NotBlank(message = "Quy cách không được để trống")
    private String specification;

    @NotBlank(message = "Đơn vị không được để trống")
    private String unitOfMeasurement;
}
