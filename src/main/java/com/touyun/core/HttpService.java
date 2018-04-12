package com.touyun.core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HttpService {
    @Autowired
    private RestTemplate restTemplate;

    public  <T> T getRequestWithHeader(String url,Class<T> clazz,Map<String,String> param){
        return restTemplate.getForObject(url,clazz,param);
    }
    public  <T> T getRequest(String url,Class<T> clazz,Map<String,String> param){
        StringBuffer fullUrl=new StringBuffer(url);
        fullUrl.append("?");
        param.keySet().forEach(key->{
            fullUrl.append(key);
            fullUrl.append("=");
            fullUrl.append(param.get(key));
            fullUrl.append("&");
        });
        return restTemplate.getForObject(fullUrl.toString(),clazz,param);
    }

}
