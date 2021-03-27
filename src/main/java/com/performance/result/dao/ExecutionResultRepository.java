package com.performance.result.dao;

import com.performance.result.dto.DatabaseType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionResultRepository extends JpaRepository<ExecutionResult, Long> {

    List<ExecutionResult> findAllByQueryAndDatabaseType(ExecutedQueries executedQueries, DatabaseType type);

    List<ExecutionResult> findAllByQuery(ExecutedQueries executedQueries);
}
