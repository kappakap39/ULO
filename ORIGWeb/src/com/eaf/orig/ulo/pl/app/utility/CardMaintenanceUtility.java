package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;
import com.eaf.orig.shared.constant.OrigConstant.cardMaintenance;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLAccountLogDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class CardMaintenanceUtility {
		
	public Vector<String[]> sortLog(String accId){
		
		ORIGDAOUtilLocal origDAO = PLORIGEJBService.getORIGDAOUtilLocal();
		String[] firstRow = origDAO.loadAccLogFirstRow(accId);
		
		Vector<String[]> resultVect = new Vector<String[]>();
		if (firstRow != null && firstRow.length > 0) {
			
			Vector<PLAccountLogDataM> accLogVect = origDAO.loadAccLogSortAsc(accId);
			String curCardNo = firstRow[1], curCustNo = firstRow[2], cardType = firstRow[3], cardFace = firstRow[4];
			
			if (!OrigUtil.isEmptyVector(accLogVect)) {
				
				Vector<String[]> logVect = new Vector<String[]>();
				for(int i=0; i<accLogVect.size(); i++){
					/*
					 * [0] = card no
					 * [1] = card type
					 * [2] = card face
					 * [3] = cust no
					 * [4] = create by
					 * [5] = create date
					 * */
					String sortLog[] = new String[6];
					PLAccountLogDataM accLogM = accLogVect.get(i);
					if (!OrigUtil.isEmptyString(accLogM.getAction()) && cardMaintenance.RE_ISSUE_CARD_NO.equals(accLogM.getAction())) {
						sortLog[0] = origDAO.deCodeCardNo(accLogM.getNewValue());
						sortLog[1] = cardType;
						sortLog[2] = cardFace;
						sortLog[3] = curCustNo;
						sortLog[4] = accLogM.getCreateBy();
						sortLog[5] = DataFormatUtility.DateEnToStringDateTh(accLogM.getCreateDate(), 6);
						
						curCardNo = sortLog[0];
						
					} else if (!OrigUtil.isEmptyString(accLogM.getAction()) && cardMaintenance.RE_ISSUE_CUST_NO.equals(accLogM.getAction())) {
						sortLog[0] = curCardNo;
						sortLog[1] = cardType;
						sortLog[2] = cardFace;
						sortLog[3] = accLogM.getNewValue();
						sortLog[4] = accLogM.getCreateBy();
						sortLog[5] = DataFormatUtility.DateEnToStringDateTh(accLogM.getCreateDate(), 6);
						
						curCustNo = sortLog[3];
					}
					logVect.add(sortLog);
				}
				if(!OrigUtil.isEmptyVector(logVect)){
					for(int i=logVect.size()-1; i>=0; i--){
						String sortLog[] = logVect.get(i);
						resultVect.add(sortLog);
					}
				}
				
				String firstRowSort[] = new String[6];
				firstRowSort[0] = firstRow[1];
				firstRowSort[1] = firstRow[3];
				firstRowSort[2] = firstRow[4];
				firstRowSort[3] = firstRow[2];
				firstRowSort[4] = firstRow[8];
				firstRowSort[5] = firstRow[7];

				resultVect.add(firstRowSort);
				
				return resultVect;
			}
			String firstRowSort[] = new String[6];
			firstRowSort[0] = firstRow[1];
			firstRowSort[1] = firstRow[3];
			firstRowSort[2] = firstRow[4];
			firstRowSort[3] = firstRow[2];
			firstRowSort[4] = firstRow[8];
			firstRowSort[5] = firstRow[7];

			resultVect.add(firstRowSort);
			return resultVect;
		}
		
		return null;
	}
	
	public int loadCardLinkStatus(String accId){
		ORIGDAOUtilLocal origDAO = PLORIGEJBService.getORIGDAOUtilLocal();
		return origDAO.getCardLinkStatus(accId);
		
	}

}
