package com.touyun.thread;

import com.touyun.Constant;
import com.touyun.SqlTemplate;
import com.touyun.model.StoreInfo;
import com.touyun.service.StoreInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class BaseStoreInfoTask implements Runnable {
    protected StoreInfoService storeInfoService;
    protected String name;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void run() {
        logger.info("开始无城市ID的门店任务");
       // List<StoreInfo> list=storeInfoService.getStoreInfoListWithoutCityId(SqlTemplate.STORE_NO_CODE);
        List<StoreInfo> list=storeInfoService.getStoreInfoListWithoutCityId(SqlTemplate.STORE_ALL);
        if(list==null || list.isEmpty())
            return;
        list.stream().forEach(a->{
            handOneRecord(a,"");
            SleepUtil.sleep(1000);
        });
        storeInfoService.batchUpdateStoreInfo(list);
        logger.info("结束无城市ID的门店任务");
    }

    public BaseStoreInfoTask( StoreInfoService storeInfoService, String name) {
        this.storeInfoService=storeInfoService;
        this.name=name;
    }

    protected void handOneRecord(StoreInfo storeInfo, String code){
        Map<String,String> param=storeInfoService.getDefaultParam();
        param.put("city",code);
        String storeAddress=storeInfo.getStoreAddress();
        int index=storeAddress.indexOf("#");
        if(index>0){
            param.put("address",storeAddress.substring(0,index));
        }else{
            param.put("address",storeAddress);
        }
        String location=storeInfoService.getLocation(param);
        if(!org.springframework.util.StringUtils.isEmpty(location)){
            String[] data=location.split(Constant.seperator);
            storeInfo.setLongitude(Double.valueOf(data[0]));
            storeInfo.setLatitude(Double.valueOf(data[1]));
        }
    }


}
