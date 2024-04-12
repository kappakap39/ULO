package com.eaf.orig.ulo.control.address.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.ibm.th.cis.twoline.IOptions;
import com.ibm.th.cis.twoline.ISplitFieldData;
import com.ibm.th.cis.twoline.ITwoLineData;
import com.ibm.th.cis.twoline.ITwoLineFactory;
import com.ibm.th.cis.twoline.ITwoLineGenerator;
import com.ibm.th.cis.twoline.implementation.STwoLineFactory;

public class DisplayAddressUtil {
	private static transient Logger logger = Logger.getLogger(DisplayAddressUtil.class);
	static final String ATL_VERSION = "SBAS_15";	//Address Processor Version
	static final int Atl1_Len = 50;					//SAFE ATL1 length
	static final int Atl2_Len = 30;					//SAFE ATL2 length
	static final boolean IsCutBetweenWord = false;	//ALWAYS FALSE
	static final String COUNTRY_TH = "TH";
	
	public static String displayAddress(String addrId, String addrIpId, int addrSeq, String addrSrcStm, String place, String po, String no, String room, String moo, String mooBan, String bldg, String fl, String soi, String road, String tambol, String amphor, String province, String zip, String countryCode, String addr1, String addr2){
		if(COUNTRY_TH.equals(countryCode) || Util.empty(countryCode)){
			return getThailandAddress(addrId, addrIpId, addrSeq, addrSrcStm, place, po, no, room, moo, mooBan, bldg, fl, soi, road, tambol, amphor, province, zip, countryCode, addr1, addr2);
		}
		return getNonThailandAddress(addrId, addrIpId, addrSeq, addrSrcStm, place, po, no, room, moo, mooBan, bldg, fl, soi, road, tambol, amphor, province, zip, countryCode, addr1, addr2);
	}
	
	public static String displayAddress(AddressDataM addressM){
		String countryCode = addressM.getCountry();
		
		String addrId = addressM.getAddressId();
		String addrIpId = "";
		int addrSeq = addressM.getSeq();
		String addrSrcStm = "";	//
		String place = "";	//
		String po = "";
		String no = addressM.getAddress();
		String room = addressM.getRoom();
		String moo = addressM.getMoo();
		String mooBan = addressM.getVilapt();
		String bldg = addressM.getBuilding();
		String fl = addressM.getFloor();
		String soi = addressM.getSoi();
		String road = addressM.getRoad();
		String tambol = addressM.getTambol();
		String amphor = addressM.getAmphur();
		String province = addressM.getProvinceDesc();
		String zip = addressM.getZipcode();
		String addr1 = addressM.getAddress1();
		String addr2 = addressM.getAddress2();
		if(COUNTRY_TH.equals(countryCode) || Util.empty(countryCode)){
			return getThailandAddress(addrId, addrIpId, addrSeq, addrSrcStm, place, po, no, room, moo, mooBan, bldg, fl, soi, road, tambol, amphor, province, zip, countryCode, addr1, addr2);
		}
		return getNonThailandAddress(addrId, addrIpId, addrSeq, addrSrcStm, place, po, no, room, moo, mooBan, bldg, fl, soi, road, tambol, amphor, province, zip, countryCode, addr1, addr2);
	}
	
	private static String  getThailandAddress(String addrId, String addrIpId, int addrSeq, String addrSrcStm, String place, String po, String no, String room, String moo, String mooBan, String bldg, String fl, String soi, String road, String tambol, String amphor, String province, String zip, String countryCode, String addr1, String addr2){
		StringBuilder  resultAddress= new StringBuilder();
		ITwoLineFactory fac = STwoLineFactory.Instance();
		IOptions options;
		ISplitFieldData splitFieldData;
		ITwoLineData addressTwoLine;
		
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);

