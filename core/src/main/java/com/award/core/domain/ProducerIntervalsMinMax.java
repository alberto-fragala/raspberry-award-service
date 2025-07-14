package com.award.core.domain;

import lombok.*;

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
