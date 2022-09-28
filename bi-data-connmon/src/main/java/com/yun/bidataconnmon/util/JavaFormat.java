package com.yun.bidataconnmon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yun
 */
public class JavaFormat {
    private static Pattern HUMP = Pattern.compile("[A-Z]");
    private static Pattern UNDERLINE = Pattern.compile("_[a-z]");

    /**
     * @param str
     * @return java.lang.String
     * @author Howe
     * @Description 将驼峰转为下划线
     * @Date 2022/4/22 13:11
     * @since 1.0.0
     */
    public static String javaToMysql(String str) {
        Matcher matcher = HUMP.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * @param str
     * @return java.lang.String
     * @author Howe
     * @Description 将下划线转为驼峰
     * @Date 2022/4/22 13:12
     * @since 1.0.0
     */
    public static String mysqlToJava(String str) {
        str = str.toLowerCase();
        Matcher matcher = UNDERLINE.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(0).toUpperCase().replace("_", ""));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
