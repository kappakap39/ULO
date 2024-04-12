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

public class LoadMainImageThumbnail  implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(LoadMainImageThumbnail.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		Vector<PLApplicationImageDataM> imageVect = applicationM.getApplicationImageVect();
		if(OrigUtil.isEmptyVector(imageVect)){
			return "";
		}		
		JsonObjectUtil json = new JsonObjectUtil();
			json.CreateJsonObject("main-image-thumbnail", CreateThumbnailImage(imageVect, request));		
		return json.returnJson();
	}
	
	public String CreateThumbnailImage(Vector<PLApplicationImageDataM> imageVect,HttpServletRequest request){
		Vector<PLApplicationImageSplitDataM> splitVect = null;
		StringBuilder str = new StringBuilder("");
		str.append("<table class='table-main-img-thumbnail'><tr valign='top'>");
		for(PLApplicationImageDataM imageM : imageVect) {
			splitVect = imageM.getAppImageSplitVect();
			if(!OrigUtil.isEmptyVector(splitVect)){					
				for (PLApplicationImageSplitDataM splitM : splitVect) {
					try{
						str.append("<td valign='top' id='td-"+splitM.getImgID()+"'>");
						str.append(CreateImage(splitM));
						str.append("</td>");
					}catch(Exception e){
						logger.error("ERROR "+e.getMessage());
					}
				}
			}
		}
		str.append("</tr></table>");
		return str.toString();
	}
	
	public static String CreateImage(PLApplicationImageSplitDataM splitM) throws IOException{
		StringBuilder str = new StringBuilder("");
		String IMAGE_PATH = ImageUtil.IMAGEPath(OrigConstant.TypeImage.TYPE_IMAGE_S, splitM);
		
		ImageUtil imageUtil = ImageUtil.getInstance();
		BufferedImage image = imageUtil.readImage(IMAGE_PATH);
		
		String width = String.valueOf(image.getWidth());
		String height = String.valueOf(image.getHeight());	
		long time = new Date().getTime();
		str.append("<div class='img-main-thumbnail' id='"+splitM.getImgID()+"' width='"+width+"' height='"+height+"'>");
			str.append("<img id='"+splitM.getFileName()+"' src='GetImage?imgID="+splitM.getImgID()+"&type="+OrigConstant.TypeImage.TYPE_IMAGE_S+"&"+time+"'/>");
		str.append("</div>");
		
		if(null != image){
			image.flush();
			image = null;
		}		
		return str.toString();
	}
	
}
