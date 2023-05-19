package cn.itcast.n3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestJoin")
public class TestJoin {
    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        test3();
    }

    public static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(2);
            r1 = 10;
        });

        long start = System.currentTimeMillis();
        t1.start();

        // 线程执行结束会导致 join 结束
        log.debug("join begin");
        t1.join(3000);//t1最多只等3s，如果sleep的时间大于3秒，还是3s就执行接下来的，如果小于3s，那就是sleep里面的
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);//执行时间只有2s，不是3s，因为会提前结束
    }

    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            sleep(1);
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            sleep(2);
            r2 = 20;
        });
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        log.debug("join begin");
        t2.join();
        log.debug("t2 join end");
        t1.join();
        log.debug("t1 join end");
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);//结果是2s不是3s，因为两个线程几乎是同步起来的,是并行，是把t1的“结果”给t2，并不是t1执行完以后再执行t2
    }

    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            sleep(1);
            log.debug("结束");
            r = 10;
        });
        t1.start();
        t1.join();//等待t1线程的结束
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
