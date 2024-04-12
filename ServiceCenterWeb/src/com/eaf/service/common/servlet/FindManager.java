package com.eaf.service.common.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.inf.batch.ulo.notification.condition.NotificationCondition;
import com.eaf.inf.batch.ulo.notification.model.JobCodeDataM;
import com.eaf.inf.batch.ulo.notification.model.VCEmpInfoDataM;
import com.eaf.service.common.utils.ResponseUtils;
import com.google.gson.Gson;

@WebServlet("/FindManager")
public class FindManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(FindManager.class);
    public FindManager() {
        super();
    }
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("request")){
			String sale_id = request.getParameter("sale_id");
			 
			
			//CISContact Array
			String[] JobCode = request.getParameterValues("JobCode");
			String[] OPTIONAL1 = request.getParameterValues("OPTIONAL1");
			String[] OPTIONAL2 = request.getParameterValues("OPTIONAL2");
			String[] OPTIONAL3 = request.getParameterValues("OPTIONAL3");
			String[] OPTIONAL4 = request.getParameterValues("OPTIONAL4");
			String[] OPTIONAL5 = request.getParameterValues("OPTIONAL5");
			String[] PATTERN = request.getParameterValues("PATTERN");
			
			ArrayList<JobCodeDataM> jobList  = new ArrayList<JobCodeDataM>();
			for(int i=0;i<JobCode.length;i++){
				JobCodeDataM jobCode = new JobCodeDataM();
				jobCode.setJobCode(JobCode[i]);
				jobCode.setOptional1(OPTIONAL1[i]);
				jobCode.setOptional2(OPTIONAL2[i]);
				jobCode.setOptional3(OPTIONAL3[i]);
				jobCode.setOptional4(OPTIONAL4[i]);
				jobCode.setOptional5(OPTIONAL5[i]);
				jobCode.setPattern(PATTERN[i]);
				jobList.add(jobCode);
			}
			
			logger.debug("jobList>>"+ new Gson().toJson(jobList));
			logger.debug("sale_id>>"+ sale_id);
			
			NotificationCondition noticond = new NotificationCondition();
			ArrayList<VCEmpInfoDataM> managers = null;
			try {
				managers = noticond.getSendToVCEmpManagers(sale_id, jobList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			HashMap<String,Object> object = new HashMap<String,Object>();
			Gson gson = new Gson();
			object.put("jsonRq", "sale_id:"+sale_id+gson.toJson(jobList));
			object.put("jsonRs", gson.toJson(managers));
			ResponseUtils.sendJsonResponse(response, object);
		}
	}
  }