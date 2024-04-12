package com.eaf.service.rest.resource.img;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.OutputStream;

//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.StreamingOutput;

//import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.util.Util;
//import com.eaf.service.common.api.ServiceCache;
//import com.eaf.service.common.controller.ServiceController;

//@Path("/service")
public class ImageService {
//	private static transient Logger logger = Logger.getLogger(ImageService.class);
//    @GET
//    @Path("/fullsize/{templateId}/{uniqueId}/{imageId}")
//    @Produces({"image/png","image/jpeg"})
//    public Response getImage(@PathParam("templateId") String templateId,@PathParam("uniqueId") String uniqueId,@PathParam("imageId") String imageId){
//    	logger.debug("templateId >> "+templateId);
//    	logger.debug("imageId >> "+imageId);
//		String templatePath = getImageTempletePath(templateId,uniqueId);
//		logger.debug("templatePath >> "+templatePath);
//		try{
//			File folder = new File(templatePath);
//			File[] listOfFiles = folder.listFiles();
//			if(null != listOfFiles){
//				for(final File file : listOfFiles){
//					String Name = file.getName();
//					String fileName = Name.replaceFirst("[.][^.]+$","");	
//					if(!Util.empty(fileName)&&!fileName.contains("thumb")&&!file.isDirectory()&&fileName.contains(imageId)){
//						StreamingOutput stream = new StreamingOutput() {							
//				            @Override
//				            public void write(OutputStream output) throws IOException {
//				            	FileInputStream input = new FileInputStream(file);
//				                try{
//				                	 int bytes;
//				                     while ((bytes = input.read()) != -1) {
//				                         output.write(bytes);
//				                     }
//				                }catch(Exception e){
//				                  logger.fatal("ERROR",e);
//				                }finally{
//				                    if (output != null) output.close();
//				                    if (input != null) input.close();
//				                }
//				            }
//					    };
//					    return Response.ok(stream).build();
//					}
//				}
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}
//    	return Response.status(Response.Status.CONFLICT).build();
//    }
//	private String getImageTempletePath(String qr1,String qr2){
//		String IMAGE_TEMPLATE_PATH = ServiceCache.getProperty("IMAGE_TEMPLATE_PATH");
//		String imageTemplatePath = IMAGE_TEMPLATE_PATH+qr1+File.separator+"UniqueSmart"+File.separator+qr2;
//		File folder = new File(imageTemplatePath);
//		if(!folder.exists()){
//			imageTemplatePath = IMAGE_TEMPLATE_PATH+qr1;
//		}
//		return imageTemplatePath;
//	}
//    @GET
//    @Path("/thumbnail/{templateId}/{uniqueId}/{imageId}")
//    @Produces({"image/png","image/jpeg"})
//    public Response getThumbnail(@PathParam("templateId") String templateId,@PathParam("uniqueId") String uniqueId,@PathParam("imageId") String imageId){
//    	logger.debug("templateId >> "+templateId);
//    	logger.debug("imageId >> "+imageId);
//		String templatePath = getImageTempletePath(templateId,uniqueId);
//		logger.debug("templatePath >> "+templatePath);
//		try{
//			File folder = new File(templatePath);
//			File[] listOfFiles = folder.listFiles();
//			if(null != listOfFiles){
//				for(final File file : listOfFiles){
//					String Name = file.getName();
//					String fileName = Name.replaceFirst("[.][^.]+$","");	
//					if(!Util.empty(fileName)&&fileName.contains("thumb")&&!file.isDirectory()&&fileName.contains(imageId)){
//						StreamingOutput stream = new StreamingOutput() {							
//				            @Override
//				            public void write(OutputStream output) throws IOException {
//				            	FileInputStream input = new FileInputStream(file);
//				                try{
//				                	 int bytes;
//				                     while ((bytes = input.read()) != -1) {
//				                         output.write(bytes);
//				                     }
//				                }catch(Exception e){
//				                  logger.fatal("ERROR",e);
//				                }finally{
//				                    if (output != null) output.close();
//				                    if (input != null) input.close();
//				                }
//				            }
//					    };
//					    return Response.ok(stream).build();
//					}
//				}
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}
//    	return Response.status(Response.Status.CONFLICT).build();
//    }
//    
//    @GET
//    @Path("/image/user/{userName}")
//    @Produces({"image/png","image/jpeg"})
//    public Response getUserImage(@PathParam("userName") String userName){
//    	String CACHE_NAME_USER = ServiceCache.getProperty("CACHE_NAME_USER");
//    	logger.debug("userName >> "+userName);
//		String userImagePath = ServiceCache.getName(CACHE_NAME_USER,userName,"IMAGE_FILE");
//		if(Util.empty(userImagePath) && !(new File(userImagePath).exists())){
//			logger.debug("Not Found Image. Do Get Default Image..");
//			String image = "user.png";
//			userImagePath = ServiceController.webContentPath+"image"+File.separator+image;
//		}
//		logger.debug("userImagePath >> "+userImagePath);
//		try{
//			final File file = new File(userImagePath);
//			StreamingOutput stream = new StreamingOutput() {							
//		         @Override
//		         public void write(OutputStream output) throws IOException {
//	            	FileInputStream input = new FileInputStream(file);
//	                try{
//	                	 int bytes;
//	                     while ((bytes = input.read()) != -1) {
//	                         output.write(bytes);
//	                     }
//	                }catch(Exception e){
//	                  logger.fatal("ERROR",e);
//	                }finally{
//	                    if (output != null) output.close();
//	                    if (input != null) input.close();
//	                }
//		         }
//			};
//			return Response.ok(stream).build();
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}
//    	return Response.status(Response.Status.CONFLICT).build();
//    }
}
