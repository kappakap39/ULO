/**
 * name: TestConsole.java
 * pack: com.kbank.cis
 * proj: TestSbasLib
 * date: Dec 18, 2009 9:22:39 PM
 */
package com.eaf.orig.ulo.control.address.util;


import java.util.Iterator;
import java.util.List;

import com.ibm.th.cis.twoline.IOptions;
import com.ibm.th.cis.twoline.ISplitFieldData;
import com.ibm.th.cis.twoline.ITwoLineData;
import com.ibm.th.cis.twoline.ITwoLineFactory;
import com.ibm.th.cis.twoline.ITwoLineGenerator;
import com.ibm.th.cis.twoline.implementation.STwoLineFactory;


/**
 * :TestConsole
 * <p>
 * Tests all key funtions of SBAS Library 
 * @author Apirut - apirut@th.ibm.com
 * @since Feb 1, 2010 9:12:06 AM
 * © 2009-2010 IBM Corporation
 */
/**
 * :TestConsole
 * <p>
 * TODO [add description] 
 * @author Apirut - apirut@th.ibm.com
 * @since Feb 1, 2010 9:21:34 AM
 * © 2009-2010 IBM Corporation
 */
public class TestConsole {
	
	static final String ATL_VERSION = "SBAS_15";	//Address Processor Version
	static final int Atl1_Len = 50;					//SAFE ATL1 length
	static final int Atl2_Len = 30;					//SAFE ATL2 length
	static final boolean IsCutBetweenWord = false;	//ALWAYS FALSE

	 /**
	 * TestConsole:main
	 * <p>
	 * @param args
	 * @since Feb 1, 2010 9:13:10 AM
	 * © 2009-2010 IBM Corporation
	 */
	public static void main(String[] args) {
		
		System.out.println("\nNON-THAILAND ADDRESS : LIMITED LENGTHS");
		_testNonThailandAddress_LimitedLength();
		
		System.out.println("\nTHAILAND ADDRESS : LIMITED LENGTHS");
		_testThailandAddress_LimitedLength();
		
		System.out.println("\nNON-THAILAND ADDRESS : UNLIMITED LENGTHS");
		_testNonThailandAddress_UnlimitedLength();
		
		System.out.println("\nTHAILAND ADDRESS : UNLIMITED LENGTHS");
		_testThailandAddress_UnlimitedLength();
	}
	
