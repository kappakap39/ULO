package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.GuidelineDataM;

public interface OrigGuidelineDAO {
	
	public void createOrigGuidelineM(GuidelineDataM guidelineM)throws ApplicationException;
	public void createOrigGuidelineM(GuidelineDataM guidelineM,Connection conn)throws ApplicationException;
	public void deleteOrigGuidelineM(String orPolicyRulesDetailId, String guidelineId)throws ApplicationException;
	public void deleteOrigGuidelineM(String orPolicyRulesDetailId, String guidelineId,Connection conn)throws ApplicationException;
	public ArrayList<GuidelineDataM> loadOrigGuidelineM(String orPolicyRulesDetailId)throws ApplicationException;	 
	public void saveUpdateOrigGuidelineM(GuidelineDataM guidelineM)throws ApplicationException;
	public void saveUpdateOrigGuidelineM(GuidelineDataM guidelineM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyGuideline(ArrayList<GuidelineDataM> guidelineList, 
			String orPolicyRulesDetailId) throws ApplicationException;
	public void deleteNotInKeyGuideline(ArrayList<GuidelineDataM> guidelineList, 
			String orPolicyRulesDetailId,Connection conn) throws ApplicationException;
}
