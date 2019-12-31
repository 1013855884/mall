package com.school.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    private BigDecimalUtil(){

    }

    public static BigDecimal add(double v1 ,double v2){
        BigDecimal d1 = new BigDecimal(v1);
        BigDecimal d2 = new BigDecimal(v2);
        return d1.add(d2);
    }
    public static BigDecimal sub(double v1 ,double v2){
        BigDecimal d1 = new BigDecimal(v1);
        BigDecimal d2 = new BigDecimal(v2);
        return d1.subtract(d2);
    }
    public static BigDecimal mul(double v1 ,double v2){
        BigDecimal d1 = new BigDecimal(v1);
        BigDecimal d2 = new BigDecimal(v2);
        return d1.multiply(d2);
    }
    public static BigDecimal div(double v1 ,double v2){
        BigDecimal d1 = new BigDecimal(v1);
        BigDecimal d2 = new BigDecimal(v2);
        return d1.divide(d2,2,BigDecimal.ROUND_HALF_UP);//四舍五入保留两位小数
        //除不尽的情况
    }
}
