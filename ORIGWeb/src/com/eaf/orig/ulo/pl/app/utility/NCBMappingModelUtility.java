package com.eaf.orig.ulo.pl.app.utility;

import java.util.Date;
import java.util.Vector;
import com.eaf.ncb.model.NCBDataM;
import com.eaf.ncb.model.input.IDEnquiryM;
import com.eaf.ncb.model.input.NCBInputDataM;
import com.eaf.ncb.model.input.PAEnquiryM;
import com.eaf.ncb.model.input.PNEnquiryM;
import com.eaf.ncb.model.log.NCBLogM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class NCBMappingModelUtility {
	
	public NCBDataM mappingModel(UserDetailM userM, PLApplicationDataM appM){
		
		PLPersonalInfoDataM perM = appM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);

		NCBDataM ncbM = new NCBDataM();
		ncbM.setCreateBy(userM.getUserName());
		ncbM.setUpdateBy(userM.getUserName());
		ncbM.setUpdateRole(userM.getCurrentRole());
		ncbM.setApplicationNo(appM.getApplicationNo());
		
		NCBInputDataM ncbInputM = new NCBInputDataM();
		
		Vector<PNEnquiryM> pnEnquiryVect = new Vector<PNEnquiryM>();
			PNEnquiryM pnEnquiryM = new PNEnquiryM();
			pnEnquiryM.setCustomerType(perM.getCustomerType());
			pnEnquiryM.setDateOfBirth(perM.getBirthDate());
			pnEnquiryM.setFamilyName1(perM.getThaiLastName());
			pnEnquiryM.setFamilyName2(null);  //I don't know
			pnEnquiryM.setFirstName(perM.getThaiFirstName());
			pnEnquiryM.setGender(perM.getGender());
			pnEnquiryM.setMaritalStatus(perM.getMaritalStatus());
			pnEnquiryM.setMiddleName(perM.getThaiMidName());
			pnEnquiryM.setSegmentTag(null);
			pnEnquiryM.setSegmentValue(null);
			pnEnquiryVect.add(pnEnquiryM);
		ncbInputM.setPnEnquiryMs(pnEnquiryVect);
		
		Vector<IDEnquiryM> idEnquiryVect = new Vector<IDEnquiryM>();
			IDEnquiryM idEnquiryM = new IDEnquiryM();
			idEnquiryM.setIdIssueCountry("Thailand(hard code)");
			idEnquiryM.setIdNumber(perM.getIdNo());
			idEnquiryM.setIdType(perM.getCidType());
			idEnquiryM.setSegmentTag(null);
			idEnquiryM.setSegmentValue(null);
			idEnquiryVect.add(idEnquiryM);
		ncbInputM.setIdEnquiryMs(idEnquiryVect);
		
		Vector<PAEnquiryM> paEnquiryVect = new Vector<PAEnquiryM>();
			PAEnquiryM paEnquiryM = new PAEnquiryM();
			paEnquiryM.setAddressLine1(perM.getAddressDocLine1());
			paEnquiryM.setAddressLine2(perM.getAddressDocLine2());
			paEnquiryM.setAddressLine3(null);
			paEnquiryM.setCountry(null);
			/////.......
			paEnquiryVect.add(paEnquiryM);
		ncbInputM.setPaEnquiryMs(paEnquiryVect);
		
//		Vector<PIEnquiryM> piEnquiryVect = new Vector<PIEnquiryM>();
//			PIEnquiryM piEnquriyM = new PIEnquiryM();
		
		ncbM.setNcbInputDataM(ncbInputM);
		
		return ncbM;
	}
	
	public NCBLogM mappingNcbLogM(String activityType, String trackingCode){
		
		NCBLogM logM = new NCBLogM();
		logM.setActivityType(activityType);
		logM.setCreateDate(new Date());
		logM.setStreamData(null);
		logM.setTrackingCode(trackingCode);
		
		return logM;
	}

}
