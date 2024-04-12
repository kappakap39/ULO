package com.eaf.orig.ulo.app.view.form.addressphone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;

public class CardLinkPhoneNo extends ElementHelper {
	String ADDRESS_TYPE_WORK_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_WORK_CARDLINK");
	String VERIFY_CUSTOMER_DISPLAY = SystemConstant.getConstant("VERIFY_CUSTOMER_DISPLAY");	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_CODE_CC = SystemConstant.getConstant("PRODUCT_CODE_CC");	
	String KEY_CARD_LINK_CUST_NO ="CC_CST_NO";
	static String KEY_CARD_LINK_ORG_NO ="CC_CST_ORG_NO";
	private static transient Logger logger = Logger.getLogger(CardLinkPhoneNo.class);
	@Override
	public String getElement(HttpServletRequest request, Object objectElement){
		StringBuilder HTML = new StringBuilder();
		ApplicationGroupDataM applicationGroupData = FormControl.getOrigObjectForm(request);
		DIHProxy proxy = new DIHProxy();
		ApplicationDataM applicationDataM = applicationGroupData.getApplicationProduct(PRODUCT_CODE_CC);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroupData);
		if(!Util.empty(applicationDataM)){
			 CardDataM card = applicationDataM.getCard();
			 if(!Util.empty(card)){
				String encMainCard =  card.getMainCardNo();
				logger.debug("encMainCard : "+encMainCard);
				DIHQueryResult<CardLinkDataM> queryResult = proxy.getCardLinkInfoENCPT(encMainCard);
				CardLinkDataM cardLinkInfo = queryResult.getResult();
				if(!Util.empty(cardLinkInfo) && !Util.empty(cardLinkInfo.getPhoneNo())){
					personalInfo.addVerCusPhoneNo(cardLinkInfo.getPhoneNo());
					HTML.append(" <tr> ")
						.append(" 	<td width='250px'>")
						.append(		HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "","", "", ADDRESS_TYPE_WORK_CARDLINK, HtmlUtil.EDIT, "", 
										FormatUtil.display(HtmlUtil.getText(request, "PRIMARY_CARD") )+" "+
									    HtmlUtil.getText(request, "PHONE_CARD_LINK"), "col-sm-8 col-md-7", request))
									.append(" 	</td>")
									.append(" 	<td width='150px'>"+FormatUtil.getMobileNo(cardLinkInfo.getPhoneNo())+"</td>");
					HTML.append(" <td></td>")
						.append(" <td></td>");
					HTML.append(		HtmlUtil.hidden("PHONE_NO",FormatUtil.getMobileNo(cardLinkInfo.getPhoneNo())))
						.append(" </tr> ");	
				}					
			 }
		}
		return HTML.toString();
	}
	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		String DISPLAY = (String)getObjectRequest();
		if(VERIFY_CUSTOMER_DISPLAY.equals(DISPLAY)){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			if(!PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
				FLAG = MConstant.FLAG.YES;
			}
		}
		return FLAG;
	}
}
