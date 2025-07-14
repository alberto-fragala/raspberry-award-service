package com.award.application.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jdbi.v3.core.Jdbi;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class DatabaseSchemaConfig {

    public static final String BASE_DATA_CSV_NAME = "movielist.csv";

    private final Jdbi jdbi;

    void onStart(@Observes StartupEvent ev) {
        runInitSchema(BASE_DATA_CSV_NAME);
    }

    public void runInitSchema(String dataCsvName) {
        String schemaTemplate = loadInitSchemaFromClasspath();
        String sql = schemaTemplate.replace("&csv-name", dataCsvName);
        jdbi.useHandle(handle -> handle.createScript(sql).execute());
    }

    @SneakyThrows
    private String loadInitSchemaFromClasspath() {
        try (InputStream in = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("db/migration/init-schema-etl.sql")) {
            if (in == null) {
                throw new IllegalStateException("'init-schema-etl.sql' not found in classpath");
            }
            byte[] bytes = in.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

}
