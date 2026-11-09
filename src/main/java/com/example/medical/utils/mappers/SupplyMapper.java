package com.example.medical.utils.mappers;

import com.example.medical.models.dtos.req.supplies.SupplyCreateRequest;
import com.example.medical.models.dtos.req.supplies.SupplyUpdateRequest;
import com.example.medical.models.dtos.res.supplies.SupplyResponse;
import com.example.medical.models.entities.Supply;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface SupplyMapper {
    SupplyResponse toSupplyResponse(Supply supply);

    List<SupplyResponse> toSupplyResponseList(List<Supply> supplies);

    @Mapping(target = "quantity", constant = "0")
    @Mapping(target = "isDelete", constant = "false")
    Supply toSupply(SupplyCreateRequest supplyCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplyFromRequest(SupplyUpdateRequest supplyUpdateRequest, @MappingTarget Supply supply);
}
