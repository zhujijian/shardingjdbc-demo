package com.snowalker.shardingjdbc.snowalker.demo.util.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Lenovo
 * @Title: DesensitizedUtils
 * @Description: 脱敏工具类
 * @date 2021/10/22
 */
public class DesensitizedUtils {

	// 私有构造方法
	private DesensitizedUtils() {
	}


	// 手机号码前三后四脱敏
	public static String mobileDesensitized(String mobile) {
		if (StringUtils.isBlank(mobile) || (mobile.length() != 11)) {
			return mobile;
		}
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})" , "$1****$2");
	}

	/**
	 * 校验银行卡号方法
	 * @param bankCard
	 * @return
	 */
	public static boolean checkBankCard(String bankCard) {
		if(bankCard.length() < 15 || bankCard.length() > 19) {
			return false;
		}
		char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
		if(bit == 'N'){
			return false;
		}
		return bankCard.charAt(bankCard.length() - 1) == bit;
	}


	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * @param nonCheckCodeBankCard
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeBankCard){
		if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
				|| !nonCheckCodeBankCard.matches("\\d+")) {
			//如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeBankCard.trim().toCharArray();
		int luhmSum = 0;
		for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if(j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
	}



}
