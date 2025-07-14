package com.award.core.domain;

import lombok.*;

// DDD: anotações para garantir imutabilidade e encapsulamento
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProducerIntervals {

    @NonNull
    private final String producerName;

    private final int interval;

    private final int previousWin;

    private final int followingWin;

}
