package cn.itcast.n4;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark {
    //wait,notify,notifyAll使用的时候必须先获得锁，unpark不用
    //park&unpark可以先unpark,但是wait&notify不能先notify
    //park原理：
//    1. 当前线程调用 Unsafe.park() 方法
//2. 检查 _counter ，本情况为 0，这时，获得 _mutex 互斥锁
//3. 线程进入 _cond 条件变量阻塞
//4. 设置 _counter = 0

    // unpark原理1：
    //     1. 调用 Unsafe.unpark(Thread_0) 方法，设置 _counter 为 1
    //     2. 唤醒 _cond 条件变量中的 Thread_0
    //     3. Thread_0 恢复运行
    //     4. 设置 _counter 为 0
    //unpark原理2：
//    1. 调用 Unsafe.unpark(Thread_0) 方法，设置 _counter 为 1
//            2. 当前线程调用 Unsafe.park() 方法
//3. 检查 _counter ，本情况为 1，这时线程无需阻塞，继续运行
//4. 设置 _counter 为 0


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            sleep(2);
            log.debug("park...");
            LockSupport.park();
            log.debug("resume...");
        }, "t1");
        t1.start();

        sleep(1);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}
