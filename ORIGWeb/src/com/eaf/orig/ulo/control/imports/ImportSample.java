package com.eaf.orig.ulo.control.imports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;

public class ImportSample implements ImportControl{
	private static transient Logger logger = Logger.getLogger(ImportSample.class);	
	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID,String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("IMPORT_ID >> "+IMPORT_ID);
		logger.debug("FILE_NAME >> "+FILE_NAME);
		logger.debug("LOCATION >> "+LOCATION);
		return null;
	}
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID,String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public boolean requiredEvent() {
		return false;
	}
	@Override
	public ProcessResponse processEvent(Part part, HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
	}	
}
