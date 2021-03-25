package com.performance.users;

import com.performance.result.QueryExecutionResultService;
import com.performance.result.dao.DatabaseType;
import com.performance.result.dto.CreatExecutionQueryResultDto;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    JdbcTemplate activeUsersJdbcTemplate;
    JdbcTemplate legacyUsersJdbcTemplate;
    JdbcTemplate inactiveUsersJdbcTemplate;
    QueryExecutionResultService queryExecutionResultService;

    public UserService(@Qualifier("activeUsersJdbcTemplate") JdbcTemplate activeUsersJdbcTemplate,
                       @Qualifier("legacyUsersJdbcTemplate") JdbcTemplate legacyUsersJdbcTemplate,
                       @Qualifier("inactiveUsersJdbcTemplate") JdbcTemplate inactiveUsersJdbcTemplate,
                       QueryExecutionResultService queryExecutionResultService) {
        this.activeUsersJdbcTemplate = activeUsersJdbcTemplate;
        this.legacyUsersJdbcTemplate = legacyUsersJdbcTemplate;
        this.inactiveUsersJdbcTemplate = inactiveUsersJdbcTemplate;
        this.queryExecutionResultService = queryExecutionResultService;
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
        final CreatExecutionQueryResultDto creatExecutionQueryResultDto =
                CreatExecutionQueryResultDto.builder().query(query).databaseType(type).executionDelta(end - start)
                        .build();
        queryExecutionResultService.saveQueryExecutionResult(creatExecutionQueryResultDto);
    }
}
