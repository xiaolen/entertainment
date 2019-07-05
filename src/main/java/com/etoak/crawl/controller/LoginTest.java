package com.etoak.crawl.controller;


import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;


/**
 * @Author: 秦渊渊
 * @Date: 2018/11/8 13:59
 */
public class LoginTest {

    //post请求登录页面方式三
    @Test
    public void testlogin() throws IOException {

        HttpClient httpClient = new HttpClient();

//        CrawlShell crawlShell = new CrawlShell();
//        String imgUrl = "https://upassport.ke.com/freshCaptch?t="+System.currentTimeMillis();
//        String codetype = "1004";
//        String shell = crawlShell.shell(imgUrl,codetype);
//        Map<String, String> map = (Map) JSON.parse(shell);
//        String pic_str = map.get("pic_str");
        //构建表单参数
        NameValuePair[] data = {
                new NameValuePair("userName", "15934593494"),
                new NameValuePair("password", "LIUJINTAO0302"),
        };
        PostMethod postMethod = new PostMethod("https://passport.baidu.com/v2/api/?login/");
//        PostMethod postMethod = new PostMethod("https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&get-lt=true&isajax=true&type=1&from=lianjiaweb&");
        postMethod.setRequestBody(data);

        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
        int statusCode = httpClient.executeMethod(postMethod);
        //  获得登陆后的 Cookie
        Cookie[] cookies = httpClient.getState().getCookies();
        StringBuffer tmpcookies = new StringBuffer();
        for (Cookie c : cookies) {
            tmpcookies.append(c.toString() + ";");
            System.out.println("cookies = " + c.toString());
        }
        if (statusCode == 200) {
            System.out.println("模拟登录成功");
            // 进行登陆后的操作
//            GetMethod getMethod = new GetMethod("https://ty.ke.com/");
            GetMethod getMethod = new GetMethod("https://passport.baidu.com/v2/api/?login/");
            // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
            getMethod.setRequestHeader("cookie", tmpcookies.toString());
            // 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
            // 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
            postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            postMethod.setRequestHeader("cookie", tmpcookies.toString());
//            postMethod.setRequestHeader("Referer", "http://192.168.1.92:8800/users/sign_in");
            postMethod.setRequestHeader("Referer", "http://www.bidchance.com/");
            postMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpClient.executeMethod(getMethod);
            // 打印出返回数据，检验一下是否成功
            String text = getMethod.getResponseBodyAsString();
            System.out.println(text);
            System.out.println(statusCode);
        }
    }

