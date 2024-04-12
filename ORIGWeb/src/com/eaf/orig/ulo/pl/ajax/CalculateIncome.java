package com.eaf.orig.ulo.pl.ajax;

import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class CalculateIncome implements AjaxDisplayGenerateInf{
	
	Logger logger = Logger.getLogger(CalculateIncome.class);

	@Override
	public String getDisplayObject(HttpServletRequest request) 	throws PLOrigApplicationException {		
		try{	
			BigDecimal zero = new BigDecimal("0.00");
			
			BigDecimal mainIncome  =  DataFormatUtility.StringToBigDecimal(request.getParameter("salary"));
			BigDecimal loanIncome  =  DataFormatUtility.StringToBigDecimal(request.getParameter("loan-income"));
			BigDecimal otherIncome =  DataFormatUtility.StringToBigDecimal(request.getParameter("other-income"));
			String otherIncomeRemark = request.getParameter("other-income-remark");
			
			BigDecimal totalIncome  = new BigDecimal(0);
			
			totalIncome = totalIncome.add(mainIncome).add(loanIncome).add(otherIncome);
			
			totalIncome.setScale(2,BigDecimal.ROUND_HALF_UP);
			
			String totalIncomeStr = DataFormatUtility.displayCommaNumber(totalIncome);
			
			JsonObjectUtil jObjectUtil = new JsonObjectUtil();
			
			String displayModeIncome = HTMLRenderUtil.DISPLAY_MODE_VIEW;
			String styleOtherIncomeRemark = "textbox-readonly";						
			if(null == otherIncome){
				otherIncome = new BigDecimal("0.00");
			}						
			if(null != otherIncome && otherIncome.compareTo(zero) > 0){
				styleOtherIncomeRemark = "textbox";
				displayModeIncome = HTMLRenderUtil.DISPLAY_MODE_EDIT;
			}else{
				otherIncomeRemark = "";
			}
			
			jObjectUtil.CreateJsonObject("total-income", totalIncomeStr);			
			jObjectUtil.CreateJsonObject("td-other-income-remark", HTMLRenderUtil.displayInputTagScriptAction(otherIncomeRemark,displayModeIncome,"","other-income-remark",styleOtherIncomeRemark,"","200"));
			
			return jObjectUtil.returnJson();
			
		}catch (Exception e) {
			logger.fatal("[Error] ",e);
		}		
		return "";
	}

}
