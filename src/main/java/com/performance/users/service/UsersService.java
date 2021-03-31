package com.performance.users.service;

import com.performance.result.dto.CreateExecutionQueryResultDto;
import com.performance.result.dto.DatabaseType;
import com.performance.result.service.ExecutionQueryResultService;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersService {

    JdbcTemplate activeUsersJdbcTemplate;
    JdbcTemplate legacyUsersJdbcTemplate;
    JdbcTemplate inactiveUsersJdbcTemplate;
    ExecutionQueryResultService executionQueryResultService;

    public UsersService(@Qualifier("activeUsersJdbcTemplate") JdbcTemplate activeUsersJdbcTemplate,
                        @Qualifier("legacyUsersJdbcTemplate") JdbcTemplate legacyUsersJdbcTemplate,
                        @Qualifier("inactiveUsersJdbcTemplate") JdbcTemplate inactiveUsersJdbcTemplate,
                        ExecutionQueryResultService executionQueryResultService) {
        this.activeUsersJdbcTemplate = activeUsersJdbcTemplate;
        this.legacyUsersJdbcTemplate = legacyUsersJdbcTemplate;
        this.inactiveUsersJdbcTemplate = inactiveUsersJdbcTemplate;
        this.executionQueryResultService = executionQueryResultService;
    }

    @Async
    public void executeQuery(String query, DatabaseType type) {
        executeQueryAndSaveResult(query, type, getJdbcTemplate(type));
    }

    private Consumer<String> getJdbcTemplate(DatabaseType databaseType) {
        return switch (databaseType) {
            case ACTIVE_USER -> activeUsersJdbcTemplate::execute;
            case LEGACY_USER -> legacyUsersJdbcTemplate::execute;
            case INACTIVE_USER -> inactiveUsersJdbcTemplate::execute;
        };
    }

    private void executeQueryAndSaveResult(String query, DatabaseType type, Consumer<String> consumer) {
        final long start = System.currentTimeMillis();
        consumer.accept(query);
        final long end = System.currentTimeMillis();
        final CreateExecutionQueryResultDto createExecutionQueryResultDto =
                CreateExecutionQueryResultDto.builder().query(query).databaseType(type).executionDelta(end - start)
                        .build();
        executionQueryResultService.saveQueryExecutionResult(createExecutionQueryResultDto);
    }
}
