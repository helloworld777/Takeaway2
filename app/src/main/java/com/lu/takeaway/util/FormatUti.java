package com.lu.takeaway.util;

import java.text.DecimalFormat;

public class FormatUti {
	public static String formatNumber(double d) {
		final DecimalFormat decimalFormat = new DecimalFormat("#.0");
		return decimalFormat.format(d);
	}
}
