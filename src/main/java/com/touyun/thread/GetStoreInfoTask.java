package com.touyun.thread;

import com.sun.deploy.util.StringUtils;
import com.touyun.Constant;
import com.touyun.SqlTemplate;
import com.touyun.model.StoreInfo;
import com.touyun.service.StoreInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class GetStoreInfoTask implements Runnable {
    private Integer start=0;
    private Integer step=100;
    private String code;
    private Integer count=0;
    private StoreInfoService storeInfoService;
    private String name;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void run() {
        logger.info(name+"--code:"+code);
        while(start<count){
            List<StoreInfo> list=storeInfoService.getStoreInfoList(SqlTemplate.STORE_PAGE_BY_CODE,code,start,step);
            if(list==null || list.isEmpty())
                break;
            int size=list.size();
//            for(int i=0;i<size;i+=Constant.MAX_BATCH_REQUEST_ADDRESS_COUNT){
//                if(i+Constant.MAX_BATCH_REQUEST_ADDRESS_COUNT>size){
//                    handDataList(list.subList(i,size-1),code);
//                }else{
//                    handDataList(list.subList(i,i+Constant.MAX_BATCH_REQUEST_ADDRESS_COUNT),code);
//                }
//
//            }
            list.stream().forEach(a->{
                handOneRecord(a,code);
                SleepUtil.sleep(1000);
            });
            storeInfoService.batchUpdateStoreInfo(list);
            logger.info(name+"--code:"+code);
            start+=step;
        }
    }

    public GetStoreInfoTask(String code,StoreInfoService storeInfoService,String name) {
        this.code = code;
        this.storeInfoService=storeInfoService;
        this.name=name;
        init(code);
    }

    void init(String code){
        count=storeInfoService.getStoreCountByCode(code);
    }

    private void handDataList(List<StoreInfo> data, String code){
        if( data.size()>0){
            List<String> addressList=data.stream().map(StoreInfo::getStoreAddress).collect(Collectors.toList());
            Map<String,String> param=storeInfoService.getDefaultParam();
            param.put("city",code);
            String address= StringUtils.join(addressList, Constant.ADDRESS_SEPERATOR);
            param.put("address",address);
            List<String> locations=storeInfoService.getLoactionList(param);
            setLocationInfo(data.size(),data,locations);
        }
    }
    private void handOneRecord(StoreInfo storeInfo, String code){
        Map<String,String> param=storeInfoService.getDefaultParam();
        param.put("city",code);
        param.put("address",storeInfo.getStoreAddress());
        String location=storeInfoService.getLocation(param);
        if(!org.springframework.util.StringUtils.isEmpty(location)){
            String[] data=location.split(Constant.seperator);
            storeInfo.setLongitude(Double.valueOf(data[0]));
            storeInfo.setLatitude(Double.valueOf(data[1]));
        }
    }

    private void setLocationInfo(Integer size,List<StoreInfo> storeInfos,List<String> locations){
        int locationSize=locations.size();
        for(int i=0;i<size && locationSize>0;i++){
            try {
                StoreInfo storeInfo=storeInfos.get(i);
                String location=locations.get(i);
                if(!org.springframework.util.StringUtils.isEmpty(location)){
                    String[] data=location.split(Constant.seperator);
                    storeInfo.setLongitude(Double.valueOf(data[0]));
                    storeInfo.setLatitude(Double.valueOf(data[1]));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
