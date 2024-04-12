package com.eaf.service.common.api;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceSQLDataM {
	public ServiceSQLDataM(){
		super();
		sqlCriteria = new HashMap<Integer, ServiceModuleMSGDataM>();
	}
	private String SQL = "";
	private HashMap<Integer,ServiceModuleMSGDataM> sqlCriteria;
	private String jdbc;
	private String TableID;
	private HashMap Table;
	private ArrayList<String> Key;	
	
	public String getTableID() {
		return TableID;
	}
	public void setTableID(String tableID) {
		TableID = tableID;
	}
	public HashMap getTable() {
		return Table;
	}
	public void setTable(HashMap table) {
		Table = table;
	}
	public ArrayList<String> getKey() {
		return Key;
	}
	public void setKey(ArrayList<String> key) {
		Key = key;
	}
	
	public String getSQL() {
		return SQL;
	}
	public void setSQL(String sQL) {
		SQL = sQL;
	}
	public String getJdbc() {
		return jdbc;
	}
	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}	
	public void setSqlCriteria(HashMap<Integer, ServiceModuleMSGDataM> sqlCriteria) {
		this.sqlCriteria = sqlCriteria;
	}
	public void setNull(int index, int sqlType){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM("Null",new Integer(sqlType)));
	}	
	public void setObject(int index, Object x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Object.class.getName(),x));
	}	
	public void setRef(int index, Ref x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Ref.class.getName(),x));
	}	
	public void setShort(int index, short x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Short.class.getName(),new Short(x)));
	}	
	public void setString(int index, String x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(String.class.getName(),x));
	}	
	public void setTime(int index, Time x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Time.class.getName(),x));
	}	
	public void setTimestamp(int index, Timestamp x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Timestamp.class.getName(),x));
	}	
	public void setURL(int index, URL x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(URL.class.getName(),x));
	}
	public void setArray(int i, Array x){
		sqlCriteria.put(new Integer(i),new ServiceModuleMSGDataM(Array.class.getName(),x));
	}	
	public void setBigDecimal(int parameterIndex, BigDecimal x){
		sqlCriteria.put(new Integer(parameterIndex), new ServiceModuleMSGDataM(BigDecimal.class.getName(),x));
	}	
	public void setBlob(int i, Blob x){
		sqlCriteria.put(new Integer(i),new ServiceModuleMSGDataM(Blob.class.getName(),x));
	}	
	public void setBoolean(int index, boolean x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Boolean.class.getName(),new Boolean(x)));
	}	
	public void setByte(int index, byte x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Byte.class.getName(),new Byte(x)));
	}	
	public void setBytes(int index, byte[] x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(byte[].class.getName(),x));
	}	
	public void setClob(int index, Clob x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Clob.class.getName(),x));
	}	
	public void setDate(int index, Date x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Date.class.getName(),x));
	}	
	public void setDouble(int index, double x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Double.class.getName(),new Double(x)));
	}	
	public void setFloat(int index, float x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Float.class.getName(),new Float(x)));
	}	
	public void setInt(int index, int x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Integer.class.getName(),new Integer(x)));
	}	
	public void setLong(int index, long x){
		sqlCriteria.put(new Integer(index),new ServiceModuleMSGDataM(Long.class.getName(),new Long(x)));
	}	
	public HashMap<Integer,ServiceModuleMSGDataM> getSqlCriteria() {
		return sqlCriteria;
	}	
	public void clearSqlCriteria() {
		sqlCriteria.clear();
	}
	public int size(){
		if(null == sqlCriteria){
			sqlCriteria = new HashMap<Integer, ServiceModuleMSGDataM>();
		}
		return sqlCriteria.size();
	}
}
