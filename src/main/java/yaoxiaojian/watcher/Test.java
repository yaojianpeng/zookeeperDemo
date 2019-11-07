package yaoxiaojian.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
*@Author:yaoxiaojian on 2019/11/7 14:33
*@param:
*@return:
*@Description:客户端注册watcher三种方式：
 *         1.getData()——获取数据并对这个节点进行监听；
 *         2.exists()——判断节点是否存在并进行监听；
 *        3.getChildren()——获取节点的子节点并进行监听；
 */
public class Test implements Watcher{

    static ZooKeeper zooKeeper;

    static {
        try {
            zooKeeper = new ZooKeeper("localhost:2181", 4000,new Test());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("eventType:"+event.getType());
        if(event.getType()==Event.EventType.NodeDataChanged){
            try {
                zooKeeper.exists(event.getPath(),true);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //getData()/  exists  /getChildren
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //Curator
        String path="/watcher";
        if(zooKeeper.exists(path,false)==null) {
            zooKeeper.create("/watcher", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        Thread.sleep(1000);
        System.out.println("-----------");
        Stat stat=zooKeeper.exists(path,true); //true表示使用zookeeper实例中配置的watcher

        System.in.read();
    }
}
