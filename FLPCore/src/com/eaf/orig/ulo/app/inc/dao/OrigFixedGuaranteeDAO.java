package com.eaf.orig.ulo.app.inc.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;

public interface OrigFixedGuaranteeDAO {
	
	public void createOrigFixedGuaranteeM(FixedGuaranteeDataM fixedGuaranteeM)throws ApplicationException;
	public void deleteOrigFixedGuaranteeM(String incomeId, String fixedGuaranteeId)throws ApplicationException;
	public ArrayList<FixedGuaranteeDataM> loadOrigFixedGuaranteeM(String incomeId, String incomeType)throws ApplicationException;	 
	public ArrayList<FixedGuaranteeDataM> loadOrigFixedGuaranteeM(String incomeId, String incomeType,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigFixedGuaranteeM(FixedGuaranteeDataM fixedGuaranteeM)throws ApplicationException;
	public void deleteNotInKeyFixedGuarantee(ArrayList<IncomeCategoryDataM> fixedGuaranteeList, 
			String incomeId) throws ApplicationException;
}
