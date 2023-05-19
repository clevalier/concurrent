package cn.itcast.n3;

public class TestCpu {
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1); //此处加sleep是为了避免空转浪费cpu，通过sleep把cpu让给其他程序，无需锁同步
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
