package com.eaf.orig.ulo.app.view.form.subform.product.manual;

//import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
//import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
//import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.CardDataM;
//import com.eaf.orig.ulo.model.app.LoanDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@Deprecated
public class ProductUtil  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ProductUtil.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		/*logger.debug("ProductUtil.....");
		String function = request.getParameter("FUNCTION");
		String product = request.getParameter("PRODUCT_TYPE");
		String cardId = request.getParameter("CARD_ID");
	
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup =	ORIGForm.getObjectForm();
		ArrayList<ApplicationDataM> applications =applicationGroup.getApplications();
			 if(function.equals("delProduct")){
				logger.debug("product :"+product);
				logger.debug("cardId :"+cardId);
			
				if(!Util.empty(applications)){	
					for(int i=0;i<applications.size();i++){
						ArrayList<LoanDataM> loans = 	applications.get(i).getLoans();
						if(!Util.empty(loans)){
							for(LoanDataM loan :loans){
								CardDataM card= loan.getCard();
								if(null !=card.getCardId() && card.getCardId().equals(cardId)){
									applications.remove(i);
								}
								
							}
						}
					}
				}
			}
			
			*/
		return null;
	}

}
