package com.award.application.entrypoint.rest;

import com.award.application.entrypoint.rest.dto.ProducerIntervalsMinMaxResponse;
import com.award.core.usecase.GetWorstFilmProducerIntervalsUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/awards/worst-film")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorstFilmResource {

    private final GetWorstFilmProducerIntervalsUseCase getIntervalsUseCase;

    @GET
    @Path("/producers-intervals")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducersIntervals() {
        var intervalsMinMax = getIntervalsUseCase.execute();
        var response = ProducerIntervalsMinMaxResponse.of(intervalsMinMax);
        return Response.ok(response).build();
    }

}
