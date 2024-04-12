package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDedupDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class DuplicateApplicationPopupWebAction extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(DuplicateApplicationPopupWebAction.class);
	@Override
	public Event toEvent(){		
		return null;
	}

	@Override
	public boolean requiredModelRequest(){		
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){		
		return false;
	}

	@Override
	public boolean preModelRequest(){			
		PLOrigFormHandler formHandler = (PLOrigFormHandler) getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		try{
			ArrayList<String> list = getListApplicationNo(xrulesVerM);
			
			if(null != list){
				logger.debug(">> found deplicate application "+list.size());
			}else{
				logger.debug(">> not found deplicate application !! ");
			}
			
			if(null != list && list.size() > 0){
				UtilityDAO dao = new UtilityDAOImpl();
				xrulesVerM.setVXRulesDedupDataM(dao.LoadDataDupApplication(list));
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		return true;
	}
	private ArrayList<String> getListApplicationNo(PLXRulesVerificationResultDataM xrulesVerM){
		ArrayList<String> list = new ArrayList<String>();
		Vector<PLXRulesDedupDataM> dupVect = xrulesVerM.getVXRulesDedupDataM();
		if(null != dupVect){
			for(PLXRulesDedupDataM dataM : dupVect){
				if(!OrigUtil.isEmptyString(dataM.getApplicationNo())){
					list.add(dataM.getApplicationNo());
				}
			}
		}
		return list;
	}
	@Override
	public int getNextActivityType(){	
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
