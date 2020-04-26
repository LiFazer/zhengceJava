package cn.gov.hebei.ylbzj;

import cn.gov.hebei.ylbzj.constant.Singleton;
import cn.gov.hebei.ylbzj.service.TimesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private TimesService timesService;
    @Test
    public void exec() {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    timesService.countOpenTimes();
                }
            }).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==============="+ Singleton.count);

    }

}
