package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test25")
public class Test25 {
    static final Object lock = new Object();
    // 表示 t2 是否运行过
    static boolean t2runned = false;

    //按照顺序2，1执行（设计模式-固定顺序运行）
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();//t1线程等待，把锁释放开
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();//这里的唤醒有点像是虚假唤醒
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
