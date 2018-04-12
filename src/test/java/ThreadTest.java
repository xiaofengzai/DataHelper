import com.touyun.DataHelperApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Executor;

/**
 * Created by wenfeng on 2018/4/12.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataHelperApplication.class)
public class ThreadTest {
    @Autowired
    private Executor executor;
    public volatile Integer code=0;
    @Test
    public void threadTest(){
        executor.execute(new Parent("webfe",executor));
        executor.execute(new Parent("ff",executor));
        executor.execute(new Parent("gg",executor));
    }

    class Parent implements Runnable{
        private String name;

        private Executor executor;
        @Override
        public void run() {
            code+=1;
            executor.execute(new Children("children"+code,name));
            System.out.println("parent:"+name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Parent(String name, Executor executor) {
            this.name = name;
            this.executor=executor;
        }
    }
    class Children implements Runnable{
        private String name;
        private String parentName;
        @Override
        public void run() {
            System.out.println("parent:"+parentName+",children:"+name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public Children(String name, String parentName) {
            this.name = name;
            this.parentName = parentName;
        }
    }
}
