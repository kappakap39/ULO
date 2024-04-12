/*
 * Created on Dec 2, 2008
 * Created by wichaya
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
package com.eaf.orig.master.form.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.form.model.BussinessClassFormM;
import com.eaf.orig.master.form.model.FormM;
import com.eaf.orig.master.form.model.FormTabM;
import com.eaf.orig.master.form.model.SubFormM;
import com.eaf.orig.master.form.model.SubFormRoleM;
import com.eaf.orig.master.form.model.SubFormRoleModeM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.util.OrigUtil;

/**
 * @author wichaya
 *
 * Type: OrigFormMasterDAOImpl
 */
public class OrigFormMasterDAOImpl extends OrigObjectDAO implements OrigFormMasterDAO {
    private Logger log = Logger.getLogger(this.getClass());
    
    public void CreateFormM(FormM formM) throws OrigFormMasterDAOException{
        log.info(">>> CreateFormM Begin");
        try{
            InsertIntoSC_FORM(formM);
            
            Vector vFormTabs = formM.getFormTabs();
            if(vFormTabs!=null && vFormTabs.size()>0){
                for(int i=0; i<vFormTabs.size(); i++){
                    FormTabM formTabM = (FormTabM)vFormTabs.elementAt(i);
                    ///// Mapping Form with Tab
                    InsertIntoSC_FORM_FORM_TAB(formM.getFormID(),formTabM.getFormTabID());
                    
                    Vector vSubForms = formTabM.getSubForms();
                    if(vSubForms!=null && vSubForms.size()>0){
                        for(int j=0; j< vSubForms.size(); j++){
                            SubFormM subFormM = (SubFormM)vSubForms.elementAt(j);
                            ///// Mapping Form with Subform
                            InsertIntoSC_FORM_SUBFORM(String.valueOf(j+1),formM.getFormID(),subFormM.getSubFormID());
                            
                            Vector vSubFormRoles = subFormM.getSubFormRoles();
                            if(vSubFormRoles!=null && vSubFormRoles.size()>0){
                                for(int k=0; k<vSubFormRoles.size(); k++){
                                    SubFormRoleM subFormRoleM = (SubFormRoleM)vSubFormRoles.elementAt(k);
                                    ///// Mapping SubFormRole with FormSubForm
                                    InsertIntoSC_SUBFORM_ROLE(subFormRoleM);
                                    
                                    Vector vSubFormModes = subFormRoleM.getSubFormRoleModes();
                                    if(vSubFormModes!=null && vSubFormModes.size()>0){
                                        for(int l =0; l<vSubFormModes.size(); l++){
                                            SubFormRoleModeM subFormRoleModeM = (SubFormRoleModeM)vSubFormModes.elementAt(l);
                                            ///// Mapping SubFormMode with SubFormRole
                                            InsertIntoSC_SUBFORM_MODE(subFormRoleModeM);
                                        }
                                    }// Insert SubFormMode
                                }
                            }// Insert SubFormRole
                        }
                    }// Insert FormSubForm
                }
            }//Insert FormTab
            
            
            log.info(">>> CreateFormM Success");
        }catch(Exception e){
            log.fatal("***** Error in CreateFormM *****",e);
            throw new OrigFormMasterDAOException(e.getMessage());
        }
    }
    
    public void UpdateFormM(FormM formM) throws OrigFormMasterDAOException{
        log.info(">>> UpdateFormM Begin");
        try{
            if(formM!=null){
                DeleteFormM(formM.getFormID());
                
                CreateFormM(formM);
            }
            
            log.info(">>> UpdateFormM Success");
        }catch(Exception e){
            log.fatal("***** Error in UpdateFormM *****",e);
            throw new OrigFormMasterDAOException(e.getMessage());
        }
    }
    
    public void DeleteFormM(String formID) throws OrigFormMasterDAOException{
        log.info(">>> DeleteFormM (Cascade Delete) '"+formID+"' Begin");
        try{
            DeleteSC_FORM(formID);
            log.info(">>> DeleteFormM (Cascade Delete) Success");
        }catch(Exception e){
            log.fatal("***** Error in DeleteFormM *****",e);
            throw new OrigFormMasterDAOException(e.getMessage());
        }
    }
    
