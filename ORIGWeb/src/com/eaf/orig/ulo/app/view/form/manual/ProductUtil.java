package com.eaf.orig.ulo.app.view.form.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;

@Deprecated
public class ProductUtil  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(ProductUtil.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
//		logger.debug("ProductUtil.....");
//		String function = request.getParameter("FUNCTION");
//		String product = request.getParameter("PRODUCT_TYPE");
//		String cardId = request.getParameter("CARD_ID");
//		String cardType ="Kbank-Visa AMWAY (Chip Card)";
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
//		ApplicationGroupDataM appGM =	ORIGForm.getObjectForm();
//		ArrayList<ApplicationDataM> appListM =appGM.getApplications();
//		logger.debug("Mode :"+function);
//		int PERSONAL_SEQ = 1;
//		PersonalInfoDataM personalinfo = appGM.getPersonalInfo(PERSONAL_SEQ);
//		String ERROR = "";
//		if(Util.empty(personalinfo)){
//			personalinfo = new PersonalInfoDataM();
//			appGM.addPersonalInfo(personalinfo);
//		}
//		
//		
//		
//		logger.debug("PersonalId >>> "+personalinfo.getPersonalId());
//		if(product.equals("CC")){
//			product ="KCC";
//		}
//		
//		if(Util.empty(appGM)){
//			appGM = new ApplicationGroupDataM();
//		}
//		if(Util.empty(appListM)){
//			appListM = new ArrayList<ApplicationDataM>();
//		}
//		
//			if( function.equals("addProduct")){
//				ApplicationDataM appM = new ApplicationDataM();
//				LoanDataM loanM = new LoanDataM();
//				CardDataM cardM = new CardDataM();
//				logger.debug("product :"+product);
//				logger.debug("cardId :"+cardId);
//				cardM.setCardType(cardType);
//				cardM.setCardId(cardId);
//				cardM.setApplicationType(CardDataM.applicationType.BORROWER);
//				cardM.setPersonalId("05");
//				loanM.setCard(cardM);
//				appM.setBusinessClassId(product);
//				appM.addLoan(loanM);
//				appM.setApplicationRecordId("001");
//				appListM.add(appM);
//				appGM.setApplications(appListM);
//				
//				personalinfo.setThFirstName("TmpName");
//				personalinfo.setThLastName("TmpLastName");
//				personalinfo.setPersonalId("05");
//				personalinfo.setSeq(PERSONAL_SEQ);
//				personalinfo.setPersonalType(PersonalInfoDataM.PersonalType.APPLICANT);
//			
//				
//				
//			}
//			else if(function.equals("delProduct")){
//				logger.debug("product :"+product);
//				logger.debug("cardId :"+cardId);
//			
//				if(!Util.empty(appListM)){	
//					for(int i=0;i<appListM.size();i++){
//						ArrayList<LoanDataM> loans = 	appListM.get(i).getLoans();
//						if(!Util.empty(loans)){
//							for(LoanDataM loan :loans){
//								CardDataM card= loan.getCard();
//								if(null !=card.getCardId() && card.getCardId().equals(cardId)){
//									appListM.remove(i);
//								}
//								
//							}
//						}
//					}
//				}
//			}
//			
//			else if(function.equals("addProductSup")){
//				String refAppID =request.getParameter("REF_APPLICATION_RECORD_ID");
//				ApplicationDataM appM = new ApplicationDataM();
//				LoanDataM loanM = new LoanDataM();
//				CardDataM cardM = new CardDataM();
//				logger.debug("cardId :"+cardId);
//				cardM.setCardType(cardType);
//				cardM.setCardId(cardId);
//				cardM.setApplicationType(CardDataM.applicationType.SUPPLEMENTARY);
//				cardM.setPersonalId("011");
//				loanM.setCard(cardM);
//				logger.debug("getApplicationType :"+cardM.getApplicationType());
//				appM.setBusinessClassId(product);
//				appM.addLoan(loanM);
//				appM.setRefApplicationRecordId(refAppID);
//				appListM.add(appM);
//
//			}
		return null;
	}

}
