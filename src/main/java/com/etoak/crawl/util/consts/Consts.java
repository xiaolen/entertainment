package com.etoak.crawl.util.consts;

import java.nio.charset.Charset;

/**
 * Created by echo on 2016/11/7.
 */
public class Consts {
    public static final Charset UTF8 = Charset.forName("utf8");
    public static final String TIMEOUT = "timeout";

    public static final String PUBLIC_CONFIG = "/config/public_config.properties";
    public static final String PHONE_MSG_CONFIG = "/config/phone_msg.properties";
    public static final String SHORT_URL_CONFIG = "/config/short_url.properties";
    public static final String LONG_URL_CONFIG = "/config/sms_templates.properties";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    /**
     * 默认页码
     */
    public static final Integer DEFAULT_PAGE_NUM = 1;
    /**
     * 默认单页显示条数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 线程池数量
     */
    public static final Integer MAX_THREAD_NUM = 5;
    /**
     * 最大持续发送时间,单位小时
     */
    public static final Integer MAX_SEND_TIME = 3;
    /**
     * 分页最大数据
     */
    public static final Integer MAX_SIZE = Integer.MAX_VALUE;


}
