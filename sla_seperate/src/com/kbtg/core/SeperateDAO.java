/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kbtg.core;

import com.kbtg.util.DBManager;
import com.kbtg.vo.AppSlaPaVO;
import com.kbtg.vo.PATimeSeries;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Kritsakorn.c
 */
public class SeperateDAO {
    
    final static Logger logger = Logger.getLogger(SeperateDAO.class);
    
    public List<PATimeSeries> getPAWorking() throws SQLException{
        
        List<PATimeSeries> timeSeriesList = null;
        PATimeSeries timeSeries = null;
        DBManager db = new DBManager();
        Connection conn = null;//
        Statement stmt = null;
        //String date = "201807";
        //String date = "201801";
        
        StringBuilder query = new StringBuilder()
                .append("with appOver as ( ")
                    .append("select distinct application_group_id ")
                    .append("from orig_application_log ")
                    .append("where application_group_id in( ")
                        .append("select distinct application_group_id ")
                        .append("from orig_application_log ")
                        .append("where job_state in('NM9902', 'NM9903', 'NM9904') ")
                        //.append("and to_char(create_date, 'YYYYMMDD') = '").append(date).append("' ")
                        //.append("and to_char(create_date, 'YYYYMM') = '").append(date).append("' ")
                        .append("and to_char(create_date, 'YYYYMM') = (select to_char(app_date, 'YYYYMM') from application_date) ")
                    .append(") ")
                    .append("and (role_name like 'VT%' or role_name like 'CA%') ")
                .append(")")
                .append("")
                .append("select submit.application_group_id ")
                .append(", receive.from_action, submit.to_action, receive.from_job_state, submit.to_job_state ")
                .append(", receive.receive_date, submit.submit_date ")
                .append(", receive.send_out_by, submit.receive_by, receive.from_role, submit.to_role ")
                .append("from appOver ao ")
                .append("join (select app_log_id, application_group_id, action as to_action ")
                    .append(", job_state to_job_state, to_char(create_date, 'YYYY/MM/DD HH24:MI:SS') submit_date, create_by receive_by ")
                    .append(", role_name to_role, life_cycle, reprocess_version reprocess ")
                    .append(", row_number() over(order by application_group_id, create_date) ref_seq ")
                    .append("from orig_application_log ")
                    .append("where 1=1 ")
                    .append(") submit on ao.application_group_id = submit.application_group_id ")
                .append("left join (select application_group_id, action as from_action ")
                    //.append(", job_state from_job_state, to_char(create_date, 'YYYY/MM/DD HH24:MI:SS') receive_date, create_by send_out_by ")
                    .append(", NVL(job_state, 'NM0000') from_job_state, to_char(create_date, 'YYYY/MM/DD HH24:MI:SS') receive_date, create_by send_out_by ")
                    .append(", role_name from_role, row_number() over(order by application_group_id, create_date)+1 from_seq ")
                    .append("from orig_application_log ")
                    .append("where 1=1 ")
                    .append(") receive on submit.ref_seq = receive.from_seq and submit.application_group_id = receive.application_group_id ")
                                .append("and ao.application_group_id = receive.application_group_id ")
                .append("where 1=1 ")
                .append("and SUBSTR(receive.from_job_state, 1, 4) <> 'NM99' ")
                .append("order by submit.application_group_id, submit.submit_date asc ")
                .append("")
                .append("")
                .append("");
        
        try{
            //
            conn = db.getConnection();
            stmt = conn.createStatement();
            //logger.info("sql: " +query);
            logger.info("Start: in "+ new Timestamp(System.currentTimeMillis()));
            ResultSet rs = stmt.executeQuery(query.toString());
            if (rs != null) {
                timeSeriesList = new ArrayList();
                while (rs.next()) {
                    timeSeries = new PATimeSeries();
                    timeSeries.setAppGroupId(rs.getString("application_group_id"));
                    timeSeries.setFromAction(rs.getString("from_action"));
                    timeSeries.setToAction(rs.getString("to_action"));
                    timeSeries.setFromJobState(rs.getString("from_job_state"));
                    timeSeries.setToJobState(rs.getString("to_job_state"));
                    timeSeries.setReceiveDate(rs.getString("receive_date"));
                    timeSeries.setSubmitDate(rs.getString("submit_date"));
                    timeSeries.setSendOutBy(rs.getString("send_out_by"));
                    timeSeries.setReceiveBy(rs.getString("receive_by"));
                    timeSeries.setFromRole(rs.getString("from_role"));
                    timeSeries.setToRole(rs.getString("to_role"));
                    timeSeriesList.add(timeSeries);
                    
                }
                logger.info("Done: in "+ new Timestamp(System.currentTimeMillis()));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        
        return timeSeriesList;
    }
    
    public int insertAppSlaPa(List<AppSlaPaVO> appList) throws SQLException{
        
        int rec = 0;
        DBManager db = new DBManager();
        Connection conn = null;//
        PreparedStatement prepStmt = null;
        
        StringBuilder sql = new StringBuilder()
                .append("insert into app_sla_pa ")
                .append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .append("")
                .append("")
                .append("");
        
        try{
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            for(AppSlaPaVO vo : appList){
                //
                int i = 1;
                prepStmt = conn.prepareStatement(sql.toString());
                prepStmt.setString(i++, vo.getApplicationGroupId());
                prepStmt.setTimestamp(i++, vo.getStartDate());
                prepStmt.setTimestamp(i++, vo.getEndDate());
                prepStmt.setInt(i++, vo.getWorkday());
                prepStmt.setInt(i++, vo.getRqRound());
                prepStmt.setInt(i++, vo.getRqDay());
                prepStmt.setInt(i++, vo.getRqOvertime());
                prepStmt.setInt(i++, vo.getPaRound());
                prepStmt.setInt(i++, vo.getPaDay());
                prepStmt.setInt(i++, vo.getPaSameday());
                prepStmt.setInt(i++, vo.getPaPass());
                
                rec += prepStmt.executeUpdate();
            }
            
            conn.setAutoCommit(true);
            
        }catch(SQLException e){
            e.printStackTrace();
        } finally {

            if (prepStmt != null) {
                prepStmt.close();
            }

            if (conn != null) {
                conn.close();
            }

        }
        
        return rec;
    }
    
    public int deleteAppSlaPa() throws SQLException{
        
        int rec = 0;
        DBManager db = new DBManager();
        Connection conn = null;//
        Statement stmt = null;
        
        StringBuilder sql = new StringBuilder()
                .append("DELETE FROM app_sla_pa")
                .append("")
                .append("");
        
        try{
            conn = db.getConnection();
            conn.setAutoCommit(false);
            
            stmt = conn.createStatement();
            rec += stmt.executeUpdate(sql.toString());
            
            conn.setAutoCommit(true);
            
        }catch(SQLException e){
            e.printStackTrace();
        } finally {

            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }

        }
        
        return rec;
    }
    
    public void callProcedure() throws SQLException{
        
        DBManager db = new DBManager();
        Connection conn = null;//
        CallableStatement pstmt = null;
        
        StringBuilder sql = new StringBuilder()
                .append("{call pka_dashboard.p_exec_main()}")
                .append("")
                .append("");
        logger.info("sql: " +sql);
        
        try {
            conn = db.getConnection();
            pstmt = conn.prepareCall(sql.toString());
            logger.info("Start: in "+ new Timestamp(System.currentTimeMillis()));
            pstmt.execute();
            logger.info("Done: in "+ new Timestamp(System.currentTimeMillis()));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }

        }
        
    }
    
}
