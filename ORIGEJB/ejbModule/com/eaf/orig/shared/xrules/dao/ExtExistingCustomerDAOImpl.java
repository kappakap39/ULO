/*
 * Created on Oct 24, 2007
 * Created by Sankom Sanpunya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.xrules.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.xrules.dao.exception.ExtExistingCustomerException;
import com.eaf.xrules.shared.constant.XRulesConstant;
import com.eaf.xrules.shared.model.XRulesDataM;
import com.eaf.xrules.shared.model.XRulesExistcustDataM;
import com.eaf.xrules.shared.model.XRulesExistcustInprogressDataM;
import java.math.BigDecimal;

/**
 * @author Sankom Sanpunya
 *
 * Type: ExtExistingCustomerDAOImpl
 */
public class ExtExistingCustomerDAOImpl extends OrigObjectDAO implements
		ExtExistingCustomerDAO {
	private static Logger log = Logger.getLogger(ExtExistingCustomerDAOImpl.class);
	/**
	 * 
	 */
	public ExtExistingCustomerDAOImpl() {
		super();
	}

	 
    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerInprogressEXTHire(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerInprogressEXTHire(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersInprogressExtHire = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();	
	    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" SELECT applno,appldte,finamt,aprvsts,(INSTAMT+INSTVAT) ");			 
			sql.append(" FROM HPAPMS00  ");
			sql.append(" WHERE APRVSTS in('E','L','H','F','D') ");
			sql.append(" and CMPCDE=? and  IDNO=?  ");			 			 
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("IdNO= " + idNo);
			log.debug("getExistCustomer Inprogress Ext Hire ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,idNo);		 					  
			rs = ps.executeQuery();			
			int seq = 1;
			while (rs.next()) {
				XRulesExistcustInprogressDataM xRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
				xRulesExistcustInprogressDataM.setApplicationNo(rs.getString(1));
				xRulesExistcustInprogressDataM.setCustomerType("H");				
				xRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(2));
				xRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(3));
				xRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(4));	
				xRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(5));
				log.debug("ExtExistCustomer Ext Hire  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustInprogressDataM.setSeq(seq++);  				
				vExistCustomersInprogressExtHire.add(xRulesExistcustInprogressDataM);
			}
			return vExistCustomersInprogressExtHire;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }
    public Vector getExisitingCustomerInprogressEXTGuarantor(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersInprogressExtGuarantor = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" SELECT inp.applno,inp.appldte,inp.finamt,inp.aprvsts,(inp.INSTAMT+inp.INSTVAT) ");			 
			sql.append(" FROM hpapms00 inp ,hpapms09 gur  ");
			sql.append(" WHERE  inp.APPLNO=gur.APPLNO and inp.APRVSTS in('E','L','H','F','D') ");
			sql.append(" and inp.CMPCDE=? and  gur.GTYIDNO=?  ");			 			 
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("IdNO= " + idNo);
			log.debug("getExistCustomer Inprogress  Ext Guarantor ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,idNo);		 					  
			rs = ps.executeQuery();			
			int seq = 1;
			while (rs.next()) {
				XRulesExistcustInprogressDataM xRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
				xRulesExistcustInprogressDataM.setApplicationNo(rs.getString(1));
				xRulesExistcustInprogressDataM.setCustomerType("G");				
				xRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(2));
				xRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(3));
				xRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(4));
				xRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(5));
				log.debug("ExtExistCustomer Inprogress Ext Guarantor  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustInprogressDataM.setSeq(seq++);  				
				vExistCustomersInprogressExtGuarantor.add(xRulesExistcustInprogressDataM);
			}
			return vExistCustomersInprogressExtGuarantor;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }
    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerInprogressOrig(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerInprogressOrig(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersOrig = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		
	    String appRecordID=xRulesDataM.getApplicationRecordID();
	    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>> getExisitingCustomerInprogressOrig <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();			 
			sql.append(" SELECT app.APPLICATION_NO,cust.PERSONAL_TYPE ,app.CREATE_DATE,loan.COST_OF_FINANCIAL_AMT,app.APPLICATION_STATUS,loan.TOTAL_OF_INSTALLMENT1,loan.installment_flag,app.APPLICATION_RECORD_ID,cust.COBORROWER_FLAG ");			 
			sql.append(" FROM ORIG_APPLICATION app,ORIG_PERSONAL_INFO cust,orig_loan loan ");
			sql.append(" WHERE app.APPLICATION_RECORD_ID=cust.APPLICATION_RECORD_ID and app.APPLICATION_RECORD_ID=loan.APPLICATION_RECORD_ID(+) ");
			sql.append(" and cust.IDNO=?  ");			 
			sql.append(" and app.JOB_STATE IN ("+XRulesConstant.JOBSTATE_PROGRESS_APPLICATION_SQL+ ")     ");
			if(appRecordID!=null && !("".equals(appRecordID))){
				  sql.append(" and  app.APPLICATION_RECORD_ID != ? ");
				}
			String dSql = sql.toString();    
			log.debug("getExistCustomer Orig ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);			 
			ps.setString(1,idNo); 
			if(appRecordID!=null && !("".equals(appRecordID))){
			    ps.setString(2,appRecordID);
			}
			rs = ps.executeQuery();			
			int seq = 1;
			while (rs.next()) {
				XRulesExistcustInprogressDataM xRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
				xRulesExistcustInprogressDataM.setApplicationNo(rs.getString(1));				 				
				xRulesExistcustInprogressDataM.setCustomerType(rs.getString(2));				
				xRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(3));
				xRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(4));
				xRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(5));	
				xRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(6));
				xRulesExistcustInprogressDataM.setInstallmentFlag(rs.getString(7));
				xRulesExistcustInprogressDataM.setApplicationRecordId(rs.getString(8));
				xRulesExistcustInprogressDataM.setCoBorrowerFlag(rs.getString(9));
				log.debug("ExtExistCustomer Orig  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustInprogressDataM.setSeq(seq++);  				
				vExistCustomersOrig.add(xRulesExistcustInprogressDataM);
			}
			return vExistCustomersOrig;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerBookHire(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerBookedHire(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersBookExtHire = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" SELECT hpmshp05.CONTNO,hpmshp05.APPLDTE,(hpmshp05.OUTSBAL-hpmshp05.VATBAL-hpmshp05.UNREALINC) \"Net FinanceAMT\",hpmshp05.CONTSTS,hpmshp06.COLSTS,hpmshp06.OVDPER,hpmshp06.INSTPAID,(hpmshp05.TERM-hpmshp06.INSTPAID) \"UNPAID Period\" ,(hpmshp05.INSTAMT+hpmshp05.INSTVAT),hpmshp05.FINAMT ");			 
			sql.append(" FROM  hpmshp05   ,hpmshp06  ");
			sql.append(" WHERE hpmshp05.CONTNO=hpmshp06.CONTNO ");
			sql.append(" and hpmshp05.CMPCDE=? and  hpmshp05.IDNO=?  ");	
			sql.append(" ORDER BY hpmshp05.CONTSTS ");
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("IdNO= " + idNo);
			log.debug("getExistCustomer booked Ext Hire ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,idNo);		 					  
			rs = ps.executeQuery();			
			int seq = 1;
			while (rs.next()) {
				XRulesExistcustDataM xRulesExistcustDataM = new XRulesExistcustDataM();
				xRulesExistcustDataM.setContractNo(rs.getString(1));
				xRulesExistcustDataM.setCustomerType("H");				
				xRulesExistcustDataM.setBookingDate(rs.getDate(2));
				xRulesExistcustDataM.setNetFinanceAmount(rs.getBigDecimal(3));
				xRulesExistcustDataM.setContractStatus(rs.getString(4));
				xRulesExistcustDataM.setCollectionStatus(rs.getString(5));
				xRulesExistcustDataM.setOverDuePeriod(rs.getInt(6));
				xRulesExistcustDataM.setPaidPeriod(rs.getInt(7));
				xRulesExistcustDataM.setUnPaidPeriod(rs.getInt(8));
				xRulesExistcustDataM.setInstallment(rs.getBigDecimal(9));
				xRulesExistcustDataM.setOriginalFinaceAmount(rs.getBigDecimal(10));
				log.debug("ExtExistCustomer Booked Ext Hire  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustDataM.setSeq(seq++);  			
				vExistCustomersBookExtHire.add(xRulesExistcustDataM);
			}
			return vExistCustomersBookExtHire;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerBookGaruntor(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerBookedGuarantor(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersBookExtGuarantor = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" select  hpmshp05.CONTNO,hpmshp05.APPLDTE,(hpmshp05.OUTSBAL-hpmshp05.VATBAL-hpmshp05.UNREALINC) \"Net FinanceAMT\",hpmshp05.CONTSTS,hpmshp06.COLSTS,hpmshp06.OVDPER,hpmshp06.INSTPAID,(hpmshp05.TERM-hpmshp06.INSTPAID) \"UNPAID Period\" ,(hpmshp05.INSTAMT+hpmshp05.INSTVAT),hpmshp05.FINAMT ");			 
			sql.append(" FROM  hpmshp05,hpmshp06,hpmshp00,hpmshp09  ");
			sql.append(" WHERE hpmshp00.CUSTNO=hpmshp09.GTYCUSTNO and hpmshp09.CONTNO=hpmshp05.CONTNO and hpmshp05.CONTNO=hpmshp06.CONTNO ");
			sql.append(" and hpmshp00.CMPCDE=? and  hpmshp00.IDNO=?  ");	
			sql.append(" ORDER BY hpmshp05.CONTSTS ");
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("IdNO= " + idNo);
			log.debug("getExistCustomer booked Ext Guarantor ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,idNo);		 					  
			rs = ps.executeQuery();			
			int seq = 1;
			while (rs.next()) {
				XRulesExistcustDataM xRulesExistcustDataM = new XRulesExistcustDataM();
				xRulesExistcustDataM.setContractNo(rs.getString(1));
				xRulesExistcustDataM.setCustomerType("G");				
				xRulesExistcustDataM.setBookingDate(rs.getDate(2));
				xRulesExistcustDataM.setNetFinanceAmount(rs.getBigDecimal(3));
				xRulesExistcustDataM.setContractStatus(rs.getString(4));
				xRulesExistcustDataM.setCollectionStatus(rs.getString(5));
				xRulesExistcustDataM.setOverDuePeriod(rs.getInt(6));
				xRulesExistcustDataM.setPaidPeriod(rs.getInt(7));
				xRulesExistcustDataM.setUnPaidPeriod(rs.getInt(8));
				xRulesExistcustDataM.setInstallment(rs.getBigDecimal(9));
				xRulesExistcustDataM.setOriginalFinaceAmount(rs.getBigDecimal(10));
				log.debug("ExtExistCustomer Booked Ext Guarantor  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustDataM.setSeq(seq++);  		 								
				vExistCustomersBookExtGuarantor.add(xRulesExistcustDataM);
			}
			return vExistCustomersBookExtGuarantor;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getInstallmentApplicationBooked(java.lang.String)
     */
    public BigDecimal getInstallmentApplicationBooked(String contractNo) throws ExtExistingCustomerException {        
        Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal result=new BigDecimal(0); 	    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" select q1.CONTNO,round(sum(q1.unpaidAmt)/sum(q1.unpaid),2) installmentAVG  from ");			 
			sql.append("  (  select cont.CONTNO,inst.INSTSEQ,inst.INSTFROM,inst.INSTTO,cont.INSTPAID,(inst.INSTAMT+inst.INSTVAT) installment ,( instto-instpaid )  unpaid,(inst.INSTAMT+inst.INSTVAT) * (instto-instpaid) unpaidAmt from hpmshp08 inst,hpmshp06 cont where cont.CONTNO=inst.CONTNO  ");			 	
			sql.append(" and (cont.INSTPAID>=inst.INSTFROM  and  cont.INSTPAID<inst.INSTTO) ");
			sql.append(" and   cont.contno=? ");			
			sql.append(" union select cont.CONTNO,inst.INSTSEQ,inst.INSTFROM,inst.INSTTO,cont.INSTPAID,(inst.INSTAMT+inst.INSTVAT) installment ,( instto-instfrom+1 )  unpaid,(inst.INSTAMT+inst.INSTVAT) * (instto-instfrom+1 ) unpaidAmt from hpmshp08 inst,hpmshp06 cont where cont.CONTNO=inst.CONTNO  ");
			sql.append(" and (cont.INSTPAID<=inst.INSTFROM  )   ");	
			sql.append(" and   cont.contno=? ");
			sql.append(" ) q1 group by  q1.contno");
			String dSql = sql.toString();    		 
			log.debug("getInstallment Booked... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,contractNo);
			ps.setString(2,contractNo);	
			rs = ps.executeQuery();					 
			if (rs.next()) {				 
			    result=rs.getBigDecimal(2);												 
			}
			return result;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
     
    }
    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getInstallmentApplicationInprogress(java.lang.String)
     */
    public BigDecimal getInstallmentApplicationInprogress(String applicationNo) throws ExtExistingCustomerException {
        Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal result=new BigDecimal(0); 	    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append("  select q1.applno,round(sum(q1.unpaidAmt)/sum(q1.unpaid),2) installmentAVG  from (");			 
			sql.append(" select inst.applno,inst.INSTSEQ,inst.INSTFROM,inst.INSTTO, (inst.INSTAMT+inst.INSTVAT) installment ,( instto-instfrom+1 )  unpaid,(inst.INSTAMT+inst.INSTVAT) * (instto-instfrom+1 ) unpaidAmt  ");			 	
			sql.append("from hpapms05 inst  where inst.applno =? ");
			sql.append(" ) q1 group by applno");			
			String dSql = sql.toString();    		 
			log.debug("getInstallment Inprogress Orig ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,applicationNo);			 	 					  
			rs = ps.executeQuery();						 
			if (rs.next()) {				 
			    result=rs.getBigDecimal(2);												 
			}
			return result;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }
    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getInstallmentApplicationInprogressOrig(java.lang.String)
     */
    public BigDecimal getInstallmentApplicationInprogressOrig(String applicationRecordId) throws ExtExistingCustomerException {
        Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal result=new BigDecimal(0); 	    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" SELECT   application_record_id,ROUND (SUM (installment_amt * term_duration) / SUM (term_duration),2) installmentavg");			 
			sql.append(" FROM orig_installment inst  ");			 	
			sql.append(" GROUP BY application_record_id ");
			sql.append(" having application_record_id=?  ");			
			String dSql = sql.toString();    		 
			log.debug("getInstallment Inprogress Orig ... sql= " + sql);
			log.debug("application_record_id="+ applicationRecordId);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,applicationRecordId);			 	 					  
			rs = ps.executeQuery();			
			 
			if (rs.next()) {				 
			    result=rs.getBigDecimal(2);												 
			}
			return result;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }


    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerInprogressEXTHireMatchLastname(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerInprogressEXTHireMatchSurname(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersInprogressExtHire = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();	
	    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" SELECT applno,appldte,finamt,aprvsts,(INSTAMT+INSTVAT) ");	
			sql.append(" ,HPMSHP00.TITCDE ,HPMSHP00.THNAME,HPMSHP00.THSURN ");
			sql.append(" FROM HPAPMS00,HPMSHP00  ");
			sql.append(" WHERE APRVSTS in('E','L','H','F','D') ");
			sql.append(" and hpapms00.IDNO=HPMSHP00.IDNO ");
			sql.append(" and hpapms00.CMPCDE=? and  hpmshp00.THSURN=? ");
			sql.append(" and HPAPMS00.IDNO!= ?");
			 String surname=xRulesDataM.getThaiLname();
			    if(surname==null||"".equals(surname)){
			     log.debug("Surname is null or blank");
			    return vExistCustomersInprogressExtHire;    
			    }
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("surname= " + surname);
			log.debug("idno "+idNo);
			log.debug("getExistCustomer Inprogress Ext Hire  Surname ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,surname);		
			ps.setString(3,idNo);
			rs = ps.executeQuery();			
			int seq = 1;
			 int limitResult = XRulesConstant.limitResult;
			while (rs.next()) {
			    if(seq>limitResult){break;}
				XRulesExistcustInprogressDataM xRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
				xRulesExistcustInprogressDataM.setApplicationNo(rs.getString(1));
				xRulesExistcustInprogressDataM.setCustomerType("H");				
				xRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(2));
				xRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(3));
				xRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(4));	
				xRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(5));
				xRulesExistcustInprogressDataM.setTitleCode(rs.getString(6));
				xRulesExistcustInprogressDataM.setFirstName(rs.getString(7));
				xRulesExistcustInprogressDataM.setLastName(rs.getString(8));
				log.debug("ExtExistCustomer Ext Hire  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustInprogressDataM.setSeq(seq++);  				
				vExistCustomersInprogressExtHire.add(xRulesExistcustInprogressDataM);
			}
			return vExistCustomersInprogressExtHire;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }


    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerInprogressEXTGuarantorMatchLastname(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerInprogressEXTGuarantorMatchSurname(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersInprogressExtGuarantor = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		    
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();
			 String surname=xRulesDataM.getThaiLname();
			    if(surname==null||"".equals(surname)){
			     log.debug("Surname is null or blank");
			    return vExistCustomersInprogressExtGuarantor;    
			    }
			sql.append(" SELECT inp.applno,inp.appldte,inp.finamt,inp.aprvsts,(inp.INSTAMT+inp.INSTVAT) ");		
			sql.append(" ,cust.TITCDE ,cust.THNAME,cust.THSURN ");
			sql.append(" FROM hpapms00 inp ,hpapms09 gur,hpmshp00 cust ");
			sql.append(" WHERE  inp.APPLNO=gur.APPLNO and inp.APRVSTS in('E','L','H','F','D') ");
			//sql.append(" and inp.CMPCDE=? and  gur.GTYIDNO=?  ");
			sql.append(" AND gur.GTYIDNO=cust.IdNO ");
			sql.append(" AND inp.CMPCDE=? and  cust.THSURN=?");	
			sql.append(" AND gur.GTYIDNO <> ? ");
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("surname= " + surname);
			log.debug("idno ="+idNo);
			log.debug("getExistCustomer Inprogress  Ext Guarantor Surname ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,surname);
			ps.setString(3,idNo);
			rs = ps.executeQuery();			
			int seq = 1;
			 int limitResult = XRulesConstant.limitResult;
			while (rs.next()) {
			    if(seq>limitResult){break;}
				XRulesExistcustInprogressDataM xRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
				xRulesExistcustInprogressDataM.setApplicationNo(rs.getString(1));
				xRulesExistcustInprogressDataM.setCustomerType("G");				
				xRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(2));
				xRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(3));
				xRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(4));
				xRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(5));
				xRulesExistcustInprogressDataM.setTitleCode(rs.getString(6));
				xRulesExistcustInprogressDataM.setFirstName(rs.getString(7));
				xRulesExistcustInprogressDataM.setLastName(rs.getString(8));
				log.debug("ExtExistCustomer Inprogress Ext Guarantor  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustInprogressDataM.setSeq(seq++);  				
				vExistCustomersInprogressExtGuarantor.add(xRulesExistcustInprogressDataM);
			}
			return vExistCustomersInprogressExtGuarantor;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }


    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerInprogressOrigMatchLastname(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerInprogressOrigMatchSurname(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersOrig = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		
	    String appRecordID=xRulesDataM.getApplicationRecordID();
	    String surname=xRulesDataM.getThaiLname();
	    if(surname==null||"".equals(surname)){
	     log.debug("Surname is null or blank");
	    return vExistCustomersOrig;    
	    }
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();			 
			sql.append(" SELECT app.APPLICATION_NO,cust.PERSONAL_TYPE ,app.CREATE_DATE,loan.COST_OF_FINANCIAL_AMT,app.APPLICATION_STATUS,loan.TOTAL_OF_INSTALLMENT1,loan.installment_flag,app.APPLICATION_RECORD_ID,cust.COBORROWER_FLAG ");
			sql.append(" ,HPMSHP00.TITCDE ,HPMSHP00.THNAME,HPMSHP00.THSURN ");
			sql.append(" FROM orig_application app,orig_application_customer cust,orig_loan loan,hpmshp00 ");
			sql.append(" WHERE app.APPLICATION_RECORD_ID=cust.APPLICATION_RECORD_ID and app.APPLICATION_RECORD_ID=loan.APPLICATION_RECORD_ID(+) ");
			//sql.append(" and cust.idno=?  ");	
			  
			sql.append(" AND cust.idno=hpmshp00.idno ");
			sql.append(" AND hpmshp00.THSURN=? ");
			sql.append(" AND cust.idno <> ? ");
			sql.append(" AND  app.JOB_STATE IN ("+XRulesConstant.JOBSTATE_PROGRESS_APPLICATION_SQL+ ")     ");
			if(appRecordID!=null && !("".equals(appRecordID))){
				  sql.append(" and  app.APPLICATION_RECORD_ID != ? ");
				}
			String dSql = sql.toString();    
			log.debug("getExistCustomer Orig  Surname ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);			 
			ps.setString(1,surname); 
			ps.setString(2,idNo);
			if(appRecordID!=null && !("".equals(appRecordID))){
			    ps.setString(3,appRecordID);
			}
			rs = ps.executeQuery();			
			int seq = 1;
			 int limitResult = XRulesConstant.limitResult;
			while (rs.next()) {
			    if(seq>limitResult){break;}
				XRulesExistcustInprogressDataM xRulesExistcustInprogressDataM = new XRulesExistcustInprogressDataM();
				xRulesExistcustInprogressDataM.setApplicationNo(rs.getString(1));				 				
				xRulesExistcustInprogressDataM.setCustomerType(rs.getString(2));				
				xRulesExistcustInprogressDataM.setApplicationDate(rs.getDate(3));
				xRulesExistcustInprogressDataM.setFinanceAmt(rs.getBigDecimal(4));
				xRulesExistcustInprogressDataM.setApplicationStatus(rs.getString(5));	
				xRulesExistcustInprogressDataM.setInstallment(rs.getBigDecimal(6));
				xRulesExistcustInprogressDataM.setInstallmentFlag(rs.getString(7));
				xRulesExistcustInprogressDataM.setApplicationRecordId(rs.getString(8));
				xRulesExistcustInprogressDataM.setCoBorrowerFlag(rs.getString(9));
				xRulesExistcustInprogressDataM.setTitleCode(rs.getString(10));
				xRulesExistcustInprogressDataM.setFirstName(rs.getString(11));
				xRulesExistcustInprogressDataM.setLastName(rs.getString(12));
				log.debug("ExtExistCustomer Orig  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustInprogressDataM.setSeq(seq++);  				
				vExistCustomersOrig.add(xRulesExistcustInprogressDataM);
			}
			return vExistCustomersOrig;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }


    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerBookedHireMatchLastname(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerBookedHireMatchSurname(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersBookExtHire = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();	
	    String surname=xRulesDataM.getThaiLname();
	    if(surname==null||"".equals(surname)){
	     log.debug("Surname is null or blank");
	    return vExistCustomersBookExtHire;    
	    }
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" SELECT hpmshp05.CONTNO,hpmshp05.APPLDTE,(hpmshp05.OUTSBAL-hpmshp05.VATBAL-hpmshp05.UNREALINC) \"Net FinanceAMT\",hpmshp05.CONTSTS,hpmshp06.COLSTS,hpmshp06.OVDPER,hpmshp06.INSTPAID,(hpmshp05.TERM-hpmshp06.INSTPAID) \"UNPAID Period\" ,(hpmshp05.INSTAMT+hpmshp05.INSTVAT),hpmshp05.FINAMT ");
			sql.append(" ,HPMSHP00.TITCDE ,HPMSHP00.THNAME,HPMSHP00.THSURN ");
			sql.append(" FROM  hpmshp05   ,hpmshp06,hpmshp00  ");
			sql.append(" WHERE hpmshp05.CONTNO=hpmshp06.CONTNO ");
			sql.append(" and hpmshp00.custno=hpmshp05.custno ");
			sql.append(" and hpmshp05.CMPCDE=? and  hpmshp00.THSURN=?  ");
			sql.append(" and hpmshp05.IDNO <> ? ");
			sql.append(" ORDER BY hpmshp05.CONTSTS ");
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("surname= " + surname);
			log.debug("idno= " + idNo);
			log.debug("getExistCustomer booked Ext Hire  Surname ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,surname);		
			ps.setString(3,idNo);
			rs = ps.executeQuery();			
			int seq = 1;
			 int limitResult = XRulesConstant.limitResult;
			while (rs.next()) {
			    if(seq>limitResult){break;}
				XRulesExistcustDataM xRulesExistcustDataM = new XRulesExistcustDataM();
				xRulesExistcustDataM.setContractNo(rs.getString(1));
				xRulesExistcustDataM.setCustomerType("H");				
				xRulesExistcustDataM.setBookingDate(rs.getDate(2));
				xRulesExistcustDataM.setNetFinanceAmount(rs.getBigDecimal(3));
				xRulesExistcustDataM.setContractStatus(rs.getString(4));
				xRulesExistcustDataM.setCollectionStatus(rs.getString(5));
				xRulesExistcustDataM.setOverDuePeriod(rs.getInt(6));
				xRulesExistcustDataM.setPaidPeriod(rs.getInt(7));
				xRulesExistcustDataM.setUnPaidPeriod(rs.getInt(8));
				xRulesExistcustDataM.setInstallment(rs.getBigDecimal(9));
				xRulesExistcustDataM.setOriginalFinaceAmount(rs.getBigDecimal(10));
				xRulesExistcustDataM.setTitleCode(rs.getString(11));
				xRulesExistcustDataM.setFirstName(rs.getString(12));
				xRulesExistcustDataM.setLastName(rs.getString(13));
				log.debug("ExtExistCustomer Booked Ext Hire  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustDataM.setSeq(seq++);  			
				vExistCustomersBookExtHire.add(xRulesExistcustDataM);
			}
			return vExistCustomersBookExtHire;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }


    /* (non-Javadoc)
     * @see com.eaf.orig.exint.xrules.dao.ExtExistingCustomerDAO#getExisitingCustomerBookedGuarantorMatchLastname(com.eaf.xrules.shared.model.XRulesDataM)
     */
    public Vector getExisitingCustomerBookedGuarantorMatchSurname(XRulesDataM xRulesDataM) throws ExtExistingCustomerException {
        Vector vExistCustomersBookExtGuarantor = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cmpCde=xRulesDataM.getCmpCde();
	    String idNo=xRulesDataM.getIdNo();		    
	    String surname=xRulesDataM.getThaiLname();
	    if(surname==null||"".equals(surname)){
	     log.debug("Surname is null or blank");
	    return vExistCustomersBookExtGuarantor;    
	    }
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer();	
			sql.append(" select  hpmshp05.CONTNO,hpmshp05.APPLDTE,(hpmshp05.OUTSBAL-hpmshp05.VATBAL-hpmshp05.UNREALINC) \"Net FinanceAMT\",hpmshp05.CONTSTS,hpmshp06.COLSTS,hpmshp06.OVDPER,hpmshp06.INSTPAID,(hpmshp05.TERM-hpmshp06.INSTPAID) \"UNPAID Period\" ,(hpmshp05.INSTAMT+hpmshp05.INSTVAT),hpmshp05.FINAMT ");
			sql.append(" ,HPMSHP00.TITCDE ,HPMSHP00.THNAME,HPMSHP00.THSURN ");
			sql.append(" FROM  hpmshp05,hpmshp06,hpmshp00,hpmshp09  ");
			sql.append(" WHERE hpmshp00.CUSTNO=hpmshp09.GTYCUSTNO and hpmshp09.CONTNO=hpmshp05.CONTNO and hpmshp05.CONTNO=hpmshp06.CONTNO ");			 
			sql.append(" and hpmshp00.CMPCDE=? and  hpmshp00.THSURN=?  ");
			sql.append(" and hpmshp00.IDNO <> ? ");
			sql.append(" ORDER BY hpmshp05.CONTSTS ");
			String dSql = sql.toString();    
			log.debug("CMPCDE= " + cmpCde);
			log.debug("Surname = " + surname);
			log.debug("getExistCustomer booked Ext Guarantor  Surname ... sql= " + sql);					
			ps = conn.prepareStatement(dSql);
			ps.setString(1,cmpCde);
			ps.setString(2,surname);		 
			ps.setString(3,idNo);
			rs = ps.executeQuery();			
			int seq = 1;
			 int limitResult = XRulesConstant.limitResult;
			while (rs.next()) {
			    if(seq>limitResult){break;}
				XRulesExistcustDataM xRulesExistcustDataM = new XRulesExistcustDataM();
				xRulesExistcustDataM.setContractNo(rs.getString(1));
				xRulesExistcustDataM.setCustomerType("G");				
				xRulesExistcustDataM.setBookingDate(rs.getDate(2));
				xRulesExistcustDataM.setNetFinanceAmount(rs.getBigDecimal(3));
				xRulesExistcustDataM.setContractStatus(rs.getString(4));
				xRulesExistcustDataM.setCollectionStatus(rs.getString(5));
				xRulesExistcustDataM.setOverDuePeriod(rs.getInt(6));
				xRulesExistcustDataM.setPaidPeriod(rs.getInt(7));
				xRulesExistcustDataM.setUnPaidPeriod(rs.getInt(8));
				xRulesExistcustDataM.setInstallment(rs.getBigDecimal(9));
				xRulesExistcustDataM.setOriginalFinaceAmount(rs.getBigDecimal(10));
				log.debug("ExtExistCustomer Booked Ext Guarantor  Hit["+seq+"]" + rs.getString(1));
				xRulesExistcustDataM.setTitleCode(rs.getString(11));
				xRulesExistcustDataM.setFirstName(rs.getString(12));
				xRulesExistcustDataM.setLastName(rs.getString(13));
				xRulesExistcustDataM.setSeq(seq++);  		 								
				vExistCustomersBookExtGuarantor.add(xRulesExistcustDataM);
			}
			return vExistCustomersBookExtGuarantor;
		} catch (Exception e) {
		    log.error("Error:", e);
			throw new ExtExistingCustomerException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
			    log.error("Error:", e);
				throw new ExtExistingCustomerException(e.getMessage());
			}
		}
    }
}
