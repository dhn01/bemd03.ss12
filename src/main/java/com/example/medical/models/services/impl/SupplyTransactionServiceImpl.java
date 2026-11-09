package com.example.medical.models.services.impl;

import com.example.medical.exceptions.ResourceNotFoundException;
import com.example.medical.models.dtos.res.supplies.SupplyTopDailyExportResponse;
import com.example.medical.models.dtos.res.supplies.SupplyExportResponse;
import com.example.medical.models.repositories.SupplyTransactionRepository;
import com.example.medical.models.services.SupplyTransactionService;
import com.example.medical.utils.mappers.SupplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SupplyTransactionServiceImpl implements SupplyTransactionService {
    private final SupplyTransactionRepository supplyTransactionRepository;
    private final SupplyMapper supplyMapper;

    @Override
    public List<SupplyTopDailyExportResponse> statisticsSupplyDailyExport() {
        LocalDate now = LocalDate.now();
        return supplyTransactionRepository.findTotalExportInTime(
                now.atStartOfDay(),
                now.plusDays(1).atStartOfDay()
        );
    }

    @Override
    public SupplyExportResponse findTopSupplyExport() {
        List<SupplyExportResponse> supplies = supplyTransactionRepository.findTopExportAllTime(PageRequest.of(0, 1));

        if (supplies == null || supplies.isEmpty()) {
            throw new ResourceNotFoundException("Chưa có dữ liệu giao dịch để thống kê");
        }

        return supplies.getFirst();
    }
}
