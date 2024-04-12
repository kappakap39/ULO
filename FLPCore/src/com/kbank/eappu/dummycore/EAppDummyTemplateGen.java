package com.kbank.eappu.dummycore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kbank.eappu.model.Message;
import com.kbank.eappu.model.MessageData;
import com.kbank.eappu.model.MessageHeader;

public class EAppDummyTemplateGen {


	public static Message getDummyMsg(String templateId) throws InstantiationException, IllegalAccessException {
		if("50001".equals(templateId)) {
			return getDummy50001();
		}else if("50002".equals(templateId)) {
			return getDummy50002();
		}else if("50003".equals(templateId)) {
			return getDummy50003();
		}
		return null;
	}
	
	public static Map<String, Message> getDummyMsgTemplates() throws InstantiationException, IllegalAccessException {
		Map<String, Message> dumMsgs = new HashMap<>();
		dumMsgs.put("50001", getDummy50001());
		dumMsgs.put("50002", getDummy50002());
		dumMsgs.put("S0001", getDummy50003());
		return dumMsgs;
	}

	private static Message getDummy50001() throws InstantiationException, IllegalAccessException {
		Message msg = new Message();
		MessageHeader header = new MessageHeader();
		MessageData data = new MessageData();
		msg.setHeader(header);
		msg.setData(data);
		
		//header
		//appId
		header.setAppId("715");

		//data
		//contentCategory
		data.setContentCategory("data");
		//authType
		data.setAuthType(0);
		
		
		
		//< and > could be both unicode and escape sequence but in transform() must replace both of them too.
		String landingPageMsgBodyTHStr = "{ \"paragraph1\": \r\n\"�?ารสมัคร 50001 <Card type> ของคุณไม่ผ่าน�?ารอนุมัติ เนื่องจา�?\r\n\r\n•ผู้สมัครบัตรหลั�? ต้องมีอายุระหว่าง 20-80 ปี \r\n•ผู้สมัครบัตรเสริม ต้องมีอายุระหว่าง 15-80 ปี \r\n\r\nสอบถามข้อมูลเพิ่มเติมได้ที่ K-Contact Center โทร02-8888888\" }";
		JsonParser parser = new JsonParser();
		JsonObject landingPageMsgBodyTHObj = parser.parse(landingPageMsgBodyTHStr).getAsJsonObject();
		
		data.setLandingPageMsgBodyTH(landingPageMsgBodyTHObj);
		
		return msg;
	}
	
	private static Message getDummy50002() throws InstantiationException, IllegalAccessException {
		Message msg = new Message();
		MessageHeader header = new MessageHeader();
		MessageData data = new MessageData();
		msg.setHeader(header);
		msg.setData(data);
		
		//header
		//appId
		header.setAppId("715");

		//data
		//contentCategory
		data.setContentCategory("data");
		//authType
		data.setAuthType(0);
		
		
		
		//< and > could be both unicode and escape sequence but in transform() must replace both of them too.
		String landingPageMsgBodyTHStr = "{ \"paragraph1\": \r\n" + 
				"\"�?ารสมัคร  50002 <Card type> ของคุณไม่ผ่าน�?ารอนุมัติ เนื่องจา�?\r\n" + 
				"\r\n" + 
				"•ผู้สมัครบัตรหลั�? ต้องมีอายุระหว่าง 20-80 ปี \r\n" + 
				"•ผู้สมัครบัตรเสริม ต้องมีอายุระหว่าง 15-80 ปี \r\n" + 
				"\r\n" + 
				"สอบถามข้อมูลเพิ่มเติมได้ที่ K-Contact Center โทร02-8888888\" }";
		JsonParser parser = new JsonParser();
		JsonObject landingPageMsgBodyTHObj = parser.parse(landingPageMsgBodyTHStr).getAsJsonObject();
		
		data.setLandingPageMsgBodyTH(landingPageMsgBodyTHObj);
		
		return msg;
	}
	
