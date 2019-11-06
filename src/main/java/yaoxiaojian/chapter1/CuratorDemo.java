package yaoxiaojian.chapter1;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
/**
*@Author:yaoxiaojian on 2019/11/6 14:53
*@param:
*@return:
*@Description:
 */
public class CuratorDemo {

    private static String CONNECTION_STR="192.168.13.102:2181,192.168.13.103:2181,192.168.13.104:2181";



    public static void main(String[] args) throws Exception {
//        CuratorFramework curatorFramework= CuratorFrameworkFactory.newClient("")

        CuratorFramework curatorFramework=CuratorFrameworkFactory.builder().
                connectString(CONNECTION_STR).sessionTimeoutMs(5000).
                retryPolicy(new ExponentialBackoffRetry(1000,3)).build();
        //ExponentialBackoffRetry 递增重试
        //RetryOneTime  仅仅只重试一次
        //RetryUntilElapsed 一直重试到规定时间结束
        //RetryNTimes 指定最大重试次数

        curatorFramework.start(); //启动
//        createData(curatorFramework);
//        updateData(curatorFramework);
        deleteData(curatorFramework);
        //CRUD
//        curatorFramework.create();
//        curatorFramework.setData(); //修改
//        curatorFramework.delete() ;// 删除
//        curatorFramework.getData(); //查询
    }

    private static void createData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.create().creatingParentsIfNeeded() //是否创建父节点
                .withMode(CreateMode.PERSISTENT).  //创建临时节点
                forPath("/data/program","test".getBytes());

    }

    private static void updateData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.setData().forPath("/data/program","up".getBytes());

    }

    private static void deleteData(CuratorFramework curatorFramework) throws Exception {
        Stat stat=new Stat();
        String value=new String(curatorFramework.getData().storingStatIn(stat).forPath("/data/program")); //的到版本号
        curatorFramework.delete().withVersion(stat.getVersion()).forPath("/data/program"); //根据版本号删除
    }



}
