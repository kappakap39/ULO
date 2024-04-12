package com.eaf.orig.ulo.pl.app.utility;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.ulo.pl.app.dao.PLOrigEmailSMSDAO;
import com.eaf.orig.ulo.pl.model.app.EmailSMSDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

public class EmailLogic {
	
	static Logger logger = Logger.getLogger(EmailLogic.class);
	
	public void process(EmailSMSDataM emailSmsM) throws Exception{
		if(OrigConstant.ROLE_DE.equals(emailSmsM.getRole())){
			Process_DE(emailSmsM);
		}else if(OrigConstant.ROLE_DC.equals(emailSmsM.getRole())){
			Process_DC(emailSmsM);
		}else if(OrigConstant.ROLE_CA.equals(emailSmsM.getRole())){
			Process_CA(emailSmsM);
		}
	}
	
	public void Process_CA(EmailSMSDataM emailSmsM) throws Exception{
		if(OrigConstant.Action.APPROVE.equals(emailSmsM.getCaDecision()) || 
				OrigConstant.Action.OVERRIDE.equals(emailSmsM.getCaDecision()) ||
					OrigConstant.Action.POLICY_EXCEPTION.equals(emailSmsM.getCaDecision())){
			if(OrigConstant.Channel.BRANCH.equals(emailSmsM.getApplyChannel()) ||
					OrigConstant.Channel.K_CONTRACT_CENTER.equals(emailSmsM.getApplyChannel()) ||
						OrigConstant.Channel.OUTBOUND_SALE.equals(emailSmsM.getApplyChannel()) ||
							OrigConstant.Channel.DSA.equals(emailSmsM.getApplyChannel())){		
					create(emailSmsM,OrigConstant.EmailSMS.EMAIL_APPROVE_TO_BRANCH);
			}
		}
	}
	public void Process_DC(EmailSMSDataM emailSmsM) throws Exception{
		PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
		if(!OrigConstant.BusClass.FCP_KEC_CC.equals(emailSmsM.getBusClassID())
				&& !OrigConstant.BusClass.FCP_KEC_CG.equals(emailSmsM.getBusClassID())){
			if(emailDAO.countOrigContactLog(emailSmsM.getAppRecID(), OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH) == 0){
				create(emailSmsM,OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH);
			}
		}
	}
	
	public void Process_DE(EmailSMSDataM emailSmsM) throws Exception{
		PLOrigEmailSMSDAO emailDAO = PLORIGDAOFactory.getPLOrigEmailSMSDAO();
		if(emailDAO.countOrigContactLog(emailSmsM.getAppRecID(), OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH) == 0){
			create(emailSmsM,OrigConstant.EmailSMS.EMAIL_RECEIVE_NEW_APP_TO_BRANCH);
		}
	}
		
	public void create(EmailSMSDataM emailSmsM,String templateID) throws Exception {
		logger.debug("create "+templateID);
		MapEmailData map = new MapEmailData();
		PLOrigContactDataM contractM = map.map(emailSmsM, templateID);
		
		PLOrigEmailSMSUtil tool = new PLOrigEmailSMSUtil();		
			contractM.setContactLogId(tool.getContactLogID());
			contractM.setApplicationRecordId(emailSmsM.getAppRecID());
			contractM.setContactType(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL);
			contractM.setCreateBy(emailSmsM.getUserID());
			contractM.setSendBy(emailSmsM.getUserID());
			contractM.setTemplateName(templateID);
			
		if(OrigConstant.FLAG_Y.equals(contractM.getFlag())){
			EmailEngine engine = new EmailEngine();
			engine.send(contractM);
		}
	}
	
}
