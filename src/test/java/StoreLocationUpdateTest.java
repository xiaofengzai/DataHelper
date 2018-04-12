import com.touyun.DataHelperApplication;
import com.touyun.core.DataService;
import com.touyun.core.HttpService;
import com.touyun.service.StoreInfoService;
import com.touyun.thread.CodeDistributeThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Created by wenfeng on 2018/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataHelperApplication.class)
public class StoreLocationUpdateTest {
    @Autowired
    private StoreInfoService storeInfoService;
    @Autowired
    private Executor executor;

    @Test
    public void mapApiTest() throws InterruptedException {
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
