package com.eaf.orig.rest.controller;

import javax.servlet.http.HttpServletRequest;

import com.orig.bpm.workflow.model.BPMApplication;
import com.orig.bpm.workflow.model.BPMRequest;

public class RestControllerUtil {
	public static BPMRequest constructTaskRequest(HttpServletRequest request){
		BPMRequest result = null;
		if(request == null){
			return null;
		}
		String title = request.getParameter("title");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String card_type = request.getParameter("card_type");
		String card_no = request.getParameter("card_no");
//		String birth_date = request.getParameter("birth_date");
		String project_code = request.getParameter("project_code");
		String channel = request.getParameter("channel");
		String template = request.getParameter("template");
		
		result = new BPMRequest();
		BPMApplication app = result.getApplicationDetail();
		if(app == null){
			result.setApplicationDetail(new BPMApplication());
		}
//		app.setTitle(title);
//		app.setFirstName(fname);
//		app.setLastName(lname);
//		app.setCardType(card_type);
//		app.setIdNo(card_no);
//		app.setBirthDate(new Date());
//		app.setProjectCode(project_code);
//		app.setChannel(channel);
//		app.setTemplate(template);
//		app.setFollowDoc(false);

		return result;
	}
}
