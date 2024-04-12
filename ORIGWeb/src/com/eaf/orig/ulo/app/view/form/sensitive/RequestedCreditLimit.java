package com.eaf.orig.ulo.app.view.form.sensitive;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.FieldConfigDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class RequestedCreditLimit extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(RequestedCreditLimit.class);
	String REKEY_FORM = SystemConstant.getConstant("REKEY_FORM");
	String FIELD_ID_APPLICATION_CARD_TYPE = SystemConstant.getConstant("FIELD_ID_APPLICATION_CARD_TYPE");
	@Override
	public String getElement(HttpServletRequest request,Object objectElement) {		
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = ((CompareDataM)objectElement);		
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);		
		if(null == application){
			application = new ApplicationDataM();
		}
		PersonalInfoDataM personalInfoM=  applicationGroup.getPersonalInfoByRelation(application.getApplicationRecordId());	
		if(null==personalInfoM){
			personalInfoM = new PersonalInfoDataM();
		}
 		CardDataM card = application.getCard();
 		if(Util.empty(card)){
 			card = new CardDataM();
 		}
 		
 		String CARD_ENCRP = card.getCardNo();
 		String CARD_NO = "";
 		if(!Util.empty(CARD_ENCRP)){
 			Encryptor enc = EncryptorFactory.getDIHEncryptor();
 			try{
				CARD_NO = enc.decrypt(CARD_ENCRP);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
 		}
		String elementId = FormatUtil.getPersonalElementId(personalInfoM);
		String tagId = HtmlUtil.elementTagId(compareData.getFieldNameType(), elementId);
		StringBuffer HTML = new StringBuffer();
		
		HTML.append("<div class='col-xs-12'>")
			.append("<table class='table table-striped' style='margin-top:12px;'>");
			
		HTML.append("<tr>")
			.append("<td class='rekey-td-boder bold' width='20%'>"+LabelUtil.getText(request, "PRODUCT_"+application.getProduct())+"</td>")
			.append("<td class='bold'  width='40%'>"+FormatUtil.getCardNo(CARD_NO)+"</td>")
			.append("<td class='bold'  width='20%'>"+ListBoxControl.getName(FIELD_ID_APPLICATION_CARD_TYPE,"CHOICE_NO",card.getApplicationType(),"DISPLAY_NAME")+"</td>")
			.append("<td class='bold' width='20%'>"+card.getMainCardHolderName()+"</td>")
			.append("</tr>");
		
		HTML.append("<tr>")
			.append("<td class='rekey-td-boder' colspan='4'>")
			.append("<div class='row'>");		
		HTML.append("<div class='col-sm-12'>")
			.append("<div class='form-group'>")
			.append(HtmlUtil.getFieldLabel(request, "IC_CURRENT_CREDIT_LIMIT", "col-sm-4 col-md-5 control-label"))
			.append(HtmlUtil.currencyBox(CompareSensitiveFieldUtil.getElementName(compareData),CompareSensitiveFieldUtil.getElementSuffix(compareData), null, "",true,"9", 
				HtmlUtil.EDIT,tagId, "col-sm-8 col-md-7",request))
			.append("</div></div>");
		
		HTML.append("</div>")
			.append("</td>")
			.append("</tr>");
		
		HTML.append("</table></div>");		
		HTML.append("<div class='clearfix'></div>");		
		return HTML.toString();
	}	
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();		
		if(!Util.empty(applications)) {
			for(ApplicationDataM applicationM: applications) {
				PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationM.getApplicationRecordId());
				CardDataM cardM = applicationM.getCard();
				String cardNoRefId = CompareSensitiveFieldUtil.getCardNoRefId(personalInfoM,applicationM.getProduct(),cardM);
				LoanDataM loanData = applicationM.getLoan();
				if(!Util.empty(loanData)) {
					CompareDataM compareData  = new CompareDataM();
					compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					compareData.setFieldNameType(getImplementId());
					compareData.setValue(FormatUtil.display(loanData.getRequestLoanAmt()));
					compareData.setRole(roleId);
					compareData.setRefId(cardNoRefId);
					compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION);
					compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
					compareData.setUniqueId(applicationM.getApplicationRecordId());
					compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION);
					compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
					compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,applicationM.getProduct(),cardM.getCardNo()));
					compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));

					compareList.add(compareData);
				}
			}
		}
		return compareList;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM application = CompareSensitiveFieldUtil.getApplicationByUniqueId(applicationGroup, compareData);
		if(!Util.empty(application)) {
			LoanDataM loanData = application.getLoan();
			if(!Util.empty(loanData)) {
				String paraName = CompareSensitiveFieldUtil.getParameterName(compareData);
				logger.debug("paraName>>"+paraName);
				BigDecimal reqLoanAmt =FormatUtil.toBigDecimal(request.getParameter(paraName),true);
				loanData.setRequestLoanAmt(reqLoanAmt);
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		/*String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		ModuleFormHandler RekeyForm = (ModuleFormHandler)request.getSession().getAttribute(REKEY_FORM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)RekeyForm.getObjectForm();		
		CompareDataM compareData = (CompareDataM)objectElement;
		ApplicationDataM linkedAppM = CompareSensitiveFieldUtil.getApplicationCardNoByRefId(applicationGroup, compareData);
		if(linkedAppM == null) {
			linkedAppM = new ApplicationDataM();
		}
		LoanDataM loanData = linkedAppM.getLoan();
		if(Util.empty(loanData) || Util.empty(loanData.getRequestLoanAmt())) {
			formError.error(compareData.getFieldName(),PREFIX_ERROR+LabelUtil.getText(request,"IC_CURRENT_CREDIT_LIMIT"));
		}*/
		return formError.getFormError();
	}
	
	@Override
	public ArrayList<CompareFieldElement> compareFieldElement(HttpServletRequest request, Object objectForm) {
		ArrayList<CompareFieldElement> compareFieldElementList = new ArrayList<CompareFieldElement>();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);	
		ArrayList<CompareDataM> currCompareDataMList = (ArrayList<CompareDataM>)objectForm;
		if(!Util.empty(currCompareDataMList)){
			for(CompareDataM currentCompareData : currCompareDataMList){
				String currentFieldName = currentCompareData.getFieldName();
				String currentFieldNameType = currentCompareData.getFieldNameType();
				logger.debug("currentFieldName>>>"+currentFieldName);
				logger.debug("currentFieldNameType>>>"+currentFieldNameType);
				CompareDataM prevCompareData  = this.prevCompareDataMRequestedCreditCard(applicationGroup, currentCompareData);
				if(!Util.empty(prevCompareData)){
					boolean isSame = CompareObject.compare(currentCompareData.getValue(), prevCompareData.getValue(),currentCompareData.getCompareFlag());
					logger.debug("isSame>>>>"+isSame);
					if(!isSame) {
						currentCompareData.setCompareFlag(MConstant.FLAG.YES);
						currentCompareData.setOldValue(prevCompareData.getValue());
						
						CompareFieldElement compareFieldElement = new CompareFieldElement();
						compareFieldElement.addCompareDatas(currentCompareData);
						compareFieldElement.setCompareFieldType(CompareSensitiveFieldUtil.getCompareElementFieldType(currentCompareData));
						compareFieldElement.setImplementId(currentFieldNameType);
						compareFieldElementList.add(compareFieldElement);
					}
				}
			}
		}		
		return compareFieldElementList;
	}
	
	
  private  CompareDataM prevCompareDataMRequestedCreditCard(ApplicationGroupDataM applicationGroup,CompareDataM currCompareData){
		FieldConfigDataM currFieldConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(currCompareData.getConfigData());      
		ComparisonGroupDataM prevRoleData = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);		
		HashMap<String,CompareDataM> prevCompareList = prevRoleData.getComparisonFields();
		if(!Util.empty(prevCompareList)){
			for(CompareDataM preCompareData : prevCompareList.values()){
				FieldConfigDataM preFieldConfigData =  CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(preCompareData.getConfigData());
				String prePersonalType = preFieldConfigData.getPersonalType();
				String preProduct = preFieldConfigData.getProduct();
				int preSeq = preFieldConfigData.getSeq();
				String prevCardNumber = encryptorCard(preFieldConfigData.getCardNumber());
				String currCardNumber = encryptorCard(currFieldConfigData.getCardNumber());
				if(currCompareData.getFieldNameType().equals(preCompareData.getFieldNameType()) && 
						currFieldConfigData.getPersonalType().equals(prePersonalType) &&
						currFieldConfigData.getProduct().equals(preProduct)&&
						currFieldConfigData.getSeq()==preSeq && 
						currCardNumber.equals(prevCardNumber)){
					return preCompareData;
				}
			}
			
		}
		
		return null;
	}

	private String encryptorCard(String cardNoEncrp){
			Encryptor enc = EncryptorFactory.getDIHEncryptor();
		try{
			 if(!Util.empty(cardNoEncrp)) {
				 return enc.decrypt(cardNoEncrp);
			 }
		}catch(Exception e){
		}
		return "";
	}
}
