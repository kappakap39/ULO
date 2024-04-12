package com.eaf.orig.ulo.pl.control.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

@Deprecated
public class Message {
	static Logger logger = Logger.getLogger(Message.class);
	public static final String ERROR = "\u0E23\u0E30\u0E1A\u0E1A\u0E40\u0E01\u0E34\u0E14\u0E02\u0E49\u0E2D\u0E1C\u0E34\u0E14\u0E1E\u0E25\u0E32\u0E14";
	public static String error(Exception e){
		Date date = new Date();
		String refID = createRefID(date);
		String serverTime = createServerTime(date);		
		String ERROR_ID = " Error Reference ID = "+refID+", Server time "+serverTime;
		logger.error(ERROR_ID);
		String MSG =ERROR+ERROR_ID;
		logger.fatal("ERROR ",e);	
		return MSG;
	}
	public static String error(){
		Date date = new Date();
		String refID = createRefID(date);
		String serverTime = createServerTime(date);		
		String ERROR_ID = " Error Reference ID = "+refID+", Server time "+serverTime;
		logger.error(ERROR_ID);	
		String MSG =ERROR+ERROR_ID;
		return MSG;
	}
	public static String createServerTime(Date date){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(date);
	}
	
	public static String createRefID(Date date){
		SimpleDateFormat format = new SimpleDateFormat("HHmmssSSS");
		return format.format(date);
	}
	
}
