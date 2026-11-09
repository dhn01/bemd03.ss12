package com.example.medical.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "supplies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "provider")
    private String provider;

    @Column(name = "specification")
    private String specification;

    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_delete")
    private Boolean isDelete = false;
}
