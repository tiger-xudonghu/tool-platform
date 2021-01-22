package com.platform.tool.starter.lock;

/**
 * @author SZ
 * @Description:
 * @date 2021/1/20 17:52
 */
public interface Lock {

    /**
     * 获取锁
     */
    void getLock() throws Exception;

    /**
     * 释放锁
     */
    void unLock() throws Exception;
}
