/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbtg.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Kritsakorn.c
 */
public class SeperateUtil {
    
    final static Logger logger = Logger.getLogger(SeperateUtil.class);
    
    public int[] calcTeamPeriod(List<String> worktime, int counter, String team){
        
        int day = 0;
        int[] day_flag = new int[3];
        String minDate = "", maxDate = "";
        HashMap<String, String> dayMap = new HashMap();
        int paPass = 1;
        for (String working : worktime) {

            String mn_dt = working.split("-")[0];
            String mx_dt = working.split("-")[1];
            //logger.info("team: "+team);
            if( "RQ".equals(team) ) {
                // RQ case, Check sending task overtime
                int time = Integer.parseInt(mx_dt.split(" ")[1].replaceAll(":", "").substring(0, 4) );
                if( time >= 1500 ){
                    counter += 1;
                }
            }else{
                // PA case
                if( mn_dt.split(" ")[0].equals(mx_dt.split(" ")[0]) ){
                    counter += 1;
                    //logger.info("sameday");
                }
            }
            //--------------//
            if( (!"".equals(minDate) && !"".equals(minDate)) && mn_dt.split(" ")[0].equals(maxDate) ){
                maxDate = mx_dt.split(" ")[0];
                //dayMap.replace(minDate, maxDate); //for jdk8
                dayMap.put(minDate, maxDate); //for jdk6
            }else{
                minDate = mn_dt.split(" ")[0];
                maxDate = mx_dt.split(" ")[0];
                //totTime.add(minDate +"-"+ maxDate);
                dayMap.put(minDate, maxDate);
            }
        }
        
        Set<String> minSet = dayMap.keySet();
        for (String mnDate : minSet) {
            Date dmn = null;
            Date dmx = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            try {

                dmn = format.parse(mnDate);
                dmx = format.parse(dayMap.get(mnDate));
                //logger.info("Date: "+ mnDate);
                DateTime dtmn = new DateTime(dmn);
                DateTime dtmx = new DateTime(dmx);
                
                if( "PA".equals(team) ){
                    //logger.info("time: "+datetimeMap.get(mnDate));
                    int paDay = Days.daysBetween(dtmn, dtmx).getDays();
                    if( paDay > 1 ){
                        paPass = 0;
                    }
                    day += paDay;
                    //logger.info("PA sameday: "+ day);
                    //}
                }else{
                    day += Days.daysBetween(dtmn, dtmx).getDays() + 1;
                    //logger.info(team +": "+ day);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        day_flag[0] = day;
        day_flag[1] = counter;
        day_flag[2] = paPass;
  
        return day_flag;
    }
    
    public java.sql.Date convertStringToSqldate(String date){
        
        //SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        java.sql.Date sqlDate = null;
        try {
            logger.info("convert date: " +date);
            Date parsed = format.parse(date);
            logger.info(" @ util date: " +parsed);
            //sqlDate = new java.sql.Date(parsed.getTime());
            sqlDate = new java.sql.Date(parsed.getTime());
            logger.info(" - parsed date: " +sqlDate);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return sqlDate;
    }
    
    public Timestamp convertStringToTimestamp(String date){
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        Timestamp dateStamp = null;
        try {
            Date parsed = format.parse(date);
            dateStamp = new Timestamp(parsed.getTime());
            //
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return dateStamp;
    }
    
    public int calcAppDay(String startDate, String stopDate){
        
        int totDays = 0;
        Date dmn = null;
        Date dmx = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

        try {
            //
            dmn = format.parse(startDate);
            dmx = format.parse(stopDate);

            DateTime dtmn = new DateTime(dmn);
            DateTime dtmx = new DateTime(dmx);
            totDays += Days.daysBetween(dtmn, dtmx).getDays() + 1;
            //logger.info(AppGroupId + ": " + totDays + " days, ");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return totDays;
    }
    
    public static Properties loadProperties(String path){
        
        Properties prop = null;
        FileInputStream file = null;
        
        try {
            logger.info("load properties file from: " +path);
            file = new FileInputStream(path);
            prop = new Properties();
            prop.load(file);
            file.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
//        } finally {
//            if( null != file ){
//                file.close();
//            }
        }
        
        return prop;
    }
    
}
