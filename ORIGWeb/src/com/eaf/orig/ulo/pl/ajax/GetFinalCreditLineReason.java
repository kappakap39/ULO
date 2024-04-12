package com.eaf.orig.ulo.pl.ajax;

//import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
//import com.eaf.orig.cache.CacheDataInf;
//import com.eaf.orig.cache.util.ORIGCacheUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class GetFinalCreditLineReason implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
//		ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
		
		String finalCreditLine = request.getParameter("final_credit_line");
		String recommendCreditLine = request.getParameter("recommend_credit_line");
        String finalCreditLineReasonCode = request.getParameter("reasonCode");
        String displayMode = request.getParameter("displayMode-decision");
        
        double finalCredit = DataFormatUtility.StringToDouble(finalCreditLine);
        double recommendCredit = DataFormatUtility.StringToDouble(recommendCreditLine);
        
        logger.debug("@@@@@ finalCreditLine >> " + finalCredit);
        logger.debug("@@@@@ recommendCreditLine >> " + recommendCredit);
        logger.debug("@@@@@ finalCreditLineReasonCode >> " + finalCreditLineReasonCode);
        logger.debug("@@@@@ displayMode >> " + displayMode);
        
        PLOrigFormHandler origForm =  (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
        PLApplicationDataM applicationM =  (PLApplicationDataM) origForm.getAppForm();
        
        int fieldId = 0;
        if(finalCredit < recommendCredit){
        	logger.debug("@@@@@ finalCredit < recommendCredit");
        	fieldId = OrigConstant.fieldId.FINAL_CREDIT_LESS_RECCOMMEND_REASON;
        }else if (finalCredit > recommendCredit){
        	logger.debug("@@@@@ finalCredit > recommendCredit");
        	fieldId = OrigConstant.fieldId.FINAL_CREDIT_MORE_RECCOMMEND_REASON;
        }
        return HTMLRenderUtil.displaySelectTagScriptAction_ORIG(fieldId,applicationM.getBusinessClassId(),finalCreditLineReasonCode,"final_credit_reason",displayMode," style='width:300px' ");
	}

}
