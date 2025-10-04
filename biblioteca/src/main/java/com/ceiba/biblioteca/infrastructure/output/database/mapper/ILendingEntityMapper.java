package com.ceiba.biblioteca.infrastructure.output.database.mapper;

import com.ceiba.biblioteca.domain.model.Lending;
import com.ceiba.biblioteca.domain.model.UserType;
import com.ceiba.biblioteca.infrastructure.output.database.entity.LendingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILendingEntityMapper {
    default LendingEntity toLendingEntity(Lending lending){
        return LendingEntity.builder()
                .userType(lending.getUserType().name())
                .isbn(lending.getIsbn())
                .maxReturnDate(lending.getMaxReturnDate())
                .userId(lending.getUserId())
                .build();
    }

    default Lending toLending (LendingEntity lendingEntity){
        return new Lending(lendingEntity.getId(), lendingEntity.getMaxReturnDate(), lendingEntity.getIsbn(), lendingEntity.getUserId(), UserType.valueOf(lendingEntity.getUserType()));
    }

}
