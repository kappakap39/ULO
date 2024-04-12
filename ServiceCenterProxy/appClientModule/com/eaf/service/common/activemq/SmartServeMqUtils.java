package com.eaf.service.common.activemq;

import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.activemq.constant.SmartServeConstant;
import com.eaf.service.common.activemq.model.SSContactPerson;
import com.eaf.service.common.activemq.model.SSHeaderRequest;
import com.eaf.service.common.activemq.model.SSRequiredDoc;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.util.ServiceUtil;

public class SmartServeMqUtils {


	public static SSHeaderRequest createHeaderRequest(String userId, String funcNm,String appId,String serviceReqResId) {
		SSHeaderRequest header = new SSHeaderRequest();
		header.setFuncNm(funcNm);
		header.setRqUID(ServiceUtil.generateRqUID(ServiceCache.getGeneralParam("KBANK_APP_ID"),appId));
		header.setRqDt(ServiceApplicationDate.getCalendar());
		header.setRqAppId(appId);
		header.setUserId(userId);
		header.setCorrID(serviceReqResId);
		header.setUserLangPref(SystemConstant.getConstant("LOCAL_TH"));
	
		return header;
	}

	public static String mappingDocType(String docType) {
		if (SystemConstant.getConstant("CIDTYPE_IDCARD").equals(docType)) {
			return "11";
		} else if (SystemConstant.getConstant("CIDTYPE_PASSPORT").equals(docType)) {
			return "12";
		} else if (SystemConstant.getConstant("CIDTYPE_MIGRANT").equals(docType)) {
			return "13";
		} else {
			return "99";
		}
	}
	
	public static List<SSContactPerson> generateContactPerson(List<PersonalInfoDataM> customerNRelateds) throws Exception {
		List<SSContactPerson> contactPersonInfo = new ArrayList<>();
		if (MyCommonUtils.isCollectionNotEmpty(customerNRelateds)) {
			for (PersonalInfoDataM nRelated : customerNRelateds) {
				if (nRelated != null) {
					SSContactPerson contactPersonBean = new SSContactPerson();

					contactPersonBean.setFullName(nRelated.getThName());
					contactPersonBean.setPhoneNo(ServiceUtil.displayText(nRelated.getMobileNo()));
					contactPersonBean.setRelationship("mom");
					for (AddressDataM address : nRelated.getAddresses()) {
						contactPersonBean.setAddress(SmartServeMqUtils.genAddressLine(address));
						break;
					}
					contactPersonBean.setEmail(ServiceUtil.displayText(nRelated.getEmailPrimary()));
					contactPersonInfo.add(contactPersonBean);
				}
			}
		}
		return contactPersonInfo;
	}
	public static String genAddressLine(AddressDataM address) {
		String addressLine = nvl(address.getAddress()) + nvlWithSpace(address.getSoi()) + nvlWithSpace(address.getMoo()) + nvlWithSpace(address.getRoad()) + nvlWithSpace(address.getAmphur()) + nvlWithSpace(address.getTambol()) + nvlWithSpace(address.getProvinceDesc()) + nvlWithSpace(address.getZipcode());
		return addressLine;
	}
	public static String nvl(String data) {
		return MyStringUtils.isNotEmpty(data) ? data : "";
	}

	public static String nvlWithSpace(String data) {
		return MyStringUtils.isNotEmpty(data) ? " " + data : "";
	}
}
