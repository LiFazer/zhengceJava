package cn.gov.hebei.ylbzj.constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SuSu
 * @description
 * @date 2020/2/8
 */
public class Singleton {

    //定义自增变量
    public static AtomicInteger count = new AtomicInteger(0);
    //累加
    public static void increase() {
        count.incrementAndGet();
    }

    private static Singleton instance = null;

    private Singleton() {
    }
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }



}
