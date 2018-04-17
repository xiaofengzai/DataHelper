package com.touyun;

import com.touyun.service.StoreInfoService;
import com.touyun.thread.BaseStoreInfoTask;
import com.touyun.thread.CodeDistributeThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
public class DataHelperApplication {
    private final static Logger logger = LoggerFactory.getLogger(DataHelperApplication.class);
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DataHelperApplication.class, args);
        StoreInfoService storeInfoService=SpringUtil.getBean(StoreInfoService.class);
        Executor executor=SpringUtil.getBean(Executor.class);
//        Integer batchSize=10;
//        List<String> codeList=storeInfoService.getCodeList();
//        Integer threadSize=codeList.size()/batchSize;
//        if(codeList.size()%batchSize>0){
//            threadSize+=1;
//        }
//        CountDownLatch latch = new CountDownLatch(threadSize);
//        for(int i=0;i<codeList.size();i+=batchSize){
//            if(i+batchSize>codeList.size()){
//                executor.execute(new CodeDistributeThread(codeList.subList(i,codeList.size()-1),storeInfoService,executor,latch,"parent"+i));
//            }else{
//                executor.execute(new CodeDistributeThread(codeList.subList(i,i+batchSize),storeInfoService,executor,latch,"parent"+i));
//            }
//
//        }
//        //
//         //executor.execute(new BaseStoreInfoTask(storeInfoService,"没有城市ID"));
//        latch.await();
        executor.execute(new BaseStoreInfoTask(storeInfoService,"所有"));
        logger.info("OK");

    }
}