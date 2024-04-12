package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationImageMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ApplicationImageDataM;
import com.eaf.orig.shared.model.ImageCategoryDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

public class OrigApplicationImageMDAOImpl extends OrigObjectDAO implements OrigApplicationImageMDAO{
	
	private static Logger logger = Logger.getLogger(OrigApplicationImageMDAOImpl.class);
	
	public OrigApplicationImageMDAOImpl(){
		super();
	}

	@Override
	public Vector<ImageCategoryDataM> LoadModelImageCategoryDataM(String appRecordId) throws OrigApplicationImageMException{
		logger.debug("[OrigApplicationImageMDAOImpl]..[LoadModelApplicationImageDataM].. appRecordId "+appRecordId);
		
		Vector<ImageCategoryDataM> imageVect =  new Vector<ImageCategoryDataM>();
		
		Vector<ImageCategoryDataM> imageCategoryVect = SelectApplicationImageGroupCategory(appRecordId);
		
		if(imageCategoryVect != null && imageCategoryVect.size() > 0){
			
			for(int i = 0; i<imageCategoryVect.size(); i++){
				
				ImageCategoryDataM imageCategoryM = imageCategoryVect.elementAt(i);
				
				logger.debug("[OrigApplicationImageMDAOImpl]..[LoadModelApplicationImageDataM].. Document Category ID "+imageCategoryM.getDocumentCategoryID());
				
				Vector<ApplicationImageDataM> applicationImageVect = SelectTableORIG_APPLICATION_IMGByCategory(appRecordId,imageCategoryM.getDocumentCategoryID());
					
				imageCategoryM.setApplicationImageVect(applicationImageVect);
				
				imageVect.add(imageCategoryM);
				
			}
			
		}		
		return (imageVect == null)? new Vector<ImageCategoryDataM>():imageVect;
	}
	
	private Vector<ImageCategoryDataM> SelectApplicationImageGroupCategory(String appRecordID) throws OrigApplicationImageMException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector<ImageCategoryDataM> imageCategoryVect = new Vector<ImageCategoryDataM>();
		Connection conn = null;
		try{
			
			conn = getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			sql.append(" select distinct oais.document_category from orig_application_img oai,orig_application_img_split oais ");
			
			sql.append(" where oai.app_img_id = oais.app_img_id and oai.active_status = ? ");
			
			sql.append(" and oai.application_record_id = ? ");
			
			sql.append(" order by document_category ");
			
//			logger.debug("[OrigApplicationImageMDAOImpl]..[SelectApplicationImageCategory].. sql "+sql.toString());
			
			ps = conn.prepareStatement(sql.toString());
			
			int index = 0;
			
			ps.setString(++index, "A");
			
			ps.setString(++index, appRecordID);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				ImageCategoryDataM imageCategoryM = new ImageCategoryDataM();
				
				imageCategoryM.setDocumentCategoryID(rs.getString(1));				
								
				imageCategoryVect.add(imageCategoryM);
				
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationImageMException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {				
				logger.fatal(e.getLocalizedMessage());
			}
		}
				
