package com.zjw.myframe.web.formatter;

import com.zjw.myframe.common.enums.fomat.DateFormatEnum;
import com.zjw.myframe.common.exception.ParamException;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间类型的转化
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class DateFormatter implements Formatter<Date> {

    @Override
    public String print(Date object, Locale locale){
        return null;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException{
        Date date = null;
        for (DateFormatEnum format : DateFormatEnum.values()){
            try{
                date = format.getSimpleDateFormat().parse(text);
            }catch(Exception e){
                continue;
            }
        }
        if(date == null){
            throw new ParamException("时间类型格式错误");
        }
        return date;
    }

}
