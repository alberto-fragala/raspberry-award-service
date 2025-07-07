package com.award.core.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProducerIntervalsMinMax {

    private final List<ProducerIntervals> min;

    private final List<ProducerIntervals> max;

}
