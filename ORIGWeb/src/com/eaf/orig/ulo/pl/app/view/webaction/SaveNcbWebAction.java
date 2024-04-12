package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SaveNcbWebAction extends WebActionHelper implements WebAction{
	static Logger logger = Logger.getLogger(SaveNcbWebAction.class);
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest(){
		UserDetailM userM = (UserDetailM)getRequest().getSession().getAttribute("ORIGUser");
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());	
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
		
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		Vector<PLApplicationImageDataM>	imageVect = applicationM.getApplicationImageVect();		
		Vector<PLApplicationImageSplitDataM> imageSplitVect = null;		
		Vector<PLNCBDocDataM> ncbDocVect = new Vector<PLNCBDocDataM>();
		PLNCBDocDataM ncbDocM = null;
		if(!ORIGUtility.isEmptyVector(imageVect)){
			for(PLApplicationImageDataM imageM : imageVect){
				imageSplitVect = imageM.getAppImageSplitVect();
				if(!ORIGUtility.isEmptyVector(imageSplitVect)){
					for(PLApplicationImageSplitDataM imageSplitM:imageSplitVect){
						String imgCheck = getRequest().getParameter("checkBox_"+imageSplitM.getImgID());
						if(ORIGUtility.isEmptyString(imgCheck))
							continue;							
						ncbDocM = new PLNCBDocDataM();
						ncbDocM.setImgID(imageSplitM.getImgID());
						ncbDocM.setPersonalID(personalM.getPersonalID());
						ncbDocM.setCreateBy(userM.getUserName());
						ncbDocM.setUpdateBy(userM.getUserName());
						ncbDocVect.add(ncbDocM);
					}
				}
			}
		}
		personalM.setNcbDocVect(ncbDocVect);		
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		
		if(!OrigUtil.isEmptyVector(ncbDocVect) && ncbDocVect.size() >= 2 
				&& (OrigUtil.isEmptyString(xrulesVerM.getNCBCode())
							|| NCBConstant.ncbResultCode.SEND_BACK.equals(xrulesVerM.getNCBCode()))){
			xrulesVerM.setNCBCode(null);
			xrulesVerM.setNCBResult(NCBConstant.NcbResultDesc.SELECT_IMAGE);
			jObj.CreateJsonObject("result_1023", xrulesVerM.getNCBResult());
			jObj.CreateJsonObject("code_1023", "");
		}else{
			xrulesVerM.setNCBCode(null);
			xrulesVerM.setNCBResult(null);
			jObj.CreateJsonObject("result_1023","");
			jObj.CreateJsonObject("code_1023", "");
		}
		
		jObj.ResponseJsonArray(getResponse());
		
		return true; 
	}

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
