package com.eaf.orig.ulo.app.factory;

import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationDAO;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationDAOImpl;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationGroupDAOImpl;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationLogDAO;
import com.eaf.orig.ulo.app.dao.InfBatchOrigApplicationLogDAOImpl;
import com.eaf.orig.ulo.app.dao.InfBatchOrigPersonalInfoDAO;
import com.eaf.orig.ulo.app.dao.InfBatchOrigPersonalInfoDAOImpl;
import com.eaf.orig.ulo.app.dao.InfBatchOrigPersonalRelationDAO;
import com.eaf.orig.ulo.app.dao.InfBatchOrigPersonalRelationDAOImpl;
import com.eaf.orig.ulo.app.dao.InfBatchXrulesPolicyRulesDAO;
import com.eaf.orig.ulo.app.dao.InfBatchXrulesPolicyRulesDAOImpl;
import com.eaf.orig.ulo.app.dao.InfBatchXrulesVerificationResultDAO;
import com.eaf.orig.ulo.app.dao.InfBatchXrulesVerificationResultDAOImpl;

public class InfBatchOrigDAOFactory {
	public static InfBatchOrigApplicationGroupDAO getApplicationGroupDAO(){
		return new InfBatchOrigApplicationGroupDAOImpl();
	}
	public static InfBatchOrigApplicationDAO getApplicationDAO(){
		return new InfBatchOrigApplicationDAOImpl();
	}
	public static InfBatchOrigPersonalInfoDAO getPersonalInfoDAO(){
		return new InfBatchOrigPersonalInfoDAOImpl();
	}
	public static InfBatchOrigPersonalRelationDAO getPersonalRelationDAO(){
		return new InfBatchOrigPersonalRelationDAOImpl();
	}
	public static InfBatchXrulesVerificationResultDAO getXrulesVerificationDAO(){
		return new InfBatchXrulesVerificationResultDAOImpl();
	}
	public static InfBatchXrulesPolicyRulesDAO getXrulesPolicyRulesDAO(){
		return new InfBatchXrulesPolicyRulesDAOImpl();
	}
	public static InfBatchOrigApplicationLogDAO getApplicationLogDAO(){
		return new InfBatchOrigApplicationLogDAOImpl();
	}
}
