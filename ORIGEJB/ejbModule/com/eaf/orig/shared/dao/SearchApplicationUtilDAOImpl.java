package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.SearchApplicationUtilDAOException;
import com.eaf.orig.shared.dao.utility.BusinessClassByUserDAO;
import com.eaf.orig.shared.model.SearchImageM;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SearchApplicationUtilDAOImpl extends OrigObjectDAO implements SearchApplicationUtilDAO{
	 private static Logger logger = Logger.getLogger(SearchApplicationUtilDAOImpl.class);
	/**
	 * Constructor for SearchApplicationUtilDAOImpl.
	 */
	
	public Vector getSearchApplicationEnhance_SG(String username,String searchType,String appNo,String cardNo,String citizenId,String chiName, String fromReceiveDate, String toReceiveDate, String status, String selectedUser) throws SearchApplicationUtilDAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset = null;	
		Vector result =new Vector();	
		searchType=(searchType==null)?"":searchType;			   	
//		try{			   					    
//				conn = getConnection();
//				if(conn!=null){							   		   	
//				String sql="";				   				   				   				   			   	
//				sql = sql.concat(" SELECT Application.application_record_id, ");
//		    	sql = sql.concat(" Application.application_no,");
//			    sql = sql.concat(" Application.account_id,");
//			    sql = sql.concat(" Application.business_class_id, ");
//			    sql = sql.concat(" Application.job_state,");
//			    sql = sql.concat(" Application.product_code,");
//			    sql = sql.concat(" Application.channel_code,");
//			    //sql = sql.concat(" Application.application_status, ");
//			    //sql = sql.concat(" decode(Application.application_status, '"+ NaosConstant.APPLICATION_STATUS_NEW + "','"+ NaosConstant.APPLICATION_STATUS_CA + "','"+ NaosConstant.APPLICATION_STATUS_EXCEPTION + "','"+ NaosConstant.APPLICATION_STATUS_UW + "',application_status,application_status) as application_status, ");
//				//sql = sql.concat(" Application.application_type,");
//				sql = sql.concat(" decode(Application.application_type, 'B', 'Primary', 'S', 'Supplementary') as application_type,");
//				sql = sql.concat(" Application.entry_type,");
//				sql = sql.concat(" Application.final_credit_line,");
//				sql = sql.concat(" Application.first_ca_id,");
//				sql = sql.concat(" Application.final_lending_limit,");
//				
//				sql = sql.concat(" Application.apply_receive_date,");
//				sql = sql.concat(" Application.de_date,");
//				sql = sql.concat(" Application.last_ca_decision_date,");
//				sql = sql.concat(" Application.tw_pending_status_date,");
//				sql = sql.concat(" Application.last_ex_date,");
//				sql = sql.concat(" Application.last_ex_id,");
//				
//				sql = sql.concat(" Personal_info.citizen_id,");
//				sql = sql.concat(" Personal_info.tw_ch_f_name,");
//				sql = sql.concat(" Personal_info.tw_ch_l_name,");
//				sql = sql.concat(" Personal_info.emboss_name,");
//				sql = sql.concat(" Product.product_desc,");
//				sql = sql.concat(" Ge_card.card_no");
//				
//				sql = sql.concat(" from application,personal_Info,product,ge_card");		
//				sql = sql.concat(" where application.APPLICATION_RECORD_ID = personal_Info.APPLICATION_RECORD_ID");						
//				sql = sql.concat(" and application.PRODUCT_CODE=product.PRODUCT_ID ");
//				sql = sql.concat(" and PERSONAL_INFO.PERSONAL_INFO_TYPE='Applicant' ");
//				sql = sql.concat(" and ge_card.APPLICATION_RECORD_ID(+)=APPLICATION.APPLICATION_RECORD_ID ");
//				sql = sql.concat(" and ge_card.RE_ISSUE_FLAG(+)='N' ");				
//										
//				/*****Criteria for Search Type*****/
//				
//				/*****Search All Applicatiom *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_ALL)){
//				}
//				
//				/*****Search Applicatiom Mail-in*****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_MAILIN)){
//					sql=sql.concat("  and ");
//					sql=sql.concat(" 	(application.JOB_STATE in ('ST004','ST014')");
//					sql=sql.concat(" 		or (application.JOB_STATE in('ST021','ST025','ST027')");
//					sql=sql.concat(" 			and application.LAST_CA_ID='");
//					sql=sql.concat(username);
//					sql=sql.concat("'");			
//					sql=sql.concat(" 			)       ");
//					sql=sql.concat("    )");
//					sql=sql.concat(" and application.ENTRY_TYPE='ET04'");
//					sql=sql.concat(" and application.APPLICATION_STATUS='New' ");
//				}
//				
//				/*****Search Application for Re-open *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_REOPEN)){
//					sql=sql.concat(" and (application.APPLICATION_STATUS='Canceled' or application.APPLICATION_STATUS='Rejected')");
//					sql=sql.concat(" and application.JOB_STATE   in( 'ST007','ST009','ST010','ST016','ST019','ST022','ST023','ST029','ST040','ST041','ST042' )  ");
//				}
//				
//				
//				/*****Search Application for Upgrede-Downgrade *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_UPDOWNGRADE)){
//					sql=sql.concat("  and application.JOB_STATE   in( 'ST008','ST018','ST039','ST045' )  ");
//					sql=sql.concat("  and application.UP_DOWN_FLAG='N'");
//				}
//				
//				
//				/*****Search  Application for add Supplementary*****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_ADDSUPPLEMENTARY)){
//					sql=sql.concat("  and application.JOB_STATE   in('ST018','ST056','ST058')");
//					sql=sql.concat("  and application.APPLICATION_TYPE='B'");
//					sql=sql.concat("  and application.UP_DOWN_FLAG='N'");
//				}
//				
//				
//				/*****Search  Exception Application Main-in *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_EXCEP_MAILIN)){
//					sql=sql.concat("  and application.JOB_STATE   in('ST020','ST024','ST026')  ");
//					sql=sql.concat("  and application.ENTRY_TYPE='ET04'");
//				}
//				
//				/*****Search Instant Credit for Make Decision *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_INSTANT_CREDIT_MAKE_DECISION)){
//					sql=sql.concat("  and application.JOB_STATE in('"+NaosConstant.JOB_STATE_COMPLETE_CREATE_INSTANT_CREDIT+"')  ");
//				}
//				
//				/*****Search Instant Credit for Print Sale Slip *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_INSTANT_CREDIT_PRINT_SALE_SLIP)){
//					sql=sql.concat("  and application.JOB_STATE in('"+NaosConstant.JOB_STATE_CA_MAKE_PURCHASE_INSTANT_CREDIT+"')  ");
//				}
//				
//																									   				   				   				   				   	
//				/*****Criteria *****/
//				
//				if(appNo!=null&&!appNo.equals("")){
//					sql = sql.concat(" and  APPLICATION.APPLICATION_NO = '"+appNo+"'");
//				}
//				if(cardNo!=null&&!cardNo.equals("")){
//					sql = sql.concat(" and GE_CARD.CARD_NO = '"+cardNo+"'");
//				}
//				if(citizenId!=null&&!citizenId.equals("")){
//					sql = sql.concat(" and PERSONAL_INFO.CITIZEN_ID = '"+citizenId+"'");					
//				}
//				if(chiName!=null&&!chiName.equals("")){
//					sql = sql.concat(" and PERSONAL_INFO.TW_CH_L_NAME ||  PERSONAL_INFO.TW_CH_F_NAME = '"+chiName+"'");
//				}
//				if(fromReceiveDate!=null && !fromReceiveDate.equals("") && toReceiveDate!=null && !toReceiveDate.equals("")){
//					sql = sql.concat(" and (APPLICATION.APPLY_RECEIVE_DATE between to_date('"+ fromReceiveDate +" 00:00:00','dd/mm/yyyy hh24:mi:ss') and to_date('" + toReceiveDate + " 23:59:59','dd/mm/yyyy hh24:mi:ss')) ");
//				}
//				if (searchType.equals(NaosConstant.SEARCH_TYPE_ALL)) {
//					if(status!=null && !status.equals("")){
//						sql = sql.concat(" and decode(Application.application_status, '"+ NaosConstant.APPLICATION_STATUS_NEW + "','"+ NaosConstant.APPLICATION_STATUS_CA + "','"+ NaosConstant.APPLICATION_STATUS_EXCEPTION + "','"+ NaosConstant.APPLICATION_STATUS_UW + "',application_status,application_status) = '" + status + "'");
//					}
//					if(selectedUser != null && !selectedUser.equals("") && (status.equals(NaosConstant.APPLICATION_STATUS_APPROVED) || status.equals(NaosConstant.APPLICATION_STATUS_REJECTED))) {
//						sql = sql.concat(" and LAST_EX_ID = '" + selectedUser + "'");
//					}
//				}
//				/*****End Criteria *****/
//				
//				
//				//sql = sql.concat(" and (ROWNUM >0 and ROWNUM <500) ");
//				/*****Check business class *****/
//				sql = sql.concat(" and APPLICATION.BUSINESS_CLASS_ID IN"+getStringStatementIn(getAccessBusinessClass(username)));
//				sql = sql.concat(" order by   APPLICATION.APPLICATION_NO, APPLICATION.APPLICATION_TYPE, APPLICATION.APPLICATION_SEQ"); 
//				/*****Check business class *****/				
//							
//			   	logger.debug("*****SQL FOR SEARCH APPLICATION*****TYPE>>"+searchType+"<<"); 
//			   	logger.debug(sql);  
//			   	logger.debug("*****END SQL FOR SEARCH APPLICATION*****"); 		   				   				   				   	
//			   	ps = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);										
//				rset = ps.executeQuery();		   					 			   	 
//				SearchApplicationM searchAppM;
//				
//				EJBServiceLocator ejbServiceLocator = EJBServiceLocator.getInstance();
////				NaosSGWorkflowManagerHome wkFlowHome = (NaosSGWorkflowManagerHome) ejbServiceLocator.getHome(NaosEJBService.WORKFLOW_EJB);
////				NaosSGWorkflowManager wkFlowObj = wkFlowHome.create();	
////				HashMap appMap = wkFlowObj.getReadyOrClaimedJobInAllStaffQueue();
//				
//				while(rset.next()){//while	
//					searchAppM =new SearchApplicationM();
//					searchAppM.setSgChiFName(rset.getString("TW_CH_F_NAME"));
//					searchAppM.setSgChiLName(rset.getString("TW_CH_L_NAME"));
//					searchAppM.setCitizenId(rset.getString("CITIZEN_ID"));
//					searchAppM.setCardNo(rset.getString("CARD_NO"));
//					searchAppM.setApplicationType(rset.getString("APPLICATION_TYPE"));
//					searchAppM.setApplicationStatus(rset.getString("APPLICATION_STATUS"));
//					searchAppM.setProductCode(rset.getString("PRODUCT_CODE"));
//					searchAppM.setProductDesc(rset.getString("PRODUCT_DESC"));
//					searchAppM.setEmbossName(rset.getString("EMBOSS_NAME"));
//					searchAppM.setAccountId(rset.getString("ACCOUNT_ID"));
//					searchAppM.setApplication_rec_Id(rset.getString("APPLICATION_RECORD_ID"));
//					searchAppM.setChannelCode(rset.getString("CHANNEL_CODE"));
//					searchAppM.setJobState(rset.getString("JOB_STATE"));
//					searchAppM.setEntryType(rset.getString("ENTRY_TYPE"));
//					searchAppM.setFinalLendingLimit(rset.getString("FINAL_LENDING_LIMIT"));
//					searchAppM.setFinalLendingLimit(rset.getString("FINAL_LENDING_LIMIT"));
//					searchAppM.setFirstCAId(rset.getString("FIRST_CA_ID"));
//					searchAppM.setBusinessClassId(rset.getString("BUSINESS_CLASS_ID"));
//					searchAppM.setApplicationNo(rset.getString("APPLICATION_NO"));
//					searchAppM.setFinalCreditLine(rset.getString("FINAL_CREDIT_LINE"));					
//					searchAppM.setReceiveDate(rset.getTimestamp("APPLY_RECEIVE_DATE"));					
//					if (searchAppM.getApplicationStatus().equals(NaosConstant.APPLICATION_STATUS_APPROVED) ) {
//						searchAppM.setOwner(rset.getString("LAST_EX_ID"));
//						searchAppM.setActionDate(rset.getTimestamp("LAST_EX_DATE"));
//						searchAppM.setReason("");
//					}else if( searchAppM.getApplicationStatus().equals(NaosConstant.APPLICATION_STATUS_REJECTED) ){
//						  searchAppM.setOwner(rset.getString("LAST_EX_ID"));
//						  searchAppM.setActionDate(rset.getTimestamp("LAST_EX_DATE"));					
//						  //get Reason FromTable
//						 ReasonMDAO  reasonDAO=GEDAOFactory.getReasonMDAO();
//						 Vector reasons= reasonDAO.loadReasons(searchAppM.getApplication_rec_Id(),GEServiceLocator.ORIG_DB);					
//						 String reason="";
//						 for(int i=0;i<reasons.size();i++){
//						    ReasonM   reasonM=(ReasonM)reasons.get(i);
//						    if( NaosConstant.APPLICATION_STATUS_REJECTED.equals( reasonM.getReasonType() )){
//						        if(!reason.equals("") ){reason+=", ";}
//						        reason+=reasonM.getReasonCode();						        
//						    }
//						 }
//						  searchAppM.setReason(reason);
//						  //==============================
//					} else if (searchAppM.getApplicationStatus().equals(NaosConstant.APPLICATION_STATUS_CANCELED)) {
//						searchAppM.setOwner("");
//						searchAppM.setActionDate(rset.getTimestamp("TW_PENDING_STATUS_DATE"));
//                         //get Reason FromTable
//						 ReasonMDAO  reasonDAO=GEDAOFactory.getReasonMDAO();
//						 Vector reasons= reasonDAO.loadReasons(searchAppM.getApplication_rec_Id(),GEServiceLocator.ORIG_DB);					
//						 String reason="";
//						 for(int i=0;i<reasons.size();i++){
//						    ReasonM   reasonM=(ReasonM)reasons.get(i);
//						    if( NaosConstant.APPLICATION_STATUS_CANCELED.equals( reasonM.getReasonType() )){
//						        if(!reason.equals("") ){reason+=", ";}
//						        reason+=reasonM.getReasonCode();						        
//						    }
//						 }
//						  searchAppM.setReason(reason);
//					    //==============================						  
//					} else {
//						
////						WorkQueue workQ = (WorkQueue) appMap.get(searchAppM.getApplicationNo());
//						String owner = "";
//						
//
//						if (searchAppM.getApplicationStatus().equals(NaosConstant.APPLICATION_STATUS_CA)) {
////							if (workQ != null) {
////								owner = workQ.getCaId();
////								if (owner == null || owner.equals("")) {
////									owner = "";
////								}
////							} else {
//								owner = "-";
////							}
//							
//							searchAppM.setOwner(owner);
//							searchAppM.setActionDate(rset.getTimestamp("DE_DATE"));								
//							searchAppM.setReason("");	
//												
//						} else if (searchAppM.getApplicationStatus().equals(NaosConstant.APPLICATION_STATUS_PENDING)) {
////							if (workQ != null) {
////								searchAppM.setOwner(((CreditCardAppTemplateM)workQ.getMessage()).getCAPQUserName());
////							} else {
////								searchAppM.setOwner("-");
////							}
//								
//							searchAppM.setActionDate(rset.getTimestamp("LAST_CA_DECISION_DATE"));
//			                //get Reason FromTable
//					 		ReasonMDAO  reasonDAO=GEDAOFactory.getReasonMDAO();
//					 		Vector reasons= reasonDAO.loadReasons(searchAppM.getApplication_rec_Id(),GEServiceLocator.ORIG_DB);				
//					 		
//					 		logger.debug(" $ # reasons = " + reasons.size());	
//					 		
//					 		String reason="";
//
//					 		for(int i=reasons.size()-1;i>=0;i--){
//						   		ReasonM   reasonM=(ReasonM)reasons.get(i);
//						    	if( NaosConstant.APPLICATION_STATUS_PENDING.equals( reasonM.getReasonType() )){						       
//						              reason=reasonM.getReasonCode();
//						              break;		
//						         }				        
//					       }
//					       searchAppM.setReason(reason);
//					       //==============================
//						 } else if (searchAppM.getApplicationStatus().equals(NaosConstant.APPLICATION_STATUS_UW)) {
//								
////								if (workQ != null) {	
////									owner = ((CreditCardAppTemplateM)workQ.getMessage()).getEXIQUserName();
////									if (owner == null || owner.equals(""))
////										owner = "";
////								} else {
////									owner = "-";
////								}
//								searchAppM.setOwner(owner);
//								searchAppM.setActionDate(rset.getTimestamp("LAST_CA_DECISION_DATE"));
//								searchAppM.setReason("");	
//						}
//
//					}
//					
//					result.add(searchAppM);
//				} // end while			         		   					          
//			   	 }
//			   } // end try
//			   catch(Exception e){
//			   	   logger.error("getSearchApplication : "+e.getMessage());
//			   	   e.printStackTrace();
//			   }finally{
//			   	try{
//			   		try{
//						if(conn!=null)conn.commit();
//					}catch(Exception e){}
//				   	 if(rset!=null){rset.close();}
//				   	 if(ps!=null){ps.close();}
//				   	 if(conn!=null){conn.close();}
//			   	}catch(Exception e){
//			   		 e.printStackTrace();
//			   }
//			   }
		     			
		return result;
	}
	
	public Vector getSearchApplication_SG(String username,String searchType,String appNo,String cardNo,String citizenId,String chiFName,String chiLName,String chiName) throws SearchApplicationUtilDAOException{				
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset = null;	
		Vector result =new Vector();	
		searchType=(searchType==null)?"":searchType;			   	
//		try{			   					    
//				conn = getConnection();
//				if(conn!=null){							   		   	
//				String sql="";				   				   				   				   			   	
//				sql = sql.concat(" SELECT Application.application_record_id, ");
//		    	sql = sql.concat(" Application.application_no,");
//			    sql = sql.concat(" Application.account_id,");
//			    sql = sql.concat(" Application.business_class_id, ");
//			    sql = sql.concat(" Application.job_state,");
//			    sql = sql.concat(" Application.product_code,");
//			    sql = sql.concat(" Application.channel_code,");
//			    sql = sql.concat(" Application.application_status, ");
//				sql = sql.concat(" Application.application_type,");
//				sql = sql.concat(" Application.entry_type,");
//				sql = sql.concat(" Application.final_credit_line,");
//				sql = sql.concat(" Application.first_ca_id,");
//				sql = sql.concat(" Application.final_lending_limit,");
//				sql = sql.concat(" Personal_info.citizen_id,");
//				sql = sql.concat(" Personal_info.tw_ch_f_name,");
//				sql = sql.concat(" Personal_info.tw_ch_l_name,");
//				sql = sql.concat(" Personal_info.ENG_FIRST_NAME,");
//				sql = sql.concat(" Personal_info.ENG_LAST_NAME,");
//				sql = sql.concat(" Personal_info.emboss_name,");
//				sql = sql.concat(" Product.product_desc,");
//				sql = sql.concat(" Ge_card.card_no");
//				
//				//sql = sql.concat(" from application,personal_Info,product,ge_card");		
//				//sql = sql.concat(" where application.APPLICATION_RECORD_ID = personal_Info.APPLICATION_RECORD_ID");			
//				
//				if(!searchType.equals(NaosConstant.SEARCH_TYPE_UW_MAILIN)){
//					sql = sql.concat(" from application,personal_Info,product,ge_card");		
//					sql = sql.concat(" where application.APPLICATION_RECORD_ID = personal_Info.APPLICATION_RECORD_ID");				
//				} else {
//					sql = sql.concat(" from application,personal_Info,product,ge_card,user_profile");
//					sql = sql.concat(" where application.APPLICATION_RECORD_ID = personal_Info.APPLICATION_RECORD_ID and user_profile.USER_NAME = '");
//					sql = sql.concat(username);
//					sql = sql.concat("' and application.TW_PROFILE_ID = user_profile.PROFILE_ID");
//				}
//					
//				sql = sql.concat(" and application.PRODUCT_CODE=product.PRODUCT_ID ");
//				sql = sql.concat(" and PERSONAL_INFO.PERSONAL_INFO_TYPE='Applicant' ");
//				sql = sql.concat(" and ge_card.APPLICATION_RECORD_ID(+)=APPLICATION.APPLICATION_RECORD_ID ");
//				sql = sql.concat(" and ge_card.RE_ISSUE_FLAG(+)='N' ");		
//					
//				/*****Criteria for Search Type*****/
//				
//				/*****Search All Applicatiom *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_ALL)){
//				}
//				
//				/*****Search Applicatiom Mail-in*****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_MAILIN)){
//					sql=sql.concat("  and ");
//					sql=sql.concat(" 	(application.JOB_STATE in ('ST004','ST014')");
//					sql=sql.concat(" 		or (application.JOB_STATE in('ST021','ST025','ST027')");
//					sql=sql.concat(" 			and application.LAST_CA_ID='");
//					sql=sql.concat(username);
//					sql=sql.concat("'");			
//					sql=sql.concat(" 			)       ");
//					sql=sql.concat("    )");
//					sql=sql.concat(" and application.ENTRY_TYPE='ET04'");
//					sql=sql.concat(" and application.APPLICATION_STATUS='New' ");
//				}
//				
//				/*****Search Application for Re-open *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_REOPEN)){
//					sql=sql.concat(" and (application.APPLICATION_STATUS='Canceled' or application.APPLICATION_STATUS='Rejected')");
//					sql=sql.concat(" and application.JOB_STATE   in( 'ST007','ST009','ST010','ST016','ST019','ST022','ST023','ST029','ST040','ST041','ST042' )  ");
//				}
//				
//				
//				/*****Search Application for Upgrede-Downgrade *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_UPDOWNGRADE)){
//					sql=sql.concat("  and application.JOB_STATE   in( 'ST008','ST018','ST039','ST045' )  ");
//					sql=sql.concat("  and application.UP_DOWN_FLAG='N'");
//				}
//				
//				
//				/*****Search  Application for add Supplementary*****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_ADDSUPPLEMENTARY)){
//					//sql=sql.concat("  and application.JOB_STATE   in('ST018','ST039','ST045')");
//					sql=sql.concat("  and application.JOB_STATE   in('ST018','ST056','ST058')");
//					sql=sql.concat("  and application.APPLICATION_TYPE='B'");
//				}
//				
//				
//				/*****Search  Exception Application Main-in *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_EXCEP_MAILIN)){
//					sql=sql.concat("  and application.JOB_STATE   in('ST020','ST024','ST026')  ");
//					sql=sql.concat("  and application.ENTRY_TYPE='ET04'");
//				}
//				
//				/*****Search Instant Credit for Make Decision *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_INSTANT_CREDIT_MAKE_DECISION)){
//					sql=sql.concat("  and application.JOB_STATE in('"+NaosConstant.JOB_STATE_COMPLETE_CREATE_INSTANT_CREDIT+"')  ");
//				}
//				
//				/*****Search Instant Credit for Print Sale Slip *****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_INSTANT_CREDIT_PRINT_SALE_SLIP)){
//					sql=sql.concat("  and application.JOB_STATE in('"+NaosConstant.JOB_STATE_CA_MAKE_PURCHASE_INSTANT_CREDIT+"')  ");
//				}
//				
//				/*****Search  Application for add Supplementary*****/
//				if(searchType.equals(NaosConstant.SEARCH_TYPE_UW_MAILIN)){
//					sql=sql.concat("  and application.JOB_STATE   in('ST038','ST055','ST059','ST060')");
//					sql=sql.concat("  and application.APPLICATION_TYPE='B'");
//				}
//																									   				   				   				   				   	
//				/*****Criteria *****/
//				
//				if(appNo!=null&&!appNo.equals("")){
//					sql = sql.concat(" and  APPLICATION.APPLICATION_NO = '"+appNo+"'");
//				}
//				if(cardNo!=null&&!cardNo.equals("")){
//					sql = sql.concat(" and GE_CARD.CARD_NO = '"+cardNo+"'");
//				}
//				if(citizenId!=null&&!citizenId.equals("")){
//					sql = sql.concat(" and PERSONAL_INFO.CITIZEN_ID = '"+citizenId+"'");					
//				}
//				if(chiFName!=null&&!chiFName.equals("")){
//					// comment out for tw
//					//sql = sql.concat(" and PERSONAL_INFO.THAI_FIRST_NAME = '"+chiFName+"'");
//					sql = sql.concat(" and PERSONAL_INFO.TW_CH_F_NAME = '"+chiFName+"'");
//				}
//				if(chiLName!=null&&!chiLName.equals("")){
//					// comment out for tw
//					//sql = sql.concat(" and PERSONAL_INFO.THAI_LAST_NAME = '"+chiLName+"'");
//					sql = sql.concat(" and PERSONAL_INFO.TW_CH_L_NAME = '"+chiLName+"'");
//				}
//				if(chiName!=null&&!chiName.equals("")){				
//					sql = sql.concat(" and PERSONAL_INFO.TW_CH_L_NAME ||  PERSONAL_INFO.TW_CH_F_NAME = '"+chiName+"'");
//				}
//				/*****End Criteria *****/
//				
//				
//				//sql = sql.concat(" and (ROWNUM >0 and ROWNUM <500) ");
//				/*****Check business class *****/
//				sql = sql.concat(" and APPLICATION.BUSINESS_CLASS_ID IN"+getStringStatementIn(getAccessBusinessClass(username)));
//				sql = sql.concat(" order by   APPLICATION.APPLICATION_NO ASC"); 
//				/*****Check business class *****/				
//							
//			   	logger.debug("*****SQL FOR SEARCH APPLICATION*****TYPE>>"+searchType+"<<"); 
//			   	logger.debug(sql);  
//			   	logger.debug("*****END SQL FOR SEARCH APPLICATION*****"); 		   				   				   				   	
//			   	ps = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);										
//				rset = ps.executeQuery();		   					 			   	 
//				SearchApplicationM searchAppM;
//				while(rset.next()){//while	
//					searchAppM =new SearchApplicationM();					
//					searchAppM.setSgChiFName(rset.getString("TW_CH_F_NAME"));
//					searchAppM.setSgChiLName(rset.getString("TW_CH_L_NAME"));
//					searchAppM.setThaiFName(rset.getString("ENG_FIRST_NAME"));
//					searchAppM.setThaiLName(rset.getString("ENG_LAST_NAME"));
//					searchAppM.setCitizenId(rset.getString("CITIZEN_ID"));
//					searchAppM.setCardNo(rset.getString("CARD_NO"));
//					searchAppM.setApplicationType(rset.getString("APPLICATION_TYPE"));
//					searchAppM.setApplicationStatus(rset.getString("APPLICATION_STATUS"));
//					searchAppM.setProductCode(rset.getString("PRODUCT_CODE"));
//					searchAppM.setProductDesc(rset.getString("PRODUCT_DESC"));
//					searchAppM.setEngFName(rset.getString("ENG_FIRST_NAME"));
//					searchAppM.setEngLName(rset.getString("ENG_LAST_NAME"));
//					searchAppM.setEmbossName(rset.getString("EMBOSS_NAME"));
//					searchAppM.setAccountId(rset.getString("ACCOUNT_ID"));
//					searchAppM.setApplication_rec_Id(rset.getString("APPLICATION_RECORD_ID"));
//					searchAppM.setChannelCode(rset.getString("CHANNEL_CODE"));
//					searchAppM.setJobState(rset.getString("JOB_STATE"));
//					searchAppM.setEntryType(rset.getString("ENTRY_TYPE"));
//					searchAppM.setFinalLendingLimit(rset.getString("FINAL_LENDING_LIMIT"));
//					searchAppM.setFinalLendingLimit(rset.getString("FINAL_LENDING_LIMIT"));
//					searchAppM.setFirstCAId(rset.getString("FIRST_CA_ID"));
//					searchAppM.setBusinessClassId(rset.getString("BUSINESS_CLASS_ID"));
//					searchAppM.setApplicationNo(rset.getString("APPLICATION_NO"));
//					searchAppM.setFinalCreditLine(rset.getString("FINAL_CREDIT_LINE"));
//					result.add(searchAppM);
//				} // end while			         		   					          
//			   	 }
//			   } // end try
//			   catch(Exception e){
//			   	   logger.error("getSearchApplication : "+e.getMessage());
//			   	   e.printStackTrace();
//			   }finally{
//			   	try{
//			   		try{
//						if(conn!=null)conn.commit();
//					}catch(Exception e){}
//				   	 if(rset!=null){rset.close();}
//				   	 if(ps!=null){ps.close();}
//				   	 if(conn!=null){conn.close();}
//			   	}catch(Exception e){
//			   		 e.printStackTrace();
//			   }
//			   }
		     			
		return result;
	}	 
	
	private HashMap getAccessBusinessClass(String username){
		HashMap map = new HashMap();
		try{
			BusinessClassByUserDAO dao = ORIGDAOFactory.getBusinessClassByUserDAO();
			map=dao.getAccessBusinessClassByUser(username);
		}catch (Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	private  String getStringStatementIn(HashMap map) {
		String re="( ";			
		if(map==null){
			map=new HashMap();
		}
		Set set =map.keySet();
		Object o[]=set.toArray();
		if(o.length==0){
			return "( '' )";
		}else{				
			for(int j=0;j<o.length;j++){											
						re= re.concat("'");
						re=re.concat((String)o[j]);
						re=re.concat("'");
						if(j!=(o.length-1))
								re=re.concat(",");									
			}		
		re=re.concat(")");	
		return re;
		}	
	}	
		
	public Vector getSearchImage(String searchType,String requestId,String dealerCode,String channelCode,String userName,String faxInDateFrom,String faxInDateTo, String nric, String firstName, String lastName, String sortBy, String appNo, String mainCustomerType, String dealerFaxNo) throws SearchApplicationUtilDAOException{
		System.out.println("in getSearchImage");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rset = null;	
		Vector result =new Vector();	
		searchType=(searchType==null)?"":searchType;			   	
		
		String onlyReadyWorkItem = ""; // return
		try {
//			HashMap h = TableLookupCache.getCacheStructure();
//			Vector v = (Vector) (h.get("GeneralParamDataM"));
//			for (int i = 0; i < v.size(); i++) {
//				GeneralParamProperties model = (GeneralParamProperties) v.elementAt(i);
//				if (model.getCode().equalsIgnoreCase("SG_ONLY_READY_WORK_ITEM")) {
//					onlyReadyWorkItem = model.getValue();
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{			   					    
				conn = getConnection();
				if(conn!=null){							   		   	
				String sql="";				   				   				   				   			   	
				sql = sql.concat(" SELECT V.REQUEST_ID, V.ORG, V.PRODUCT_CODE, V.CHANNEL_CODE, V.PRODUCT, V.MKT_SOURCE_CODE, V.CREATE_DATE,V.FAX_IN_DATE_FIRST,V.NRIC,V.FIRST_NAME,V.LAST_NAME,V.BUS_CLASS_ID,V.DEALER,V.UPDATE_DATE,V.UPDATE_BY,V.MAIN_CUSTOMER_TYPE,V.DEALER,D.THNAME,D.FAX,C.CHANNEL_DESC ");
				
				if (onlyReadyWorkItem != null && onlyReadyWorkItem.equalsIgnoreCase("Y")) {
					sql = sql.concat(" from SG_VIEW_IMG_REQUEST, img_process_instance, img_activity, img_work_item");		
//					sql = sql.concat(" where img_activity.template_name = 'NO_DE0204_DEPullQ' and img_work_item.reason = 1 and (img_activity.state = 2 or (img_activity.state = 8 and img_activity.owner = '" + userName + "')) and '"+ NaosProcessConstant.PROCESS_PREFIX_DE_REQUEST + "' || SG_VIEW_IMG_REQUEST.REQUEST_ID = img_process_instance.top_level_name ");	
					sql = sql.concat(" and img_process_instance.piid = img_activity.piid and img_work_item.object_id = img_activity.aiid ");			
				} else {
					sql = sql.concat(" from SG_VIEW_IMG_REQUEST V INNER JOIN APPLICATION_IMG_REQUEST I ON I.IMG_REQUEST = V.REQUEST_ID LEFT JOIN CHANNEL C ON V.CHANNEL_CODE=C.CHANNEL_ID LEFT JOIN DEALER D ON (V.DEALER=D.DEACDE and d.CMPCDE = '01') where 1=1 ");
				}	
																									   				   				   				   				   	
				/*****Criteria *****/
				
				if(requestId!=null&&!requestId.equals("")){
					sql = sql.concat(" and V.REQUEST_ID = '"+requestId+"'");
				}
				if(faxInDateFrom!=null&&!faxInDateFrom.equals("")){
					sql = sql.concat(" and TRUNC(V.FAX_IN_DATE_FIRST) >= TO_DATE('"+faxInDateFrom+"','DD/MM/YYYY')");
				}
				if(faxInDateTo!=null&&!faxInDateTo.equals("")){
					sql = sql.concat(" and TRUNC(V.FAX_IN_DATE_FIRST) <= TO_DATE('"+faxInDateTo+"','DD/MM/YYYY')");
				}
				if(dealerCode!=null&&!dealerCode.equals("")){				
					sql = sql.concat(" and V.DEALER = '"+dealerCode+"'");
				}
				if(channelCode!= null && !channelCode.equals("ALL") && !channelCode.equals("")){				
					sql = sql.concat(" and V.CHANNEL_CODE = '"+channelCode+"'");
				}
				if(nric!=null&&!nric.equals("")){				
					sql = sql.concat(" and upper(V.NRIC) = upper('"+nric+"')");
				}
				if(firstName!=null&&!firstName.equals("")){				
					sql = sql.concat(" and upper(V.FIRST_NAME) = upper('"+firstName+"')");
				}
				if(lastName!=null&&!lastName.equals("")){				
					sql = sql.concat(" and upper(V.LAST_NAME) = upper('"+lastName+"')");
				}
				if(mainCustomerType!=null&&!mainCustomerType.equals("")){				
					sql = sql.concat(" and V.MAIN_CUSTOMER_TYPE = '"+mainCustomerType+"'");
				}
				if(dealerFaxNo!=null&&!dealerFaxNo.equals("")){				
					sql = sql.concat(" and D.DEALER_FAX_NO = '"+dealerFaxNo+"'");
				}
				

				/*****End Criteria *****/
							
				//sql = sql.concat(" and (ROWNUM >0 and ROWNUM <500) ");

				sql = sql.concat(" and BUS_CLASS_ID IN"+getStringStatementIn(getAccessBusinessClass(userName)));
				if(sortBy!=null&&!sortBy.equals("")){				
					sql = sql.concat(" ORDER BY "+sortBy);
				}else{
					sql = sql.concat(" ORDER BY CHANNEL_CODE desc,CREATE_DATE");
				}
				
			   	System.out.println("*****SQL FOR SEARCH IMAGE*****TYPE>>"+searchType+"<<"); 
			   	System.out.println(sql);  
			   	System.out.println("*****END SQL FOR SEARCH IMAGE*****"); 		   				   				   				   	
			   	ps = conn.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);										
				rset = ps.executeQuery();		   					 			   	 
				SearchImageM searchImage;
				while(rset.next()){//while	
					searchImage =new SearchImageM();					
					searchImage.setRequestID(rset.getString("REQUEST_ID"));
					searchImage.setOrg(rset.getString("ORG"));
					searchImage.setProductCode(rset.getString("PRODUCT_CODE"));
					searchImage.setChannelCode(rset.getString("CHANNEL_CODE"));
					searchImage.setProduct(rset.getString("PRODUCT"));
					searchImage.setMktSourceCode(rset.getString("MKT_SOURCE_CODE"));
					searchImage.setCreateDate(this.getDateByTimeStamp(rset.getTimestamp("CREATE_DATE")));
					searchImage.setFaxInDateFirst(this.getDateByTimeStamp(rset.getTimestamp("FAX_IN_DATE_FIRST")));
					searchImage.setNric(rset.getString("NRIC"));
					searchImage.setFirstName(rset.getString("FIRST_NAME"));
					searchImage.setLastName(rset.getString("LAST_NAME"));
					searchImage.setBusClassID(rset.getString("BUS_CLASS_ID"));
					searchImage.setMainCustomerType(rset.getString("MAIN_CUSTOMER_TYPE"));
					searchImage.setDealerID(rset.getString("DEALER"));
					searchImage.setDealer(rset.getString("DEALER_DESC"));
					searchImage.setDealerFaxNo(rset.getString("DEALER_FAX_NO"));
					searchImage.setUpdateDate(this.getDateByTimeStamp(rset.getTimestamp("UPDATE_DATE")));
					searchImage.setUpdateBy(rset.getString("UPDATE_BY"));
					searchImage.setChannelDesc(rset.getString("CHANNEL_DESC"));
					result.add(searchImage);
				} // end while			         		   					          
			   	 }
			   } // end try
			   catch(Exception e){
			   	   logger.error("getSearchApplication : "+e.getMessage());
			   	   e.printStackTrace();
			   }finally{
			   	try{
			   		try{
//						if(conn!=null)conn.commit();
					}catch(Exception e){}
				   	 if(rset!=null){rset.close();}
				   	 if(ps!=null){ps.close();}
				   	 if(conn!=null){conn.close();}
			   	}catch(Exception e){
			   		 e.printStackTrace();
			   }
			   }
		     			
		return result;
	}	 
	
		
	private String getDateByTimeStamp(Timestamp timestamp) {
		String result = "";

		Calendar calendar = Calendar.getInstance();

		logger.debug("timestamp : " + timestamp);

		if (timestamp != null) {
			calendar.setTime(new Date(timestamp.getTime()));

			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int date = calendar.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int min = calendar.get(Calendar.MINUTE);
			int sec = calendar.get(Calendar.SECOND);

			result = this.appendZero(date + "", 2) + "/" + this.appendZero((month + 1) + "", 2) + "/" + (year) + " " + this.appendZero(hour + "", 2) + ":" + this.appendZero(min + "", 2);

			logger.debug("result : " + result);
		}

		return result;
	}
			
	private String appendZero(String src, int numOfDes) {
		if (src == null || src.trim().length() > numOfDes)
			return src;

		StringBuffer resultBuffer = new StringBuffer(src);

		for (int i = src.length(); i < numOfDes; i++) {
			resultBuffer.insert(0, '0');
		}

		return resultBuffer.toString();
	}


}
