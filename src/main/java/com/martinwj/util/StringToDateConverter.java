package com.martinwj.util;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: StringToDateConverter
 * @Description: TODO 把字符串转换日期
 * @author: martin-wj
 * @createDate: 2020-12-01
 */
public class StringToDateConverter implements Converter<String, Date> {

    /**
     * @param s 传入进来字符串
     * @return
     */
    @Override
    public Date convert(String s) {
        // 判断
        if(s == null){
            throw new RuntimeException("请您传入数据");
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // 把字符串转换日期
            return df.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("数据类型转换出现错误");
        }
    }
}
