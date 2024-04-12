package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;

public interface OrigApplicationImageDAO {
	
	public void createOrigApplicationImageM(ApplicationImageDataM applicationImageDataM)throws ApplicationException;
	public void deleteOrigApplicationImageM(String applicationGroupId, String appImgId)throws ApplicationException;
	public ArrayList<ApplicationImageDataM> loadOrigApplicationImageM(String applicationGroupId)throws ApplicationException;	 
	public ArrayList<ApplicationImageDataM> loadOrigApplicationImageM(String applicationGroupId,Connection conn)throws ApplicationException;	 
	public void saveUpdateOrigApplicationImageM(ApplicationImageDataM applicationImageDataM)throws ApplicationException;
	public void saveUpdateOrigApplicationImageM(ApplicationImageDataM applicationImageDataM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyApplicationImage(ArrayList<ApplicationImageDataM> appImageList, 
			String applicationGroupId) throws ApplicationException;
	public void deleteNotInKeyApplicationImage(ArrayList<ApplicationImageDataM> appImageList, 
			String applicationGroupId,Connection conn) throws ApplicationException;
	void createOrigApplicationImageM(
			ApplicationImageDataM applicationImageDataM, Connection conn)
			throws ApplicationException;
}
