package com.joybike.server.api.thirdparty.aliyun.redix;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by 58 on 2016/10/26.
 */
public class RedixUtil {

    private static String host = "r-2ze4892679511cf4.redis.rds.aliyuncs.com";
    private static int port = 6379;

    public static void main(String[] args) {
        Jedis redis = new Jedis(host, port);//连接redis
        redis.auth(" r-2ze4892679511cf4:841029JOYbike");//验证密码

        //KEYS
        Set keys = redis.keys("*");//列出所有的key，查找特定的key如：redis.keys("foo")
        Iterator t1 = keys.iterator();
        while (t1.hasNext()) {
            Object obj1 = t1.next();
            System.out.println(obj1);
        }


        redis.quit();
        redis.close();
    }

    private void test() {
        try {
            String host = "xx.kvstore.aliyuncs.com";//控制台显示访问地址
            int port = 6379;
            Jedis jedis = new Jedis(host, port);
//鉴权信息由用户名:密码拼接而成
            jedis.auth("instance_id:password");//instance_id:password
            String key = "redis";
            String value = "aliyun-redis";
//select db默认为0
            jedis.select(1);
//set一个key
            jedis.set(key, value);
            System.out.println("Set Key " + key + " Value: " + value);
//get 设置进去的key
            String getvalue = jedis.get(key);
            System.out.println("Get Key " + key + " ReturnValue: " + getvalue);
            jedis.quit();
            jedis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test2()
    {
        JedisPoolConfig config = new JedisPoolConfig();
//最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxIdle(200);
//最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
        config.setMaxTotal(300);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        String host = "*.aliyuncs.com";
        String password = "实例id:密码";
        JedisPool pool = new JedisPool(config, host, 6379, 3000, password);
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
/// ... do stuff here ... for example
            jedis.set("foo", "bar");
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
/// ... when closing your application:
        pool.destroy();
    }
}

