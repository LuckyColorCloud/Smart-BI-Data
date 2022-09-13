package com.yun.bidataconnmon.util;

import java.util.regex.Pattern;

/**
 * 一些正则 匹配 等....
 * @author Yun
 */
public class Regular {
    /**
     * 敏感sql操作
     */
    public static final String REG_SENSITIVE_SQL = "drop\\s|alter\\s|grant\\s|insert\\s|replace\\s|delete\\s|truncate\\s|update\\s|remove\\s";
    public static final Pattern PATTERN_SENSITIVE_SQL = Pattern.compile(REG_SENSITIVE_SQL);
}
