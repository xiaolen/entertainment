package com.etoak.crawl.util.text;


public class ReplaceUtil {

    private ReplaceUtil(){
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 替换省份后缀
     *
     * @param province
     * @return
     */
    public static String replaceRegionSuffix(String province) {
        return StringUtil.isEmpty(province) ? "" : province.replace("省", "").replace("市", "");
    }

}
