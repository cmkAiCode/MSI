package cn.fanrunqi.materiallogin.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cmk on 2018/4/18.
 * 用于验证输入是否正确的工具类
 */

public class ValidateInput {

    /**
     * 验证手机格式是否正确
     * @param phone 传入手机号作为参数
     * @return 验证结果
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {//手机号应为11位数
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    /**
     * 判断字符串是否为空
     * @param text
     * @return 为空返回true  不为空返回false
     */
    public static boolean isEmpty(String text){
        if("".equals(text)){
            return true;
        }
        return false;
    }


}
