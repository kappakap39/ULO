package com.eaf.orig.ulo.app.view.form.subform.increase.manual;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
@Deprecated
public class AddCardRequestInfoSubForm1 implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddCardRequestInfoSubForm1.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
//		// TODO Auto-generated method stub
//		String cardNo = request.getParameter("CARD_NO");
//		String function = request.getParameter("FUNCTION");
//		logger.debug("setProperties >> CardRequestInfoSubForm1");
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		ArrayList<ApplicationDataM> appListM=	applicationGroup.getApplications();
//		if(Util.empty(appListM)){
//			ApplicationDataM application = new ApplicationDataM();
//			appListM  = new ArrayList<ApplicationDataM>();
//			appListM.add(application);
//			applicationGroup.setApplications(appListM);
//
//		}
//		
//		if(function.equals("addCardNo")){
//			if(!Util.empty(appListM)){
//				for(ApplicationDataM appM :appListM){
//					LoanDataM newloanM = new LoanDataM();
//					CardDataM cardM = new CardDataM();
//					if(!Util.empty(cardNo)){
//						cardM.setCardNo(cardNo);
//						newloanM.setCard(cardM);
//						appM.addLoan(newloanM);			
//					}
//							
//				}
//			}
//		}
//		else if (function.equals("delCardNo")){
//			logger.debug("delCardNo .... ");
//			if(!Util.empty(appListM)){
//				for(ApplicationDataM application :appListM){
//					ArrayList<LoanDataM> loans= application.getLoans();
//						if(!Util.empty(loans)){
//							for(int i=0;i<loans.size();i++){
//								CardDataM card = loans.get(i).getCard();
//								if(!Util.empty(card) && card.getCardNo().equals(cardNo)){
//									loans.remove(i);
//									logger.debug("Card NO Remove :"+card.getCardNo());
//								}
//							}
//						}
//							
//				}
//			}
//			
//		}
//		
	
		return null;
	}

}
