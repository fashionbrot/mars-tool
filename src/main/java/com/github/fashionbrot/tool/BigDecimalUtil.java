package com.github.fashionbrot.tool;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class BigDecimalUtil {

    private BigDecimalUtil(){

    }


    public static BigDecimal formatBigDecimal(Object object) {
        if (object == null) {
            return BigDecimal.ZERO;
        } else {
            try {
                return new BigDecimal(object.toString());
            } catch (Exception var2) {
                return BigDecimal.ZERO;
            }
        }
    }

    public static BigDecimal formatBigDecimal(Long value) {
        if (value == null) {
            return BigDecimal.ZERO;
        } else {
            try {
                return new BigDecimal(value.toString() + "");
            } catch (Exception var2) {
                return BigDecimal.ZERO;
            }
        }
    }

    public static BigDecimal formatBigDecimal(Double value) {
        if (value == null) {
            return BigDecimal.ZERO;
        } else {
            try {
                return new BigDecimal(value + "");
            } catch (Exception var2) {
                return BigDecimal.ZERO;
            }
        }
    }

    public static BigDecimal formatBigDecimal(String value) {
        if (ObjectUtil.isEmpty(value)) {
            return BigDecimal.ZERO;
        } else {
            try {
                return new BigDecimal(value);
            } catch (Exception var2) {
                return BigDecimal.ZERO;
            }
        }
    }

    /**
     * 减法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal subtract(BigDecimal a1,BigDecimal b2){
        if (a1==null){
            a1 = BigDecimal.ZERO;
        }
        if (b2==null){
            b2 = BigDecimal.ZERO;
        }
        return a1.subtract(b2);
    }

    /**
     * 减法
     * @param a1
     * @param b2
     * @return
     */
    public static double subtractDouble(Double a1,Double b2){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return subtract(a,b).doubleValue();
    }


    /**
     * 乘法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal multiply(BigDecimal a1,BigDecimal b2){
        if (a1==null){
            a1 = BigDecimal.ZERO;
        }
        if (b2==null){
            b2 = BigDecimal.ZERO;
        }
        return a1.multiply(b2).setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * 乘法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal multiply(BigDecimal a1,BigDecimal b2,int scale){
        if (a1==null){
            a1 = BigDecimal.ZERO;
        }
        if (b2==null){
            b2 = BigDecimal.ZERO;
        }
        return a1.multiply(b2).setScale(scale,RoundingMode.HALF_UP);
    }


    /**
     * 乘法
     * @param a1
     * @param b2
     * @return
     */
    public static double multiplyDouble(Double a1,Double b2){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return multiply(a,b).setScale(6,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 乘法
     * @param a1
     * @param b2
     * @return
     */
    public static double multiplyDouble(Double a1,Double b2,int scale){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return multiply(a,b).setScale(scale,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 乘法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal multiply(Double a1,Double b2){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return multiply(a,b);
    }

    /**
     * 加法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal add(BigDecimal a1,BigDecimal b2){
        if (a1==null){
            a1 = BigDecimal.ZERO;
        }
        if (b2==null){
            b2 = BigDecimal.ZERO;
        }
        return a1.add(b2);
    }

    /**
     * 加法
     * @param a1
     * @param b2
     * @return
     */
    public static double addDouble(Double a1,Double b2){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return add(a,b).doubleValue();
    }

    /**
     * 加法
     * @param a1 数组
     * @return
     */
    public static double addDouble(Double... a1 ){
        if (a1==null || a1.length==0){
            return 0D;
        }
        BigDecimal a =BigDecimal.ZERO;
        for(Double d: a1){
            a = a.add(formatBigDecimal(d));
        }
        return a.doubleValue();
    }

    /**
     * 加法
     * @param a1 数组
     * @return
     */
    public static BigDecimal addBigDecimal(BigDecimal... a1 ){
        BigDecimal a =BigDecimal.ZERO;
        if (a1==null || a1.length==0){
            return a;
        }
        for(BigDecimal d: a1){
            a = a.add(d);
        }
        return a;
    }

    public static double avgDouble(Double... a1 ){
        if (a1==null || a1.length==0){
            return 0D;
        }
        BigDecimal a =BigDecimal.ZERO;
        for(Double d: a1){
            a = a.add(formatBigDecimal(d));
        }
        BigDecimal b=new BigDecimal(a1.length+"");

        return divide(a,b).doubleValue();
    }

    /**
     * 除法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal divide(BigDecimal a1,BigDecimal b2){
        if (a1==null){
            a1 = BigDecimal.ZERO;
        }
        if (b2==null){
            b2 = BigDecimal.ZERO;
        }
        if (a1.doubleValue()==0 || b2.doubleValue()==0){
            return BigDecimal.ZERO;
        }
        return a1.divide(b2,6, RoundingMode.HALF_UP);
    }

    /**
     * 除法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal divide(Double a1,Double b2){
        BigDecimal a = a1==null? BigDecimal.ZERO:new BigDecimal(a1.doubleValue()+"");
        BigDecimal b = b2==null? BigDecimal.ZERO:new BigDecimal(b2.doubleValue()+"");
        if (a.doubleValue()==0 || b.doubleValue()==0){
            return BigDecimal.ZERO;
        }
        return a.divide(b,6, RoundingMode.HALF_UP);
    }
    /**
     * 除法
     * @param a1
     * @param b2
     * @return
     */
    public static BigDecimal divide(BigDecimal a1,BigDecimal b2,int scale,RoundingMode roundingMode){
        if (a1==null){
            a1 = BigDecimal.ZERO;
        }
        if (b2==null){
            b2 = BigDecimal.ZERO;
        }
        if (a1.doubleValue()==0 || b2.doubleValue()==0){
            return BigDecimal.ZERO;
        }
        return a1.divide(b2,scale, roundingMode);
    }


    /**
     * 除法
     * @param a1
     * @param b2
     * @return
     */
    public static double divideDouble(Double a1,Double b2){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return divide(a,b,6,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 除法
     * @param a1
     * @param b2
     * @return
     */
    public static double divideDouble(Double a1,Double b2,int scale){
        BigDecimal a = formatBigDecimal(a1);
        BigDecimal b= formatBigDecimal(b2);
        return divide(a,b,scale,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 格式化 Double 小数点
     * @param d
     * @param scale
     * @return double
     */
    public static double formatDouble(Object d,int scale){
        if (d==null){
            return 0.00D;
        }
        BigDecimal v  = formatBigDecimal(d);
        return v.setScale(scale,RoundingMode.HALF_UP).doubleValue();
    }
    /**
     * 格式化 Double 小数点
     * @param d
     * @param scale
     * @return double
     */
    public static double formatDouble(Double d,int scale){
        if (d==null){
            return 0.00D;
        }
        BigDecimal v  = formatBigDecimal(d);
        return v.setScale(scale,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 格式化 BigDecimal 小数点
     * @param d
     * @return double
     */
    public static double formatDouble(BigDecimal d){
        if (d==null){
            return 0.00D;
        }
        return d.doubleValue();
    }

    /**
     * 格式化 BigDecimal 小数点
     * @param d
     * @param scale
     * @return double
     */
    public static double formatDouble(BigDecimal d,int scale){
        if (d==null){
            return 0.00D;
        }
        return d.setScale(scale,RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 格式化 BigDecimal 小数点
     * @param d
     * @param scale
     * @return BigDecimal
     */
    public static BigDecimal formatBigDecimal(BigDecimal d,int scale){
        if (d==null){
            return BigDecimal.ZERO;
        }
        return d.setScale(scale,RoundingMode.HALF_UP);
    }

    /**
     * 格式化 BigDecimal 小数点
     * @param d
     * @param scale
     * @return BigDecimal
     */
    public static BigDecimal formatBigDecimal(Double d,int scale){
        if (d==null){
            return BigDecimal.ZERO;
        }
        BigDecimal v  = formatBigDecimal(d);
        return v.setScale(scale,RoundingMode.HALF_UP);
    }

    /**
     * 格式化 String 小数点
     * @param d
     * @param scale
     * @return BigDecimal
     */
    public static BigDecimal formatBigDecimal(String d,int scale){
        if (ObjectUtil.isEmpty(d)){
            return BigDecimal.ZERO;
        }
        BigDecimal v  = formatBigDecimal(d);
        return v.setScale(scale,RoundingMode.HALF_UP);
    }

    /**
     * 格式化 String 小数点
     * @param d
     * @param scale
     * @return BigDecimal
     */
    public static double formatString(String d,int scale){
        if (ObjectUtil.isEmpty(d)){
            return 0.00D;
        }
        BigDecimal v  = formatBigDecimal(d);
        return v.setScale(scale,RoundingMode.HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(divideDouble(1161.33D,3.498,1));
    }

}
