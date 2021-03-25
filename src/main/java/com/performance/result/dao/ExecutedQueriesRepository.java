package com.performance.result.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutedQueriesRepository extends JpaRepository<ExecutedQueries, Long> {

    Optional<ExecutedQueries> findByQueryEqualsIgnoreCaseAndDatabaseType(String query, DatabaseType databaseType);
}
