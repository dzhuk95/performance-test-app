package com.performance.users;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class UsersConfiguration {

    @Bean(name = "activeUsersDataSourceProperties")
    @ConfigurationProperties("performance.test.databases.active-users.datasource")
    public DataSourceProperties activeUsersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "activeUsersDatasource")
    @ConfigurationProperties("performance.test.databases.active-users.datasource.hikari")
    public HikariDataSource activeUsersDatasource() {
        return createDataSource(activeUsersDataSourceProperties());
    }

    @Bean(name = "activeUsersJdbcTemplate")
    public JdbcTemplate activeUsersJdbcTemplate() {
        return createJdbcTemplate(activeUsersDatasource(), 10);
    }

    @Bean(name = "legacyUsersDataSourceProperties")
    @ConfigurationProperties("performance.test.databases.legacy-users.datasource")
    public DataSourceProperties legacyUsersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "legacyUsersDatasource")
    @ConfigurationProperties("performance.test.databases.legacy-users.datasource.hikari")
    public HikariDataSource legacyUsersDatasource() {
        return createDataSource(legacyUsersDataSourceProperties());
    }

    @Bean(name = "legacyUsersJdbcTemplate")
    public JdbcTemplate legacyUsersJdbcTemplate() {
        return createJdbcTemplate(legacyUsersDatasource(), 15);
    }

    @Bean(name = "inactiveUsersDataSourceProperties")
    @ConfigurationProperties("performance.test.databases.inactive-users.datasource")
    public DataSourceProperties inactiveUsersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "inactiveUsersDatasource")
    @ConfigurationProperties("performance.test.databases.inactive-users.datasource.hikari")
    public HikariDataSource inactiveUsersDatasource() {
        return createDataSource(inactiveUsersDataSourceProperties());
    }

    @Bean(name = "inactiveUsersJdbcTemplate")
    public JdbcTemplate inactiveUsersJdbcTemplate() {
        return createJdbcTemplate(inactiveUsersDatasource(), 20);
    }

    private HikariDataSource createDataSource(final DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    private JdbcTemplate createJdbcTemplate(final DataSource dataSource, final int queryTimeoutSecs) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(queryTimeoutSecs);
        return jdbcTemplate;
    }
}
