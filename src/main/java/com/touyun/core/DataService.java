package com.touyun.core;

import com.touyun.config.DatasourceTypeEnum;
import com.touyun.config.JdbcTemplateProvider;
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
    private JdbcTemplateProvider jdbcTemplateProvider;

    /**
     * 批量更新
     * @param pattern
     * @param data
     * @param parameterizedPreparedStatementSetter
     * @param <T>
     */
    public <T> void batchUpdate(String pattern, List<T> data, ParameterizedPreparedStatementSetter<T> parameterizedPreparedStatementSetter){
       batchUpdate(null,pattern,data,parameterizedPreparedStatementSetter);
    }

    public <T> void batchUpdate(DatasourceTypeEnum datasourceTypeEnum,String pattern, List<T> data, ParameterizedPreparedStatementSetter<T> parameterizedPreparedStatementSetter){
        jdbcTemplateProvider.getJdbcTemplate(datasourceTypeEnum).batchUpdate(pattern,data,data.size(),parameterizedPreparedStatementSetter);
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
        return   selectList(null,sql,clazz,rowMapper);
    }

    public <T> List<T> selectList(DatasourceTypeEnum datasourceTypeEnum,String sql, Class<T> clazz, RowMapper<T> rowMapper){
      return   jdbcTemplateProvider.getJdbcTemplate(datasourceTypeEnum).query(sql,rowMapper);
    }

    public <T> List<T> selectList(String sql,Object[] args, Class<T> clazz, RowMapper<T> rowMapper){
        return selectList(null,sql,args,clazz,rowMapper);
    }
    public <T> List<T> selectList(DatasourceTypeEnum datasourceTypeEnum,String sql,Object[] args, Class<T> clazz, RowMapper<T> rowMapper){
        return   jdbcTemplateProvider.getJdbcTemplate(datasourceTypeEnum).query(sql,args,rowMapper);
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
        return selectList(null,sql,clazz,columnName);
    }

    public <T> List<T> selectList(DatasourceTypeEnum datasourceTypeEnum,String sql,Class<T> clazz,String columnName){
        List<T> result=jdbcTemplateProvider.getJdbcTemplate(datasourceTypeEnum ).query(sql, new RowMapper<T>() {
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
        return  getCount(null,sql,clazz,param);
    }

    public <T> T getCount(DatasourceTypeEnum datasourceTypeEnum,String sql,Class<T> clazz,Object[] param){
        return jdbcTemplateProvider.getJdbcTemplate(datasourceTypeEnum).queryForObject(sql,param,clazz);
    }


}
