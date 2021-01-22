package com.platform.tool.lock;

import com.platform.tool.starter.lock.CuratorFrameworkTemplateLock;

/**
 * @author SZ
 * @Description:
 * @date 2021/1/21 9:40
 */
public class OrderNumberGeneratorInvoke {
    private CuratorFrameworkTemplateLock lock;

    public OrderNumberGeneratorInvoke(CuratorFrameworkTemplateLock lock) {
        this.lock = lock;
    }

    private static OrderNumberGenerator generator = new OrderNumberGenerator();

    public void createOrder() {
        String orderNum = null;
        try {
            lock.getLock();
            orderNum = generator.getNumber();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.unLock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"---->"+orderNum);
    }
}
