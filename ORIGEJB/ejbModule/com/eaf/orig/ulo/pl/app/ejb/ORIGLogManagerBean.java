package com.eaf.orig.ulo.pl.app.ejb;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.eaf.orig.inf.log.dao.ServiceReqRespDAO;
import com.eaf.orig.inf.log.model.ServiceReqRespDataM;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.tool.performance.PerformanceLogDataM;

//@Stateless
public class ORIGLogManagerBean implements ORIGLogManager {
	static Logger logger = Logger.getLogger(ORIGLogManagerBean.class);
    public ORIGLogManagerBean() {
      super();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void SaveLogServiceReqResp(ServiceReqRespDataM servReqRespM){
		try{
			ServiceReqRespDAO serviceDAO = PLORIGDAOFactory.getServiceReqRespDAO();
			serviceDAO.InsertServiceReqResp(servReqRespM);
		}catch (Exception e) {
			logger.fatal("Error ",e);
			throw new EJBException(e.getMessage());
		}		
	}

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void SavePerformanceLog(PerformanceLogDataM perfLogM){
		try{
			ServiceReqRespDAO serviceDAO = PLORIGDAOFactory.getServiceReqRespDAO();
			serviceDAO.SavePerformanceLog(perfLogM);
		}catch (Exception e) {
			logger.fatal("Error ",e);
			throw new EJBException(e.getMessage());
		}
	}
       
}
