package cn.gov.hebei.ylbzj.entity;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class DBConfigModel {
    private String dbDriver;
    private String dbUrl;
    private String dbUser;
    private String dbPwd;
    private String hashKey;
    public void setDbUrl(String dbUrl){
        if(StringUtils.isEmpty(dbUrl)){
            throw new RuntimeException("dbUrl 不能为空");
        }
        this.dbUrl = dbUrl;
        this.hashKey =String.valueOf(dbUrl.hashCode());
    }
}
