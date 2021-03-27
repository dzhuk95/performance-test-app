package com.performance.users.controller;

import com.performance.result.dto.DatabaseType;
import com.performance.users.dto.QueryRequestDto;
import com.performance.users.service.UsersService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public void executeQueryForActiveUsers(@RequestBody QueryRequestDto requestDto) {
        usersService.executeQuery(requestDto.getQuery(), DatabaseType.ACTIVE_USER);
    }

    @PostMapping("inactive")
    public void executeQueryForInactiveUsers(@RequestBody QueryRequestDto requestDto) {
        usersService.executeQuery(requestDto.getQuery(), DatabaseType.INACTIVE_USER);
    }

    @PostMapping("legacy")
    public void executeQueryForLegacyUsers(@RequestBody QueryRequestDto requestDto) {
        usersService.executeQuery(requestDto.getQuery(), DatabaseType.LEGACY_USER);
    }
}
