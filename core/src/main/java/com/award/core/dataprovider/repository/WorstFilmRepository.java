package com.award.core.dataprovider.repository;

import com.award.core.domain.ProducerIntervals;

import java.util.List;

public interface WorstFilmRepository {

    List<ProducerIntervals> findProducerIntervalsMin();

    List<ProducerIntervals> findProducerIntervalsMax();

}
