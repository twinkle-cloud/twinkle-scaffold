package com.twinkle.scaffold.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * 对应restful 风格的api接口，提供了参数的简单解析功能 <br/>
 * Date:    2019年8月3日 下午11:06:16 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class ApiParamUtils {

    /**
     * 将["p1","p2"] 和 p1 转换为list
     * */
    public static List<String> convertToStringList(String srcParam){
        if(JSONArray.isValid(srcParam)){
            List<String> result = JSONArray.parseArray(srcParam, String.class);
            return result;
        }
        List<String> result = new ArrayList<>();
        result.add(srcParam);
        return result;
    }
}
