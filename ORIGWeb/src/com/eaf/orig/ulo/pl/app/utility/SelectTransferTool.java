package com.eaf.orig.ulo.pl.app.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.report.dao.ReportFileDAO;
import com.eaf.inf.batch.ulo.report.dao.ReportFileFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.google.gson.Gson;

public class SelectTransferTool implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(SelectTransferTool.class);
	public static final String REGION = "REGION";
	public static final String ZONE = "ZONE";
	public static final String TEAMZONE = "TEAMZONE";
	public static final String BRANCH_REGION_TRANSFER = "BRANCH_REGION_TRANSFER";
	public static final String BRANCH_ZONE_TRANSFER = "BRANCH_ZONE_TRANSFER";
	public static final String REPORT_VALUE_ALL = SystemConstant.getConstant("REPORT_VALUE_ALL");
	
	public static String DisplaySelectTransferData(String mainId, String type){
		return DisplaySelectTransferData(mainId, type, "");
	}
	
	public static String DisplaySelectTransferData(String mainId, String type,  String gridId){
		List<String> values = new ArrayList<>();
		try {
			ReportFileDAO reportFileDAO = ReportFileFactory.getReportFileDAO();
			if(REGION.equals(type)){
				values = reportFileDAO.getRegion();
			}else if(ZONE.equals(type) && !BRANCH_ZONE_TRANSFER.equals(mainId)){
				values = reportFileDAO.getZone(null);
			}else if(TEAMZONE.equals(type)){
				values = reportFileDAO.getTeamZone();
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
		
		StringBuilder HTML = new StringBuilder();
		
		HTML.append("<script>");
		
		HTML.append("	$(document).ready(function(){")
			.append("		$('#"+mainId+" #addAll').click(function() {")
			.append("			$('#"+mainId+" #select1 option').remove().appendTo('#"+mainId+" #select2');");
			if(BRANCH_REGION_TRANSFER.equals(mainId)){
				HTML.append(" 	generateBranchZone();");
			}
		HTML.append("		});")
			.append("		$('#"+mainId+" #add').click(function() {")
			.append("			$('#"+mainId+" #select1 option:selected').remove().appendTo('#"+mainId+" #select2');");
			if(BRANCH_REGION_TRANSFER.equals(mainId)){
				HTML.append(" 	generateBranchZone();");
			}
		HTML.append("		});")
			.append("		$('#"+mainId+" #remove').click(function() {")
			.append("			$('#"+mainId+" #select2 option:selected').remove().appendTo('#"+mainId+" #select1');");
			if(BRANCH_REGION_TRANSFER.equals(mainId)){
				HTML.append(" 	generateBranchZone();");
			}
		HTML.append("		});")
			.append("		$('#"+mainId+" .removeAll').click(function() {")
			.append("			$('#"+mainId+" #select2 option').remove().appendTo('#"+mainId+" #select1');");
			if(BRANCH_REGION_TRANSFER.equals(mainId)){
				HTML.append(" 	generateBranchZone();");
			}
		HTML.append("		});")
			.append("	});");
		
		HTML.append("</script>");
		String config_class = "col-sm-12";
		if(!Util.empty(gridId)){
			config_class = gridId;
		}
		HTML.append("<div id='"+mainId+"' class='"+config_class+"' style='border:1px solid #BEBEBE; padding : 10px;'>");
		HTML.append("	<div class='col-sm-5'>");
		HTML.append("		<select multiple id='select1' style='width:70px;height:90px;'>");
		for(String value : values){
			HTML.append("<option value='"+value+"'>"+value+"</option>");
		}
		HTML.append("  		</select>");    
		HTML.append(" 	</div>"); 
		HTML.append("	<div class='col-sm-2'>");
		HTML.append("		<input type='button' id='addAll' value='>>'/>");
		HTML.append("		<input type='button' id='add' value=' > '/>");
		HTML.append("		<input type='button' id='remove' value=' < '/>");
		HTML.append("		<input type='button' class='removeAll' value='<<'/>");
		HTML.append("	</div>");
		HTML.append("	<div class='col-sm-5'>");
		HTML.append("		<select name="+mainId+" multiple id='select2' style='width:70px;height:90px;'></select>");
		HTML.append("	</div>");
		HTML.append("</div>");
		
		return HTML.toString();
	}
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.FIND_ZONE_DATA);
		try{
			List<String> values = new ArrayList<>();
			HashMap<String, Object> hashMap = new HashMap<>();
			String[] branchRegions = request.getParameterValues("BRANCH_REGION_TRANSFER");
			String branchRegion = null;
			if(branchRegions!=null){
				if(REPORT_VALUE_ALL.equals(branchRegions[0])){
					branchRegion = branchRegions[0];
				}else{
					branchRegion = StringUtils.join(branchRegions, ",");
				}
				ReportFileDAO reportFileDAO = ReportFileFactory.getReportFileDAO();
				values = reportFileDAO.getZone(branchRegion);
			}
			hashMap.put("zones", values);
			return responseData.success(new Gson().toJson(hashMap));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
