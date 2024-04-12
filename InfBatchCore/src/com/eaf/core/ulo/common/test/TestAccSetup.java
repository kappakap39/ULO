package com.eaf.core.ulo.common.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.exception.EncryptionException;

public class TestAccSetup {
	public static void main(String[] args) {
		try(FileInputStream inputStream = new FileInputStream("/Users/Ekapol/Desktop/FLP_LOG/2017-05-11/acc_setup/output.txt")) {     
		    String everything = IOUtils.toString(inputStream);
		    replaceCardNoWithDecryptedValue(everything);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String replaceCardNoWithDecryptedValue(String text){
		if(text == null || text.isEmpty()){
			return "";
		}
		
		final Pattern pattern = Pattern.compile("<card_no>(.+?)</card_no>");
		final Matcher matcher = pattern.matcher(text);
		int count = 1;
//		Encryptor encryt = EncryptorFactory.getDIHEncryptor();
		final String spaces = "                ";
		StringBuffer bufStr = new StringBuffer();

		while(matcher.find()){
			String value = matcher.group(1);
			if(value == null || value.isEmpty())continue;
			String newVal = null;
			if("0000000000000000".equals(value)){
				matcher.appendReplacement(bufStr, "<card_no>"+spaces+"</card_no>");
//				try {
//					encryt.decrypt(value);
//				} catch (EncryptionException e) {
//					e.printStackTrace();
//				}
			}else{
				matcher.appendReplacement(bufStr, "<card_no>"+count+"</card_no>");
			}
//			System.out.println(newVal);
			count ++;
		}
		System.out.println("All records = "+count);
		System.out.println("Print = "+bufStr.toString());
//		int count = matcher.groupCount();
//		System.out.println("Matches count : "+count);
//		for(int i = 0; i < count; i ++){
//			System.out.println(matcher.group(i+1));
//		}
		return null;
	}
}
