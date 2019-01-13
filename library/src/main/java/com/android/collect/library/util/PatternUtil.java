package com.android.collect.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式类
 * 
 * @author anzai
 * 
 */
public class PatternUtil {

	/**
	 * 是否是邮箱格式
	 */
	public static boolean isEmail(String s) {
		/*
		 * String a = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+"; String b =
		 * "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)"; String c =
		 * "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"; String f =
		 * "/^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$/";
		 */
		Pattern pattern = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

	/**
	 * 是否是中文
	 */
	public static boolean isChinese(String s) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+$");
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

	/**
	 * 判断手是否是字母，不分大小写
	 */
	public static boolean isLetter(String str) {
		Pattern p = Pattern.compile("^[A-Za-z]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断手机格式是否正确
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	/**
	 * 判断是否全是数字
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 字母和中文的正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCharAndCH(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z\\u4e00-\\u9fa5]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 字母、数字和中文的正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumAndCharAndCH(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\u4e00-\\u9fa5]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 字母和数字正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumAndChar(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 数字和中文的正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumAndCH(String str) {
		Pattern pattern = Pattern.compile("^[0-9\\u4e00-\\u9fa5]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否含有特殊字符
	 * 
	 * @param content
	 * @return
	 */
	public static boolean hasSpecialChar(String content) {
		boolean flag = false;
		String regEx = "([\u4e00-\u9fa50-9A-Za-z]*)";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		if (!m.matches())
			flag = true;
		return flag;
	}

}
