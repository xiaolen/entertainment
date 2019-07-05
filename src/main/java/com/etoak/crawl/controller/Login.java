package com.etoak.crawl.controller;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author: 秦渊渊
 * @Date: 2018/11/7 20:15
 */
public class Login {




    /*===================================*/
    //post请求登录页面方式一
    @Test
    public void testlogin() {

        //构建一个Client
        HttpClient client = new DefaultHttpClient();
        CloseableHttpClient httpclient = null;
//        CloseableHttpResponse responseGet = null;
        //构建一个POST请求
        HttpPost post = new HttpPost("https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&get-lt=true&isajax=true&type=1&from=lianjiaweb&username=17635061795");
//        HttpPost post = new HttpPost("https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&get-lt=true&isajax=true&type=1&from=lianjiaweb&username=17635061795&password=52881314qxy&code=z9iq");
//        HttpPost post = new HttpPost("http://www.bidchance.com/");
        //构建表单参数
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("username", "17635061795"));
//        formParams.add(new BasicNameValuePair("userid", "liu1049461725"));
//        formParams.add(new BasicNameValuePair("pwd", "liujintao"));
        //将表单参数转化为“实体”
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "utf-8");
            //将“实体“设置到POST请求里
            post.setEntity(entity);
            //提交POST请求
            HttpResponse response = client.execute(post);
            //拿到返回的HttpResponse的"实体"
            HttpEntity result = response.getEntity();
            String content = EntityUtils.toString(result);
            //用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
            /*登录成功---------*/
            /*https://ty.ke.com/?utm_source=baidu&utm_medium=pinzhuan&utm_term=biaoti&utm_content=biaotimiaoshu&utm_campaign=sousuo*/
            System.out.println("===========================");
            System.out.println(content + "+++++++++++++");
            Map<String, String> contentMap = (Map) JSON.parse(content);
            System.out.println(contentMap.get("eventId"));
            System.out.println("===========================");
            //获取请求状态码
            System.out.println(response.getStatusLine().getStatusCode());

            //获得Cookies
            CookieStore cookieStore = ((DefaultHttpClient) client).getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
//            Cookie[] cookies = client.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                if (contentMap.get("eventId") != null && contentMap.get("eventId") != "") {
                    httpclient = HttpClients.createDefault();
                    //截取验证码
                    CrawlShell crawlShell = new CrawlShell();
                    String imgUrl = "https://upassport.ke.com/freshCaptch?t=" + System.currentTimeMillis();
                    String codetype = "1004";
                    String shell = crawlShell.shell(imgUrl, codetype);
                    Map<String, String> map = (Map) JSON.parse(shell);
                    String pic_str = map.get("pic_str");
