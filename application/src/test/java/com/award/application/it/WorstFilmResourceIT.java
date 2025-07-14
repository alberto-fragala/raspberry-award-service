package com.award.application.it;

import com.award.application.config.DatabaseSchemaConfig;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorstFilmResourceIT {

    private final DatabaseSchemaConfig databaseSchemaConfig;

    @Test
    @DisplayName("Testa intervalos com dados de Produção (arquivo padrão 'movielist.csv')")
    public void testProdGetProducersIntervalsMinMax() {
        given()
                .when().get("/api/v1/awards/worst-film/producers-intervals")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)

                .body("min", hasSize(1))
                .body("min[0].producer", equalTo("Joel Silver"))
                .body("min[0].interval", equalTo(1))
                .body("min[0].previousWin", equalTo(1990))
                .body("min[0].followingWin", equalTo(1991))

                .body("max", hasSize(1))
                .body("max[0].producer", equalTo("Matthew Vaughn"))
                .body("max[0].interval", equalTo(13))
                .body("max[0].previousWin", equalTo(2002))
                .body("max[0].followingWin", equalTo(2015));
    }

    @Test
    @DisplayName("Testa intervalos com dados preparados para aumentar a cobertura de testes")
    public void testGetProducersIntervalsMinMax() {
        databaseSchemaConfig.runInitSchema("movielist-test.csv");

        given()
                .when().get("/api/v1/awards/worst-film/producers-intervals")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)

                // Min: dois produtores com intervalo 2
                .body("min", hasSize(2))

                // ordered by previousWin ASC: Prod A e Prod X, intervalo 2000 - 2002 = 2 anos
                .body("min[0].producer", equalTo("Prod A"))
                .body("min[0].interval", equalTo(2))
                .body("min[0].previousWin", equalTo(2000))
                .body("min[0].followingWin", equalTo(2002))

                .body("min[1].producer", equalTo("Prod X"))
                .body("min[1].interval", equalTo(2))
                .body("min[1].previousWin", equalTo(2000))
                .body("min[1].followingWin", equalTo(2002))

                // Max: dois produtores com intervalo 13
                .body("max", hasSize(2))

                // ordered by previousWin ASC: Prod X (2002 - 2015), Prod Y (2005 - 2018)
                .body("max[0].producer", equalTo("Prod X"))
                .body("max[0].interval", equalTo(13))
                .body("max[0].previousWin", equalTo(2002))
                .body("max[0].followingWin", equalTo(2015))

                .body("max[1].producer", equalTo("Prod Y"))
                .body("max[1].interval", equalTo(13))
                .body("max[1].previousWin", equalTo(2005))
                .body("max[1].followingWin", equalTo(2018));
    }

}
