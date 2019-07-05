package com.etoak.crawl.controller;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static org.apache.commons.codec.digest.DigestUtils.md5;


/**
 * @Author: 秦渊渊
 * @Date: 2018/11/7 10:27
 */
public class CrawlShell {

    //打印日志
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlShell.class);
    private String username = "jakejie";
    private String password = "zhujie";
    private String softid = "111";


    public static void main(String[] args) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 取消 CSS 支持 ✔
//        webClient.getOptions().setCssEnabled(true);
//        // 取消 JavaScript支持 ✔
//        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlPage page = null;



        try {
            page = webClient.getPage("https://ty.ke.com/?utm_source=tencent&utm_medium=navi&utm_term=mingzhan&utm_content=2018615&utm_campaign=pc");
            DomNodeList<DomNode> domNodes = page.querySelectorAll(".login_panel animated");
            for (int i = 0; i < domNodes.getLength(); i++) {

                System.out.println(domNodes.get(i).asXml());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public String shell(String imgUrl,String codetype){
        //图片

        byte[] imageFromNetByUrl = getImageFromNetByUrl(imgUrl);
//        String filePath = "D:\\file\\freshCaptch.jpg";
        String lenMin = "0";
        //图片中的数据类型

        String imagCode = recognition(codetype, lenMin,imageFromNetByUrl);
//        String[] split = s.split(",");
        System.out.println(imagCode);
        return imagCode;
    }
    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    private static byte[] getImageFromNetByUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }




    /**
     * 识别图片_按图片文件路径
     * @param codetype		图片类型
     * @param lenMin		最小位数
     * @param filePath		图片文件路径
     * @return
     */
    private String postPic(String codetype, String lenMin, String filePath){
        String result = "";
        String param = String.format("user=%s&pass=%s&softid=%s&codetype=%s&lenMin=%s", username, password, softid, codetype, lenMin);
        FileInputStream fis = null;
        try {
            File f = new File(filePath);
            if (null != f) {
                int size = (int) f.length();
                byte[] data = new byte[size];
                fis = new FileInputStream(f);
                fis.read(data, 0, size);
                if(null != fis){ fis.close();}
                if (data.length > 0){
                    result = httpPostImage(param, data);
                }
            }
        } catch(Exception e) {
            LOGGER.error("识别图片出错", e);
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    LOGGER.info(">关闭出错", e);
                }
            }
        }
        return result;
    }

    /**
     * 识别图片_按图片二进制流
     * @param codetype		图片类型
     * @param lenMin		最小位数
     * @param byteArr		图片二进制数据流
     * @return
     */
    public String recognition(String codetype, String lenMin, byte[] byteArr) {
        String result = "";
        String param = String.format("user=%s&pass=%s&softid=%s&codetype=%s&lenMin=%s", username, password, softid, codetype, lenMin);
        try {
            result = httpPostImage(param, byteArr);
        } catch(Exception e) {
            LOGGER.error("识别图片出错", e);
        }
        return result;
    }

    /**
     * 识别图片_按图片二进制流
     * @param codetype		图片类型
     * @param lenMin		最小位数
     * @param image		图片对象
     * @return
     */
    public String recognition(String codetype, String lenMin, BufferedImage image) {
        String result = "";
        String param = String.format("user=%s&pass=%s&softid=%s&codetype=%s&lenMin=%s", username, password, softid, codetype, lenMin);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
            outputStream.flush();
            byte[] byteArr = outputStream.toByteArray();
            result = httpPostImage(param, byteArr);
        } catch(Exception e) {
            LOGGER.error("识别图片出错", e);
        }finally {
            if (null != outputStream){
                try{
                    outputStream.close();
                }catch (Exception e){}
            }
        }
        return result;
    }

    /**
     * 核心上传函数
     * @param param			请求参数，如：username=test&password=1
     * @param data			图片二进制流
     * @return				response
     * @throws IOException
     */
    private String httpPostImage(String param, byte[] data) throws IOException {
        long time = (new Date()).getTime();
        StringBuffer buffer = new StringBuffer();
        HttpURLConnection con = null;
        OutputStream out = null;
        try {
            String boundary = "----------" + md5(String.valueOf(time));
            String boundarybytesString = "\r\n--" + boundary + "\r\n";
            URL u  = new URL("http://upload.chaojiying.net/Upload/Processing.php");
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(60000);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(true);
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            out = con.getOutputStream();

            for (String paramValue : param.split("[&]")) {
                out.write(boundarybytesString.getBytes("UTF-8"));
                String paramString = "Content-Disposition: form-data; name=\""
                        + paramValue.split("[=]")[0] + "\"\r\n\r\n" + paramValue.split("[=]")[1];
                out.write(paramString.getBytes("UTF-8"));
            }
            out.write(boundarybytesString.getBytes("UTF-8"));

            String paramString = "Content-Disposition: form-data; name=\"userfile\"; filename=\""
                    + "chaojiying_java.gif" + "\"\r\nContent-Type: application/octet-stream\r\n\r\n";
            out.write(paramString.getBytes("UTF-8"));
            out.write(data);

            String tailer = "\r\n--" + boundary + "--\r\n";
            out.write(tailer.getBytes("UTF-8"));

            out.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String temp;
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
                buffer.append("\n");
            }
        }catch (Exception e){
            LOGGER.error("识别图片出错", e);
        }finally {
            out.close();
            con.disconnect();
        }
        return buffer.toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
