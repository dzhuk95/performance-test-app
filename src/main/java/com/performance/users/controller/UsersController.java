package com.performance.users.controller;

import com.performance.result.dto.DatabaseType;
import com.performance.users.dto.QueryRequestDto;
import com.performance.users.service.UsersService;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersController {

    UsersService usersService;

    @PostMapping("active")
    @ApiOperation(value = "Execute sql query in active users db")
    public void executeQueryForActiveUsers(@RequestBody @Validated QueryRequestDto requestDto) {
        usersService.executeQuery(requestDto.getQuery(), DatabaseType.ACTIVE_USER);
    }

    @PostMapping("inactive")
    @ApiOperation(value = "Execute sql query in inactive users db")
    public void executeQueryForInactiveUsers(@RequestBody @Validated QueryRequestDto requestDto) {
        usersService.executeQuery(requestDto.getQuery(), DatabaseType.INACTIVE_USER);
    }

    @PostMapping("legacy")
    @ApiOperation(value = "Execute sql query in legacy users db")
    public void executeQueryForLegacyUsers(@RequestBody @Validated QueryRequestDto requestDto) {
        usersService.executeQuery(requestDto.getQuery(), DatabaseType.LEGACY_USER);
    }
}
