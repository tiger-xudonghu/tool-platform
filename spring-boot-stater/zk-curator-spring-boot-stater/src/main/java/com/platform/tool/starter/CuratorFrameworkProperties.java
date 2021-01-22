package com.platform.tool.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SZ
 * @Description:
 * @date 2021/1/20 14:53
 */

@Data
@ConfigurationProperties(prefix = "tool-platform.zk-curator")
public class CuratorFrameworkProperties {

    /**
     *  ZK host地址
     */
    String zkHost = "127.0.0.1:2181";

    /**
     *  ZK 重试休眠时间
     */
    int sleepTimeMS=1000;

    /**
     *  ZK 连接最大尝试次数
     */
    int maxRetries = 3;

    /**
     *  session 会话超时时间
     */
    int sessionTimeOutMS = 30 * 1000;

    /**
     *  ZK 连接超时时间
     */
    int connectionTimeOutMS = 3 * 1000;

    /**
     *  是否开启
     */
    boolean enable = true;

    Lock lock;

    /**
     *  zookeeper 分布式锁
     */
    @Data
    static class Lock {

        /**
         *  是否开启
         */
        boolean enable = true;

        /**
         *  zookeeper 锁节点
         */
        String lockPath = "/CURATOR_LOCK_PATH";
    }
}
