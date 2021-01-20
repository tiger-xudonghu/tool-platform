package com.platform.tool.starter.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SZ
 * @Description: zookeeper session会话超时，建立临时节点监听机制，进行服务重连
 * @date 2021/1/20 15:31
 */
public class SessionConnectionListener implements ConnectionStateListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String path;
    private String data;

    public SessionConnectionListener(String path, String data) {
        this.path = path;
        this.data = data;
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState){
        if(connectionState == ConnectionState.LOST){
            logger.error("[负载均衡失败]zookeeper session超时");
            while(true){
                try {
                    if(curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()){
                        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data.getBytes("UTF-8"));
                        logger.info("[负载均衡修复]重连zookeeper成功");
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
