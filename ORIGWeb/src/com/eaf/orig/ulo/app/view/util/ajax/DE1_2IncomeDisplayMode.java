package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DE1_2IncomeDisplayMode implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DE1_2IncomeDisplayMode.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DE1_2_INCOME_DISPLAY_MODE);
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			PersonalInfoDataM personalInfo= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			if(!Util.empty(personalInfo)){
				if(Util.empty(personalInfo.getDisplayEditBTN()) || MConstant.FLAG_N.equals(personalInfo.getDisplayEditBTN())){
					personalInfo.setDisplayEditBTN(MConstant.FLAG_Y);
				}else if(MConstant.FLAG_Y.equals(personalInfo.getDisplayEditBTN())){
					personalInfo.setDisplayEditBTN(MConstant.FLAG_N);
				}			
			}
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
