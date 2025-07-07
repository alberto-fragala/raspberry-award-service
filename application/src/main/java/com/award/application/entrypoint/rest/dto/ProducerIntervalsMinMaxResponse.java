package com.award.application.entrypoint.rest.dto;

import com.award.core.domain.ProducerIntervalsMinMax;

import java.util.List;

public record ProducerIntervalsMinMaxResponse(
        List<ProducerIntervalsResponse> min,
        List<ProducerIntervalsResponse> max
) {

    public static ProducerIntervalsMinMaxResponse of(ProducerIntervalsMinMax intervalsMinMax) {
        return new ProducerIntervalsMinMaxResponse(
                intervalsMinMax.getMin().stream().map(ProducerIntervalsResponse::of).toList(),
                intervalsMinMax.getMax().stream().map(ProducerIntervalsResponse::of).toList());
    }

}
