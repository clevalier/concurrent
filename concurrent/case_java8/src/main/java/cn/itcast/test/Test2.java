package cn.itcast.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        //alt+enter可以转换为lambda表达式
        Runnable r = () -> log.debug("running");

        Thread t = new Thread(r, "t2");

        t.start();
    }
}
