package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationImageDataM;
import com.eaf.orig.shared.model.ImageCategoryDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;


public class DisplayThumbnailImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private  Logger logger = Logger.getLogger(DisplayThumbnailImageServlet.class); 

    public DisplayThumbnailImageServlet() {
        super();       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
		
			logger.debug("[DisplayThumbnailImageServlet]... ");
			
			String thumbnailResult = "";		
					
			Vector<ImageCategoryDataM> imagetCategoryVect = getImageCategoryVectFormHendle(request);
			
			if(imagetCategoryVect == null  || imagetCategoryVect.size() == 0){	
				
				logger.debug("[DisplayThumbnailImageServlet]... Image Expire");
	
			}else{		
				thumbnailResult = DrowModuleThumbnail(imagetCategoryVect,request);		
			} 
			
			DisplayResponse(response,thumbnailResult);
			
		}catch(Exception e){
			logger.error("DisplayThumbnailImageServlet error "+e.getMessage());
			DisplayResponse(response,"error");
		}
		
	}
	public static void DisplayResponse(HttpServletResponse response ,String value) throws IOException{
		 response.setContentType("text");
		 response.setHeader("Pragma", "No-cache");
		 response.setHeader("Cache-Control", "no-cache,no-store");
		 response.setDateHeader("Expires", 0);
			
		 PrintWriter pw = response.getWriter();			
	     pw.write(value);
		 pw.close();
	}
	public static String DrowModuleThumbnail(Vector<ImageCategoryDataM> imagetCategoryVect,HttpServletRequest request){
		
		StringBuilder htmlTag = new StringBuilder();
		
		htmlTag.append(DrawTabImageCategory(imagetCategoryVect));
		
		ImageCategoryDataM imageCategoryM = imagetCategoryVect.elementAt(0);
		
		Vector<ApplicationImageDataM> applicationImageVect = GetObjectImageThumbnailFromCategory(imageCategoryM.getDocumentCategoryID(),imagetCategoryVect);
		
		htmlTag.append(DrawThumbnailImage(applicationImageVect,request));
		
		return htmlTag.toString();
	}
	

	public static String DrawTabImageCategory(Vector<ImageCategoryDataM> imageCategoryVect){
		
		StringBuilder htmlTag = new StringBuilder();
		
		ORIGCacheUtil origCacheUtil = new ORIGCacheUtil();
		
		htmlTag.append("<div id = \"tabImg\" >");
		
			htmlTag.append("<div id=\"tabImage\"> <ul>");
			
			if(imageCategoryVect != null && imageCategoryVect.size() > 0){
				
				for(int i = 0; i<imageCategoryVect.size(); i++){
					
					ImageCategoryDataM imageCategoryM = imageCategoryVect.elementAt(i);
					
					String javascript = "javascript:LoadImageThumbanil('"+imageCategoryM.getDocumentCategoryID()+"')";
					
					String currentTab = "";
					String idTab ="";
					if(i==0){
						currentTab = " class =\"current\"";
					}
					idTab = "id = \"tab"+imageCategoryM.getDocumentCategoryID()+"\" ";
					htmlTag.append("<li "+idTab+currentTab+"><a href=\""+javascript+"\">" +
									"<span>" 
									+origCacheUtil.getORIGCacheDisplayNameDataM(37, imageCategoryM.getDocumentCategoryID())
									+"</span>"+
									"</a></li>");
				}
				
			}
			
			htmlTag.append("</ul></div>");
		
		htmlTag.append("</div>");
		
		return htmlTag.toString();
	}
	public static String DisplayHiddenTagImg(){		
		StringBuilder htmlTag = new StringBuilder();		
		htmlTag.append("<input id=\"activeImgID\" type=\"hidden\" value=\"\"/>");
		htmlTag.append("<input id=\"imgThumbnailID\" type=\"hidden\" value=\"\"/>");
		htmlTag.append("<input id=\"categoryID\" type=\"hidden\" value=\"\"/>");	
		return htmlTag.toString();
	}
	public static String DrawThumbnailImage(Vector<ApplicationImageDataM> applicationImageVect , HttpServletRequest request){
		
		StringBuilder htmlTag = new StringBuilder();
		
		htmlTag.append("<div id = \"thumbnailImg\">");
		
		htmlTag.append("<table id = \"tableThumbanil\"><tr>");
		
		if(applicationImageVect != null && applicationImageVect.size() >0){
			
			for(int i = 0; i<applicationImageVect.size(); i++){
				
				ApplicationImageDataM applicationImageM = applicationImageVect.elementAt(i);
				
				htmlTag.append("<td valign=\"top\">");
				
				if(i == 0){
					htmlTag.append("<input id=\"activeImgID\" type=\"hidden\" value=\""+applicationImageM.getImgID()+"\"/>");
					htmlTag.append("<input id=\"imgThumbnailID\" type=\"hidden\" value=\""+applicationImageM.getFileName()+"\"/>");
					htmlTag.append("<input id=\"categoryID\" type=\"hidden\" value=\""+applicationImageM.getDocumentCategory()+"\"/>");					
				}
				
				String jsMouseOutOver = "onmouseover=\"mouseOverThumbnail('"+applicationImageM.getImgID()+"')\" onmouseout=\"mouseOutThumbnail('"+applicationImageM.getImgID()+"')\"";
				
				String jsonclick = "onclick=\"changeImg('"+applicationImageM.getImgID()+"','"+applicationImageM.getFileName()+"')\"";
				
				htmlTag.append("<div class=\"Imglist\" id=\""+applicationImageM.getImgID()+"\"" +jsMouseOutOver+" "+jsonclick+ ">");
				
				String imageSPath = CreateImageSrcThumbnailHttpPath(applicationImageM,OrigConstant.FoderImage.FODER_IMAGE_S,OrigConstant.TypeImage.TYPE_IMAGE_S,request);
				
				String imageLpath = CreateImageSrcThumbnailHttpPath(applicationImageM,OrigConstant.FoderImage.FODER_IMAGE_L,OrigConstant.TypeImage.TYPE_IMAGE_L,request);
				
				Date dateCurrent = new Date();
				
				htmlTag.append("<img id=\""+applicationImageM.getFileName()+"\" src=\""+imageSPath+"?"+dateCurrent+"\">");
				
				htmlTag.append("<input id=\""+applicationImageM.getImgID()+"imgSPath\" type=\"hidden\" value=\""+imageSPath+"\"/>");
				htmlTag.append("<input id=\""+applicationImageM.getImgID()+"imgLPath\" type=\"hidden\" value=\""+imageLpath+"\"/>");	
				
				htmlTag.append("</div>");
				
				htmlTag.append("</td>");
				
			}
			
		}
		
		htmlTag.append("<tr></table>");
		
		htmlTag.append("</div>");
		
		return htmlTag.toString();		
	}
	
	public static Vector<ApplicationImageDataM> GetObjectImageThumbnailFromCategory(String categoryID , Vector<ImageCategoryDataM> imageCategoryVect){
		
		Vector<ApplicationImageDataM> applicationImageVect = new Vector<ApplicationImageDataM>();		
		
		if( imageCategoryVect != null && imageCategoryVect.size() > 0){
			
			for(int i = 0; i< imageCategoryVect.size(); i++) {
				
				ImageCategoryDataM imageCategoryM = imageCategoryVect.elementAt(i);
				
				if(imageCategoryM.getDocumentCategoryID().equalsIgnoreCase(categoryID)){
					
					applicationImageVect = imageCategoryM.getApplicationImageVect();
					
					break;
				}	
				
			}
			
		}
		
		return ( applicationImageVect == null )? new Vector<ApplicationImageDataM>(): applicationImageVect ;		
	}
	public static ApplicationImageDataM getApplicationImageDataMByImgID(String categoryID,String imgID, Vector<ImageCategoryDataM> imageCategoryVect){
		
		ApplicationImageDataM applicationImageM = new ApplicationImageDataM();
		
		Vector<ApplicationImageDataM> applicationImageVect = GetObjectImageThumbnailFromCategory(categoryID,imageCategoryVect);
		
		if( applicationImageVect != null && applicationImageVect.size() >0){
			
			for(int i = 0 ; i < applicationImageVect.size(); i++){
				
				ApplicationImageDataM appImageM = applicationImageVect.elementAt(i);
				
				if(appImageM.getImgID().equalsIgnoreCase(imgID)){
					applicationImageM = appImageM;
					break;
				}
				
			}
		}
		
		return (applicationImageM==null)? new ApplicationImageDataM():applicationImageM;		
	}
	public static String CreateImageSrcThumbnailHttpPath(ApplicationImageDataM applicationImageM ,String foderType ,String imageType ,HttpServletRequest request){
		
		ORIGCacheUtil origCacheUtil = new ORIGCacheUtil();
		
		StringBuilder serverPath = new StringBuilder();
		
		serverPath.append("http://"+request.getServerName()+":"+request.getServerPort()+"/");
		
		serverPath.append(origCacheUtil.getGeneralParamByCode(OrigConstant.ParamCode.CONTEXT_IMAGE_PATH));
		
		serverPath.append(applicationImageM.getPath()+applicationImageM.getDocumentCategory()+"/"+foderType
								+"/"+applicationImageM.getFileName()+"_"+imageType+applicationImageM.getFileType());
		
		return serverPath.toString();
	}
	public static String CreateImageRealPath(ApplicationImageDataM applicationImageM,String foderType ,String imageType){

		StringBuilder realPath = new StringBuilder();
		
		realPath.append(applicationImageM.getRealPath()+applicationImageM.getDocumentCategory()+File.separator+foderType);
		
		realPath.append(File.separator+applicationImageM.getFileName()+"_"+imageType+applicationImageM.getFileType());		
			
		return realPath.toString();
	}
	public static Vector<ImageCategoryDataM> getImageCategoryVectFormHendle(HttpServletRequest request){
		
		PLApplicationDataM plApplicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		if(plApplicationM == null) plApplicationM = new PLApplicationDataM();
		
		return plApplicationM.getImageCategoryVect();
	}
	
}
