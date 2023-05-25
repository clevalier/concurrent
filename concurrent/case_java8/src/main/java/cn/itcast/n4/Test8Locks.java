package cn.itcast.n4;

import lombok.extern.slf4j.Slf4j;

import static cn.itcast.n2.util.Sleeper.sleep;

@Slf4j(topic = "c.Test8Locks")
public class Test8Locks {
    public static void main(String[] args) {
        Number n1 = new Number();
        Number n2 = new Number();
        new Thread(() -> {
            log.debug("begin");
            n1.a();
        }).start();
        new Thread(() -> {
            log.debug("begin");
            n2.b();
        }).start();
    }
}
//主要看synchronized锁住的是谁，如果是static,则锁的是类，如果不加static，锁的是对象，对象是会互斥的；而且类和对象不会互斥

//该题两个一起跑，同一个对象，所以先2后1，因为a,b虽然同时运行，但是第一个线程等了1s
@Slf4j(topic = "c.Number")
class Number{
    public synchronized void a() {
        sleep(1);
        log.debug("1");
    }
    public synchronized void b() {
        log.debug("2");
    }
}
