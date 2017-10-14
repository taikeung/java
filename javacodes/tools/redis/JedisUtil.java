package com.andy.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:redis操作工具类
 * @Author: Andy Hoo
 * @Date: 2017/10/14 10:57
 */
public class JedisUtil {
    private static Map<RedisDataSource,JedisPool> poolsMap = new HashMap<>(); //存放所有的连接池记录
    private JedisPool currentPool = null; //当前所使用的连接池

    /**
     * @methodName : JedisUtil
     * @Description : 构造函数
     * @params : [dataSource]
     */
    public JedisUtil(RedisDataSource dataSource) {

        if(!poolsMap.keySet().contains(dataSource)){ //如果当前请求的连接池不存在那么初始化
            createPool(dataSource);
        }

        currentPool = poolsMap.get(dataSource);

    }

    /**
     * @methodName : createPool
     * @Description : 创建连接池对象
     * @params : [dataSource]
     * @return : void
     */
    private void createPool(RedisDataSource dataSource){

        String name = dataSource.name().toLowerCase();

        /*
         *拼接参数名称
         */

        //host
        String hostName = "redis." + name + ".host";
        //port
        String portName = "redis." + name + ".port";
        //auth
        String authName = "redis." + name + ".auth";
        //最大连接数
        String maxActiveName = "redis." + name + ".maxActive";
       //maxidle
        String maxIdleName = "redis." + name + ".maxIdle";
        //maxWait
        String maxWaitName = "redis." + name + ".maxWait";
        //timeOut
        String timeOutName = "redis." + name + ".timeOut";
        //testOnBorrow
        String testOnBorrowName = "redis." + name + ".testOnBorrow";
        //dbIndex
        String dbIndexName = "redis." + name + ".dbIndex";

        JedisPoolConfig config = new JedisPoolConfig();


        //从配置文件中获取配置参数
        String host = ConfigUtils.getProperty(hostName);
        Integer port = Integer.valueOf(ConfigUtils.getProperty(portName));
        String auth = ConfigUtils.getProperty(authName);
        Integer maxActive = Integer.valueOf(ConfigUtils.getProperty(maxActiveName));
        Integer maxIdle = Integer.valueOf(ConfigUtils.getProperty(maxIdleName));
        Long maxWait = Long.valueOf(ConfigUtils.getProperty(maxWaitName));
        Integer timeOut = Integer.valueOf(ConfigUtils.getProperty(timeOutName));
        Boolean testOnBorrow = Boolean.valueOf(ConfigUtils.getProperty(testOnBorrowName));
        Integer dbIndex = Integer.valueOf(ConfigUtils.getProperty(dbIndexName));

        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);

