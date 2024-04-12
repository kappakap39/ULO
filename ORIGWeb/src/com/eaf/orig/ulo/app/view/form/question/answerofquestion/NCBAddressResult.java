package com.eaf.orig.ulo.app.view.form.question.answerofquestion;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.ncb.NcbAddressDataM;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;

public class NCBAddressResult extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(NCBAddressResult.class);
	String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public String processForm() {
		return super.processForm();
	}

	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ArrayList<NcbAddressDataM> addressList = new ArrayList<NcbAddressDataM>();
		ApplicationGroupDataM applicationGroup = null;
		try {
			applicationGroup = (ApplicationGroupDataM)ORIGForm.getObjectForm();
			OrigNCBInfoDAO dao = ORIGDAOFactory.getNCBInfoDAO();
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			logger.debug(">>>>>personalInfo.getPersonalId()>>"+personalInfo.getPersonalId());
			ArrayList<NcbInfoDataM> origNcbInfo =	dao.loadOrigNcbInfos(personalInfo.getPersonalId());
			if(!Util.empty(origNcbInfo)){
				for(NcbInfoDataM ncbInfo:origNcbInfo){
					ArrayList<NcbAddressDataM> ncbAddresss	= ncbInfo.getNcbAddress();
					if(!Util.empty(ncbAddresss)){
						addressList.addAll(ncbAddresss);
					}
				}		 
			}
			logger.debug(">>>>>addressList.size()>>"+addressList.size());
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return addressList;
	}

}