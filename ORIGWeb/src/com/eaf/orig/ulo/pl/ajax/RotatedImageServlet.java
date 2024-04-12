package com.eaf.orig.ulo.pl.ajax;

//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;
//import java.awt.image.BufferedImage;
//import java.awt.image.RenderedImage;
//import java.awt.image.renderable.ParameterBlock;
//import java.io.File;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
import java.util.Vector;

//import javax.imageio.ImageIO;
//import javax.media.jai.JAI;
//import javax.media.jai.PlanarImage;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationImageDataM;
import com.eaf.orig.shared.model.ImageCategoryDataM;

/**
 * Servlet implementation class RotatedImageServlet
 */
public class RotatedImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RotatedImageServlet.class);  
    /**
     * @see HttpServlet#HttpServlet()
     */
		
    public RotatedImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		logger.debug("[RotatedImageServlet]...Begin");
		
		String angle = request.getParameter("angle");
		
		String categoryID = request.getParameter("categoryID");
		
		String activeImgID = request.getParameter("activeImgID");
		
		Vector<ImageCategoryDataM> imagetCategoryVect = DisplayThumbnailImageServlet.getImageCategoryVectFormHendle(request);
		
		ApplicationImageDataM applicationImgM = DisplayThumbnailImageServlet.getApplicationImageDataMByImgID(categoryID, activeImgID, imagetCategoryVect);
		
		try{
				int _angle = Integer.parseInt(angle==null?"0":angle);
				
				String imagePathL = DisplayThumbnailImageServlet.CreateImageRealPath(applicationImgM,OrigConstant.FoderImage.FODER_IMAGE_L, OrigConstant.TypeImage.TYPE_IMAGE_L);	
				String imagePathS = DisplayThumbnailImageServlet.CreateImageRealPath(applicationImgM, OrigConstant.FoderImage.FODER_IMAGE_S, OrigConstant.TypeImage.TYPE_IMAGE_S);
				
				logger.debug("[RotatedImageServlet]...imagePathS "+imagePathS);
//					
//				BufferedImage buffImageS = ImageUtil.readImage(imagePathS);
//				
//				BufferedImage rotateBuffImageS = ImageUtil.rotateImage(buffImageS, _angle);
//				
//				ImageUtil.writeImage(rotateBuffImageS, imagePathS, ImageUtil.IMAGE_TYPE.PNG);
//				
//				logger.debug("[RotatedImageServlet]...imagePathL "+imagePathL);
//					
//				BufferedImage buffImageL = ImageUtil.readImage(imagePathL);
//				
//				BufferedImage rotateBuffImageL = ImageUtil.rotateImage(buffImageL, _angle);
//				
//				ImageUtil.writeImage(rotateBuffImageL, imagePathL, ImageUtil.IMAGE_TYPE.PNG);
				
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache,no-store");
				response.setDateHeader("Expires", 0);
				
		}catch (Exception e) {
			logger.debug("error :"+e.getMessage()); 
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
		}finally{
			System.gc();
		}
		
		logger.debug("[RotatedImageServlet]...End");
	}
}
