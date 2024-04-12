package com.eaf.core.ulo.service.template.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterProcessDataM;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;
import com.eaf.service.common.util.ServiceUtil;

public class TemplateBuilderDAOImpl extends InfBatchObjectDAO implements TemplateBuilderDAO{
	private static transient Logger logger = Logger.getLogger(TemplateBuilderDAOImpl.class);
	@Override
	public TemplateMasterDataM getEmailTemplate(TemplateBuilderRequest templateBuilderRequest) {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TemplateMasterDataM  templateMaster  = new TemplateMasterDataM();
		String templateId = templateBuilderRequest.getTemplateId();
		int dbType = templateBuilderRequest.getDbType();
		logger.debug("templateId >> "+templateId);
		logger.debug("dbType >> "+dbType);
		try{
			conn = getConnection(dbType);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TEMPLATE_ID, TEMPLATE_NAME,CONTENT, SUBJECT");
			sql.append(" FROM MS_EMAIL_TEMPLATE_MASTER EMAIL ");
			sql.append(" WHERE EMAIL.TEMPLATE_ID =?");			 
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			int index=1;
			ps.setString(index++,templateId);
			rs = ps.executeQuery();			
			if(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				String TEMPLATE_NAME = rs.getString("TEMPLATE_NAME");
				String CONTENT = rs.getString("CONTENT");
				String SUBJECT = rs.getString("SUBJECT");
				templateMaster.setTemplateBody(CONTENT);
				templateMaster.setTemplateHeader(SUBJECT);
				templateMaster.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateName(TEMPLATE_NAME);
				templateMaster.setTemplateType(templateBuilderRequest.getTemplateType());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		 return templateMaster;
	}
	@Override
	public TemplateMasterDataM getSmsTemplate(TemplateBuilderRequest templateBuilderRequest) {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TemplateMasterDataM  templateMaster  = new TemplateMasterDataM();
		try{
			conn = getConnection(templateBuilderRequest.getDbType());
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT TEMPLATE_ID, TEMPLATE_NAME,MESSAGE_TH,MESSAGE_EN,APPLICATION_STATUS,CONTACT_POINT,PRODUCT_TYPE ");
			sql.append(" FROM MS_SMS_TEMPLATE_MASTER  ");
			sql.append(" WHERE TEMPLATE_ID  =? ");
			
			 
			String language = templateBuilderRequest.getLanguage();
			logger.debug("MESSAGE SELECT TEMPLATE BY NATIONALITY : "+language);
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());	
			int index=1;
			ps.setString(index,templateBuilderRequest.getTemplateId()); 	
			rs = ps.executeQuery();
			if(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");		
				String TEMPLATE_NAME = rs.getString("TEMPLATE_NAME");		
				String MESSAGE_TH = rs.getString("MESSAGE_TH");	
				String MESSAGE_EN = rs.getString("MESSAGE_EN");	
				String CONTACT_POINT = rs.getString("CONTACT_POINT");	
				String PRODUCT_TYPE = rs.getString("PRODUCT_TYPE");
				if(FormatUtil.TH.toUpperCase().equals(language.toUpperCase())){
					templateMaster.setTemplateBody(MESSAGE_TH);
					templateMaster.setLanguage(FormatUtil.TH.toUpperCase());
				}else{
					if(!InfBatchUtil.empty(MESSAGE_EN)){
						templateMaster.setTemplateBody(MESSAGE_EN);
						templateMaster.setLanguage(FormatUtil.EN.toUpperCase());
					}else{
						templateMaster.setTemplateBody(MESSAGE_TH);
						templateMaster.setLanguage(FormatUtil.TH.toUpperCase());
					}
				}
				templateMaster.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateName(TEMPLATE_NAME);
				templateMaster.setTemplateType(templateBuilderRequest.getTemplateType());
				templateMaster.setContactPoint(CONTACT_POINT);
				templateMaster.setProductType(PRODUCT_TYPE);
			}	
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		 return templateMaster;
	}
	@Override
	public TemplateMasterDataM getRejectLetterTemplate(TemplateBuilderRequest templateBuilderRequest) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TemplateMasterDataM  templateMaster  = new TemplateMasterDataM();
		RejectLetterProcessDataM   rejectLetterProcessDataM = (RejectLetterProcessDataM)templateBuilderRequest.getRequestObject();
		String templateCode = rejectLetterProcessDataM.getTemplateCode();
		String applyType =rejectLetterProcessDataM.getApplyType();
		String product =rejectLetterProcessDataM.getProduct();
		if(!ServiceUtil.empty(rejectLetterProcessDataM.getLanguage()) && !rejectLetterProcessDataM.getLanguage().equals(RejectLetterUtil.TH)){
			rejectLetterProcessDataM.setLanguage(RejectLetterUtil.EN);
		}
		String language=rejectLetterProcessDataM.getLanguage();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM MS_REJECT_LETTER_TEMPLATE RLT");
			sql.append(" WHERE TEMPLATE_CODE =?");
			sql.append(" AND APPLY_TYPE=?");
			sql.append(" AND PRODUCT=?");
			sql.append(" AND LANGUAGE=?");
			sql.append(" UNION ALL");
			sql.append(" SELECT * FROM MS_REJECT_LETTER_TEMPLATE MLT");
			sql.append(" WHERE  TEMPLATE_CODE =?");
			sql.append(" AND APPLY_TYPE=?");
			sql.append(" AND PRODUCT='ALL'"); // CASE ALL
			sql.append(" AND LANGUAGE=?");
			sql.append(" AND NOT EXISTS(SELECT 1 FROM MS_REJECT_LETTER_TEMPLATE ");
			sql.append(" WHERE TEMPLATE_CODE =?");
			sql.append(" AND APPLY_TYPE=?");
			sql.append(" AND PRODUCT=?");
			sql.append(" AND LANGUAGE=?)");
	 
			logger.debug("sql : " + sql);
			logger.debug("templateCode : " + templateCode);
			logger.debug("applyType : " + applyType);
			logger.debug("product : " + product);
			logger.debug("language" + language);
			ps = conn.prepareStatement(sql.toString());
			int index=1;
			ps.setString(index++,templateCode);
			ps.setString(index++,applyType);
			ps.setString(index++,product);
			ps.setString(index++,language);
			
			ps.setString(index++,templateCode);
			ps.setString(index++,applyType);
			ps.setString(index++,language);
			
			ps.setString(index++,templateCode);
			ps.setString(index++,applyType);
			ps.setString(index++,product);
			ps.setString(index++,language);
			rs = ps.executeQuery();			
			if(rs.next()) {
				String APPLY_TYPE = rs.getString("APPLY_TYPE");
				String DETAIL1 = rs.getString("DETAIL1");
				String DETAIL2 = rs.getString("DETAIL2");
				String DETAIL3 = rs.getString("DETAIL3");
				String LANGUAGE = rs.getString("LANGUAGE");
				String POST_SCRIPT1 = rs.getString("POST_SCRIPT1");
				String POST_SCRIPT2 = rs.getString("POST_SCRIPT2");
				String PRODUCT = rs.getString("PRODUCT");
				String REMARK1 = rs.getString("REMARK1");
				String TEMPLATE_CODE = rs.getString("TEMPLATE_CODE");
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				String TEMPLATE_NAME = rs.getString("TEMPLATE_NAME");
				String REJECT_TEMPLATE_TYPE = rs.getString("TEMPLATE_TYPE");
				 				
				templateBuilderRequest.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateType(templateBuilderRequest.getTemplateType());
				templateMaster.setApplyType(APPLY_TYPE);
				templateMaster.setDetail1(DETAIL1);
				templateMaster.setDetail2(DETAIL2);
				templateMaster.setDetail3(DETAIL3);
				templateMaster.setLanguage(LANGUAGE);
				templateMaster.setPostScript1(POST_SCRIPT1);
				templateMaster.setPostScript2(POST_SCRIPT2);
				templateMaster.setRejectLetterProduct(PRODUCT);
				templateMaster.setRemark1(REMARK1);
				templateMaster.setTemplateCode(TEMPLATE_CODE);
				templateMaster.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateName(TEMPLATE_NAME);
				templateMaster.setRejectLetterTemplateType(REJECT_TEMPLATE_TYPE);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return templateMaster;
	}
	@Override
	public TemplateMasterDataM getKMobileTemplate(TemplateBuilderRequest templateBuilderRequest) {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TemplateMasterDataM  templateMaster  = new TemplateMasterDataM();
		try{
			conn = getConnection(templateBuilderRequest.getDbType());
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT TEMPLATE_ID, TEMPLATE_NAME,APPLICATION_STATUS,  ");
			sql.append(" KMOBILE_MESSAGE_EN,KMOBILE_MESSAGE_TH,CONTACT_POINT,PRODUCT_TYPE, ");
			sql.append(" KMOBILE_ALERT_MESSAGE_EN, KMOBILE_ALERT_MESSAGE_TH ");
			sql.append(" FROM MS_SMS_TEMPLATE_MASTER   ");
			sql.append(" WHERE TEMPLATE_ID  =?  ");
			
			String language = templateBuilderRequest.getLanguage();
			if(InfBatchUtil.empty(language)){
				language = FormatUtil.TH.toUpperCase();
			}
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());	
			int index=1;
			ps.setString(index,templateBuilderRequest.getTemplateId()); 	
			rs = ps.executeQuery();
			if(rs.next()) {
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");		
				String TEMPLATE_NAME = rs.getString("TEMPLATE_NAME");		
				String KMOBILE_MESSAGE_TH = rs.getString("KMOBILE_MESSAGE_TH");
				String KMOBILE_MESSAGE_EN = rs.getString("KMOBILE_MESSAGE_EN");
				String CONTACT_POINT = rs.getString("CONTACT_POINT");
				String PRODUCT_TYPE = rs.getString("PRODUCT_TYPE");
				String KMOBILE_ALERT_MESSAGE_EN = rs.getString("KMOBILE_ALERT_MESSAGE_EN");
				String KMOBILE_ALERT_MESSAGE_TH = rs.getString("KMOBILE_ALERT_MESSAGE_TH");
				
				if(FormatUtil.TH.toUpperCase().equals(language.toUpperCase())){					
					templateMaster.setLanguage(FormatUtil.TH.toUpperCase());
				}else{
					if(!InfBatchUtil.empty(KMOBILE_MESSAGE_EN)){
						templateMaster.setLanguage(FormatUtil.EN.toUpperCase());
					}else{						
						templateMaster.setLanguage(FormatUtil.TH.toUpperCase());
					}
				}
				templateMaster.setTemplateBodyKmobileTh(KMOBILE_MESSAGE_TH);
				templateMaster.setTemplateBodyKmobileEn(KMOBILE_MESSAGE_EN);
				templateMaster.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateName(TEMPLATE_NAME);
				templateMaster.setContactPoint(CONTACT_POINT);
				templateMaster.setProductType(PRODUCT_TYPE);
				templateMaster.setTemplateType(templateBuilderRequest.getTemplateType());
				templateMaster.setAlertMessageTh(KMOBILE_ALERT_MESSAGE_TH);
				templateMaster.setAlertMessageEn(KMOBILE_ALERT_MESSAGE_EN);
			}	
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		 return templateMaster;
	}
	public TemplateMasterDataM getRejectLetterTemplateBundle(TemplateBuilderRequest templateBuilderRequest, String product) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TemplateMasterDataM  templateMaster  = new TemplateMasterDataM();
		RejectLetterProcessDataM   rejectLetterProcessDataM = (RejectLetterProcessDataM)templateBuilderRequest.getRequestObject();
		String templateCode = rejectLetterProcessDataM.getTemplateCode();
		if(!ServiceUtil.empty(rejectLetterProcessDataM.getLanguage()) && !rejectLetterProcessDataM.getLanguage().equals(RejectLetterUtil.TH)){
			rejectLetterProcessDataM.setLanguage(RejectLetterUtil.EN);
		}
		String language=rejectLetterProcessDataM.getLanguage();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			//sub query
//			sql.append("select * ");
//			sql.append("from MS_REJECT_LETTER_TEMPLATE ");
//			sql.append("where TEMPLATE_ID=( ");
//			sql.append("	select LINK_TEMPLATE_ID ");
//			sql.append("	from MS_REJECT_LETTER_TEMPLATE ");
//			sql.append("	where PRODUCT=? and TEMPLATE_CODE=? and LANGUAGE=?)");
			//join
			sql.append("select MAIN.APPLY_TYPE,main.DETAIL1,main.DETAIL2,main.DETAIL3,main.LANGUAGE,main.POST_SCRIPT1,main.POST_SCRIPT2,main.PRODUCT,main.REMARK1,link.TEMPLATE_CODE,link.TEMPLATE_ID,main.TEMPLATE_NAME,main.TEMPLATE_TYPE ");
			sql.append("from ");
			sql.append("	MS_REJECT_LETTER_TEMPLATE link ");
			sql.append("	join MS_REJECT_LETTER_TEMPLATE main on link.LINK_TEMPLATE_ID=main.TEMPLATE_ID ");
			sql.append("where link.PRODUCT=? and link.TEMPLATE_CODE=? and link.LANGUAGE=?");
			ps = conn.prepareStatement(sql.toString());
			int index=1;
			ps.setString(index++,product);
			ps.setString(index++,templateCode);
			ps.setString(index++,language);
			rs = ps.executeQuery();			
			if(rs.next()) {
				String APPLY_TYPE = rs.getString("APPLY_TYPE");
				String DETAIL1 = rs.getString("DETAIL1");
				String DETAIL2 = rs.getString("DETAIL2");
				String DETAIL3 = rs.getString("DETAIL3");
				String LANGUAGE = rs.getString("LANGUAGE");
				String POST_SCRIPT1 = rs.getString("POST_SCRIPT1");
				String POST_SCRIPT2 = rs.getString("POST_SCRIPT2");
				String PRODUCT = rs.getString("PRODUCT");
				String REMARK1 = rs.getString("REMARK1");
				String TEMPLATE_CODE = templateCode;//rs.getString("TEMPLATE_CODE");
				String TEMPLATE_ID = rs.getString("TEMPLATE_ID");
				String TEMPLATE_NAME = rs.getString("TEMPLATE_NAME");
				String REJECT_TEMPLATE_TYPE = rs.getString("TEMPLATE_TYPE");
				 				
				templateBuilderRequest.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateType(templateBuilderRequest.getTemplateType());
				templateMaster.setApplyType(APPLY_TYPE);
				templateMaster.setDetail1(DETAIL1);
				templateMaster.setDetail2(DETAIL2);
				templateMaster.setDetail3(DETAIL3);
				templateMaster.setLanguage(LANGUAGE);
				templateMaster.setPostScript1(POST_SCRIPT1);
				templateMaster.setPostScript2(POST_SCRIPT2);
//				templateMaster.setRejectLetterProduct(PRODUCT);
				templateMaster.setRejectLetterProduct(rejectLetterProcessDataM.getProduct());
				templateMaster.setRemark1(REMARK1);
				templateMaster.setTemplateCode(TEMPLATE_CODE);
				templateMaster.setTemplateId(TEMPLATE_ID);
				templateMaster.setTemplateName(TEMPLATE_NAME);
				templateMaster.setRejectLetterTemplateType(REJECT_TEMPLATE_TYPE);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return templateMaster;
	}	
}
