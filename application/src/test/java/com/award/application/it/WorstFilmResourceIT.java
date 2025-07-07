package com.award.application.it;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class WorstFilmResourceIT {

    @Test
    public void testGetProducersIntervalsMinMax() {
        given()
                .when().get("/api/v1/awards/worst-film/producers-intervals")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)

                // Min: apenas Prod X, intervalo 2000 - 2002 = 2 anos
                .body("min", hasSize(1))
                .body("min[0].producer", equalTo("Prod X"))
                .body("min[0].interval", equalTo(2))
                .body("min[0].previousWin", equalTo(2000))
                .body("min[0].followingWin", equalTo(2002))

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
