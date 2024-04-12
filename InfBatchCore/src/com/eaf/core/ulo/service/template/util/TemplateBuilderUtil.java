package com.eaf.core.ulo.service.template.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.InfBatchUtil;

public class TemplateBuilderUtil {
	private static transient Logger logger = Logger.getLogger(TemplateBuilderUtil.class);
	private static String REPLACE_TEMPLATE_PATTERN=InfBatchProperty.getInfBatchConfig("NOTIFICATION_REPLACE_TEMPLATE_PATTERN");	
	
	public static String getReplaceTemplateMessage(String templateContents ,Map<String, Object> variableValues){	
		StringBuilder builder = new StringBuilder("");
		if(!InfBatchUtil.empty(templateContents)){
			Pattern pattern = Pattern.compile(REPLACE_TEMPLATE_PATTERN);
			Matcher matcher = pattern.matcher(templateContents);
			try {
				int i = 0;
				while (matcher.find()) {
				    String replacement = (String)variableValues.get(matcher.group(1));
				    builder.append(templateContents.substring(i, matcher.start()));
				    if (InfBatchUtil.empty(replacement)){
				    	 builder.append("");
				    }else{
					     builder.append(replacement);
				    }
				    i = matcher.end();
				}
				builder.append(templateContents.substring(i, templateContents.length()));
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}		
		}
		return builder.toString();
	}
}
