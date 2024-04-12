package com.eaf.inf.batch.ulo.capport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.service.notify.model.NotifyTemplateDataM;
import com.eaf.core.ulo.service.notify.model.NotifyTransactionResultDataM;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.orig.ulo.email.model.EmailRequest;


public class AdjustCapPortTransaction extends InfBatchHelper{	
	private static transient Logger logger = Logger.getLogger(AdjustCapPortTransaction.class);
	
	String ADJUST_CAPPORT_TRANSACTION_MODULE_ID = InfBatchProperty.getInfBatchConfig("ADJUST_CAPPORT_TRANSACTION_MODULE_ID");
	
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		logger.debug("[Begin-----------------processAction-----------------------]");		
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
			 ArrayList rsArrayListCapportManualFile = new ArrayList();
			 ArrayList rsArrayListCapportTransaction = new ArrayList();
		try{
			//SQLQueryController query = new SQLQueryController();
			CapportSQLQueryController sqlController = new CapportSQLQueryController();
			rsArrayListCapportManualFile = sqlController.getCapportManualFile();
			
			if(rsArrayListCapportManualFile!=null && rsArrayListCapportManualFile.size()>0){
				logger.debug("rsArrayListCapportManualFile.size() >> " + rsArrayListCapportManualFile.size());
				
//				Begin Loop Manual File per CAP_PORT_NAME
				for(int i=0;i<rsArrayListCapportManualFile.size();i++){
					
					String capportNameTrans = null;
					String grantTypeTrans = null;
					BigDecimal newGrantedAmount = null;
					BigDecimal newGrantedApplication = null;
					
					HashMap rsManualHash = (HashMap) rsArrayListCapportManualFile.get(i);
					String capportName = (String)rsManualHash.get("CAP_PORT_NAME");
					BigDecimal capAmt = (BigDecimal)rsManualHash.get("CAP_AMOUNT");
					BigDecimal grantedAmt = (BigDecimal)rsManualHash.get("GRANTED_AMOUNT");
					BigDecimal capApp = (BigDecimal)rsManualHash.get("CAP_APPLICATION");
					BigDecimal grantedApp = (BigDecimal)rsManualHash.get("GRANTED_APPLICATION");
					BigDecimal warningPoint = (BigDecimal)rsManualHash.get("WARNING_POINT");
					String emails = (String)rsManualHash.get("RECIPIENT_EMAILS");
					
					logger.debug("[Begin Loop("+i+")------Capport Name : "+capportName+"-------]");
				    
				    logger.debug(">> Step: Sum Cap Port Transaction");
					rsArrayListCapportTransaction = sqlController.getSumCapport_ByCapNameAndGrantType(capportName);
				
					
					if(rsArrayListCapportTransaction!=null && rsArrayListCapportTransaction.size()>0){
						logger.debug(">>> rsArrayListCapportTransaction.size() >> " + rsArrayListCapportTransaction.size());
						
//						Begin Loop CapPort by CapName and GrantType
						for(int j=0;j<rsArrayListCapportTransaction.size();j++){
							HashMap rsTranHash = (HashMap) rsArrayListCapportTransaction.get(j);
							capportNameTrans = (String)rsTranHash.get("CAP_PORT_NAME");
							grantTypeTrans = (String)rsTranHash.get("GRANT_TYPE");
							
							if(grantTypeTrans.equals("AMT")){
								newGrantedAmount = (BigDecimal)rsTranHash.get("GRANTED_AMOUNT");
								
							}else if(grantTypeTrans.equals("NUM")){
								newGrantedApplication = (BigDecimal)rsTranHash.get("GRANTED_APPLICATION");
							}
							
						}//end for rsArrayListCapportTransaction
					}//end if rsArrayListCapportTransaction
					
					logger.debug(">> Step: Adjust Capport");
					if(capportNameTrans!=null){
						logger.debug(">>> NewGrantedAmt= "+newGrantedAmount+" , NewGrantedApplication= "+newGrantedApplication );
						
						int rsAdjust = sqlController.adjustCapport(capportName, newGrantedAmount, newGrantedApplication);
						NotifyCapportProcess process = new NotifyCapportProcess();
						process.postNotifyTransactionResult("[Before]"+capportName+
								",AMT="+Formatter.formatCurrency(grantedAmt,false)
								+",APP="+Formatter.formatCurrency(grantedApp,false), rsAdjust);
						
					}else{
						logger.debug(">>> Not Found Transaction of this capport name ");
					}
					
//					[Begin Check warning point]
					logger.debug(">> Step: CheckWarning");
					boolean rsWarnAmt = isWarningCapport(capAmt, grantedAmt, newGrantedAmount, warningPoint);
					boolean rsWarnApp = isWarningCapport(capApp, grantedApp, newGrantedApplication, warningPoint);
					if(rsWarnAmt == true || rsWarnApp == true){
//						[Begin Send Email]
						logger.debug("Start Send Email");
						BigDecimal grantedAmtCal = (grantedAmt!=null?grantedAmt:new BigDecimal(0));
						BigDecimal newGrantedAmtCal = (newGrantedAmount!=null?newGrantedAmount:new BigDecimal(0));
						
						BigDecimal grantedAppCal = (grantedApp!=null?grantedApp:new BigDecimal(0));
						BigDecimal newGrantedAppCal = (newGrantedApplication!=null?newGrantedApplication:new BigDecimal(0));
						
						BigDecimal allGrantedAmt = grantedAmtCal.add(newGrantedAmtCal);
						BigDecimal allGrantedApp = grantedAppCal.add(newGrantedAppCal);
						
						
						NotifyCapportRequest templateObject= new NotifyCapportRequest();
						templateObject.setCapportName(capportName);
						templateObject.setCapAmount(Formatter.formatCurrency(capAmt,false));
						templateObject.setCapApplication(capApp.toString());
						templateObject.setGrantedAmount(Formatter.formatCurrency(allGrantedAmt,false));
						templateObject.setGrantedApplication(allGrantedApp.toString());
						templateObject.setWarningPoint(warningPoint.toString()+"%");
						
						sendEmail(templateObject);
					}//end if isWarn
					
				}//end for rsArrayListCapportManualFile per CAP_PORT_NAME
			}//end if rsArrayListCapportManualFile
			
			//logger.debug("rsArrayList >> "+rsArrayList);
			//query.updateApplicaitonDate(CacheServiceLocator.ORIG_DB, relativeDate);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		logger.debug("[End-----------------processAction-----------------------]");			
		return infBatchResponse;
	}	 
	 
