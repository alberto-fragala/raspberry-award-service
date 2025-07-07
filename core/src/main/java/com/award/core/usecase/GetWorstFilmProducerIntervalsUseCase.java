package com.award.core.usecase;

import com.award.core.domain.ProducerIntervalsMinMax;
import com.award.core.service.WorstFilmProducerIntervalsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GetWorstFilmProducerIntervalsUseCase {

    private final WorstFilmProducerIntervalsService worstFilmProducerIntervalsService;

    public ProducerIntervalsMinMax execute() {
        return worstFilmProducerIntervalsService.getProducersIntervals();
    }

}
