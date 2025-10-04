package com.ceiba.biblioteca.application.mapper;

import com.ceiba.biblioteca.application.dto.LendingBasicResponse;
import com.ceiba.biblioteca.application.dto.LendingCompleteResponse;
import com.ceiba.biblioteca.application.dto.LendingRequest;
import com.ceiba.biblioteca.domain.model.Lending;
import com.ceiba.biblioteca.domain.model.UserType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILendingMapper {
    default Lending toLending(LendingRequest lendingRequest){
        return new Lending(lendingRequest.getIsbn(), lendingRequest.getIdentificacionUsuario(), UserType.fromNumberUserType(lendingRequest.getTipoUsuario()));
    }
    @Mapping(source = "maxReturnDate", target = "fechaMaximaDevolucion", dateFormat = "dd/MM/yyyy")
    LendingBasicResponse toLendingBasicResponse(Lending lending);

    default LendingCompleteResponse toLendingCompleteResponse(Lending  lending){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new LendingCompleteResponse(lending.getId(), lending.getMaxReturnDate().format(format), lending.getIsbn(), lending.getUserId(), lending.getUserType().getNumberUserType());
    }
}
