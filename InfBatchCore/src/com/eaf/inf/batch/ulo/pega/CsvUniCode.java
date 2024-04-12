package com.eaf.inf.batch.ulo.pega;


import org.apache.commons.lang.StringEscapeUtils;
import org.jsefa.common.converter.SimpleTypeConverter;
import org.jsefa.common.converter.SimpleTypeConverterConfiguration;

public class CsvUniCode implements SimpleTypeConverter{
	public static CsvUniCode create(SimpleTypeConverterConfiguration configuration) {
		 return new CsvUniCode();
	}
	@Override
	public Object fromString(String arg0) {
		return arg0;
	}
	@Override
	public String toString(Object arg0) {
		return StringEscapeUtils.escapeJava((String)arg0);
	}
}
