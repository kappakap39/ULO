package com.eaf.orig.ulo.app.ejb;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.ulo.app.ejb.view.GeneratorManager;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.CardDataM;

@Stateless
@Local(GeneratorManager.class)
@LocalBean
public class GeneratorManagerBean implements GeneratorManager {
	private static transient Logger logger = Logger.getLogger(GeneratorManagerBean.class);
    public GeneratorManagerBean() {
       super();
    }   
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String generateUniqueID(String name) {
		String UNIQUE_ID = "";
		try{	
			com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueIDGeneratorDAO();
			UNIQUE_ID = uniqueIDDAO.getUniqByName(name);			
		}catch(Exception e) {
			logger.fatal("ERROR ",e);
			throw new EJBException(e.getMessage());
		}
		return UNIQUE_ID;
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
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
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String generateCardNo(CardDataM card) {
		String UNIQUE_ID = "";
		try{	
			com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
			UNIQUE_ID = uniqueIDDAO.getCardNo(card);			
		}catch(Exception e) {
			logger.fatal("ERROR ",e);
			throw new EJBException(e.getMessage());
		}
		return UNIQUE_ID;
	}
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public String generateCardLinkCustNo(CardDataM card) {
    	String UNIQUE_ID = "";
    	try{	
    		com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
    		UNIQUE_ID = uniqueIDDAO.getCardLinkNo(card);			
    	}catch(Exception e) {
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
    	}
    	return UNIQUE_ID;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public String generateHisHerMembershipNo(CardDataM card) {
    	String UNIQUE_ID = "";
    	try{	
    		com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
    		UNIQUE_ID = uniqueIDDAO.getHisHerMembershipNo(card);			
    	}catch(Exception e) {
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
    	}
    	return UNIQUE_ID;
    }
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public String generatePriorityPassNo(CardDataM card) {
    	String UNIQUE_ID = "";
    	try{	
    		com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
    		UNIQUE_ID = uniqueIDDAO.getPriorityPassNo(card);			
    	}catch(Exception e) {
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
    	}
    	return UNIQUE_ID;
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String generateCardLinkRefNo() {
		String cardLinkRefNo = "";
		try{
			com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
			cardLinkRefNo = uniqueIDDAO.getGenerateRunningNo(MConstant.RunningParamId.CARD_LINK_REF_NO, MConstant.RunningParamType.CARD_LINK);
		}catch(Exception e) {
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
    	}
		return EjbUtil.appendZero(cardLinkRefNo,7);
	}
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public String generateStatckMembershipNo(String paramId,String paramType){
		String UNIQUE_ID = "";
    	try{	
    		com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO();
    		UNIQUE_ID = uniqueIDDAO.getGenerateRunningNoStack(paramId,paramType); 			
    	}catch(Exception e) {
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e.getMessage());
    	}
    	return UNIQUE_ID;
	}
    
}
