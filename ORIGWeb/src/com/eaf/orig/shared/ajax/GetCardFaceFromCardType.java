/*
 * Created on Dec 12, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.CardFaceProperties;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.AjaxUtils;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GetCardFaceFromCardType implements AjaxDisplayGenerateInf {
	static Logger log = Logger.getLogger(GetCardFaceFromCardType.class);

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf#getDisplayObject(javax.servlet.http.HttpServletRequest)
	 */
	public String getDisplayObject(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String cardType = (String)request.getParameter("cardType");
		String displayMode = (String)request.getParameter("displayMode");
		log.debug("getDisplayObject cardType = "+cardType+" : displayMode = "+displayMode);
        if(cardType!=null && !"".equals(cardType)){
            ORIGUtility utility = new ORIGUtility();
            CardFaceProperties cardFaceObj = new CardFaceProperties();
            Vector vCardFace = utility.loadCacheByName("CardFace");
            Vector vCardFaceTmp = new Vector();
            String xmlObj = "";
            
			for (int i = 0; i < vCardFace.size(); i++) {
				cardFaceObj = (CardFaceProperties) vCardFace.get(i);
				log.debug("compare with : "+cardFaceObj.getCardTypeId());
			    if(cardType.equals(cardFaceObj.getCardTypeId())){
			        vCardFaceTmp.add(cardFaceObj);
			    }
			}
			
			xmlObj = ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(vCardFaceTmp,"" ,"cardFace",displayMode," style=\"width:80%;\" textbox");

			AjaxUtils util = new AjaxUtils();			
			String xmlStr = util.getXML4OneItem("div_cardFace",xmlObj);
			return xmlStr;
        }
		return null;
	}
}
