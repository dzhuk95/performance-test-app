package com.performance.result.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateExecutionQueryResultDto {

    String query;
    Long executionDelta;
    DatabaseType databaseType;
}
