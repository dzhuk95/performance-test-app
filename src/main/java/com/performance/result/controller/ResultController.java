package com.performance.result.controller;

import com.performance.result.dto.ExecutedQueriesResultDto;
import com.performance.result.dto.GetExecutionResultDto;
import com.performance.result.service.ExecutionQueryResultService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("results")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResultController {

    ExecutionQueryResultService executionQueryResultService;

    @PostMapping
    @ApiOperation(value = "Get sql query execution results")
    public List<ExecutedQueriesResultDto> getExecutionResult(@RequestBody @Validated GetExecutionResultDto dto) {
        return executionQueryResultService.getExecutedQueriesResults(dto.getQuery(), dto.getDatabaseType());
    }
}
