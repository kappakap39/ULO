package com.eaf.orig.ulo.app.view.form.card.inc.kec;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class KecCardInfo extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(KecCardInfo.class);
	private static final String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/increase/KecCardInfo.jsp?PRODUCT="+PRODUCT_K_EXPRESS_CASH;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request, Object objectElement) {
		String FLAG = MConstant.FLAG.YES;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectElement;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(Util.empty(applications)){
			FLAG = MConstant.FLAG.NO;
		}
		return FLAG;
	}
	
	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectElement;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationM : applications){
				String applicationRecordId = applicationM.getApplicationRecordId();
		 		LoanDataM loan = applicationM.getLoan();
				String REQUEST_LOAN_AMT = request.getParameter("REQUEST_LOAN_AMT_"+applicationRecordId);
		 		loan.setRequestLoanAmt(FormatUtil.toBigDecimal(REQUEST_LOAN_AMT,true));
		 	}
		}
		return null;
	}
	
	@Override
	public void displayValueElement(HttpServletRequest request, Object objectElement, FormDisplayValueUtil formValue) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectElement;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applications)){
			for(ApplicationDataM  applicationDataM : applications){
				LoanDataM loanData = applicationDataM.getLoan();
				if(!Util.empty(loanData)){
					String SUFFIX_ELEMENT_ID=CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.APPLICATION,applicationDataM.getApplicationRecordId());
					 loanData.setRequestLoanAmt(formValue.getValue("REQUEST_LOAN_AMT","REQUESTED_CREDIT_LIMIT_"+SUFFIX_ELEMENT_ID,loanData.getRequestLoanAmt()));
					}
				}
			}
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"REQUESTED_CREDIT_LIMIT"};
		try {
			ApplicationDataM applicationM = ((ApplicationDataM)objectForm);	
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationM.getApplicationRecordId());
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(personalInfoM.getPersonalId());
				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfoM));
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
