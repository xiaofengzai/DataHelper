package com.touyun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
   @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batchUpdate(String tableName){

    }

    public void batchInsert(String tableName){

    }

    public <T> List<T> selectList(String sql, Class<T> clazz){
        return null;
    }

    public <T> T selecOne(String sql,Class<T> tClass){
        return null;
    }


}
