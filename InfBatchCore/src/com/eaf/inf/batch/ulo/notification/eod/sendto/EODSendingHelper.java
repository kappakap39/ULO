package com.eaf.inf.batch.ulo.notification.eod.sendto;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.notify.model.EmailRecipientDataM;
import com.eaf.core.ulo.service.notify.model.RecipientTypeDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.eod.sendto.inf.EODSendingInf;
import com.eaf.inf.batch.ulo.notification.model.EODRecipientRequestDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;

public class EODSendingHelper implements EODSendingInf {
	private static transient Logger logger = Logger.getLogger(EODSendingHelper.class);
	@Override
	public void processGetRecipient(EODRecipientRequestDataM eodReceipientRequest,RecipientTypeDataM recipient)throws Exception{
		
	}
	private ArrayList<String> getEmployeeIds(ArrayList<EodEmployee> empIds){
		ArrayList<String> filterEmpIds = new ArrayList<String>();
		if(null!=empIds){
			for(EodEmployee eodEmployee:empIds){
				filterEmpIds.add(eodEmployee.getEmpId());
			}
		}
		return filterEmpIds;
	}
	private String findNotificationType(String empId,ArrayList<EodEmployee> empIds){
		if(null!=empIds){
			for (EodEmployee eodEmployee : empIds) {
				if(null!=eodEmployee.getEmpId()&&eodEmployee.getEmpId().equals(empId))return eodEmployee.getNotificationType();
			}
		}
		return null;
	}
	protected void getEmailEmployeeInfo(ArrayList<EodEmployee> empIds,RecipientTypeDataM recipientType,List<String> emails) throws Exception{
		logger.debug("empIds : "+empIds);
		if(!InfBatchUtil.empty(empIds)){
			ArrayList<VCEmpInfoDataM> empInfos = NotificationFactory.getNotificationDAO().findEmployeeInfos(getEmployeeIds(empIds));
			logger.debug("empInfos : "+empInfos);
			if(!InfBatchUtil.empty(empInfos)){
				for(VCEmpInfoDataM empInfo :empInfos){
					if(!InfBatchUtil.empty(empInfo.getEmail())){
						String  email = empInfo.getEmail();
						logger.debug("email : "+email);
						if(!InfBatchUtil.empty(email) && !emails.contains(email)){
							EmailRecipientDataM emailRecipient= new EmailRecipientDataM();
							emailRecipient.setEmail(empInfo.getEmail());
							emailRecipient.setRecipientType(findNotificationType(empInfo.getEmpId(), empIds));
							logger.debug("emailRecipient : "+emailRecipient);
							recipientType.put(emailRecipient);
							emails.add(email);
						}
					}	
				}
			}
		}
	}

	public boolean foundEmpId(ArrayList<EodEmployee> sellerEmpIds,String saleId){
		if(null!=sellerEmpIds){
			for (EodEmployee eodEmployee : sellerEmpIds) {
				if(null!=eodEmployee.getEmpId()&&eodEmployee.getEmpId().equals(saleId))return true;
			}
		}
		return false;		
	}
}
