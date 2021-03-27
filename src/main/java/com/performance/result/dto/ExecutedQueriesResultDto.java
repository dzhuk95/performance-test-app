package com.performance.result.dto;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExecutedQueriesResultDto implements Serializable {
    static final long serialVersionUID = -3920059969077475926L;

    String query;
    DatabaseType databaseType;
    Long minExecutionTime;
    Long maxExecutionTime;
    Double averageExecutionTime;
}
