package cn.gov.hebei.ylbzj.task;

import cn.gov.hebei.ylbzj.service.SyncThirdQADataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SyncQADataTask {
    @Autowired
    private SyncThirdQADataService syncThirdQADataService;

//    @Scheduled(cron = "0 0 1 * * ?")
    @Scheduled(cron = "0 0 12 * * ?")
    public void doSync(){
        syncThirdQADataService.syncData();
    }
}
