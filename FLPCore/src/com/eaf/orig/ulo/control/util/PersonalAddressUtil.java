package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.google.gson.Gson;

public class PersonalAddressUtil {
	private static transient Logger logger = Logger.getLogger(PersonalAddressUtil.class);	
	public String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	public String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	public String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	public String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	public static String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	public static String ADDRESS_TYPE_CARDLINK_REJECT_LETTER = SystemConstant.getConstant("ADDRESS_TYPE_CARDLINK_REJECT_LETTER");
	
	public static void updatePersonalAddressSeq(PersonalInfoDataM personalInfo){
		 ArrayList<AddressDataM> addresses = personalInfo.getAddresses();
		 int seq = 1;
		 if(null != addresses){
			 for (AddressDataM address : addresses) {
				 address.setSeq(seq++);
			}
		 }
	}
	public String getAddressText(HttpServletRequest request,AddressDataM addressData){
		String TEXT_ROAD = LabelUtil.getText(request,"TEXT_ROAD");
		String TEXT_TAMBOL = LabelUtil.getText(request,"TEXT_TAMBOL");
		String TEXT_AMPHUR = LabelUtil.getText(request,"TEXT_AMPHUR");		
		String addressText ="";
		if(!Util.empty(addressData.getAddress())){
			addressText += addressData.getAddress();
		}
		if(!Util.empty(addressData.getRoad())){
			addressText += " "+TEXT_ROAD+addressData.getRoad();
		}
		if(!Util.empty(addressData.getTambol())){
			addressText += " "+TEXT_TAMBOL+addressData.getTambol();
		}
		if(!Util.empty(addressData.getAmphur())){
			addressText += " "+TEXT_AMPHUR+addressData.getAmphur();
		}
		if(!Util.empty(addressData.getProvince())){
			addressText += " "+addressData.getProvince();
		}
		if(!Util.empty(addressData.getZipcode())){
			addressText += " "+addressData.getZipcode();
		}
		return addressText;
	}
	
