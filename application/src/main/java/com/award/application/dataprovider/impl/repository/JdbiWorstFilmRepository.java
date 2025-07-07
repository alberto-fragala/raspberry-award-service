package com.award.application.dataprovider.impl.repository;

import com.award.core.dataprovider.repository.WorstFilmRepository;
import com.award.core.domain.ProducerIntervals;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.Query;

import java.util.List;

import static com.award.application.dataprovider.impl.repository.query.WorstFilmSqlQueries.FIND_PRODUCERS_INTERVALS_MAX;
import static com.award.application.dataprovider.impl.repository.query.WorstFilmSqlQueries.FIND_PRODUCERS_INTERVALS_MIN;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class JdbiWorstFilmRepository implements WorstFilmRepository {

    private final Jdbi jdbi;

    @Override
    public List<ProducerIntervals> findProducerIntervalsMin() {
        return execute(FIND_PRODUCERS_INTERVALS_MIN);
    }

    @Override
    public List<ProducerIntervals> findProducerIntervalsMax() {
        return execute(FIND_PRODUCERS_INTERVALS_MAX);
    }

    private List<ProducerIntervals> execute(String sql) {
        return jdbi.withHandle(handle -> {
            Query q = handle.createQuery(sql);
            return q.map(PRODUCER_INTERVALS_MAPPER).list();
        });
    }

    private static final RowMapper<ProducerIntervals> PRODUCER_INTERVALS_MAPPER =
            (rs, ctx) -> ProducerIntervals.builder()
                    .producer(rs.getString("producer"))
                    .interval(rs.getInt("interval"))
                    .previousWin(rs.getInt("previousWin"))
                    .followingWin(rs.getInt("followingWin"))
                    .build();
}