		splitFieldData = fac.NewSplitfieldData(
				FormatUtil.displayText(addrId), 
			      "",  			//addrIpId
			      addrSeq, 
			      FormatUtil.displayText(addrSrcStm), 
			      FormatUtil.displayText(place), 
			      "",			//po 
			      FormatUtil.displayText(no), 
			      "", 			//room
			      "", 			//moo
			      "", 			//mooBan
			      FormatUtil.displayText(bldg), 
			      FormatUtil.displayText(fl), 
			      "", 			//soi
			      FormatUtil.displayText(road), 
			      FormatUtil.displayText(tambol), 
			      FormatUtil.displayText(amphor), 
			      FormatUtil.displayText(province), 
			      FormatUtil.displayText(zip), 
			      getCountryCode(countryCode), 
			      "", 			//addr1
			      ""			//addr2
				);		
		List results =getATL(splitFieldData,options);
		if(!Util.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			
			resultAddress.append(FormatUtil.display(addressTwoLine.GetAddressLine1()));
			if(!Util.empty(addressTwoLine.GetAddressLine2())){
				resultAddress.append(" "+FormatUtil.display(addressTwoLine.GetAddressLine2()));
			}
		
		}
		return resultAddress.toString();
	}
	private static String getNonThailandAddress(String addrId, String addrIpId, int addrSeq, String addrSrcStm, String place, String po, String no, String room, String moo, String mooBan, String bldg, String fl, String soi, String road, String tambol, String amphor, String province, String zip, String countryCode, String addr1, String addr2){
		StringBuilder  resultAddress= new StringBuilder();
		ITwoLineFactory fac = STwoLineFactory.Instance();		
		IOptions options;						// Process Options
		ISplitFieldData splitFieldData;			// The input Split Field
		ITwoLineData addressTwoLine;			// The results
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);
		splitFieldData = fac.NewSplitfieldData(
			     FormatUtil.displayText(addrId), 
			      "",				//addrIpId 
			      addrSeq, 
			      "", 				//addrSrcStm
			      "", 				//place
			      "",				//po 
			      FormatUtil.displayText(no), 
			      FormatUtil.displayText(room), 
			      "",				//moo 
			      "",				//mooBan 
			      "",				//bldg 
			      FormatUtil.displayText(fl), 
			      "", 				//soi
			      FormatUtil.displayText(road), 
			      "",				//tambol 
			      FormatUtil.displayText(amphor), 
			      FormatUtil.displayText(province), 
			      FormatUtil.displayText(zip), 
			      getCountryCode(countryCode), 
			      "", 				//addr1
			      ""				//addr2
				);
		List results = getATL(splitFieldData,options);		
		if(!Util.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			
			resultAddress.append(FormatUtil.display(addressTwoLine.GetAddressLine1()));
			if(!Util.empty(addressTwoLine.GetAddressLine2())){
				resultAddress.append(", "+FormatUtil.display(addressTwoLine.GetAddressLine2()));
			}
			
		}
		return resultAddress.toString();
	}
	
	public static void setAddressLine(AddressDataM addressM){
		try{
			String countryCode =  getCountryCode(addressM.getCountry());
			logger.debug("countryCode >> "+countryCode);
			if(!Util.empty(countryCode) && COUNTRY_TH.equals(countryCode.trim())){
				setThailandAddressLine(addressM);
			}else{
				setNonThailandAddressLine(addressM);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void setThailandAddressLine(AddressDataM addressM){
		ITwoLineFactory fac = STwoLineFactory.Instance();
		IOptions options;
		ISplitFieldData splitFieldData;
		ITwoLineData addressTwoLine;
		
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);
		splitFieldData = fac.NewSplitfieldData(
			FormatUtil.displayText(addressM.getAddressId()), 
			"",  			//addrIpId
			addressM.getSeq(), 
			"", 
			"",
			"",			//po 
			FormatUtil.displayText(addressM.getAddress()), 
			FormatUtil.displayText(addressM.getRoom()), 			//room
			FormatUtil.displayText(addressM.getMoo()),			//moo
			FormatUtil.displayText(addressM.getVilapt()), 			//mooBan
			FormatUtil.displayText(addressM.getBuilding()), 
			FormatUtil.displayText(addressM.getFloor()), 
			FormatUtil.displayText(addressM.getSoi()), 			//soi
			FormatUtil.displayText(addressM.getRoad()), 
			FormatUtil.displayText(addressM.getTambol()), 
			FormatUtil.displayText(addressM.getAmphur()), 
			FormatUtil.displayText(addressM.getProvinceDesc()), 
			FormatUtil.displayText(addressM.getZipcode()), 
			getCountryCode(addressM.getCountry()), 
			"", 			//addr1
			""			//addr2
		);
		
		List results = getATL(splitFieldData,options);
		if(!Util.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			addressM.setAddress1(addressTwoLine.GetAddressLine1());
				addressM.setAddress2(FormatUtil.displayText(addressTwoLine.GetAddressLine2()));
		}
	}
	
	private static void setNonThailandAddressLine(AddressDataM addressM){
		ITwoLineFactory fac = STwoLineFactory.Instance();		
		IOptions options;						// Process Options
		ISplitFieldData splitFieldData;			// The input Split Field
		ITwoLineData addressTwoLine;			// The results
		
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);
		splitFieldData = fac.NewSplitfieldData(
			FormatUtil.displayText(addressM.getAddressId()), 
			"",				//addrIpId 
			addressM.getSeq(), 
			"", 				//addrSrcStm
			"", 				//place
			"",				//po 
			FormatUtil.displayText(addressM.getAddress()), 
			FormatUtil.displayText(addressM.getRoom()), 
			FormatUtil.displayText(addressM.getMoo()),				//moo 
			FormatUtil.displayText(addressM.getVilapt()),				//mooBan 
			FormatUtil.displayText(addressM.getBuilding()),				//bldg 
			FormatUtil.displayText(addressM.getFloor()), 
			FormatUtil.displayText(addressM.getSoi()), 				//soi
			FormatUtil.displayText(addressM.getRoad()), 
			FormatUtil.displayText(addressM.getTambol()),				//tambol 
			FormatUtil.displayText(addressM.getAmphur()), 
			FormatUtil.displayText(addressM.getProvinceDesc()), 
			FormatUtil.displayText(addressM.getZipcode()),
			getCountryCode(addressM.getCountry()), 
			"", 				//addr1
			""				//addr2
		);
		
		List results = getATL(splitFieldData,options);		
		if(!Util.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			addressM.setAddress1(addressTwoLine.GetAddressLine1());
				addressM.setAddress2(FormatUtil.displayText(addressTwoLine.GetAddressLine2()));
		}
	}
	
	private static List getATL(ISplitFieldData splitField, IOptions options){
		ITwoLineGenerator gen = STwoLineFactory.Instance().GetGenerator();
		List results = null;
		try {
			STwoLineFactory.Instance().SelectApl(ATL_VERSION);
			results = gen.GetTwolines(splitField, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public static String getCountryCode(String countryCode){
		if(Util.empty(countryCode)){
			return COUNTRY_TH;
		}
		return countryCode;
	}
}
