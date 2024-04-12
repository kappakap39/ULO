package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;

public class GetReportListFile implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(GetReportListFile.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		File security_image_dir = new File("C:"+File.separator+"Users"+File.separator+"Pipe"+File.separator+"Pictures"+File.separator);
		logger.debug("security_image_dir"+security_image_dir);
		String security_images[] = security_image_dir.list();
		JsonObjectUtil jsonObj = new JsonObjectUtil();
		for(int i=0;i<security_images.length;i++){
			logger.debug("security_images["+i+"] "+security_images[i]);
			jsonObj.CreateJsonObject(security_images[i], security_images[i]);
		}
		

		return jsonObj.returnJson();
	}

}
