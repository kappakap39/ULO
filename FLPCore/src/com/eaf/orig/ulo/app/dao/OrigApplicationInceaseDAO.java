package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM;

public interface OrigApplicationInceaseDAO {
	public void createOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM)throws ApplicationException;
	public void deleteOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM)throws ApplicationException;
	public ArrayList<ApplicationIncreaseDataM> loadOrigApplicationIncreaseM(String applicationGroupId)throws ApplicationException;
	public ArrayList<ApplicationIncreaseDataM> loadOrigApplicationIncreaseM(String applicationGroupId,Connection conn)throws ApplicationException;
	public void saveOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM)throws ApplicationException;
	public void saveOrigApplicationIncreaseM(ApplicationIncreaseDataM applicationincreaseM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyApplicationIncrease(ArrayList<ApplicationIncreaseDataM> ApplicationIncreases,String applicationGroupId)throws ApplicationException;
	public void deleteNotInKeyApplicationIncrease(ArrayList<ApplicationIncreaseDataM> ApplicationIncreases,String applicationGroupId,Connection conn)throws ApplicationException;
	void createOrigApplicationIncreaseM(
			ApplicationIncreaseDataM applicationincreaseM, Connection conn)
			throws ApplicationException;
}
