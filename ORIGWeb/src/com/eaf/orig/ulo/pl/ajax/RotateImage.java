package com.eaf.orig.ulo.pl.ajax;

import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;

public class RotateImage implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(RotateImage.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		Vector<PLApplicationImageDataM> imageVect = applicationM.getApplicationImageVect();
		if(OrigUtil.isEmptyVector(imageVect)){
			return null;
		}		
		
		String imgID = request.getParameter("imgID");
		String rotation = request.getParameter("rotation");
		
		logger.debug("do RotateImage()..imgID "+imgID);
				
	    JsonObjectUtil json = new JsonObjectUtil();
	    BufferedImage bufferIMG = null;
	    
		try{			
			String PATH_L = getPath(request,imgID,OrigConstant.FoderImage.FODER_IMAGE_L);		
			String PATH_S = getPath(request,imgID,OrigConstant.FoderImage.FODER_IMAGE_S);
			
			ImageUtil imageUtil = ImageUtil.getInstance();
				bufferIMG = imageUtil.readImage(PATH_S);
				bufferIMG = imageUtil.rotateImage(bufferIMG,270);
				imageUtil.writeImage(bufferIMG, PATH_S, ImageUtil.IMAGE_TYPE.PNG);
				
				bufferIMG = imageUtil.readImage(PATH_L);
				bufferIMG = imageUtil.rotateImage(bufferIMG,270);
		    	imageUtil.writeImage(bufferIMG,PATH_L, ImageUtil.IMAGE_TYPE.PNG);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			System.gc();
		}
		
		try{
			if("NCB".equals(rotation)){
				json.CreateJsonObject("td-"+imgID+"-1",NcbSelectedImages.CreateElementImage(getImageID(imageVect, imgID),"1"));
				json.CreateJsonObject("td-"+imgID+"-2",NcbSelectedImages.CreateElementImage(getImageID(imageVect, imgID),"2"));
			}else{
				json.CreateJsonObject("td-"+imgID,LoadMainImageThumbnail.CreateImage(getImageID(imageVect, imgID)));
			}
		}catch(Exception e){
			logger.error("ERROR "+e.getMessage());
		}		
		logger.debug("success RotateImage()..imgID "+imgID);
				
		return json.returnJson();
	}
		
	public String getPath(HttpServletRequest request,String imgID ,String type)throws Exception{
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		Vector<PLApplicationImageDataM> imageVect = applicationM.getApplicationImageVect();
		if(!OrigUtil.isEmptyVector(imageVect)){
			Vector<PLApplicationImageSplitDataM> splitVect = null;
			for(PLApplicationImageDataM imageM : imageVect) {
				splitVect = imageM.getAppImageSplitVect();
				if(!OrigUtil.isEmptyVector(splitVect)){					
					for(PLApplicationImageSplitDataM splitM : splitVect){
						if(null != imgID && imgID.equals(splitM.getImgID())){
							return ImageUtil.IMAGEPath(type, splitM);
						}
					}
				}
			}
		}
		return "";
	}
	
	public PLApplicationImageSplitDataM getImageID(Vector<PLApplicationImageDataM> imageVect ,String imgID){
		for(PLApplicationImageDataM imageM : imageVect) {
			if(!OrigUtil.isEmptyVector(imageM.getAppImageSplitVect())){
				for(PLApplicationImageSplitDataM splitM : imageM.getAppImageSplitVect()) {
					if( null != splitM.getImgID() && splitM.getImgID().equals(imgID)){
						return splitM;
					}
				}
			}
		}
		return new PLApplicationImageSplitDataM();
	}

}
