package flp.db;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBConnector {
	
	private static transient Logger logger = Logger.getLogger(DBConnector.class);
	
	public final static int OLDB_DATA = 1;
    public final static int WFDB_DATA = 2;
	private final static String JAVA_ENV= "";
	public final static String OLDB_DATA_SOURCE= "jdbc/ol_data";
	public final static String WFDB_DATA_SOURCE= "jdbc/fls2wfdb";
	
	public Connection getConnection( int dbType ) throws Exception {
		try{
			return getDatasource(dbType).getConnection();
		} catch(Exception e){
			logger.fatal("ERROR getConnection() : ",e);
			throw e;
		}
	}
	
	public DataSource getDatasource( int dbType ) throws Exception {
		try {
			
			InitialContext ctx = new InitialContext();
			Object obj = null;
			switch(dbType){
				case OLDB_DATA: obj = ctx.lookup(JAVA_ENV+OLDB_DATA_SOURCE); break;
				case WFDB_DATA: obj = ctx.lookup(JAVA_ENV+WFDB_DATA_SOURCE); break;
				default: obj = ctx.lookup(JAVA_ENV+OLDB_DATA_SOURCE); break;
			}
			DataSource dataSrc = (DataSource) javax.rmi.PortableRemoteObject.narrow(obj, DataSource.class);
			return dataSrc;
		} catch ( Exception e ) {
			logger.fatal("ERROR : getDatasource() ",e);
			throw e;
		}
	}
	
}
