package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.constant.Singleton;
import cn.gov.hebei.ylbzj.dao.TimesDao;
import cn.gov.hebei.ylbzj.entity.TimesDO;
import cn.gov.hebei.ylbzj.service.TimesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Service
@Slf4j
public class TimesServiceImpl implements TimesService {

    @Autowired
    private TimesDao timesDao;

    private ExecutorService service = new ThreadPoolExecutor(5, 30, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new AsynchronousThreadFactory());

    public static class AsynchronousThreadFactory implements ThreadFactory {

        private static int threadInitNumber = 0;

        private static synchronized int nextThreadNum() {
            return threadInitNumber++;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "update-times-" + nextThreadNum());
        }
    }

    @Override
    public TimesDO get(Integer id) {
        return timesDao.get(id);
    }

    @Override
    public List<TimesDO> list(Map<String, Object> map) {
        return timesDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return timesDao.count(map);
    }

    @Override
    public int save(TimesDO times) {
        Date date = new Date();
        times.setUpdateTime(date);
        times.setInsertTime(date);
        return timesDao.save(times);
    }

    @Override
    public int update(TimesDO times) {
        return timesDao.update(times);
    }

    @Override
    public int remove(Integer id) {
        return timesDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return timesDao.batchRemove(ids);
    }

    @Override
    public void countOpenTimes() {
        asyncOpenTimes();
    }

    @Override
    public TimesDO countTimes() {
        Map<String, Object> param = new HashMap<>();
        List<TimesDO> list = timesDao.list(param);
        TimesDO timesDO = null;
        if (CollectionUtils.isEmpty(list)) {
            return timesDO;
        }else{
            timesDO = list.get(0);
        }
        return timesDO;
    }

    //@Async
    public void asyncOpenTimes() {
        Singleton.increase();
        //System.out.println(Singleton.count);
    }


}
