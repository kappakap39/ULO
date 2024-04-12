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
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class HomeAddressForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(HomeAddressForm.class);
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");
	String ACTION_ADD = SystemConstant.getConstant("ACTION_ADD");	
	String ACTION_EDIT = SystemConstant.getConstant("ACTION_EDIT");	
	@Override
	public Object getObjectForm(){
		logger.debug("getFormObject()..");
//		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
//		PersonalInfoDataM personalInfo = null;
//		if(masterObjectForm instanceof ApplicationGroupDataM){
//			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
//			personalInfo =PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
//		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
//			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
//			personalInfo = personalApplicationInfo.getPersonalInfo();
//		}
		
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
		
		HashMap<String,AddressDataM> hashAddress = new HashMap<>();
		
		ArrayList<AddressDataM> addresses = personalInfo.getAddresses();
		if(null == addresses){
			addresses = new ArrayList<AddressDataM>();
		}		
		AddressDataM Address = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
		if(null == Address){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			Address = new AddressDataM();
			Address.setSeq(-1);
			Address.setAddressType(ADDRESS_TYPE_DOCUMENT);
			Address.setAddressId(addressId);
		}
		logger.debug("SEQ_DOCUMENT_ADDRESS >> "+Address.getSeq());
		hashAddress.put(ADDRESS_TYPE_DOCUMENT,Address);
		
		AddressDataM CardLinkAddress = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT_CARDLINK);
		if(null == CardLinkAddress){
			CardLinkAddress = new AddressDataM();
			CardLinkAddress.setSeq(-1);
			CardLinkAddress.setAddressType(ADDRESS_TYPE_DOCUMENT_CARDLINK);
		}
		logger.debug("SEQ_DOCUMENT_CARDLINK_ADDRESS >> "+CardLinkAddress.getSeq());
		hashAddress.put(ADDRESS_TYPE_DOCUMENT_CARDLINK,CardLinkAddress);
		return hashAddress;
	}
	@Override
	public String processForm() {
		logger.debug("processForm()..");		
		String ADDRESS_TYPE = getRequestData("ADDRESS_TYPE");
		String SEND_DOC = getRequestData("SEND_DOC");
		String ACTION_TYPE = getRequestData("ACTION_TYPE");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		/*Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}*/
		PersonalInfoDataM personalInfo =PersonalInfoUtil.getPersonalInfoObjectForm(request);
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
		AddressDataM Address = hashAddress.get(ADDRESS_TYPE_DOCUMENT);
		String addressId = Address.getAddressId();
		int addressIndex = -1;
		try {
			if (Util.empty(addressId)) {
				addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
				Address.setAddressId(addressId);
				Address.setCreateBy(userM.getUserNo());
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
		//-------------------currentCardlinkAddress----------------
		AddressDataM CardlinkAddress = hashAddress.get(ADDRESS_TYPE_DOCUMENT_CARDLINK);
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
