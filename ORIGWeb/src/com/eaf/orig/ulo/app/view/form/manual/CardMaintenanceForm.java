package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.xrules.dao.OrigCardMaintenanceDAO;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDetailDataM;
import com.eaf.orig.ulo.model.cardMaintenance.PersonalCardMaintenanceDataM;

public class CardMaintenanceForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(CardMaintenanceForm.class);
	@Override
	public Object getObjectForm() {
		String applicationGroupId = getRequestData("APPLICATION_GROUP_ID");	
		String cardlinkRefNo = getRequestData("CARDLINK_REF_NO");
		String regType = getRequestData("REG_TYPE");
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("cardlinkRefNo >> "+cardlinkRefNo);
		logger.debug("regType >> "+regType);
		CardMaintenanceDataM cardMaintenance =  new CardMaintenanceDataM();
		OrigCardMaintenanceDAO cardMaintenanceDAO = ORIGDAOFactory.getCardMaintenanceDAO();
		try{
			cardMaintenance = cardMaintenanceDAO.getCardMaintenance(applicationGroupId,cardlinkRefNo, regType);
		}catch(ApplicationException e){
			logger.fatal("ERROR",e);
		}	
		String CARDLINK_FLAG_SUCCESS = SystemConstant.getConstant("CARDLINK_FLAG_SUCCESS");
		String CARDLINK_FLAG_FAIL = SystemConstant.getConstant("CARDLINK_FLAG_FAIL");
		String CARDLINK_BATCH_WAITING = SystemConstant.getConstant("CARDLINK_BATCH_WAITING");
		cardMaintenance.setDisplayMode("VIEW");
		ArrayList<PersonalCardMaintenanceDataM> personalCardMaintenances = cardMaintenance.getPersonalCardMaintenances();
		if(null!=personalCardMaintenances){
			for (PersonalCardMaintenanceDataM personalCardMaintenance: personalCardMaintenances) {
				if(CARDLINK_FLAG_FAIL.equals(personalCardMaintenance.getPersonalStatus())){
					cardMaintenance.setDisplayMode("EDIT");
				}
				if(null!=personalCardMaintenance.getCardMaintenanceDetails()){
					for (CardMaintenanceDetailDataM cardMaintenanceDetail : personalCardMaintenance.getCardMaintenanceDetails()) {
						if(CARDLINK_FLAG_FAIL.equals(cardMaintenanceDetail.getCardStatus())
								&&(CARDLINK_FLAG_SUCCESS.equals(personalCardMaintenance.getPersonalStatus())||CARDLINK_BATCH_WAITING.equals(personalCardMaintenance.getPersonalStatus()))){
							cardMaintenance.setDisplayMode("EDIT");
						}
					}
				}
				
			}
		}
		return cardMaintenance;
	}

}
