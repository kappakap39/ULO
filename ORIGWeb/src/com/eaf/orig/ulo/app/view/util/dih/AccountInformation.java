package com.eaf.orig.ulo.app.view.util.dih;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
//import com.eaf.j2ee.pattern.control.ResponseDataController;
//import com.eaf.j2ee.pattern.util.JSONUtil;
//import com.eaf.orig.ulo.model.error.ErrorResponseDataM;

public class AccountInformation implements AjaxInf{
//	private static transient Logger logger = Logger.getLogger(AccountInformation.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
//		logger.debug("CustomerInformation.....");		
//		ArrayList<ErrorResponseDataM> errors = new ArrayList<>();
//		JSONUtil json = new JSONUtil();	
//		SQLQueryEngine QueryEngine = new SQLQueryEngine();
//		DIHProxy dih = new DIHProxy();
//		
//		String TYPE_ACCOUNT = SystemConstant.getConstant("TYPE_ACCOUNT");
//		String FORM_NAME_ORIG = SystemConstant.getConstant("FORM_NAME_ORIG");
//		String FORM_NAME_ENTITY = SystemConstant.getConstant("FORM_NAME_ENTITY");
//		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
//		
//		String FUNCTION = request.getParameter("FUNCTION");
//		String ACCOUNT_NO = request.getParameter("ACCOUNT_NO");
//		String FORM_NAME = request.getParameter("FORM_NAME");
//
//		logger.debug("ACCOUNT_NO : "+ACCOUNT_NO);	
//		logger.debug("FORM_NAME : "+FORM_NAME);		
//		
//		if (TYPE_ACCOUNT.equals(FUNCTION)) {
//			ArrayList<PersonalInfoDataM> listPersonal = new ArrayList<PersonalInfoDataM>();
//			Vector<HashMap> informations = new Vector<HashMap>();
//			PaymentMethodDataM paymentMethod = new PaymentMethodDataM();
//
//			if(FORM_NAME_ORIG.equals(FORM_NAME)){ //FORM_NAME_ORIG
//				String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
//				ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
//				ApplicationGroupDataM applicationGroupM = ORIGForm.getObjectForm();
//				paymentMethod = applicationGroupM.getPaymentMethodByProduct(PERSONAL_TYPE,PRODUCT_K_EXPRESS_CASH);
//			} else { //FORM_NAME_ENTITY
//				EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
//				HashMap<String,Object> supplementaryApplicant = (HashMap<String,Object>)EntityForm.getObjectForm();	
//				if(null == supplementaryApplicant){
//					supplementaryApplicant = new HashMap<>();
//				}
//				paymentMethod = (PaymentMethodDataM)supplementaryApplicant.get("PAYMENTMETHOD");
//			}
//			
//			informations = dih.getAccountInfomation("");
//						
//			if (!Util.empty(informations)) {
//				for (HashMap information : informations) {
//					if(!Util.empty(information.get("IP_ID"))){
//						logger.debug("Set json : PersonalInfoDataM");
//
//						String CIS_NUMBER = SQLQueryEngine.display(information, "IP_ID");
//						String TH_TITLE_CODE = SQLQueryEngine.display(information, "TH_TTL");
//
//						paymentMethod.setAccountName("");
//						
//						json.put("CIS_NUMBER", CIS_NUMBER);							
//						
//					}
//				}
//			}
//		}
//		return ResponseDataController.response(json.getJSON(), errors);
		return null;
	}
}