//            formParams.add(new BasicNameValuePair("code",pic_str));
                    String url = "https://upassport.ke.com/login?service=https%3A%2F%2Fajax.api.ke.com%2Flogin%2Flogin%2Fgetuserinfo&type=1&get-lt=true&isajax=true&from=lianjiaweb&username=18392993083&code=" + pic_str;
                    HttpGet httpget = new HttpGet(url);
                    System.out.println("executing request " + httpget.getURI());
                    httpget.setHeader("cookies", cookies.toString());
                    // 执行get请求.
                    CloseableHttpResponse responseGet = httpclient.execute(httpget);
                    if (responseGet.getStatusLine().getStatusCode() == 200) {
                        httpclient = HttpClients.createDefault();
                        String url1 = "https://upassport.ke.com/login";
                        List<NameValuePair> formParams01 = new ArrayList<NameValuePair>();
                        formParams.add(new BasicNameValuePair("username", "17635061795"));
                        formParams.add(new BasicNameValuePair("password", "52881314qxy"));
                        formParams.add(new BasicNameValuePair("lt", contentMap.get("lt")));
                        formParams.add(new BasicNameValuePair("code", pic_str));
                        try {
                            UrlEncodedFormEntity entity01 = new UrlEncodedFormEntity(formParams, "utf-8");
                            //将“实体“设置到POST请求里
                            post.setEntity(entity);
                            //提交POST请求
                            HttpResponse response01 = client.execute(post);
                            //拿到返回的HttpResponse的"实体"
//                        HttpEntity response01 = response.getEntity();
                            if (response01.getStatusLine().getStatusCode() == 200) {
                                httpclient = HttpClients.createDefault();
                                String url2 = "https://user.ke.com/site/favorHouse/";
                                HttpGet httpget2 = new HttpGet(url2);
                                httpclient.execute(httpget2);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    if (responseGet.getStatusLine().getStatusCode() == 200) {
                        httpclient = HttpClients.createDefault();
                        String url1 = "https://upassport.ke.com/login";
                        List<NameValuePair> formParams01 = new ArrayList<NameValuePair>();
                        formParams.add(new BasicNameValuePair("username", "17635061795"));
                        formParams.add(new BasicNameValuePair("password", "52881314qxy"));
                        formParams.add(new BasicNameValuePair("lt", contentMap.get("lt")));
                        try {
                            UrlEncodedFormEntity entity01 = new UrlEncodedFormEntity(formParams, "utf-8");
                            //将“实体“设置到POST请求里
                            post.setEntity(entity);
                            //提交POST请求
                            HttpResponse response01 = client.execute(post);
                            //拿到返回的HttpResponse的"实体"
//                        HttpEntity response01 = response.getEntity();
                            if (response01.getStatusLine().getStatusCode() == 200) {
                                httpclient = HttpClients.createDefault();
                                String url2 = "https://user.ke.com/site/favorHouse/";
                                HttpGet httpget2 = new HttpGet(url2);
                                httpclient.execute(httpget2);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
//            crawlLogin(cookieStore);
            System.out.println("===========================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //post请求方式登录方式二
    @Test
    public void testPost() throws IOException {
        URL url = new URL("https://gitee.com/login?");
        URLConnection connection = url.openConnection();

        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
        //向页面传递数据。post的关键所在！
        //截取验证码
        CrawlShell crawlShell = new CrawlShell();
        String imgUrl = "https://gitee.com/rucaptcha?" + System.currentTimeMillis();
//        String imgUrl = "https://gitee.com/rucaptcha?"+System.currentTimeMillis();
        String codetype = "1006";
//        String shell = crawlShell.shell(imgUrl,codetype);
//        Map<String, String> map = (Map) JSON.parse(shell);
//        String pic_str = map.get("pic_str");
//        System.out.println(pic_str+"==============================");
        //&captcha="+pic_str
//        out.write("&userName=15934593494&password=LIUJINTAO0302");
//        out.write("&user[login]=&user[password]=");
        out.write("user[login]=&user[password]=");
        out.flush();
        out.close();
        // 一旦发送成功，用以下方法就可以得到服务器的回应：     
        String sCurrentLine;
        String sTotalString;
        sCurrentLine = "";
        sTotalString = "";
        InputStream urlStream = null;
        urlStream = connection.getInputStream();
        // 传说中的三层包装阿！     
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream));
        while ((sCurrentLine = reader.readLine()) != null) {
            sTotalString += sCurrentLine + "/r/n";

        }
        System.out.println(sTotalString);
//        crawlLogin();
    }


    //爬取页面
    @Test
    public void crawlLogin(String url) {
//        List<Cookie> cookies = cookieStore.getCookies();
////            Cookie[] cookies = client.getState().getCookies();
//        StringBuffer tmpcookies = new StringBuffer();
//        for (Cookie c : cookies) {
//            tmpcookies.append(c.toString() + ";");
//        }

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        // 取消 JavaScript支持 ✔
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = null;

        try {
//            HttpGet httpget = new HttpGet("https://upassport.ke.com/login");
//            httpget.setHeader("cookies", cookies.toString());
//            page = webClient.getPage("https://gitee.com/qinxiaoyuan/events");
            page = webClient.getPage(url);
//            page = webClient.getPage("http://i.baidu.com/");
//            page = webClient.getPage("http://www.bidchance.com/homePageUc.do");
            HtmlPage page1 = page.getPage();
            System.out.println("======================");
            System.out.println(page1.asXml());
            System.out.println("======================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    //抓取指定标签内容
    public void crawlLoginTest() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        // 取消 JavaScript支持 ✔
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = null;
        try {
            page = webClient.getPage("https://ty.ke.com/?utm_source=baidu&utm_medium=pinzhuan&utm_term=biaoti&utm_content=biaotimiaoshu&utm_campaign=sousuo");

            DomText rentingStyle = (DomText) page.getByXPath("//*[@id=\"loginModel\"]/div[2]/div[2]/div[2]/ul/li[1]/input").get(0);
            System.out.println("======================");
            System.out.println(rentingStyle.toString());
            System.out.println("======================");
//            HtmlPage page1 = page.getPage();
//            System.out.println("======================");
//            System.out.println(page1.asXml());
//            System.out.println("======================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