	public static String getDocCardlinkLine1(HttpServletRequest request , AddressDataM addressData){
		StringBuilder carkLinkLine1 = new StringBuilder();
		String EMPTY_STR = "";
		if(!Util.empty(addressData.getAddress())){
			carkLinkLine1.append(EMPTY_STR+addressData.getAddress());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getMoo())){
			moo(request, addressData, carkLinkLine1);
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getSoi())){
			carkLinkLine1.append(EMPTY_STR+addressData.getSoi());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getRoad())){
			carkLinkLine1.append(EMPTY_STR+addressData.getRoad());
			EMPTY_STR = " ";
		}
		return carkLinkLine1.toString();
	}
	
	public static String getCurrentCardlinkLine1(HttpServletRequest request,AddressDataM addressData){	
		StringBuilder carkLinkLine1 = new StringBuilder();
		String EMPTY_STR = "";
		if(!Util.empty(addressData.getAddress())){
			carkLinkLine1.append(EMPTY_STR+addressData.getAddress());
			EMPTY_STR = " ";
		}	
		if(!Util.empty(addressData.getMoo())){
			moo(request, addressData, carkLinkLine1);
			EMPTY_STR = " ";
		}
//		if(!Util.empty(addressData.getSoi())){
//			carkLinkLine1.append(EMPTY_STR+addressData.getSoi());
//			EMPTY_STR = " ";
//		}	
			carkLinkLine1.append(EMPTY_STR+getVilaptBuildingSoi(addressData,true));
			EMPTY_STR = " ";
		if(!Util.empty(addressData.getRoad())){
			carkLinkLine1.append(EMPTY_STR+addressData.getRoad());
			EMPTY_STR = " ";
		}
		return carkLinkLine1.toString();
	}
	public static void moo(HttpServletRequest request,AddressDataM addressData,StringBuilder carkLinkLine1){
		String prefixMoo = " "+LabelUtil.getText(request,"TEXT_MOO");
		String addressNo = addressData.getAddress();
		if(Util.empty(addressNo)){
			addressNo = "";
		}
		String moo = addressData.getMoo();
		if(Util.empty(moo)){
			moo = "";
		}
		if(addressNo.contains(prefixMoo)){
			carkLinkLine1.append(moo);
		}else{
			carkLinkLine1.append(" "+moo);
		}
	}
	public static String getWorkCardlinkLine1(HttpServletRequest request , AddressDataM addressData){
		StringBuilder carkLinkLine1 = new StringBuilder();
		String EMPTY_STR = "";
		if(!Util.empty(addressData.getCompanyName())){
			carkLinkLine1.append(EMPTY_STR+addressData.getCompanyName());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getBuilding())){
			carkLinkLine1.append(EMPTY_STR+addressData.getBuilding());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getFloor())){
			carkLinkLine1.append(EMPTY_STR+addressData.getFloor());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getDepartment())){
			carkLinkLine1.append(EMPTY_STR+addressData.getDepartment());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getAddress())){
			carkLinkLine1.append(EMPTY_STR+addressData.getAddress());
			EMPTY_STR = " ";
		}	
		if(!Util.empty(addressData.getMoo())){
			moo(request, addressData, carkLinkLine1);
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getSoi())){
			carkLinkLine1.append(EMPTY_STR+addressData.getSoi());
			EMPTY_STR = " ";
		}		
		if(!Util.empty(addressData.getRoad())){
			carkLinkLine1.append(EMPTY_STR+addressData.getRoad());
			EMPTY_STR = " ";
		}
		return carkLinkLine1.toString();
	}
	
	public static String getCardlinkLine2(HttpServletRequest request , AddressDataM addressData){		
		String cardLinkLine2 = "";
		List<String> addressList = new ArrayList<>();
		if(!Util.empty(addressData.getTambol())){
			addressList.add(addressData.getTambol());
		}
		if(!Util.empty(addressData.getAmphur())){
			addressList.add(addressData.getAmphur());
		}
		if(!Util.empty(addressData.getProvinceDesc())){
			addressList.add(addressData.getProvinceDesc());
		}
		if(!Util.empty(addressList)){
			cardLinkLine2 = StringUtils.join(addressList, ' ');
		}
		return cardLinkLine2;
	}
	
	public static boolean bkkProvince(String province){
		return SystemConfig.containsGeneralParam("PROVINCE_BKK",province);
	}
	public String getAddressFormId(String sendDoc,String addressType,ApplicationGroupDataM applicationGroup,String roldId){
		boolean matchesCardLink = applicationGroup.matchesCardLinkByproduct();
		String popupForm="";
		String formType = "_2";
		if(null!=addressType&&!addressType.equals(sendDoc) && !ROLE_DE2.equals(roldId) && !ADDRESS_TYPE_DOCUMENT.equals(addressType)){
			formType = "_1";
		}
		if(ROLE_DE2.equals(roldId) && matchesCardLink != true){
			formType = "_3";
		}		
		if(ADDRESS_TYPE_CURRENT.equals(addressType)){
			popupForm="POPUP_CURRENT_ADDRESS_FORM"+formType;
		}else if(ADDRESS_TYPE_WORK.equals(addressType)){
			popupForm="POPUP_OFFICE_ADDRESS_FORM"+formType;
		}else if(ADDRESS_TYPE_DOCUMENT.equals(addressType)){
			popupForm="POPUP_HOME_ADDRESS_FORM"+formType;
		}
		return popupForm;
	}
	
	public static String getVilaptBuildingSoiCurrent(AddressDataM addressM,boolean copyCardLinkAddress){
		String data = "";
		if(null == addressM){
			return "";
		}
		if(copyCardLinkAddress){
			if(!Util.empty(addressM.getVilapt())){
				data +=addressM.getVilapt();
			}
			if(!Util.empty(addressM.getBuilding())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getBuilding();
			}
			if(!Util.empty(addressM.getRoom())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getRoom();
			}
			if(!Util.empty(addressM.getFloor())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getFloor();
			}
//			if(!Util.empty(addressM.getSoi())){
//				if(!Util.empty(data)){
//					data +=" ";
//				}
//				data +=addressM.getSoi();
//			}
		}else{
			if(!Util.empty(addressM.getVilapt())){
				data +=addressM.getVilapt();
			}
			if(!Util.empty(addressM.getSoi())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getSoi();
			}
		}
		return data;
	}
	
	public static String getVilaptBuildingSoi(AddressDataM addressM,boolean copyCardLinkAddress){
		String data = "";
		if(null == addressM){
			return "";
		}
		if(copyCardLinkAddress){
			if(!Util.empty(addressM.getVilapt())){
				data +=addressM.getVilapt();
			}
			if(!Util.empty(addressM.getBuilding())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getBuilding();
			}
			if(!Util.empty(addressM.getRoom())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getRoom();
			}
			if(!Util.empty(addressM.getFloor())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getFloor();
			}
			if(!Util.empty(addressM.getSoi())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getSoi();
			}
		}else{
			if(!Util.empty(addressM.getVilapt())){
				data +=addressM.getVilapt();
			}
			if(!Util.empty(addressM.getSoi())){
				if(!Util.empty(data)){
					data +=" ";
				}
				data +=addressM.getSoi();
			}
		}
		return data;
	}
	
	public static void setAddressCardLinkIncrease(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		DIHProxy proxy = new DIHProxy();
		if(!Util.empty(personalInfo)){
			ApplicationDataM application = applicationGroup.filterApplicationItemLifeCycle();
			if(!Util.empty(application) && !Util.empty(application.getCard())){
				CardDataM card = application.getCard();
				String encMainCard =  card.getCardNo();
				logger.debug("encMainCard : "+encMainCard);
				DIHQueryResult<CardLinkDataM> queryResult = proxy.getCardLinkInfoENCPT(encMainCard);
				CardLinkDataM cardLinkInfo = queryResult.getResult();
				if(!Util.empty(cardLinkInfo)){
				logger.debug("CardLink Process DE2 Submit : "+new Gson().toJson(cardLinkInfo));
				AddressDataM addressCardLink = new AddressDataM();
				addressCardLink.setAddressType(ADDRESS_TYPE_CARDLINK_REJECT_LETTER);
				addressCardLink.setAddress1(cardLinkInfo.getAddressLine1());
				addressCardLink.setAddress2(cardLinkInfo.getAddressLine2()+" "+cardLinkInfo.getAddressLine3()+" "+cardLinkInfo.getMainZipCode());
				addressCardLink.setZipcode(cardLinkInfo.getMainZipCode());
				personalInfo.addAddress(addressCardLink);
				}
			}
		}
	}
}
