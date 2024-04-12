package com.eaf.orig.ulo.control.address.util;

import java.util.Iterator;
import java.util.List;

import com.ibm.th.cis.twoline.IOptions;
import com.ibm.th.cis.twoline.ISplitFieldData;
import com.ibm.th.cis.twoline.ITwoLineData;
import com.ibm.th.cis.twoline.ITwoLineFactory;
import com.ibm.th.cis.twoline.ITwoLineGenerator;
import com.ibm.th.cis.twoline.implementation.STwoLineFactory;

public class Test {

	static final String ATL_VERSION = "SBAS_15";	//Address Processor Version
	static final int Atl1_Len = 50;					//SAFE ATL1 length
	static final int Atl2_Len = 30;					//SAFE ATL2 length
	static final boolean IsCutBetweenWord = false;	//ALWAYS FALSE
	/**
	 * Test:main
	 * <p>
	 * TODO [add description]
	 * @param args
	 * @since Aug 26, 2011 5:28:59 PM
	 * ï¿½ 2009-2010 IBM Corporation
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		_testNew();
	}

	private static void _testNew(){
		
		ITwoLineFactory fac = STwoLineFactory.Instance();
		IOptions options;
		ISplitFieldData splitFieldData;
		ITwoLineData addressTwoLine;
		
		options = fac.NewOptions(
				Atl1_Len, 
				Atl2_Len, 
				IsCutBetweenWord);

		/*
		 * Thailand address Split Fields
		 */
//		splitFieldData = fac.NewSplitfieldData(
//				"000000035171001", 
//				"", 
//				0, 
//				"",						
//				"", 					//place
//				"", 					//PO
//				"777/77",					// number 
//				"",						//room 
//				"", 					//moo 
//				"", 					//mooban 
//				"à¹‚à¸šà¹Šà¹€à¸šà¹Šà¸—à¸²à¸§à¹€à¸§à¸­à¸£à¹Œ",			//bldg
//				"",	//fl
//				"à¸›à¸£à¸°à¸Šà¸²à¸£à¸²à¸©à¸�à¸£à¹Œà¸šà¸³à¹€à¸žà¹‡à¸�", 					//soi
//				"à¸›à¸£à¸°à¸Šà¸²à¸£à¸²à¸©à¸�à¸£à¹Œ",					//road 
//				"à¸ªà¸¡à¹€à¸�?à¹‡à¸ˆà¹€à¸ˆà¹‰à¸²à¸žà¸£à¸°à¸¢à¸²", 					//tambol
//				"à¸„à¸¥à¸­à¸‡à¸ªà¸²à¸™", 					//amphor
//				"à¸�à¸—à¸¡.", 					//province
//				"10600", 				//zip
//				"TH", 					//country code
//				"", 					//ATL1
//				""						//ATL2
//				);
		
		splitFieldData = fac.NewSplitfieldData(
				"000000035171001", 
				"", 
				0, 
				"",						
				"", 					//place
				"", 					//PO
				"96/1",					// number 
				"",						//room 
				"7", 					//moo 
				"", 					//mooban 
				"",			//bldg
				"",	//fl
				"à¹‚à¸•à¹ˆà¸‡à¹‚à¸•à¹‰à¸™", 					//soi
				"à¸«à¸¡à¸¹à¹ˆà¸šà¹‰à¸²à¸™à¹‚à¸•à¹ˆà¸‡à¹‚à¸•à¹‰à¸™",					//road 
				"à¹‚à¸™à¸™à¸ªà¸°à¸­à¸²à¸�?", 					//tambol
				"à¸¨à¸£à¸µà¸šà¸¸à¸�à¹€à¸£à¸·à¸­à¸‡", 					//amphor
				"à¸«à¸™à¸­à¸‡à¸šà¸±à¸§à¸¥à¸³à¸ à¸¹", 					//province
				"39180", 				//zip
				"TH", 					//country code
				"", 					//ATL1
				""						//ATL2
				);
		List results = _getATL(splitFieldData,options);
		
		Iterator i = results.iterator();
		while(i.hasNext()){
			addressTwoLine = (ITwoLineData) i.next();
			int len1, len2;
			len1 = addressTwoLine.GetAddressLine1().length();
			len2 = addressTwoLine.GetAddressLine2().length();
			System.out.println(
					addressTwoLine.GetFinalState() + "/"
					+ addressTwoLine.GetMaxState() + "\t"
					+ len1 + "+" + len2 + "=" + (len1+len2) + "\t"
					+ addressTwoLine.GetAddrId() + ", "
					+ addressTwoLine.GetAddressLine1() + ", "
					+ addressTwoLine.GetAddressLine2()
			);
		}
	}
	private static List _getATL(ISplitFieldData splitField, IOptions options){
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
