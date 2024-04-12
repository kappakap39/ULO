package com.eaf.orig.shared.ejb;

import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.model.InfBackupLogM;
import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
import com.eaf.orig.shared.model.ServiceEmailSMSSchedulerQDataM;
import com.eaf.orig.ulo.pl.app.dao.PLORIGSchedulerDAO;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

/**
 * Session Bean implementation class ORIGContactManagerBean
 */
//@Stateless
public class ORIGContactManagerBean implements ORIGContactManager {
	private static Logger logger = Logger.getLogger(ORIGContactManagerBean.class);
    /**
     * Default constructor. 
     */
    public ORIGContactManagerBean() {
        super();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createServiceEmailSMSQLog(ServiceEmailSMSQLogM emailSMSQLogM){
		try{
			PLORIGDAOFactory.getPLOrigEmailSMSDAO().createServiceEmailSMSQLog(emailSMSQLogM);
		}catch (Exception e){
			logger.fatal("ERROR ",e);
		}
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void increaseEmailSMSQCount(int contactLogID){
		try{
			PLORIGDAOFactory.getPLOrigEmailSMSDAO().increaseEmailSMSQCount(contactLogID);
		}catch (Exception e){
			logger.fatal("ERROR ",e);
		}
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createOrigContractLog(PLOrigContactDataM contactM){
		try{
			PLORIGDAOFactory.getPLOrigEmailSMSDAO().createOrigContactLog(contactM);
		}catch (Exception e){
			logger.fatal("ERROR ",e);
		}
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteEmailSMSQueue(int contactLogID){
		try{
			PLORIGDAOFactory.getPLOrigEmailSMSDAO().deleteEmailSMSQueue(contactLogID);
		}catch (Exception e){
			logger.fatal("ERROR ",e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateStatusEmailSMSSchedulerQ( Vector<ServiceEmailSMSSchedulerQDataM> schedulerQVT) {
		if(schedulerQVT != null && schedulerQVT.size() > 0){
			try{
				PLORIGSchedulerDAO schedulerDAO = PLORIGDAOFactory.getPLORIGSchedulerDAO();
				for(int i=0;i<schedulerQVT.size();i++){
					ServiceEmailSMSSchedulerQDataM schedulerQM = (ServiceEmailSMSSchedulerQDataM)schedulerQVT.get(i);
					schedulerDAO.updateStatusEmailSMSSchedulerQ(schedulerQM);
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateStatusEmailSMSSchedulerQ( ServiceEmailSMSSchedulerQDataM schedulerQDataM) {
		try{
			PLORIGDAOFactory.getPLORIGSchedulerDAO().updateStatusEmailSMSSchedulerQ(schedulerQDataM);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createInterfaceBackupLog(InfBackupLogM infBackupM, String userName) {
		try{
			PLORIGDAOFactory.getORIGManualImportDAO().createInterfaceBackupLog(infBackupM, userName);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void deleteInterfaceBackupLog(int infBackupId) {
		try{
			PLORIGDAOFactory.getORIGManualImportDAO().deleteInterfaceBackupLog(infBackupId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
}
