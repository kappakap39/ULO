package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.common.cont.IMConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SmartDataUtil {
	private static transient Logger log = Logger.getLogger(SmartDataUtil.class);
	public static String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	
	public static boolean isPrint(ApplicationImageSplitDataM applicationImageSplit, String roleId){
		boolean isPrint = false;
		ArrayList<String> PRINT_DOC_TYPEs = SystemConstant.getArrayListConstant("PRINT_DOC_TYPE");
		ArrayList<String> PRINT_ROLE_IDs = SystemConstant.getArrayListConstant("PRINT_ROLE_ID");
		if(!Util.empty(applicationImageSplit.getDocType())){
			if(PRINT_DOC_TYPEs.contains(applicationImageSplit.getDocType()) && PRINT_ROLE_IDs.contains(roleId)){
				isPrint = true;
			}
		}
		return isPrint;
	}
	
	public static String getImageData(ApplicationGroupDataM applicationGroup, String role){
		//Validation
		String result = "[]";
		if(applicationGroup == null)return result;
		List<ApplicationImageDataM> images = applicationGroup.getApplicationImages();
		if(images == null || images.isEmpty())return result;
		
		//Perpare data
		String IM_IMAGE_PART = SystemConfig.getProperty("IM_IMAGE_PART");
		String VIEW_IMAGE_URL = SystemConfig.getProperty("VIEW_IMAGE_URL");
//		String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
		String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");
		String CACHE_SMARTDATA_DOC_MAPPING = SystemConstant.getConstant("CACHE_SMARTDATA_DOC_MAPPING");
		String SMART_DATA_DOCTYPE_APPFORM = SystemConstant.getConstant("SMART_DATA_DOCTYPE_APPFORM");
		String DOC_TYPE_ADDITIONAL = SystemConfig.getGeneralParam("DOC_TYPE_ADDITIONAL");
		int docTypeAppFormSeq = 1;
		//Start mapping logic
		JSONArray array = new JSONArray();
		for(ApplicationImageDataM image : images){
			String host = image.getPath();
			List<ApplicationImageSplitDataM> splits = image.getApplicationImageSplits();
			if(splits == null || splits.isEmpty())continue;
			
			for(ApplicationImageSplitDataM split : splits){
				if(Util.empty(split.getDocType()) || !DOC_TYPE_ADDITIONAL.equals(split.getDocType())){
					JSONObject jsonImage = new JSONObject();
					PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(split.getPersonalId());
					if(personalInfo == null)
						personalInfo = new PersonalInfoDataM();
					
					String imageURL = VIEW_IMAGE_URL+"imageId="+FormatUtil.displayText(split.getImageId())+"&imageSize="+IMConstant.ImageSize.ORIGINAL;
					String thumbnailURL = VIEW_IMAGE_URL+"imageId="+FormatUtil.displayText(split.getImageId())+"&imageSize="+IMConstant.ImageSize.THUMBNAIL;		
					try{
						jsonImage.put("personalTypeDesc", getPersonalType(split.getApplicantTypeIM()));
						jsonImage.put("personalType", split.getApplicantTypeIM());
						jsonImage.put("docTypeDesc", CacheControl.getName(CACH_NAME_DOCUMENT_LIST, split.getDocType()));
						jsonImage.put("imgpath",imageURL);
						jsonImage.put("isPrint",SmartDataUtil.isPrint(split, role));
						jsonImage.put("imgthumbpath",thumbnailURL);
						String smartDocTypeId = CacheControl.getName(CACHE_SMARTDATA_DOC_MAPPING, split.getDocType());
						jsonImage.put("doctypeid",FormatUtil.getInt(smartDocTypeId));
						if(SMART_DATA_DOCTYPE_APPFORM.equals(smartDocTypeId)){
							jsonImage.put("docTypeSeq",docTypeAppFormSeq++);
						}else{
							jsonImage.put("docTypeSeq",split.getDocTypeSeq());
						}
//						#rawi comment remove fix size natural image change to used javascript find!
//						jsonImage.put("naturalWidth",824);
//						jsonImage.put("naturalHeight",1172);
					}catch(JSONException e){
						log.debug("Error while putting value to JSON");
						e.printStackTrace();
					}
					array.put(jsonImage);
				}
			}
		}
		
		result = array.toString();
		return result;
	}
	
	public static String getSmartPersonalType(PersonalInfoDataM personalInfo){
		if(personalInfo == null)return null;
		return isApplicant(personalInfo)?personalInfo.getPersonalType():personalInfo.getPersonalType()+personalInfo.getSeq();
	}
	
	public static boolean isApplicant(PersonalInfoDataM personalInfo){
		if(personalInfo == null)return false;
		String applicantType = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		if(applicantType != null && applicantType.equalsIgnoreCase(personalInfo.getPersonalType())){
			return true;
		}
		return false;
	}
	
	/**Temporarily Hard Code......
	 * @param imPersonalType
	 * @return
	 */
	public static String getPersonalType(String imPersonalType){
		if(imPersonalType == null)return null;
		if(IM_PERSONAL_TYPE_APPLICANT.equals(imPersonalType)){
			return "\u0E1C\u0E39\u0E49\u0E2A\u0E21\u0E31\u0E04\u0E23\u0E2B\u0E25\u0E31\u0E01";
		}else{
			String[] splits = imPersonalType.split("S");
			if(splits == null || splits.length < 1)
				return null;
			if(splits.length == 1){
				return "\u0E1C\u0E39\u0E49\u0E2A\u0E21\u0E31\u0E04\u0E23\u0E40\u0E2A\u0E23\u0E34\u0E21";
			}else{
				return "\u0E1C\u0E39\u0E49\u0E2A\u0E21\u0E31\u0E04\u0E23\u0E40\u0E2A\u0E23\u0E34\u0E21"+splits[1];
			}
		}
		
	}
}
