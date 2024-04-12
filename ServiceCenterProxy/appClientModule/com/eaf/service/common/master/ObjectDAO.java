package com.eaf.service.common.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ObjectDAO {
	private static Logger logger = Logger.getLogger(ObjectDAO.class);
	public ObjectDAO(){
		
	}
	public static final Connection getConnection(int dbType){
		try{
			ServiceLocator OrigService = ServiceLocator.getInstance();
			return OrigService.getConnection(dbType);  
		}catch(Exception e){ 
			logger.fatal("Connection is null",e);
		}
		return null;
	}
	public final Connection getConnection(){
		try{
			ServiceLocator OrigService = ServiceLocator.getInstance();
			return OrigService.getConnection(ServiceLocator.ORIG_DB);  
		}catch(Exception e){ 
			logger.fatal("Connection is null",e);
		}
		return null;
	}
	public final void closeConnection(Connection conn) throws Exception{
		try{
			if(null != conn){
				conn.close();
			}
		}catch(Exception ex){
			logger.fatal("Connection have error",ex);
			throw new SQLException(ex.getMessage());
		}
	}	
	public final static void closeConnection(Connection conn, ResultSet rs, PreparedStatement ps) throws Exception{ 
		try{
			
			if (rs != null){
				try{
					rs.close();
				} catch (Exception e) {
				}
			}
			if (ps != null){
				try{
					ps.close();
				} catch (Exception e) {
				}
			}
			if (conn != null){
				try{
					conn.close();
				} catch (Exception e) {
				}
			}
			rs = null;
			ps = null;
			conn = null;
		}catch(Exception ex){
			logger.fatal("Connection have error",ex);
			throw new SQLException(ex.getMessage());
		}
	}	
	public final void closeConnection(Connection con, PreparedStatement ps) throws Exception{
		try{
			if (ps != null){
				try{
					ps.close();
				} catch (Exception e) {
				}
			}
			if (con != null){
				try{
					con.close();
				} catch (Exception e) {
				}
			}
			ps = null;
			con = null;
		}catch(Exception ex){
			logger.fatal("Connection have",ex);
			throw new SQLException(ex.getMessage());
		}
	}
	@SuppressWarnings("rawtypes")
	public final String genSqlInClauseCondition(String fieldName, Vector stringOfCondition) {
		
		String resultStr = "";
		
		if (stringOfCondition != null && stringOfCondition.size() > 0){
			StringBuffer inClz = new StringBuffer("");
			StringBuffer totalInClz = new StringBuffer("");
			
			
			for (int i = 0; i < stringOfCondition.size(); i++) {
				inClz.append("'");
				inClz.append((String) stringOfCondition.elementAt(i));
				inClz.append("', ");
				
				if (((i + 1) % 800) == 0){
					totalInClz.append("(" + fieldName + " IN (" + inClz.substring(0, inClz.length() - 2) + ")) OR ");
					inClz = new StringBuffer("");
				}
			}
			
			if (inClz.length() > 0){
				totalInClz.append("(" + fieldName + " IN (" + inClz.substring(0, inClz.length() - 2) + ")) OR ");
			}
					
			resultStr = " (" + totalInClz.substring(0, totalInClz.length() - 4) + ") ";
		}
					
		return resultStr;
	}		
	public   java.sql.Date parseDate(java.util.Date uDate) {
		if(uDate==null){return null;}
		return new java.sql.Date(uDate.getTime());
		 
	}
	public  java.util.Date parseDate(java.sql.Date sDate) {
		if(sDate==null){return null;}
		return new java.util.Date(sDate.getTime());
	}
	public  java.sql.Timestamp parseTimestamp(java.util.Date sDate) {
		if(sDate==null){return null;}
		return new java.sql.Timestamp(sDate.getTime());
	}
	public String trimSpace(String str){
		if (str==null){return "";}
		else {return str.trim();}		
	}
	public String datetoString(java.util.Date dateValue) {
		if (dateValue != null) {
			DecimalFormat dformat = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			c.setTime(dateValue);
			StringBuffer date = new StringBuffer();
			date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append(
			"/");
			date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
			date.append(c.get(Calendar.YEAR));
			return date.toString();
		} else {
			return "";
		}
	} 
	
	 public static Date parseThaiToEngDate(String thaiDate){
		try{
			if(thaiDate!=null && !"".equals(thaiDate)){
				Calendar sbirthDate2 = Calendar.getInstance();
				int year = Integer.parseInt(thaiDate.substring(6,thaiDate.length()))-543;
				logger.debug("year = "+year);
				int month = Integer.parseInt(thaiDate.substring(3,5));
				int day = Integer.parseInt(thaiDate.substring(0,2));
				sbirthDate2.set(year,month-1,day);					
				//sbirthDate = new java.sql.Date(dateFormat.parse(birthDate).getTime());
				return new java.sql.Date(sbirthDate2.getTime().getTime());
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
		}
		return null;
	}
	public static String splitParameterIN(String value){
		if(null == value)
			return value;
		StringBuilder sql = new StringBuilder();	
		String [] values = value.split(",");
		for(int i=0; i<values.length; i++){
			if(i==0){
				sql.append("?");
			}else{
				sql.append(",?");
			}
		}
		return sql.toString();
	}	
	public static String ParameterINVect(Vector<String> param){
		StringBuilder str = new StringBuilder();
			if(null != param && param.size() >0){
				for(int i=0; i<param.size(); i++){
					str.append("?,");
				}
				str.deleteCharAt((str.length()-1));
			}
		return str.toString();
	}
	public static String ParameterINVect(List<String> param){
		StringBuilder str = new StringBuilder();
			if(null != param && param.size() >0){
				for(int i=0; i<param.size(); i++){
					str.append("?,");
				}
				str.deleteCharAt((str.length()-1));
			}
		return str.toString();
	}
	public static void PreparedStatementParameter(int index ,PreparedStatement ps,Vector<String> param) throws SQLException{
		if (null != param && param.size() > 0) {
			for (int i=0; i<param.size(); i++){
				ps.setString(index++, param.get(i));
			}
		}
	}
	public static void PreparedStatementParameter(int index ,PreparedStatement ps,List<String> param) throws SQLException{
		if (null != param && param.size() > 0) {
			for (int i=0; i<param.size(); i++){
				ps.setString(index++, param.get(i));
			}
		}
	}
	public final void closeConnection(Connection con, CallableStatement cs) throws Exception{
		try{
			if (cs != null){
				try{
					cs.close();
				} catch (Exception e) {
				}
			}
			if (con != null){
				try{
					con.close();
				} catch (Exception e) {
				}
			}
			cs = null;
			con = null;
		}catch(Exception ex){
			logger.fatal("Connection have",ex);
			throw new SQLException(ex.getMessage());
		}
	}	
	public final void closeConnection(Connection con, ResultSet rs, CallableStatement cs) throws Exception{
		try{
			if (rs != null){
				try{
					rs.close();
				} catch (Exception e) {
				}
			}
			if (cs != null){
				try{
					cs.close();
				} catch (Exception e) {
				}
			}
			if (con != null){
				try{
					con.close();
				} catch (Exception e) {
				}
			}
			rs = null;
			cs = null;
			con = null;
		}catch(Exception ex){
			logger.fatal("Connection have",ex);
			throw new SQLException(ex.getMessage());
		}
	}
	
	public final void closeConnection(PreparedStatement ps) throws Exception{
		try{
			if (ps != null){
				try{
					ps.close();
				}catch (Exception e){
				}
			}
			ps = null;
		}catch(Exception ex){
			logger.fatal("Connection have error",ex);
			throw new SQLException(ex.getMessage());
		}
	}	
	public final void closeConnection(PreparedStatement ps,ResultSet rs) throws Exception{
		try{
			if (ps != null){
				try{
					ps.close();
				}catch (Exception e){
				}
			}
			if (rs != null){
				try{
					rs.close();
				}catch (Exception e){
				}
			}
			ps = null;
			rs = null;
		}catch(Exception ex){
			logger.fatal("Connection have error",ex);
			throw new SQLException(ex.getMessage());
		}
	}
	public int setParameter(PreparedStatement ps,String KEY,HashMap<String, Object> hashMap,int index) throws Exception{
		Object object = hashMap.get(KEY);		
		logger.debug("FIELD "+KEY+" : VALUE "+object);
		if(object instanceof String){
			String VALUE = (String)object;			
			ps.setString(index++, VALUE);
		}else if(object instanceof java.sql.Date){
			java.sql.Date VALUE = (java.sql.Date)object;			
			ps.setDate(index++, VALUE);
		}else if(object instanceof java.util.Date){
			java.util.Date VALUE = (java.util.Date)object;
			ps.setDate(index++, new java.sql.Date(VALUE.getTime()));
		}else if(object instanceof java.sql.Timestamp){
			java.sql.Timestamp VALUE = (java.sql.Timestamp)object;			
			ps.setTimestamp(index++, VALUE);
		}else if(object instanceof java.math.BigDecimal){
			java.math.BigDecimal VALUE = (java.math.BigDecimal)object;			
			ps.setBigDecimal(index++, VALUE);
		}else{
			ps.setObject(index++,object);
		}
		return index;
	}
}
