package com.zjw.myframe.web.test.main;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MAIN方法测试类
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年7月28日
 */
public class test {

    public static void main(String[] args) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ahh:mm:ss");
        System.out.println(sdf.format(new Date()));
    }

}
