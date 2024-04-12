package com.eaf.core.ulo.common.diagnostics;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.core.diagnostics.model.DiagnosticsHander;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.ulo.cache.dao.engine.SQLQueryController;
import com.eaf.ulo.cache.dao.model.SQLQueryDataM;

public class TaskDiagnostics implements TaskInf{
	private static transient Logger logger = Logger.getLogger(TaskDiagnostics.class);
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		SQLQueryController queryController = new SQLQueryController();
		SQLQueryDataM sqlQuery = new SQLQueryDataM();
		sqlQuery.setSQL("SELECT * FROM MS_SERVER_INFO");
		ArrayList<TaskObjectDataM> tasks = new ArrayList<TaskObjectDataM>();
		ArrayList<HashMap<String,Object>> results  = queryController.loadResults(sqlQuery);
		if(null!=results){
			for(HashMap<String,Object> result:results){
				TaskObjectDataM taskObject = new TaskObjectDataM();
				ServerInfo serverInfo = new ServerInfo();
				String url = SQLQueryEngine.display(result,"SERVER_URL");
				if(url.contains("ORIGWeb")||url.contains("IASWeb")||url.contains("ServiceCenterWeb")){
					serverInfo.setUrl(url);
					serverInfo.setSeq(Integer.parseInt(SQLQueryEngine.display(result,"SERVER_ID")));
					taskObject.setObject(serverInfo);
					tasks.add(taskObject);
				}
			}
		}
		return tasks;
	}
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			ServerInfo serverInfo = (ServerInfo)taskObject.getObject();
			String URL = serverInfo.getUrl()+"/rest/controller/service/diagnostics/process/all";
			RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			        if(connection instanceof HttpsURLConnection ){
			            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
							@Override
							public boolean verify(String arg0, SSLSession arg1) {
								return true;
							}
						});
			        }
					super.prepareConnection(connection, httpMethod);
				}
			});
			ResponseEntity<DiagnosticsHander> responseEntity = restTemplate.postForEntity(URL,null,DiagnosticsHander.class);
			DiagnosticsHander responseObject = responseEntity.getBody();
			taskExecute.setResponseObject(responseObject);
			taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecute;
	}
}
