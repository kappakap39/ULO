package com.eaf.orig.ulo.control.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.view.util.inf_export.model.InfExportDataM;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/ViewReportServlet")
public class ViewReportServlet extends HttpServlet {
	private static transient Logger logger = Logger.getLogger(ViewReportServlet.class);
	private static final long serialVersionUID = 1L;
	HashMap<String,Integer> reportDatasources = new HashMap<String, Integer>();
	public void init(){
		reportDatasources.put("RE_R016.jasper",OrigServiceLocator.ORIG_IAS);
	}
	public Integer getDataSource(String fileName){
		return reportDatasources.get(fileName);
	}
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		init();
		logger.debug("ViewReport..");
		String FILE_NAME = request.getParameter("FILE_NAME"); 
		String DATE_NOW = request.getParameter("DATE_NOW");
		String REPORT_JASPER_PATH = SystemConfig.getProperty("REPORT_JASPER_PATH");
		String textOUTPUT_FILENAME = request.getParameter("OUTPUT_FILENAME");
		logger.debug("REPORT_JASPER_PATH >> "+REPORT_JASPER_PATH);
		logger.debug("FILE_NAME >> "+FILE_NAME);
		JsonElement root = new JsonParser().parse(request.getParameter("JSON_DATA"));
		JsonArray jsonArray = root.getAsJsonArray();
		Connection conn = null;
		JasperPrint jasperPrint = null;
		try{
			Integer datasource  = getDataSource(FILE_NAME);
			logger.debug("datasource : "+datasource);
			if(null!=datasource&&datasource>0){
				conn = OrigServiceLocator.getInstance().getConnection(datasource);
			}else{
				conn = OrigServiceLocator.getInstance().getConnection(OrigServiceLocator.ORIG_DB);
			}
			Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("SUBREPORT_DIR",REPORT_JASPER_PATH);
				Date appDate = InfBatchProperty.getDate();
				logger.debug("appDate >> "+appDate);
				parameter.put("P_ADD_DATE", appDate);
				for(int i=0;i<jsonArray.size();i++){
					JsonObject object = jsonArray.get(i).getAsJsonObject();
					String id = (Util.empty(object.get("FIELD_ID")) ? "":object.get("FIELD_ID").getAsString());
					String value = (Util.empty(object.get("FIELD_VALUE")) ? "":object.get("FIELD_VALUE").getAsString());
					logger.debug("id"+i+" >> "+id+" : "+value);
					parameter.put(id,value);
				}
			jasperPrint = JasperFillManager.fillReport(REPORT_JASPER_PATH+FILE_NAME,parameter,conn);			
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}finally{
			try{
				if(null != conn){
					conn.close();
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}	
		try{
			InfExportProxy infExport = new InfExportProxy();
			int index = 1;
			String MODULE_ID = "";
			if(!Util.empty(FILE_NAME)){
				MODULE_ID = FILE_NAME.substring(0, FILE_NAME.indexOf(".jasper"));
				ArrayList<InfExportDataM> infExportList = infExport.getMODULE_ID(MODULE_ID,DATE_NOW);
				if(!Util.empty(infExportList)){
					index = infExportList.size()+1;
				}
			}
			String txtIndex = Integer.toString(index);
			while(txtIndex.length() < 2){
				txtIndex = "0"+txtIndex;
			}
			String OUTPUT_FILENAME = textOUTPUT_FILENAME+"_"+txtIndex+".xls";
			logger.debug("OUTPUT_FILENAME >> "+OUTPUT_FILENAME);
			OutputStream outputStream =  response.getOutputStream();
			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "inline; filename="+OUTPUT_FILENAME);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
			exporter.exportReport();
			
			if(!Util.empty(MODULE_ID)){
				InfExportDataM InfExportM = new InfExportDataM();
				UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
				InfExportM.setModuleId(MODULE_ID);
				InfExportM.setUpdateBy(userM.getUserName());
				infExport.createINF_EXPORT(InfExportM);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
}
