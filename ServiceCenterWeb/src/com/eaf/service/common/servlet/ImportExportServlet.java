package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.FileUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.dao.XmlFactory;
import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.ulo.model.ServiceApplicationGroupM;
import com.google.gson.Gson;

@WebServlet("/ImportExportServlet")
@MultipartConfig
public class ImportExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(ImportExportServlet.class);

    public ImportExportServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("ImportExportServlet");
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("import")){
			Collection<Part> parts = request.getParts();
			for(Part part : parts){
				if("IMPORT_FILE".equals(part.getName())){
					String FILE_NAME = FileUtil.getFilename(part);
					logger.debug("FILE_NAME : "+FILE_NAME);
					if(!Util.empty(FILE_NAME)){
						String TEMP = "C:\\FLP\\TEMP.tmp";
						File TEMP_FILE = new File(TEMP);
						FileUtils.copyInputStreamToFile(part.getInputStream(), TEMP_FILE);
						logger.debug("TEMP_FILE : "+TEMP_FILE.getAbsoluteFile());
						ZipFile zipFile = new ZipFile(TEMP_FILE);
					    Enumeration<? extends ZipEntry> entries = zipFile.entries();
					    while(entries.hasMoreElements()){
					        ZipEntry entry = entries.nextElement();
					        InputStream is = zipFile.getInputStream(entry);
					        ServiceApplicationGroupM ServicepplicationGroupData = jaxbXMLToObject(is);
					        ApplicationGroupDataM applicationGroupData = ServicepplicationGroupData.getApplicationGroup();
					        try{
					        	ORIGDAOFactory.getApplicationGroupDAO().saveUpdateOrigApplicationGroupM(applicationGroupData);
					        }catch(Exception e){
					        	logger.fatal("ERROR ",e);
					        }
					        is.close();
					    }
					    zipFile.close();
					    System.gc();
					    if(!TEMP_FILE.delete())	logger.debug("CANNOT DELETE TEMP FILE");
					    
					}
				}
			}
		}else if(process.equals("export")){
			String[] applicationGroupIds = request.getParameterValues("applicationGroupIdBox");
			File FOLDER_PATH = new File("C:\\FLP\\XML");
			File ZIP_FILE = new File("C:\\FLP\\XML.zip");
			try{
				for(String applicationGroupId : applicationGroupIds){
					
					String FILE_NAME = File.separator+applicationGroupId+".xml";
					logger.debug("applicationGroupId : "+applicationGroupId);
					ApplicationGroupDataM applicationGroupData = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
					String xml = jaxbObjectToXML(applicationGroupData);

					if(!FOLDER_PATH.exists()){
						logger.debug("FOLDER_PATH NOT FOUND THEN CREATE");
						FOLDER_PATH.mkdirs();
					}
					
					FileWriter fw = new FileWriter(FOLDER_PATH+FILE_NAME);
					fw.write(xml);
					fw.close();
				}
				
				byte[] buffer = new byte[1024];
				
				FileOutputStream fos = new FileOutputStream(ZIP_FILE);
				ZipOutputStream zos = new ZipOutputStream(fos);
				File[] files = FOLDER_PATH.listFiles();
				for (int i = 0; i < files.length; i++) {
					System.out.println("Adding file: " + files[i].getName());
					FileInputStream fis = new FileInputStream(files[i]);
					zos.putNextEntry(new ZipEntry(files[i].getName()));
					int length;
					while ((length = fis.read(buffer)) > 0) {
						zos.write(buffer, 0, length);
					}
					zos.closeEntry();
					fis.close();
				}
				zos.close();

			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}finally{
				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				response.setDateHeader("Expires", 0);
				response.setHeader("Content-disposition", "attachment;filename=Xml.zip");
				response.setContentType("multipart/form-data");			
				logger.debug("ZIP_FILE Path=" + ZIP_FILE);

				if (ZIP_FILE.exists()) {
					FileInputStream is = new FileInputStream(ZIP_FILE);
					OutputStream output = response.getOutputStream();
					logger.debug("Input Stream=" + is);
					if (is != null) {
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = is.read(bytes)) != -1) {
							output.write(bytes, 0, read);
						}
						output.flush();
						output.close();
						is.close();
					}
				}
				System.gc();;
				FileUtils.deleteDirectory(FOLDER_PATH);
				if(!ZIP_FILE.delete())	logger.debug("CANNOT DELETE ZIP");
			}
			
		}else if(process.equals("loadData")){
			String applicationGroupIds = request.getParameter("applicationGroupId");
			String[] applicationGroupIdArray = applicationGroupIds.split(",");
			ArrayList<ApplicationGroupDataM> applicationGroupDataList = new ArrayList<>();
			for(String applicationGroupId : applicationGroupIdArray){
				try{
					logger.debug("applicationGroupId : "+applicationGroupId);
					ApplicationGroupDataM applicationGroupData = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
					applicationGroupDataList.add(applicationGroupData);
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}
			}
			HashMap<String, Object> object = new HashMap<>();
			object.put("applicationGroupDataList", applicationGroupDataList);
			ResponseUtils.sendJsonResponse(response, object);
		}
	}
	
	private static String jaxbObjectToXML(ApplicationGroupDataM applicationGroupData) throws IOException {
		String xml = "";
		ServiceApplicationGroupM serviceApplicationGroup = new ServiceApplicationGroupM();
		serviceApplicationGroup.setApplicationGroup(applicationGroupData);
    	try{
    		JAXBContext context = JAXBContext.newInstance(ServiceApplicationGroupM.class);
    		Marshaller marshaller = context.createMarshaller();
    		
    		StringWriter sw = new StringWriter();
    		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		marshaller.marshal(serviceApplicationGroup, sw);
    		xml = sw.toString();
    		logger.debug("xml : "+xml);
    		List<String> keyList = XmlFactory.getXmlDAO().getPrimaryKeyAppGroup();
    		for(String key : keyList){
    			xml = xml.replace("<"+key+">", "<"+key+">-");
    		}
    		logger.debug("xml replace : "+xml);
    	} catch (JAXBException | ServiceControlException e) {
    		logger.fatal("ERROR ",e);
    	}
    	return xml;
	}
	
	private static ServiceApplicationGroupM jaxbXMLToObject(InputStream xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(ServiceApplicationGroupM.class);
			Unmarshaller un = context.createUnmarshaller();
			ServiceApplicationGroupM ServiceApplicationGroupData = (ServiceApplicationGroupM)un.unmarshal(xml);
	        Gson gson = new Gson();
	        logger.debug("ServiceApplicationGroupData : "+gson.toJson(ServiceApplicationGroupData));
	        return ServiceApplicationGroupData;
		} catch (JAXBException e) {
			logger.fatal("ERROR ",e);
	    }
	    return null;
	}

}
