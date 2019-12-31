package com.school.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {

    @Test
    public void test(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
        System.out.println(123.3/100);
    }

    @Test
    public void test2(){
        BigDecimal decimal = new BigDecimal(0.05);
        BigDecimal decimal1 = new BigDecimal(0.01);
        System.out.println(decimal.add(decimal1));
    }

    @Test
    public void test3(){
        BigDecimal decimal = new BigDecimal("0.05");
        BigDecimal decimal1 = new BigDecimal("0.01");
        System.out.println(decimal.add(decimal1));
    }

    public void test4(){

    }
}
