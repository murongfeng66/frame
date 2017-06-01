package com.zjw.myframe.common.enums.fomat;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * 数值正则表达式验证枚举类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum NumericalFomatEnum {
    /**
     * 小数<p>
     * （正小数 + 负小数 + 0）
     */
    DECIMAL("^-?([1-9]\\d*\\.\\d*[1-9]|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$", "小数，包含0"),
    /**
     * 小数<p>
     * （正小数 + 负小数）
     */
    DECIMAL_WITHOUT_0("^-?([1-9]\\d*\\.\\d*[1-9]|0\\.\\d*[1-9]\\d*)$", "小数，不包含0"),
    /**
     * 负小数
     */
    DECIMAL_NEGATIVE("^-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)$", "负小数"),
    /**
     * 非负小数<p>
     * （正小数  + 0）
     */
    DECIMAL_NONENGATIVE("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$", "非负小数"),
    /**
     * 非正小数<p>
     * （负小数 + 0）
     */
    DECIMAL_NONPOSITIVE("^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$", "非正小数"),
    /**
     * 正小数
     */
    DECIMAL_POSITIVE("^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$", "正小数"),
    /**
     * 整数<p>
     * （正整数 + 负整数 + 0）
     */
    INTEGER("^-?[0-9]\\d*$", "整数，包含0"),
    /**
     * 整数<p>
     * （正整数 + 负整数）
     */
    INTEGER_WITHOUT_0("^-?[1-9]\\d*$", "整数，不包含0"),
    /**
     * 负整数
     */
    INTEGER_NEGATIVE("^-[1-9]\\d*$", "负整数"),
    /**
     * 非负整数<p>
     * （正整数  + 0）
     */
    INTEGER_NONENGATIVE("^[1-9]\\d*|0$", "非负整数"),
    /**
     * 非正整数<p>
     * （负整数 + 0）
     */
    INTEGER_NONPOSITIVE("^-[1-9]\\d*|0$", "非正整数"),
    /**
     * 正整数
     */
    INTEGER_POSITIVE("^[1-9]\\d*$", "正整数"),
    /**
     * 数值<p>
     * 包含：<p>
     * 3 （整数）<p>
     * 3.14 （小数）<p>
     * +3.14 （带有＋标识数字）<p>
     * -2.5 （带有－标识数字）<p>
     * 128,234 （会计计数法）<p>
     * 1.9e10 （科学计数法）<p>
     */
    NUMBER("^[+-]?\\d+(,\\d+)*(.\\d+(e\\d+)?)?$", "数值"),
    /**
     * 科学计数法
     */
    SCIENTIFIC_NOTATION("^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$", "科学计数法");

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

    NumericalFomatEnum(String regular, String description) {
        this.regular = regular;
        this.description = description;
        this.pattern = Pattern.compile(regular);
    }

    /**
     * 验证精度
     *
     * @param str       验证字符串
     * @param precision 精度
     * @return 验证结果
     */
    public static boolean matchPrecision(String str, int precision) {
        return str != null && str.contains(".") && str.substring(str.indexOf('.') + 1).length() == precision;
    }

    /**
     * {@linkplain #description}
     */
    public String getDescription() {
        return description;
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

    /**
     * 验证字符串是否符合正则表达式
     *
     * @param str       验证字符串
     * @param precision 精度
     * @return 验证结果
     */
    public boolean match(String str, int precision) {
        return match(str) && matchPrecision(str, precision);
    }
}
