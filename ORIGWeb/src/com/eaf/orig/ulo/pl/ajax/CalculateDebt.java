package com.eaf.orig.ulo.pl.ajax;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class CalculateDebt implements AjaxDisplayGenerateInf{
	
	Logger logger = Logger.getLogger(CalculateIncome.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		try{
			BigDecimal zero = new BigDecimal("0.00");
//			BigDecimal ncbDebt  =  DataFormatUtility.StringToBigDecimal(request.getParameter("ncb-debt"));
			
			BigDecimal ncbconsumer = DataFormatUtility.StringToBigDecimal(request.getParameter("ncb-consumer"));
			BigDecimal ncbcommercial = DataFormatUtility.StringToBigDecimal(request.getParameter("ncb-commercial"));
			BigDecimal ncbDebt = new BigDecimal(0);
					   ncbDebt = ncbDebt.add(ncbconsumer).add(ncbcommercial);
						
			BigDecimal otherDebt  =  DataFormatUtility.StringToBigDecimal(request.getParameter("other-debt"));
			String otherDebtRemark = request.getParameter("other-debt-remark");
			
			BigDecimal totalDebt = new BigDecimal(0);					
					  totalDebt = totalDebt.add(ncbDebt).add(otherDebt);
			
			totalDebt.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			String totalDebtStr = DataFormatUtility.displayCommaNumber(totalDebt);
			
			String styleOtherncbRemark = "textbox-readonly";						
			String displayModeOtherDebt = HTMLRenderUtil.DISPLAY_MODE_VIEW;	
			if(null != otherDebt && otherDebt.compareTo(zero) > 0){
				styleOtherncbRemark = "textbox";
				displayModeOtherDebt = HTMLRenderUtil.DISPLAY_MODE_EDIT;
			}else{
				otherDebtRemark = "";
			}	
			
			JsonObjectUtil jsonObjectUtil = new JsonObjectUtil();
			
			jsonObjectUtil.CreateJsonObject("total-debt", totalDebtStr);
			jsonObjectUtil.CreateJsonObject("td-other-debt-remark",HTMLRenderUtil.displayInputTagScriptAction(otherDebtRemark,displayModeOtherDebt,"","other-debt-remark",styleOtherncbRemark,"","200"));
			
			return jsonObjectUtil.returnJson();
			
		}catch (Exception e) {
			logger.fatal("[Error] ",e);
		}
		return "";
	}

}