	private boolean isWarningCapport(BigDecimal cap, BigDecimal granted,BigDecimal newGranted, BigDecimal warningPoint){
		boolean rs = false;
		if(warningPoint!=null && warningPoint.intValue()!=0 && cap!=null && cap.intValue()!=0){
			BigDecimal wanringPointPercentage =warningPoint.divide(new BigDecimal(100));
			
			BigDecimal grantedCal = (granted!=null?granted:new BigDecimal(0));
			BigDecimal newGrantedCal = (newGranted!=null?newGranted:new BigDecimal(0));
			BigDecimal warningPointTotal = cap.multiply(wanringPointPercentage) ;
			BigDecimal allGranted = grantedCal.add(newGrantedCal);
			
		
			logger.debug("grantedCal:"+grantedCal+" + newGrantedCal:"+newGrantedCal+" = allGranted:"+allGranted);
			logger.debug("cap="+cap);
			logger.debug("warningPoint="+warningPoint);
			logger.debug("wanringPointPercentage="+wanringPointPercentage);
			logger.debug("warningPointTotal = cap * wanringPointPercentage = "+warningPointTotal  );
			
			
			int rsCompare = allGranted.compareTo(warningPointTotal);
			logger.debug("COMPARE >>> allGranted["+allGranted+"] is "+(rsCompare<0?"<":(rsCompare>0?">":"=") ) + " warningPointTotal["+warningPointTotal+"]" );
			if(rsCompare >= 0){
				//compare : -1 is mean  a < b 
				//compare : 0 is mean  a = b 
				//compare : 1 is mean  a > b 
				rs = true;
			}
		}
		logger.debug("rs="+rs);
		return rs;		
		
	}
	private void sendEmail(NotifyCapportRequest templateObject){
		try {
			NotifyCapportProcess process = new NotifyCapportProcess();
			
			NotifyTransactionResultDataM transactionResult = new NotifyTransactionResultDataM();
			transactionResult.setUniqueId(templateObject.getCapportName());
			transactionResult.setTransactionObject(templateObject);
			
			NotifyTemplateDataM notifyTemplate = new NotifyTemplateDataM();
			notifyTemplate.setTemplateType(TemplateBuilderConstant.TemplateType.EMAIL);
			notifyTemplate.setDbType(1);
			notifyTemplate.setTemplateObject(templateObject);
			
			EmailRequest emailRequest = process.getEmailRequest(transactionResult, notifyTemplate);
			process.sendEmail(emailRequest);

		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
	}
	
}

