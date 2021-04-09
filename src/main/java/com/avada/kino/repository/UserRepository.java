package com.avada.kino.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    public Integer getUsersCount() {
        return jdbcTemplate.queryForObject(
                "select count(*) from users", Integer.class
        );
    }
}
