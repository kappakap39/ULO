/*
 * Created on Oct 3 , 2007
 * Created by weeraya
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
package com.eaf.orig.shared.dao;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.model.ValueListMsgDataM;
import com.eaf.orig.shared.search.SearchM;
import com.eaf.orig.shared.service.OrigServiceLocator;


/**
 * @author Administrator
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValueListDAOImpl implements ValueListDAO{
	private static Logger logger = Logger.getLogger(ValueListDAOImpl.class);
	
/////	private int dbType = GEServiceLocator.XENOZ_DB;
	private int dbType = OrigServiceLocator.ORIG_DB;
	
	public ValueListDAOImpl(){
		dbType = OrigServiceLocator.ORIG_DB;
	}
	
	public ValueListDAOImpl(int db){
		dbType = db;
	}
	
	public ValueListM getResult(ValueListM valueListM) throws OrigApplicationMException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = new Vector();
		Vector resultElement = null;
		try{
			con = this.getConnection();
			if(!valueListM.isNextPage()){ 
				logger.debug(">>>>>>>>getNextpage");
				valueListM.setCount(this.getCount(valueListM));
				valueListM.setAtPage(1);
				valueListM.setNextPage(true);
			}else{valueListM.setCount(this.getCount(valueListM));}
			int beginIndex = (valueListM.getAtPage()-1)*valueListM.getItemsPerPage();
			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append("SELECT * FROM (SELECT ROWNUM AS CURSOR_INDEX, m.* FROM (");
			sqlBuffer.append(valueListM.getSQL());
			sqlBuffer.append(" ) m )WHERE CURSOR_INDEX > ? AND CURSOR_INDEX < ? ");
			
			ps = con.prepareStatement(sqlBuffer.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ps.setInt(1, beginIndex);
			ps.setInt(2, (beginIndex+valueListM.getItemsPerPage())+1);
			
			logger.debug(sqlBuffer.toString());
			logger.debug("rownum > "+beginIndex);
			logger.debug("rownum < "+((beginIndex+valueListM.getItemsPerPage())+1));

			logger.debug("executeQuery()...start");
			
			rs = ps.executeQuery();
			
			logger.debug("executeQuery()...success");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colCnt = rsmd.getColumnCount();
						
//			int checkEntry = 0;
						
			//collect column name
			resultElement = new Vector();
			for(int i=1;i<=colCnt;i++){
				resultElement.add(rsmd.getColumnName(i));
			}
			result.add(resultElement);
			
			while(rs.next()){
				resultElement = new Vector();
				for(int i=1;i<=colCnt;i++){
					resultElement.add(rs.getString(i));
				}
				result.add(resultElement);
			}
						
			valueListM.setResult(result);
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getResult()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
//				if(con!=null)con.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
				rs = null;
				ps = null;
				con = null;
			}catch(Exception ex){}
		}
		return valueListM;
	}
	
	public ValueListM getResult2(ValueListM valueListM) throws OrigApplicationMException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = new Vector();
		Vector resultElement = null;
		try{
			con = this.getConnection();
			if(!valueListM.isNextPage()){ 
				logger.debug(">>>>>>>>getNextpage");
				valueListM.setCount(this.getCount2(valueListM));
				valueListM.setAtPage(1);
				valueListM.setNextPage(true);
			}
			int beginIndex = (valueListM.getAtPage()-1)*valueListM.getItemsPerPage();
			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append("SELECT * FROM (SELECT ROWNUM AS CURSOR_INDEX, m.* FROM (");
			sqlBuffer.append(valueListM.getSQL());
			sqlBuffer.append(" ) m )WHERE CURSOR_INDEX > ? AND CURSOR_INDEX < ? ");
			
			ps = con.prepareStatement(sqlBuffer.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			HashMap criteria = valueListM.getSqlCriteria();
			Set indexSet = criteria.keySet();
			Iterator indexIt = indexSet.iterator();
			Integer index;
			int lastIndex = 0;
			while(indexIt.hasNext()){
				index = (Integer)indexIt.next();
				if(lastIndex < index.intValue()){
					lastIndex = index.intValue();
				}
				this.setCriteria(ps,index.intValue(),(ValueListMsgDataM)criteria.get(index));
			}
			
			//ps.setString(1, "00%");
			logger.debug("beginIndex ->" + beginIndex);
			ps.setInt(++lastIndex, beginIndex);
			logger.debug("lastIndex ->" + (beginIndex+valueListM.getItemsPerPage())+1);
			ps.setInt(++lastIndex, (beginIndex+valueListM.getItemsPerPage())+1);
			
			logger.debug(sqlBuffer.toString());
			logger.debug("rownum > "+beginIndex);
			logger.debug("rownum < "+((beginIndex+valueListM.getItemsPerPage())+1));
			
			logger.debug("executeQuery()...start");
			rs = ps.executeQuery();
			logger.debug("executeQuery()...success");
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colCnt = rsmd.getColumnCount();
			
//			int checkEntry = 0;
			
			//collect column name
			resultElement = new Vector();
			for(int i=1;i<=colCnt;i++){
				resultElement.add(rsmd.getColumnName(i));
			}
			result.add(resultElement);

			while(rs.next()){
				resultElement = new Vector();
				for(int i=1;i<=colCnt;i++){
					resultElement.add(rs.getString(i));
				}
				result.add(resultElement);
				//logger.debug("yeah...yeah..."); //pae debug
			}
			valueListM.setResult(result);
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getResult()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
//				if(con!=null)con.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
				rs = null;
				ps = null;
				con = null;
			}catch(Exception ex){}
		}
		return valueListM;
	}
	
	/*
	 * 	@parameter dataValue check for master data
	 */
	public ValueListM getResult_master(ValueListM valueListM, String dataValue) throws OrigApplicationMException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = new Vector();
		Vector resultElement = null;
		try{
			con = this.getConnection();
			if(!valueListM.isNextPage()){ 
				logger.debug(">>>>>>>>getNextpage");
				valueListM.setCount(this.getCount(valueListM));
				valueListM.setAtPage(1);
				valueListM.setNextPage(true);
			}
			int beginIndex = (valueListM.getAtPage()-1)*valueListM.getItemsPerPage();
			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append("SELECT * FROM (SELECT ROWNUM AS CURSOR_INDEX, m.* FROM (");
			sqlBuffer.append(valueListM.getSQL());
			sqlBuffer.append(" ) m )WHERE CURSOR_INDEX > ? AND CURSOR_INDEX < ? ");
			
			ps = con.prepareStatement(sqlBuffer.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ps.setInt(1, beginIndex);
			ps.setInt(2, (beginIndex+valueListM.getItemsPerPage())+1);
			
			logger.debug(sqlBuffer.toString());
			logger.debug("rownum > "+beginIndex);
			logger.debug("rownum < "+((beginIndex+valueListM.getItemsPerPage())+1));

			logger.debug("executeQuery()...start");
			
			rs = ps.executeQuery();
			
			logger.debug("executeQuery()...success");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colCnt = rsmd.getColumnCount();
			
//			int checkEntry = 0;
			String active = "A";
			//collect column name
			resultElement = new Vector();
			for(int i=1;i<=colCnt;i++){
				resultElement.add(rsmd.getColumnName(i));
			}
			result.add(resultElement);
			while(rs.next()){
				resultElement = new Vector();	
				if(active.equalsIgnoreCase(rs.getString(colCnt).trim()) || active.equalsIgnoreCase(rs.getString(4).trim()) 
						|| rs.getString(2).equals(dataValue)) {	//	See in each Load...WebAction
					for(int i=1;i<colCnt;i++){
						resultElement.add(rs.getString(i));
					}
					result.add(resultElement);
				}
			}
			valueListM.setResult(result);
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getResult()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
//				if(con!=null)con.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
				rs = null;
				ps = null;
				con = null;
			}catch(Exception ex){}
		}
		return valueListM;
	}
	/*
	 * 	@parameter dataValue check for master data
	 */
	public ValueListM getResult_master2(ValueListM valueListM, String dataValue) throws OrigApplicationMException{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector result = new Vector();
		Vector resultElement = null;
		try{
			logger.debug("getResult_master2 >>>>>> ");
			con = this.getConnection();
			if(!valueListM.isNextPage()){ 
				logger.debug(">>>>>>>>getNextpage");
				valueListM.setCount(this.getCount2(valueListM));
				valueListM.setAtPage(1);
				valueListM.setNextPage(true);
			}
			int beginIndex = (valueListM.getAtPage()-1)*valueListM.getItemsPerPage();
			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append("SELECT * FROM (SELECT ROWNUM AS CURSOR_INDEX, m.* FROM (");
			sqlBuffer.append(valueListM.getSQL());
			sqlBuffer.append(" ) m )WHERE CURSOR_INDEX > ? AND CURSOR_INDEX < ? ");
			
			ps = con.prepareStatement(sqlBuffer.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			HashMap criteria = valueListM.getSqlCriteria();
			Set indexSet = criteria.keySet();
			Iterator indexIt = indexSet.iterator();
			Integer index;
			int lastIndex = 0;
			while(indexIt.hasNext()){
				index = (Integer)indexIt.next();
				if(lastIndex < index.intValue()){
					lastIndex = index.intValue();
				}
				this.setCriteria(ps,index.intValue(),(ValueListMsgDataM)criteria.get(index));
			}
			
			//ps.setString(1, "00%");
			logger.debug("beginIndex ->" + beginIndex);
			ps.setInt(++lastIndex, beginIndex);
			logger.debug("lastIndex ->" + (beginIndex+valueListM.getItemsPerPage())+1);
			ps.setInt(++lastIndex, (beginIndex+valueListM.getItemsPerPage())+1);
			
			logger.debug(sqlBuffer.toString());
			logger.debug("rownum > "+beginIndex);
			logger.debug("rownum < "+((beginIndex+valueListM.getItemsPerPage())+1));
			
			logger.debug("executeQuery()...start");
			
			rs = ps.executeQuery();
			
			logger.debug("executeQuery()...success");
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int colCnt = rsmd.getColumnCount();
			
//			int checkEntry = 0;
			
			//collect column name
			resultElement = new Vector();
			for(int i=1;i<=colCnt;i++){
				resultElement.add(rsmd.getColumnName(i));
			}
			result.add(resultElement);
			String active = "A";
			while(rs.next()){
				if( active.equals(rs.getString(colCnt).trim()) || active.equals(rs.getString(4).trim()) || rs.getString(2).equals(dataValue) ) {	//	4 is ACTIVE_STATUS, 2 is DataID 
					resultElement = new Vector();
					for(int i=1;i<=colCnt;i++){
						resultElement.add(rs.getString(i));
					}
					result.add(resultElement);
					//logger.debug("yeah...yeah..."); //pae debug
				}
			}
			valueListM.setResult(result);
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getResult()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
//				if(con!=null)con.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
				rs = null;
				ps = null;
				con = null;
			}catch(Exception ex){}
		}
		return valueListM;
	}

	private int getCount(ValueListM valueListM) throws OrigApplicationMException{
		Connection con= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try{
			con = this.getConnection();
			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append("SELECT COUNT(*) AS COUNT FROM ( ");
			sqlBuffer.append(valueListM.getSQL());
			sqlBuffer.append(" ) ");
			
			logger.info(sqlBuffer.toString());

			ps = con.prepareStatement(sqlBuffer.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug("executeQuery()...start");
			
			rs = ps.executeQuery();
			
			logger.debug("executeQuery()...success");
			
			count = (rs.next())?rs.getInt("COUNT"):0;
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getCount()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
//				if(con!=null)con.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch(Exception ex){}
		}
		return count;
	}
	
	Connection getConnection(){
		try{
			return (OrigServiceLocator.getInstance()).getConnection(dbType);
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getConnection()",e);
			return null;
		}
	}
	
	private int getCount2(ValueListM valueListM) throws OrigApplicationMException{
		Connection con= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try{
			con = this.getConnection();
			StringBuilder sqlBuffer = new StringBuilder();
			sqlBuffer.append("SELECT COUNT(*) AS COUNT FROM ( ");
			sqlBuffer.append(valueListM.getSQL());
			sqlBuffer.append(" ) ");
			
			logger.info(sqlBuffer.toString());

			ps = con.prepareStatement(sqlBuffer.toString(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			HashMap criteria = valueListM.getSqlCriteria();
			Set indexSet = criteria.keySet();
			Iterator indexIt = indexSet.iterator();
			Integer index;
			
			while(indexIt.hasNext()){
				index = (Integer)indexIt.next();
				this.setCriteria(ps,index.intValue(),(ValueListMsgDataM)criteria.get(index));
			}
			
			logger.debug("executeQuery()...start");
			
			rs = ps.executeQuery();
			
			logger.debug("executeQuery()...success");
			
			count = (rs.next())?rs.getInt("COUNT"):0;
		}catch(Exception e){
			logger.error("Error in ValueListDAOImpl.getCount()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
//				if(con!=null)con.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch(Exception ex){}
		}
		return count;
	}
	
	/**
	 * Returns the dbType.
	 * @return int
	 */
	public int getDbType() {
		return dbType;
	}

	/**
	 * Sets the dbType.
	 * @param dbType The dbType to set
	 */
	public void setDbType(int dbType) {
		this.dbType = dbType;
	}
	
	private void setCriteria(PreparedStatement ps,int index,ValueListMsgDataM dataM)throws SQLException{
		if(ps != null && dataM != null){
			if(Array.class.getName().equals(dataM.getClassName())){
				ps.setArray(index,(Array)dataM.getValue());
			}else if(BigDecimal.class.getName().equals(dataM.getClassName())){
				ps.setBigDecimal(index,(BigDecimal)dataM.getValue());
			}else if(Blob.class.getName().equals(dataM.getClassName())){
				ps.setBlob(index,(Blob)dataM.getValue());
			}else if(Boolean.class.getName().equals(dataM.getClassName())){
				ps.setBoolean(index,((Boolean)dataM.getValue()).booleanValue());
			}else if(Byte.class.getName().equals(dataM.getClassName())){
				ps.setByte(index,((Byte)dataM.getValue()).byteValue());
			}else if(byte[].class.getName().equals(dataM.getClassName())){
				ps.setBytes(index, (byte[])dataM.getValue());
			}else if(Clob.class.getName().equals(dataM.getClassName())){
				ps.setClob(index,(Clob)dataM.getValue());
			}else if(Date.class.getName().equals(dataM.getClassName())){
				ps.setDate(index,(Date)dataM.getValue());
			}else if(Double.class.getName().equals(dataM.getClassName())){
				ps.setDouble(index,((Double)dataM.getValue()).doubleValue());
			}else if(Float.class.getName().equals(dataM.getClassName())){
				ps.setFloat(index,((Float)dataM.getValue()).floatValue());
			}else if(Integer.class.getName().equals(dataM.getClassName())){
				logger.debug("int ->" + ((Integer)dataM.getValue()).intValue());
				ps.setInt(index,((Integer)dataM.getValue()).intValue());
			}else if(Long.class.getName().equals(dataM.getClassName())){
				ps.setLong(index, ((Long)dataM.getValue()).longValue());
			}else if("Null".equals(dataM.getClassName())){
				ps.setNull(index, ((Integer)dataM.getValue()).intValue());
			}else if(Object.class.getName().equals(dataM.getClassName())){
				ps.setObject(index, (Object)dataM.getValue());
			}else if(Ref.class.getName().equals(dataM.getClassName())){
				ps.setRef(index, (Ref)dataM.getValue());
			}else if(Short.class.getName().equals(dataM.getClassName())){
				ps.setShort(index, ((Short)dataM.getValue()).shortValue());
			}else if(String.class.getName().equals(dataM.getClassName())){
				logger.debug("index ->" + index + "str ->" + (String)dataM.getValue());
				ps.setString(index, (String)dataM.getValue());
			}else if(Time.class.getName().equals(dataM.getClassName())){
				ps.setTime(index, (Time)dataM.getValue());
			}else if(Timestamp.class.getName().equals(dataM.getClassName())){
				ps.setTimestamp(index,(Timestamp)dataM.getValue());
			}else if(URL.class.getName().equals(dataM.getClassName())){
				ps.setURL(index, (URL)dataM.getValue());
			}
		}
	}
	
	@Override
	public SearchM getResultSearchM(SearchM searchM) throws OrigApplicationMException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		Vector<String> resultElement = null;
		try{
			con = this.getConnection();
			if(!searchM.isNextPage()){ 
				logger.debug(">>>>>>>>getNextpage");
				searchM.setCount(this.getCount(searchM));
				searchM.setAtPage(1);
				searchM.setNextPage(true);
			}
			int beginIndex = (searchM.getAtPage()-1)*searchM.getItemsPerPage();

			logger.debug(">> "+searchM.getSqlMain());
			
			ps = con.prepareStatement(searchM.getSqlMain(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int index = 1;			
			if(null != searchM.getCountCriteria()){
				for(Object object : searchM.getMainCriteria()){
					setCriteria(ps,object,index);
					index++;
				}
			}			
			ps.setInt(index, beginIndex);
			ps.setInt(++index, (beginIndex+searchM.getItemsPerPage())+1);
			
			logger.debug("rownum > "+beginIndex);
			logger.debug("rownum < "+((beginIndex+searchM.getItemsPerPage())+1));
			
			rs = ps.executeQuery();
						
			ResultSetMetaData rsmd = rs.getMetaData();			
			int colCnt = rsmd.getColumnCount();
			
			while(rs.next()){
				resultElement = new Vector<String>();
				for(int i=1;i<=colCnt;i++){
					resultElement.add(rs.getString(i));
				}
				result.add(resultElement);
			}
						
			searchM.setResult(result);
		}catch(Exception e){
			logger.error("ERROR getResultSearchM()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
				rs = null;
				ps = null;
				con = null;
			}catch(Exception ex){}
		}
		return searchM;
	}
	private int getCount(SearchM searchM) throws OrigApplicationMException{
		Connection con= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try{
			con = this.getConnection();
						
			logger.info(">> "+searchM.getSqlCount());

			ps = con.prepareStatement(searchM.getSqlCount(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
			int index = 1;
			if(null != searchM.getCountCriteria()){
				for(Object object : searchM.getCountCriteria()){
					setCriteria(ps,object,index);
					index++;
				}
			}
			
			rs = ps.executeQuery();
			
			count = (rs.next())?rs.getInt("COUNT"):0;
		}catch(Exception e){
			logger.error("ERROR ValueListDAOImpl.getCount()",e);
			throw new OrigApplicationMException(e.getMessage());
		}finally{
			try{
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch(Exception ex){}
		}
		return count;
	}
	private void setCriteria(PreparedStatement ps,Object object,int index) throws SQLException{		
		if(null != object){
			if(object instanceof String){
				logger.debug("index -> "+index+" str "+object);
				ps.setString(index,(String)object);
			}else if(object instanceof java.util.Date){
				ps.setDate(index,parseDate((java.util.Date)object));
			}
		}else{
			ps.setString(index,null);
		}
	}
	public  java.sql.Date parseDate(java.util.Date uDate) {
		if(uDate==null){return null;}
		return new java.sql.Date(uDate.getTime());
		 
	}
}
