package com.eaf.orig.ulo.control.util;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.google.gson.Gson;

public class CheckFraudApplicationAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(CheckFraudApplicationAction.class);
	public static final String SEND_TO_FRAUD = "SEND_TO_FRAUD";
	String BUTTON_ACTION_SEND_TO_FRAUD = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FRAUD");
	private String fraudAplicationText;
	private String fruadFlag ="N";
	private ArrayList<String> fruadApplicationGroupNo;	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CHECK_FRUAD_APPLICATION_LIST);
		CheckFraudApplicationAction checkFruadActionDataM =  (CheckFraudApplicationAction) request.getSession().getAttribute(SEND_TO_FRAUD);
		if(Util.empty(checkFruadActionDataM)){
			checkFruadActionDataM = new CheckFraudApplicationAction();
		}
		
		ArrayList<String> fruadApplicationGroupNoList = checkFruadActionDataM.getFruadApplicationGroupNo();
		String buttonAction = request.getParameter("buttonAction");
		StringBuilder fruadApplicationText = new StringBuilder();
		try {
			logger.debug("buttonAction>>"+buttonAction);
			if(BUTTON_ACTION_SEND_TO_FRAUD.equals(buttonAction) && !Util.empty(fruadApplicationGroupNoList)){
				String COMMA="";
				for(String applicationGroupNo :fruadApplicationGroupNoList){
					fruadApplicationText.append(COMMA+applicationGroupNo);
					COMMA=", ";
					checkFruadActionDataM.setFruadFlag(MConstant.FLAG.YES);
				}
				String textMsg = !Util.empty(fruadApplicationText)?String.format(MessageErrorUtil.getText("ERROR_APPLICATION_GROUP_NO_SEND_TO_FRAUD"), fruadApplicationText.toString()):"";
				checkFruadActionDataM.setFraudAplicationText(textMsg);
			}
			logger.debug("buttonAction>>"+buttonAction);
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			responseData.error(e);
		}	
		String response = new Gson().toJson(checkFruadActionDataM).toString();
		request.getSession().removeAttribute(CheckFraudApplicationAction.SEND_TO_FRAUD);
		
		logger.debug("response>>"+response);
		return responseData.success(response);
	}
	
	public String getFraudAplicationText() {
		return fraudAplicationText;
	}
	public void setFraudAplicationText(String fraudAplicationText) {
		this.fraudAplicationText = fraudAplicationText;
	}
	public String getFruadFlag() {
		return fruadFlag;
	}
	public void setFruadFlag(String fruadFlag) {
		this.fruadFlag = fruadFlag;
	}
 
	public ArrayList<String> getFruadApplicationGroupNo() {
		return fruadApplicationGroupNo;
	}

	public void setFruadApplicationGroupNo(ArrayList<String> fruadApplicationGroupNo) {
		this.fruadApplicationGroupNo = fruadApplicationGroupNo;
	}			
	public void addFruadApplicationGroupNo(String applicationGroupNo) {
		if(null==fruadApplicationGroupNo){
			fruadApplicationGroupNo = new ArrayList<String>();
		}
		fruadApplicationGroupNo.add(applicationGroupNo);
	}			
}
