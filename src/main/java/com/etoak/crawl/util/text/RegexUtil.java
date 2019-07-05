package com.etoak.crawl.util.text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtil {
    /**
     * 中文字母加上逗号随意组合,如:电信1区,电信2区
     */
    public static final String MULTI_WORD_CN = "([\\u4e00-\\u9fa5\\w]+,*)+";

    private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtil.class);

    private RegexUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static boolean isMatch(String regex, String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.find();
    }

    public static String getValue(String regex, String text) {
        return getValue(regex, text, 0);
    }

    /**
     * @param regex
     * @param text
     * @param group
     * @return
     */
    public static String getValue(String regex, String text, int group) {
        try {
            Matcher matcher = Pattern.compile(regex).matcher(text);
            if (matcher.find()) {
                return matcher.group(group);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
        return "";
    }

    /**
     * 返回
     *
     * @param regex
     * @param text
     * @param group
     * @return
     */
    public static String[] getValue(String regex, String text, int... group) {
        String[] val = new String[group.length];
        try {
            Matcher matcher = Pattern.compile(regex).matcher(text);
            if (matcher.find()) {
                for (int i = 0; i < group.length; i++) {
                    val[i] = matcher.group(group[i]);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return val;
    }

    /**
     * @param patternStr
     * @param source
     * @return matchValue
     */
    public static String matchValue(String patternStr, String source) {
        //处理由于系统不同导致换行符差异
        if (org.apache.commons.lang3.StringUtils.isNotBlank(patternStr)) {
            String lineSeparator = System.getProperties().getProperty("line.separator");
            String osName = System.getProperties().getProperty("os.name");
            if (!org.apache.commons.lang3.StringUtils.contains(osName, "Window")) {
                patternStr = patternStr.replace("\\r\\n", lineSeparator);
            }
        }
        List<String> resultList = new ArrayList<String>();
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                resultList.add(matcher.group(i + 1).trim());
            }
        }
        String[] result = resultList.toArray(new String[]{});
        if (result != null && result.length > 0) {
            return result[0].trim();
        }
        return "";
    }

    // 默认是第1组的所有数据
    public static List<String> getMatches(String regex, String text) {
        return getMatches(regex, text, 1);
    }

    public static List<String> getMatches(String regex, String text, int group) {
        List<String> matches = new ArrayList<String>();
        Matcher matcher = Pattern.compile(regex).matcher(text);
        while (matcher.find()) {
            matches.add(matcher.group(group));
        }
        return matches;
    }

}
