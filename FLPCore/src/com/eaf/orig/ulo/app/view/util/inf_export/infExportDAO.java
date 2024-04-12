package com.eaf.orig.ulo.app.view.util.inf_export;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.inf_export.model.InfExportDataM;

public interface infExportDAO {
	public ArrayList<InfExportDataM> getMODULE_ID(String MODULE_ID , String DATE_NOW)throws ApplicationException;
	public void createTableINF_EXPORT(InfExportDataM InfExportM) throws ApplicationException;
}
