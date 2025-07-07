package com.award.core.service;

import com.award.core.dataprovider.repository.WorstFilmRepository;
import com.award.core.domain.ProducerIntervalsMinMax;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorstFilmProducerIntervalsService {

    private final WorstFilmRepository worstFilmRepository;

    @Named("producerIntervalsExecutor")
    private final Executor producerIntervalsExecutor;

    @SneakyThrows
    public ProducerIntervalsMinMax getProducersIntervals() {
        // Paralelismo para garantir a máxima performance
        var minFuture = supplyAsync(worstFilmRepository::findProducerIntervalsMin, producerIntervalsExecutor);
        var maxFuture = supplyAsync(worstFilmRepository::findProducerIntervalsMax, producerIntervalsExecutor);

        return ProducerIntervalsMinMax.builder()
                .min(minFuture.get())
                .max(maxFuture.get())
                .build();
    }

}
