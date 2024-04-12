package com.eaf.orig.ulo.app.factory;

import com.eaf.orig.logon.dao.OrigLogOnDAO;
import com.eaf.orig.logon.dao.OrigLogOnDAOImpl;
import com.eaf.orig.logs.dao.LogDAO;
import com.eaf.orig.logs.dao.LogDAOImpl;
import com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO;
import com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAOImpl;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationDAO;
import com.eaf.orig.ulo.app.dao.OrigApplicationDAOImpl;
import com.eaf.orig.ulo.app.dao.OrigComparisionDataDAO;
import com.eaf.orig.ulo.app.dao.OrigComparisionDataDAOImpl;
import com.eaf.orig.ulo.app.dao.ReAssignDAO;
import com.eaf.orig.ulo.app.dao.ReAssignDAOImpl;
import com.eaf.orig.ulo.app.view.util.batch.InfBatchLogDAO;
import com.eaf.orig.ulo.app.view.util.batch.InfBatchLogDAOImpl;

public class ModuleFactory extends ORIGDAOFactory{
	public static OrigLogOnDAO getOrigLogOnDAO() {
		return new OrigLogOnDAOImpl();
	}
	public static LogDAO getLogDao(){
	    return new LogDAOImpl();
	}	
	public static UniqueIDGeneratorDAO getUniqueIDGeneratorDAO(){
		return new UniqueIDGeneratorDAOImpl();
	}
	public static OrigApplicationDAO getApplicationDAO(){
		return new OrigApplicationDAOImpl();
	}
	public static OrigApplicationDAO getApplicationDAO(String userId){
		return new OrigApplicationDAOImpl(userId);
	}
	public static OrigComparisionDataDAO getOrigComparisionDataDAO(String userId){
		return new OrigComparisionDataDAOImpl(userId);
	}
	public static OrigComparisionDataDAO getOrigComparisionDataDAO(){
		return new OrigComparisionDataDAOImpl();
	}
	public static ReAssignDAO getReAssignDAO(){
		return new ReAssignDAOImpl();
	}
	public static com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO getUniqueCardGeneratorDAO(String execution){
		return new com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAOImpl(execution);
	}
	public static com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO getUniqueCardGeneratorDAO(){
		return new com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAOImpl();
	}
	public static InfBatchLogDAO getInfBatchLogDAO(){
		return new InfBatchLogDAOImpl();
	}
	public static InfBatchLogDAO getInfBatchLogDAO(String userId){
		return new InfBatchLogDAOImpl(userId);
	}
}
