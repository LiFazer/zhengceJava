package cn.gov.hebei.ylbzj.entity;

import lombok.Data;

@Data
public class SyncDataStatistics {
    private Integer insertCount = 0;
    private Integer updateCount = 0;
    private Integer failCount = 0;
    private Integer allCount = 0;
    private Integer normalCount = 0;
    public void addI(Integer integer){
        insertCount+=integer;
    }
    public void addU(Integer integer){
        updateCount+=integer;
    }
    public void addF(Integer integer){
        failCount+=integer;
    }
    public void addA(Integer integer){
        allCount+=integer;
    }
    public void addN(Integer integer){
        normalCount+=integer;
    }
}
