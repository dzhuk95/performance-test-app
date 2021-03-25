package com.performance.result;

import com.performance.result.dao.ExecutedQueries;
import com.performance.result.dao.ExecutedQueriesRepository;
import com.performance.result.dao.ExecutionResult;
import com.performance.result.dao.ExecutionResultRepository;
import com.performance.result.dto.CreatExecutionQueryResultDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QueryExecutionResultService {

    ExecutedQueriesRepository executedQueriesRepository;
    ExecutionResultRepository executionResultRepository;

    public void saveQueryExecutionResult(CreatExecutionQueryResultDto dto) {
        final ExecutedQueries executedQuery = executedQueriesRepository
                .findByQueryEqualsIgnoreCaseAndDatabaseType(dto.getQuery(), dto.getDatabaseType())
                .orElseGet(() -> createQuery(dto));

        final ExecutionResult executionResult =
                new ExecutionResult().setQuery(executedQuery).setExecutionTimeDelta(dto.getExecutionDelta());
        executionResultRepository.save(executionResult);
    }

    private ExecutedQueries createQuery(CreatExecutionQueryResultDto dto) {
        final ExecutedQueries executedQueries =
                new ExecutedQueries().setDatabaseType(dto.getDatabaseType()).setQuery(dto.getQuery());
        return executedQueriesRepository.save(executedQueries);
    }
}
