package com.twinkle.scaffold.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.twinkle.scaffold.AbstractTest;
import com.twinkle.scaffold.client.redis.RedisClient;
import com.twinkle.scaffold.common.data.email.SimpleAttachment;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月6日 下午8:27:57 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class RedisClientTest extends AbstractTest {

    @Autowired
    private RedisClient redisClient;
    
    @Test
    public void redisClientTest1(){
        String objKey = "obj";
        SimpleAttachment simpleAttachment = new SimpleAttachment();
        simpleAttachment.setName("n1");
        simpleAttachment.setContentType("124");
        redisClient.setObj(objKey, simpleAttachment);
        simpleAttachment = redisClient.getObj(objKey,SimpleAttachment.class);
        System.out.println(simpleAttachment.toString());
    }
    
    @Test
    public void redisClientTest2(){
        String stringKey = "string";
        redisClient.setObj(stringKey,"string value");
        String keyValue = redisClient.getObj(stringKey,String.class);
        System.out.println(keyValue);
    }
    
    @Test
    public void redisClientTest3(){
        String listKey1 = "list";
        redisClient.setObj(listKey1,Arrays.asList(new String[]{"1","2"}));
        List<String> listValue1 = redisClient.getObj(listKey1,ArrayList.class);
        System.out.println(listValue1);
        
        List<SimpleAttachment> list = new ArrayList<>();
        SimpleAttachment simpleAttachment = new SimpleAttachment();
        simpleAttachment.setName("n1");
        simpleAttachment.setContentType("124");
        list.add(simpleAttachment);
        simpleAttachment = new SimpleAttachment();
        simpleAttachment.setName("n1");
        simpleAttachment.setContentType("124");
        list.add(simpleAttachment);
        String listKey2 = "list1";
        redisClient.setObj(listKey2,list);
        List<SimpleAttachment> listValue2= redisClient.getObj(listKey2,ArrayList.class);
        System.out.println(listValue2);
    }
    
    @Test
    public void redisClientTest4(){
        String mapKey = "map";
        Map<String,SimpleAttachment> mapValue = new HashMap<>();
        SimpleAttachment simpleAttachment = new SimpleAttachment();
        simpleAttachment.setName("n1");
        simpleAttachment.setContentType("124");
        mapValue.put("attachment1", simpleAttachment);
        simpleAttachment = new SimpleAttachment();
        simpleAttachment.setName("n1");
        simpleAttachment.setContentType("124");
        mapValue.put("attachment2", simpleAttachment);
        
        redisClient.setObj(mapKey,mapValue);
        Map<String,SimpleAttachment> map = redisClient.getObj(mapKey,HashMap.class);
        System.out.println(map);
    }
    
    @Test
    public void redisClientTest5(){
        redisClient.addSet("set", 20L, new String[]{"1","2","2"});
        Set<String> set = redisClient.getSet("set",String.class);
        System.out.println(set);
    }
    
}
