package com.example.medical.models.services.impl;

import com.example.medical.constants.SupplyTransactionType;
import com.example.medical.exceptions.ResourceNotFoundException;
import com.example.medical.models.dtos.req.supplies.SupplyCreateRequest;
import com.example.medical.models.dtos.req.supplies.SupplyExportRequest;
import com.example.medical.models.dtos.req.supplies.SupplyImportRequest;
import com.example.medical.models.dtos.req.supplies.SupplyUpdateRequest;
import com.example.medical.models.dtos.res.supplies.SupplyResponse;
import com.example.medical.models.entities.Supply;
import com.example.medical.models.entities.SupplyTransaction;
import com.example.medical.models.repositories.SupplyRepository;
import com.example.medical.models.repositories.SupplyTransactionRepository;
import com.example.medical.models.services.SupplyService;
import com.example.medical.utils.mappers.SupplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;
    private final SupplyTransactionRepository supplyTransactionRepository;
    private final SupplyMapper supplyMapper;
    private final Logger historyLogger = LoggerFactory.getLogger("INVENTORY_HISTORY");

    @Override
    public List<SupplyResponse> getActiveSupplies() {
        List<Supply> supplies = supplyRepository.findSuppliesByIsDeleteFalse(false);
        log.debug("Số lượng bản ghi: {}", supplies.size());
        return supplyMapper.toSupplyResponseList(supplies);
    }

    @Override
    public SupplyResponse createSupply(SupplyCreateRequest supplyCreateRequest) {
        Supply supply = supplyMapper.toSupply(supplyCreateRequest);
        supply = supplyRepository.save(supply);
        log.info("Đã tạo mới vật tư: {} với ID: {}", supply.getName(), supply.getId());
        return supplyMapper.toSupplyResponse(supply);
    }

    @Override
    public SupplyResponse updateSupply(SupplyUpdateRequest supplyUpdateRequest, Long id) {
        Supply supply = findActiveSupplyById(id);
        supplyMapper.updateSupplyFromRequest(supplyUpdateRequest, supply);
        supply = supplyRepository.save(supply);
        return supplyMapper.toSupplyResponse(supply);
    }

    @Override
    public void softDeleteSupply(Long supplyId) {
        Supply supply = findActiveSupplyById(supplyId);
        supply.setIsDelete(true);
        supplyRepository.save(supply);
    }

    @Override
    public Supply findActiveSupplyById(Long supplyId) {
        Supply supply = supplyRepository.findById(supplyId)
                .orElseThrow(() -> new ResourceNotFoundException("Supply not found"));
        if (supply.getIsDelete()) {
            throw new ResourceNotFoundException("Supply is already deleted!");
        }
        return supply;
    }

    @Override
    public List<SupplyResponse> getSuppliesByKey(String searchKey) {
        List<Supply> supplyList = supplyRepository.findAllByNameLike(searchKey);
        if (supplyList.isEmpty()) {
            log.info("Không tìm thấy vật tư nào khớp với từ khóa: {}", searchKey);
        }
        return supplyMapper.toSupplyResponseList(supplyList);
    }

    @Override
    public SupplyResponse importSupplies(Long supplyId, SupplyImportRequest supplyImportRequest) {
        Supply supply = findActiveSupplyById(supplyId);
        int oldQuantity = supply.getQuantity();
        supply.setQuantity(oldQuantity + supplyImportRequest.getAmount());
        supplyRepository.save(supply);
        SupplyTransaction supplyTransaction = SupplyTransaction.builder()
                .type(SupplyTransactionType.IMPORT)
                .dateOfTransaction(LocalDateTime.now())
                .amount(supplyImportRequest.getAmount())
                .supply(supply)
                .build();
        supplyTransactionRepository.save(supplyTransaction);
        historyLogger.info("Nhập kho ID {}, số lượng +{}, tồn cũ {}", supplyId, supplyImportRequest.getAmount(), oldQuantity);

        return supplyMapper.toSupplyResponse(supply);
    }

    @Override
    public SupplyResponse exportSupplies(Long supplyId, SupplyExportRequest supplyExportRequest) {
        Supply supply = findActiveSupplyById(supplyId);
        if (supply.getQuantity() < supplyExportRequest.getAmount()) {
            log.error("Thất bại khi xuất kho ID {}: Yêu cầu {}, hiện có {}", supplyId, supplyExportRequest.getAmount(), supply.getQuantity());
            throw new ResourceNotFoundException("Supply quantity is less than amount!");
        }
        supply.setQuantity(supply.getQuantity() - supplyExportRequest.getAmount());
        supplyRepository.save(supply);
        SupplyTransaction supplyTransaction = SupplyTransaction.builder()
                .type(SupplyTransactionType.EXPORT)
                .dateOfTransaction(LocalDateTime.now())
                .amount(supplyExportRequest.getAmount())
                .supply(supply)
                .build();
        supplyTransactionRepository.save(supplyTransaction);

        return supplyMapper.toSupplyResponse(supply);
    }
}
