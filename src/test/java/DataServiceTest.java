import com.touyun.DataHelperApplication;
import com.touyun.SqlTemplate;
import com.touyun.config.DatasourceTypeEnum;
import com.touyun.model.StoreInfo;
import com.touyun.model.User;
import com.touyun.core.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wenfeng on 2018/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataHelperApplication.class)
public class DataServiceTest {
    @Autowired
    private DataService dataService;

    @Test
    public void dataTest(){
        //getStoreIds();
        //batchUpdateTest();
        Integer count=dataService.getCount(DatasourceTypeEnum.Secondary,SqlTemplate.STORE_COUNT_BY_CODE,Integer.class,new Object[]{"330200"});
        System.out.println(count);
    }

    private void getStoreInfos() {
        List<StoreInfo> storeInfos=dataService.selectList("select id as storeId,0 as lat,0 as lon ,store_address as storeAddress from store_info limit 10",
                StoreInfo.class, new RowMapper<StoreInfo>() {
                    @Override
                    public StoreInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                        String id = resultSet.getString("storeId");
                        String address = resultSet.getString("storeAddress");
                        StoreInfo storeInfo = new StoreInfo();
                        storeInfo.setStoreId(id);
                        storeInfo.setStoreAddress(address);
                        return storeInfo;
                    }
                });

        System.out.println(storeInfos.size());
    }

    private void getStoreIds() {
        List<Integer> storeInfos=dataService.selectList("select display_times  from store_info limit 10",Integer.class,"display_times");

        System.out.println(storeInfos.size());
    }

    private void batchUpdateTest(){
        List<User> data= Arrays.asList(new User(1,"ws",12),new User(2,"ds",34),new User(3,"vv",78));
        dataService.batchUpdate(SqlTemplate.UPDATE_USER, data, new ParameterizedPreparedStatementSetter<User>() {
            @Override
            public void setValues(PreparedStatement preparedStatement, User user) throws SQLException {
                preparedStatement.setString(1,user.getName());
                preparedStatement.setInt(2,user.getAge());
                preparedStatement.setInt(3,user.getId());
            }
        });
    }
}
