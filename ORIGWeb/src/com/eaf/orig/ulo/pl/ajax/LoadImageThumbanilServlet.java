package com.eaf.orig.ulo.pl.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.model.ApplicationImageDataM;
import com.eaf.orig.shared.model.ImageCategoryDataM;

/**
 * Servlet implementation class LoadImageThumbanilServlet
 */
public class LoadImageThumbanilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoadImageThumbanilServlet.class);    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadImageThumbanilServlet() {
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
		
		logger.debug("[LoadImageThumbanilServlet]... ");
		
		String thumbnailResult = "";
		
		String categoryID = request.getParameter("categoryID");
				
		Vector<ImageCategoryDataM> imagetCategoryVect = DisplayThumbnailImageServlet.getImageCategoryVectFormHendle(request);
		
		Vector<ApplicationImageDataM> applicationImageVect = DisplayThumbnailImageServlet.GetObjectImageThumbnailFromCategory(categoryID,imagetCategoryVect);
		
		thumbnailResult = DisplayThumbnailImageServlet.DrawThumbnailImage(applicationImageVect,request);
		
		logger.debug("thumbnailResult : "+thumbnailResult);
		
		response.setContentType("text");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store");
		response.setDateHeader("Expires", 0);	
			
		PrintWriter pw = response.getWriter();			
	    pw.write(thumbnailResult);
		pw.close();
		
	}

}
