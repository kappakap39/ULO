package com.eaf.orig.ulo.pl.ajax;

//import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.ApplicationImageDataM;
import com.eaf.orig.shared.model.ImageCategoryDataM;

/**
 * Servlet implementation class GetImageSizeServlet
 */
public class GetImageSizeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(GetImageSizeServlet.class);  
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImageSizeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("[GetImageSizeServlet]..");
		
		String fullRealPath = null;
		
		String typeImg = request.getParameter("typeImg");
		
		String activeImgID = request.getParameter("activeImgID");
		
		String categoryID = request.getParameter("categoryID");
		
		if(activeImgID == null ||categoryID == null ){
			return;
		}

		Vector<ImageCategoryDataM> imagetCategoryVect = DisplayThumbnailImageServlet.getImageCategoryVectFormHendle(request);
		
		ApplicationImageDataM applicationImgM = DisplayThumbnailImageServlet.getApplicationImageDataMByImgID(categoryID, activeImgID, imagetCategoryVect);
				
		if("LVIEW".equalsIgnoreCase(typeImg.trim())){
			fullRealPath =  DisplayThumbnailImageServlet.CreateImageRealPath(applicationImgM,OrigConstant.FoderImage.FODER_IMAGE_L, OrigConstant.TypeImage.TYPE_IMAGE_L);		
		}else{
			fullRealPath =  DisplayThumbnailImageServlet.CreateImageRealPath(applicationImgM, OrigConstant.FoderImage.FODER_IMAGE_S, OrigConstant.TypeImage.TYPE_IMAGE_S);
		}
		
		logger.debug("[GetImageSizeServlet]...Full Real Path "+fullRealPath);
		
//		BufferedImage buffImageS = ImageUtil.readImage(fullRealPath);

		response.setContentType("text");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store");
		response.setDateHeader("Expires", 0);
		
		PrintWriter pw = response.getWriter();			
//		pw.write(buffImageS.getHeight()+"|"+buffImageS.getWidth());
		pw.close();		
	}

}
