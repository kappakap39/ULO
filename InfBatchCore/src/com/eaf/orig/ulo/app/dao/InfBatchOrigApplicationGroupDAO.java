package com.eaf.orig.ulo.app.dao;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.ApplicationGroupDataM;

public interface InfBatchOrigApplicationGroupDAO {
	public ApplicationGroupDataM loadOrigApplicationGroup(String applicationGroupId) throws InfBatchException;
}