    public void SelectFormM(String formID) throws OrigFormMasterDAOException{
        log.info(">>> SelectFormM '"+formID+"' Begin");
        try{
            SelectSC_FORM(formID);
            log.info(">>> SelectFormM Success");
        }catch(Exception e){
            log.fatal("***** Error in SelectFormM *****",e);
            throw new OrigFormMasterDAOException(e.getMessage());
        }
    }
    
    private void InsertIntoSC_FORM(FormM formM) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_FORM ");
			sql.append("(FORM_ID, FORM_NAME, FORM_BUTTON_FILE_NAME, FORM_OTHER_FILE_NAME)");
			sql.append(" VALUES(?,?,?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, formM.getFormID());
			ps.setString(2, formM.getFormName());
			ps.setString(3, formM.getButtonFileName());
			ps.setString(4, formM.getOtherFileName());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    public void createFormTab(FormTabM formTabM)throws OrigFormMasterDAOException{
        log.info(">>> createFormTab");
        try{
            InsertIntoSC_FORM_TAB(formTabM);
            log.info(">>> createFormTab Success");
        }catch(Exception e){
            log.fatal("***** Error in createFormTab *****",e);
            throw new OrigFormMasterDAOException(e.getMessage());
        }
    }
    
    private void InsertIntoSC_FORM_TAB(FormTabM formTabM) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_FORM_TAB ");
			sql.append("(FORM_TAB_ID, FORM_TAB_NAME, FORM_TAB_URL)");
			sql.append(" VALUES(?,?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, formTabM.getFormTabID());
			ps.setString(2, formTabM.getFormTabName());
			ps.setString(3, formTabM.getFormTabURL());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    public void createSubForms(Vector vSubFormM)throws OrigFormMasterDAOException{
        log.info(">>> createSubForms");
        try{
            if(vSubFormM!=null && vSubFormM.size()>0){
                for(int i=0; i<vSubFormM.size(); i++){
                    SubFormM subFormM = (SubFormM)vSubFormM.elementAt(i);
                    InsertIntoSC_SUBFORM(subFormM);
                }
                log.info(">>> createFormTab Success "+vSubFormM.size()+" Records");
            }
        }catch(Exception e){
            log.fatal("***** Error in createSubForms *****",e);
            throw new OrigFormMasterDAOException(e.getMessage());
        }
    }
    
    private void InsertIntoSC_SUBFORM(SubFormM subFormM) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_SUBFORM ");
			sql.append("(SUBFORM_ID, SUBFORM_FILE_NAME, SUBFORM_CLASS_NAME, FORM_TAB_ID, SUBFORM_DESC)");
			sql.append(" VALUES(?,?,?,?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, subFormM.getSubFormID());
			ps.setString(2, subFormM.getSubFormFileName());
			ps.setString(3, subFormM.getSubFormClassName());
			ps.setString(4, subFormM.getSubFormID());
			ps.setString(5, subFormM.getSubFormDesc());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private void InsertIntoSC_FORM_FORM_TAB(String formID, String formTabID) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_FORM_FORM_TAB ");
			sql.append("(FORM_ID, FORMFORM_TAB_ID)");
			sql.append(" VALUES(?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, formID);
			ps.setString(2, formTabID);
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private void InsertIntoSC_FORM_SUBFORM(String subFormSeq, String formID, String subFormID) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_FORM_SUBFORM ");
			sql.append("(SUBFORM_SEQ, FORM_ID, SUBFORM_ID)");
			sql.append(" VALUES(?,?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, subFormSeq);
			ps.setString(2, formID);
			ps.setString(3, subFormID);
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private void InsertIntoSC_SUBFORM_ROLE(SubFormRoleM subFormRoleM) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
        OrigUtil origUtil = new OrigUtil();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_SUBFORM_ROLE ");
			sql.append("(SUBFORM_ROLE_ID, ROLE_ID, IS_DEFAULT, SUBFORM_ID, FORM_ID, SEQ)");
			sql.append(" VALUES(?,?,?,?,?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, subFormRoleM.getSubFormRoleID());
			ps.setString(2, subFormRoleM.getRoleID());
			ps.setString(3, origUtil.getYesNoFromBoolean(subFormRoleM.isDefault()));
			ps.setString(4, subFormRoleM.getSubFormID());
			ps.setString(5, subFormRoleM.getFormID());
			ps.setInt(6, subFormRoleM.getSeq());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private void InsertIntoSC_SUBFORM_MODE(SubFormRoleModeM subFormRoleModeM) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_SUBFORM_MODE ");
			sql.append("(JOB_STATE, DISPLAY_MODE, SUBFORM_ROLE_ID)");
			sql.append(" VALUES(?,?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, subFormRoleModeM.getJobState());
			ps.setString(2, subFormRoleModeM.getDisplayMode());
			ps.setString(3, subFormRoleModeM.getSubFormRoleID());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private void InsertIntoSC_BUS_CLASS_FORM(BussinessClassFormM bussinessClassFormM) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO SC_BUS_CLASS_FORM ");
			sql.append("(BUS_CLASS_ID, FORM_ID)");
			sql.append(" VALUES(?,?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, bussinessClassFormM.getBusclassID());
			ps.setString(2, bussinessClassFormM.getFormID());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private void DeleteSC_FORM(String FormID) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE SC_FORM ");
			sql.append("WHERE FORM_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, FormID);
			ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private FormM SelectSC_FORM(String FormID) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        FormM formM = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FORM_ID, FORM_NAME, FORM_BUTTON_FILE_NAME, FORM_OTHER_FILE_NAME ");
			sql.append("FROM SC_FORM ");
			sql.append("WHERE FORM_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, FormID);
			rs = ps.executeQuery();
			
			if (rs.next()) {
			    if(formM == null){
			        formM = new FormM();
				}
			    formM.setFormID(rs.getString(1));
			    formM.setFormName(rs.getString(2));
			    formM.setButtonFileName(rs.getString(3));
			    formM.setOtherFileName(rs.getString(4));
			}
			return formM;
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private Vector SelectSC_FORM_TAB(String formID) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector vFormTab = new Vector();
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT FORM_TAB_ID, FORM_TAB_NAME, FORM_TAB_URL ");
			sql.append("FROM SC_FORM_TAB t, SC_FORM_FORM_TAB ft ");
			sql.append("WHERE t.FORM_TAB_ID = ft.FORM_TAB_ID ");
			sql.append("and ft.FORM_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, formID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
			    FormTabM formTabM = new FormTabM();
			    formTabM.setFormTabID(rs.getString(1));
			    formTabM.setFormTabName(rs.getString(2));
			    formTabM.setFormTabURL(rs.getString(3));
			    
			    vFormTab.add(formTabM);
			}
			return vFormTab;
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
    
    private Vector SelectSC_SUBFORM(String formID) throws OrigFormMasterDAOException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vector vFormTab = new Vector();
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SUBFORM_ID, SUBFORM_FILE_NAME, SUBFORM_CLASS_NAME, FORM_TAB_ID, SUBFORM_DESC ");
			sql.append("FROM SC_FORM_SUBFORM ");
			sql.append("WHERE FORM_TAB_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, formID);
			rs = ps.executeQuery();
			
			while (rs.next()) {
			    FormTabM formTabM = new FormTabM();
			    formTabM.setFormTabID(rs.getString(1));
			    formTabM.setFormTabName(rs.getString(2));
			    formTabM.setFormTabURL(rs.getString(3));
			    
			    vFormTab.add(formTabM);
			}
			return vFormTab;
		} catch (Exception e) {
			log.fatal(e.getMessage());
			throw new OrigFormMasterDAOException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getMessage());
			}
		}
    }
}
