package com.touyun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by wenfeng on 2018/4/13.
 */
@Service
public class JdbcTemplateProvider {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate primaryJdbcTemplate;
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate secondaryJdbcTemplate;

    public JdbcTemplate getJdbcTemplate(DatasourceTypeEnum datasourceTypeEnum){
        if(datasourceTypeEnum==null)
            return primaryJdbcTemplate;
       switch (datasourceTypeEnum){
           case Secondary:
               return secondaryJdbcTemplate;
               default:
                   return  primaryJdbcTemplate;
       }

    }
}
