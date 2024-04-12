package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;

public interface OrigAdditionalServiceDAO {
	
	public void createOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM)throws ApplicationException;
	public void createOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM,Connection conn)throws ApplicationException;
	public void saveUpdateOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM)throws ApplicationException;
	public void saveUpdateOrigAdditionalServiceM(SpecialAdditionalServiceDataM addServiceDataM,Connection conn)throws ApplicationException;
	public SpecialAdditionalServiceDataM loadOrigAdditionalServiceM(String serviceID)throws ApplicationException;	 
	public ArrayList<SpecialAdditionalServiceDataM> loadOrigAdditionalServiceM(List<String> serviceIDList)throws ApplicationException;
	void deleteNotInKeySpecialAdditionalServices(List<SpecialAdditionalServiceDataM> services, ApplicationGroupDataM appGroup) throws ApplicationException;
	void preDeleteSpecialAdditionalServices(ApplicationGroupDataM appGroup) throws ApplicationException;
	public ArrayList<SpecialAdditionalServiceDataM> loadOrigAdditionalService(ArrayList<String> personaIds) throws ApplicationException;
	public ArrayList<SpecialAdditionalServiceDataM> loadOrigAdditionalService(ArrayList<String> personaIds,Connection conn) throws ApplicationException;

}
