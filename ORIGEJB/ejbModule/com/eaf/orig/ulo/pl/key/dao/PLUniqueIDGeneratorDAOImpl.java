package com.eaf.orig.ulo.pl.key.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;

//import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
//import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
//import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

@Deprecated
public class PLUniqueIDGeneratorDAOImpl extends OrigObjectDAO implements PLUniqueIDGeneratorDAO {
	
	static Logger log = Logger.getLogger(PLUniqueIDGeneratorDAOImpl.class);

	@Override
	public String getUniqByName(String systemType) throws UniqueIDGeneratorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqByName(String systemType, int dbType) throws UniqueIDGeneratorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getApplicationNo(ApplicationDataM app)throws UniqueIDGeneratorException {
		
		log.debug("Old getApplicationNo");
		// TODO Auto-generated method stub
		return null;
			
	}
	
	private String getGenerateRunningNo(String paramID, String paramType) throws UniqueIDGeneratorException {
		log.debug("PL getGenerateRunningNo");
		java.math.BigDecimal testbig = null;
		try {
			log.debug("paramID = " + paramID);
			log.debug("paramType = " + paramType);
			//testbig = etest.getNextKeyAfterIncrementingBy(1);
			Calendar cl=Calendar.getInstance();
			//int month=cl.get(Calendar.MONTH)+1;
			int year=cl.get(Calendar.YEAR);
			testbig = this.getNextKeyAfterIncrementingByCompareValue2(paramID, paramType, 1, year);
			if (testbig.intValue() == 0) {
				throw new UniqueIDGeneratorException("SYSTEM ERR : Running Number(PARAM_ID = " + paramID + ", PARAM_TYPE" + paramType + ") ---> Out of range");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return testbig.toString();
		
		
//		return String.valueOf(UniqueRunningParamCache.getKey(paramID+paramType,1));
//		return this.getRunningFromSequence(paramID, paramType);

	}
	
	private BigDecimal getNextKeyAfterIncrementingByCompareValue2(String paramID, String paramType, int size,int value2) throws UniqueIDGeneratorException {
		log.debug("PL getNextKeyAfterIncrementingByCompareValue2");
		BigDecimal tmpID = new BigDecimal(0);
		int value_1 = 0;
		int value_2 = 0;
		int value_from = 0;
		int value_to = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VALUE_FROM, VALUE_TO, VALUE1, VALUE2 FROM RUNNING_PARAM WHERE PARAM_ID = ? AND PARAM_TYPE = ? FOR UPDATE");
			//sql.append(" UPDATE KEY SET ID = ID + 1 WHERE NAME = ?;");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
//			int increment = 1;
			ps.setString(1, paramID);
			ps.setString(2, paramType);
			log.debug("paramID = "+paramID+" paramType = "+paramType);
			rs = ps.executeQuery();
			if (rs.next()) {
				value_from = rs.getInt(1);
				value_to = rs.getInt(2);
				value_1 = rs.getInt(3);
				value_2 = rs.getInt(4);
			}
			
			if(value_2 != value2) {
				value_2 = value2;
				value_1 = 0;
			}
			 tmpID = new java.math.BigDecimal( value_from + value_1 + size);        
				//check from+value1 < value to
			if(!(tmpID.intValue() > value_to)){			
				//this.setValue1(new java.math.BigDecimal( value_1 + size));			 
				value_1 = value_1 + size;
			}else{
				tmpID = new java.math.BigDecimal(size-1);
				value_1 = tmpID.intValue();
				//this.setValue1(tmpID);			 
			}		
			return tmpID;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
				this.updateRunningCode(paramID, paramType, value_1, value_2);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	private double updateRunningCode(String paramID, String paramType, int value_1, int value_2) throws UniqueIDGeneratorException {
		log.debug("PL updateRunningCode");
		double returnRows = 0;
		PreparedStatement ps = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" UPDATE RUNNING_PARAM SET VALUE1 = ?, VALUE2 = ? WHERE PARAM_ID = ? AND PARAM_TYPE = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, value_1);
			ps.setInt(2, value_2);
			ps.setString(3, paramID);
			ps.setString(4, paramType);
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public String getCardNo(CardInformationDataM card)
			throws UniqueIDGeneratorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * 
	 * Application No. format = KEC+YY+9999999 
	 * 
	 */
	public String getApplicationNo(PLApplicationDataM appM) throws UniqueIDGeneratorException {
////		log.debug("PL getApplicationNo");
//		String yearCode = "";
//		String runningCode = "";
//		try {				
//			String YEAR_FORMAT = "yy";
//			int year = 0;
//			SimpleDateFormat formatDate = new SimpleDateFormat(YEAR_FORMAT);
//			year = Integer.parseInt(formatDate.format(new java.util.Date())) ;// 20080218 not change to thai date 
//			yearCode = String.valueOf(year);
////			log.debug("formatDate.format(new java.util.Date()) = "+formatDate.format(new java.util.Date()));
////			log.debug("yearCode = "+yearCode);
//				
//			runningCode = EjbUtil.appendZero(this.getGenerateRunningNo(EJBConstant.PARAM_TYPE_APP, EJBConstant.PARAM_TYPE_ACC), 7);
////			log.debug("runningCode = "+runningCode);
//		} catch (Exception e) {
//			log.info("Err in getAppilcationNo >>> ", e);
//			throw new UniqueIDGeneratorException(e.getMessage());
//		}
//		ORIGCacheUtil cache = new ORIGCacheUtil();
//		return cache.findProductID(appM.getBusinessClassId()) + yearCode + runningCode;
		return null;
	}

	@Override
	public String getUniqueBySequence(String seqId)
			throws UniqueIDGeneratorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUniqueBySequence(String seqId, int dbType)
			throws UniqueIDGeneratorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getNextSequenceID(String seqName) throws UniqueIDGeneratorException {
		long seqID = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ").append(seqName).append(".NEXTVAL AS SEQ FROM DUAL");
			
			String dSql = String.valueOf(sql);

			ps = conn.prepareStatement(dSql);

			rs = ps.executeQuery();
			if (rs.next()) {
				seqID = rs.getLong("SEQ");
			}
			logger.info("##### NEXT SEQ = " + seqID);

		} catch (Exception e) {
			e.printStackTrace();
			throw new UniqueIDGeneratorException(e.getMessage());
		} finally {
			try {
			    closeConnection(conn, rs, ps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return seqID;
	}

	@Override
	public String getUniqueBySequence(String seqId, Connection conn)
			throws UniqueIDGeneratorException {
		// TODO Auto-generated method stub
		return null;
	}
}
