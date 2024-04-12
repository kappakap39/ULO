package com.eaf.orig.ulo.app.view.form.manual.displayvalue;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayValueHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class RekeyDE1_1ProductMakerDisplayValue extends FormDisplayValueHelper {
	private static final transient Logger logger = Logger.getLogger(RekeyDE1_1ProductMakerDisplayValue.class);	
	public String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");	
	String DECISION_FINAL_DECISION_CANCEL = SystemConstant.getConstant("DECISION_FINAL_DECISION_CANCEL");
	String DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		logger.debug("RekeyDE1_1ProductMakerDisplayValue >> ");
		try{
			boolean isVetoFlag = isVetoApplication();
			logger.debug("isVetoFlag : "+isVetoFlag);
			if(!isVetoFlag){
				boolean existSrcOfData = existSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				logger.debug("existSrcOfData >> "+existSrcOfData);
				if(!existSrcOfData){	
					ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)elementValue;
					String applicationTemplate = applicationGroup.getApplicationTemplate();
					logger.debug("applicationTemplate >> "+applicationTemplate);
					String flipType = applicationGroup.getFlipType();
					if(!FLIP_TYPE_INC.equals(flipType)){
						ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
						ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
						if(!Util.empty(applications)){
							Iterator<ApplicationDataM> iterator = applications.iterator();
							while(iterator.hasNext()){
								ApplicationDataM application = iterator.next();
								if(!DECISION_FINAL_DECISION_CANCEL.equals(application.getFinalAppDecision()) && !DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
									iterator.remove();
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return elementValue;
	}
}
