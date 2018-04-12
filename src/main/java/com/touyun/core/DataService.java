package com.touyun.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
   @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 批量更新
     * @param pattern
     * @param data
     * @param parameterizedPreparedStatementSetter
     * @param <T>
     */
    public <T> void batchUpdate(String pattern, List<T> data, ParameterizedPreparedStatementSetter<T> parameterizedPreparedStatementSetter){
        jdbcTemplate.batchUpdate(pattern,data,data.size(),parameterizedPreparedStatementSetter);
    }

    public void batchInsert(String tableName){

    }

    /**
     * 查询多列
     * @param sql
     * @param clazz
     * @param rowMapper
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(String sql, Class<T> clazz, RowMapper<T> rowMapper){
      return   jdbcTemplate.query(sql,rowMapper);
    }

    public <T> List<T> selectList(String sql,Object[] args, Class<T> clazz, RowMapper<T> rowMapper){
        return   jdbcTemplate.query(sql,args,rowMapper);
    }


    /**
     * 查询单列
     * @param sql
     * @param clazz
     * @param columnName
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(String sql,Class<T> clazz,String columnName){
        List<T> result=jdbcTemplate.query(sql, new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                    return (T)resultSet.getString(columnName);
            }
        });
        return  result;
    }

    public <T> T selecOne(String sql,Class<T> tClass){
        return null;
    }

    public <T> T getCount(String sql,Class<T> clazz,Object[] param){
        return jdbcTemplate.queryForObject(sql,param,clazz);
    }


}
