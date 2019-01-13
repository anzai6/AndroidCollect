package com.android.collect.library.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param num1 被加数
	 * @param num2 加数
	 * @return num1 + num2
	 */
	public static String add(String num1, String num2) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		return b1.add(b2).toString();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param num1 被减数
	 * @param num2 减数
	 * @return num1 - num2
	 */
	public static String subtract(String num1, String num2) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		return b1.subtract(b2).toString();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param num1 被乘数
	 * @param num2 乘数
	 * @return num1 * num2
	 */
	public static String multiply(String num1, String num2) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		return b1.multiply(b2).toString();
	}

	/**
	 * 提供精确的除法运算，结果是四舍五入后的（BigDecimal.ROUND_HALF_UP）
	 * 
	 * @param num1 被除数
	 * @param num2 乘除
	 * @return num1 除 num2
	 */
	public static String divide(String num1, String num2) {
		return divide(num1, num2, 2);
	}

	/**
	 * 提供精确的除法运算，结果是四舍五入后的（BigDecimal.ROUND_HALF_UP）
	 * 
	 * @param num1 被除数
	 * @param num2 乘除
	 * @param scale 保留小数的位数
	 * @return num1 除 num2
	 */
	public static String divide(String num1, String num2, int scale) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 比较大小，num1 > num2返回true
	 */
	public static boolean BigThan(String num1, String num2) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		// -1 小于 0 等于 1 大于
		if (b1.compareTo(b2) == 1)
			return true;
		return false;
	}
	
	/**
	 * 比较大小，num1 = num2返回true
	 */
	public static boolean isEqual(String num1, String num2) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		// -1 小于 0 等于 1 大于
		if (b1.compareTo(b2) == 0)
			return true;
		return false;
	}
	
	/**
	 * 比较大小，num1 < num2返回true
	 */
	public static boolean SmallThan(String num1, String num2) {
		BigDecimal b1 = new BigDecimal(num1);
		BigDecimal b2 = new BigDecimal(num2);
		// -1 小于 0 等于 1 大于
		if (b1.compareTo(b2) == -1)
			return true;
		return false;
	}

}
