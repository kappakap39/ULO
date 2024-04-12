package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;

public class ConditionOfNoDocument extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ConditionOfNoDocument.class);
	private int PRVLG_PROJECT_DOC_INDEX=0;
	private String NO_DOC_TYPE =SystemConstant.getConstant("PRVLG_DOC_TYPE_NO_DOC");
	private String[] INFINITE_IMPLEMENT_ID =SystemConstant.getArrayConstant("PRVLG_NO_DOC_INFINITE");
	private String[] WISDOM_IMPLEMENT_ID =SystemConstant.getArrayConstant("PRVLG_NO_DOC_WISDOM");
	private String[] PREMIER_IMPLEMENT_ID =SystemConstant.getArrayConstant("PRVLG_NO_DOC_PREMIER");
	@Override
	public Object processAction() {
		String cardType=request.getParameter("CARD_TYPE");
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;		
		ArrayList<String> IMPLEMENT_IDS = new ArrayList<String>();
		ArrayList<String> projectCodes = new ArrayList<String>();
		try {
			PrivilegeProjectCodeDocDataM   privilegeProjectCodeDoc =  privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
			
			logger.debug("cardType>>>"+cardType);
			if(NO_DOC_TYPE.equals(privilegeProjectCodeDoc.getDocType())){
				if(PrivilegeUtil.ccInfinitesCardType().contains(cardType)){
					IMPLEMENT_IDS =new ArrayList<String>(Arrays.asList(INFINITE_IMPLEMENT_ID));
				}else if(PrivilegeUtil.ccWisdomsCardType().contains(cardType)){
					IMPLEMENT_IDS =new ArrayList<String>(Arrays.asList(WISDOM_IMPLEMENT_ID));
				}else if(PrivilegeUtil.ccPremierCardType().contains(cardType)){
					IMPLEMENT_IDS =new ArrayList<String>(Arrays.asList(PREMIER_IMPLEMENT_ID));
				}
				
				logger.debug("IMPLEMENT_IDS>>>"+IMPLEMENT_IDS.size());
				
				if(!Util.empty(IMPLEMENT_IDS)){
					for(String implementId :IMPLEMENT_IDS){
						ProcessActionInf processActionInf = ImplementControl.getProcessAction("PRIVILEGE_PROJECT_CODE",implementId);
						processActionInf.init(request, privilegeProjectCode, "", "",null);
						String[] resultFromProcess =(String[])processActionInf.processAction();					
						if(!Util.empty(resultFromProcess)){
							ArrayList<String> resultArr = new ArrayList<String>(Arrays.asList(resultFromProcess));
							projectCodes.addAll(resultArr);
						}
					}
				}	
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return  null!=projectCodes? projectCodes.toArray(new String[projectCodes.size()]):null;
	}
}
