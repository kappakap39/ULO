package com.eaf.orig.shared.utility;

import org.apache.log4j.Logger;

public class CalculateTime{	
	 static Logger logger = Logger.getLogger(CalculateTime.class);
	 
	 public CalculateTime(String process){
		 this.startTime = 0;
		 this.endTime = 0;
		 this.process = process;
		 start();
	 }
	 
	 private String process;
	 private long startTime;
	 private long endTime;
	 
	 private String subProcess;
	 private long subStartTime;
	 private long subEndTime;
	 
	 public void doTime(String subProcess){
		 this.subProcess = subProcess;
		 this.subStartTime = System.nanoTime();
	 }
	 
	 public void endTime(){
		 this.subEndTime = System.nanoTime();
		 debugTime();
	 }
	 public void debugTime(){
		 logger.debug(">> Process() "+process+"#"+subProcess+" >> TIME : "+time("sub"));
	 }
	 
	 public void start(){
		 this.startTime = System.nanoTime();
	 }
	 
	 public void end(){
		 this.endTime = System.nanoTime();
		 debug();
	 }
	 
	 public void debug(){
		 logger.debug(">> Process() "+process+" >> TOTAL TIME : "+time("main"));
	 }
	 
	 private String time(String type) {
		  long nanoSecs = ("sub".equals(type))? this.subEndTime - this.subStartTime : this.endTime - this.startTime;
		  int minutes    = (int) (nanoSecs / 60000000000.0);
		  int seconds    = (int) (nanoSecs / 1000000000.0)  - (minutes * 60);
		  int millisecs  = (int) ( ((nanoSecs / 1000000000.0) - (seconds + minutes * 60)) * 1000);
	      if (minutes == 0 && seconds == 0)
	         return millisecs + "ms";
	      else if (minutes == 0 && millisecs == 0)
	         return seconds + "s";
	      else if (seconds == 0 && millisecs == 0)
	         return minutes + "min";
	      else if (minutes == 0)
	         return seconds + "s " + millisecs + "ms";
	      else if (seconds == 0)
	         return minutes + "min " + millisecs + "ms";
	      else if (millisecs == 0)
	         return minutes + "min " + seconds + "s";

	      return minutes + "min " + seconds + "s " + millisecs + "ms";
	}
}
