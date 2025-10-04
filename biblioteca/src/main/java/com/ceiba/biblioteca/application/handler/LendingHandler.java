package com.ceiba.biblioteca.application.handler;

import com.ceiba.biblioteca.application.dto.LendingBasicResponse;
import com.ceiba.biblioteca.application.dto.LendingRequest;
import com.ceiba.biblioteca.domain.exception.BadUserTypeException;
import com.ceiba.biblioteca.application.mapper.ILendingMapper;
import com.ceiba.biblioteca.domain.api.ILendingServicePort;
import com.ceiba.biblioteca.domain.model.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LendingHandler implements ILendingHandler{

    private final ILendingServicePort lendingServicePort;
    private final ILendingMapper lendingMapper;

    @Override
    public LendingBasicResponse saveLending(LendingRequest lendingRequest) {
        return lendingMapper.toLendingBasicResponse(lendingServicePort.saveLending(lendingMapper.toLending(lendingRequest)));
    }
}
