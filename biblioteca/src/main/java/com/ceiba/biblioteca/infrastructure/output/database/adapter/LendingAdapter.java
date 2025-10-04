package com.ceiba.biblioteca.infrastructure.output.database.adapter;

import com.ceiba.biblioteca.domain.model.Lending;
import com.ceiba.biblioteca.domain.spi.ILendingPersistencePort;
import com.ceiba.biblioteca.infrastructure.output.database.entity.LendingEntity;
import com.ceiba.biblioteca.infrastructure.output.database.mapper.ILendingEntityMapper;
import com.ceiba.biblioteca.infrastructure.output.database.repository.ILendingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class LendingAdapter implements ILendingPersistencePort {
    private final ILendingRepository lendingRepository;
    private final ILendingEntityMapper lendingEntityMapper;
    @Override
    public boolean isUserWithBookLending(String userId) {
        return lendingRepository.countByUserIdAndMaxReturnDateGreaterThanEqual(userId, LocalDate.now()) > 0L;
    }


    @Override
    public Lending saveLending(Lending lending) {
        LendingEntity lendingEntity = lendingRepository.save(lendingEntityMapper.toLendingEntity(lending));
        Lending lendingSaved = lendingEntityMapper.toLending(lendingEntity);
        return lendingSaved;
    }
}
