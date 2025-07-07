package com.award.application.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class JdbiConfig {

    private final DataSource dataSource;

    @Produces
    public Jdbi jdbi() {
        return Jdbi.create(dataSource);
    }

}
