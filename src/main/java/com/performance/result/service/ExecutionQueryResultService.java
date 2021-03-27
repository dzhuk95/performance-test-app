package com.performance.result.service;

import com.performance.result.dao.ExecutedQueries;
import com.performance.result.dao.ExecutedQueriesRepository;
import com.performance.result.dao.ExecutionResult;
import com.performance.result.dao.ExecutionResultRepository;
import com.performance.result.dto.CreatExecutionQueryResultDto;
import com.performance.result.dto.DatabaseType;
import com.performance.result.dto.ExecutedQueriesResultDto;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExecutionQueryResultService {

    ExecutedQueriesRepository executedQueriesRepository;
    ExecutionResultRepository executionResultRepository;

    public void saveQueryExecutionResult(CreatExecutionQueryResultDto dto) {
        final ExecutedQueries executedQuery = executedQueriesRepository
                .findByQueryEqualsIgnoreCase(dto.getQuery())
                .orElseGet(() -> createQuery(dto));

        final ExecutionResult executionResult =
                new ExecutionResult().setDatabaseType(dto.getDatabaseType()).setQuery(executedQuery)
                        .setExecutionTimeDelta(dto.getExecutionDelta());
        executionResultRepository.save(executionResult);
    }

    private ExecutedQueries createQuery(CreatExecutionQueryResultDto dto) {
        final ExecutedQueries executedQueries =
                new ExecutedQueries().setQuery(dto.getQuery());
        return executedQueriesRepository.save(executedQueries);
    }

    public List<ExecutedQueriesResultDto> getExecutedQueriesResults(String query, DatabaseType databaseType) {
        final ExecutedQueries executedQuery =
                executedQueriesRepository.findByQueryEqualsIgnoreCase(query)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Query %s not found", query)));
        final List<ExecutionResult> executionResults =
                databaseType == null ? executionResultRepository.findAllByQuery(executedQuery) :
                        executionResultRepository.findAllByQueryAndDatabaseType(executedQuery, databaseType);

        return executionResults.stream().collect(Collectors.groupingBy(ExecutionResult::getDatabaseType))
                .entrySet().stream().map(it -> createExecutedQueriesResultDto(it.getKey(), it.getValue()))
                .peek(it -> it.setQuery(query))
                .collect(Collectors.toList());
    }

    private ExecutedQueriesResultDto createExecutedQueriesResultDto(DatabaseType databaseType,
                                                                    List<ExecutionResult> executionResults) {
        final double averageExecutionTime =
                executionResults.stream().mapToLong(ExecutionResult::getExecutionTimeDelta).average().orElse(0L);
        final Long minimalExecutionTime =
                executionResults.stream().min(Comparator.comparing(ExecutionResult::getExecutionTimeDelta))
                        .map(ExecutionResult::getExecutionTimeDelta).orElse(0L);
        final Long maximumExecutionTime =
                executionResults.stream().max(Comparator.comparing(ExecutionResult::getExecutionTimeDelta))
                        .map(ExecutionResult::getExecutionTimeDelta).orElse(0L);

        return new ExecutedQueriesResultDto().setDatabaseType(databaseType)
                .setAverageExecutionTime(averageExecutionTime).setMinExecutionTime(minimalExecutionTime)
                .setMaxExecutionTime(maximumExecutionTime);
    }
}
