package com.performance.result.dto;

import com.performance.result.dao.DatabaseType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreatExecutionQueryResultDto {

    String query;
    Long executionDelta;
    DatabaseType databaseType;
}
