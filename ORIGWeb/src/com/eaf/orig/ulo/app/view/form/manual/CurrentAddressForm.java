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
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CurrentAddressForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(CurrentAddressForm.class);
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
	String ACTION_ADD = SystemConstant.getConstant("ACTION_ADD");	
	String ACTION_EDIT = SystemConstant.getConstant("ACTION_EDIT");	
	@Override
	public Object getObjectForm() {
		logger.debug("getFormObject()..");
		//#Fix get current screen request
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
//		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
//		if(masterObjectForm instanceof ApplicationGroupDataM){
//			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
//			personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
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
		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		if(null == currentAddress){
			String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
			currentAddress = new AddressDataM();
			currentAddress.setSeq(-1);
			currentAddress.setAddressType(ADDRESS_TYPE_CURRENT);
			currentAddress.setAddressId(addressId);
		}
		logger.debug("SEQ_CURRENT_ADDRESS >> "+currentAddress.getSeq());
		hashAddress.put(ADDRESS_TYPE_CURRENT,currentAddress);		
		AddressDataM currentCardLinkAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(null == currentCardLinkAddress){
			currentCardLinkAddress = new AddressDataM();
			currentCardLinkAddress.setSeq(-1);
			currentCardLinkAddress.setAddressType(ADDRESS_TYPE_CURRENT_CARDLINK);
		}
		logger.debug("SEQ_CURRENT_CARDLINK_ADDRESS >> "+currentCardLinkAddress.getSeq());
		hashAddress.put(ADDRESS_TYPE_CURRENT_CARDLINK,currentCardLinkAddress);
		return hashAddress;
	}
	@Override
	public String processForm() {
		logger.debug("processForm()..");		
		String ADDRESS_TYPE = getRequestData("ADDRESS_TYPE");
		String SEND_DOC = getRequestData("SEND_DOC");
		String ACTION_TYPE = getRequestData("ACTION_TYPE");
//		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
//		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(FormatUtil.getInt(PERSONAL_SEQ));		
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}
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
		AddressDataM currentAddress = hashAddress.get(ADDRESS_TYPE_CURRENT);
		String addressId = currentAddress.getAddressId();
		int addressIndex = -1;
		try {
			if (Util.empty(addressId)) {
				addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
				currentAddress.setAddressId(addressId);
			} else {
				addressIndex = personalInfo.getAddressesIndex(addressId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		logger.debug("ADDRESS_ID >> "+addressId);
		logger.debug("ADDRESS_INDEX >> "+addressIndex);
		currentAddress.setEditFlag(MConstant.FLAG_Y);
		if(addressIndex == -1){
			addresses.add(currentAddress);
		}else{
			addresses.set(addressIndex,currentAddress);
		}	
		//-------------------currentCardlinkAddress----------------
		AddressDataM currentCardlinkAddress = hashAddress.get(ADDRESS_TYPE_CURRENT_CARDLINK);	
		String addressCardlinkId = currentCardlinkAddress.getAddressId();
		int addressCardlinkIndex = -1;
		try {
			if (Util.empty(addressCardlinkId)) {
				addressCardlinkId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
				currentCardlinkAddress.setAddressId(addressCardlinkId);
			} else {
				addressCardlinkIndex = personalInfo.getAddressesIndex(addressCardlinkId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		logger.debug("ADDRESS_ID >> "+addressCardlinkId);
		logger.debug("ADDRESS_INDEX >> "+addressCardlinkIndex);
		if(addressCardlinkIndex == -1){
			addresses.add(currentCardlinkAddress);
		}else{
			addresses.set(addressCardlinkIndex,currentCardlinkAddress);
		}
		return null;
	}
}
