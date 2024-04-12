package com.eaf.orig.ulo.pl.app.dao;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.orig.ulo.pl.app.dao.exception.UpdateApplicationStatusDAOException;

public interface UpdateApplicationStatusDAO {
	 public boolean updateAppStatus(WorkflowResponse response) throws UpdateApplicationStatusDAOException;
}
