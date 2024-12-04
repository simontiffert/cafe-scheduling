package com.optazen.cafe.rest;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolverJob;
import ai.timefold.solver.core.api.solver.SolverManager;
import com.optazen.cafe.domain.Schedule;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Path("/api")
public class ApiResource {
    @Inject
    SolverManager solverManager;

    @POST
    @Path("solve")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Schedule solve(Schedule schedule) throws ExecutionException, InterruptedException {
        SolverJob<Schedule, HardSoftScore> solverJob = solverManager.solveBuilder()
                .withProblemId(UUID.randomUUID())
                .withProblem(schedule)
                .run();

        return solverJob.getFinalBestSolution();
    }

}
