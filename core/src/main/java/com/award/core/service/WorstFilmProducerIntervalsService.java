package com.award.core.service;

import com.award.core.dataprovider.repository.WorstFilmRepository;
import com.award.core.domain.ProducerIntervalsMinMax;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorstFilmProducerIntervalsService {

    private final WorstFilmRepository worstFilmRepository;

    @SneakyThrows
    public ProducerIntervalsMinMax getProducersIntervals() {
        // Paralelismo para garantir a máxima performance
        var minFuture = supplyAsync(worstFilmRepository::findProducerIntervalsMin);
        var maxFuture = supplyAsync(worstFilmRepository::findProducerIntervalsMax);

        return ProducerIntervalsMinMax.builder()
                .min(minFuture.get())
                .max(maxFuture.get())
                .build();
    }

}
