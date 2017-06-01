package com.zjw.myframe.common.enums.fomat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式常量枚举类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum DateFormatEnum {

    /**
     * 日期时间——默认格式
     */
    DATETIME_FOMAT_DEFAULT("yyyy-MM-dd HH:mm:ss"),
    /**
     * 日期——默认格式
     */
    DATE_FOMAT_DEFAULT("yyyy-MM-dd"),
    /**
     * 时间——24小时制
     */
    TIME_FOMAT_24("HH:mm:ss"),
    /**
     * 日期时间——中国
     */
    DATETIME_FOMAT_CHINESE("yyyy年MM月dd日 HH:mm:ss"),
    /**
     * 日期——中国
     */
    DATE_FOMAT_CHINESE("yyyy年MM月dd日"),
    /**
     * 时间——12小时制
     */
    TIME_FOMAT_12("ahh:mm:ss");

    /**
     * 表达式
     */
    private String format;
    private SimpleDateFormat simpleDateFormat;

    DateFormatEnum(String format) {
        this.format = format;
        this.simpleDateFormat = new SimpleDateFormat(format);
    }

    public String fomatDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return this.simpleDateFormat.format(date);
    }

    public String fomatDateTime(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(this.format, locale).format(date);
    }

    public String fomatNow() {
        return fomatDateTime(new Date());
    }

    /**
     * {@linkplain #format}
     */
    public String getFormat() {
        return format;
    }

    /**
     * {@linkplain #simpleDateFormat}
     */
    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }
}
