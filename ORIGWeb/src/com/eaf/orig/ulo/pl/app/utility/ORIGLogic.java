package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.OrigApplicationUtil;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.OrigBusinessClassUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ORIGLogic {
	static Logger logger = Logger.getLogger(ORIGLogic.class);
	public boolean LogicRequestCBDecision(WorkflowResponse wfResp ,String decision){
		if(null != wfResp
			&& OrigConstant.Action.REQUEST_CB.equals(wfResp.getAction())
				|| OrigConstant.Action.REQUEST_CB_BLOCK.equals(wfResp.getAction())
					|| OrigConstant.Action.REQUEST_CB.equals(decision)													
						||OrigConstant.Action.SEND_TO_CB.equals(wfResp.getAction())
							|| OrigConstant.Action.SEND_TO_CB_BLOCK.equals(wfResp.getAction())
								|| OrigConstant.Action.SEND_TO_CB.equals(decision)
							){
			return true;
		}		
		return false;
	}
	public boolean LogicRequestCBDecision(String decision){
		if(OrigConstant.Action.REQUEST_CB.equals(decision) 
				|| OrigConstant.Action.REQUEST_CB_BLOCK.equals(decision)	
					|| OrigConstant.Action.SEND_TO_CB_BLOCK.equals(decision) 
						|| OrigConstant.Action.SEND_TO_CB.equals(decision)){
			return true;
		}
		return false;
	}
	public boolean LogicSendToCB(String role,String decision,String ncbCode){
		logger.debug("LogicSendToCB role >> "+role);
		logger.debug("LogicSendToCB decision >> "+decision);
		logger.debug("LogicSendToCB ncb code >> "+ncbCode);
		if((OrigConstant.ROLE_DE_SUP.equals(role) && OrigConstant.wfProcessState.SENDX.equals(decision))
				&& OrigUtil.isEmptyString(ncbCode)
					){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryDocList(WorkflowResponse wfResp,String role, String decision){
		if (OrigConstant.Action.REQUEST_FU.equals(decision) 
				||((OrigConstant.ROLE_VC.equals(role)|| OrigConstant.ROLE_VC_SUP.equals(role))	
							&&(OrigConstant.Action.SEND_TO_CA.equals(decision) 
									|| OrigConstant.Action.SEND_TO_CA.equals(wfResp.getAction()))
						)
				){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryButtonExecute1(String decision ,String role){
		if (OrigConstant.wfProcessState.SEND.equals(decision) || OrigConstant.wfProcessState.SENDX.equals(decision) 
				|| (OrigConstant.ROLE_VC.equals(role)&& OrigConstant.Action.SEND_TO_CA.equals(decision))
					|| (OrigConstant.ROLE_DC.equals(role) && OrigConstant.Action.REQUEST_CB.equals(decision))	
			){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryButtonExecute2(String decision ,String role){
		if (OrigConstant.wfProcessState.SEND.equals(decision) || OrigConstant.wfProcessState.SENDX.equals(decision) 
				|| (OrigConstant.ROLE_VC.equals(role)&& OrigConstant.Action.SEND_TO_CA.equals(decision))
			){
			return true;
		}
		return false;
	}
	public boolean LogicWaivedRefferenceNo(HttpServletRequest request ,String jobState , String role ,String executeResult){
		String decisionRefNo = request.getParameter("decision_ref_no");
		String ca_decisionRefNo = request.getParameter("ca_decision_refference");
		if((!OrigUtil.isEmptyString(decisionRefNo) || !OrigUtil.isEmptyString(ca_decisionRefNo)) && !OrigUtil.isEmptyString(executeResult)){
//			#septem comment waived refference no all station
//			if(OrigConstant.ROLE_DE_SUP.equals(role) || OrigConstant.ROLE_DC_SUP.equals(role)
//					|| OrigConstant.roleJobState.CA_I_SUP.equals(jobState)){
//				return true;
//			}
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryDssKbank(String listedCompanyCode ,String bankruptcyCode ,String acmtamcCode ,String decision){
		if (PLXrulesConstant.ResultCode.CODE_NOT_FOUND.equals(listedCompanyCode)
				&& PLXrulesConstant.ResultCode.CODE_FAIL.equals(bankruptcyCode)
					&& PLXrulesConstant.ResultCode.CODE_FAIL.equals(acmtamcCode)
						&& OrigConstant.wfProcessState.SEND.equals(decision)){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryIncomeDebt(String decision){
		if(OrigConstant.wfProcessState.SEND.equals(decision)){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryVerHR(String role ,String decision){
		if((OrigConstant.ROLE_VC.equals(role) && OrigConstant.Action.SEND_TO_CA.equals(decision))
					|| (OrigConstant.ROLE_VC_SUP.equals(role) && OrigConstant.wfProcessState.SENDX.equals(decision))	
				){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryVerHRCG(String role ,String decision,String busClassID){
		if(OrigConstant.BusClass.FCP_KEC_CG.equals(busClassID)){
			if((OrigConstant.ROLE_DC.equals(role) || OrigConstant.ROLE_DC_SUP.equals(role))
					&& OrigConstant.wfProcessState.SEND.equals(decision)){
				return true;
			}
		}
		return false;
	}
	public boolean LogicMandatoryVerCustomer(String role ,String decision){
		if((OrigConstant.ROLE_VC.equals(role)&& OrigConstant.Action.SEND_TO_CA.equals(decision))
					|| (OrigConstant.ROLE_VC_SUP.equals(role) && OrigConstant.wfProcessState.SENDX.equals(decision))
				){
			return true;
		}
		return false;
	}
	public boolean LogicMandatoryVerCustomerCG(String role ,String decision,String busClassID){
		if(OrigConstant.BusClass.FCP_KEC_CG.equals(busClassID)){
			if((OrigConstant.ROLE_DC.equals(role) || OrigConstant.ROLE_DC_SUP.equals(role))
					&& OrigConstant.wfProcessState.SEND.equals(decision)){
				return true;
			}
		}
		return false;
	}
	public boolean LogicMandatoryBehavior(String decision ,String saleType){
		if (OrigConstant.wfProcessState.SEND.equals(decision) 
				&& OrigConstant.SaleType.X_SELL.equals(saleType)) {
			return true;
		}
		return false;
	}	
	
	public String LogicDCDecision(PLApplicationDataM applicationM ,UserDetailM userM ,String decision ,String docRefID) throws Exception{
		try{
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}	
			String recommendResult = xrulesVerM.getRecommendResult();
			String saleType = applicationM.getSaleType();		
			
			String verCusCode = xrulesVerM.getVerCusResultCode();
			String verHRCode = xrulesVerM.getVerHRResultCode();
			
			logger.debug("RecommendResult >> " + recommendResult);
			logger.debug("SaleType >> " + saleType);
			logger.debug("Verify Customer Code >> " + verCusCode);
			logger.debug("Verify HR Code >> " + verHRCode);
		
		
			if (PLXrulesConstant.RecommendResultCode.REJECT.equals(recommendResult)){				
				logger.debug("Case RecommendResult Type Reject !!");				
				
				if (OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD.equals(saleType)
						|| OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD_GENERIC.equals(saleType)) {
					logger.debug("Sale Type Bundling CC or Bundling CG !! .. Application Decision set Reject");	
					decision = OrigConstant.Action.REJECT;// reject to ca by wf
				}else{
					logger.debug("Application Decision set Reject");	
					decision = OrigConstant.Action.REJECT;// reject to dc sup
				}
				
				/*
				 * Modify by Wichaya Case DCSup decision Continue but ILOG answer Reject
				 * Modify by Praisan Case DCSup decision Continue but ILOG answer Reject add check role
				 */
				logger.debug("Reopen FLAG >> " + applicationM.getReopenFlag());
				logger.debug("userM.getCurrentRole() >> " + userM.getCurrentRole());	
				if(OrigConstant.ROLE_DC_SUP.equals(userM.getCurrentRole()) && OrigConstant.FLAG_Y.equalsIgnoreCase(applicationM.getReopenFlag())){
					logger.debug("Reopen FLAG Application Decision Comfirm Reject");	
					decision = OrigConstant.Action.CONFIRM_REJECT;					
				}
				
				// remove other reason except reject
				if(!OrigUtil.isEmptyVector(applicationM.getReasonVect())){
					for(int i=applicationM.getReasonVect().size()-1; i>=0; i--){
						PLReasonDataM reasonM = (PLReasonDataM) applicationM.getReasonVect().get(i);
						if(!OrigConstant.fieldId.REJECT_REASON.equals(reasonM.getReasonType())){
							applicationM.getReasonVect().remove(i);
						}					
					}				
				}
				
			}else if(PLXrulesConstant.RecommendResultCode.ACCEPT.equals(recommendResult) 
						|| PLXrulesConstant.RecommendResultCode.OVERIDE.equals(recommendResult)){
				
				logger.debug("Case RecommendResult Type Accept or Overide !!");					
				if (PLXrulesConstant.ResultCode.CODE_FAIL.equals(verCusCode) || PLXrulesConstant.ResultCode.CODE_FAIL.equals(verHRCode)) {
					logger.debug("Verify Customer or Verify HR Fail !!");
					
					if (OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD.equals(saleType)
							|| OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD_GENERIC.equals(saleType)) {
						logger.debug("Sale Type Bundling CC or Bundling CG !! .. Application Decision set Reject");	
						decision = OrigConstant.Action.REJECT;// reject to ca	
					}
					
				}else if((PLXrulesConstant.ResultCode.CODE_PASS.equals(verCusCode) 
								|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(verCusCode)) 
									&& (PLXrulesConstant.ResultCode.CODE_PASS.equals(verHRCode) 
											|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(verHRCode))
												){
					logger.debug("Case Verify Customer or Verify HR Pass,Waived");
				}else{
					logger.debug("Empty Verify Customer or Empty Verify HR !!");
					
					if (OrigConstant.ShortSaleType.NORMAL.equals(saleType) || OrigConstant.ShortSaleType.X_SELL.equals(saleType)) {
						logger.debug("Sale Type  NM or XS !! .. Application Decision set Send to VC ");	
						decision = OrigConstant.Action.SEND_TO_VC;
					}else{
						logger.debug("Send To Next Station !!");	
						decision = OrigConstant.wfProcessState.SEND;
					}
				}
				
				logger.debug("@@@@@ decision when calculate DLA:" + decision);
				//Calculate DLA Level
				String levelID = null;
				if (OrigConstant.typeColor.typeGreen.equals(applicationM.getJobType())
						|| OrigConstant.typeColor.typeRed.equals(applicationM.getJobType())) {
					ORIGDAOUtilLocal origDaoBean = PLORIGEJBService.getORIGDAOUtilLocal();
					levelID = origDaoBean.getDLA(applicationM.getRecommentCreditLine(),applicationM.getJobType());
				} else if (OrigConstant.typeColor.typeYellow.equals(applicationM.getJobType())) {// Yellow
					OrigApplicationUtil origAppUtil = new OrigApplicationUtil();
					levelID = origAppUtil.getJobTypeByPolicy(applicationM);
				}

				logger.debug("Job Type >> " + applicationM.getJobType());
				logger.debug("Level ID >> " + levelID);
				
				applicationM.setGroupAllocateID(applicationM.getJobType());
				applicationM.setLevelID(levelID);
			}
			
			if(OrigConstant.Action.REJECT.equals(decision)){
				xrulesVerM.setRecommendResult(OrigConstant.recommendResult.REJECT);
				applicationM.setFinalCreditLimit(new BigDecimal(0));
			}
			
			return decision;
		}catch (Exception e) {
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}		
	}
	
	public String LogicVCDecision(PLApplicationDataM applicationM ,UserDetailM userM ,String decision, String docRefID) throws Exception{
		try{
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}	
			String recommendResult = xrulesVerM.getRecommendResult();
			String saleType = applicationM.getSaleType();		
			
			String verCusCode = xrulesVerM.getVerCusResultCode();
			String verHRCode = xrulesVerM.getVerHRResultCode();
			
			logger.debug("RecommendResult >> " + recommendResult);
			logger.debug("SaleType >> " + saleType);
			logger.debug("Verify Customer Code >> " + verCusCode);
			logger.debug("Verify HR Code >> " + verHRCode);
		
		
			if (PLXrulesConstant.RecommendResultCode.REJECT.equals(recommendResult)){				
				logger.debug("Case RecommendResult Type Reject !!");				
				
				if (OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD.equals(saleType)
						|| OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD_GENERIC.equals(saleType)) {
					logger.debug("Sale Type Bundling CC or Bundling CG !! .. Application Decision set Reject");	
					decision = OrigConstant.Action.REJECT;// reject to ca by wf
				}else{
					logger.debug("Application Decision set Reject");	
					decision = OrigConstant.Action.REJECT;// reject to dc sup
				}
				
			}else if(PLXrulesConstant.RecommendResultCode.ACCEPT.equals(recommendResult) 
						|| PLXrulesConstant.RecommendResultCode.OVERIDE.equals(recommendResult)) {
				
				logger.debug("Case RecommendResult Type Accept or Overide !!");				
				if (PLXrulesConstant.ResultCode.CODE_FAIL.equals(verCusCode) || PLXrulesConstant.ResultCode.CODE_FAIL.equals(verHRCode)) {
					logger.debug("Verify Customer or Verify HR Fail !!");
					if (OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD.equals(saleType)
							|| OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD_GENERIC.equals(saleType)) {
						logger.debug("Sale Type Bundling CC or Bundling CG !! .. Application Decision set Reject");	
						decision = OrigConstant.Action.REJECT;// reject to ca	
					}else{
						if(!OrigUtil.isEmptyString(docRefID)){
							decision = OrigConstant.Action.SEND_TO_CA;
						}else{
							decision = OrigConstant.Action.REJECT;
						}
					}
				}else if((PLXrulesConstant.ResultCode.CODE_PASS.equals(verCusCode) 
								|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(verCusCode)) 
									&& (PLXrulesConstant.ResultCode.CODE_PASS.equals(verHRCode) 
											|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(verHRCode))
												){
					logger.debug("Case Verify Customer or Verify HR Pass,Waived");	
					decision = OrigConstant.Action.SEND_TO_CA;
				}else{
					logger.debug("Empty Verify Customer or Empty Verify HR !!");
					if (OrigConstant.ShortSaleType.NORMAL.equals(saleType) || OrigConstant.ShortSaleType.X_SELL.equals(saleType)) {
						logger.debug("Sale Type  NM or XS !! .. Application Decision set Send to VC ");	
						if(!OrigUtil.isEmptyString(docRefID)){
							decision = OrigConstant.Action.SEND_TO_CA;
						}else{
							decision = OrigConstant.Action.REJECT;
						}
					}else{
						logger.debug("Send To Next Station !!");	
						decision = OrigConstant.Action.SEND_TO_CA;
					}
				}
				
				logger.debug("@@@@@ decision when calculate DLA:" + decision);
				//Calculate DLA Level
				String levelID = null;
				if (OrigConstant.typeColor.typeGreen.equals(applicationM.getJobType())
						|| OrigConstant.typeColor.typeRed.equals(applicationM.getJobType())) {
					ORIGDAOUtilLocal origDaoBean = PLORIGEJBService.getORIGDAOUtilLocal();
					levelID = origDaoBean.getDLA(applicationM.getRecommentCreditLine(),applicationM.getJobType());
				} else if (OrigConstant.typeColor.typeYellow.equals(applicationM.getJobType())) {// Yellow
					OrigApplicationUtil origAppUtil = new OrigApplicationUtil();
					levelID = origAppUtil.getJobTypeByPolicy(applicationM);
				}

				logger.debug("Job Type >> " + applicationM.getJobType());
				logger.debug("Level ID >> " + levelID);
				
				applicationM.setGroupAllocateID(applicationM.getJobType());
				applicationM.setLevelID(levelID);
			}
			
			if(OrigConstant.Action.REJECT.equals(decision)){
				xrulesVerM.setRecommendResult(OrigConstant.recommendResult.REJECT);
				applicationM.setFinalCreditLimit(new BigDecimal(0));
			}
			
			return decision;
		}catch (Exception e) {
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}		
	}
	public String LogicDCIDecision(PLApplicationDataM applicationM ,UserDetailM userM ,String decision ,String docRefID) throws Exception{
		try{
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}	
			String recommendResult = xrulesVerM.getRecommendResult();
			String saleType = applicationM.getSaleType();		
			
			String verCusCode = xrulesVerM.getVerCusResultCode();
			String verHRCode = xrulesVerM.getVerHRResultCode();
			
			logger.debug("RecommendResult >> " + recommendResult);
			logger.debug("SaleType >> " + saleType);
			logger.debug("Verify Customer Code >> " + verCusCode);
			logger.debug("Verify HR Code >> " + verHRCode);
		
		
			if (PLXrulesConstant.RecommendResultCode.REJECT.equals(recommendResult)){				
				logger.debug("Case RecommendResult Type Reject !!");	
				
				decision = OrigConstant.Action.REJECT;
			
			}else if(PLXrulesConstant.RecommendResultCode.ACCEPT.equals(recommendResult) 
						|| PLXrulesConstant.RecommendResultCode.OVERIDE.equals(recommendResult)){				
				logger.debug("Case RecommendResult Type Accept or Overide !!");					
				if (PLXrulesConstant.ResultCode.CODE_FAIL.equals(verCusCode) || PLXrulesConstant.ResultCode.CODE_FAIL.equals(verHRCode)) {
					logger.debug("Verify Customer or Verify HR Fail !!");
					if(!OrigUtil.isEmptyString(docRefID)){
						decision = OrigConstant.wfProcessState.SEND;
					}else{
						decision = OrigConstant.Action.REJECT;
					}
				}else if((PLXrulesConstant.ResultCode.CODE_PASS.equals(verCusCode) 
								|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(verCusCode)) 
									&& (PLXrulesConstant.ResultCode.CODE_PASS.equals(verHRCode) 
											|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(verHRCode))
												){			
					decision = OrigConstant.wfProcessState.SEND;
					logger.debug("Case Verify Customer or Verify HR Pass,Waived");
				}else{
					logger.debug("Empty Verify Customer or Empty Verify HR !!");
					logger.debug("Send To Next Station !!");	
					decision = OrigConstant.wfProcessState.SEND;
				}
				
				logger.debug("@@@@@ decision when calculate DLA:" + decision);
				//Calculate DLA Level
				String levelID = null;
				if (OrigConstant.typeColor.typeGreen.equals(applicationM.getJobType())
						|| OrigConstant.typeColor.typeRed.equals(applicationM.getJobType())) {
					ORIGDAOUtilLocal origDaoBean = PLORIGEJBService.getORIGDAOUtilLocal();
					levelID = origDaoBean.getDLA(applicationM.getRecommentCreditLine(),applicationM.getJobType());
				} else if (OrigConstant.typeColor.typeYellow.equals(applicationM.getJobType())) {// Yellow
					OrigApplicationUtil origAppUtil = new OrigApplicationUtil();
					levelID = origAppUtil.getJobTypeByPolicy(applicationM);
				}

				logger.debug("Job Type >> " + applicationM.getJobType());
				logger.debug("Level ID >> " + levelID);
				
				applicationM.setGroupAllocateID(applicationM.getJobType());
				applicationM.setLevelID(levelID);
			}
			
			if(OrigConstant.Action.REJECT.equals(decision)){
				xrulesVerM.setRecommendResult(OrigConstant.recommendResult.REJECT);
				applicationM.setFinalCreditLimit(new BigDecimal(0));
			}
			
			return decision;
		}catch (Exception e) {
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}		
	}
	
	public String LogicDE_SUPDecision(PLApplicationDataM applicationM, UserDetailM userM, String decision,String docRefID) throws Exception {
		try {
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
			
			if(!OrigUtil.isEmptyString(docRefID) && PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getExistCustCode())){
				return OrigConstant.Action.CONFIRM_REJECT;
			}
			
			return decision;
		}catch (Exception e) {
			logger.fatal("Exception ",e);
			throw new Exception(e.getMessage());
		}		
	}
	
	public void SetRoleDecision(String role ,PLApplicationDataM applicationM ,UserDetailM userM){
		Timestamp sysdate = new Timestamp(new Date().getTime());
		if (OrigConstant.ROLE_CA.equals(role)) {
			
			if(OrigUtil.isEmptyString(applicationM.getCaDecision())){
				applicationM.setCaDecision(applicationM.getAppDecision());
			}
			if(OrigUtil.isEmptyString(applicationM.getCaFirstId())){
				applicationM.setCaFirstId(userM.getUserName());
				applicationM.setCaStartDate(sysdate);
			}
			applicationM.setCaLastId(userM.getUserName());
			applicationM.setCaLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_CB.equals(role)) {
			
			applicationM.setCbDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getCbFirstId())){
				applicationM.setCbFirstId(userM.getUserName());
				applicationM.setCbStartDate(sysdate);
			}
			applicationM.setCbLastId(userM.getUserName());
			applicationM.setCbLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_DC.equals(role)) {
			
			applicationM.setDcDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getDcFirstId())){
				applicationM.setDcFirstId(userM.getUserName());
				applicationM.setDcStartDate(sysdate);
			}
			applicationM.setDcLastId(userM.getUserName());
			applicationM.setDcLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_DC_SUP.equals(role)) {
			
			applicationM.setDcSupDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getDcSupFirstId())){
				applicationM.setDcSupFirstId(userM.getUserName());
				applicationM.setDcSupFirstDate(sysdate);
			}
			applicationM.setDcSupLastId(userM.getUserName());
			applicationM.setDcSupLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_DE.equals(role)) {
			
			applicationM.setDeDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getDeFirstId())){
				applicationM.setDeFirstId(userM.getUserName());
				applicationM.setDeStartDate(sysdate);
			}
			applicationM.setDeLastId(userM.getUserName());
			applicationM.setDeLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_DE_SUP.equals(role)) {
			
			applicationM.setDeSupDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getDeSupFirstId())){
				applicationM.setDeSupFirstId(userM.getUserName());
				applicationM.setDeSupFirstDate(sysdate);
			}
			applicationM.setDeSupLastId(userM.getUserName());
			applicationM.setDeSupLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_DF.equals(role) || OrigConstant.ROLE_DF_REJECT.equals(role)) {
			
			applicationM.setDF_Decision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getDfFirstId())){
				applicationM.setDfFirstId(userM.getUserName());
				applicationM.setDfStartDate(sysdate);
			}
			applicationM.setDfLastId(userM.getUserName());
			applicationM.setDfLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_FU.equals(role)) {
			
			applicationM.setFuDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getFuFirstId())){
				applicationM.setFuFirstId(userM.getUserName());
				applicationM.setFuStartDate(sysdate);
			}
			applicationM.setFuLastId(userM.getUserName());
			applicationM.setFuLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_PO.equals(role)) {
			
			applicationM.setPoDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getPoFirstId())){
				applicationM.setPoFirstId(userM.getUserName());
				applicationM.setPoStartDate(sysdate);
			}
			applicationM.setPoLastId(userM.getUserName());
			applicationM.setPoLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_VC.equals(role)) {
			
			applicationM.setVcDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getVcFirstId())){
				applicationM.setVcFirstId(userM.getUserName());
				applicationM.setVcStartDate(sysdate);
			}
			applicationM.setVcLastId(userM.getUserName());
			applicationM.setVcLastDate(sysdate);
			
		} else if (OrigConstant.ROLE_VC_SUP.equals(role)){			
			applicationM.setVcSupDecision(applicationM.getAppDecision());
			if(OrigUtil.isEmptyString(applicationM.getVcSupFirstId())){
				applicationM.setVcSupFirstId(userM.getUserName());
				applicationM.setVcSupStartDate(sysdate);
			}
			applicationM.setVcSupLastId(userM.getUserName());
			applicationM.setVcSupLastDate(sysdate);
			
		}
	}
	
	public void LogicClearDataNCB(PLApplicationDataM applicationM , String decision){		
		if(LogicRequestCBDecision(decision)){			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
			
			PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
			
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				personalM.setXrulesVerification(xrulesVerM);
			}
		
			xrulesVerM.setNcbOutPutM(null);
			xrulesVerM.setNCBResult(null);
			xrulesVerM.setNCBCode(null);
//			#septemwi comment chnage to NcbFicoCode
//			xrulesVerM.setFicoCode(null);
//			xrulesVerM.setFicoResult(null);	
			xrulesVerM.setNcbFicoCode(null);
			xrulesVerM.setNcbFicoResult(null);
			xrulesVerM.setNcbFailHistoryCode(null);
			xrulesVerM.setNcbFailHistoryReason(null);
			xrulesVerM.setNcbOverdueHistoryCode(null);
			xrulesVerM.setNcbOverdueHistoryReason(null);
//			xrulesVerM.setXRulesDebtBdDataM(null);
			
			PLXRulesDebtBdDataM xrulesDebtM = xrulesVerM.getXRulesDebtBdDataM();
			if(null == xrulesDebtM){
				xrulesDebtM = new PLXRulesDebtBdDataM();
				xrulesVerM.setXRulesDebtBdDataM(xrulesDebtM);
			}	  	
						
			xrulesDebtM.setUseFlag(null);	
			xrulesDebtM.setBurden(null);
			xrulesDebtM.setBurdenAdjust(null);
			xrulesDebtM.setIncome(null);
			xrulesDebtM.setDebtBurdentScore(null);
			xrulesDebtM.setDebtBurdentScoreAdjust(null);
			xrulesDebtM.setNcbDebt(null);
			xrulesDebtM.setConsumerLoanDebtTotal(null);
			xrulesDebtM.setCommercialLoanDebtTotal(null);
			
			BigDecimal totalIncome = BigDecimal.ZERO;
			
			if(null != xrulesDebtM.getSalary()){
				totalIncome = totalIncome.add(xrulesDebtM.getSalary());
			}
			if(null != xrulesDebtM.getLoanIncome()){
				totalIncome = totalIncome.add(xrulesDebtM.getLoanIncome());
			}
			if(null != xrulesDebtM.getOtherIncome()){
				totalIncome = totalIncome.add(xrulesDebtM.getOtherIncome());
			}
			xrulesDebtM.setTotalIncome(totalIncome);
			
			xrulesDebtM.setTotalDebt(xrulesDebtM.getOtherDebt());
			
			xrulesVerM.setVXRulesNCBDataM(null);
			xrulesVerM.setXrulesFICODataM(null);
			xrulesVerM.setvXrulesNCBRestructureDebtVect(null);
			xrulesVerM.setvXrulesNCBVerifyVect(null);
			xrulesVerM.setNCBTrackingCode(null);
			xrulesVerM.setNcbUpdateBy(null);
			xrulesVerM.setNCBConsentRefNoDate(null);
			xrulesVerM.setNcbUpdateDate(null);
			xrulesVerM.setNcbRQapprover(null);				
		}	
	}
	public static String CreateCaportMessage(HttpServletRequest request , PLApplicationDataM applicationM ,String message){
		CapportGroupDataM capportM = (CapportGroupDataM) request.getSession().getAttribute("PL_CAPPORT");
		if(null != capportM){
			OrigBusinessClassUtil origUtil = new OrigBusinessClassUtil();
			if(capportM.isAlertFlag() && !origUtil.isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
				StringBuilder str = new StringBuilder("");
					str.append("Capport ").append(capportM.getCapportGroupName());
					str.append(" ").append(message).append(" ");
					DecimalFormat decimal_format = new DecimalFormat ("#,000.00#");
					str.append(decimal_format.format(capportM.getCapportAmount()-capportM.getCapportUsed()));
				return HTMLRenderUtil.displayHiddenField(str.toString(), "capport_message");
			}
		}
		return "";
	}
	
	public static String LogicColorStyleResult(String resultCode ,String resultDesc){	
		if(PLXrulesConstant.ResultCode.CODE_PASS.equals(resultCode)
			|| PLXrulesConstant.ResultCode.CODE_PASS_OR.equals(resultCode)
				|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(resultCode)){
			return "verify-textbox-green";
		}
		if(PLXrulesConstant.ResultCode.CODE_FAIL.equals(resultCode)){
			return "verify-textbox-red";
		}
		if(PLXrulesConstant.ResultCode.CODE_OVERRIDE.equals(resultCode)
				|| PLXrulesConstant.ResultCode.CODE_GRACE_ORVERRIDE.equals(resultCode)){
			return "verify-textbox-yellow";
		}
		if(PLXrulesConstant.ResultCode.CODE_WARNING.equals(resultCode)
			|| PLXrulesConstant.ResultCode.CODE_SETUP_DECISION.equals(resultCode)
				|| PLXrulesConstant.ResultCode.CODE_REQUIRED.equals(resultCode)
					|| PLXrulesConstant.ResultCode.CODE_NA.equals(resultCode)
						|| "N/A".equals(resultDesc)){
			return "verify-textbox-orange";
		}
		if(OrigUtil.isEmptyString(resultCode) && !OrigUtil.isEmptyString(resultDesc)){
			return "verify-textbox-error";
		}
		return "verify-textbox-default";
	}
	
	public static String LogicColorStyleResultDiv(String resultCode ,String resultDesc){	
		if(PLXrulesConstant.ResultCode.CODE_PASS.equals(resultCode)
			|| PLXrulesConstant.ResultCode.CODE_PASS_OR.equals(resultCode)
				|| PLXrulesConstant.ResultCode.CODE_WAIVED.equals(resultCode)){
			return "verify-div-green";
		}
		if(PLXrulesConstant.ResultCode.CODE_FAIL.equals(resultCode)){
			return "verify-div-red";
		}
		if(PLXrulesConstant.ResultCode.CODE_OVERRIDE.equals(resultCode)
				|| PLXrulesConstant.ResultCode.CODE_GRACE_ORVERRIDE.equals(resultCode)){
			return "verify-div-yellow";
		}
		if(PLXrulesConstant.ResultCode.CODE_WARNING.equals(resultCode)
				|| PLXrulesConstant.ResultCode.CODE_SETUP_DECISION.equals(resultCode)
					|| PLXrulesConstant.ResultCode.CODE_REQUIRED.equals(resultCode)
						|| PLXrulesConstant.ResultCode.CODE_NA.equals(resultCode)
							|| "N/A".equals(resultDesc)){
			return "verify-div-orange";
		}
		if(OrigUtil.isEmptyString(resultCode) && !OrigUtil.isEmptyString(resultDesc)){
			return "verify-div-error";
		}
		return "verify-div-while";
	}
	
	public boolean IgnoreJobState(String param , String jobState){
		if(!OrigUtil.isEmptyString(param)){
			String []obj = param.split("\\,");
			for(String data : obj){
				if(!OrigUtil.isEmptyString(data) && data.equals(jobState)){
					return true;
				}
			}
		}
		return false;
	}
	public static String DisplayMandatoryYearOfService(String busClass ,String currentRole){
		if(MandatoryYearOfService(busClass, currentRole)){
			return "<font color=\"#ff0000\">*</font>";
		}
		return "";
	}
	public static boolean MandatoryYearOfService(String busClass ,String currentRole){
		if(!OrigUtil.isEmptyString(currentRole)
				&& (currentRole.indexOf(OrigConstant.ROLE_DF) > -1 
						|| currentRole.indexOf(OrigConstant.ROLE_FU) > -1 )
				){
			return false;
		}
		if((OrigConstant.BusClass.FCP_KEC_NM.equals(busClass)|| OrigConstant.BusClass.FCP_KEC_XS.equals(busClass)
					|| OrigConstant.BusClass.FCP_KEC_IC.equals(busClass))
//#SepteMwi Comment Not Fix Role MandatoryYearOfService
//				&& (OrigConstant.ROLE_DC.equals(currentRole) || OrigConstant.ROLE_DC_SUP.equals(currentRole) 
//						|| OrigConstant.ROLE_VC.equals(currentRole) || OrigConstant.ROLE_VC_SUP.equals(currentRole))
				){
				return true;
			}
		return false;
	}
	public static boolean MandatorySendBackRequestNcb(String decision,String currentRole,String jobState){
		if(OrigConstant.ROLE_DC_SUP.equals(currentRole) || OrigConstant.ROLE_CA_SUP.equals(currentRole)){
			 if(OrigConstant.wfProcessState.SEND_BACKX.equals(decision) 
					 	|| OrigConstant.Action.SEND_BACK_TO_DC.equals(decision)){
				 return true;
			 }
		}
		if(WorkflowConstant.JobState.CASUP_ICDC_IQ.equals(jobState) && OrigConstant.Action.SEND_BACK_TO_DC.equals(decision)){
			return true;
		}
		return false;
	}
	public static boolean LogicResultNCBData(String ncbCode){
		if(!OrigUtil.isEmptyString(ncbCode)&&!NCBConstant.ncbResultCode.SEND_BACK.equals(ncbCode)){
			return true;
		}
		return false;
	}
		
	public static boolean RoleICDC(String role){
		if(OrigConstant.ROLE_I_SUP_CA.equals(role) 
			||OrigConstant.ROLE_I_SUP_CA1.equals(role) 
				||OrigConstant.ROLE_I_SUP_CA2.equals(role) 
					|| OrigConstant.ROLE_I_SUP_DC.equals(role) 
						||OrigConstant.ROLE_I_SUP_FU.equals(role)
							|| OrigConstant.ROLE_I_CA.equals(role) 
								|| OrigConstant.ROLE_I_DC.equals(role) 
									||OrigConstant.ROLE_I_FU.equals(role)){
			return true;
		}
		return false;
	}
	
	public static String spMandatory(PLApplicationDataM applicationM , String currentRole){
	  String spMandatory = "";
 	  OrigBusinessClassUtil busclass = new OrigBusinessClassUtil();
 	  if(!busclass.isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
 	  		if(OrigConstant.ROLE_DC.equals(currentRole) || OrigConstant.ROLE_DC_SUP.equals(currentRole) ||
					OrigConstant.ROLE_VC.equals(currentRole) || OrigConstant.ROLE_VC_SUP.equals(currentRole)){
				spMandatory = "*";
			}
 	  }
 	  return spMandatory;
	}
	public static String DisplayModeOccupationText(String mode,String profession){
		String displayMode = mode;
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			if("18".equals(profession)){
				displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
			}else{
				displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
			}
		}
		return displayMode;
	}
	public static String DisplayModeBusinessText(String mode,String businessType){
		String displayMode = mode;
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			if("25".equals(businessType)){
				displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
			}else{
				displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
			}
		}
		return displayMode;
	}
	public static String DisplayModeSourceIncomeText(String mode,String sourceOfIncome){
		String displayMode = mode;
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			if("04".equals(sourceOfIncome)){
				displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
			}else{
				displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
			}
		}
		return displayMode;
	}
	
	public static String DisplayModeRelationFlag(String mode,String have_related){
		String displayMode = mode;
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){
			if("Y".equals(have_related)){
				displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
			}else{
				displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
			}
		}
		return displayMode;
	}
	public static String MandatoryRelationFlag(String currentRole , String have_related){
		String mandatory = "";
		if("Y".equalsIgnoreCase(have_related) && !OrigConstant.ROLE_DF_REJECT.equals(currentRole)){
			mandatory = "*";
		}
		return mandatory;
	}
	public static String displayServerFLAG(PLApplicationDataM applicationM){
		String serverFLAG = applicationM.getServerFLAG();
			applicationM.setServerFLAG(null);
		return serverFLAG;
	}
	public static boolean superTrackingSearch(String current_role){
		if(OrigConstant.ROLE_SP.equals(current_role)){
			return true;
		}
		return false;
	}
	public static boolean superReassignSearch(String current_role){
		if(OrigConstant.ROLE_SP.equals(current_role)){
			return true;
		}
		return false;
	}
	public static String getRoleWf(String role){
		if(OrigConstant.ROLE_I_CA.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_I_DC.equals(role))return OrigConstant.ROLE_DC;
		else if(OrigConstant.ROLE_I_FU.equals(role))return OrigConstant.ROLE_FU;
		else if(OrigConstant.ROLE_I_SUP_CA.equals(role))return OrigConstant.ROLE_CA_SUP;
		else if(OrigConstant.ROLE_I_SUP_CA1.equals(role))return OrigConstant.ROLE_CA;
		else if(OrigConstant.ROLE_I_SUP_CA2.equals(role))return OrigConstant.ROLE_CA;
		return role;
	}
	public static String getRoleFormWf(String role,String ptid){
		if(WorkflowConstant.ProcessTemplate.KLOP002.equals(ptid)){
			if(OrigConstant.ROLE_CA.equals(role))return OrigConstant.ROLE_I_CA;
			else if(OrigConstant.ROLE_DC.equals(role))return OrigConstant.ROLE_I_DC;
			else if(OrigConstant.ROLE_FU.equals(role))return OrigConstant.ROLE_I_FU;
			else if(OrigConstant.ROLE_CA_SUP.equals(role))return OrigConstant.ROLE_I_SUP_CA;
		}
		return role;
	}
	public static String getReassignRole(HttpServletRequest request,UserDetailM userM){
		String role = "FIELD";
		String current_role = userM.getCurrentRole();
		if(ORIGLogic.superReassignSearch(current_role)){
			String[] checks = request.getParameterValues("check_apprecid");
			if(null != checks){
				role = request.getParameter("role-"+checks[0]);
			}
		}else{
			role = getUnderRole(current_role);
		}
		return role;
	}
	public static String getUnderRole(String role){
		role = role.replaceAll("_SUP","");
		role = role.replaceAll("\\ SUP","");
		return role;
	}
	public static String getROLE_VARIABLE(String role){
		if(null == role) return "";
		role = role.replaceAll(" ","_");
		return "ROLE_"+role;
	}
	public static String MandatoryFieldSeller(String busclassID, String role){
		StringBuilder STR = new StringBuilder("");
			if(isMandatoryFieldSeller(busclassID, role)){
				STR.append("<font color=\"#ff0000\">*</font>");
			}
		return STR.toString();
	}
	public static boolean isMandatoryFieldSeller(String busclassID, String role){
		if(OrigConstant.BusClass.FCP_KEC_IC.equals(busclassID)
				|| OrigConstant.BusClass.FCP_KEC_DC.equals(busclassID)
				|| OrigConstant.ROLE_DF_REJECT.equals(role)){
			return false;
		}
		return true;
	}
	public static String ManadatoryFiledSellerBranchCode(String busclassID, String role){
		StringBuilder STR = new StringBuilder("");
		if(isManadatoryFiledSellerBranchCode(busclassID, role)){
			STR.append("<font color=\"#ff0000\">*</font>");
		}
		return STR.toString();
	}
	public static boolean isManadatoryFiledSellerBranchCode(String busclassID, String role){
		if(OrigConstant.BusClass.FCP_KEC_IC.equals(busclassID)
				|| OrigConstant.BusClass.FCP_KEC_DC.equals(busclassID)
				|| OrigConstant.ROLE_DF_REJECT.equals(role)){
			return false;
		}
		return true;
	}
	public static String getRoleMenu(HttpServletRequest request){
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");	
		String searchType = (String) request.getSession().getAttribute("searchType");
		String role = userM.getCurrentRole();
		if(OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
			role = userM.getRoleMenu();
			userM.setCurrentRole(role);
		}
		return role;
	}
	public static boolean isRejectReason(String reason_type){
		if(OrigConstant.fieldId.REJECT_REASON.equals(reason_type)){
			return true;
		}
		return false;
	}
	
	public static void LogicMapReasonReject(PLApplicationDataM applicationM,UserDetailM userM){
		if(OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())
			|| OrigConstant.Action.REJECT_SKIP_DF.equals(applicationM.getAppDecision())){
			OrigApplicationUtil origAppUtil = new OrigApplicationUtil();
			Vector<PLReasonDataM> rejectVect = origAppUtil.generateRejectReasonFromRulesResult(applicationM, userM);
			applicationM.setReasonVect(rejectVect);
		}
	}
}
