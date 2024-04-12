package com.eaf.orig.ulo.pl.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.DocumentReasonProperties;
import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.cache.properties.ProductProperties;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.EmailTemplateMasterM;
import com.eaf.orig.shared.model.SMSDataM;
import com.eaf.orig.shared.model.SMSPrepareDataM;
import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;

public class PLOrigEmailSMSDAOImpl extends OrigObjectDAO implements PLOrigEmailSMSDAO {
	
	Logger log = Logger.getLogger(this.getClass().getName());

	@Override
	public String getEMailTemplateMasterValue(String emailTemplateID, String templateType)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select content from ms_email_template_master where template_name = ? and template_type = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getEMailTemplateMasterValue Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, emailTemplateID);
			ps.setString(2, templateType);
			rs = ps.executeQuery();
			java.sql.Clob contentValue = null;
			if (rs.next()) {
				contentValue = rs.getClob("content");
				if(null != contentValue){
					result = contentValue.getSubString(1,(int)contentValue.length());
				}else{
					log.error("##### Found content clob but not found content details");
				}
			}			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return result;
	}
	
	public ProductProperties getProductData(String productCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductProperties productM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select p.product_code, p.product_desc  from MS_PRODUCT p where product_code = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getProductData Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				productM = new ProductProperties();
				productM.setCode(rs.getString("product_code"));
				productM.setThDesc(rs.getString("product_desc"));
				productM.setEnDesc(rs.getString("product_desc"));
			}			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return productM;
	}

	@Override
	public GeneralParamProperties getGeneralParamData(String paramCode)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		GeneralParamProperties generalParamM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select gp.param_code, gp.param_value, gp.param_value2 from general_param gp where gp.param_code = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getGeneralParamData Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, paramCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				generalParamM = new GeneralParamProperties();
				generalParamM.setCode(rs.getString("param_code"));
				generalParamM.setParamvalue(rs.getString("param_value"));
				generalParamM.setParamvalue2(rs.getString("param_value2"));
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return generalParamM;		
	}

	@Override
	public CacheDataM getDocumentData(String documentCode)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		CacheDataM docM = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT doc.document_code , doc.th_desc, doc.en_desc FROM MS_DOC_LIST doc where doc.document_code = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getDocumentData Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, documentCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				docM = new CacheDataM();
				docM.setCode(rs.getString("document_code"));
				docM.setThDesc(rs.getString("th_desc"));
				docM.setEnDesc(rs.getString("en_desc"));
			}			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	

		return docM;
	}

	@Override
	public DocumentReasonProperties getDocReasonData(String documentCode, String reasonCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		DocumentReasonProperties docReasonM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DLR.DOCUMENT_CODE, DLR.DOC_LIST_REASON_ID, DLR.DOC_LIST_REASON_DESC ");
			sql.append("FROM MS_DOC_LIST_REASON DLR WHERE DLR.DOCUMENT_CODE = ? AND DLR.DOC_LIST_REASON_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getDocReasonData Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, documentCode);
			ps.setString(2, reasonCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				docReasonM = new DocumentReasonProperties();
				docReasonM.setDocCode(rs.getString("DOCUMENT_CODE"));
				docReasonM.setReasonID(rs.getString("DOC_LIST_REASON_ID"));
				docReasonM.setReasonDesc(rs.getString("DOC_LIST_REASON_DESC"));
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	

		return docReasonM;
		
	}

	@Override
	public String getKBANKEmployeeEmail(String branchCode,String companyTitleCode) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String emails = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_kbank_employee_email(?,?) emp_email from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getKBANKEmployeeEmail Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, branchCode);
			ps.setString(2, companyTitleCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				emails = rs.getString("emp_email");
			}			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	

		return emails;
	}
	
	@Override
	public String getKBANKEmployeeEmails(String branchCode,String busClass) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String emails = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_kbank_employee_emails(?,?) emp_email from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getKBANKEmployeeEmail Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, branchCode);
			ps.setString(2, busClass);
			rs = ps.executeQuery();
			if (rs.next()) {
				emails = rs.getString("emp_email");
			}			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return emails;
	}

	@Override
	public void createOrigContactLog(PLOrigContactDataM contactM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
//			log.debug("@@@@@ [createOrigContactLog] appRecordId:"+contactM.getApplicationRecordId());
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into orig_contact_log ");
			sql.append("(contact_log_id,application_record_id,send_to,subject,message,cc_to,send_by,send_date,");
			sql.append("send_status,create_by,create_date,contact_type, template_name) ");
			sql.append("values (?,?,?,?,?,?,?,sysdate,?,?,sysdate,?,?)");
		
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ createOrigContactLog Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
//			log.debug("@@@@@ contactM.getContactLogId():" + contactM.getContactLogId());
			
			int index = 1;
			ps.setInt(index++, contactM.getContactLogId());
			ps.setString(index++, contactM.getApplicationRecordId());
			if(OrigConstant.EmailSMS.CONTACT_TYPE_EMAIL.equals(contactM.getContactType())){
				EmailM mailM = contactM.getEmail();
				ps.setString(index++, mailM.getTo());
				ps.setString(index++, mailM.getSubject());
				ps.setString(index++, mailM.getContent());
				ps.setString(index++, mailM.getCcTo());
			}else if(OrigConstant.EmailSMS.CONTACT_TYPE_SMS.equals(contactM.getContactType())){
				SMSDataM smsM = contactM.getSms();
				ps.setString(index++, smsM.getNumber());
				ps.setString(index++, smsM.getCode());
				ps.setString(index++, smsM.getContent());
				ps.setString(index++, null);
			}
			ps.setString(index++, contactM.getSendBy());
			ps.setString(index++, contactM.getSendStatus());
			ps.setString(index++, contactM.getCreateBy());
			ps.setString(index++, contactM.getContactType());
			ps.setString(index++, contactM.getTemplateName());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			e.printStackTrace();
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	public int countOrigContactLog(String appRecordId, String templateName) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int templateCount = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select count(1) template_count from orig_contact_log ocl where ocl.application_record_id = ? and ocl.template_name = ? ");
			String dSql = String.valueOf(sql);
			log.debug("SQL " + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordId);
			ps.setString(2, templateName);
			rs = ps.executeQuery();
			if (rs.next()) {
				templateCount = rs.getInt("template_count");
			}			
			
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			return templateCount;
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return templateCount;
	}

	@Override
	public String getSMSTemplateMasterValue(String smsTemplateCode, String nationality, String busClass) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			if(OrigConstant.NATION_THAI.equals(nationality)){
				sql.append("select sms.message_th sms_message ");
			}else{
				sql.append("select sms.message_en sms_message ");
			}
			sql.append("from ms_sms_template_master sms ");
			sql.append("inner join bus_class_grp_map bgm on bgm.bus_grp_id = sms.buss_class ");
			sql.append("inner join business_class bc on bc.bus_class_id = bgm.bus_class_id ");
			sql.append("where sms.template_name = ? and sms.enabled = ? and bc.bus_class_id = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getSMSTemplateMasterValue Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, smsTemplateCode);
			ps.setString(2, OrigConstant.FLAG_Y);
			ps.setString(3, busClass);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("sms_message");
			}			
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public String getKBANKSendToEmails(String appId, String channelCode,
									   String saleId, String branchCode, 
									   String refId, String busClass, String emailTemplate) throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		String emails = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_email_util.get_kbank_send_to_email(?,?,?,?,?,?,?) send_to_email from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getKBANKSendToEmails Sql=" + dSql + "|appId["+appId+"]channelCode["+channelCode+"]saleId["+saleId+"]refid["+refId+"]branchCode["+branchCode+"]busClass["+busClass+"]emailTemplate["+emailTemplate+"]");
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appId);
			ps.setString(2, channelCode);
			ps.setString(3, saleId);
			ps.setString(4, branchCode);
			ps.setString(5, refId);
			ps.setString(6, busClass);
			ps.setString(7, emailTemplate);
			rs = ps.executeQuery();
			if (rs.next()) {
				emails = rs.getString("send_to_email");
			}			
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return emails;
	}

	@Override
	public String getFollowUpAutoEmailSendTo(String appId)
			throws PLOrigApplicationException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		String emails = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_fu_util.get_fu_auto_email_sendTo_email(?) send_to_email from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getFollowUpAutoEmailSendTo Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appId);
			rs = ps.executeQuery();
			if (rs.next()) {
				emails = rs.getString("send_to_email");
			}			
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return emails;
	}

	@Override
	public EmailM getSendFromByUser(String userName) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		EmailM emailM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_kbank_user_name_surname(?) as fromName, pka_email_util.get_kbank_user_email(?) as fromEmail from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getSendFromByUser Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);
			ps.setString(2, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				emailM = new EmailM();
				emailM.setFromName(rs.getString("fromName"));
				emailM.setFrom(rs.getString("fromEmail"));
			}			
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return emailM;
	}

	@Override
	public EmailM getSendFromByOwner(String appId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		EmailM emailM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_kbank_user_name_surname(wq.owner) as fromName, pka_email_util.get_kbank_user_email(wq.owner) as fromEmail ");
			sql.append("from orig_application app ");
			sql.append("inner join wf_jobid_mapping jm on jm.application_record_id = app.application_record_id ");
			sql.append("inner join wf_work_queue wq on wq.job_id = jm.job_id ");
			sql.append("where app.application_record_id = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getSendFromByOwner Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appId);
			rs = ps.executeQuery();
			if (rs.next()) {
				emailM = new EmailM();
				emailM.setFromName(rs.getString("fromName"));
				emailM.setFrom(rs.getString("fromEmail"));
			}			
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return emailM;
	}

	@Override
	public int getContactLogID() throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int contactLogID = 0;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select contact_log_id_seq.Nextval contactLogID from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getContactLogID Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();
			if(rs.next()){
				contactLogID = rs.getInt("contactLogID");
			}							
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return contactLogID;
	}

	@Override
	public void createServiceEmailSMSQLog(ServiceEmailSMSQLogM emailSMSQLogM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
//			log.debug("@@@@@ [createServiceEmailSMSQLog] contacLogID:" + emailSMSQLogM.getContactLogID());
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("call PKA_MESSAGE_QUEUE.insert_sevice_email_sms_q_log(?,?,?,?,?)");
		
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ createOrigContactLog Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setInt(index++, emailSMSQLogM.getContactLogID());
			ps.setString(index++, emailSMSQLogM.getServiceType());
			ps.setString(index++, emailSMSQLogM.getLogType());
			ps.setString(index++, emailSMSQLogM.getLogDesc());
			ps.setString(index++, emailSMSQLogM.getCreateBy());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public int getEmailSMSQCount(int contactLogID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int cnt = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select q.queue_count from service_email_sms_q q where q.contact_log_id = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getContactLogID Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, contactLogID);
			rs = ps.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt("queue_count");
			}			
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return cnt;
	}

	@Override
	public void increaseEmailSMSQCount(int contactLogID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("update service_email_sms_q q set q.queue_count = q.queue_count + 1 where q.contact_log_id = ?");
			String dSql = String.valueOf(sql);
			log.debug("increaseEmailSMSQCount Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setInt(1, contactLogID);
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void deleteEmailSMSQueue(int contactLogID) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("delete from service_email_sms_q q where q.contact_log_id = ?");
			String dSql = String.valueOf(sql);
			log.debug("deleteEmailSMSQueue Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setInt(1, contactLogID);
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public EmailTemplateMasterM getEMailTemplateMaster(String emailTemplateID, String busClass) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		EmailTemplateMasterM templateM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select em.* from ms_email_template_master em ");
			sql.append("inner join bus_class_grp_map bgm on bgm.bus_grp_id = em.bus_grp_id ");
			sql.append("inner join business_class bc on bc.bus_class_id = bgm.bus_class_id ");
			sql.append("where em.template_name = ? and  bc.bus_class_id = ?");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getEMailTemplateMaster Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, emailTemplateID);
			ps.setString(2, busClass);
			rs = ps.executeQuery();
			java.sql.Clob contentValue = null;
			java.sql.Clob subjectValue = null;
			if (rs.next()) {
				templateM = new EmailTemplateMasterM();
				templateM.setTemplateId(rs.getInt("TEMPLATE_ID"));
				templateM.setTemplateName(rs.getString("TEMPLATE_NAME"));
				
				subjectValue = rs.getClob("SUBJECT");
				if(null != subjectValue){
					templateM.setSubject(subjectValue.getSubString(1,(int)subjectValue.length()));
				}
				contentValue = rs.getClob("CONTENT");
				if(null != contentValue){
					templateM.setContent(contentValue.getSubString(1,(int)contentValue.length()));
				}
				
				templateM.setBusGroup(rs.getString("BUS_GRP_ID"));
				templateM.setEnable(rs.getString("ENABLED"));
				templateM.setCreateBy(rs.getString("CREATE_BY"));
				templateM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				templateM.setUpdateBy(rs.getString("UPDATE_BY"));
				templateM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return templateM;
	}

	@Override
	public void insertFollowDetail(PLXRulesFUVerificationDataM xrulesFuVerM) throws PLOrigApplicationException {
		PreparedStatement ps = null;		
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("insert into xrules_fu_verification(personal_id, seq, phone_no, phone_ver_status, create_date, create_by, update_date, update_by) ");
			sql.append("values (?,?,?,?,?,?,?,?)");
		
			String dSql = String.valueOf(sql);
			log.debug("SQL >> " + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, xrulesFuVerM.getPersonalID());
			ps.setInt(index++, xrulesFuVerM.getSeq());
			ps.setString(index++, xrulesFuVerM.getPhoneNo());
			ps.setString(index++, xrulesFuVerM.getPhoneVerStatus());
			
			ps.setTimestamp(index++, xrulesFuVerM.getCreateDate());
			ps.setString(index++, xrulesFuVerM.getCreateBy());
			
			ps.setTimestamp(index++, xrulesFuVerM.getUpdateDate());
			ps.setString(index++, xrulesFuVerM.getUpdateBy());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public String getKBankUserNameSurname(String userName) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select pka_app_util.get_kbank_user_name_surname(?) as name_surname from dual");
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getKBankUserNameSurname Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("name_surname");
			}
			if(result == null) result = "";
			
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return result;
	}

	@Override
	public int getFollowUpSLA(String appId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int sla = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PKA_FU_UTIL.FU_SLA(?) FU_SLA FROM DUAL");
			String dSql = String.valueOf(sql);
			
//			log.debug("@@@@@ getFollowUpSLA Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appId);
			rs = ps.executeQuery();
			if (rs.next()) {
				sla = rs.getInt("fu_sla");
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}	
		return sla;
	}

	@Override
	public SMSPrepareDataM prepareSMSData(String appId, String templateName) throws PLOrigApplicationException {
		CallableStatement callStmt = null;
		ResultSet rs = null;
		SMSPrepareDataM smsPrepareM = null;
		Connection conn = null;
		try{
			conn = getConnection();
			callStmt = conn.prepareCall("{? = call pka_sms.prepareSMS(?,?)}");		
			callStmt.registerOutParameter(1, OracleTypes.CURSOR);
			callStmt.setString(2, appId);
			callStmt.setString(3, templateName);
			callStmt.execute();
			
			rs = (ResultSet)callStmt.getObject(1);
			
			if (rs.next()) {
				smsPrepareM = new SMSPrepareDataM();
				smsPrepareM.setTemplateName(rs.getString("template_name"));
				smsPrepareM.setEnableFlag(rs.getString("enabled"));
				smsPrepareM.setMobile(rs.getString("mobile"));
				smsPrepareM.setMessage(rs.getString("message"));
				smsPrepareM.setRefNo(rs.getString("ref_no"));
				smsPrepareM.setFinalCreditLimit(rs.getBigDecimal("final_credit_limit"));
			}
			
			return smsPrepareM;
		} catch(Exception e){			
			logger.fatal("prepareSMSData  Error "+e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			if (callStmt != null){
				try{
					callStmt.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
