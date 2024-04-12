package com.eaf.orig.ulo.control.servlet;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.inf_export.infExportDAO;
import com.eaf.orig.ulo.app.view.util.inf_export.infExportDAOImpl;
import com.eaf.orig.ulo.app.view.util.inf_export.model.InfExportDataM;

public class InfExportProxy{
	private static transient Logger logger = Logger.getLogger(InfExportProxy.class);
	String DEFAULT_VLD_TO_DT = SystemConstant.getConstant("DEFAULT_VLD_TO_DT");
	
	public ArrayList<InfExportDataM> getMODULE_ID(String MODULE_ID , String DATE_NOW ) throws ApplicationException{
		infExportDAO infExport = new infExportDAOImpl();
		return infExport.getMODULE_ID(MODULE_ID , DATE_NOW);
	}
	public void createINF_EXPORT(InfExportDataM InfExportM) throws ApplicationException {
		infExportDAO infExport = new infExportDAOImpl();
		infExport.createTableINF_EXPORT(InfExportM);
	}
}
