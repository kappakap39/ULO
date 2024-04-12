package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationImageSplitDataM;

public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	static Logger logger = Logger.getLogger(GetImage.class);	
    public GetImage() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String imgID = request.getParameter("imgID");
		String type  = request.getParameter("type");
		
		response.setDateHeader("Last-Modified",System.currentTimeMillis());
		response.setHeader("Cache-Control", "no-cache,no-store");
		response.setDateHeader("Expires",0);
		response.setContentType("image/png");
		
		logger.debug(">>> GetImage().imgID "+imgID);
		logger.debug(">>> GetImage().type "+type);
		
		OutputStream output = null;
		FileInputStream is = null;
		try{
			String PATH = getPath(request,imgID,type);			
			logger.debug("PATH >> "+PATH);
			
			if(!OrigUtil.isEmptyString(PATH)){
				is = new FileInputStream(new File(PATH));
			 	output = response.getOutputStream();		
				if(null != is){			
					int read = 0;
					byte[] bytes = new byte[1024];
					while((read = is.read(bytes)) != -1){
						output.write(bytes, 0, read);
					}				
				}
			}
		}catch(Exception e){
			logger.error("ERROR "+e.getMessage());
		}finally{
			if(null != output){
				output.close();
				output.flush();
			}
			if(null != is){
				is.close();				
			}
			is = null;
			output = null;
		}
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
}
