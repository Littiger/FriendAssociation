package com.quifeng.utils.generate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc 对手机号 、邮箱 等进行的验证
 * @author 语录
 *
 */
public class ValiUtils {

	/**
	 * @Desc 验证手机号
	 * @param phtone
	 * @return
	 */
	public static boolean isPhone(String phtone) {
		String pattern = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(phtone);
		return m.matches();
	}

	/**
	 * @Desc 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email)) {
			return false;
		}
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(regEx1);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Desc 验证长度
	 * @param str
	 * @param size
	 * @return
	 */
	public static boolean isStrSize(String str, int size) {
		if (str.length() < size) {
			return true;
		}
		return false;
	}

}
