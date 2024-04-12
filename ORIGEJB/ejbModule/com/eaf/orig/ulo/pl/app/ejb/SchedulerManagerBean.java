package com.eaf.orig.ulo.pl.app.ejb;

import javax.ejb.EJBException;
//import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAO;
//import com.eaf.orig.ulo.pl.app.utility.CommissionUtility;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigEmailUtil;
//import com.eaf.orig.ulo.pl.app.utility.PLOrigSMSUtil;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionAppDetialDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDataM;

//@Stateless
public class SchedulerManagerBean implements SchedulerManager {
	static Logger logger = Logger.getLogger(SchedulerManagerBean.class);
    
	public SchedulerManagerBean() {
    	super();
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void SendEmail(PLOrigContactDataM contactM) {
    	try{
//    		PLOrigEmailUtil emailUtil = new PLOrigEmailUtil();
//    			emailUtil.sendEmail(contactM);
    	}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void SendSMS(PLOrigContactDataM contactM){
    	try{
//    		PLOrigSMSUtil smsUtil = new PLOrigSMSUtil();
//    			smsUtil.sendSMS(contactM);
    	}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new EJBException(e.getMessage());
		}
    }
    
    @Override
    public SAUserCommissionAppDetialDataM CalCommission(String applicationNo) {
    	SAUserCommissionAppDetialDataM comissionM = null;
    	try{
//    		PLOrigApplicationDAO applicationDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
//    		PLApplicationDataM applicationM = applicationDAO.loadOrigApplicationAppNo(applicationNo);
//    		if(null !=applicationM && null != applicationM.getApproveDate()){
//    			comissionM = CommissionUtility.mapUserCommission(applicationM);
//    		}
    	}catch(Exception e){
    		logger.fatal("Exception ",e);
    		throw new EJBException(e.getMessage());
		}
    	return comissionM;
    }
    @Override
    public SAUserCommissionDataM getCommissionMALW(SAUserCommissionDataM commissionM){
    	SAUserCommissionDataM malwM = null;
    	try{
//    		malwM = CommissionUtility.getCommissionMALW(commissionM);
    	}catch(Exception e){
    		logger.fatal("Exception ",e);
    		throw new EJBException(e.getMessage());
		}
    	return malwM;
    }
}
