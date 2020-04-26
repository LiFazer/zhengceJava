package cn.gov.hebei.ylbzj.timing;

import cn.gov.hebei.ylbzj.constant.Singleton;
import cn.gov.hebei.ylbzj.dao.TimesDao;
import cn.gov.hebei.ylbzj.entity.TimesDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SuSu
 * @description
 * @date 2020/2/8
 */
@Component
public class SyncData {

    @Autowired
    private TimesDao timesDao;

    @Scheduled(cron = "0 0/3 * * * ? ")
    public void syncData() {
        //查询数据库数据
        Map<String, Object> param = new HashMap<>();
        List<TimesDO> list = timesDao.list(param);
        if (CollectionUtils.isEmpty(list)) {
            //新增一条数据
            TimesDO timesDo = new TimesDO();
            AtomicInteger count = Singleton.count;
            timesDo.setOpenTimes(count.intValue());
            Date date = new Date();
            timesDo.setUpdateTime(date);
            timesDo.setInsertTime(date);
            timesDao.save(timesDo);
            Singleton.count = new AtomicInteger(0);
        }else{
            TimesDO timesDo = list.get(0);
            AtomicInteger count = Singleton.count;
            timesDo.setOpenTimes(count.intValue()+timesDo.getOpenTimes().intValue());
            //更新数据库数据
            timesDao.update(timesDo);
            Singleton.count =  new AtomicInteger(0);
        }

    }
}
