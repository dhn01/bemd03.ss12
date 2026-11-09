package com.example.medical.models.repositories;

import com.example.medical.models.entities.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {
    List<Supply> findSuppliesByIsDeleteFalse(Boolean isDelete);

    @Query("""
            SELECT s
            FROM Supply s
            WHERE s.isDelete = false
                AND (:name IS NULL OR s.name ILIKE CONCAT('%', :name, '%'))
            """)
    List<Supply> findAllByNameLike(@Param("name") String name);
}
