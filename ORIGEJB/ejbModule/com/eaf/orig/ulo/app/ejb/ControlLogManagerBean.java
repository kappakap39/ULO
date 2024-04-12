package com.eaf.orig.ulo.app.ejb;

import com.eaf.orig.logon.dao.OrigLogOnDAO;
import com.eaf.orig.logs.dao.LogDAO;
import com.eaf.orig.profile.model.LogonDataM;
import com.eaf.orig.ulo.app.ejb.view.ControlLogManager;
import com.eaf.orig.ulo.app.factory.ModuleFactory;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class ControlLogManagerBean
 */
@Stateless
@Local(ControlLogManager.class)
@LocalBean
public class ControlLogManagerBean implements ControlLogManager {
	private static transient Logger logger = Logger.getLogger(ControlLogManagerBean.class);
    public ControlLogManagerBean() {
        super();
    }
    @Override
    public void saveLog(LogonDataM logonM) {
    	try{
    		LogDAO logDAO = ModuleFactory.getLogDao();
    		logDAO.saveLog(logonM);
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e);
    	}
    }
    @Override
    public void updateLogonFlag(String userName,String flag) {
    	try{
    		 OrigLogOnDAO logonDAO = ModuleFactory.getOrigLogOnDAO();
    		 logonDAO.updateLogonFlag(userName, flag);
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    		throw new EJBException(e);
    	}
    }
}
