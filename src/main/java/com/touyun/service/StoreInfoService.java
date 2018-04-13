package com.touyun.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.touyun.Constant;
import com.touyun.SqlTemplate;
import com.touyun.core.DataService;
import com.touyun.core.HttpService;
import com.touyun.model.StoreInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenfeng on 2018/4/12.
 */
@Service
public class StoreInfoService {
    @Autowired
    private DataService dataService;
    @Autowired
    private HttpService httpService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Integer getStoreCountByCode(String code){
        return dataService.getCount(SqlTemplate.STORE_COUNT_BY_CODE,Integer.class,new Object[]{code});
    }

    public List<StoreInfo> getStoreInfoList(String sql,String code,Integer start,Integer step){
        return dataService.selectList(sql, new Object[]{code, start, step}, StoreInfo.class, new RowMapper<StoreInfo>() {
            @Override
            public StoreInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                String id = resultSet.getString("storeId");
                String address = resultSet.getString("storeAddress");
                StoreInfo storeInfo = new StoreInfo();
                storeInfo.setStoreId(id);
                storeInfo.setStoreAddress(address);
                return storeInfo;
            }
        });
    }

    public List<String> getLocationList(Map<String,String> param){
        JsonNode root=httpService.getRequest(Constant.mapApiUrl, JsonNode.class,param);
        List<String> list=new ArrayList<>();
        try {
            if(root.findValue("info").asText().equals(Constant.MAP_REQUEST_SUCCESS)){
                List<JsonNode> children=root.findValues("geocodes");
                if(children==null ||children.size()==0)
                    return list;
                children.stream().forEach(a->{
                    JsonNode locationNode=a.findValue("location");
                    if(locationNode==null){
                        list.add("");
                    }else {
                        String location=locationNode.asText("");
                        if(location.equals(Constant.NOT_FOUND_LOCATION)){
                            location="";
                        }
                        list.add(location);
                    }
                });
            }else{
                logger.error("请求异常");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

       return list;
    }
    public String getLocation(Map<String,String> param){
        String location="";
        JsonNode root=httpService.getRequest(Constant.mapApiUrl, JsonNode.class,param);
        if(root.findValue("info").asText().equals(Constant.MAP_REQUEST_SUCCESS)){
            JsonNode locationNode=root.findValue("location");
            if(locationNode==null)
                return "";
            location=locationNode.asText();
            if(StringUtils.isEmpty(location) || location.equals(Constant.NOT_FOUND_LOCATION))
                location="";
        }else{
            logger.error("请求异常");
        }
        return location;
    }

    public Map<String,String> getDefaultParam(){
        Map<String,String> param=new HashMap<>();
        param.put("output","JSON");
        param.put("batch","true");
        param.put("key", Constant.mapAppKey);
        return param;
    }

    public void batchUpdateStoreInfo(List<StoreInfo> data){
        dataService.batchUpdate(SqlTemplate.BATCH_UPDATE_STORE_INFO,data,new ParameterizedPreparedStatementSetter<StoreInfo>(){

            @Override
            public void setValues(PreparedStatement preparedStatement, StoreInfo storeInfo) throws SQLException {
                preparedStatement.setDouble(1,storeInfo.getLongitude());
                preparedStatement.setDouble(2,storeInfo.getLatitude());
                preparedStatement.setString(3,storeInfo.getStoreId());
            }
        });
    }

   public  List<String> getCodeList(){
        return dataService.selectList(SqlTemplate.STORE_CODE,String.class,"code");
    }
}
