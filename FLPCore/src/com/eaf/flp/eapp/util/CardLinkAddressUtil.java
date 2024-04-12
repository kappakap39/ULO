  package com.eaf.flp.eapp.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.ResourceBundleUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.AbbreviationDAOImpl;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class CardLinkAddressUtil {
	private static transient Logger logger = Logger.getLogger(CardLinkAddressUtil.class);
	private static String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");		
	private static String ADDRESS_TYPE_WORK_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_WORK_CARDLINK");	
	private static String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");
	private static int LINE1_MAX = Integer.parseInt(SystemConstant.getConstant("LINE1_MAX"));	
	private static int LINE2_MAX = Integer.parseInt(SystemConstant.getConstant("LINE2_MAX"));	
	private static String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");		
	private static String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
	private static String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");	
	private static String TEXT_ROAD = SystemConstant.getConstant("TEXT_ROAD");
	private static String TEXT_TAMBOL = SystemConstant.getConstant("TEXT_TAMBOL");
	private static String TEXT_AMPHUR = SystemConstant.getConstant("TEXT_AMPHUR");
	private static String TEXT_MOO = SystemConstant.getConstant("TEXT_MOO");
	private static String TEXT_SOI = SystemConstant.getConstant("TEXT_SOI");
	private static String TEXT_ADDRESS_NAME = SystemConstant.getConstant("TEXT_ADDRESS_NAME");
	private static String TEXT_BUILDING = SystemConstant.getConstant("TEXT_BUILDING");
	private static String TEXT_ROOM = SystemConstant.getConstant("TEXT_ROOM");
	private static String TEXT_FLOOR = SystemConstant.getConstant("TEXT_FLOOR");
	private static String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");
	private static int FLOOR_MAX = Integer.parseInt(SystemConstant.getConstant("VALIDATE_FLOOR"));	
	private static int MOO_MAX = Integer.parseInt(SystemConstant.getConstant("VALIDATE_MOO"));	
	private static int ROOM_MAX = Integer.parseInt(SystemConstant.getConstant("ROOM_MAX"));	
	private static int SOI_MAX = Integer.parseInt(SystemConstant.getConstant("VALIDATE_SOI"));	
	private static int ROAD_MAX = Integer.parseInt(SystemConstant.getConstant("VALIDATE_ROAD"));	
	private static String Blank_Space = " ";
	private static int VALIDATE_MOO = Integer.parseInt(SystemConstant.getConstant("VALIDATE_MOO"));	
	private static int VALIDATE_ADDR_ID = Integer.parseInt(SystemConstant.getConstant("VALIDATE_ADDR_ID"));	
	private static int VALIDATE_SOI = Integer.parseInt(SystemConstant.getConstant("VALIDATE_SOI"));	
	private static int VALIDATE_ROAD = Integer.parseInt(SystemConstant.getConstant("VALIDATE_ROAD"));	
	private static int VALIDATE_COMPANY = Integer.parseInt(SystemConstant.getConstant("VALIDATE_COMPANY"));	
	private static int VALIDATE_DEPARTMENT = Integer.parseInt(SystemConstant.getConstant("VALIDATE_DEPARTMENT"));	
	private static int VALIDATE_FLOOR = Integer.parseInt(SystemConstant.getConstant("VALIDATE_FLOOR"));	
	private static int VALIDATE_BUILDING = Integer.parseInt(SystemConstant.getConstant("VALIDATE_BUILDING"));	
	
	public static boolean checkCardlinkAddress(List<AddressDataM> addresses){
		boolean result = false;
		try{
			logger.debug("checkCardlinkAddress >>");
			if(validateAddress(addresses)){
				logger.debug("validateAddress true >>");
				copyAddress(addresses, ADDRESS_TYPE_CURRENT, ADDRESS_TYPE_CURRENT_CARDLINK);
				copyAddress(addresses, ADDRESS_TYPE_WORK, ADDRESS_TYPE_WORK_CARDLINK);
				copyAddress(addresses, ADDRESS_TYPE_DOCUMENT, ADDRESS_TYPE_DOCUMENT_CARDLINK);
				
				if(validateAddressCardlink(addresses)){
					logger.debug("validateAddressCardlink true>>");
					result = true;
				}
				setAddressCurr(addresses);
				if(validateAddressLength(addresses)){
					logger.debug("validateAddressLength true>>");
					result = true;
				}
				checkLengthAddr(addresses);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			logger.error("Error in checkCardlinkAddress", e);
		}
		return result;
	}

	public static boolean validateAddress(List<AddressDataM> addresses){
		boolean result = false;
		AddressDataM currAddressM = getAddress(addresses, ADDRESS_TYPE_CURRENT);
		AddressDataM workAddressM = getAddress(addresses, ADDRESS_TYPE_WORK);
		AddressDataM docAddressM = getAddress(addresses, ADDRESS_TYPE_DOCUMENT);
		
		logger.debug("validateAddress currAddressM: "+currAddressM);
		logger.debug("validateAddress workAddressM: "+workAddressM);
		logger.debug("validateAddress docAddressM: "+docAddressM);
		
		String currentCardlinkLine1 = getCurrentCardlinkLine1(currAddressM==null?new AddressDataM():currAddressM);
		String workCardlinkLine1 = getWorkCardlinkLine1(workAddressM==null?new AddressDataM():workAddressM);
		String docCardlinkLine1 = getDocCardlinkLine1(docAddressM==null?new AddressDataM():docAddressM);
		
		logger.debug("validateAddress currentCardlinkLine1: "+currentCardlinkLine1);
		logger.debug("validateAddress workCardlinkLine1: "+workCardlinkLine1);
		logger.debug("validateAddress workCardlinkLine1: "+docCardlinkLine1);
		
		int currAddrLine1Length = currentCardlinkLine1.length();
		int workAddrLine1Length = workCardlinkLine1.length();
		int docAddrLine1Length = docCardlinkLine1.length();
		
		String currentCardlinkLine2 = getCardlinkLine2(currAddressM==null?new AddressDataM():currAddressM);
		String workCardlinkLine2 = getCardlinkLine2(workAddressM==null?new AddressDataM():workAddressM);
		String docCardlinkLine2 = getCardlinkLine2(docAddressM==null?new AddressDataM():docAddressM);
		
		int currAddrLine2Length = currentCardlinkLine2.length();
		int workAddrLine2Length = workCardlinkLine2.length();
		int docAddrLine2Length = docCardlinkLine2.length();
		
		logger.debug("validateAddress currAddrLine1Length: "+currAddrLine1Length);
		logger.debug("validateAddress workAddrLine1Length: "+workAddrLine1Length);
		logger.debug("validateAddress docAddrLine1Length: "+docAddrLine1Length);
		logger.debug("validateAddress currAddrLine2Length: "+currAddrLine2Length);
		logger.debug("validateAddress workAddrLine2Length: "+workAddrLine2Length);
		logger.debug("validateAddress docAddrLine2Length: "+docAddrLine2Length);
		if(checkAddressLength("LINE1",currAddrLine1Length)&&checkAddressLength("LINE2",currAddrLine2Length)&&
		   checkAddressLength("LINE1",workAddrLine1Length)&&checkAddressLength("LINE2",workAddrLine2Length)&&
		   checkAddressLength("LINE1",docAddrLine1Length)&&checkAddressLength("LINE2",docAddrLine2Length)){
			result = true;
		}
		
		return result;
	}
	
	public static boolean validateAddressCardlink(List<AddressDataM> addresses){
		boolean result = false;
		AddressDataM currAddressM = getAddress(addresses, ADDRESS_TYPE_CURRENT_CARDLINK);
		AddressDataM workAddressM = getAddress(addresses, ADDRESS_TYPE_WORK_CARDLINK);
		AddressDataM docAddressM = getAddress(addresses, ADDRESS_TYPE_DOCUMENT_CARDLINK);
		
		logger.debug("validateAddress currAddressM: "+currAddressM);
		logger.debug("validateAddress workAddressM: "+workAddressM);
		logger.debug("validateAddress docAddressM: "+docAddressM);
		
		String currentCardlinkLine1 = getCurrentCardlinkLine1(currAddressM==null?new AddressDataM():currAddressM);
		String workCardlinkLine1 = getWorkCardlinkLine1(workAddressM==null?new AddressDataM():workAddressM);
		String docCardlinkLine1 = getDocCardlinkLine1(docAddressM==null?new AddressDataM():docAddressM);
		
		logger.debug("validateAddress currentCardlinkLine1: "+currentCardlinkLine1);
		logger.debug("validateAddress workCardlinkLine1: "+workCardlinkLine1);
		logger.debug("validateAddress workCardlinkLine1: "+docCardlinkLine1);
		
		int currAddrLine1Length = currentCardlinkLine1.length();
		int workAddrLine1Length = workCardlinkLine1.length();
		int docAddrLine1Length = docCardlinkLine1.length();
		
		String currentCardlinkLine2 = getCardlinkLine2(currAddressM==null?new AddressDataM():currAddressM);
		String workCardlinkLine2 = getCardlinkLine2(workAddressM==null?new AddressDataM():workAddressM);
		String docCardlinkLine2 = getCardlinkLine2(docAddressM==null?new AddressDataM():docAddressM);
		
		int currAddrLine2Length = currentCardlinkLine2.length();
		int workAddrLine2Length = workCardlinkLine2.length();
		int docAddrLine2Length = docCardlinkLine2.length();
		
		logger.debug("validateAddress currAddrLine1Length: "+currAddrLine1Length);
		logger.debug("validateAddress workAddrLine1Length: "+workAddrLine1Length);
		logger.debug("validateAddress docAddrLine1Length: "+docAddrLine1Length);
		logger.debug("validateAddress currAddrLine2Length: "+currAddrLine2Length);
		logger.debug("validateAddress workAddrLine2Length: "+workAddrLine2Length);
		logger.debug("validateAddress docAddrLine2Length: "+docAddrLine2Length);
		
		if(checkAddressLength("LINE1",currAddrLine1Length)&&checkAddressLength("LINE2",currAddrLine2Length)&&
		   checkAddressLength("LINE1",workAddrLine1Length)&&checkAddressLength("LINE2",workAddrLine2Length)&&
		   checkAddressLength("LINE1",docAddrLine1Length)&&checkAddressLength("LINE2",docAddrLine2Length)){
			result = true;
		}
		
		return result;
	}
	public static boolean validateAddressLength(List<AddressDataM> addresses){
		boolean result = false;
		for(AddressDataM addressM : addresses){
			if(null == addressM){
				addressM = new AddressDataM();
			}
			if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(addressM.getAddressType())){
				if((!Util.empty(addressM.getSoi()) && addressM.getSoi().length()>VALIDATE_SOI)
					||(!Util.empty(addressM.getMoo()) && addressM.getMoo().length()>VALIDATE_MOO)
					||(!Util.empty(addressM.getRoad()) && addressM.getRoad().length()>VALIDATE_ROAD)
					||(!Util.empty(addressM.getAddressId()) && addressM.getAddressId().length()>VALIDATE_ADDR_ID)
				){
					return false;
				}
			}else if(ADDRESS_TYPE_WORK_CARDLINK.equals(addressM.getAddressType())){
				if((!Util.empty(addressM.getSoi()) && addressM.getSoi().length()>VALIDATE_SOI)
					||(!Util.empty(addressM.getMoo()) && addressM.getMoo().length()>VALIDATE_MOO)
					||(!Util.empty(addressM.getRoad()) && addressM.getRoad().length()>VALIDATE_ROAD)
					||(!Util.empty(addressM.getAddressId()) && addressM.getAddressId().length()>VALIDATE_ADDR_ID)
					||(!Util.empty(addressM.getCompanyName()) && addressM.getCompanyName().length()>VALIDATE_COMPANY)
					||(!Util.empty(addressM.getBuilding()) && addressM.getBuilding().length()>VALIDATE_BUILDING)
					||(!Util.empty(addressM.getFloor()) && addressM.getFloor().length()>VALIDATE_FLOOR)
					||(!Util.empty(addressM.getDepartment()) && addressM.getDepartment().length()>VALIDATE_DEPARTMENT)
				){
					return false;
				}
			}else if(ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(addressM.getAddressType())){
				if((!Util.empty(addressM.getSoi()) && addressM.getSoi().length()>VALIDATE_SOI)
					||(!Util.empty(addressM.getMoo()) && addressM.getMoo().length()>VALIDATE_MOO)
					||(!Util.empty(addressM.getRoad()) && addressM.getRoad().length()>VALIDATE_ROAD)
					||(!Util.empty(addressM.getAddressId()) && addressM.getAddressId().length()>VALIDATE_ADDR_ID)
				){
					return false;
				}
			}
		}	
		return true;
	}
	public static boolean checkAddressLength(String line ,int length){
		boolean result = false;
		if("LINE1".equals(line)){
			if(length <= LINE1_MAX){
				result = true;
			}
		}else if("LINE2".equals(line)){
			if(length <= LINE2_MAX){
				result = true;
			}
		}
		logger.debug("LINE2_MAX: "+LINE2_MAX);
		logger.debug("LINE1_MAX: "+LINE1_MAX);
		return result;
	}
	public static void copyAddress(List<AddressDataM> addresses,String copyAddressType,String toAddressType){
		AddressDataM addressM = new AddressDataM();
		AddressDataM cardlinkAddressM = new AddressDataM();
		AbbreviationDAOImpl abbreviationdaoimpl = new AbbreviationDAOImpl();
		if(ADDRESS_TYPE_CURRENT.equals(copyAddressType)){
			addressM = getAddress(addresses,ADDRESS_TYPE_CURRENT);
		}else if(ADDRESS_TYPE_WORK.equals(copyAddressType)){
			addressM = getAddress(addresses,ADDRESS_TYPE_WORK);
		}else if(ADDRESS_TYPE_DOCUMENT.equals(copyAddressType)){
			addressM = getAddress(addresses,ADDRESS_TYPE_DOCUMENT);
		}
		
		if(null == addressM){
			addressM = new AddressDataM();
		}
		
		String ADDRESS_NAME =  addressM.getVilapt();
		String COMPANY_NAME = CacheControl.getName(FIELD_ID_COMPANY_TITLE,addressM.getCompanyTitle())+(!Util.empty(addressM.getCompanyTitle())?" "+addressM.getCompanyName():addressM.getCompanyName());
		String COMPANY_TITLE =  addressM.getCompanyTitle();
		String DEPARTMENT =  addressM.getDepartment();
		String BUILDING =  addressM.getBuilding();
		String ROOM =  addressM.getRoom();
		String FLOOR =  addressM.getFloor();
		String ADDRESS_ID = addressM.getAddress();
		String MOO =  addressM.getMoo();
		String SOI =  addressM.getSoi();
		String ROAD =  addressM.getRoad();
		String COUNTRY =  addressM.getCountry();
		String TAMBOL =  addressM.getTambol();
		String AMPHUR =  addressM.getAmphur();
		String PROVINCE = addressM.getProvinceDesc();
		String ZIPCODE =  addressM.getZipcode();	
		String ADRSTS =  addressM.getAdrsts();
		String RESIDEY = (!Util.empty(addressM.getResidey())?addressM.getResidey().toString():"");
		String RESIDEM = (!Util.empty(addressM.getResidem())?addressM.getResidem().toString():"");
		
		if(!Util.empty(COMPANY_NAME)){
			COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim());
		}
		if(Util.empty(ADDRESS_ID)){
			ADDRESS_ID = "";
		}	
		if(!ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(toAddressType)){
			if(!Util.empty(MOO) && MOO.length() == 1){
				MOO = TEXT_MOO+MOO;
			}else if(!Util.empty(MOO)&& MOO.length() == 2 && ADDRESS_ID.length() <= 6){
				ADDRESS_ID = ADDRESS_ID+" "+TEXT_MOO;
			}
		}
		if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){
			if(!Util.empty(SOI)){
				SOI = TEXT_SOI+SOI;
			}else SOI="";
			if(!Util.empty(ROAD) || !Util.empty(SOI)){
				ROAD =(!Util.empty(ROAD)?TEXT_ROAD+ROAD:"");
			}
			if(!Util.empty(ADDRESS_NAME)){
				ADDRESS_NAME = TEXT_ADDRESS_NAME+ADDRESS_NAME;
			}
			if(!Util.empty(BUILDING)){
				BUILDING = TEXT_BUILDING+BUILDING;
			}
			if(!Util.empty(ROOM)){
				ROOM = TEXT_ROOM+ROOM;
			}
			if(!Util.empty(FLOOR)){
				FLOOR = TEXT_FLOOR+FLOOR;
			}
		}else {
			if(!Util.empty(SOI)){
				SOI = TEXT_SOI+SOI;
			}
			if(!Util.empty(ROAD)){
				ROAD = TEXT_ROAD+ROAD;
			}
			if(!Util.empty(BUILDING)){
				BUILDING = TEXT_BUILDING+BUILDING;
			}
			if(!Util.empty(FLOOR)){
				FLOOR = TEXT_FLOOR+FLOOR;
			}
		}
		
		if(!Util.empty(TAMBOL)&&!Util.empty(abbreviationdaoimpl.getAbbreviation(TAMBOL))&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
			TAMBOL = TEXT_TAMBOL+abbreviationdaoimpl.getAbbreviation(TAMBOL);
		}else if(!Util.empty(TAMBOL)&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
			TAMBOL = TEXT_TAMBOL+TAMBOL;
		}else if(!Util.empty(abbreviationdaoimpl.getAbbreviation(TAMBOL))){
			TAMBOL = abbreviationdaoimpl.getAbbreviation(TAMBOL);
		}

		if(!Util.empty(AMPHUR)&&!Util.empty(abbreviationdaoimpl.getAbbreviation(AMPHUR))&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
			AMPHUR = TEXT_AMPHUR+abbreviationdaoimpl.getAbbreviation(AMPHUR);
		}else if(!Util.empty(AMPHUR)&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
			AMPHUR = TEXT_AMPHUR+AMPHUR;
		}else 
			if(!Util.empty(abbreviationdaoimpl.getAbbreviation(AMPHUR))){
			AMPHUR = abbreviationdaoimpl.getAbbreviation(AMPHUR);
		}
		if(!Util.empty(abbreviationdaoimpl.getAbbreviation(PROVINCE))){
			PROVINCE = abbreviationdaoimpl.getAbbreviation(PROVINCE);
		}
		
		if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){
			cardlinkAddressM.setAddressType(ADDRESS_TYPE_CURRENT_CARDLINK);
			cardlinkAddressM.setCompanyName(COMPANY_NAME);
			cardlinkAddressM.setDepartment(DEPARTMENT);
			cardlinkAddressM.setAddress(ADDRESS_ID);
			cardlinkAddressM.setMoo(MOO);
			cardlinkAddressM.setSoi(SOI);
			cardlinkAddressM.setRoad(ROAD);
			cardlinkAddressM.setCountry(COUNTRY);
			cardlinkAddressM.setTambol(TAMBOL);
			cardlinkAddressM.setAmphur(AMPHUR);
			cardlinkAddressM.setProvinceDesc(PROVINCE);
			cardlinkAddressM.setZipcode(ZIPCODE);
			cardlinkAddressM.setCompanyTitle(COMPANY_TITLE);
			cardlinkAddressM.setRoom(ROOM);
			cardlinkAddressM.setFloor(FLOOR);
			cardlinkAddressM.setBuilding(BUILDING);
			cardlinkAddressM.setVilapt(ADDRESS_NAME);
			if(!Util.empty(ADRSTS))	cardlinkAddressM.setAdrsts(ADRSTS);
			if(!Util.empty(RESIDEY)&&Util.isBigDecimal(RESIDEY))cardlinkAddressM.setResidey(new BigDecimal(RESIDEY));
			if(!Util.empty(RESIDEM)&&Util.isBigDecimal(RESIDEM))cardlinkAddressM.setResidem(new BigDecimal(RESIDEM));
			addresses.add(cardlinkAddressM);
		
		}else if(ADDRESS_TYPE_WORK_CARDLINK.equals(toAddressType)){		
			cardlinkAddressM.setAddressType(ADDRESS_TYPE_WORK_CARDLINK);
			cardlinkAddressM.setCompanyName(COMPANY_NAME);
			cardlinkAddressM.setDepartment(DEPARTMENT);
			cardlinkAddressM.setBuilding(BUILDING);
			cardlinkAddressM.setRoom(ROOM);
			cardlinkAddressM.setFloor(FLOOR);
			cardlinkAddressM.setAddress(ADDRESS_ID);
			cardlinkAddressM.setMoo(MOO);
			cardlinkAddressM.setSoi(SOI);
			cardlinkAddressM.setRoad(ROAD);
			cardlinkAddressM.setCountry(COUNTRY);
			cardlinkAddressM.setTambol(TAMBOL);
			cardlinkAddressM.setAmphur(AMPHUR);
			cardlinkAddressM.setProvinceDesc(PROVINCE);
			cardlinkAddressM.setZipcode(ZIPCODE);
			cardlinkAddressM.setVilapt(ADDRESS_NAME);
			cardlinkAddressM.setCompanyTitle(COMPANY_TITLE);
			if(!Util.empty(ADRSTS))	cardlinkAddressM.setAdrsts(ADRSTS);
			if(!Util.empty(RESIDEY)&&Util.isBigDecimal(RESIDEY))cardlinkAddressM.setResidey(new BigDecimal(RESIDEY));
			if(!Util.empty(RESIDEM)&&Util.isBigDecimal(RESIDEM))cardlinkAddressM.setResidem(new BigDecimal(RESIDEM));
			addresses.add(cardlinkAddressM);

		}else if(ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(toAddressType)){
			cardlinkAddressM.setAddressType(ADDRESS_TYPE_DOCUMENT_CARDLINK);
			cardlinkAddressM.setCompanyName(COMPANY_NAME);
			cardlinkAddressM.setDepartment(DEPARTMENT);
			cardlinkAddressM.setBuilding(BUILDING);
			cardlinkAddressM.setRoom(ROOM);
			cardlinkAddressM.setFloor(FLOOR);
			cardlinkAddressM.setAddress(ADDRESS_ID);
			cardlinkAddressM.setMoo(MOO);
			cardlinkAddressM.setSoi(SOI);
			cardlinkAddressM.setRoad(ROAD);
			cardlinkAddressM.setCountry(COUNTRY);
			cardlinkAddressM.setTambol(TAMBOL);
			cardlinkAddressM.setAmphur(AMPHUR);
			cardlinkAddressM.setProvinceDesc(PROVINCE);
			cardlinkAddressM.setZipcode(ZIPCODE);
			cardlinkAddressM.setVilapt(ADDRESS_NAME);
			cardlinkAddressM.setCompanyTitle(COMPANY_TITLE);
			if(!Util.empty(ADRSTS))	cardlinkAddressM.setAdrsts(ADRSTS);
			if(!Util.empty(RESIDEY)&&Util.isBigDecimal(RESIDEY))cardlinkAddressM.setResidey(new BigDecimal(RESIDEY));
			if(!Util.empty(RESIDEM)&&Util.isBigDecimal(RESIDEM))cardlinkAddressM.setResidem(new BigDecimal(RESIDEM));
			addresses.add(cardlinkAddressM);

		}
		logger.debug("addresses: "+addresses);
	}
	
	public static AddressDataM getAddress(List<AddressDataM> addresses,String addressType){
		for (AddressDataM address:addresses) {
			if(null != addressType && addressType.equals(address.getAddressType())){
				return address;
			}
		}
		return null;
	}
	
	public static String getCardlinkLine2( AddressDataM addressData){		
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
	
	public static String getWorkCardlinkLine1(AddressDataM addressData){
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
			moo(addressData, carkLinkLine1);
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
	public static String getCurrentCardlinkLine1(AddressDataM addressData){	
		StringBuilder carkLinkLine1 = new StringBuilder();
		String EMPTY_STR = "";
		if(!Util.empty(addressData.getAddress())){
			carkLinkLine1.append(EMPTY_STR+addressData.getAddress());
			EMPTY_STR = " ";
		}	
		if(!Util.empty(addressData.getMoo())){
			moo(addressData, carkLinkLine1);
			EMPTY_STR = " ";
		}
			carkLinkLine1.append(EMPTY_STR+getVilaptBuildingSoi(addressData,true));
			EMPTY_STR = " ";
		if(!Util.empty(addressData.getRoad())){
			carkLinkLine1.append(EMPTY_STR+addressData.getRoad());
			EMPTY_STR = " ";
		}
		return carkLinkLine1.toString();
	}
	 public static String getText( String textCode)
	  {
	    Locale locale = new Locale("th", "TH");
	    ResourceBundle resource = null;
	    String baseName = locale + "/properties/getLabel";
	    if (ResourceBundleUtil.isUsed()) {
	      resource = ResourceBundle.getBundle(baseName, locale, ResourceBundleUtil.get(baseName));
	    } else {
	      resource = ResourceBundle.getBundle(baseName, locale);
	    }
	    if (!Util.empty(textCode)) {
	      try
	      {
	        return resource.getString(textCode);
	      }
	      catch (Exception localException) {}
	    }
	    return "";
	}
	 
	public static void moo(AddressDataM addressData,StringBuilder carkLinkLine1){
		String prefixMoo = " "+getText("TEXT_MOO");
		logger.debug("prefixMoo: "+prefixMoo);
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
	public static String getDocCardlinkLine1(AddressDataM addressData){
		StringBuilder carkLinkLine1 = new StringBuilder();
		String EMPTY_STR = "";
		if(!Util.empty(addressData.getAddress())){
			carkLinkLine1.append(EMPTY_STR+addressData.getAddress());
			EMPTY_STR = " ";
		}
		if(!Util.empty(addressData.getMoo())){
			moo(addressData, carkLinkLine1);
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
	public static void setAddressCurr(List<AddressDataM> addresses){
		AddressDataM addressM = new AddressDataM();
		AddressDataM cardlinkAddressM = new AddressDataM();
		String soi = "";
		addressM = getAddress(addresses,ADDRESS_TYPE_CURRENT);
		cardlinkAddressM = getAddress(addresses,ADDRESS_TYPE_CURRENT_CARDLINK);
		if(null == addressM){
			addressM = new AddressDataM();
		}

		String COMPANY_NAME = addressM.getCompanyName();
		String BUILDING =  addressM.getBuilding();
		String ROOM =  addressM.getRoom();
		String FLOOR =  addressM.getFloor();
		String ADDRESS_ID = addressM.getAddress();
		String SOI =  addressM.getSoi();
		String ROAD =  addressM.getRoad();
		String VILAPT = (!Util.empty(addressM.getVilapt())?addressM.getVilapt():"");
		
		if(!Util.empty(COMPANY_NAME)){
			COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim());
		}
		if(Util.empty(ADDRESS_ID)){
			ADDRESS_ID = "";
		}	
		if(!Util.empty(SOI)){
			SOI = TEXT_SOI+SOI;
		}else SOI="";
		if(!Util.empty(ROAD) || !Util.empty(SOI)){
			ROAD =SOI+(!Util.empty(SOI)?Blank_Space:"")+(!Util.empty(ROAD)?TEXT_ROAD+ROAD:"");
		}
		if(!Util.empty(BUILDING)){
			BUILDING = TEXT_BUILDING+BUILDING;
		}
		if(!Util.empty(ROOM)){
			ROOM = TEXT_ROOM+ROOM;
		}
		if(!Util.empty(FLOOR)){
			FLOOR = TEXT_FLOOR+FLOOR;
		}
		if(!Util.empty(VILAPT)){
			VILAPT = TEXT_ADDRESS_NAME+VILAPT;
		}
		if(!Util.empty(VILAPT)){
			soi +=VILAPT;
		}
		if(!Util.empty(BUILDING)){
			if(!Util.empty(soi)){
				soi +=" ";
			}
			soi +=BUILDING;
		}
		if(!Util.empty(ROOM)){
			if(!Util.empty(soi)){
				soi +=" ";
			}
			soi +=ROOM;
		}
		if(!Util.empty(FLOOR)){
			if(!Util.empty(soi)){
				soi +=" ";
			}
			soi +=FLOOR;
		}

		cardlinkAddressM.setSoi(soi);
		cardlinkAddressM.setRoad(ROAD);
		cardlinkAddressM.setRoom(null);
		cardlinkAddressM.setFloor(null);
		cardlinkAddressM.setBuilding(null);
		cardlinkAddressM.setVilapt(null);
		logger.debug("addresses: "+addresses);
	}
	public static void checkLengthAddr(List<AddressDataM> addresses){
		for(AddressDataM addressM : addresses){
			if(null == addressM){
				addressM = new AddressDataM();
			}
			if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(addressM.getAddressType()) || ADDRESS_TYPE_WORK_CARDLINK.equals(addressM.getAddressType()) || ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(addressM.getAddressType())){
				String ROOM =  addressM.getRoom();
				String FLOOR =  addressM.getFloor();
				String SOI =  addressM.getSoi();
				String ROAD =  addressM.getRoad();
				String MOO = addressM.getMoo();
				logger.debug("addressM Before: "+addressM);
				if(!Util.empty(SOI) && SOI.length()>SOI_MAX){
					SOI=SOI.substring(0, SOI_MAX);
				}
				if(!Util.empty(ROAD) && ROAD.length()>ROAD_MAX){
					ROAD=ROAD.substring(0, ROAD_MAX);
				}
				if(!ADDRESS_TYPE_CURRENT_CARDLINK.equals(addressM.getAddressType())){
					if(!Util.empty(FLOOR) && FLOOR.length()>FLOOR_MAX){
						FLOOR=FLOOR.substring(0, FLOOR_MAX);
					}
					if(!Util.empty(MOO) && MOO.length()>MOO_MAX){
						MOO=MOO.substring(0, MOO_MAX);
					}
					if(!Util.empty(ROOM) && ROOM.length()>ROOM_MAX){
						ROOM=ROOM.substring(0, ROOM_MAX);
					}
					addressM.setFloor(FLOOR);
					addressM.setMoo(MOO);
					addressM.setRoom(ROOM);
				}
				addressM.setSoi(SOI);
				addressM.setRoad(ROAD);
				
				logger.debug("addressM After: "+addressM);
			}

		}	
		
	}
}
