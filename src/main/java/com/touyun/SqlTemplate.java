package com.touyun;

/**
 * Created by wenfeng on 2018/4/12.
 */
public class SqlTemplate {
    public static String UPDATE_USER="update demo set name=?,age=? where id=?;";
    public static String STORE_CODE="SELECT DISTINCT IFNULL(a.`code`,'') 'code' from store_info si INNER  JOIN area a on si.city_id=a.id where  store_source=1;";
    public static String STORE_COUNT_BY_CODE="SELECT count(*) from area a INNER JOIN store_info si on a.id=si.city_id and a.`code`=? and si.store_source=1;";
    public static String STORE_COUNT_NO_CODE="SELECT count(*) store_info  where city_id='' and store_source=1;";
    public static String STORE_PAGE_BY_CODE="SELECT si.id as storeId,0 as longitude,0 as latitude ,si.store_address as storeAddress from area a INNER JOIN store_info si on a.id=si.city_id and a.`code`=? and si.store_source=1 limit ?,?;";
    public static String BATCH_UPDATE_STORE_INFO ="update store_info set longitude=? ,latitude=? where id=? ";
}
