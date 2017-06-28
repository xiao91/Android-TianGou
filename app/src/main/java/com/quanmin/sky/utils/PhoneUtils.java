package com.quanmin.sky.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号是否合法
 * Created by xiao on 2017/6/13.
 */

public class PhoneUtils {

    /**
     * 判断输入的是否是手机号
     * 移动：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188
     * 联通：130、131、132、155、156、185、186
     * 电信：133、153、180、189、181
     * @param phone 手机号
     * @return false 否
     */
    public static boolean isMobile(String phone) {
        Pattern p = Pattern.compile("^((13\\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\\d{8}$)|(18[0,1,2,5,6,7,8,9]\\d{8}$)|(147\\d{8})$)");
        // 正则表达式
        Matcher m = p.matcher(phone);
        return m.matches();
    }

}
