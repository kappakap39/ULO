/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbtg.util;

import com.eaf.core.ulo.flp.util.FLPPasswordUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Kritsakorn.c
 */
public class DBManager {
    
    final static Logger logger = Logger.getLogger(DBManager.class);
    
//    static ResourceBundle rb = ResourceBundle.getBundle("config");
//    static String url = rb.getString("url");
//    static String driver = rb.getString("driver");
//    static String user = rb.getString("user");
//    static String pass = rb.getString("pass");
    
//    static String url = "jdbc:oracle:thin:@172.30.153.98:1525:ULODB";
    static String driver = "oracle.jdbc.driver.OracleDriver";
//    static String user = "ORIG_APP";
//    //static String pass = "eRzOSL8Y0qIsWb268Lx69g==";
//    static String pass = "passw0rd";
    
    static Properties prop = SeperateUtil.loadProperties("/flp/batch/InfBatchLib/resource/InfBatchConfig.properties");
    //String a = prop.getProperty("")
    static String url = prop.getProperty("ORIG_DB_HOST");
//    static String driver = prop.getProperty("driver");
    static String user = prop.getProperty("ORIG_DB_USER");
    static String pass = prop.getProperty("ORIG_DB_PASSWORD");
    
    
    public Connection getConnection(){
        Connection con = null;
        
        try {
            Class.forName(driver);
            //con = DriverManager.getConnection(url, user, pass);
            con = DriverManager.getConnection(url, user, FLPPasswordUtil.decrypt(pass));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return con;
    }

    public static void main(String[] args) throws Exception {
        logger.info("Getting Column Names  ");
        Connection con = null;
        
        try {
            Class.forName(driver);
            logger.info("url: " +url);
            logger.info("user: " +user);
            logger.info("pass: " +pass);
            logger.info("password: " +FLPPasswordUtil.decrypt(pass));
            //con = DriverManager.getConnection(url, user, pass);
            //con = DriverManager.getConnection(url, user, FLPPasswordUtil.decrypt(pass));
            if (con != null) {
                logger.info("Got Connection.");
                DatabaseMetaData meta = con.getMetaData();
                logger.info("Driver Name : " + meta.getDriverName());
                logger.info("Driver Version : " + meta.getDriverVersion());
                logger.info("Got Connection success....");
            } else {
                logger.info("Could not Get Connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

}
