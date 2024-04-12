package com.eaf.inf.batch.ulo.doa;

import com.eaf.inf.batch.ulo.doa.DeleteOldAppDAO;

public class DeleteOldAppFactory {
	public static DeleteOldAppDAO getDeleteOldAppDAO(){
		return new DeleteOldAppDAOImpl();
	}
}