        @Test
        public void test() {
            System.out.println("开始登陆。。。。。");
            HttpClient httpClient = new HttpClient();

            GetMethod httpGet = new GetMethod("https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&type=1&get-lt=true&isajax=true&from=lianjiaweb&username=18392993083");
            String res=null;
            try {
                httpClient.executeMethod(httpGet);
                res = httpGet.getResponseBodyAsString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }

//            Gson gson =new Gson();
//            Map<Object,Object>  map= gson.fromJson(res, Map.class);
            Map<String, String> map = (Map) JSON.parse(res);
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
            }
            httpGet.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            httpGet.setRequestHeader("cookie",tmpcookies.toString());
            httpGet.setRequestHeader("Referer", "https://upassport.ke.com/xd/api?name=upassport-ke-com");
            int statusCode=0;
            try {
                statusCode = httpClient.executeMethod(httpGet);
            }catch (Exception e){
                e.printStackTrace();
            }
            //截取验证码
            CrawlShell crawlShell = new CrawlShell();
            String imgUrl = "https://upassport.ke.com/freshCaptch?t=" + System.currentTimeMillis();
            String codetype = "1004";
            String shell = crawlShell.shell(imgUrl, codetype);
            Map<String, String> mapImpl = (Map) JSON.parse(shell);
            String pic_str = mapImpl.get("pic_str");
            int statusCode1 = 0;
            if(map.get("eventId")!=null&&map.get("eventId")!="") {
                GetMethod httpGet1 = new GetMethod("https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&type=1&get-lt=true&isajax=true&from=lianjiaweb&username=18392993083&code="+pic_str);
                try {
                    statusCode1 = httpClient.executeMethod(httpGet1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                httpGet1.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                httpGet1.setRequestHeader("", tmpcookies.toString());
                httpGet1.setRequestHeader("Referer", "https://upassport.ke.com/xd/api?name=upassport-ke-com");
            }


            if (statusCode1==200||statusCode==200) {
                // httpClient.executeMethod(method);
                //构建一个POST请求
                // HttpPost post = new HttpPost("https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&type=1&get-lt=true&isajax=true&from=lianjiaweb&username=18392993083&code=ylpf");
                NameValuePair[] data = {
                        new NameValuePair("isajax", "true"),
                        new NameValuePair("username", "17635061795"),
                        new NameValuePair("password", "52881314qxy"),
                        new NameValuePair("_eventId", "submit"),
                        new NameValuePair("code", pic_str),
                        new NameValuePair("service", "https://ajax.api.ke.com/login/login/getuserinfo"),
                        new NameValuePair("remember", "1"),
                        new NameValuePair("execution", "e2s1"),
                        new NameValuePair("lt", "LT-175290-WgqcY1uCziTTtx1xYb1EF24ufObQOB-upassport.ke.com"),
                        new NameValuePair("execution", "e2s1")
                };
                PostMethod postMethod = new PostMethod("https://upassport.ke.com/login");
                postMethod.setRequestBody(data);
                httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
                int statusCode2 = 0;
                try {

                    statusCode2 = httpClient.executeMethod(postMethod);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Cookie[] cookiess = httpClient.getState().getCookies();
                StringBuffer tmpcookiess = new StringBuffer();
                for (Cookie c : cookiess) {
                    tmpcookiess.append(c.toString() + ";");
                }
                if (statusCode2 == 200) {
                    System.out.println("模拟登录成功！！！");
                    System.out.println("爬取开始........");
                    // 进行登陆后的操作
//                    GetMethod getMethod = new GetMethod("https://user.ke.com/site/favorHouse/");
                    GetMethod getMethod = new GetMethod("https://ty.ke.com/ershoufang/");
                    // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
                    //   getMethod.setRequestHeader("cookie","lianjia_uuid=6b36dac6-9c8b-4d55-be2c-9249fcf21236; _ga=GA1.2.1421988624.1541586533; UM_distinctid=166edb7b9b5695-030c665efde825-8383268-1fa400-166edb7b9b618f; UM_distinctid=166f0d64e026e9-0ff0b7deb1febe-8383268-1fa400-166f0d64e0314f; ke_uuid=2f5c0bd0781568e8396f7c4ce23547a3; CNZZDATA1254525948=486299322-1541582064-https%253A%252F%252Fm.ke.com%252F%7C1541747341; select_city=140100; _gid=GA1.2.933585912.1541984577; lianjia_ssid=cd2b269e-67f7-482a-a68c-d5a345d70b4e; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22166ed66463778e-00f7a8f4bb62a5-8383268-2073600-166ed664638351%22%2C%22%24device_id%22%3A%22166ed66463778e-00f7a8f4bb62a5-8383268-2073600-166ed664638351%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_utm_source%22%3A%22baidu%22%2C%22%24latest_utm_medium%22%3A%22pinzhuan%22%2C%22%24latest_utm_campaign%22%3A%22sousuo%22%2C%22%24latest_utm_content%22%3A%22biaotimiaoshu%22%2C%22%24latest_utm_term%22%3A%22biaoti%22%7D%7D; SESSION=1995f5ef-6b6c-4f86-8e46-1e5afdc3a73e");
                    // 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
                    // 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
                    System.out.println(tmpcookies.toString() + "111111111111111111111");
                    postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
                    postMethod.setRequestHeader("cookie", tmpcookies.toString());//
                    postMethod.setRequestHeader("Referer", "https://ty.ke.com/ershoufang/");
                    postMethod.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                    try {
                        httpClient.executeMethod(getMethod);
                        getMethod.getRequestCharSet();
                        System.out.println(getMethod.getRequestCharSet()+"123123123123123");
                        String text = getMethod.getResponseBodyAsString();
                        String text1 = new String(text.getBytes("unicode"), "ISO-8859-1");
                        //tring newName=new String(text.getBytes(),"utf-8");ISO-8859-1
                        System.out.println("=======================");
                        String url= "https://user.ke.com/site/favorHouse/";
                        Login login = new Login();
                        login.crawlLogin(url);
                        System.out.println("=======================");
                        System.out.println(text1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
}
