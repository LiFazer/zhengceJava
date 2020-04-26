package cn.gov.hebei.ylbzj.db;

import cn.gov.hebei.ylbzj.entity.DBConfigModel;
import com.mchange.v2.c3p0.DataSources;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DBHelper {

    private static Map<String, DataSource> dsMap = new ConcurrentHashMap<>();

    private DBHelper() throws IllegalAccessException {
        throw new IllegalAccessException("辅助静态类无需实例化");
    }

    public synchronized static QueryRunner getQueryRunner(DBConfigModel dbConfig){
        if(dsMap.containsKey(dbConfig.getHashKey())){
            return new QueryRunner(dsMap.get(dbConfig.getHashKey()));
        }
        try {
            DataSource ds = DataSources.unpooledDataSource(dbConfig.getDbUrl(),dbConfig.getDbUser(),dbConfig.getDbPwd());
            dsMap.put(dbConfig.getHashKey(),ds);
            return  new QueryRunner(ds);
        } catch (SQLException e) {
           throw new RuntimeException("数据库初始化失败");
        }
    }

    public static List<Map<String, Object>> select(DBConfigModel dbConfig,String sql){
        try {
            QueryRunner runner = DBHelper.getQueryRunner(dbConfig);
            List<Map<String, Object>> lists = runner.query(sql, new MapListHandler());
            return lists;
        }catch (Exception e){
            log.error("sql执行失败,sql={},config:{},e=",sql,dbConfig,e);
            return Collections.EMPTY_LIST;
        }
    }

    public static void main(String[] args) {
        DBConfigModel db = new DBConfigModel();
        db.setDbDriver("mysql");
        db.setDbUrl("jdbc:mysql://localhost:3306/fun?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        db.setDbUser("root");
        db.setDbPwd("123456");
        System.out.println(DBHelper.select(db,"select * from answer"));
    }
}
