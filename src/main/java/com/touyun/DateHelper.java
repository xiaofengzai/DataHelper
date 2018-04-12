package com.touyun;

import com.touyun.service.StoreInfoService;
import com.touyun.thread.CodeDistributeThread;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class DateHelper {
    public static void main(String[] args) throws InterruptedException {
        StoreInfoService storeInfoService=SpringUtil.getBean(StoreInfoService.class);
        Executor executor=SpringUtil.getBean(Executor.class);
        Integer batchSize=10;
        List<String> codeList=storeInfoService.getCodeList();
        Integer threadSize=codeList.size()/batchSize;
        if(codeList.size()%batchSize>0){
            threadSize+=1;
        }
        CountDownLatch latch = new CountDownLatch(threadSize);
        for(int i=0;i<codeList.size();i+=batchSize){
            if(i+batchSize>codeList.size()){
                executor.execute(new CodeDistributeThread(codeList.subList(i,codeList.size()-1),storeInfoService,executor,latch,"parent"+i));
            }else{
                executor.execute(new CodeDistributeThread(codeList.subList(i,i+batchSize),storeInfoService,executor,latch,"parent"+i));
            }

        }
        latch.await();
    }
}
