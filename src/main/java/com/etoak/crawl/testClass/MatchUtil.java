package com.etoak.crawl.testClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {

    private static final Logger logger= LoggerFactory.getLogger(MatchUtil.class);

    public static void main(String[] args) {
        String text = "吾辈既务斯业009090，便当3333专心用功";

        String reg1 = "[\\d]";
        String reg2 = "[^\\d{6}]";

        String reg3 = "(?<![a-zA-Z0-9])([a-zA-Z0-9]{6})(?![a-zA-Z0-9])";
        Pattern p = Pattern.compile(reg1);
        Matcher matcher = p.matcher(text);

        logger.info("将所有数字替换，即提取字符串中非数字,替换后：{}",matcher.replaceAll(""));

        p=Pattern.compile(reg2);
        logger.info("将所有非数字替换，即提取字符串中数字，替换后：{}",p.matcher(text).replaceAll(""));
        p=Pattern.compile(reg3);
        logger.info("将所有非数字替换，即提取字符串中数字，替换后：{}",p.matcher(text).replaceAll(""));
    }

}


