package com.example.service.impl;

import com.example.service.RedisCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JedisCluster jedisCluster;
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheServiceImpl.class);

    //    @Resource
//    private  StringRedisTemplate stringRedisTemplate;
//    private static final String COMPARE_AND_DELETE =
//            "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
//                    "then\n" +
//                    "    return redis.call('del',KEYS[1])\n" +
//                    "else\n" +
//                    "    return 0\n" +
//                    "end";
//    private static final Long SUCCESS = 1L;
    private static final Long UNLOCK_SUCCESS_CODE = 1L;

    private static final String LOCK_SUCCESS_CODE = "ok";
    /**
     * lua脚本：判断锁住值是否为当前线程持有，是的话解锁，不是的话解锁失败
     */
    private static final String DISTRIBUTE_LOCK_SCRIPT_UNLOCK_VAL = "if" +
            " redis.call('get', KEYS[1]) == ARGV[1]" +
            " then" +
            " return redis.call('del', KEYS[1])" +
            " else" +
            " return 0" +
            " end";

    private volatile String unlockSha1 = "";

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 分布式锁
     *
     * @param key
     * @param time 锁定时间,以秒为单位
     * @return Boolean
     */
    @Override
    public Boolean getLock(String key, Integer time ) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        if(time==null||time==0){
            time=5;
        }
        logger.info(String.format("[getLock][分布式锁]key=%s;time=%s",key,time));
//            final long seconds=time;
//                String status = stringRedisTemplate.execute(new RedisCallback<String>() {
//                    @Override
//                    public String doInRedis(RedisConnection connection) throws DataAccessException {
//                        Jedis jedis = (Jedis) connection.getNativeConnection();
//                        String status = jedis.set(key,key, "nx", "ex", seconds);
//                        return status;
//                    }
//                });
//            if ("OK".equals(status)) {//抢到锁
//                return true;
//            }
        return tryLock(key,key,time);
    }

    /**
     * 一次尝试，快速失败。不支持重入
     * @param lockKey 锁key
     * @param lockVal 锁值，用于解锁校验
     * @param expiryTime 锁过期时间 MILLS
     * @return 是否获得锁
     */
    private boolean tryLock(String lockKey, String lockVal, long expiryTime){
        //相比一般的分布式锁，这里把setNx和setExpiry操作合并到一起，jedis保证原子性，避免连个命令之间出现宕机等问题
        try{
            String result = jedisCluster.set(lockKey, lockKey);
            logger.info(String.format("[tryLock][分布式锁]key=%s;time=%s;result=%s",lockKey,expiryTime,result));
            return LOCK_SUCCESS_CODE.equalsIgnoreCase(result);
        }catch(Exception e){
            logger.info(String.format("[tryLock][分布式锁][异常]key=%s;time=%s;e=%s",lockKey,expiryTime,e.getMessage()));
            return false;
        }

    }
    /**
     * 释放分布式锁，释放失败最可能是业务执行时间长于lockKey过期时间，应当结合业务场景调整过期时间
     * @param lockKey 锁key
     * @param lockVal 锁值
     * @return 是否释放成功
     */
//    public boolean tryUnLock(String lockKey, String lockVal){
//        List<String> keys = new ArrayList<>();
//        keys.add(lockKey);
//        List<String> argv = new ArrayList<>();
//        argv.add(lockVal);
//        try {
//            logger.info(String.format("[tryUnLock][分布式锁][释放锁]keys=%s;",keys));
//            Object result = jedisCluster.eval(unlockSha1, keys, argv);
//            logger.info(String.format("[tryUnLock][分布式锁][释放锁]keys=%s;result=%s",keys,result));
//            return UNLOCK_SUCCESS_CODE.equals(result);
//        }catch (JedisNoScriptException e){
//            logger.info(String.format("[tryUnLock][分布式锁][释放锁][异常]keys=%s;e=%s",keys,e.getMessage()));
//            //没有脚本缓存时，重新发送缓存
//            storeScript(lockKey);
//            Object result = jedisCluster.eval(unlockSha1, keys, argv);
//            return UNLOCK_SUCCESS_CODE.equals(result);
//        }catch (Exception e){
//            logger.info(String.format("[tryUnLock][分布式锁][释放锁][异常]keys=%s;e=%s",keys,e.getMessage()));
//            return false;
//        }
//    }
//
//    /**
//     * 由于使用redis集群，因此每个节点都需要各自缓存一份脚本数据
//     * @param slotKey 用来定位对应的slot的slotKey
//     */
//    public void storeScript(String slotKey){
//        if (StringUtils.isEmpty(unlockSha1) || !jedisCluster.scriptExists(unlockSha1, slotKey)){
//            //redis支持脚本缓存，返回哈希码，后续可以继续用来调用脚本
//            unlockSha1 = jedisCluster.scriptLoad(DISTRIBUTE_LOCK_SCRIPT_UNLOCK_VAL, slotKey);
//        }
//    }

    /**
     * 删除锁
     * @param key
     * @return
     */
    @Override
    public Boolean delLock(String key) {
        if(StringUtils.isEmpty(key)){
            return false;
        }
        logger.info(String.format("[delLock][分布式锁][释放锁]key=%s;",key));
        try{
            long result=jedisCluster.del(key);
            logger.info(String.format("[delLock][分布式锁][释放锁]key=%s;result=%s",key,result));
            if(result==UNLOCK_SUCCESS_CODE){
                return true;
            }
            return false;
        }catch (Exception e){
            logger.info(String.format("[delLock][分布式锁][释放锁]key=%s;e=%s",key,e.getMessage()));
            return false;

        }
//        try{
//            List<String> keys = Collections.singletonList(key);
//            stringRedisTemplate.execute(new DefaultRedisScript<>(COMPARE_AND_DELETE, String.class), keys, key);
//        }catch (Exception e){
//            return false;
//        }

    }


    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            logger.info(String.format("[hasKey][判断key是否存在][key]=%s;",key));
            boolean flag = redisTemplate.hasKey(key);
            logger.info(String.format("[hasKey][判断key是否存在结果][key]=%s[value=%s];",key,flag));
            return flag;

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(String.format("[hasKey][判断key是否存在失败][key]=%s;",key));
            return false;
        }
    }

    /**
     * 判断分布式锁是否存在
     * @param key
     * @return
     */
    public boolean hasLockKey(String key){
        try {
            logger.info(String.format("[hasKey][判断key是否存在][key]=%s;",key));
            boolean flag = jedisCluster.exists(key);
            logger.info(String.format("[hasKey][判断key是否存在结果][key]=%s[value=%s];",key,flag));
            return flag;

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(String.format("[hasKey][判断key是否存在失败][key]=%s;",key));
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        logger.info(String.format("[delKey][删除缓存][key]=%s;",key));
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
//        RedisConnectionFactory redisConnectionFactory=redisTemplate.getConnectionFactory();
//        RedisConnection redisConnection=redisConnectionFactory.getConnection();
//        List<RedisClientInfo> ss=redisConnection.getClientList();
        logger.info(String.format("[getKey][查询缓存][key]=%s;",key));
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            logger.info(String.format("[setKey][普通缓存放入][key]=%s, [value]=%s;",key, value.toString()));
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            logger.info(String.format("[setKey][普通缓存放入并设置时间][key]=%s, [value]=%s , [value]=%l;",key, value.toString(), time));
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 键 不能为null
     *
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
