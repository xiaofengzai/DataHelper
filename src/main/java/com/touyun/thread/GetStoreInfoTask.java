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
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class GetStoreInfoTask extends BaseStoreInfoTask {
    private Integer start=0;
    private Integer step=100;
    private String code;
    private Integer count=0;
    private Executor executor;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<StoreInfo> storeList;
    @Override
    public void run() {
        logger.info(name+"--code:"+code);
        //List<StoreInfo> list=storeInfoService.getAllStoreInfoList(SqlTemplate.STORE_ALL_PAGE_BY_CODE,code);
        if(count>step){
            storeList.stream().forEach(a->{
                handOneRecord(a,code);
            });
            storeInfoService.batchUpdateStoreInfo(storeList);
            logger.info(name+"--code:"+code);
        }else{
            while(start<count){
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<StoreInfo> subList=null;
                        if(start+step>count-1){
                            subList=storeList.subList(start,count-1);
                        }else {
                            subList=storeList.subList(start,start+step);
                        }

                        if(subList==null || subList.isEmpty())
                            return;
                        subList.stream().forEach(a->{
                            handOneRecord(a,code);
                        });
                        storeInfoService.batchUpdateStoreInfo(subList);
                        logger.info(name+"--code:"+code);
                    }
                });
                SleepUtil.sleep(2000);
                start+=step;
            }
        }

    }

    public GetStoreInfoTask(String code,StoreInfoService storeInfoService,Executor executor,String name) {
        super(storeInfoService,name);
        this.executor=executor;
        this.code = code;
        init(code);
        initStoreList();
    }

    void init(String code){
        count=storeInfoService.getStoreCountByCode(code);
    }

    void initStoreList(){
        storeList=storeInfoService.getAllStoreInfoList(SqlTemplate.STORE_ALL_PAGE_BY_CODE,code);
    }

//    private void handDataList(List<StoreInfo> data, String code){
//        if( data.size()>0){
//            List<String> addressList=data.stream().map(StoreInfo::getStoreAddress).collect(Collectors.toList());
//            Map<String,String> param=storeInfoService.getDefaultParam();
//            param.put("city",code);
//            String address= StringUtils.join(addressList, Constant.ADDRESS_SEPERATOR);
//            param.put("address",address);
//            List<String> locations=storeInfoService.getLocationList(param);
//            setLocationInfo(data.size(),data,locations);
//        }
//    }


//    private void setLocationInfo(Integer size,List<StoreInfo> storeInfos,List<String> locations){
//        int locationSize=locations.size();
//        for(int i=0;i<size && locationSize>0;i++){
//            try {
//                StoreInfo storeInfo=storeInfos.get(i);
//                String location=locations.get(i);
//                if(!org.springframework.util.StringUtils.isEmpty(location)){
//                    String[] data=location.split(Constant.seperator);
//                    storeInfo.setLongitude(Double.valueOf(data[0]));
//                    storeInfo.setLatitude(Double.valueOf(data[1]));
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//    }
}
