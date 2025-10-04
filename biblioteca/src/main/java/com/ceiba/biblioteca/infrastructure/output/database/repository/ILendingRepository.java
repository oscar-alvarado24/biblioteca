package com.ceiba.biblioteca.infrastructure.output.database.repository;

import com.ceiba.biblioteca.infrastructure.output.database.entity.LendingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ILendingRepository extends CrudRepository<LendingEntity, Integer> {
    Long countByUserIdAndMaxReturnDateGreaterThanEqual (String userId, LocalDate date);
}
