package com.twinkle.scaffold.client.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * redis client <br/>
 * Date:    2019年8月4日 下午5:00:14 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
@Component
@Slf4j
public class RedisClient {

	private static final int DEFAULT_RETRY_INTERVAL = 200;// ms

	@Autowired
	@Nullable
	private RedisTemplate<String,Object> redisTemplate;

	/**
	 * 批量删除对应的value
	 * @param keys
	 */
	public void removeKeys(String... key) {
	    List<String> keyList = Arrays.asList(key);
	    redisTemplate.delete(keyList);
	}

	/**
	 * 根据pattern删除redis缓存
	 * @param pattern
	 */
	public void removePattern(String pattern) {
	    Set<String> keySet = redisTemplate.keys(pattern);
		if (CollectionUtils.isNotEmpty(keySet)){
		    this.removeKeys((String[]) keySet.toArray());
		}
	}

	/**
	 * 将对象保存为redis中String类型
	 */
    public void setObj(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    
    /**
     * 将对象保存为redis中String类型<br/>
     * 带有超时时间，单位秒
     */
    public void setObj(String key, Object value, Long expireSeconds) {
        redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }
    
    /**
     * 从redis的String类型中，获取一个对象
     */
	public <T> T getObj(String key,Class<?> T) {
	    Object obj = redisTemplate.opsForValue().get(key);
	    String jsonStr = JSON.toJSONString(obj);
	    return JSON.parseObject(jsonStr, T);
	}

	/**
	 * 获取Set集合，set不可重复
	 */
	public <T> Set<T> getSet(String key,Class<?> T) {
	    return (Set<T>)redisTemplate.opsForSet().members(key);
	}

	/**
	 * 新增一个集合，或向集合中添加item
	 */
	public void addSet(String key,Object... obj) {
	    redisTemplate.opsForSet().add(key, obj);
	}

	/**
     * 新增一个集合，或向集合中添加item<br/>
     * 同时设置key的过期时间
     */
	public void addSet(String key,Long expireSeconds,Object... obj) {
	    redisTemplate.multi();
	    addSet(key,obj);
	    redisTemplate.expire(key, expireSeconds,  TimeUnit.SECONDS);
	    redisTemplate.exec();
	}
	
	/**
     * 按顺序，向list中添加item,或者新增一个list
     */
    public void addItemToList(String key, Object... value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }
    
    /**
     * 按顺序，向list中添加item,或者新增一个list<br/>
     * 带有超时时间
     */
    public void addItemToList(String key,Long expireSeconds,Object... value) {
        redisTemplate.multi();
        addItemToList(key, value);
        redisTemplate.expire(key, expireSeconds,  TimeUnit.SECONDS);
        redisTemplate.exec();
    }
	
    /**
     * 按顺序，获取一个item
     */
    public <T> T popFirstItem(String key,Class<?> T) {
      return (T) redisTemplate.opsForList().leftPop(key);
    }
    
	/**
	 * 获取一个list
	 */
	public <T> List<T> getList(String key,Class<?> T) {
	    Long size = redisTemplate.opsForList().size(key);
	    return (List<T>) redisTemplate.opsForList().range(key, 0, size);
	}
	
     /**
      * 向map中添加一个item
      */
     public void putItemToMap(String key, String mapKey, Object mapValue) {
         redisTemplate.opsForHash().put(key, mapKey, mapValue);
     }
     
     /**
      * 新增一个Map,或者向已有Map中批量添加item
      */
     public void putMap(String key,Map<String,Object> map) {
         redisTemplate.opsForHash().putAll(key, map);
     }
     
     /**
      * 新增一个Map,或者向已有Map中批量添加item<br/>
      * 带有一个超时时间
      */
     public void putMap(String key,Map<String,Object> map,Long expireSeconds) {
         redisTemplate.multi();
         putMap(key,map);
         redisTemplate.expire(key, expireSeconds,  TimeUnit.SECONDS);
         redisTemplate.exec();
     }

	/**
	 * 获取一个Map
	 */
	public Map<Object, Object> getHashSet(String key) {
	    return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 获取一个Map中Item
	 */
	public <T> T getHashSetValue(String key, String mapKey,Class<?> T) {
	    return (T) redisTemplate.opsForHash().get(key, mapKey);
	}

	/**
	 * 从一个Map中，删除item
	 */
	public void removeFromMap(String key, String... mapKeys) {
	    redisTemplate.opsForHash().delete(key, mapKeys);
	}
	
	/**
	 * 向redis中添加一个锁标记
	 * @param lockTimeout 锁的锁定时间
	 * @param retryCount 枷锁失败后的尝试次数
	 * @return true 加锁成功，加锁失败
	 * */
	public boolean addLock(String key, int lockTimeout, int retryCount) {
		Boolean setResult = redisTemplate.opsForValue().setIfAbsent(key, key + "LOCK");
		if (setResult) {
			redisTemplate.expire(key, lockTimeout, TimeUnit.SECONDS);
			return true;
		}
		try {
			Thread.sleep(DEFAULT_RETRY_INTERVAL);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		if (!setResult) {
			int count = retryCount;
			while (count > 0) {
				setResult = redisTemplate.opsForValue().setIfAbsent(key, key + "LOCK");
				if (setResult) {
					redisTemplate.expire(key, lockTimeout, TimeUnit.SECONDS);
					return true;
				}
				try {
					Thread.sleep(DEFAULT_RETRY_INTERVAL);
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
				count--;
			}
		}
		return false;
	}
}
