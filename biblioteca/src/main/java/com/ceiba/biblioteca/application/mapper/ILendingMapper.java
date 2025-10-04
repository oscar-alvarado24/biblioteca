package com.ceiba.biblioteca.application.mapper;

import com.ceiba.biblioteca.application.dto.LendingBasicResponse;
import com.ceiba.biblioteca.application.dto.LendingRequest;
import com.ceiba.biblioteca.domain.model.Lending;
import com.ceiba.biblioteca.domain.model.UserType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILendingMapper {
    default Lending toLending(LendingRequest lendingRequest){
        return new Lending(lendingRequest.getIsbn(), lendingRequest.getIdentificacionUsuario(), UserType.fromNumberUserType(lendingRequest.getTipoUsuario()));
    }
    @Mapping(source = "maxReturnDate", target = "fechaMaximaDevolucion", dateFormat = "dd/MM/yyyy")
    LendingBasicResponse toLendingBasicResponse(Lending lending);
}
