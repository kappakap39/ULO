package com.eaf.orig.shared.ejb;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.ulo.pl.key.dao.PLUniqueIDGeneratorDAO;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import javax.ejb.Stateless;

/**
 * Bean implementation class for Enterprise Bean: ORIGGeneratorManager
 */
@Stateless
public class ORIGGeneratorManagerBean implements ORIGGeneratorManager,ORIGGeneratorRemoteManager{
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String generateUniqueIDByName(String name) {
		String id = "";
		try{	
			com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ORIGDAOFactory.getUniqueIDGeneratorDAO2();
			id = uniqueIDDAO.getUniqByName(name);			
		}catch(Exception e) {
			throw new EJBException(e.getMessage());
		}
		return id;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String generateUniqueIDByName(String name, int dbType) {
		String id = "";
		try{			
			com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ORIGDAOFactory.getUniqueIDGeneratorDAO2();
			id = uniqueIDDAO.getUniqByName(name, dbType);			
		} catch (UniqueIDGeneratorException e) {
			throw new EJBException(e.getMessage());
		}
		return id;
	}
	
	@Override
	public String generateApplicationNo(ApplicationDataM applicationDataM){
		String appNo = "";
		try{			
			appNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getApplicationNo(applicationDataM);			
		} catch (UniqueIDGeneratorException e) {
			throw new EJBException(e.getMessage());
		}
		return appNo;
	}
	
	@Override
	public String generateCardNo(CardInformationDataM card){
		String cardNo = "";
		try{			
			cardNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getCardNo(card);			
		} catch (UniqueIDGeneratorException e) {
			throw new EJBException(e.getMessage());
		}
		return cardNo;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String getApplicationNo(PLApplicationDataM appM) {
		String appNo = "";
		try{
			PLUniqueIDGeneratorDAO uniqueIDDAO = PLORIGDAOFactory.getPLUniqueIDGeneratorDAO();
			appNo = uniqueIDDAO.getApplicationNo(appM);
		} catch (UniqueIDGeneratorException e) {
			throw new EJBException(e.getMessage());
		}
		return appNo;
	}

	@Override
	public String generateNextSequenceID(String seqName) {
		String nextSeqId = null;
		UniqueIDGeneratorDAO uniqueIDDAO = ORIGDAOFactory.getUniqueIDGeneratorDAO2();
		try {
			nextSeqId = uniqueIDDAO.getUniqueBySequence(seqName);
		} catch (UniqueIDGeneratorException e) {
			throw new EJBException(e.getMessage());
		}
		return nextSeqId;
	}
	
}
