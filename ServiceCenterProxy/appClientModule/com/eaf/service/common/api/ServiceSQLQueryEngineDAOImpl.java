package com.eaf.service.common.api;

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

import com.eaf.service.common.master.ObjectDAO;
import com.eaf.service.common.master.ServiceLocator;

public class ServiceSQLQueryEngineDAOImpl extends ObjectDAO implements ServiceSQLQueryEngineDAO{
	static Logger logger = Logger.getLogger(ServiceSQLQueryEngineDAOImpl.class);
	private int dbType = ServiceLocator.ORIG_DB;
	public ServiceSQLQueryEngineDAOImpl(){
		this.dbType = ServiceLocator.ORIG_DB;
	}
	public ServiceSQLQueryEngineDAOImpl(int type){
		this.dbType = type;
	}
	@Override
	public void LoadModule(ServiceSQLDataM sqlM, Vector<HashMap> vModuleList,HashMap Module, String TYPE, Connection conn) throws ServiceException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{	
			logger.debug("SQL >> "+sqlM.getSQL());
			ps = conn.prepareStatement(sqlM.getSQL());
			Integer index;			
			if(null != sqlM.getSqlCriteria() && !sqlM.getSqlCriteria().isEmpty()){
				HashMap<Integer,ServiceModuleMSGDataM> criteria = sqlM.getSqlCriteria();
				Set<Integer> indexSet = criteria.keySet();
				Iterator<Integer> indexIt = indexSet.iterator();			
				while(indexIt.hasNext()){
					index = (Integer)indexIt.next();		
					this.setCriteria(ps,index.intValue(),(ServiceModuleMSGDataM)criteria.get(index));
				}			
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();			
			int colCnt = rsmd.getColumnCount();	
			if(ServiceSQLQueryEngine.TYPE.MODULE_ONE.equals(TYPE)){
				if(rs.next()){
					for(int i=1;i<=colCnt;i++){
						set(Module, i, rsmd, rs);
					}	
//					logger.debug("TABLE "+Module);
				}
			}else{
				while(rs.next()){
					HashMap<String, Object> LIST = new HashMap<String, Object>();
					for(int i=1;i<=colCnt;i++){
						set(LIST, i, rsmd, rs);
					}
//					logger.debug("TABLE "+LIST);
					vModuleList.add(LIST);
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceException(e.getMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceException(e.getMessage());
			}
		}
	}
	@Override
	public void LoadModule(ServiceSQLDataM sqlM, Vector<HashMap> vModuleList,HashMap Module,String TYPE) throws ServiceException {
		Connection conn = null;
		try{
			conn = getConnection(dbType);	
			LoadModule(sqlM, vModuleList, Module, TYPE, conn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	@Override
	public void LoadModuleEAFModel(ServiceSQLDataM sqlM, Vector<HashMap> vModuleList,HashMap Module, String TYPE) throws ServiceException {
		Connection conn = null;
		try{
			conn = getConnection(dbType);	
			LoadModuleEAFModel(sqlM, vModuleList, Module, TYPE, conn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	@Override
	public void LoadModuleEAFModel(ServiceSQLDataM sqlM, Vector<HashMap> vModuleList,HashMap Module, String TYPE, Connection conn) throws ServiceException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{	
			logger.debug("SQL >> "+sqlM.getSQL());
			ps = conn.prepareStatement(sqlM.getSQL());
			Integer index;			
			if(null != sqlM.getSqlCriteria() && !sqlM.getSqlCriteria().isEmpty()){
				HashMap<Integer,ServiceModuleMSGDataM> criteria = sqlM.getSqlCriteria();
				Set<Integer> indexSet = criteria.keySet();
				Iterator<Integer> indexIt = indexSet.iterator();			
				while(indexIt.hasNext()){
					index = (Integer)indexIt.next();		
					this.setCriteria(ps,index.intValue(),(ServiceModuleMSGDataM)criteria.get(index));
				}			
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();			
			int colCnt = rsmd.getColumnCount();	
			if(ServiceSQLQueryEngine.TYPE.MODULE_ONE.equals(TYPE)){
				if(rs.next()){
					for(int i=1;i<=colCnt;i++){
						setEAFModule(Module, i, rsmd, rs);
					}	
//					logger.debug("TABLE "+Module);
				}
			}else{
				while(rs.next()){
					HashMap<String, Object> LIST = new HashMap<String, Object>();
					for(int i=1;i<=colCnt;i++){
						setEAFModule(LIST, i, rsmd, rs);
					}
//					logger.debug("TABLE "+LIST);
					vModuleList.add(LIST);
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceException(e.getMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceException(e.getMessage());
			}
		}
	}
	
	private void setCriteria(PreparedStatement ps,int index,ServiceModuleMSGDataM dataM) throws SQLException{
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
	public void set(HashMap Module,int index,ResultSetMetaData rsmd,ResultSet rs) throws SQLException{
		String CLASS_NAME = rsmd.getColumnClassName(index);
		if(java.lang.String.class.getName().endsWith(CLASS_NAME)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getString(index));
		}else if(java.sql.Date.class.getName().endsWith(CLASS_NAME)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getDate(index));
		}else if(java.sql.Timestamp.class.getName().endsWith(CLASS_NAME)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getTimestamp(index));
		}else if(java.math.BigDecimal.class.getName().endsWith(CLASS_NAME)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getBigDecimal(index));
		}else if(java.lang.Double.class.getName().endsWith(CLASS_NAME)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getBigDecimal(index));
		}else if(java.lang.Long.class.getName().endsWith(CLASS_NAME)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getBigDecimal(index));
		}
	}
	
	public void setEAFModule(HashMap Module,int index,ResultSetMetaData rsmd,ResultSet rs) throws SQLException{
		String ColumnClassName = rsmd.getColumnClassName(index);
		String ColumnTypeName = rsmd.getColumnTypeName(index);
		String ColumnLabel = rsmd.getColumnLabel(index);
		if(java.lang.String.class.getName().endsWith(ColumnClassName)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getString(index));
		}else if(java.sql.Date.class.getName().endsWith(ColumnClassName)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getDate(index));
		}else if(java.sql.Timestamp.class.getName().endsWith(ColumnClassName)){
			Module.put((rsmd.getColumnLabel(index)).toUpperCase(), rs.getTimestamp(index));
		}else if(java.math.BigDecimal.class.getName().endsWith(ColumnClassName)){
			if(ColumnLabel.toUpperCase().endsWith("ID")){
				Module.put((rsmd.getColumnLabel(index)).toUpperCase(), String.valueOf(rs.getString(index)));
			}else{
				Module.put((rsmd.getColumnLabel(index)).toUpperCase(), String.valueOf(rs.getDouble(index)));
			}
		}
	}
	
	@Override
	public Date getApplicationDate() throws ServiceException {
		Date applicationDate = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT * FROM APPLICATION_DATE");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				applicationDate = rs.getDate("APP_DATE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceException(e.getLocalizedMessage());
			}
		}
		logger.debug("applicationDate >> "+applicationDate);
		return applicationDate;
	}	
}
