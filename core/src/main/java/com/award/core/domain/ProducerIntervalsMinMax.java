package com.award.core.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProducerIntervalsMinMax {

    @NonNull
    private final List<ProducerIntervals> min;

    @NonNull
    private final List<ProducerIntervals> max;

}
