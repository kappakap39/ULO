package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

public interface OrigApplicationImageSplitDAO {
	public void createOrigApplicationImageSplitDataM(ApplicationImageSplitDataM appImageSplitDataM,Connection conn)throws ApplicationException;
	public void createOrigApplicationImageSplitDataM(ApplicationImageSplitDataM appImageSplitDataM)throws ApplicationException;
	public void deleteOrigApplicationImageSplitDataM(String appImgId, String imgPageId)throws ApplicationException;
	public void deleteOrigApplicationImageSplitDataM(String appImgId, String imgPageId,Connection conn)throws ApplicationException;
	public ArrayList<ApplicationImageSplitDataM> loadOrigApplicationImageM(String appImgId)throws ApplicationException;	 
	public ArrayList<ApplicationImageSplitDataM> loadOrigApplicationImageM(String appImgId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigApplicationImageSplitDataM(ApplicationImageSplitDataM appImageSplitDataM)throws ApplicationException;
	public void saveUpdateOrigApplicationImageSplitDataM(ApplicationImageSplitDataM appImageSplitDataM,Connection conn)throws ApplicationException;
	public String selectDocumentGroupForDoc(String personalId, String docCode)throws ApplicationException;
	public void deleteNotInKeyApplicationImageSplit(ArrayList<ApplicationImageSplitDataM> appImageSplitList, 
			String appImgId) throws ApplicationException;
	public void deleteNotInKeyApplicationImageSplit(ArrayList<ApplicationImageSplitDataM> appImageSplitList, 
			String appImgId,Connection conn) throws ApplicationException;
}
