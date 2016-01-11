package com.u4f.util;



import java.math.BigDecimal;

/**
 * 四则运算类
 *
 */
public class MyMath {
	
	// 默认保留小数点位数
	public static final int DEFAULT_SCALE = 20;


	
	public static double add(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.add(second).doubleValue();
	}
	
	/**
	 * 为一个数字创建BigDecimal对象
	 * @param number
	 * @return
	 */
	private static BigDecimal getBigDecimal(double number) {
		return new BigDecimal(number);
	}


	public static double subtract(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.subtract(second).doubleValue();
	}


	public static double multiply(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.multiply(second).doubleValue();
	}


	public static double divide(double num1, double num2) {
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.divide(second, DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}
	
	//取合理的有效位数
	public  static double take_effect_number(double number)
	{
			 BigDecimal temp =new BigDecimal(number);
			 number=temp.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
			 return number;
			 
	}

}