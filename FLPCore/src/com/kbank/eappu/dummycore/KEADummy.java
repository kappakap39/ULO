package com.kbank.eappu.dummycore;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.kbank.eappu.dummymodel.Contract;
import com.kbank.eappu.dummymodel.ContractGeneric;
import com.kbank.eappu.dummymodel.ContractPlus;
import com.kbank.eappu.dummymodel.Payload;

public class KEADummy {
	private static Logger logger = Logger.getLogger(KEADummy.class);

	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		
		/**
		testToJson();
		/**/
		
		/**/
		testToJsonPlus();
		/**/
		
		/**
		testToJsonUnstructured();
		/**/
		
		/**
		//testFromJson();
		/**/

	}

	private static void testFromJson() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();

		// 1. JSON to Java object, read it from a file.
		Contract ctFromFile = gson.fromJson(new FileReader("D:\\contract.json"), Contract.class);

		// 2. JSON to Java object, read it from a Json String.
		String jsonInString = "{'name' : 'Pinyosky'}";
		Contract ctFromStr = gson.fromJson(jsonInString, Contract.class);
			
		// JSON to JsonElement, convert to String later.
		JsonElement jsonEle = gson.fromJson(new FileReader("D:\\contract.json"), JsonElement.class);
	    String jsonEleStr = gson.toJson(jsonEle);
	    logger.debug("jsonEleStr = "+jsonEleStr );
	}
	
	private static void testToJsonUnstructured() throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		// 1. Convert object to JSON string
		Gson gson = new Gson();
		JsonElement je = gson.fromJson(new FileReader("D:\\contract.json"), JsonElement.class);
		JsonObject jo = je.getAsJsonObject();
		jo.addProperty("requestNo", "0011");
		String jsonEleStr = gson.toJson(jo);
	    logger.debug("jsonEleStr = "+jsonEleStr );
	}

	private static void testToJson() {

		//Create dummy contract
		Contract contract = createDummyObject();

		// 1. Convert object to JSON string
		Gson gson = new Gson();
		String json = gson.toJson(contract);
		logger.debug(json);

		// 2. Convert object to JSON string and save into a file directly
		try (FileWriter writer = new FileWriter("D:\\contract.json")) {
			gson.toJson(contract, writer);
		} catch (IOException e) {
			logger.error(null,e);
		}
		
	}

	private static void testToJsonPlus() {

		//Create dummy contract
		ContractPlus contract = createDummyObjectPlus();
		transformPlus(contract);

		// 1. Convert object to JSON string
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		String json = gson.toJson(contract);
		logger.debug(json);

		// 2. Convert object to JSON string and save into a file directly
		try (FileWriter writer = new FileWriter("D:\\contract.json")) {
			gson.toJson(contract, writer);
		} catch (IOException e) {
			logger.error(null,e);
		}
		
	}
	
	
	private static void transformPlus(ContractPlus contract) {
		contract.setName(contract.getName()+contract.getPlusVal1());
	}

	private static Contract createDummyObject() {
		ContractGeneric con = new ContractGeneric();

		con.setName("Pinyosky");
		con.setContractId(1005000);
		con.setContractNameTH("ชื่อสมมุติ 1");
		con.setContractNameEN("Sample 1");
		con.setAmount((new BigDecimal("1000000000")));
		con.setCreateDate(new Date(System.currentTimeMillis()));

		List<Integer> subContractIds = new ArrayList<>();
		subContractIds.add(1005001);
		subContractIds.add(1005002);
		subContractIds.add(1005003);
		con.setSubContract(subContractIds);
		
		List<Payload> pls = new ArrayList<>();
		Payload plOne = new Payload();
		plOne.setDesc("This is payload 1");
		plOne.setPayloadVal("This is payload 1 val");
		Payload plTwo = new Payload();
		plTwo.setDesc("This is payload 2");
		plTwo.setPayloadVal("This is payload 2 val");
		pls.add(plOne);
		pls.add(plTwo);
		con.setPayloads(pls);
		
		return con;
	}
	
	private static ContractPlus createDummyObjectPlus() {
		ContractPlus con = new ContractPlus();

		con.setName("Pinyosky");
		con.setContractId(1005000);
		con.setContractNameTH("ชื่อสมมุติ 1");
		con.setContractNameEN("Sample 1");
		con.setAmount((new BigDecimal("1000000000")));
		con.setCreateDate(new Date(System.currentTimeMillis()));

		List<Integer> subContractIds = new ArrayList<>();
		subContractIds.add(1005001);
		subContractIds.add(1005002);
		subContractIds.add(1005003);
		con.setSubContract(subContractIds);
		
		List<Payload> pls = new ArrayList<>();
		Payload plOne = new Payload();
		plOne.setDesc("This is String payload 1");
		plOne.setPayloadVal("This is Int payload 1 val");
		Payload plTwo = new Payload();
		plTwo.setDesc("This is String payload 2");
		plTwo.setPayloadVal("This is Int payload 2 val");
		pls.add(plOne);
		pls.add(plTwo);
		con.setPayloads(pls);
		
		//Plus attribute
		con.setPlusVal1("PlusStr1");
		con.setPlusVal2(2);
		
		return con;
	}

}
