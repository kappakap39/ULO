package com.avalant.wm.common;

import java.util.Calendar;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.service.common.api.ServiceApplicationDate;

public class WorkManagerUtil {

	public static boolean isWorkHour(Calendar runTime){
		String WORK_MANAGER_START_TIME = SystemConfig.getGeneralParam("WORK_MANAGER_START_TIME");
		String WORK_MANAGER_END_TIME = SystemConfig.getGeneralParam("WORK_MANAGER_END_TIME");
		
		int startWorkingHour = Integer.parseInt(WORK_MANAGER_START_TIME.split(":")[0]);
		int startWorkingMin = Integer.parseInt(WORK_MANAGER_START_TIME.split(":")[1]);
		Calendar startWorkingTime = ServiceApplicationDate.getCalendar();
		startWorkingTime.set(Calendar.HOUR_OF_DAY, startWorkingHour);
		startWorkingTime.set(Calendar.MINUTE, startWorkingMin);
		startWorkingTime.set(Calendar.SECOND, 0);
		
		int endWorkingHour = Integer.parseInt(WORK_MANAGER_END_TIME.split(":")[0]);
		int endWorkingMin = Integer.parseInt(WORK_MANAGER_END_TIME.split(":")[1]);
		Calendar endWorkingTime = ServiceApplicationDate.getCalendar();
		endWorkingTime.set(Calendar.HOUR_OF_DAY, endWorkingHour);
		endWorkingTime.set(Calendar.MINUTE, endWorkingMin);
		endWorkingTime.set(Calendar.SECOND, 0);
		
//		System.out.println("startWorkingTime : " + startWorkingTime.getTime());
//		System.out.println("endWorkingTime : " + endWorkingTime.getTime());
//		System.out.println("runTime : " + runTime.getTime());
		
		if(startWorkingTime.compareTo(runTime) <= 0 && endWorkingTime.compareTo(runTime) >= 0){
			return true;
		}
		
		return false;
	}
	
	public static boolean isWorkHourCJD(Calendar runTime){
		String WORK_MANAGER_START_TIME = SystemConfig.getGeneralParam("WORK_MANAGER_START_TIME");
		String WORK_MANAGER_END_TIME = SystemConfig.getGeneralParam("WORK_MANAGER_END_TIME");
		
		int startWorkingHour = Integer.parseInt(WORK_MANAGER_START_TIME.split(":")[0]);
		int startWorkingMin = Integer.parseInt(WORK_MANAGER_START_TIME.split(":")[1]);
		Calendar startWorkingTime = ServiceApplicationDate.getCalendar();
		startWorkingTime.set(Calendar.HOUR_OF_DAY, startWorkingHour);
		startWorkingTime.set(Calendar.MINUTE, startWorkingMin);
		startWorkingTime.set(Calendar.SECOND, 0);
		
		int endWorkingHour = Integer.parseInt(WORK_MANAGER_END_TIME.split(":")[0]);
		int endWorkingMin = Integer.parseInt(WORK_MANAGER_END_TIME.split(":")[1]);
		Calendar endWorkingTime = ServiceApplicationDate.getCalendar();
		endWorkingTime.set(Calendar.HOUR_OF_DAY, endWorkingHour);
		endWorkingTime.set(Calendar.MINUTE, endWorkingMin);
		endWorkingTime.set(Calendar.SECOND, 0);
		
//		System.out.println("startWorkingTime : " + startWorkingTime.getTime());
//		System.out.println("endWorkingTime : " + endWorkingTime.getTime());
//		System.out.println("runTime : " + runTime.getTime());
		
		if(startWorkingTime.compareTo(runTime) <= 0 && endWorkingTime.compareTo(runTime) >= 0){
			return true;
		}
		
		return false;
	}
}
