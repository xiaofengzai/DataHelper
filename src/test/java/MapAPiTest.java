
import com.fasterxml.jackson.databind.JsonNode;
import com.touyun.Constant;
import com.touyun.DataHelperApplication;
import com.touyun.core.HttpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenfeng on 2018/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataHelperApplication.class)
public class MapAPiTest {
    @Autowired
    private HttpService httpService;
    @Test
    public void mapApiTest(){
        //?city="+city+"&address="+address+"&output=JSON&key="+
        Map<String,String> param=new HashMap<>();
        param.put("city","440300");
        param.put("address","广东省深圳市中城康桥花园南区a6栋广东省深圳市龙岗区中城康桥花园南区a6栋7号|深圳世界之窗|深圳大学|sfsfsfsd,.gsdgdsgmdsgmdlgmdgmdgm");
        param.put("output","JSON");
        param.put("batch","true");
        param.put("key", Constant.mapAppKey);
        JsonNode object=httpService.getRequest(Constant.mapApiUrl, JsonNode.class,param);
//        ObjectMapper mapper = new ObjectMapper();
//        JSONPObject jSONPObject = null;
//        try {
//            jSONPObject = mapper.readValue(object, new TypeReference<JSONPObject>() {});
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(object);
    }
}
