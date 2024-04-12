package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.control.ProcessForm;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
//import com.eaf.core.ulo.common.properties.ListBoxControl;

@SuppressWarnings("serial")
public class OfficeAddressForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(OfficeAddressForm.class);	
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String ADDRESS_TYPE_WORK_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_WORK_CARDLINK");
	String ACTION_ADD = SystemConstant.getConstant("ACTION_ADD");	
	String ACTION_EDIT = SystemConstant.getConstant("ACTION_EDIT");	
//	String CACHE_COMPANY_CIS = SystemConstant.getConstant("CACHE_COMPANY_CIS");
	@Override
	public Object getObjectForm() {
		logger.debug("getFormObject()..");
	/*	Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}*/
		
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getPersonalInfoObjectForm(request);
		logger.debug("processForm : "+processForm);
		if(ProcessForm.SUP_APPLICANT_VALIDATE.equals(processForm)){
			Object masterObjectForm = FormControl.getMasterObjectForm(request);
			if(masterObjectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
				String personalId = getRequestData("PERSONAL_ID");
				logger.debug("personalId : "+personalId);
				personalInfo = applicationGroup.getPersonalInfoById(personalId);
				if(Util.empty(personalInfo)){
					personalInfo = new PersonalInfoDataM();
				}
			}
		}
		
		logger.debug("personalInfo.getPersonalId()>>"+personalInfo.getPersonalId());
		logger.debug("personalInfo.getMailingAddress()>>"+personalInfo.getMailingAddress());
		HashMap<String,AddressDataM> hashAddress = new HashMap<>();
		
		ArrayList<AddressDataM> addresses = personalInfo.getAddresses();
		if(null == addresses){
			addresses = new ArrayList<AddressDataM>();
		}		
		AddressDataM Address = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == Address){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			Address = new AddressDataM();
			Address.setSeq(-1);
			Address.setAddressType(ADDRESS_TYPE_WORK);
			Address.setAddressId(addressId);
		}
		
		String companyName=Address.getCompanyName();
		if(!Util.empty(companyName)){
			companyName = Util.removeNotAllowSpeacialCharactors(companyName.trim());
			Address.setCompanyName(companyName);
		}
		
		
		logger.debug("SEQ_WORK_ADDRESS >> "+Address.getSeq());
		hashAddress.put(ADDRESS_TYPE_WORK,Address);
		
		AddressDataM CardLinkAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK_CARDLINK);
		if(null == CardLinkAddress){
			CardLinkAddress = new AddressDataM();
			CardLinkAddress.setSeq(-1);
			CardLinkAddress.setAddressType(ADDRESS_TYPE_WORK_CARDLINK);
		}
		logger.debug("SEQ_WORK_CARDLINK_ADDRESS >> "+CardLinkAddress.getSeq());
		hashAddress.put(ADDRESS_TYPE_WORK_CARDLINK,CardLinkAddress);

		return hashAddress;
	}
	@Override
	public String processForm() {
		System.out.println("processForm()..");		
		String ADDRESS_TYPE = getRequestData("ADDRESS_TYPE");
		String SEND_DOC = getRequestData("SEND_DOC");
		String ACTION_TYPE = getRequestData("ACTION_TYPE");
		/*Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}*/
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getPersonalInfoObjectForm(request);
		if(!Util.empty(ADDRESS_TYPE) && !Util.empty(SEND_DOC) && ADDRESS_TYPE.equals(SEND_DOC) && ACTION_ADD.equals(ACTION_TYPE)){
			personalInfo.setMailingAddress(ADDRESS_TYPE);
			personalInfo.setPlaceReceiveCard(ADDRESS_TYPE);
		}
		
		ArrayList<AddressDataM> addresses = personalInfo.getAddresses();
		if(null == addresses){
			addresses = new ArrayList<AddressDataM>();
			personalInfo.setAddresses(addresses);
		}
				
		
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;


		//-------------------currentAddress----------------
		AddressDataM Address = hashAddress.get(ADDRESS_TYPE_WORK);
		String addressId = Address.getAddressId();
		int addressIndex = -1;
		try {
			if (Util.empty(addressId)) {
				addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
				Address.setAddressId(addressId);
			} else {
				addressIndex = personalInfo.getAddressesIndex(addressId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		logger.debug("ADDRESS_ID >> "+addressId);
		logger.debug("ADDRESS_INDEX >> "+addressIndex);
		Address.setEditFlag(MConstant.FLAG_Y);
		if(addressIndex == -1){
			addresses.add(Address);
		}else{
			addresses.set(addressIndex,Address);
		}	
		
//		Default Company CIS Id.
//		String COMPANY_CIS_NAME = Address.getCompanyName()+Address.getCompanyTitle();
//		String companyCisId = ListBoxControl.getName(CACHE_COMPANY_CIS,"VALUE",COMPANY_CIS_NAME,"CODE");
//		logger.debug("companyCisId >> "+companyCisId);
//		personalInfo.setCompanyCISId(companyCisId);
		
		//-------------------currentCardlinkAddress----------------
		AddressDataM CardlinkAddress = hashAddress.get(ADDRESS_TYPE_WORK_CARDLINK);	
		String addressCardlinkId = CardlinkAddress.getAddressId();
		int addressCardlinkIndex = -1;
		try {
			if (Util.empty(addressCardlinkId)) {
				addressCardlinkId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
				CardlinkAddress.setAddressId(addressCardlinkId);
			} else {
				addressCardlinkIndex = personalInfo.getAddressesIndex(addressCardlinkId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		logger.debug("ADDRESS_ID >> "+addressCardlinkId);
		logger.debug("ADDRESS_INDEX >> "+addressCardlinkIndex);
		if(addressCardlinkIndex == -1){
			addresses.add(CardlinkAddress);
		}else{
			addresses.set(addressCardlinkIndex,CardlinkAddress);
		}
		return null;
	}
}
