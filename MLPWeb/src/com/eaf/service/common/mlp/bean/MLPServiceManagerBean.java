package com.eaf.service.common.mlp.bean;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

@Stateless
@LocalBean
public class MLPServiceManagerBean 
{
	private static transient Logger logger = Logger.getLogger(MLPServiceManagerBean.class);
	public void saveApplication(ApplicationGroupDataM applicationGroup,String userId) throws EJBException
	{
		try{
			String applicationGroupId = applicationGroup.getApplicationGroupId();
			logger.debug("applicationGroupId : "+applicationGroupId);
			ORIGDAOFactory.getApplicationGroupDAO(userId).saveUpdateOrigApplicationGroupM(applicationGroup);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new EJBException(e);
		}
	}
	
	
}