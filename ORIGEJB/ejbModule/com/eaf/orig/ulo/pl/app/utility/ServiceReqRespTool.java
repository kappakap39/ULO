package com.eaf.orig.ulo.pl.app.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;

public class ServiceReqRespTool{
	static Logger logger = Logger.getLogger(ServiceReqRespTool.class);
	public String GenerateTransectionId(){
		StringBuilder transId = new StringBuilder("");
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		Date currentDate = new Date();
		transId.append(OrigConstant.ServiceActivityType.TRANSECTION);
		transId.append(format.format(currentDate));
		Random rand = new Random();
		transId.append(rand.nextInt(10)).append(rand.nextInt(10))
				.append(rand.nextInt(10));		
		logger.debug("GenerateTransectionId = "+transId);
		return transId.toString();
	}
	public String GenerateReqResNo(){		
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		StringBuilder reqResNo = new StringBuilder();		
		reqResNo.append(format.format(currentDate));
//		Random rand = new Random();
//		reqResNo.append(rand.nextInt(10)).append(rand.nextInt(10)).append(rand.nextInt(10)).append(rand.nextInt(10));		
		logger.debug("GenerateReqResNo = "+reqResNo);
		return reqResNo.toString();
		
	}
	public String GenereateEAIRefNo(){
		StringBuilder eaiRefNo = new StringBuilder();
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		eaiRefNo.append("EAI").append(format.format(currentDate));
		return eaiRefNo.toString();
	}
	public String GeneraterqUID(String paramValue, String apprecordID) {
		if (OrigUtil.isEmptyString(paramValue)
				|| OrigUtil.isEmptyString(apprecordID)) {
			return null;
		}
		StringBuilder rqUIDResult = new StringBuilder();
		rqUIDResult.append(paramValue);
		rqUIDResult.append("_");
		Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
			calendar.setTime(new Date());
		rqUIDResult.append(calendar.get(Calendar.YEAR)).append(GetMonth(calendar)).append(calendar.get(Calendar.DAY_OF_MONTH));
		rqUIDResult.append("_");
		rqUIDResult.append(apprecordID);
		logger.debug("[GeneraterqUID] rqUIDResult= "+rqUIDResult);
		
		return rqUIDResult.toString();
	}
	public String GeneraterqDt() {
		StringBuilder rqDtResult = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
		rqDtResult.append(dateFormat.format(new Date()));
		return rqDtResult.toString();
	}
	public String GetMonth(Calendar calendar){
		int month = (calendar.get(Calendar.MONTH)+1);
		String monthResult = null;
		if (month <= 9) {
			monthResult = "0" + String.valueOf(month);
		} else {
			monthResult = String.valueOf(month);
		}
		return monthResult;
	}
}
