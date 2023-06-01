package cn.itcast.n4;

import java.util.ArrayList;

public class TestThreadSafe {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;
    public static void main(String[] args) {
        ThreadSafeSubClass test = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i+1)).start();
        }
    }
}
class ThreadUnsafe {
    ArrayList<String> list = new ArrayList<>();//不在方法内部，是共享的资源，所以会不安全
    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    private void method2() {   //private可以保护线程安全，如果用public，那么子类会重新创建线程，从而触发线程安全的问题
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}

class ThreadSafe {
    public final void method1(int loopNumber) {  //final防止子类重写方法
        ArrayList<String> list = new ArrayList<>();//局部变量，线程独自创造实例，线程是安全的
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    public void method2(ArrayList<String> list) {
        list.add("1");
    }

    private void method3(ArrayList<String> list) {
        System.out.println(1);
        list.remove(0);
    }
}

class ThreadSafeSubClass extends ThreadSafe{
//    @Override
    public void method3(ArrayList<String> list) {
        System.out.println(2);
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}