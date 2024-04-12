package com.eaf.orig.ulo.pl.app.utility;

//import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
//import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigApplicationDAO;
//import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.commission.dao.PLCommisionDAO;
//import com.eaf.orig.ulo.pl.commission.dao.PLCommisionDAOImpl;
//import com.eaf.orig.ulo.pl.commission.dao.exception.PLCommisssionException;
//import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionAppDetialDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDataM;
//import com.eaf.orig.ulo.pl.model.app.comission.SATeamDataM;

public class CommissionUtility {
	private static Logger logger = Logger.getLogger(CommissionUtility.class);

	public static SAUserCommissionAppDetialDataM mapUserCommission(PLApplicationDataM applicationM) throws Exception{
		SAUserCommissionAppDetialDataM userCommision = new SAUserCommissionAppDetialDataM();
		if(applicationM.getSaleInfo() != null) {
			userCommision.setUserName(applicationM.getSaleInfo().getSalesName());
		}
		userCommision.setApplicationNo(applicationM.getApplicationNo());
		String periodNo = "";
		userCommision.setUpdateBy(OrigConstant.SYSTEM);
		userCommision.setCreateBy(OrigConstant.SYSTEM);
		userCommision.setCreditLine(applicationM.getFinalCreditLimit());
		userCommision.setSaleType(applicationM.getSaleType());
		try{
			Timestamp approveDate = applicationM.getApproveDate();
			Calendar cl = Calendar.getInstance();
			cl.setTime(approveDate);
			DecimalFormat df4 = new DecimalFormat("0000");
			DecimalFormat df2 = new DecimalFormat("00");
			periodNo = df4.format(cl.get(Calendar.YEAR)) + df2.format(cl.get(Calendar.MONTH) + 1);
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		setCommision(applicationM, userCommision);
		userCommision.setPeriodNo(periodNo);
		return userCommision;
	}

	private static void setCommision(PLApplicationDataM applicationM, SAUserCommissionAppDetialDataM userCommision) throws Exception{
//		IlogModelMapping ilogModelMapping = new IlogModelMapping();
//		XrulesRequestDataM requestM = new XrulesRequestDataM();
//		requestM.setPlAppM(applicationM);
//		ilogModelMapping.MappingModelIlog(requestM);
//		XrulesIlogServiceProxy ilogProxy = new XrulesIlogServiceProxy();
//		XrulesIlogDataM ilogData = ilogProxy.ModuleServicesCommission(requestM, ORIGConfig.ILOG_SERVICE);
//		com.ilog.xrules.ulo.model.response.XRulesDSACommissionResultM dsaCommissionM = 
//								(com.ilog.xrules.ulo.model.response.XRulesDSACommissionResultM) ilogData.getObj();
//		userCommision.setAmount(new BigDecimal(dsaCommissionM.getTotalCommission()));
	}

	private static double calculateLeaderCommision(String position, int totalApp) throws Exception{
//		XrulesIlogServiceProxy ilogProxy = new XrulesIlogServiceProxy();
//		PLXRulesLeadCommission leadCommission = new PLXRulesLeadCommission();
//			leadCommission.setPosition(position);
//			leadCommission.setTeamAppAmount(totalApp);
//		XrulesIlogDataM ilogData = ilogProxy.ModuleServicesCommissionLeader(leadCommission, ORIGConfig.ILOG_SERVICE);
//		if(ilogData.getObj() != null){
//			Double dValue = (Double) ilogData.getObj();
//			return dValue.doubleValue();
//		}
		return 0d;
	}

	public static void callCulateComission(Date calDate) throws Exception {
//		PLCommisionDAO commisionDAO = new PLCommisionDAOImpl();
//		Vector<SAUserCommissionAppDetialDataM> vApplication = null;
//		try {
//			vApplication = commisionDAO.selectApplicaionCalulateCommisson(calDate);
//		} catch (PLCommisssionException e1) {
//			e1.printStackTrace();
//		}
//		Vector<SAUserCommissionAppDetialDataM> vUserCommision = new Vector<SAUserCommissionAppDetialDataM>();
//		if ( vApplication != null) {
//			PLOrigApplicationDAO appDAO = PLORIGDAOFactory.getPLOrigApplicationDAO();
//			for (int i = 0; i < vApplication.size(); i++) {
//				SAUserCommissionAppDetialDataM comission = vApplication.get(i);
//				try {
//					PLApplicationDataM plApplicationDataM = appDAO.loadOrigApplicationAppNo(comission.getApplicationNo());
//					logger.debug("plApplication " + plApplicationDataM.getApplicationNo());
//					// cal culate Point
//					logger.debug("Approve Date=" + plApplicationDataM.getApproveDate());
//					if (plApplicationDataM.getApproveDate() != null) {
//						SAUserCommissionAppDetialDataM saveUserCommission = CommissionUtility.mapUserCommission(plApplicationDataM);
//						// inset into table Commision
//						vUserCommision.add(saveUserCommission);
//					}
//					// XrulesResponseDataM respon
//
//				} catch (PLOrigApplicationException e) {
//					logger.fatal("error", e);
//					e.printStackTrace();
//				}
//			}
//			try {
//				commisionDAO.createUserCommissionAppDetial(vUserCommision);
//			} catch (PLCommisssionException e) {
//				logger.fatal("Error", e);
//				e.printStackTrace();
//			}
//		}
	}

	public static void callCulateComissionPeriod(String period) {
//		PLCommisionDAO commisionDAO = new PLCommisionDAOImpl();
//		Vector<SAUserCommissionDataM> vCommissionData = null;
//		Vector<SAUserCommissionDataM> vCommissionSaveData = null;
//		Vector<SAUserCommissionDetialsDataM> vCommissionDetail = null;
//		try {
//			logger.debug("period=" + period);
//			vCommissionData = commisionDAO.selectCommisionPeriod(period);
//		} catch (PLCommisssionException e1) {
//			logger.fatal("error", e1);
//			e1.printStackTrace();
//		}
//		// cal Ilog for cal Period
//		vCommissionSaveData = new Vector<SAUserCommissionDataM>();
//
//		if (vCommissionData != null) {
//			logger.debug("vCommissionData size=" + vCommissionData.size());
//			BigDecimal bZero = new BigDecimal(0);
//			for (int i = 0; i < vCommissionData.size(); i++) {
//				SAUserCommissionDataM commissionData = vCommissionData.get(i);
//				SAUserCommissionDataM commissionMALWData = null;
//				try {
//					commissionMALWData = getCommissionMALW(commissionData);
//				} catch (Exception e) {
//					logger.fatal("error", e);
//					e.printStackTrace();
//				}
//				if (commissionMALWData != null) {
//					commissionMALWData.setUpdateBy(OrigConstant.SYSTEM);
//					commissionMALWData.setCreateBy(OrigConstant.SYSTEM);
//					if (bZero.compareTo(commissionMALWData.getIncomeAmount()) < 0) {
//						vCommissionSaveData.add(commissionMALWData);
//					}
//				}
//				commissionData.setUpdateBy(OrigConstant.SYSTEM);
//				commissionData.setCreateBy(OrigConstant.SYSTEM);
//				if (bZero.compareTo(commissionData.getIncomeAmount()) < 0) {
//					vCommissionSaveData.add(commissionData);
//				}
//			}
//
//		}
//		try {
//			commisionDAO.createUserCommissionData(vCommissionSaveData);
//		} catch (PLCommisssionException e) {
//			logger.fatal("Error", e);
//			e.printStackTrace();
//		}
//		// =================================MALW=========================
//
//		logger.debug("Delete Commission Details");
//		try {
//			commisionDAO.deleteUserCommissionDetailData(period);
//		} catch (PLCommisssionException e) {
//			logger.fatal("Error", e);
//			e.printStackTrace();
//		}
//		try {
//			vCommissionDetail = commisionDAO.selectCommisionPeriodDetail(period);
//			if (vCommissionDetail != null) {
//				commisionDAO.createUserCommissionDetailsData(vCommissionDetail);
//			}
//
//		} catch (PLCommisssionException e) {
//			logger.fatal("Error", e);
//			e.printStackTrace();
//		}

	}

	public static SAUserCommissionDataM getCommissionMALW(SAUserCommissionDataM commissionData) throws Exception{
		SAUserCommissionDataM resultDataM = null;
//		PLCommisionDAO commisionDAO = new PLCommisionDAOImpl();
//		SATeamDataM teamData = commisionDAO.getTeamData(commissionData.getPeriodNo(), commissionData.getUserName());
//		if(null != teamData){
//			double commisionValue = calculateLeaderCommision(teamData.getTeamName(), teamData.getTotalApp());
//			resultDataM = new SAUserCommissionDataM();
//			resultDataM.setUserName(commissionData.getUserName());
//			resultDataM.setUpdateBy(OrigConstant.SYSTEM);
//			resultDataM.setCreateBy(OrigConstant.SYSTEM);
//			resultDataM.setIncomeType(OrigConstant.COMMISSION_INCOME_TYPE_MALW);
//			resultDataM.setIncomeAmount(new BigDecimal(commisionValue));
//			resultDataM.setPeriodNo(commissionData.getPeriodNo());
//			resultDataM.setTotalApplication(teamData.getTotalApp());
//		}
		return resultDataM;
	}

}
