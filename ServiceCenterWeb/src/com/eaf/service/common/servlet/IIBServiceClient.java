package com.eaf.service.common.servlet;

import decisionservice_iib.ApplicationGroupDataM;


public class IIBServiceClient {
	public static void main(String[] args) {
		try{
			com.kasikornbank.ava.iibservice.IIBServiceHttpPortProxy proxy = new com.kasikornbank.ava.iibservice.IIBServiceHttpPortProxy();
			proxy._getDescriptor().setEndpoint("http://192.168.0.217:7800/IIBService");
			ApplicationGroupDataM bScoreRequest = new ApplicationGroupDataM();
			proxy.bScore(bScoreRequest);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
