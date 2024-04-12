/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbtg;

import com.kbtg.core.SeperateDAO;
import com.kbtg.util.SeperateUtil;
import com.kbtg.vo.AppSlaPaVO;
import com.kbtg.vo.AppTeamPeriod;
import com.kbtg.vo.PATimeSeries;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Kritsakorn.c
 */
public class Seperate {

    final static Logger logger = Logger.getLogger(Seperate.class);

    public static HashMap<String, List<AppTeamPeriod>> calcPeriod(List<PATimeSeries> timeSeriesList) {
        //
        HashMap<String, List<AppTeamPeriod>> appTeamPeriodMap = null;

        String appGroupId = "";
        String jobOwner = "";

        if (timeSeriesList != null && timeSeriesList.size() > 0) {
            appTeamPeriodMap = new HashMap();
            List<AppTeamPeriod> appTeamPeriodList = null;
            AppTeamPeriod appTeamPeriod = null;

            for (PATimeSeries timeSeries : timeSeriesList) {
                //

                if (!timeSeries.getFromJobState().contains("NM0000") && (timeSeries.getFromJobState().contains("NM05") || timeSeries.getFromJobState().contains("NM06"))) {
                    jobOwner = "PA";

                } else if (timeSeries.getFromJobState().contains("NM0000") && (null != timeSeries.getToRole() && (timeSeries.getToRole().contains("VT") || timeSeries.getToRole().contains("CA")))) {
                    jobOwner = "PA";

                } else {
                    jobOwner = "RQ";
                }
                //logger.info(jobOwner+" Work: ");
                //logger.info(timeSeries.toString());

                appGroupId = timeSeries.getAppGroupId();
                appTeamPeriod = new AppTeamPeriod();
                appTeamPeriod.setAppGroupId(appGroupId);
                appTeamPeriod.setJobOwner(jobOwner);
                if (null == timeSeries.getReceiveDate()) {
                    appTeamPeriod.setStartDate(timeSeries.getSubmitDate());
                } else {
                    appTeamPeriod.setStartDate(timeSeries.getReceiveDate());
                }
                appTeamPeriod.setEndDate(timeSeries.getSubmitDate());

                if (!appTeamPeriodMap.isEmpty() && null != appTeamPeriodMap.get(appGroupId)) {
                    //
                    appTeamPeriodList = appTeamPeriodMap.get(appGroupId);
                    if (appTeamPeriodList.get(appTeamPeriodList.size() - 1).getAppGroupId().equals(appGroupId) && appTeamPeriodList.get(appTeamPeriodList.size() - 1).getJobOwner().equals(jobOwner)) {
                        //
                        appTeamPeriod = appTeamPeriodList.get(appTeamPeriodList.size() - 1);
                        appTeamPeriod.setEndDate(timeSeries.getSubmitDate());
                        appTeamPeriodList.set(appTeamPeriodList.size() - 1, appTeamPeriod);
                    } else {
                        appTeamPeriodList.add(appTeamPeriod);
                    }

                } else {
                    //
                    appTeamPeriodList = new ArrayList();
                    appTeamPeriodList.add(appTeamPeriod);
                    appTeamPeriodMap.put(appGroupId, appTeamPeriodList);
                }

            }
        }

        return appTeamPeriodMap;
    }

    public static List<AppSlaPaVO> calcAppTeamPeriod(HashMap<String, List<AppTeamPeriod>> appTeamPeriodMap) {
        List<AppSlaPaVO> appList = null;
        Set<String> appGroupIdSet = null;
        SeperateUtil util = new SeperateUtil();

        if (null != appTeamPeriodMap && !appTeamPeriodMap.isEmpty()) {
            //
            logger.info("periodMap.size: " + appTeamPeriodMap.size());
            appGroupIdSet = appTeamPeriodMap.keySet();
            List<AppTeamPeriod> teamList = null;

            List<String> rqWorktime = null;
            List<String> paWorktime = null;

            appList = new ArrayList();

            for (String appGroupId : appGroupIdSet) {
                teamList = appTeamPeriodMap.get(appGroupId);
                String minDate = teamList.get(0).getStartDate();
                String maxDate = teamList.get(teamList.size() - 1).getEndDate();
                //--- RQ var set ---//
                int rqRound = 0;
                //int rqDay = 0;
                int rqOvertime = 0;
                rqWorktime = new ArrayList();
                //--- PA var set ---//
                int paRound = 0;
                //int paDay = 0;
                int paSameday = 0;
                paWorktime = new ArrayList();

                for (AppTeamPeriod vo : teamList) {
                    logger.info("AppGroupId: " + appGroupId + " - group: " + vo.toString());
                    if ("RQ".equals(vo.getJobOwner())) {
                        //RQ case
                        rqWorktime.add(vo.getStartDate() + "-" + vo.getEndDate());
                        rqRound++;

                    } else {
                        //PA case
                        paWorktime.add(vo.getStartDate() + "-" + vo.getEndDate());
                        paRound++;
                    }
                }
                //
                int[] rqDay_flag = util.calcTeamPeriod(rqWorktime, rqOvertime, "RQ");
                int[] paDay_flag = util.calcTeamPeriod(paWorktime, paSameday, "PA");

                AppSlaPaVO vo = new AppSlaPaVO();
                vo.setApplicationGroupId(appGroupId);
                vo.setStartDate(util.convertStringToTimestamp(minDate));
                vo.setEndDate(util.convertStringToTimestamp(maxDate));
                vo.setWorkday(util.calcAppDay(minDate.split(" ")[0], maxDate.split(" ")[0]));
                vo.setRqRound(rqRound);
                vo.setRqDay(rqDay_flag[0]);
                vo.setRqOvertime(rqDay_flag[1]);
                vo.setPaRound(paRound);
                vo.setPaDay(paDay_flag[0]);
                vo.setPaSameday(paDay_flag[1]);
                vo.setPaPass(paDay_flag[2]);
                logger.info(vo.toString());

                appList.add(vo);
            }

        }

        return appList;
    }

    public static void main(String[] args) {

        int record = 0;
        SeperateDAO dao = new SeperateDAO();

        try {

            // 1st step: calutale Leg2
            List<PATimeSeries> timeSeriesList = dao.getPAWorking();
            logger.info("timeSeriesList.size(): " + timeSeriesList.size());
            HashMap<String, List<AppTeamPeriod>> appTeamPeriodMap = calcPeriod(timeSeriesList);
            List<AppSlaPaVO> appList = calcAppTeamPeriod(appTeamPeriodMap);

            // checking
            if (null == appList) {
                logger.info("no application");
            } else {

                // 2nd step: delete data from APP_SLA_PA
                record = dao.deleteAppSlaPa();
                logger.info("delete APP_SLA_PA in " + record + " records.");

                // 3rd step: insert data into APP_SLA_PA
                record = dao.insertAppSlaPa(appList);
                logger.info("insert APP_SLA_PA in " + record + " records.");
            }

            // 4th step: call dashboard procedure
            dao.callProcedure();
            logger.info("dashboard procedure has been execute.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
