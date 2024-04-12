package com.eaf.orig.ulo.pl.app.utility;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

public class SavingUtil {
	Logger logger = Logger.getLogger(AddressUtil.class);
	
	public String getSavingHTML(String type, String bank, String account_no, String balance){
		StringBuilder result = new StringBuilder();
		result.append("<tr name='saving_details'>");
		result.append("<td class='jobopening2' id='checkNo' width='6%' align='center'>"+HTMLRenderUtil.displayCheckBox("", "saving", "", "") +"</td>"); 
		result.append("<td class='jobopening2' id='saving_No'  width='10%' align='center'>"+HTMLRenderUtil.displayHTML("01") +"</td>");
		result.append("<td class='jobopening2' id='saving_type'  width='9%'>"+ HTMLRenderUtil.displayHTMLFieldIDDesc(type,75)+ "</td>"); 
		result.append("<td class='jobopening2' id='saving_bank'  width='15%'>"+ HTMLRenderUtil.displayHTMLFieldIDDesc(bank,25)+ "</td> ");
		result.append("<td class='jobopening2' id='account_no' width='26%'>"+account_no+ "</td>");
		result.append("<td class='jobopening2' id='saving_avg_current'>"+ balance +"</td>");
		result.append("</tr>");
		
		return result.toString();
	}
}
