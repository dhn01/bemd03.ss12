package com.example.medical.controllers;

import com.example.medical.models.dtos.req.supplies.SupplyCreateRequest;
import com.example.medical.models.dtos.req.supplies.SupplyExportRequest;
import com.example.medical.models.dtos.req.supplies.SupplyImportRequest;
import com.example.medical.models.dtos.req.supplies.SupplyUpdateRequest;
import com.example.medical.models.services.SupplyService;
import com.example.medical.models.services.SupplyTransactionService;
import com.example.medical.utils.wrappers.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/supplies")
public class SupplyController {
    private final SupplyService supplyService;
    private final SupplyTransactionService supplyTransactionService;

    @GetMapping("")
    public ResponseEntity<?> getAllActiveSupplies() {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "List of active supplies",
                        supplyService.getActiveSupplies()
                )
        );
    }

    @PostMapping("")
    public ResponseEntity<?> createSupply(@Validated @RequestBody SupplyCreateRequest supplyCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.response(
                                HttpStatus.CREATED.name(),
                                "Supply create successfully",
                                supplyService.createSupply(supplyCreateRequest)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupply(@Validated @RequestBody SupplyUpdateRequest updateRequest, @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "Update supply successfully",
                        supplyService.updateSupply(updateRequest, id)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteSupply(@PathVariable Long id) {
        supplyService.softDeleteSupply(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSuppliesByKey(@RequestParam(name = "name", defaultValue = "") String searchKey) {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "List Supplies",
                        supplyService.getSuppliesByKey(searchKey)
                )
        );
    }

    @PatchMapping("/{id}/import")
    public ResponseEntity<?> patch(@Validated @RequestBody SupplyImportRequest supplyImportRequest, @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "Import supply successfully",
                        supplyService.importSupplies(id, supplyImportRequest)
                )
        );
    }

    @PatchMapping("/{id}/export")
    public ResponseEntity<?> patch(@Validated @RequestBody SupplyExportRequest supplyExportRequest, @PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "Export supply successfully",
                        supplyService.exportSupplies(id, supplyExportRequest)
                )
        );
    }

    @GetMapping("/statistics/daily-export")
    public ResponseEntity<?> getSuppliesExportDailyReport() {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "Supplies Export Daily Report",
                        supplyTransactionService.statisticsSupplyDailyExport()
                )
        );
    }

    @GetMapping("/statistics/top-export")
    public ResponseEntity<?> getSuppliesTopExport() {
        return ResponseEntity.ok(
                ApiResponse.response(
                        HttpStatus.OK.name(),
                        "Supplies Top Export",
                        supplyTransactionService.findTopSupplyExport()
                )
        );
    }
}
