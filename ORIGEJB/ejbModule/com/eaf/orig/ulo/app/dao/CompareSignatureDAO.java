package com.eaf.orig.ulo.app.dao;

import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.comparesignature.CompareSignatureDataM;

public interface CompareSignatureDAO {
	public void selectOldApplicationInfo(CompareSignatureDataM compareSignatureDataM, ApplicationGroupDataM applicationGroup) throws Exception;
}
