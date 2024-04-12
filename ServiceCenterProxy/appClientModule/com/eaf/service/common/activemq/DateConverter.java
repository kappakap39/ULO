package com.eaf.service.common.activemq;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang3.time.FastDateFormat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;


public class DateConverter implements Converter {
	public static final Locale ENG_LOCALE = new Locale("en", "US");
	public static final String STANDARD_DATE_TIME_WS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SZZ";
	
	 private FastDateFormat formatter = FastDateFormat.getInstance(STANDARD_DATE_TIME_WS_PATTERN, ENG_LOCALE);

	    @SuppressWarnings("rawtypes")
		public boolean canConvert(Class clazz) {
	        // This converter is only for Calendar fields.
	        return Calendar.class.isAssignableFrom(clazz);
	    }

	    public void marshal(Object value, HierarchicalStreamWriter writer,
	            MarshallingContext context) {
	        Calendar calendar = (Calendar) value;
	        Date date = calendar.getTime();
	        writer.setValue(formatter.format(date));
	    }

	    public Object unmarshal(HierarchicalStreamReader reader,
	            UnmarshallingContext context) {
	        GregorianCalendar calendar = new GregorianCalendar();
	        try {
	            calendar.setTime(formatter.parse(reader.getValue()));
	        } catch (ParseException e) {
	            throw new ConversionException(e.getMessage(), e);
	        }
	        return calendar;
	    }

		
}

