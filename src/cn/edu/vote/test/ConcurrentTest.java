package cn.edu.vote.test;

import cn.edu.vote.dao.TeacherDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试并发多线程访问是否会出现数据不一致的情况
 *
 * @author zzu
 */
public class ConcurrentTest implements Runnable{
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("cn.edu.vote.dao.TeacherDAO");
        new Thread(new ConcurrentTest(), "one").start();
        new Thread(new ConcurrentTest(), "two").start();
        new Thread(new ConcurrentTest(), "three").start();
        new Thread(new ConcurrentTest(), "four").start();
        new Thread(new ConcurrentTest(), "five").start();
        new Thread(new ConcurrentTest(), "six").start();
        new Thread(new ConcurrentTest(), "seven").start();
        new Thread(new ConcurrentTest(), "eight").start();
        new Thread(new ConcurrentTest(), "nine").start();
        new Thread(new ConcurrentTest(), "ten").start();
        new Thread(new ConcurrentTest(), "one 1").start();
        new Thread(new ConcurrentTest(), "two 1").start();
        new Thread(new ConcurrentTest(), "three 1").start();
        new Thread(new ConcurrentTest(), "four 1").start();
        new Thread(new ConcurrentTest(), "five 1").start();
        new Thread(new ConcurrentTest(), "six 1").start();
        new Thread(new ConcurrentTest(), "seven 1").start();
        new Thread(new ConcurrentTest(), "eight 1").start();
        new Thread(new ConcurrentTest(), "nine 1").start();
        new Thread(new ConcurrentTest(), "ten 1").start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread()+" start");
        List<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(2);
        res.add(3);
        res.add(4);
        res.add(5);
        for (int i = 0; i < 1000; i++) {
            TeacherDAO.push(res);
        }
        System.out.println(Thread.currentThread()+" end");
    }
}
