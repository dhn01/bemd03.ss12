package com.example.medical.models.repositories;

import com.example.medical.models.dtos.res.supplies.SupplyTopDailyExportResponse;
import com.example.medical.models.dtos.res.supplies.SupplyExportResponse;
import com.example.medical.models.entities.SupplyTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SupplyTransactionRepository extends JpaRepository<SupplyTransaction, Long> {

    @Query("""
            SELECT new com.example.medical.models.dtos.res.supplies.SupplyTopDailyExportResponse (
                st.supply.id,
                st.supply.name,
                sum(st.amount)
            )
            FROM SupplyTransaction st
            WHERE st.type = 'EXPORT'
                AND st.dateOfTransaction >= :startDate
                AND st.dateOfTransaction <= :endDate
            GROUP BY st.supply.id, st.supply.name
            """)
    List<SupplyTopDailyExportResponse> findTotalExportInTime(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
            SELECT new com.example.medical.models.dtos.res.supplies.SupplyExportResponse (
                st.supply.id,
                st.supply.name,
                sum(st.amount)
            )
            FROM SupplyTransaction st
            WHERE st.type = 'EXPORT'
            GROUP BY st.supply.id, st.supply.name
            ORDER BY sum(st.amount) DESC
            """)
    List<SupplyExportResponse> findTopExportAllTime(Pageable pageable);
}
