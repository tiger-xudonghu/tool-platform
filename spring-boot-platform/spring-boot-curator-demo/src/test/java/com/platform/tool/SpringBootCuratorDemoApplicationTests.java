package com.platform.tool;

import com.platform.tool.lock.OrderNumberGeneratorInvoke;
import com.platform.tool.starter.lock.CuratorFrameworkTemplateLock;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
class SpringBootCuratorDemoApplicationTests {


    @Autowired
    CuratorFrameworkTemplateLock lock;

    @SneakyThrows
    @Test
    void contextLoads() {
        final int currency = 30;

        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < currency; i++) {
            OrderNumberGeneratorInvoke orderService = new OrderNumberGeneratorInvoke(lock);
            new Thread(orderService::createOrder, "thread-" + i).start();
        }

        latch.await();
    }


    @SneakyThrows
    @Test
    void contextLoads_12306() {

        CountDownLatch latch = new CountDownLatch(1);

        Ticket12306 ticket12306 = new Ticket12306(lock);
        for (int i = 0; i < 20; i++) {
            new Thread(ticket12306, "thread-携程").start();
            new Thread(ticket12306, "thread-飞猪").start();
            new Thread(ticket12306, "thread-12306").start();
        }
        latch.await();
    }

    public class Ticket12306 implements Runnable {
        private int tickets = 10;//票数

        private final CuratorFrameworkTemplateLock lock;

        public Ticket12306(CuratorFrameworkTemplateLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.getLock();
                if (tickets > 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + tickets);
                    tickets--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //释放锁
                try {
                    lock.unLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
