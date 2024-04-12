package com.eaf.orig.ulo.app.view.form.property.field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;

public class BANK_STATEMENT_AMTProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BANK_STATEMENT_AMTProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BANK_STATEMENT_AMTProperty.validateForm");
		BankStatementDataM monthlyData = (BankStatementDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		ArrayList<BankStatementDetailDataM> detailList = monthlyData.getBankStatementDetails();
		if(!Util.empty(detailList)) {
			List<Integer> validateList = new ArrayList<Integer>();
			for(BankStatementDetailDataM monthlyDetail : detailList) {
				if(!Util.empty(monthlyDetail.getAmount()) && Util.compareBigDecimal(BigDecimal.ZERO, monthlyDetail.getAmount()) != 0) {
					validateList.add(monthlyDetail.getSeq());
				} 
			}
			if(0 == validateList.size()){
				for( int i = 0 ; i < detailList.size() ; i++) {
					BankStatementDetailDataM monthlyDetail = detailList.get(i);
					for( int j = 0 ; j < BankStatementDetailDataM.MONTH_COUNT ; j++) {
						formError.error("AMOUNT_"+monthlyDetail.getSeq()+"_" + (j+1),MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_AMT"));
					}
				}
				
			}
			else if(validateList.size() < BankStatementDetailDataM.CONSECUTIVE_COUNT) {
				for(int seq : validateList){
					formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + seq,MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_AMT"));
				}
			}else{
				boolean validateSeq = true;
				for(int i = 0; i < validateList.size() ; i++){
					if(i != validateList.size() - 1){
						int seq1 = validateList.get(i);
						int seq2 = validateList.get(i+1);
						int diffSeq = seq2 - seq1;
						if(1 != diffSeq){
							validateSeq = false;
						}
					}
				}
				if(!validateSeq){
					for(int seq : validateList){
						formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + (seq),MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_SEQ_AMT"));
					}
				}else {
					if(Util.empty(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_2")) && Util.empty(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_3"))){
						formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + 2,MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_MAN_MONTH_AMT"));
						formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + 3,MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_MAN_MONTH_AMT"));
					}else if(Util.empty(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_2"))){
						formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + 2,MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_MAN_MONTH_AMT"));
					}else if(Util.empty(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_3"))){
						formError.error("AMOUNT_"+monthlyData.getSeq()+"_" + 3,MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_MAN_MONTH_AMT"));
					}
				}
			}
			
			
		} else {
			formError.error("AMOUNT_"+monthlyData.getSeq()+"_1",MessageErrorUtil.getText(request,"ERROR_ID_BANK_STATEMENT_AMT"));
		}
		return formError.getFormError();
	}
}