	private static Message getDummy50003() throws InstantiationException, IllegalAccessException {
		Message msg = new Message();
		MessageHeader header = new MessageHeader();
		MessageData data = new MessageData();
		msg.setHeader(header);
		msg.setData(data);
		
		//header
		//appId
		header.setAppId("715 <Card type> 777");

		//data
		//contentCategory
		data.setContentCategory("data");
		data.setCampaignCode("CAMP_CODE_01 payload <Card type> footer ");
		//authType
		data.setAuthType(0);
		
		
		
		//< and > could be both unicode and escape sequence but in transform() must replace both of them too.
		String landingPageMsgBodyTHStr = "{ \"paragraph1\": \r\n" + 
				"\"�?ารสมัคร 50003 <Card type>  ของคุณไม่ผ่าน�?ารอนุมัติ เนื่องจา�?\r\n" + 
				"\r\n" + 
				"•ผู้สมัครบัตรหลั�? ต้องมีอายุระหว่าง 20-80 ปี \r\n" + 
				"•ผู้สมัครบัตรเสริม ต้องมีอายุระหว่าง 15-80 ปี \r\n" + 
				"\r\n" + 
				"สอบถามข้อมูลเพิ่มเติมได้ที่ K-Contact Center โทร02-8888888\" }";
		JsonParser parser = new JsonParser();
		JsonObject landingPageMsgBodyTHObj = parser.parse(landingPageMsgBodyTHStr).getAsJsonObject();
		
		//String array
		//2.45 landingPageImgTH Mandatory String Array
		List<String> strs = new ArrayList<>(3);
		strs.add("Str arr item 1 <Card type> .");
		strs.add("Str arr item 2.");
		strs.add("Str arr Footer");
		data.setLandingPageImgTH(strs);
		
		
		data.setLandingPageMsgBodyTH(landingPageMsgBodyTHObj);
		
		return msg;
	}

	public static void fillDummyData50001_1(Message msg) {
		//Fill header
		msg.getHeader().setRequestDateTime("20180622043148");
		msg.getHeader().setRequestUniqueId("715_201812312359_123456");
		msg.getHeader().setMobileNo("0812345678");
		
		//Fill data
		
		
		//Fill extra payloads
		msg.setCardType("KWave Credit Card");
		
	}
	
	public static void fillDummyData50001_2(Message msg) {
		//Fill header
		msg.getHeader().setRequestDateTime("20180622043149");
		msg.getHeader().setRequestUniqueId("715_201812312359_123457");
		msg.getHeader().setMobileNo("0812345679");
		
		//Fill data
		
		
		//Fill extra payloads
		msg.setCardType("KWave Credit Card 22");
		
	}
	
	public static void fillDummyData50002_1(Message msg) {
		//Fill header
		msg.getHeader().setRequestDateTime("20180622043150");
		msg.getHeader().setRequestUniqueId("715_201812312359_123458");
		msg.getHeader().setMobileNo("0812345680");
		
		//Fill data
		
		
		//Fill extra payloads
		msg.setCardType("Bluecard");
		
	}
	

	public static void fillDummyDataS000X(Message msg) {
		//Fill header
		msg.getHeader().setRequestDateTime("20180622043150");
		msg.getHeader().setRequestUniqueId("715_201812312359_123458");
		msg.getHeader().setMobileNo("0812345680");
		
		msg.setCardType("MasterCard Super, KWave, KBluecard");
		msg.setCreditLine("1,500,000.00");
		msg.setKbankTelNo("02-8888888");
		msg.setProductName("Sample-Product-Name");
		msg.setCardNo("4112-4425-4585-5547");
		msg.setAccountLast4Digit("5987");
		msg.setTerm("1");
		msg.setInterestRate("2%");
		msg.setInstallmentAmt("30,000");
		msg.setDocuments("1.This is document line1.\n2.This is document line2.");
	}
	

	
}
