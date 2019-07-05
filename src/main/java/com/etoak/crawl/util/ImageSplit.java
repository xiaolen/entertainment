package com.etoak.crawl.util;


import com.alibaba.fastjson.JSONArray;
import com.etoak.crawl.util.http.HttpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by liuxianxian on 2018/1/10.
 */
public class ImageSplit {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageSplit.class);

    public static byte[] splitImage(String url, String dotString, String wStr, HttpHelper httpHelper){
        try{
            LOGGER.info("开始处理识别图片");
            // 读入大图
            ByteArrayInputStream inputStream=new ByteArrayInputStream(httpHelper.goGetBytes(url));
            BufferedImage image = ImageIO.read(inputStream);

            // 分割成4*4(16)个小图
            int rows = 2;
            int cols = 15;
            int chunks = rows * cols;

            // 计算每个小图的宽度和高度
            int chunkWidth = image.getWidth() / cols;
            int chunkHeight = image.getHeight() / rows;

            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    //设置小图的大小和类型
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    //写入图像内容
                    Graphics gr = imgs[count++].getGraphics();
                    gr.drawImage(image, 0, 0,
                            chunkWidth, chunkHeight,
                            chunkWidth* y, chunkHeight * x,
                            chunkWidth * y + chunkWidth,
                            chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                }
            }


            return mergeImage(imgs,dotString,wStr);
        }catch (Exception e){
            LOGGER.error("验证码图片处理异常",e);
        }
        return new byte[0];
    }

    private static byte[] mergeImage(BufferedImage[] imgs,String dotString,String wStr) {
        try{
            LOGGER.info("开始拼接图片");
            int rows = 2;
            int cols = 15;
            String[][] dots=getDots(dotString);

            int chunkWidth, chunkHeight;
            int type;

            type = imgs[0].getType();
            chunkWidth = imgs[0].getWidth();
            chunkHeight = imgs[0].getHeight();

            //设置拼接后图的大小和类型
            BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows+30, BufferedImage.TYPE_INT_RGB);
            Graphics graphics2D=finalImg.getGraphics();

            //写入图像内容
            int num = 0;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    int dtx=Integer.parseInt(dots[num][0])/20;
                    int dty=Integer.parseInt(dots[num][1])/80;
                    int index=0;
                    if(dty>0){
                        index=15+dtx;
                    }else{
                        index=dtx;
                    }

                    graphics2D.drawImage(imgs[index], chunkWidth * j, chunkHeight * i, null);
                    num++;
                }
            }

            BufferedImage wbf=new BufferedImage(300, 30, BufferedImage.TYPE_INT_RGB);
            Graphics graph=wbf.getGraphics();
            graph.setFont(new Font("黑体", Font.BOLD,14));
            graph.setColor(new Color(255, 255, 255));
            graph.fillRect(0,0,300,30);
            graph.setColor(new Color(255, 47, 51));
            graph.drawString("顺序点击:"+wStr,20,20);

            graphics2D.drawImage(wbf, 0, 160, null);
            graphics2D.dispose();


            //输出拼接后的图像
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(finalImg, "jpeg",out);
//            ImageIO.write(finalImg, "jpeg",new FileOutputStream("d:\\img\\ad.jpeg"));
            byte[] b = out.toByteArray();
            LOGGER.info("图片处理完成");
            return b;
        }catch (Exception e){
            LOGGER.error("验证码图片拼接异常",e);
        }

        return new byte[0];
    }

    public static String[][] getDots(String dotString){
        JSONArray jsonObject= JsonUtil.parse(dotString,JSONArray.class);
        String[][] dots=new String[30][2];
        for(int i=0;i<jsonObject.size();i++){
            ArrayList<String> dlist=(ArrayList)jsonObject.get(i);
            String dotx="";
            String doty="";
            for(int n=0;n<dlist.size();n++){
                if(n==0){
                    dotx=dlist.get(n);
                    dots[i][0]=dotx;
                }else {
                    doty=dlist.get(n);
                    dots[i][1]=doty;
                }
            }


        }
        return dots;
    }

    public static   void get(){
        try{
            int imageWidth = 200;
            int imageHeight = 200;
            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            int fontSize = 100;
            Font font = new Font("楷体", Font.PLAIN, fontSize);
            graphics.setFont(font);
            graphics.setColor(new Color(246, 96, 0));
            graphics.fillRect(0, 0, imageWidth, imageHeight);
            graphics.setColor(new Color(255, 255, 255));
            int strWidth = graphics.getFontMetrics().stringWidth("好");
            graphics.drawString("好", fontSize - (strWidth / 2), fontSize + 30);
            ImageIO.write(image, "PNG", new File("D:\\img\\abc.png"));
            graphics.dispose();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


//    public static void main(String[] args) {
//        splitImage("https://o0kos1kny.qnssl.com/3/0/30b5682165e419fe671279c63a48e424.png","[[\"40\",\"0\"],[\"240\",\"80\"],[\"0\",\"0\"],[\"60\",\"0\"],[\"220\",\"80\"],[\"80\",\"0\"],[\"220\",\"0\"],[\"200\",\"0\"],[\"260\",\"0\"],[\"20\",\"0\"],[\"180\",\"80\"],[\"40\",\"80\"],[\"100\",\"0\"],[\"160\",\"0\"],[\"100\",\"80\"],[\"260\",\"80\"],[\"120\",\"0\"],[\"120\",\"80\"],[\"60\",\"80\"],[\"280\",\"80\"],[\"20\",\"80\"],[\"140\",\"0\"],[\"160\",\"80\"],[\"280\",\"0\"],[\"180\",\"0\"],[\"140\",\"80\"],[\"200\",\"80\"],[\"0\",\"80\"],[\"240\",\"0\"],[\"80\",\"80\"]]","nihao");
////         get();
//    }
}
