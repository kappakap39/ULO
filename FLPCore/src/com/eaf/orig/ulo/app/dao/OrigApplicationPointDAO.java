package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationPointDataM;

public interface OrigApplicationPointDAO {
	
	public void createOrigApplicationPointM(ApplicationPointDataM applicationPointDataM)throws ApplicationException;
	public void deleteOrigApplicationPointM(String applicationGroupId, String applicationPointId)throws ApplicationException;
	public ArrayList<ApplicationPointDataM> loadOrigApplicationPointM(String applicationGroupId)throws ApplicationException;	 
	public void saveUpdateOrigApplicationPointM(ApplicationPointDataM applicationPointDataM)throws ApplicationException;
	public void deleteNotInKeyApplicationPoint(ArrayList<ApplicationPointDataM> appPointList, 
			String applicationGroupId) throws ApplicationException;
}
