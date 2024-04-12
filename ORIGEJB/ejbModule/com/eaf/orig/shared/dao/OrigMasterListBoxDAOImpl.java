/*
 * Created on Nov 22, 2007
 * Created by Prawit Limwattanachai
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.ListBoxMasterM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.BusinessClassM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: OrigMasterListBoxDAOImpl
 */
public class OrigMasterListBoxDAOImpl extends OrigObjectDAO implements
		OrigMasterListBoxDAO {
	Logger log = Logger.getLogger(OrigMasterListBoxDAOImpl.class);
	
	public void deleteListBoxM(String listBoxID)
			throws OrigApplicationMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE LIST_BOX_MASTER ");
				sql.append(" WHERE LIST_BOX_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, listBoxID);
				ps.executeUpdate(); 
				
				//conn = Get Connection
				conn = getConnection();
				sql = new StringBuffer("");
				sql.append("DELETE LIST_BOX_BUS_CLASS ");
				sql.append(" WHERE LIST_BOX_ID = ?");
				dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, listBoxID);
				ps.executeUpdate(); 

		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
	}
	
	public void deleteOrigMasterListBoxM(String[] listBoxToDelete)
			throws OrigApplicationMException {
		for(int i = 0; i < listBoxToDelete.length; i++){
			deleteListBoxM(listBoxToDelete[i]);
		}

	}
	
	public void deleteNotInKeyTableLisboxBusCLass(ListBoxMasterM listBoxM)
			throws OrigApplicationMException {
		
		Vector listBoxBusVect = new Vector();
		listBoxBusVect = listBoxM.getListBoxBusinessClass();
		Connection conn = null;
		PreparedStatement ps = null;
		String cmpCode = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM LIST_BOX_BUS_CLASS");
            sql.append(" WHERE LIST_BOX_ID = ? ");
            
            if ((listBoxBusVect != null) && (listBoxBusVect.size() > 0)) {
                sql.append(" AND BUS_CLASS_ID NOT IN ( ");
                BusinessClassM busClassM;
                for (int i = 0; i < listBoxBusVect.size(); i++) {
                	busClassM = (BusinessClassM) listBoxBusVect.get(i);
                    sql.append(" '" + busClassM.getId() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            log.debug("dSql="+dSql);
            ps = conn.prepareStatement(dSql);

            ps.setString(1, listBoxM.getListBoxID());
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
				throw new OrigApplicationMException(e.getMessage());
			}
		}
	}
	
	public void insertLisboxBusCLass(String ListID,String BusID,ListBoxMasterM listBoxM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" INSERT INTO LIST_BOX_BUS_CLASS ");
			sql.append(" (LIST_BOX_ID, BUS_CLASS_ID,UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, ListID);
			ps.setString(2, BusID);
			ps.setString(3, listBoxM.getUpdateBy());

			ps.executeUpdate();
			
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public double updateLisboxBusCLass(String ListID,String BusID,ListBoxMasterM listBoxM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("UPDATE LIST_BOX_BUS_CLASS ");
				sql.append(" SET LIST_BOX_ID=? , BUS_CLASS_ID=?,UPDATE_BY = ?,UPDATE_DATE=SYSDATE ");
				sql.append(" WHERE LIST_BOX_ID = ?");
				sql.append(" AND BUS_CLASS_ID = ? ");
				
				String dSql = String.valueOf(sql);
				log.debug("Sql="+dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, ListID);
				ps.setString(2, BusID);	
				ps.setString(3, listBoxM.getUpdateBy());
				ps.setString(4, ListID);
				ps.setString(5, BusID);	
			
				returnRows = ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;
	}
	
	public double updateOrigMasterListBoxM(ListBoxMasterM listBoxM)
			throws OrigApplicationMException {
//		ORIGUtility utility = new ORIGUtility();
		double returnRows = 0;
		double chkReturnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE LIST_BOX_MASTER ");
			sql.append(" SET LIST_BOX_ID=? , FIELD_ID=? , CHOICE_NO = ? , DISPLAY_NAME = ?,UPDATE_BY = ?,UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE LIST_BOX_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, listBoxM.getListBoxID());
			ps.setInt(2, Integer.parseInt(listBoxM.getFieldID()));	
			ps.setString(3, listBoxM.getChoiceNo());
			ps.setString(4, listBoxM.getDisplayName());
			ps.setString(5, listBoxM.getUpdateBy());
			ps.setString(6, listBoxM.getListBoxID());
			
			
			returnRows = ps.executeUpdate();
			
			//**** Update Table ListBoxBusClass ****
			Vector listBoxBusVect = new Vector();
			listBoxBusVect = listBoxM.getListBoxBusinessClass();
			String ListID = listBoxM.getListBoxID();
			String BusID = "";
			if(listBoxBusVect!=null && listBoxBusVect.size() > 0){
				for(int i =0;i<listBoxBusVect.size();i++){
					BusinessClassM busClassM = new BusinessClassM();
					busClassM = (BusinessClassM)listBoxBusVect.get(i);
					BusID = busClassM.getId();
					
					chkReturnRows = updateLisboxBusCLass(ListID,BusID,listBoxM);
					if(chkReturnRows == 0){
						insertLisboxBusCLass(ListID,BusID,listBoxM);
					}
				}
				//*** delete not in key Table ListBoxBusClass
				deleteNotInKeyTableLisboxBusCLass(listBoxM);
			}else{
				log.debug("I' m Deleting ListBoxBusClass");
				log.debug("listBoxM.getListBoxID() = "+listBoxM.getListBoxID());
				deleteListBoxBusClass(listBoxM.getListBoxID());
			}
			
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;
	}
	
	public void deleteListBoxBusClass(String listBoxID)
			throws OrigApplicationMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE LIST_BOX_BUS_CLASS ");
				sql.append(" WHERE LIST_BOX_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, listBoxID);
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterListBoxDAO#selectOrigMasterListBoxM(java.lang.String)
	 */
	public ListBoxMasterM selectOrigMasterListBoxM(String listID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT LIST_BOX_ID, DISPLAY_NAME, CHOICE_NO, FIELD_ID  ");
			sql.append(" FROM LIST_BOX_MASTER ");
			sql.append(" WHERE LIST_BOX_ID = ?  ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, listID);

			rs = ps.executeQuery();
			ListBoxMasterM listBoxM = null;

			if (rs.next()) {
				listBoxM = new ListBoxMasterM();
				listBoxM.setListBoxID(rs.getString(1));
				listBoxM.setDisplayName(rs.getString(2));
				listBoxM.setChoiceNo(rs.getString(3));
				listBoxM.setFieldID(rs.getString(4));
			}
			
			// **** get busID from Table LIST_BOX_BUS_CLASS ****
			sql = new StringBuffer("");
			
			sql.append(" SELECT BUS_CLASS_ID ");
			sql.append(" FROM LIST_BOX_BUS_CLASS ");
			sql.append(" WHERE LIST_BOX_ID = ?  ");
			
			dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, listID);

			rs = ps.executeQuery();
			Vector listBoxBusVect = new Vector();
			
			while (rs.next()) {
				BusinessClassM busClassM = new BusinessClassM();
				busClassM.setId(rs.getString(1));
				listBoxBusVect.add(busClassM);
			}
			
			// **** get busdesc from Table LIST_BOX_BUS_CLASS ****
			
			sql = new StringBuffer("");
			
			sql.append(" SELECT BUS_CLASS_ID, BUS_CLASS_DESC ");
			sql.append(" FROM BUSINESS_CLASS ");
//			sql.append(" WHERE BUS_CLASS_ID IN ( ");
//			for(int i =0;i<listBoxBusVect.size();i++){
//				if( (i+1) == listBoxBusVect.size() ){
//					sql.append(" ? ");
//				}else{
//					sql.append(" ?, ");
//				}
//			}
//			sql.append(" ) ");
			
			
			 if ((listBoxBusVect != null) && (listBoxBusVect.size() != 0)) {
                sql.append(" WHERE BUS_CLASS_ID IN ( ");
                BusinessClassM busClassM;
                for (int i = 0; i < listBoxBusVect.size(); i++) {
                	busClassM = (BusinessClassM) listBoxBusVect.get(i);
                    sql.append(" '" + busClassM.getId() + "' , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
			
			dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
//			for(int i =0;i<listBoxBusVect.size();i++){
//				BusinessClassM busClassM = new BusinessClassM();
//				busClassM = (BusinessClassM)listBoxBusVect.get(i);
//				ps.setString(++i, busClassM.getId());
//			}

			rs = ps.executeQuery();
			listBoxBusVect = new Vector();
			
			while (rs.next()) {
				BusinessClassM busClassM = new BusinessClassM();
				busClassM.setId(rs.getString(1));
				busClassM.setDescription(rs.getString(2));
				listBoxBusVect.add(busClassM);
			}
			
			listBoxM.setListBoxBusinessClass(listBoxBusVect);
			
			return listBoxM;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public void createModelOrigMasterListBoxMasterM(ListBoxMasterM listBoxM)
	throws OrigApplicationMException {
	try {
		//String listBoxID=ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.LIST_BOX_ID);
		ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
		String listBoxID = generatorManager.generateUniqueIDByName(EJBConstant.LIST_BOX_ID);
		listBoxID = EjbUtil.appendZero(listBoxID, 5);
		listBoxM.setListBoxID(listBoxID);
		createListBoxM(listBoxM);
	} catch (Exception e) {
		log.fatal("",e);
		throw new OrigApplicationMException(e.getMessage());
	}
	
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterListBoxDAO#createModelOrigMasterListBoxMasterM(com.eaf.orig.master.shared.model.ListBoxMasterM)
	 */
	public void createListBoxM(ListBoxMasterM listBoxM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO LIST_BOX_MASTER ");
			sql.append(" (LIST_BOX_ID, DISPLAY_NAME, FIELD_ID, CHOICE_NO, UPDATE_BY, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,?,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, listBoxM.getListBoxID());
			ps.setString(2, listBoxM.getDisplayName());
			ps.setString(3, listBoxM.getFieldID());
			ps.setString(4, listBoxM.getChoiceNo());
			ps.setString(5, listBoxM.getUpdateBy());
			
			ps.executeUpdate();
			Vector busVect = listBoxM.getListBoxBusinessClass(); 
			if(busVect.size() > 0){
				for(int i = 0; i < busVect.size(); i++){
					sql = new StringBuffer("");
					sql.append(" INSERT INTO LIST_BOX_BUS_CLASS ");
					sql.append(" (LIST_BOX_ID, BUS_CLASS_ID,UPDATE_BY, UPDATE_DATE) ");
					sql.append(" VALUES (?,?,?,SYSDATE)");
					dSql = String.valueOf( sql);
					log.debug("dSql="+dSql);
					ps = conn.prepareStatement(dSql);
					
					BusinessClassM busM = new BusinessClassM();
					busM = (BusinessClassM)busVect.get(i);
					ps.setString(1, listBoxM.getListBoxID());
					ps.setString(2, busM.getId());
					ps.setString(3, listBoxM.getUpdateBy());
		
					ps.executeUpdate();
				}
			}
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterListBoxDAO#SearchAllBusinessClass()
	 */
	public Vector SearchAllBusinessClass() throws OrigApplicationMException {
		Vector busSearchVect = new Vector() ;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT BUS_CLASS_ID, BUS_CLASS_DESC ");
			sql.append(" FROM BUSINESS_CLASS ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			
			while(rs.next()) {
				BusinessClassM businessClassM = new BusinessClassM(); 
				businessClassM.setId(rs.getString(1));
				businessClassM.setDescription(rs.getString(2));
				busSearchVect.add(businessClassM);
			}
			return busSearchVect;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public Vector SearchBusinessClassByDesc(String busClassID)
			throws OrigApplicationMException {
		Vector busSearchVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT BUS_CLASS_ID, BUS_CLASS_DESC ");
			sql.append(" FROM BUSINESS_CLASS  WHERE BUS_CLASS_ID like ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("busClassID=" + busClassID);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, busClassID+"%");

			rs = ps.executeQuery();
			
			while(rs.next()) {
				BusinessClassM businessClassM = new BusinessClassM(); 
				businessClassM.setId(rs.getString(1));
				businessClassM.setDescription(rs.getString(2));
				busSearchVect.add(businessClassM);
			}
			return busSearchVect;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}

}





