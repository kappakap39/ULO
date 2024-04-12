package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IncomeFlagSave implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(IncomeFlagSave.class);

	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.INCOMESCREENVALIDATEFLAG);
		String FLAG_RETURN = SystemConstant.getConstant("FLAG_NO");
		String roleId = FormControl.getFormRoleId(request);
		String ROLE_DE1_2 = SystemConstant.getConstant("ROLE_DE1_2");
		boolean checkIncomeFlagY = false;
		ApplicationGroupDataM applicationGroup  = (ApplicationGroupDataM)FormControl.getMasterObjectForm(request);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<IncomeInfoDataM> incomelist = personalInfo.getIncomeInfos();
		ArrayList<ApplicationDataM> appList = ((ApplicationGroupDataM)FormControl.getMasterObjectForm(request)).filterApplicationLifeCycle();
		if(!Util.empty(incomelist)) {
			for(IncomeInfoDataM incomeM:incomelist){
				if(!IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
					checkIncomeFlagY = true;
					break;
				}
			}
		}
		
		if(!Util.empty(appList)){
			for(ApplicationDataM appM : appList) {
				BundleHLDataM hlData = appM.getBundleHL();
				if(!Util.empty(hlData)){
					if(!(MConstant.FLAG.YES.equals(hlData.getCompareFlag()) || 
						 MConstant.FLAG.WRONG.equals(hlData.getCompareFlag()) ||
						(ROLE_DE1_2.equals(roleId) && MConstant.FLAG.NO.equals(hlData.getCompareFlag())))) {
							checkIncomeFlagY = true;
					}
				}
				
				BundleKLDataM klData = appM.getBundleKL();
				if(!Util.empty(klData)){
					if(!(MConstant.FLAG.YES.equals(klData.getCompareFlag()) ||
						 MConstant.FLAG.WRONG.equals(klData.getCompareFlag()) ||
						 (ROLE_DE1_2.equals(roleId) && MConstant.FLAG.NO.equals(klData.getCompareFlag())))){
						   checkIncomeFlagY = true;
					}
				}
				BundleSMEDataM smeData = appM.getBundleSME();
				if(!Util.empty(smeData)){
					if(!(MConstant.FLAG.YES.equals(smeData.getCompareFlag()) || MConstant.FLAG.WRONG.equals(smeData.getCompareFlag()) ||
						(ROLE_DE1_2.equals(roleId) && MConstant.FLAG.NO.equals(smeData.getCompareFlag())))){
						  checkIncomeFlagY = true;
					}	
				}
			}
		}
		
		if(checkIncomeFlagY){
			FLAG_RETURN = SystemConstant.getConstant("FLAG_YES");
		}
		return responseData.success(FLAG_RETURN);
	}

}
