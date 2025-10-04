package com.ceiba.biblioteca.infrastructure.configuration;

import com.ceiba.biblioteca.domain.api.ILendingServicePort;
import com.ceiba.biblioteca.domain.spi.ILendingPersistencePort;
import com.ceiba.biblioteca.domain.usecase.LendingUseCase;
import com.ceiba.biblioteca.infrastructure.output.database.adapter.LendingAdapter;
import com.ceiba.biblioteca.infrastructure.output.database.mapper.ILendingEntityMapper;
import com.ceiba.biblioteca.infrastructure.output.database.repository.ILendingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @Bean
    public ILendingPersistencePort lendingPersistencePort (ILendingRepository lendingRepository, ILendingEntityMapper lendingEntityMapper){
        return new LendingAdapter(lendingRepository, lendingEntityMapper);
    }

    @Bean
    public ILendingServicePort lendingServicePort(ILendingRepository lendingRepository, ILendingEntityMapper lendingEntityMapper){
        return new LendingUseCase(lendingPersistencePort(lendingRepository, lendingEntityMapper));
    }
}
