package com.example.medical.models.services;

import com.example.medical.models.dtos.res.supplies.SupplyTopDailyExportResponse;
import com.example.medical.models.dtos.res.supplies.SupplyExportResponse;

import java.util.List;

public interface SupplyTransactionService {
    List<SupplyTopDailyExportResponse> statisticsSupplyDailyExport();

    SupplyExportResponse findTopSupplyExport();
}
