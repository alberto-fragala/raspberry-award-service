-- ===================================================================
-- 1) Limpeza de tabelas
-- ===================================================================
DROP TABLE IF EXISTS worst_film_award_producer;
DROP TABLE IF EXISTS producers;
DROP TABLE IF EXISTS worst_film_awards;

-- ===================================================================
-- 2) DDL
-- ===================================================================
CREATE TABLE worst_film_awards (
  id          BIGINT       AUTO_INCREMENT PRIMARY KEY,
  award_year  INT          NOT NULL,
  title       VARCHAR(255) NOT NULL,
  studios     VARCHAR(512),
  producers   VARCHAR(512),
  winner      BOOLEAN      DEFAULT FALSE
);

CREATE TABLE producers (
  id   BIGINT       AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE worst_film_award_producer (
  award_id    BIGINT NOT NULL,
  producer_id BIGINT NOT NULL,
  PRIMARY KEY (award_id, producer_id),
  FOREIGN KEY (award_id)    REFERENCES worst_film_awards(id),
  FOREIGN KEY (producer_id) REFERENCES producers(id)
);

-- ===================================================================
-- 3) Carrega CSV original em worst_film_awards
-- ===================================================================
INSERT INTO worst_film_awards (award_year, title, studios, producers, winner)
SELECT
  CAST("YEAR" AS INT),
  TITLE,
  STUDIOS,
  PRODUCERS,
  CASE WHEN LOWER(WINNER) = 'yes' THEN TRUE ELSE FALSE END
FROM CSVREAD(
  'classpath:movielist.csv',
  NULL,
  'UTF-8',
  ';'
);

-- ===================================================================
-- 4) Popula tabela de producers (normalização)
--    explodindo os campos “producers” por vírgula ou “ and ”
-- ===================================================================
INSERT INTO producers(name)
SELECT DISTINCT producer FROM (
  WITH RECURSIVE split_cte(award_id, rest, producer) AS (
      SELECT
        id,
        REPLACE(REPLACE(producers, ' and ', ','), ', ', ',') || ',',
        ''
      FROM worst_film_awards

      UNION ALL

      SELECT
        award_id,
        SUBSTRING(rest, LOCATE(',', rest) + 1),
        TRIM(SUBSTRING(rest, 1, LOCATE(',', rest) - 1))
      FROM split_cte
      WHERE LOCATE(',', rest) > 0
  )
  SELECT producer FROM split_cte
  WHERE producer <> ''
) t;

-- ===================================================================
-- 5) Popula tabela de relacionamento worst_film_award_producer
-- ===================================================================
INSERT INTO worst_film_award_producer (award_id, producer_id)
SELECT award_id, p.id FROM (
  WITH RECURSIVE split_cte(award_id, rest, producer) AS (
      SELECT
        id,
        REPLACE(REPLACE(producers, ' and ', ','), ', ', ',') || ',',
        ''
      FROM worst_film_awards

      UNION ALL

      SELECT
        award_id,
        SUBSTRING(rest, LOCATE(',', rest) + 1),
        TRIM(SUBSTRING(rest, 1, LOCATE(',', rest) - 1))
      FROM split_cte
      WHERE LOCATE(',', rest) > 0
  )
  SELECT award_id, producer FROM split_cte WHERE producer <> ''
) s JOIN producers p ON p.name = s.producer;
