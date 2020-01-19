package com.jgybzx.web.controller.converter;


import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyStringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(source);
        } catch (ParseException e) {
            //e.printStackTrace();
            System.out.println("日期转换失败");
        }

        return date;
    }
}
