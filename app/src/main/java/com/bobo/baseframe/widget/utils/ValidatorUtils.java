package com.bobo.baseframe.widget.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证
 */
public class ValidatorUtils {


    // 提取网址硬编码 https://www.jianshu.com/p/ba6b97c1415f
    private static final String[] ext = {
            "top", "com.cn", "com", "con", "net", "org", "edu", "gov", "int", "mil", "cn", "tel", "biz", "cc", "tv", "info",
            "name", "hk", "mobi", "asia", "cd", "travel", "pro", "museum", "coop", "aero", "ad", "ae", "af",
            "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "az", "ba", "bb", "bd",
            "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz",
            "ca", "cc", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cq", "cr", "cu", "cv", "cx",
            "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "eh", "es", "et", "ev", "fi",
            "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gh", "gi", "gl", "gm", "gn", "gp",
            "gr", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "in", "io",
            "iq", "ir", "is", "it", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw",
            "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md",
            "mg", "mh", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mv", "mw", "mx", "my", "mz",
            "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nt", "nu", "nz", "om", "qa", "pa",
            "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "pt", "pw", "py", "re", "ro", "ru", "rw",
            "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st",
            "su", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tm", "tn", "to", "tp", "tr", "tt",
            "tv", "tw", "tz", "ua", "ug", "uk", "us", "uy", "va", "vc", "ve", "vg", "vn", "vu", "wf", "ws",
            "ye", "yu", "za", "zm", "zr", "zw"
    };

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < ext.length; i++) {
            sb.append(ext[i]);
            sb.append("|");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        // final pattern str
        String pattern = "((https?|s?ftp|irc[6s]?|git|afp|telnet|smb)://)?((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|((www\\.|[a-zA-Z\\.\\-]+\\.)?[a-zA-Z0-9\\-]+\\." + sb.toString() + "(:[0-9]{1,5})?))((/[a-zA-Z0-9\\./,;\\?'\\+&%\\$#=~_\\-]*)|([^\\u4e00-\\u9fa5\\s0-9a-zA-Z\\./,;\\?'\\+&%\\$#=~_\\-]*))";
        // Log.v(TAG, "pattern = " + pattern);
        WEB_URL = Pattern.compile(pattern);
    }

    public static final Pattern WEB_URL;

    public static final int INVALID_INT = 0;


    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     * 2 种及以上
     */
    public static final String REGEX_PASSWORD = "^(?![A-Z]*$)(?![a-z]*$)(?![0-9]*$)(?![^a-zA-Z0-9]*$)\\S{8,12}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1[3456789]\\d{9}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]+";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

    /**
     * 正则表达式：验证base64
     */
    public static final String REGEX_BASE64 = "^data:image/(png|jpg|jpeg|gif|bmp);base64,.*";

    /**
     * 正则表达式: 验证是否纯数字
     */
    public static final String REGEX_NUMBER = "^\\d+$";


    /**
     * 校验用户名
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验手机号
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 去空格
     */
    public static String strTrim(String str) {
        String replaceStr="";
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        return m.replaceAll(replaceStr);
    }

    /**
     * 校验身份证
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验URL
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    /**
     * 校验IP地址
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }

    /**
     * 验证base64
     */
    public static boolean isBase64(String base64) {
        return Pattern.matches(REGEX_BASE64, base64);
    }

    /**
     * 验证是否纯数字
     */
    public static boolean isNumber(String number) {
        return Pattern.matches(REGEX_NUMBER, number);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(final CharSequence cs) {
        if (isNull(cs) || (cs.toString().trim().length()) == 0) {
            return true;
        }
        for (int i = 0; i < cs.toString().length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || "".equals(obj);
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmptyList(List list) {
        return !isEmptyList(list);
    }

    public static List<String> getWebUrl(String content) {
        List<String> result = new ArrayList<>();
        try {
            Matcher matcher = WEB_URL.matcher(content.toLowerCase());
            for (int i = 0; matcher != null
                    && matcher.find()
                    && i < matcher.groupCount(); i++) {
                String link = matcher.group(i);
                result.add(link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
