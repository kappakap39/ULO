package com.eaf.service.rest.controller.img;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.controller.ServiceController;

@RestController
@RequestMapping("/service/image")
public class ImageServiceController {
	private static transient Logger logger = Logger.getLogger(ImageServiceController.class);
	@RequestMapping(value="/user/{userName}",produces={"image/png","image/jpeg"}, method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<InputStreamResource> send(@PathVariable("userName") String userName){
    	String CACHE_NAME_USER = ServiceCache.getConstant("CACHE_NAME_USER");
    	logger.debug("userName : "+userName);
    	String fileName = ServiceCache.getName(CACHE_NAME_USER,userName,"IMAGE_FILE");
    	logger.debug("fileName : "+fileName);
		String userImagePath = ServiceCache.getProperty("USER_IMAGE_PATH")+File.separator+fileName;
		logger.debug("userImagePath : "+userImagePath);
		if(Util.empty(fileName)|| (Util.empty(userImagePath)) || !(new File(userImagePath).exists())){
			logger.debug("Not Found Image. Do Get Default Image..");
			String image = "user.png";
			userImagePath = ServiceController.webContentPath+"image"+File.separator+image;
		}
		logger.debug("userImagePath >> "+userImagePath);
		try{
			File file = new File(userImagePath);
			return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(file)),HttpStatus.OK);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/cardImage/{cardTypeId}",produces={"image/png","image/jpeg"}, method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<InputStreamResource> getCardImage(@PathVariable("cardTypeId") String cardTypeId){
		String CACHE_PRODUCT_IMAGE = ServiceCache.getConstant("CACHE_PRODUCT_IMAGE");
    	logger.debug("cardTypeId : "+cardTypeId);
    	String cardImagePath = ServiceCache.getName(CACHE_PRODUCT_IMAGE,cardTypeId,"IMAGE_PATH");
		logger.debug("cardImagePath : "+cardImagePath);
		if((Util.empty(cardImagePath)) || !(new File(cardImagePath).exists())){
			logger.debug("Not Found Image. Do Get Default Image..");
			String image = "EmptyCard.png";
			cardImagePath = ServiceController.webContentPath+"image"+File.separator+image;
		}
		logger.debug("cardImagePath >> "+cardImagePath);
		try{
			File file = new File(cardImagePath);
			return new ResponseEntity<InputStreamResource>(new InputStreamResource(new FileInputStream(file)),HttpStatus.OK);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