        //创建连接池,并加入poolsMap
        JedisPool jedisPool = new JedisPool(config, host, port, timeOut, auth, dbIndex);
        poolsMap.put(dataSource,jedisPool);

    }
    /**
     * @methodName : isExist
     * @Description : 判断key是否存在
     * @params : [key]
     * @return : boolean
     */
    public boolean isExist(String key) {

        boolean isSuccess = false;
        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            isSuccess = jedis.exists(key);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
            return isSuccess;
        }
    }
    /**
     * @methodName : returnResource
     * @Description : jedis对象使用完毕后归还给连接池
     * @params : [pool, redis]
     * @return : void
     */
    public void returnResource(JedisPool pool, Jedis redis) {

        if (redis != null) {
            pool.returnResource(redis);
        }
    }

    /**
     * @methodName : setExpire
     * @Description : 设置key的过期时间
     * @params : [key, seconds]
     * @return : boolean
     */

    public boolean setExpire(String key, int seconds){

        boolean isSuccess = false;
        Jedis jedis = null;

        try {
            jedis = currentPool.getResource();
            jedis.expire(key, seconds);
            isSuccess = true;
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
            return isSuccess;
        }

    }
    /**
     * @methodName : delKey
     * @Description : 删除key
     * @params : [key]
     * @return : java.lang.Long
     */
    public Long delKey(String key){

        Long retLong = null;
        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            retLong = jedis.del(key);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
            return retLong;
        }

    }
    /**
     * @methodName : setString
     * @Description : 添加字符串
     * @params : [key, value]
     * @return : void
     */
    public void setString(String key, String value) {

        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }

    }

    /**
     * @methodName : getString
     * @Description : 获取字符串
     * @params : [key]
     * @return : java.lang.String
     */
    public String getString(String key) {

        String value = null;
        Jedis jedis = null;

        try {
            jedis = currentPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }

        return value;
    }

    /**
     * @methodName : setList
     * @Description : 添加list
     * @params : [key, list]
     * @return : void
     */
    public void setList(String key , List<String> list) {
         Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            for(String string : list){
                jedis.lpush(string);
            }

        } catch (Exception e) {
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(currentPool,jedis);
        }
    }

    /**
     * @methodName : getList
     * @Description : 获取list
     * @params : [key, start, end]
     * @return : java.util.List<java.lang.String>
     */
    public List<String> getList(String key,Long start,Long end){

        Jedis jedis = null;
        List<String> list = null;
        try {
            jedis = currentPool.getResource();
            list = jedis.lrange(key, start, end);
        } catch (Exception e) {
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            returnResource(currentPool,jedis);
        }

        return list;
    }



    /**
     * @methodName : setSet
     * @Description : 添加set
     * @params : [key, set]
     * @return : void
     */
    public void setSet(String key ,Set<String> set){

        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            for(String string : set){
                jedis.sadd(key, string);
            }
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }

    }
    /**
     * @methodName : getSet
     * @Description : 获取set
     * @params : [key]
     * @return : java.util.Set<java.lang.String>
     */
    public Set<String> getSet(String key){

        Set<String> set = null;
        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            set = jedis.smembers(key);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }

        return set;
    }


    /**
     * @methodName : setMap
     * @Description : 添加map
     * @params : [key, data]
     * @return : void
     */
    public void setMap(String key, Map<String, String> data) {

        Jedis jedis = null;
        Map<String,String> map = new HashMap<>();
        try {
            jedis = currentPool.getResource();
            map = jedis.hgetAll(key);
            map.putAll(data);
            jedis.hmset(key, map);

        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
    }
    /**
     * @methodName : getMap
     * @Description : 获取map
     * @params : [key]
     * @return : java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getMap(String key) {

        Map<String, String> map = new HashMap<>();
        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
        return map;
    }
    /**
     * @methodName : existMapField
     * @Description : 判断map中的field是否存在
     * @params : [key, field]
     * @return : java.lang.Boolean
     */
    public Boolean existMapField(String key,String field){

        Jedis jedis = null;
        Boolean exist = false;
        try {
            jedis = currentPool.getResource();
            exist = jedis.hexists(key, field);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
        return exist;
    }
    /**
     * @methodName : setMapByField
     * @Description : 通过field添加map值
     * @params : [key, field, value]
     * @return : void
     */
    public void setMapByField(String key, String field,String value){

        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
    }
    /**
     * @methodName : getMapByField
     * @Description : 通过field获取map的值
     * @params : [key, field]
     * @return : java.lang.String
     */
    public String getMapByField(String key,String field){

        Jedis jedis = null;
        String value = null;
        try {
            jedis = currentPool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }

        return value;
    }
    /**
     * @methodName : delMapByField
     * @Description : 通过field删除map的值
     * @params : [key, field]
     * @return : void
     */
    public void delMapByField(String key,String field){

        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
    }
    /**
     * @methodName : setObjToMap
     * @Description : 通过序列化向map中添加对象
     * @params : [key, field, obj]
     * @return : void
     */
    public void setObjToMap(String key,String field,Object obj){

        Jedis jedis = null;
        try {
            jedis = currentPool.getResource();
            jedis.hset(key.getBytes(), field.getBytes(), SerializeUtil.serialize(obj));
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
    }
    /**
     * @methodName : getObjFromMap
     * @Description : 通过field获取map中的对象
     * @params : [key, field]
     * @return : java.lang.Object
     */
    public Object getObjFromMap(String key,String field){

        Jedis jedis = null;
        Object object = null;

        try {
            jedis = currentPool.getResource();
            byte[] hget = jedis.hget(key.getBytes(), field.getBytes());
            object = SerializeUtil.unserialize(hget);
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }

        return object;
    }
    /**
     * @methodName : setObject
     * @Description : 添加对象
     * @params : [object, key]
     * @return : java.lang.String
     */
    public String setObject(Object object, String key) {

        Jedis jedis = null;
        String value = null;

        try {
            jedis = currentPool.getResource();
            value = jedis.set(key.getBytes(), SerializeUtil.serialize(object));
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
        return value;

    }


    /**
     * @methodName : getObject
     * @Description : 通过key来获取对象
     * @params : [key]
     * @return : java.lang.Object
     */
    public Object getObject(String key) {

        Jedis jedis = null;
        Object value = null;

        try {
            jedis = currentPool.getResource();
            value = SerializeUtil.unserialize(jedis.get(key.getBytes()));
        } catch (Exception e) {
            // 释放redis对象
            currentPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(currentPool, jedis);
        }
        return value;
    }

}