		return (imageCategoryVect == null)? new Vector<ImageCategoryDataM>():imageCategoryVect;		
	}
	
	private Vector<ApplicationImageDataM> SelectTableORIG_APPLICATION_IMGByCategory(String appRecordID ,String documentCategoryID) throws OrigApplicationImageMException{
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector<ApplicationImageDataM> applicationImageVect = new Vector<ApplicationImageDataM>();
		Connection conn = null;
		try{
			
			conn = getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			sql.append(" select oais.img_id, oais.app_img_id, oais.path, oais.real_path, oais.file_name, oais.file_type, oais.document_category, ");
			
			sql.append(" oais.create_date, oais.create_by, oais.update_date, oais.update_date  ");
			
			sql.append(" from orig_application_img oai, orig_application_img_split oais ");
			
			sql.append(" where oai.app_img_id = oais.app_img_id ");
			
			sql.append(" and oai.active_status = ? and oai.application_record_id = ?  and oais.document_category = ? ");
			
//			logger.debug("[OrigApplicationImageMDAOImpl]..[SelectTableORIG_APPLICATION_IMGByCategory].. sql "+sql.toString());
			
			ps = conn.prepareStatement(sql.toString());
			
			int index = 0;
			
			ps.setString(++index,OrigConstant.Status.STATUS_ACTIVE);
			
			ps.setString(++index, appRecordID);
			
			ps.setString(++index, documentCategoryID);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				
				ApplicationImageDataM applicationImageM = new ApplicationImageDataM();
				
				applicationImageM.setImgID(rs.getString(1));
				
				applicationImageM.setAppImgID(rs.getString(2));
				
				applicationImageM.setPath(rs.getString(3));
				
				applicationImageM.setRealPath(rs.getString(4));
				
				applicationImageM.setFileName(rs.getString(5));
				
				applicationImageM.setFileType(".PNG");
				
				applicationImageM.setDocumentCategory(rs.getString(7));
				
				applicationImageM.setCreateDate(this.parseDate(rs.getDate(8)));
				
				applicationImageM.setCreateBy(rs.getString(9));
				
				applicationImageM.setUpdateDate(this.parseDate(rs.getDate(10)));
				
				applicationImageM.setUpdateBy(rs.getString(11));
				
				applicationImageVect.add(applicationImageM);			
			}
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationImageMException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {				
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
		return (applicationImageVect == null)? new Vector<ApplicationImageDataM>():applicationImageVect;
	}
	
	public void saveUpdateModelImageCategoryDataM(ImageCategoryDataM imageCateM)  throws OrigApplicationImageMException{

		logger.debug("[OrigApplicationImageMDAOImpl]..[saveUpdateModelApplicationImageDataM].. ");
		
		ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
		
		String appImgID = generatorManager.generateUniqueIDByName(EJBConstant.APP_IMG_ID);
		
		CreateTableORIG_APPLICATION_IMG(appImgID , imageCateM);
		
		if(imageCateM != null && imageCateM.getApplicationImageVect().size() > 0 ){
			
			for(ApplicationImageDataM appImgM : imageCateM.getApplicationImageVect()){
					
				CreateTableORIG_APPLICATION_IMG_SPLIT(appImgID,appImgM);
				
			}
			
		}
		
	}
	
	private void CreateTableORIG_APPLICATION_IMG(String appImgID ,ImageCategoryDataM imageCateM) throws OrigApplicationImageMException{
		
		PreparedStatement ps = null;
		Connection conn = null;
		 try{
			 
			 conn = getConnection();
			 
			 StringBuffer sql = new StringBuffer();
			 
			 sql.append(" INSERT INTO ORIG_APPLICATION_IMG ( APPLICATION_RECORD_ID , APP_IMG_ID , REF_NO , PATH , REAL_PATH , FILE_NAME , FILE_TYPE ,   ");	
			 sql.append(" ACTIVE_STATUS , CREATE_DATE , CREATE_BY , UPDATE_DATE , UPDATE_BY )");
			 sql.append(" VALUES (?,?,?,?,?,?,?,?,SYSDATE,?,SYSDATE,?)");
			
			 logger.debug("Sql = " + sql.toString());
			 
			 ps = conn.prepareStatement(sql.toString());
			 
			 int index = 0;
			 			 
			 ps.setString(++index, imageCateM.getAppRecordID());
			 
			 ps.setString(++index,appImgID);
			 
			 ps.setString(++index,imageCateM.getRefNo());
			 
			 ps.setString(++index,imageCateM.getPath());
			 
			 ps.setString(++index,imageCateM.getRealPath());
			 
			 ps.setString(++index,imageCateM.getFileName());
			 
			 ps.setString(++index,imageCateM.getFileType());
			 
			 ps.setString(++index,imageCateM.getActiveStatus());
			 
			 ps.setString(++index,imageCateM.getCreateBy());
			 
			 ps.setString(++index,imageCateM.getUpdateBy());
			 
			 ps.executeUpdate();
			 
		 }catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationImageMException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {				
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
	}
	private void CreateTableORIG_APPLICATION_IMG_SPLIT(String appImgID ,ApplicationImageDataM applicationImageM) throws OrigApplicationImageMException{
		

		PreparedStatement ps = null;
		Connection conn = null;
		 try{
			 
			 conn = getConnection();
			 
			 StringBuffer sql = new StringBuffer();
			 
			 sql.append(" INSERT INTO ORIG_APPLICATION_IMG_SPLIT ( IMG_ID , APP_IMG_ID , PATH , REAL_PATH , FILE_NAME , FILE_TYPE , DOCUMENT_CATEGORY , ");	
			 sql.append(" CREATE_DATE , CREATE_BY , UPDATE_DATE , UPDATE_BY  )  ");
			 sql.append(" VALUES (?,?,?,?,?,?,?,SYSDATE,?,SYSDATE,?)");
			
			 logger.debug("Sql = " + sql.toString());
			 
			 ps = conn.prepareStatement(sql.toString());
			 
			 int index = 0;
			
			 ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			 
			 ps.setString(++index, generatorManager.generateUniqueIDByName(EJBConstant.IMG_ID));
			 
			 ps.setString(++index,appImgID);
			 
			 ps.setString(++index,applicationImageM.getPath());
			 
			 ps.setString(++index,applicationImageM.getRealPath());
			 
			 ps.setString(++index,applicationImageM.getFileName());
			 
			 ps.setString(++index,applicationImageM.getFileType());
			 
			 ps.setString(++index,applicationImageM.getDocumentCategory());
			 
			 ps.setString(++index,applicationImageM.getCreateBy());
			 
			 ps.setString(++index,applicationImageM.getUpdateBy());
			 
			 ps.executeUpdate();
			 
		 }catch (Exception e) {
			 logger.fatal(e.getLocalizedMessage());
			throw new OrigApplicationImageMException(e.getMessage());
		}finally{
			try {
				closeConnection(conn,ps);
			} catch (Exception e) {				
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
	}
	
}
