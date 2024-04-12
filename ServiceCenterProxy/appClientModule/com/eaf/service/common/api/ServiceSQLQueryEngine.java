package com.eaf.service.common.api;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.service.common.master.ServiceLocator;
import com.eaf.service.common.util.ServiceUtil;

public class ServiceSQLQueryEngine {
	static Logger logger = Logger.getLogger(ServiceSQLQueryEngine.class);
	private int dbType = ServiceLocator.ORIG_DB;
	public ServiceSQLQueryEngine(){
		this.dbType = ServiceLocator.ORIG_DB;
	}
	public ServiceSQLQueryEngine(int type){
		this.dbType = type;
	}
	public class TYPE {
		public static final String MODULE_ONE = "MODULE_ONE";
		public static final String MODULE_LIST = "MODULE_LIST";
	}
	public HashMap LoadModule(ServiceSQLDataM sqlM){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM, vModuleList, Module, TYPE.MODULE_ONE);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return Module;
	}
	public HashMap LoadModuleEAFModel(ServiceSQLDataM sqlM){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModuleEAFModel(sqlM, vModuleList, Module, TYPE.MODULE_ONE);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return Module;
	}
	public HashMap LoadModule(ServiceSQLDataM sqlM,Connection conn) throws Exception{
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM,vModuleList,Module,TYPE.MODULE_ONE,conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw e;
		}
		return Module;
	}
	public Vector<HashMap> LoadModuleList(ServiceSQLDataM sqlM,Connection conn) throws Exception{
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM,vModuleList,Module,TYPE.MODULE_LIST,conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw e;
		}
		return vModuleList;
	}
	public ArrayList LoadModuleList(ServiceSQLDataM sqlM,String FIELD_ID){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM,vModuleList,Module,TYPE.MODULE_LIST);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		ArrayList vList = new ArrayList();
		if(!ServiceUtil.empty(vModuleList)){
			for (HashMap Field : vModuleList) {
				vList.add(Field.get(FIELD_ID));
			}
		}
		return vList;
	}
	public ArrayList LoadModuleListEafModule(ServiceSQLDataM sqlM,String FIELD_ID){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModuleEAFModel(sqlM,vModuleList,Module,TYPE.MODULE_LIST);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		ArrayList vList = new ArrayList();
		if(!ServiceUtil.empty(vModuleList)){
			for (HashMap Field : vModuleList) {
				vList.add(Field.get(FIELD_ID));
			}
		}
		return vList;
	}
	public Vector<HashMap> LoadModuleList(ServiceSQLDataM sqlM){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM, vModuleList, Module, TYPE.MODULE_LIST);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return vModuleList;
	}
	public HashMap LoadModule(String SQL,String CONDITION){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			logger.debug("SQL >> "+SQL);
			ServiceSQLDataM sqlM = new ServiceSQLDataM();
				sqlM.setSQL(SQL);
				sqlM.setString(1,CONDITION);
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM, vModuleList, Module, TYPE.MODULE_ONE);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return Module;
	}
	public HashMap LoadModule(String SQL){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			logger.debug("SQL >> "+SQL);
			ServiceSQLDataM sqlM = new ServiceSQLDataM();
				sqlM.setSQL(SQL);
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM, vModuleList, Module, TYPE.MODULE_ONE);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return Module;
	}
	public Vector<HashMap> LoadModuleList(String SQL){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			logger.debug("SQL >> "+SQL);
			ServiceSQLDataM sqlM = new ServiceSQLDataM();
				sqlM.setSQL(SQL);
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM, vModuleList, Module, TYPE.MODULE_LIST);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return vModuleList;
	}
	public Vector<HashMap> LoadModuleList(String SQL,String CONDITION){
		Vector<HashMap> vModuleList = new Vector<HashMap>();
		HashMap Module = new HashMap();
		try{
			logger.debug("SQL >> "+SQL);
			logger.debug("CONDITION >> "+CONDITION);
			ServiceSQLDataM sqlM = new ServiceSQLDataM();
				sqlM.setSQL(SQL);
				sqlM.setString(1,CONDITION);
			ServiceSQLQueryEngineDAO DAO = new ServiceSQLQueryEngineDAOImpl(dbType);
			DAO.LoadModule(sqlM, vModuleList, Module, TYPE.MODULE_LIST);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return vModuleList;
	}
	public static String display(HashMap<String, Object> field,String KEY){
		if(null != field){
			Object obj = field.get(KEY);
			if(null != obj){
				if(obj instanceof String){
					return (String)obj;
				}else if(obj instanceof java.util.Date){
					java.util.Date date = (java.util.Date)obj;
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					return format.format(date);
				}else if(obj instanceof java.sql.Date){
					java.sql.Date date = (java.sql.Date)obj;				
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					return format.format(new java.util.Date(date.getTime()));
				}else if(obj instanceof java.sql.Timestamp){
					java.sql.Timestamp date = (java.sql.Timestamp)obj;				
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					return format.format(new java.util.Date(date.getTime()));
				}else if(obj instanceof java.math.BigDecimal){
					return format((java.math.BigDecimal)obj,"###0",null);
				}else if(obj instanceof java.lang.Double){
					return String.valueOf(obj);
				}
			}
		}
		return "";
	}
	public static String display(HashMap<String, Object> field,String KEY,String $format){
		Object obj = field.get(KEY);
		if(null != obj){
			if(obj instanceof String){
				return (String)obj;
			}else if(obj instanceof java.util.Date){
				java.util.Date date = (java.util.Date)obj;
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				return format.format(date);
			}else if(obj instanceof java.sql.Date){
				java.sql.Date date = (java.sql.Date)obj;				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				return format.format(new java.util.Date(date.getTime()));
			}else if(obj instanceof java.sql.Timestamp){
				java.sql.Timestamp date = (java.sql.Timestamp)obj;				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				return format.format(new java.util.Date(date.getTime()));
			}else if(obj instanceof java.math.BigDecimal){
				return format((java.math.BigDecimal)obj,$format,null);
			}else if(obj instanceof java.lang.Double){
				return String.valueOf(obj);
			}
		}
		return "";
	}
	public static int getInt(HashMap resultRow,String fieldName){
		try{
			if(null != resultRow){
				Object objectField = resultRow.get(fieldName); 
				if(null != objectField){
					if(objectField instanceof String){
						String valueField = (String)objectField;
						return Integer.parseInt(valueField);
					}else if(objectField instanceof java.math.BigDecimal){
						BigDecimal valueField = (BigDecimal)objectField;
						return valueField.intValue();
					}
				}
			}
		}catch(Exception e){			
		}
		return 0;
	}
	public static String format(BigDecimal number){
		return format(number, "###0.00");
	}
	public static String format(BigDecimal number,String format){
		if(null == number){
			number = BigDecimal.ZERO;
		}
		DecimalFormat f = new DecimalFormat(format);
		return f.format(number);
	}
	public static String format(BigDecimal number,String format,String $default){
		if(null == number){
			return $default; 
		}
		DecimalFormat f = new DecimalFormat(format);
		return f.format(number);
	}
}
