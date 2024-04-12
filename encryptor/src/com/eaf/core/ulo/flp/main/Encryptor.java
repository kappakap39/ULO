package com.eaf.core.ulo.flp.main;

import com.eaf.core.ulo.flp.util.FLPPasswordUtil;

public class Encryptor {
	public static void main(String[] args) {
		String function = "";
		String value = "";
		String output = "";
		try{
			function = args[0];
		}catch(Exception e){}
		try{
			value = args[1];
		}catch(Exception e){}
		if("encrypt".equals(function)){
			output = FLPPasswordUtil.encrypt(value);
		}else if("decrypt".equals(function)){
			output = FLPPasswordUtil.decrypt(value);
		}else{
			System.out.println("Please input args encrypt,decrypt.");
		}
		System.out.println(output);
	}
}
