package com.eaf.orig.ulo.model.fraudonline.data;

import java.io.StringWriter;
import java.util.Collection;

import org.jsefa.Serializer;
import org.jsefa.common.converter.SimpleListConverter;
import org.jsefa.common.converter.SimpleTypeConverter;
import org.jsefa.common.converter.SimpleTypeConverterConfiguration;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.config.CsvConfiguration;

public class ApplicantTypeConverter implements SimpleTypeConverter{

	private static final ApplicantTypeConverter INSTANCE = new ApplicantTypeConverter();
	public static ApplicantTypeConverter create() {
		 return INSTANCE;
		 }
	private ApplicantTypeConverter(){
		
	}
	@Override
	public Object fromString(String arg0) {
		// TODO Auto-generated method stub
		//System.out.println("ManualListItem=> " + arg0);
		return new Double(arg0);
	}

	@Override
	public String toString(Object arg0) {
		// TODO Auto-generated method stub
		//System.out.println("ManualListItem=> " + arg0);
		
		CsvConfiguration config = new CsvConfiguration();
		  config.setFieldDelimiter('|');
		  //config.getSimpleTypeConverterProvider().registerConverterType(Applicant.class, ApplicantTypeConverter.class);

		
		Serializer serializer = CsvIOFactory.createFactory(config,Applicant.class).createSerializer();
		StringWriter writer = new StringWriter();
		serializer.open(writer);
		serializer.write(arg0);
		serializer.close(true);
		String result=writer.toString().replace("\n", "").replace("\r", "");
		
		//System.out.println("result=> " + result);
		return result;
	}

	
	

}
