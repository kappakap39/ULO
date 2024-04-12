package com.eaf.orig.ulo.app.view.util.ajax;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.google.gson.Gson;


public class CurrentDateTime implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(CurrentDateTime.class);
	public class ResponseObject{
		String hour = "";
		String miniute = "";
	}
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_CURRENT_DATE_TIME);
		// TODO Auto-generated method stub
		DateFormat hourFormat = new SimpleDateFormat("HH");
		DateFormat MiniuteFormat = new SimpleDateFormat("mm");
		Date date = new Date();
		ResponseObject responseObj = new ResponseObject();
		String response = null;
		try{
			responseObj.hour=hourFormat.format(date);
			responseObj.miniute=MiniuteFormat.format(date);
			response = new Gson().toJson(responseObj);
		}catch (Exception e){
			logger.fatal("ERROR",e);
		}
		return responseData.success(response);
	}

}
