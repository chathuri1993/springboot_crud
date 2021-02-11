package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {
    final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmployeeDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int updateEmployy() {
        Map<String, Object> namedParameters = new HashMap<>();
        String sql = new StringBuilder()
                .append("update employees set ")
                .append("first_name=:fname ")
                .append("where id=:id")
                .toString();

        namedParameters.put("fname", "ABC");
        namedParameters.put("id", 2);
        return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

}
