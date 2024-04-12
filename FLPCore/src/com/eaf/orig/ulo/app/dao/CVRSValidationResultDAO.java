package com.eaf.orig.ulo.app.dao;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;

public interface CVRSValidationResultDAO {
	public void createCVRSValidationResultTable (CVRSValidationResultDataM cvrsValidationResult) throws ApplicationException;

}
