package com.infervision.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/8/12 21:17
 * @Version 1.0
 */
public class StringUtils {

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
