package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.TwoPhaseTermination")
public class Test13 {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        tpt.start();
        tpt.start();

        /*Thread.sleep(3500);
        log.debug("停止监控");
        tpt.stop();*/
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    // 监控线程
    private Thread monitorThread;
    // 停止标记
    private volatile boolean stop = false; //此处用volatile的目的是为了实现两段式的退出
    // 判断是否执行过 start 方法
    private boolean starting = false;//不能用volatile，因为volatile是对最新的变量可见，这里只能用synchronized，保证第一个线程把值改了，释放了锁才可以起到监控start()的作用

    // 启动监控线程
    public void start() {
        synchronized (this) {
            if (starting) { // false
                return;//已经执行过，不用再创建监控线程了
            }
            starting = true;//下次调用时候，直接到上一步的return
        }
        monitorThread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                // 是否被打断
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    // 停止监控线程
    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }
}
