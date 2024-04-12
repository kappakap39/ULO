package com.eaf.orig.ulo.pl.ajax;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
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
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class NcbSelectedImages implements AjaxDisplayGenerateInf {

	Logger logger = Logger.getLogger(DisplayNcbImage.class);

	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {		
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		
		Vector<PLNCBDocDataM> ncbDocVect = personalM.getNcbDocVect();
		
		if(OrigUtil.isEmptyVector(ncbDocVect)){
			return "";
		}
		
		Vector<PLApplicationImageDataM> imageVect = applicationM.getApplicationImageVect();
		
		if(OrigUtil.isEmptyVector(imageVect)){
			return "";
		}
		
		JsonObjectUtil json = new JsonObjectUtil();		
		
		json.CreateJsonObject("thumbnail-img-2", CreateThumbnailImage(ncbDocVect, imageVect, "2"));
		json.CreateJsonObject("thumbnail-img-1", CreateThumbnailImage(ncbDocVect, imageVect, "1"));	
			
		return json.returnJson();
	}
	
	public String CreateThumbnailImage(Vector<PLNCBDocDataM> ncbDocVect ,Vector<PLApplicationImageDataM> imageVect,String frameID){
		StringBuilder str = new StringBuilder();
		str.append("<table class='table-ncb-thumbnail'><tr valign='top'>");
		for(PLNCBDocDataM ncbDocM : ncbDocVect) {
			try{
				PLApplicationImageSplitDataM splitM = getImageSplitM(ncbDocM.getImgID(), imageVect);
				if(null != splitM){
					str.append("<td valign='top' id='td-"+splitM.getImgID()+"-"+frameID+"'>");
					str.append(CreateElementImage(splitM, frameID));
					str.append("</td>");
				}
			}catch(Exception e){
				logger.error("ERROR "+e.getMessage());
			}
		}
		str.append("</tr></table>");
		return str.toString();
	}
	public PLApplicationImageSplitDataM getImageSplitM(String imgID,Vector<PLApplicationImageDataM> imageVect){
		for (PLApplicationImageDataM imageM : imageVect) {
			for (PLApplicationImageSplitDataM splitM : imageM.getAppImageSplitVect()){
				if(splitM.getImgID().equals(imgID)){
					return splitM;
				}
			}
		}
		return null;
	}
	public static String CreateElementImage(PLApplicationImageSplitDataM splitM ,String frameID)throws IOException{
		StringBuilder IMG = new StringBuilder("");
		ImageUtil imageUtil = ImageUtil.getInstance();
		String PATH = ImageUtil.IMAGEPath(OrigConstant.TypeImage.TYPE_IMAGE_S,splitM);	
		
		BufferedImage buffer = imageUtil.readImage(PATH);
		String width = String.valueOf(buffer.getWidth());
		String height = String.valueOf(buffer.getHeight());
		
		long time = new Date().getTime();
		
		IMG.append("<div id='"+splitM.getImgID()+"-"+frameID+"' class='img-ncb-thumbnail"+frameID+" "+splitM.getImgID()+"' width='"+width+"' height='"+height+"'>");
			IMG.append("<img id='"+splitM.getFileName()+frameID+"' src='GetImage?imgID="+splitM.getImgID()+"&type="+OrigConstant.TypeImage.TYPE_IMAGE_S+"&"+time+"'/>");
		IMG.append("</div>");
		
		if(null != buffer){
			buffer.flush();
			buffer = null;
		}
		
		return IMG.toString();
	}
	
}
