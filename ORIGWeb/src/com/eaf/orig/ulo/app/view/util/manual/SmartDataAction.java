package com.eaf.orig.ulo.app.view.util.manual;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.shared.dao.OrigObjectDAO;

public class SmartDataAction implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(SmartDataAction.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SMART_DATA_ACTION);
		String data = null;
		try{
			String smartData = request.getParameter("smartData");
			logger.debug("smartData : "+smartData);
			if(!Util.empty(smartData)){
				JSONObject smartDataObject = new JSONObject(smartData);
				String smartDataId = smartDataObject.getString("smartDataId");
				logger.debug("smartDataId : "+smartDataId);
				if(!Util.empty(smartDataId)){
					JSONArray smartDataElements = smartDataObject.getJSONArray("smartDataElement");
					SmartDataDAOImpl smartDataDAOImpl = new SmartDataDAOImpl();
					if(null != smartDataElements){
						for (int i=0; i<smartDataElements.length();i++) {
							JSONObject smartDataElement = smartDataElements.getJSONObject(i);
							String subFormId = smartDataElement.getString("subFormId");
							String elementLabel = smartDataElement.getString("elementLabel");
							String[] elementInputs = smartDataElement.getString("elementInput").split(",");
							logger.debug("subFormId : "+subFormId);
							logger.debug("elementLabel : "+elementLabel);
							for(int j=0; j<elementInputs.length; j++){
								String elementInput = elementInputs[j];
								logger.debug("elementInput : "+elementInput);
								SQLQueryEngine queryEngine = new SQLQueryEngine();
								HashMap smMain = queryEngine
										.LoadModule("select * from sm_main where templateid = '"+smartDataId+"' and  fieldid = '"+elementInput+"'");
								String pkid = SQLQueryEngine.display(smMain, "PKID");
								logger.debug("pkid : "+pkid);
								if(Util.empty(pkid)){
									smartDataDAOImpl.insert(smartDataId, subFormId, elementLabel, elementInput);
								}
							}
						}
					}else{
						data = "ERROR : Not Found Smart Data Field.";
					}
				}else{
					data = "ERROR : Please Select Smart Data Template.";
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		if(Util.empty(data)){
			data = "Update Success.";
		}
		return responseData.success(data);
	}
	
	public class SmartDataDAOImpl extends OrigObjectDAO{
		public void insert(String smartDataId,String subFormId,String elementLabel,String elementInput) throws Exception{
			Connection conn = null;
			PreparedStatement ps = null;
			try{
				conn = getConnection();
				String sql = "insert into sm_main(templateid,fieldid,subformid,field_description,createdby,createddate,updatedby,updateddate,x,y,w,h,IMGID)" +
						"values(?,?,?,?,?,?,?,?,0,0,0,0,?)";
				logger.debug("sql : "+sql);
				ps = conn.prepareStatement(sql);
				int index = 1;
				ps.setString(index++,smartDataId);
				ps.setString(index++,elementInput);
				ps.setString(index++,subFormId);
				ps.setString(index++,elementLabel);
				ps.setString(index++,"SYSTEM");
				ps.setTimestamp(index++,ApplicationDate.getTimestamp());
				ps.setString(index++,"SYSTEM");
				ps.setTimestamp(index++,ApplicationDate.getTimestamp());
				ps.setString(index++, getMaxIMGID(smartDataId,subFormId));
				ps.executeUpdate();
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new Exception(e.getLocalizedMessage());
			}finally{
				try{
					closeConnection(conn, ps);
				}catch(Exception e){
					logger.fatal("ERROR",e);
					throw new Exception(e.getLocalizedMessage());
				}
			}
		}

		public String getMaxIMGID(String templateid , String subFormId) throws Exception {
			String GET_IMGID = null;
			try{
				SQLQueryEngine queryEngine = new SQLQueryEngine();
				HashMap data = queryEngine.LoadModule(" select max(IMGID) as GET_IMGID FROM SM_MAIN where templateid = '"+templateid+"' and SUBFORMID = '"+subFormId+"' ");
				GET_IMGID = SQLQueryEngine.display(data, "GET_IMGID");			
			}catch(Exception e){
				logger.fatal("ERROR",e);
				return "ERROR : "+e.getLocalizedMessage();
			}
			return GET_IMGID;
		}
	}
}
