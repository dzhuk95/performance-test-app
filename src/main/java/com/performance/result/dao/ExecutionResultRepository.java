package com.performance.result.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionResultRepository extends JpaRepository<ExecutionResult, Long> {
}
