package com.example.medical.models.services;

import com.example.medical.models.dtos.req.supplies.SupplyCreateRequest;
import com.example.medical.models.dtos.req.supplies.SupplyExportRequest;
import com.example.medical.models.dtos.req.supplies.SupplyImportRequest;
import com.example.medical.models.dtos.req.supplies.SupplyUpdateRequest;
import com.example.medical.models.dtos.res.supplies.SupplyResponse;
import com.example.medical.models.entities.Supply;

import java.util.List;

public interface SupplyService {
    List<SupplyResponse> getActiveSupplies();

    SupplyResponse createSupply(SupplyCreateRequest supplyCreateRequest);

    SupplyResponse updateSupply(SupplyUpdateRequest supplyUpdateRequest, Long id);

    void softDeleteSupply(Long supplyId);

    Supply findActiveSupplyById(Long supplyId);

    List<SupplyResponse> getSuppliesByKey(String searchKey);

    SupplyResponse importSupplies(Long supplyId, SupplyImportRequest supplyImportRequest);

    SupplyResponse exportSupplies(Long supplyId, SupplyExportRequest supplyExportRequest);
}
