package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ProjectCodeDataM;

public interface OrigProjectCodeDAO {
	
	public void createOrigProjectCodeM(ProjectCodeDataM projectCodeM)throws ApplicationException;
	public void deleteOrigProjectCodeM(String applicationGroupId, String projectCode)throws ApplicationException;
	public ArrayList<ProjectCodeDataM> loadOrigProjectCodeM(String applicationGroupId)throws ApplicationException;	 
	public void saveUpdateOrigProjectCodeM(ProjectCodeDataM projectCodeM)throws ApplicationException;
	public void deleteNotInKeyProjectCode(ArrayList<ProjectCodeDataM> projectCodeList, 
			String applicationGroupId) throws ApplicationException;
}
