package com.fsti.fjdicClient.util;

import java.math.BigDecimal;

public class CastFloatTwo {
	/**
	 * 保留小数点后两位
	 * @param f
	 * @return
	 */
	public static float castFloat(float f){
		BigDecimal temp = new BigDecimal(f);
		f = temp.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return f;
	}
}
