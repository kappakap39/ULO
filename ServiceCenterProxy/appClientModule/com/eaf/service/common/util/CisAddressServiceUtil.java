package com.eaf.service.common.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.orig.ulo.model.app.AddressDataM;
import com.ibm.th.cis.twoline.IOptions;
import com.ibm.th.cis.twoline.ISplitFieldData;
import com.ibm.th.cis.twoline.ITwoLineData;
import com.ibm.th.cis.twoline.ITwoLineFactory;
import com.ibm.th.cis.twoline.ITwoLineGenerator;
import com.ibm.th.cis.twoline.implementation.STwoLineFactory;

public class CisAddressServiceUtil {
	private static transient Logger logger = Logger.getLogger(CisAddressServiceUtil.class);
	static final String ATL_VERSION = "SBAS_15";	//Address Processor Version
	static final int Atl1_Len = 50;					//SAFE ATL1 length
	static final int Atl2_Len = 30;					//SAFE ATL2 length
	static final boolean IsCutBetweenWord = false;	//ALWAYS FALSE
	static final String COUNTRY_TH = "TH";
	
	public static String displayAddress(String addrId, String addrIpId, int addrSeq, String addrSrcStm, String place, String po, String no, String room, String moo, String mooBan, String bldg, String fl, String soi, String road, String tambol, String amphor, String province, String zip, String countryCode, String addr1, String addr2){
		if(COUNTRY_TH.equals(countryCode)){
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
		String zip = ServiceUtil.empty(addressM.getZipcode()) ? "" : addressM.getZipcode().toString();
		String addr1 = addressM.getAddress1();
		String addr2 = addressM.getAddress2();
		if(COUNTRY_TH.equals(countryCode)){
			return getThailandAddress(addrId, addrIpId, addrSeq, addrSrcStm, place, po, no, room, moo, mooBan, bldg, fl, soi, road, tambol, amphor, province, zip, countryCode, addr1, addr2);
		}
		return getNonThailandAddress(addrId, addrIpId, addrSeq, addrSrcStm, place, po, no, room, moo, mooBan, bldg, fl, soi, road, tambol, amphor, province, zip, countryCode, addr1, addr2);
	}
	
	private static String  getThailandAddress(String addrId, String addrIpId, int addrSeq, String addrSrcStm, String place, String po, String no, String room, String moo, String mooBan, String bldg, String fl, String soi, String road, String tambol, String amphor, String province, String zip, String countryCode, String addr1, String addr2){
		StringBuilder resultAddress = new StringBuilder();
		ITwoLineFactory fac = STwoLineFactory.Instance();
		IOptions options;
		ISplitFieldData splitFieldData;
		ITwoLineData addressTwoLine;
		
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);

		splitFieldData = fac.NewSplitfieldData(
				  addrId, 
			      "",  			//addrIpId
			      addrSeq, 
			      addrSrcStm, 
			      place, 
			      "",			//po 
			      no, 
			      "", 			//room
			      "", 			//moo
			      "", 			//mooBan
			      bldg, 
			      fl, 
			      "", 			//soi
			      road, 
			      tambol, 
			      amphor, 
			      province, 
			      zip, 
			      countryCode, 
			      "", 			//addr1
			      ""			//addr2
				);		
		List results =getATL(splitFieldData,options);
		if(!ServiceUtil.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			
			resultAddress.append(ServiceUtil.display(addressTwoLine.GetAddressLine1()));
			if(!ServiceUtil.empty(addressTwoLine.GetAddressLine2())){
				resultAddress.append(", "+ServiceUtil.display(addressTwoLine.GetAddressLine2()));
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
			      addrId, 
			      "",				//addrIpId 
			      addrSeq, 
			      "", 				//addrSrcStm
			      "", 				//place
			      "",				//po 
			      no, 
			      room, 
			      "",				//moo 
			      "",				//mooBan 
			      "",				//bldg 
			      fl, 
			      "", 				//soi
			      road, 
			      "",				//tambol 
			      amphor, 
			      province, 
			      zip, 
			      countryCode, 
			      "", 				//addr1
			      ""				//addr2
				);
		List results = getATL(splitFieldData,options);		
		if(!ServiceUtil.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			
			resultAddress.append(ServiceUtil.display(addressTwoLine.GetAddressLine1()));
			if(!ServiceUtil.empty(addressTwoLine.GetAddressLine2())){
				resultAddress.append(", "+ServiceUtil.display(addressTwoLine.GetAddressLine2()));
			}
			
		}
		return resultAddress.toString();
	}
	
	public static void setAddressLine(AddressDataM addressM){
		String countryCode = addressM.getCountry();
		logger.debug("countryCode >> "+countryCode);
		if(!ServiceUtil.empty(countryCode) && COUNTRY_TH.equals(countryCode.trim())){
			setThailandAddressLine(addressM);
		}else{
			setNonThailandAddressLine(addressM);
		}
	}
	
	private static void setThailandAddressLine(AddressDataM addressM){
		ITwoLineFactory fac = STwoLineFactory.Instance();
		IOptions options;
		ISplitFieldData splitFieldData;
		ITwoLineData addressTwoLine;
		
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);
		splitFieldData = fac.NewSplitfieldData(
			addressM.getAddressId(), 
			"",  			//addrIpId
			addressM.getSeq(), 
			"", 
			"", 
			"",			//po 
			addressM.getAddress(), 
			"", 			//room
			"", 			//moo
			"", 			//mooBan
			addressM.getBuilding(), 
			addressM.getFloor(), 
			"", 			//soi
			addressM.getRoad(), 
			addressM.getTambol(), 
			addressM.getAmphur(), 
			addressM.getProvinceDesc(), 
			ServiceUtil.empty(addressM.getZipcode()) ? "" : addressM.getZipcode().toString(), 
			addressM.getCountry(), 
			"", 			//addr1
			""			//addr2
		);
		
		List results = getATL(splitFieldData,options);
		if(!ServiceUtil.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			addressM.setAddress1(addressTwoLine.GetAddressLine1());
			if(!ServiceUtil.empty(addressTwoLine.GetAddressLine2())){
				addressM.setAddress2(addressTwoLine.GetAddressLine2());
			}
		}
	}
	
