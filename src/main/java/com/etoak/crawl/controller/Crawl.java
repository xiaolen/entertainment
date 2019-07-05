package com.etoak.crawl.controller;

import com.alibaba.fastjson.JSON;
import com.etoak.crawl.model.HouseIinformation;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;


/**
 * @Author: 秦渊渊
 * @Date: 2018/11/6 9:02
 */
public class Crawl {
    public static void main(String[] args) {



        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 取消 CSS 支持 ✔
        webClient.getOptions().setCssEnabled(false);
        // 取消 JavaScript支持 ✔
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = null;
        try {
            // 解析获取页面
            page = webClient.getPage("https://ty.58.com/zufang/36005877618187x.shtml?psid=129429147202037227733256529&ClickID=4&cookie=||https://www.baidu.com/link?url=sLK-EtHXc08ymKsI2282WjI_vlCJrYvM2pQPdjfsHkq&wd=&eqid=dcc9919100000599000000025be03067|c5/njVvgMG5LxB1dAy22Ag==&PGTID=0d3090a7-002e-49af-0c37-ab623b4c83a0&apptype=0&entinfo=36005877618187_0&fzbref=0&iuType=gz_2&key=&pubid=48088460&params=rankbusitime0099^desc&local=740&trackkey=36005877618187_b6c9a2d9-e761-492c-97ec-83952200bf19_20181105205029_1541422229173&fcinfotype=gz");
//            DomNodeList<DomNode> spanList = page.querySelectorAll(".c_ff552e");
            //房屋价格
            HouseIinformation houseIinformation = new HouseIinformation();
            DomNode domNode = page.querySelector(".f36");
            houseIinformation.setPrice(Integer.valueOf(domNode.asText()));
            //租房方式
            DomText rentingStyle = (DomText) page.getByXPath("//*[@class=\"f14\"]/li[1]/span[2]/text()").get(0);
            houseIinformation.setRentingStyle(rentingStyle.toString());
            //租房类型
            DomText rentingType = (DomText) page.getByXPath("//*[@class=\"f14\"]/li[2]/span[2]/text()").get(0);
            houseIinformation.setRentingType(rentingType.toString());
//            DomNode domNode1 = page.querySelector(".strongbox");
            //朝向楼层
            DomText towardheTfloor = (DomText) page.getByXPath("//*[@class=\"f14\"]/li[3]/span[2]/text()").get(0);
            houseIinformation.setTowardheTfloor(towardheTfloor.toString());
            //所在小区
            DomText residentialQuarters = (DomText) page.getByXPath("//*[@class=\"f14\"]/li[4]/span[2]/a[1]/text()").get(0);
            houseIinformation.setResidentialQuarters(residentialQuarters.toString());
            //地址
            DomText address = (DomText) page.getByXPath("//*[@class=\"f14\"]/li[5]/span[2]/a[1]/text()").get(0);
            DomText address1 = (DomText) page.getByXPath("//*[@class=\"f14\"]/li[5]/span[2]/a[2]/text()").get(0);
//            System.out.println(address.toString()+address1.toString());
            String addresss = address.toString()+address1.toString();
            houseIinformation.setAddress(addresss);
            //房屋亮点
            DomText brightSpot = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[1]/span[2]/em[1]/text()").get(0);
            DomText brightSpot2 = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[1]/span[2]/em[2]/text()").get(0);
            houseIinformation.setBrightSpot(brightSpot.toString()+brightSpot2.toString());

            //房屋描述
            DomText description = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[2]/span[2]/span/text()").get(0);
            DomText description1 = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[2]/span[2]/p[1]/span/text()").get(0);
            DomText description2 = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[2]/span[2]/p[2]/span/text()").get(0);
            DomText description3 = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[2]/span[2]/p[3]/span/text()").get(0);
            DomText description4 = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[2]/span[2]/p[4]/span/text()").get(0);
            DomText description5 = (DomText) page.getByXPath("//*[@class=\"introduce-item\"]/li[2]/span[2]/p[5]/span/text()").get(0);

            String descriptions = description.toString()+description1.toString()+description2.toString()+description3.toString()+description4.toString()+description5.toString();
            houseIinformation.setDescription(descriptions);

            //小区详情
            DomText detailsOfTheCommunity1 = (DomText) page.getByXPath("//*[@class=\"district-info-list c_333 f14 lh28\"]/li[1]/span[2]/text()").get(0);
            DomText detailsOfTheCommunity2 = (DomText) page.getByXPath("//*[@class=\"district-info-list c_333 f14 lh28\"]/li[2]/span[2]/text()").get(0);
            DomText detailsOfTheCommunity3 = (DomText) page.getByXPath("//*[@class=\"district-info-list c_333 f14 lh28\"]/li[3]/span[2]/text()").get(0);
            String detailsOfTheCommunitys = detailsOfTheCommunity1.toString()+detailsOfTheCommunity2+detailsOfTheCommunity3+addresss;
            houseIinformation.setDetailsOfTheCommunity(detailsOfTheCommunitys);


            //联系电话
//            /html/body/div[5]/div[2]/div[2]/div[2]/div[1]/p[3]
            DomText phone = (DomText) page.getByXPath("//*[@class=\"house-chat-phonenum\"]/p[3]/text()").get(0);
            houseIinformation.setPhone(phone.toString());
//            System.out.println(houseIinformation.toString());

            String toJSONString = JSON.toJSONString(houseIinformation);
            System.out.println(toJSONString);

        } catch (IOException e) {
            e.printStackTrace();
        }
        webClient.close(); // 关闭客户端，释放内存
    }
}