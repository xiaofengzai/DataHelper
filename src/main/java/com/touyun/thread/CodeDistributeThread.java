package com.touyun.thread;

import com.touyun.service.StoreInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class CodeDistributeThread implements Runnable {
    private List<String> codes;
    private StoreInfoService storeInfoService;
    private Executor executor;
    private CountDownLatch latch;
    private String name;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void run() {
        logger.info(name+"--start,code:"+codes.size());
        codes.stream().forEach(a->{
            executor.execute(new GetStoreInfoTask(a,storeInfoService,executor,name+"-sub"));
            SleepUtil.sleep(5000);
        });
        logger.info(name+"--end"+codes.size());
        latch.countDown();
    }

    public CodeDistributeThread( List<String> codes, StoreInfoService storeInfoService, Executor executor,CountDownLatch latch ,String name) {
        this.codes = codes;
        this.storeInfoService=storeInfoService;
        this.executor=executor;
        this.latch=latch;
        this.name=name;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public StoreInfoService getStoreInfoService() {
        return storeInfoService;
    }

    public void setStoreInfoService(StoreInfoService storeInfoService) {
        this.storeInfoService = storeInfoService;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
}
