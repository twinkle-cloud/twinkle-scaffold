package com.twinkle.scaffold.component.cfig.constants;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月3日 下午9:49:35 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class CfigConstants {
    
    /**
     * 配置项结构类型 1-列表
     * */
    public static final Byte CONFIGENTRY_ITEMSTRUCTURETYPE_LIST = 1;
    /**
     * 配置项结构类型  2-字典
     * */
    public static final Byte CONFIGENTRY_ITEMSTRUCTURETYPE_MAP = 2;
    /**
     * 配置项结构类型  3-单父节点树
     * */
    public static final Byte CONFIGENTRY_ITEMSTRUCTURETYPE_SIMPLETREE = 3;
    
    /**
     * 配置作用范围 app
     * */
    public static final String CONFIGENTRY_SCOPE_APP = "app";
    /**
     * 配置作用范围  web
     * */
    public static final String CONFIGENTRY_SCOPE_WEB = "web";
    /**
     * 配置作用范围  service
     * */
    public static final String CONFIGENTRY_SCOPE_SERVICE = "service";
}
