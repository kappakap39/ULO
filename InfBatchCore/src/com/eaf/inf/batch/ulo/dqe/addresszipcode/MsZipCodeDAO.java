package com.eaf.inf.batch.ulo.dqe.addresszipcode;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface MsZipCodeDAO {
	public void insertMsZipCode() throws InfBatchException ;
	public void deleteMSZipCode() throws InfBatchException ;
	public void cvrsZipCode() throws InfBatchException ;
}
