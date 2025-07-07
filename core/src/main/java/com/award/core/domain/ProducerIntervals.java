package com.award.core.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// DDD: anotações para garantir imutabilidade e encapsulamento
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProducerIntervals {

    private final String producer;

    private final int interval;

    private final int previousWin;

    private final int followingWin;

}
