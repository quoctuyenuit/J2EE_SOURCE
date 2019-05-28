package com.j2ee.j2eeproject.common;

import java.text.DecimalFormat;
import java.util.Locale;

public class Common {
	public static class Constaints {
		public static String ADMIN_PERMISION = "ADMIN";
		public static String USER_PERMISSION = "USER";	
		
		public static String kLIST_PRODUCTS = "ListProducts";
		public static String kUSER = "UserKey";
		
		public static String WEB_NAME = "Bánh tráng shopper";
		
		public static String MONEY_UNIT = " đ";
	}
	
	static public String formatMoney(Integer money) {
		DecimalFormat BE_DF = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMAN);
		return BE_DF.format(money) + Constaints.MONEY_UNIT;
	}
}
