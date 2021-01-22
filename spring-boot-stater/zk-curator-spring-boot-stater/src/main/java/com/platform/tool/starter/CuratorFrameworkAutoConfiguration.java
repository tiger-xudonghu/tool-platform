package com.platform.tool.starter;

import com.platform.tool.starter.listener.SessionConnectionListener;
import com.platform.tool.starter.lock.CuratorFrameworkTemplateLock;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author SZ
 * @Description:
 * @date 2021/1/2014:48
 */
public class CuratorFrameworkAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "tool-platform.zk-curator.enable", havingValue = "true", matchIfMissing = true)
    CuratorFrameworkProperties curatorFrameworkProperties(){
        return new CuratorFrameworkProperties();
    }

    @SneakyThrows
    @Bean
    @ConditionalOnBean(name = "curatorFrameworkProperties")
    public CuratorFramework curatorFramework(CuratorFrameworkProperties properties) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(properties.getZkHost())
                .connectionTimeoutMs(properties.getConnectionTimeOutMS())
                .sessionTimeoutMs(properties.getSessionTimeOutMS())
                .retryPolicy(new ExponentialBackoffRetry(properties.getSleepTimeMS(), properties.getMaxRetries()))
                .build();
        curatorFramework.start();
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String tempNode = "/ZOOKEEPER_SERVER/SESSION_TIME_OUT/"+hostAddress;
        String nodeValue = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        curatorFramework.getConnectionStateListenable()
                .addListener(new SessionConnectionListener(tempNode, nodeValue));
        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(tempNode,nodeValue.getBytes("UTF-8"));
        return curatorFramework;
    }

    @Bean
    @ConditionalOnBean(name = "curatorFramework")
    @ConditionalOnProperty(name = "tool-platform.zk-curator.lock.enable", havingValue = "true")
    CuratorFrameworkTemplateLock curatorFrameworkTemplateLock(CuratorFramework curatorFramework,CuratorFrameworkProperties properties){
        return new CuratorFrameworkTemplateLock(curatorFramework,properties.getLock().getLockPath());
    }

}
