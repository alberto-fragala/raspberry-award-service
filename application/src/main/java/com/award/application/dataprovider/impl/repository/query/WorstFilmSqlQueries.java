package com.award.application.dataprovider.impl.repository.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class WorstFilmSqlQueries {

    // CTE de vitórias dos produtores
    private static final String WINS_CTE = """
        WITH wins AS (
          SELECT
            ap.producer_id,
            p.name             AS producer,
            w.award_year       AS win_year,
            LAG(w.award_year)  OVER (
              PARTITION BY ap.producer_id
              ORDER BY w.award_year
            )                  AS previous_win
          FROM worst_film_awards w
          JOIN worst_film_award_producer ap ON ap.award_id = w.id
          JOIN producers p ON p.id = ap.producer_id
          WHERE w.winner = TRUE
        )
        """;

    // CTE de intervalos entre vitórias
    private static final String INTERVALS_CTE = """
        , intervals AS (
          SELECT
            producer,
            win_year - previous_win AS interval_years,
            previous_win            AS previousWin,
            win_year                AS followingWin
          FROM wins
          WHERE previous_win IS NOT NULL
        )
        """;

    // Consulta para encontrar os produtores com o menor intervalo entre vitórias
    public static final String FIND_PRODUCERS_INTERVALS_MIN = WINS_CTE
            + INTERVALS_CTE
            + """
        , min_interval AS (
          SELECT MIN(interval_years) AS min_int
          FROM intervals
        )
        SELECT
          producer,
          interval_years AS "interval",
          previousWin,
          followingWin
        FROM intervals
        CROSS JOIN min_interval
        WHERE interval_years = min_int
        ORDER BY interval_years ASC, previousWin ASC
        """;

    // Consulta para encontrar os produtores com o maior intervalo entre vitórias
    public static final String FIND_PRODUCERS_INTERVALS_MAX = WINS_CTE
            + INTERVALS_CTE
            + """
        , max_interval AS (
          SELECT MAX(interval_years) AS max_int
          FROM intervals
        )
        SELECT
          producer,
          interval_years AS "interval",
          previousWin,
          followingWin
        FROM intervals
        CROSS JOIN max_interval
        WHERE interval_years = max_int
        ORDER BY interval_years DESC, previousWin ASC
        """;

}
