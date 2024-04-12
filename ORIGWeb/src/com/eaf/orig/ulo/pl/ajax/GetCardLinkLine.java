package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class GetCardLinkLine implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(GetCardLinkLine.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException{
		
		String mailing = request.getParameter("mailling");
		
		logger.debug("[mailing] = " + mailing);

		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		PLApplicationDataM applicationM = origForm.getAppForm();
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		Vector<PLAddressDataM> vAddress = personalM.getAddressVect();
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		
		GetJsonCardLinkAddress(request, jObj, mailing, vAddress,personalM.getDepartment());
		
		return jObj.returnJson();
	}
	
	public static void GetJsonCardLinkAddress(HttpServletRequest request , JsonObjectUtil jObj ,String mailing ,Vector<PLAddressDataM> vAddress,String department){
//		String objAddress1 = "";
//		String objAddress2 = "";
		
		String objCardLink1 = "";
		String objCardLink2 = "";
		
		if(!OrigUtil.isEmptyString(mailing)){
			if(!OrigUtil.isEmptyVector(vAddress)){
				for(PLAddressDataM addresM : vAddress){			
					if(addresM.getAddressType()!= null && addresM.getAddressType().equals(mailing)){
						objCardLink1 = AddressUtil.GetCardLinkLine1(request, addresM, department);
						objCardLink2 = AddressUtil.GetCardLinkLine2(request, addresM);
						break;
					}
				}
			}
		}
			
//		#septem comment change logic get address send email
//		if(!OrigUtil.isEmptyString(objAddress1)){
//			if(objAddress1.length()<50){
//				objCardLink1 = objAddress1.substring(0, objAddress1.length());
//			}else{
//				objCardLink1 = objAddress1.substring(0, 50);
//			}
//		}
//		if(!OrigUtil.isEmptyString(objAddress2)){
//			if(objAddress2.length()<40){
//				objCardLink2 = objAddress2.substring(0, objAddress2.length());
//			}else{
//				objCardLink2 = objAddress2.substring(0, 40);
//			}
//		}
		
		jObj.CreateJsonObject("card_link_address1", HTMLRenderUtil.replaceNull(objCardLink1));
		jObj.CreateJsonObject("card_link_address2", HTMLRenderUtil.replaceNull(objCardLink2));
	}
	
	
}
