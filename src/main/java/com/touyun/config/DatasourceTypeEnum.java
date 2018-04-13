package com.touyun.config;

/**
 * Created by wenfeng on 2018/4/13.
 */
public enum DatasourceTypeEnum {
    Primary(1,"默认数据源"),
    Secondary(2,"备用数据源");
    private int key;
    private String name;

    DatasourceTypeEnum(int key, String name) {
        this.key = key;
        this.name = name;
    }
}
