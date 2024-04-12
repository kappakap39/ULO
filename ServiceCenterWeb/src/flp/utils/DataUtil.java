package flp.utils;

import java.math.BigDecimal;
import java.util.Date;


public class DataUtil {
	public static String string(String data) {
		String str = null;
		str = (Util.empty(data)) ? null : data;	
		return str;		
	}

	public static String stringNull(String data, int state) {
		return (null == data ) ?null: data;
	}


	public static BigDecimal bigDecimal(BigDecimal data, int state) {
		return (null == data) ? BigDecimal.ZERO
				: data;
	}

	public static BigDecimal bigDecimal(BigDecimal data) {
		return (null == data) ? BigDecimal.ZERO : data;
	}

	public static Integer integer(Integer data, int state) {
		return (null == data) ? 0 : data;
	}

	public static Integer integer(Integer data) {
		return (null == data) ? 0 : data;
	}

	public static Integer integerNull(Integer data, int state) {
		return (null == data) ? 0 : data;
	}

	public static Date date(Date data, int state) {
		return data;
	}
	
	public static Date date(Date data) {
		return data;
	}
}
