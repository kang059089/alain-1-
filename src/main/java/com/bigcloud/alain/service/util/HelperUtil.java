package com.bigcloud.alain.service.util;

import java.util.regex.Pattern;

public final class HelperUtil {

    private HelperUtil() {
    }

    public static Integer passwordState(String password) {
        // 密码以字母开头并包含字母、数字及下划线的11-18位字符则属于强密码
        Pattern ptStrong = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)(?![^_]+$).{11,18}$");
        // 密码以字母开头并包含字母、数字或下划线的8-18位字符则属于中密码
        Pattern ptMiddle = Pattern.compile("^[a-zA-Z]\\w{7,17}$");
        // 密码以字母开头并包含字母、数字或下划线的6-7位字符则属于弱密码
        Pattern ptWeak = Pattern.compile("^[a-zA-Z]\\w{5,6}$");
        if(ptStrong.matcher(password).find()) {
            return 2;
        } else if (ptMiddle.matcher(password).find()) {
            return 1;
        } else if (ptWeak.matcher(password).find()) {
            return 0;
        }
        return null;
    }
}
