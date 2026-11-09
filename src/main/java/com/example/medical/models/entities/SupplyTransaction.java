package com.example.medical.models.entities;

import com.example.medical.constants.SupplyTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "supply_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SupplyTransactionType type;

    @Column(name = "date_of_transaction")
    private LocalDateTime dateOfTransaction;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne()
    @JoinColumn(name = "supply_id")
    private Supply supply;
}
