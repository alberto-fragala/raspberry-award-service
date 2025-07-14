package com.award.application.entrypoint.rest.dto;

import com.award.core.domain.ProducerIntervals;

public record ProducerIntervalsResponse(
        String producer,
        int interval,
        int previousWin,
        int followingWin
) {

    public static ProducerIntervalsResponse of(ProducerIntervals producerIntervals) {
        return new ProducerIntervalsResponse(
                producerIntervals.getProducerName(),
                producerIntervals.getInterval(),
                producerIntervals.getPreviousWin(),
                producerIntervals.getFollowingWin()
        );
    }

}
