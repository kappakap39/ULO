package com.eaf.orig.rest.controller.smartdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eaf.orig.rest.controller.smartdata.dao.SmartDataDAO;
import com.eaf.orig.rest.controller.smartdata.model.SMImgM;
import com.eaf.orig.rest.controller.smartdata.model.SMMainM;
import com.eaf.orig.rest.controller.smartdata.util.SmartConditionResolver;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;

@Service
public class SmartDataService {
	@Autowired
	private SmartDataDAO smartDataDAO;
	
	public List<SMImgM> getImageByTemplateId(String templateId){
		if(templateId == null || templateId.isEmpty())
			return null;
//		#rawi remove search smart from  dao to cache
//		return smartDataDAO.selectImageByTemplateId(templateId);
		return CacheController.getCacheData(CacheConstant.CacheName.SMART_IMAGE,templateId);
	}	
	public List<SMMainM> getFieldByTemplateId(String templateId){
		if(templateId == null || templateId.isEmpty())
			return null;
//		return smartDataDAO.selectFieldByTemplateId(templateId);
		return CacheController.getCacheData(CacheConstant.CacheName.SMART_FIELD,templateId);
	}	
	public List<SMImgM> mapImageSplitByTemplateId(List<SMImgM> requestImages, String templateId){
		if(requestImages == null || requestImages.isEmpty()){
			return null;
		}		
		List<SMImgM> templates = getImageByTemplateId(templateId);
		SmartConditionResolver scr = new SmartConditionResolver(templates);		
		//Map template data to Request Image Split
		scr.mapRequestImagesWithTemplateImage(requestImages);
		return requestImages;
	}
}
