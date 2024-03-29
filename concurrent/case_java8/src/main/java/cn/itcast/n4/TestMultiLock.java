package cn.itcast.n4;

import static cn.itcast.n2.util.Sleeper.sleep;

import cn.itcast.n2.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

public class TestMultiLock {
    public static void main(String[] args) {
        BigRoom bigRoom = new BigRoom();
        new Thread(() -> {
            bigRoom.study();
        },"小南").start();
        new Thread(() -> {
            bigRoom.sleep();
        },"小女").start();
    }
}

@Slf4j(topic = "c.BigRoom")
class BigRoom {

    //多把锁提高并发度，当然得是互不关联的业务
    //缺点在于，如果一个线程需要获得多把锁，容易产生死锁
    private final Object studyRoom = new Object();

    private final Object bedRoom = new Object();

    public void sleep() {
        synchronized (bedRoom) {
            log.debug("sleeping 2 小时");
            Sleeper.sleep(2);
        }
    }

    public void study() {
        synchronized (studyRoom) {
            log.debug("study 1 小时");
            Sleeper.sleep(1);
        }
    }

}
