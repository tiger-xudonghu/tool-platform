package com.platform.tool.starter.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author SZ
 * @Description:
 * @date 2021/1/2018:02
 */
public class CuratorFrameworkTemplateLock implements Lock {

    private final InterProcessMutex lock;

    public CuratorFrameworkTemplateLock(CuratorFramework curatorFramework,String lockPath) {
        this.lock = new InterProcessMutex (curatorFramework, lockPath);
    }


    @Override
    public void getLock() throws Exception {
        lock.acquire(5, TimeUnit.SECONDS);
    }

    @Override
    public void unLock() throws Exception {
        lock.release();
    }
}
