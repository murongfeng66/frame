package com.zjw.myframe.common.enums.fomat;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * 字符串正则表达式验证枚举类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum StringFomatEnum {
    /**
     * 中文
     */
    CHNIESE("^[\u4e00-\u9fa5]{0,}$", "中文"),
    /**
     * 身份证号
     */
    ID_CARD("^[0-9]{10}([0][1-9]|[1][0-2])([0][1-9]|[12][0-9]|30|31)[0-9]{3}[0-9xX]$", "18位身份证号"),
    /**
     * IP
     */
    IP("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)", "IP"),
    /**
     * 强密码<p>
     * 6至20位<p>
     * 大写英文、小写英文、数字、（,.?_）
     */
    PASSWORD_STRONG("^(?=.*[0-9])(?=.*[,.?_])(?=.*[a-z])(?=.*[A-Z]).{6,20}$", "包含大写英文、小写英文、数字、（,.?_），且6至20位"),
    /**
     * 弱密码<p>
     * 6至20位<p>
     * 英文、数字、下划线
     */
    PASSWORD_WEAK("^[A-Za-z0-9_]{6,20}+$", "只能包含英文、数字、下划线，且6至20位"),
    /**
     * 账号<p>
     * 英文开头、6至20位<p>
     * 英文、数字、下划线
     */
    USERNAME("^[A-Za-z][A-Za-z0-9_]{5,19}+$", "只能包含英文、数字、下划线，且6至20位"),
    /**
     * HTML标签（缺省）
     */
    HTML_TAG("", "HTML标签");

    /**
     * 正则匹配器
     */
    private Pattern pattern;

    /**
     * 正则表达式
     */
    private String regular;

    /**
     * 正则描述
     */
    private String description;

    StringFomatEnum(String regular, String description) {
        this.regular = regular;
        this.description = description;
        this.pattern = Pattern.compile(regular);
    }

    /**
     * {@linkplain #description}
     */
    public String getDescription() {
        return StringUtils.isEmpty(description) ? regular : description;
    }

    /**
     * {@linkplain #pattern}
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * {@linkplain #regular}
     */
    public String getRegular() {
        return regular;
    }

    /**
     * 验证字符串是否符合正则表达式
     *
     * @param str 验证字符串
     * @return 验证结果
     */
    public boolean match(String str) {
        return !StringUtils.isEmpty(str) && this.getPattern().matcher(str).matches();
    }
}