	private static void setNonThailandAddressLine(AddressDataM addressM){
		ITwoLineFactory fac = STwoLineFactory.Instance();		
		IOptions options;						// Process Options
		ISplitFieldData splitFieldData;			// The input Split Field
		ITwoLineData addressTwoLine;			// The results
		
		options = fac.NewOptions(Atl1_Len, Atl2_Len, IsCutBetweenWord);
		splitFieldData = fac.NewSplitfieldData(
			addressM.getAddressId(), 
			"",				//addrIpId 
			addressM.getSeq(), 
			"", 				//addrSrcStm
			"", 				//place
			"",				//po 
			addressM.getAddress(), 
			addressM.getRoom(), 
			"",				//moo 
			"",				//mooBan 
			"",				//bldg 
			addressM.getFloor(), 
			"", 				//soi
			addressM.getRoad(), 
			"",				//tambol 
			addressM.getAmphur(), 
			addressM.getProvinceDesc(), 
			ServiceUtil.empty(addressM.getZipcode()) ? "" : addressM.getZipcode().toString(),
			addressM.getCountry(), 
			"", 				//addr1
			""				//addr2
		);
		
		List results = getATL(splitFieldData,options);		
		if(!ServiceUtil.empty(results)){
			addressTwoLine = (ITwoLineData) results.get(results.size()-1);
			addressM.setAddress1(addressTwoLine.GetAddressLine1());
			if(!ServiceUtil.empty(addressTwoLine.GetAddressLine2())){
				addressM.setAddress2(addressTwoLine.GetAddressLine2());
			}
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
}