	 /**
	 * TestConsole:_testNonThailandAddress_LimitedLength
	 * <p>
	 * Generates Non-Thailand Address ATL with limited length
	 * @since Feb 1, 2010 9:14:53 AM
	 * © 2009-2010 IBM Corporation
	 */
	private static void _testNonThailandAddress_LimitedLength(){
		/*
		 * Reference to the factory
		 */
		ITwoLineFactory fac = STwoLineFactory.Instance();
		
		IOptions options;						// Process Options
		ISplitFieldData splitFieldData;			// The input Split Field
		ITwoLineData addressTwoLine;			// The results
		
		/*
		 * Options with limited length
		 */
		options = fac.NewOptions(
				Atl1_Len, 
				Atl2_Len, 
				IsCutBetweenWord);
		
		/*
		 * A dummy Split Field for out test
		 * (non-Thailand address)
		 */
		splitFieldData = fac.NewSplitfieldData(
				"000000035171001", 
				"", 
				0,
				"",							//place
				"", 
				"", 
				"205",						// number 
				"Teng & Associates Inc",	//room 
				"", 						//moo 
				"", 						//mooban 
				"", 						//bldg
				"15 Floor",					//fl
				"", 						//soi
				"North Michigan avenue",	//road 
				"", 						//tambol
				"Chicago Super City", 		//amphor
				"Illinois", 				//province
				"60626", 					//zip
				"US", 						//country code
				"", 						//ATL1
				""							//ATL2
				);
		
		/*
		 * Generates the ATL
		 */
		List results = _getATL(splitFieldData,options);
		
		/*
		 * Gets the results
		 */
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
	
	 /**
	 * TestConsole:_testNonThailandAddress_UnlimitedLength
	 * <p>
	 * Generates non-Thailand address ATL /w unlimited length
	 * @since Feb 1, 2010 9:21:36 AM
	 * © 2009-2010 IBM Corporation
	 */
	private static void _testNonThailandAddress_UnlimitedLength(){
		/*
		 * Reference to the factory
		 */
		ITwoLineFactory fac = STwoLineFactory.Instance();
		
		IOptions options;						// Process Options
		ISplitFieldData splitFieldData;			// The input Split Field
		ITwoLineData addressTwoLine;			// The results
		
		/*
		 * Options with unlimited length
		 */
		options = fac.NewOptions(
				-1,								// use -1 for unlimited length *** 
				-1, 							// use -1 for unlimited length ***
				IsCutBetweenWord);
		
		/*
		 * A dummy Split Field for out test
		 * (non-Thailand address)
		 */
		splitFieldData = fac.NewSplitfieldData(
				"000000035171001", 
				"", 
				0,
				"",						//place
				"", 
				"", 
				"205",					// number 
				"",						//room 
				"", 					//moo 
				"", 					//mooban 
				"", 					//bldg
				"15Fl",					//fl
				"", 					//soi
				"North Michigan ave",	//road 
				"", 					//tambol
				"Chicago", 				//amphor
				"IL", 					//province
				"60626", 				//zip
				"US", 					//country code
				"", 					//ATL1
				""						//ATL2
				);
		
		/*
		 * Generates the ATL
		 */
		List results = _getATL(splitFieldData,options);
		
		/*
		 * Gets the results
		 */
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
	
	 /**
	 * TestConsole:_testThailandAddress_LimitedLength
	 * <p>
	 * Generates ATL for Thailand Address /w limited length
	 * @since Feb 1, 2010 9:19:35 AM
	 * © 2009-2010 IBM Corporation
	 */
	private static void _testThailandAddress_LimitedLength(){
		
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
		splitFieldData = fac.NewSplitfieldData(
				"000000035171001", 
				"", 
				0, 
				"",						
				"ซีอาร์ซี", 					//place
				"", 					//PO
				"87/2",					// number 
				"",						//room 
				"", 					//moo 
				"", 					//mooban 
				"�?คปปิตอล ทาวเวอร์",			//bldg
				"9,11,28,30 ออลซีซั่นส์ เพลส",	//fl
				"", 					//soi
				"วิทยุ",					//road 
				"ลุมพินี", 					//tambol
				"ปทุมวัน", 					//amphor
				"�?ทม", 					//province
				"10330", 				//zip
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
	
	 /**
	 * TestConsole:_testThailandAddress_UnlimitedLength
	 * <p>
	 * Generates Thailand address /w unlimited lengths
	 * @since Feb 1, 2010 9:26:26 AM
	 * © 2009-2010 IBM Corporation
	 */
	private static void _testThailandAddress_UnlimitedLength(){
		
		ITwoLineFactory fac = STwoLineFactory.Instance();
		IOptions options;
		ISplitFieldData splitFieldData;
		ITwoLineData addressTwoLine;
		
		options = fac.NewOptions(
				-1,						// use -1 for unlimited length *** 
				-1, 					// use -1 for unlimited length ***
				IsCutBetweenWord);

		/*
		 * Thailand address Split Fields
		 */
		splitFieldData = fac.NewSplitfieldData(
				"000000035171001", 
				"", 
				0, 
				"",						
				"ซีอาร์ซี", 					//place
				"", 					//PO
				"87/2",					// number 
				"",						//room 
				"", 					//moo 
				"", 					//mooban 
				"�?คปปิตอล ทาวเวอร์",			//bldg
				"9,11,28,30 ออลซีซั่นส์ เพลส",	//fl
				"", 					//soi
				"วิทยุ",					//road 
				"ลุมพินี", 					//tambol
				"ปทุมวัน", 					//amphor
				"�?ทม", 					//province
				"10330", 				//zip
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

		
	 /**
	 * TestConsole:_getATL
	 * <p>
	 * @since Jan 8, 2010 3:35:22 PM
	 * © 2009-2010 IBM Corporation
	 */
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
