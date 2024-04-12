package com.eaf.orig.ulo.service.cvrsdq0001.dao;

import com.eaf.orig.ulo.app.dao.CVRSValidationResultDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;

public class CVRSDQ0001ServiceManager {
	public static void createCVRSValidateResult(CVRSValidationResultDataM cvrsValidationResultDataM) throws ApplicationException{
		CVRSValidationResultDAO  dao = ORIGDAOFactory.getCVRSValidationResultDAO();
		dao.createCVRSValidationResultTable(cvrsValidationResultDataM);
	}
}
