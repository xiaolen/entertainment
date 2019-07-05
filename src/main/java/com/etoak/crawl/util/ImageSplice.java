package com.etoak.crawl.util;


import com.etoak.crawl.util.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by liuxianxian on 2018/1/10.
 */
public class ImageSplice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageSplice.class);



    public static byte[] mergeImage(String url, int width, int height, String action, String word, HttpHelper httpHelper) {
        try{

            LOGGER.info("开始处理识别图片");
            // 读入大图
            byte[] bytes=httpHelper.goGetBytes(url);
            ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(inputStream);
            int type = image.getType();

            //设置拼接后图的大小和类型
            BufferedImage finalImg = new BufferedImage(width, height+30, BufferedImage.TYPE_INT_RGB);
            Graphics graphics2D=finalImg.getGraphics();

            graphics2D.drawImage(image,0,0,width,height,null);

            BufferedImage wbf=new BufferedImage(width, 30, BufferedImage.TYPE_INT_RGB);
            Graphics graph=wbf.getGraphics();
            graph.setFont(new Font("黑体", Font.BOLD,14));
            graph.setColor(new Color(255, 255, 255));
            graph.fillRect(0,0,width,30);
            graph.setColor(new Color(0, 0, 0));
            graph.drawString("顺序"+action+":"+word+"的中心点",20,20);

            graphics2D.drawImage(wbf, 0, height, null);
            graphics2D.dispose();


            //输出拼接后的图像
            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            FileOutputStream fileOutputStream=new FileOutputStream("d:\\img\\ad.jpeg");
            ImageIO.write(finalImg, "jpeg",out);
//            ImageIO.write(finalImg, "jpeg",fileOutputStream);
//            fileOutputStream.close();
            byte[] b = out.toByteArray();
            LOGGER.info("图片处理完成");
            return b;
        }catch (Exception e){
            LOGGER.error("验证码图片拼接异常",e);
        }

        return new byte[0];
    }

    public static byte[] mergeImages(byte[] bytes,int width,int height,String action) {
        try{
            LOGGER.info("开始处理识别图片");
            // 读入大图
            ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(inputStream);

            //设置拼接后图的大小和类型
            BufferedImage finalImg = new BufferedImage(width, height+30, BufferedImage.TYPE_INT_RGB);
            Graphics graphics2D=finalImg.getGraphics();

            graphics2D.drawImage(image,0,0,width,height,null);

            BufferedImage wbf=new BufferedImage(width, 30, BufferedImage.TYPE_INT_RGB);
            Graphics graph=wbf.getGraphics();
            graph.setFont(new Font("黑体", Font.BOLD,14));
            graph.setColor(new Color(255, 255, 255));
            graph.fillRect(0,0,width,30);
            graph.setColor(new Color(0, 0, 0));
            graph.drawString("请输入验证码图片中"+action+"的文字",20,20);

            graphics2D.drawImage(wbf, 0, height, null);
            graphics2D.dispose();

            //输出拼接后的图像
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(finalImg, "jpeg",out);
            byte[] b = out.toByteArray();
            LOGGER.info("图片处理完成");
            return b;
        }catch (Exception e){
            LOGGER.error("验证码图片拼接异常",e);
        }

        return new byte[0];
    }

}
