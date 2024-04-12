package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class LoadCopyAddress implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(LoadCopyAddress.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String addressTypeCopy = request.getParameter("address_type_copy");
		
		logger.debug("[addressTypeCopy] .. "+addressTypeCopy);
		
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		ORIGCacheUtil origc = new ORIGCacheUtil();
		
		PLAddressDataM addressM = personalM.getAddressDataM(addressTypeCopy);
//		Vector addressVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 12);
		Vector addressStyleVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 13);
		Vector addressStatusVect = (Vector)origc.getNaosCacheDataMs(applicationM.getBusinessClassId(), 14);
		
		ORIGCacheDataM countryM = origc.GetListboxCacheDataM(104, applicationM.getBusinessClassId(), addressM.getCountry());
				
//		#septem comment fix bug address comment
//		jObjectUtil.CreateJsonObject("td_address_seq", HTMLRenderUtil.replaceNull(String.valueOf(addressM.getAddressSeq()+1)));
		jObjectUtil.CreateJsonObject("td_address_style", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(addressStyleVect,addressM.getBuildingType(),"address_style",HTMLRenderUtil.DISPLAY_MODE_EDIT,""));
		jObjectUtil.CreateJsonObject("td_address_status", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(addressStatusVect,addressM.getAdrsts(), "address_status",HTMLRenderUtil.DISPLAY_MODE_EDIT,""));
		jObjectUtil.CreateJsonObject("returndchecks", DataFormatUtility.displayBigDecimalZeroToEmpty(addressM.getRents()));
		jObjectUtil.CreateJsonObject("time_current_address_year", DataFormatUtility.IntToString(addressM.getResidey()));
		jObjectUtil.CreateJsonObject("time_current_address_month", DataFormatUtility.IntToString(addressM.getResidem()));
//		jObjectUtil.CreateJsonObject("place_name",HTMLRenderUtil.replaceNull(addressM.getPlaceName()));
		jObjectUtil.CreateJsonObject("number",HTMLRenderUtil.replaceNull(addressM.getAddressNo()));
//		jObjectUtil.CreateJsonObject("building",HTMLRenderUtil.replaceNull(addressM.getBuilding()));
//		jObjectUtil.CreateJsonObject("floor",HTMLRenderUtil.replaceNull(addressM.getFloor()));
//		jObjectUtil.CreateJsonObject("room",HTMLRenderUtil.replaceNull(addressM.getRoom()));
		jObjectUtil.CreateJsonObject("moo",HTMLRenderUtil.replaceNull(addressM.getMoo()));
//		jObjectUtil.CreateJsonObject("village",HTMLRenderUtil.replaceNull(addressM.getHousingEstate()));
		jObjectUtil.CreateJsonObject("soi",HTMLRenderUtil.replaceNull(addressM.getSoi()));
		jObjectUtil.CreateJsonObject("road",HTMLRenderUtil.replaceNull(addressM.getRoad()));
		jObjectUtil.CreateJsonObject("tambol",HTMLRenderUtil.replaceNull(addressM.getTambol()));
		jObjectUtil.CreateJsonObject("tambol_desc",HTMLRenderUtil.replaceNull(origc.getORIGCacheDisplayNameFormDB(addressM.getTambol(), "Tambol")));
		jObjectUtil.CreateJsonObject("amphur",HTMLRenderUtil.replaceNull(addressM.getAmphur()));
		jObjectUtil.CreateJsonObject("amphur_desc",HTMLRenderUtil.replaceNull(origc.getORIGCacheDisplayNameFormDB(addressM.getAmphur(), "Amphur")));
		jObjectUtil.CreateJsonObject("province",HTMLRenderUtil.replaceNull(addressM.getProvince()));
		jObjectUtil.CreateJsonObject("province_desc",HTMLRenderUtil.replaceNull(origc.getORIGCacheDisplayNameFormDB(addressM.getProvince(), "Province")));
		jObjectUtil.CreateJsonObject("zipcode",HTMLRenderUtil.replaceNull(addressM.getZipcode()));
		
		jObjectUtil.CreateJsonObject("country_desc",HTMLRenderUtil.replaceNull(countryM.getThDesc()));
		jObjectUtil.CreateJsonObject("mobile_no",HTMLRenderUtil.replaceNull(addressM.getMobileNo()));
		jObjectUtil.CreateJsonObject("telephone1",HTMLRenderUtil.replaceNull(addressM.getPhoneNo1()));
		jObjectUtil.CreateJsonObject("ext_tel_1",HTMLRenderUtil.replaceNull(addressM.getPhoneExt1()));
		jObjectUtil.CreateJsonObject("telephone2",HTMLRenderUtil.replaceNull(addressM.getPhoneNo2()));
		jObjectUtil.CreateJsonObject("ext_tel_2",HTMLRenderUtil.replaceNull(addressM.getPhoneExt2()));
		jObjectUtil.CreateJsonObject("fax_no",HTMLRenderUtil.replaceNull(addressM.getFaxNo()));
		jObjectUtil.CreateJsonObject("note",HTMLRenderUtil.replaceNull(addressM.getRemark()));
		
		return jObjectUtil.returnJson();
	}

}
