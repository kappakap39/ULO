package com.eaf.core.ulo.common.dao;

import java.sql.Date;

import com.eaf.core.ulo.common.exception.InfBatchException;


public interface InfBatchDAO {
	public Date getApplicationDate() throws InfBatchException;
	public String getGeneralParam(String paramId);
	public String getMessage(String messageId);
}
