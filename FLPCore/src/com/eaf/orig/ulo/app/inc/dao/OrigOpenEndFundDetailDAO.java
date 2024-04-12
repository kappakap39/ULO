package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;

public interface OrigOpenEndFundDetailDAO {
	
	public void createOrigOpenEndFundDetailM(OpenedEndFundDetailDataM openEndFundDetailM)throws ApplicationException;
	public void deleteOrigOpenEndFundDetailM(String opnEndFundId, String opnEndFundDetailId)throws ApplicationException;
	public ArrayList<OpenedEndFundDetailDataM> loadOrigOpenEndFundDetailM(String opnEndFundId)throws ApplicationException;	 
	public void saveUpdateOrigOpenEndFundDetailM(OpenedEndFundDetailDataM openEndFundDetailM)throws ApplicationException;
	public void deleteNotInKeyOpenEndFundDetail(ArrayList<OpenedEndFundDetailDataM> openEndFundDetailMList, 
			String opnEndFundId) throws ApplicationException;
}
