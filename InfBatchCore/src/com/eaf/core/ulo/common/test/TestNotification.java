package com.eaf.core.ulo.common.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.api.InfBatchManager;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.inf.batch.ulo.notification.condition.NotificationCondition;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.NotificationInfoDataM;
import com.eaf.service.common.iib.mapper.util.ProcessUtil;
import com.google.gson.Gson;

import decisionservice_iib.ReferCriteriaDataM;

public class TestNotification extends InfBatchManager{
	public TestNotification(){
		super();
	}
	static transient Logger logger;
	public static void main(String[] args) {
/*		execute(getInfBatchRequest(args));
		NotificationInfoDataM notificationInfoDataM = new NotificationInfoDataM();
		notificationInfoDataM.setApplicationGroupId("3635");
		notificationInfoDataM.setSaleType("01");
		notificationInfoDataM.setSendingTime("DE2");
		notificationInfoDataM.setApplicationStatus("Approve");
		NotifyRequest notifyRequest =  new NotifyRequest();
		notifyRequest.setRequestObject(notificationInfoDataM);
		notifyRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_NOTIFICATION);
		System.out.println("notifyRequest>>"+notifyRequest.getNotifyId());
		NotifyController notifyController = new NotifyController();
		notifyController.notify(notifyRequest);	*/
		/*ArrayList<JobCodeDataM> jobList  = new ArrayList<JobCodeDataM>();
		JobCodeDataM jobCode= new JobCodeDataM();
		jobCode.setJobCode("SD3A");
		jobList.add(jobCode);
		
		NotificationCondition noticond = new NotificationCondition();
		System.out.println(new Gson().toJson(noticond.getSendToVCEmpManagers("06", jobList)));*/
		
//		TestNotification  test = new TestNotification();
//		System.out.println(test.getAddressIdFormat("1233456789"));
		BigDecimal test = new BigDecimal("3000.323");
	 
/*		System.out.println(test.precision());
		System.out.println(test.scale());
		System.out.println(test.signum());
		System.out.println(test.unscaledValue());
		System.out.println(new MathContext(test.scale()+3));*/
		
		
		/*
		BigDecimal inputValue = BigDecimal.valueOf(1548.5649); 
		
		BigDecimal moneyConstant = new BigDecimal(1000);
		BigDecimal fraction = inputValue.remainder(moneyConstant);	
		if(fraction.compareTo(moneyConstant)<0){
			// do smthing here
		}*/

		List<ReferCriteriaDataM> iibRe = new ArrayList<ReferCriteriaDataM>();
		ReferCriteriaDataM iibTes = new ReferCriteriaDataM();
		iibTes.setName("wee");
		iibRe.add(iibTes);
		iibTes = new ReferCriteriaDataM();
		iibTes.setName("wee2");
		iibRe.add(iibTes);
		iibTes = new ReferCriteriaDataM();
		iibTes.setName("wee3");
		iibRe.add(iibTes);
		
		
		System.out.println("existSrcOfDataCis>>"+new Gson().toJson(ProcessUtil.selectReferCriterias(ProcessUtil.mapReferCriterias(iibRe)))   );
		


	}
	
	public String getAddressIdFormat(String addressId){
		if(null == addressId){
			return "";
		}		
		return String.format("%15s",addressId).replace(' ','0');
	}
}
