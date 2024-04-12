package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;

public class ReAssignTaskCheckRole  implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(ReAssignTaskCheckRole.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CHECK_ROLE_REASSIGN_TASK);
		try{
			String data = null;
			String[] CHECK_BOX_VALUE = request.getParameterValues("CHECK_BOX_VALUE");
			String role="";
			if(!Util.empty(CHECK_BOX_VALUE)){
				for(int i =0; i<CHECK_BOX_VALUE.length;i++){
					String valueList = CHECK_BOX_VALUE[i];
					String[] ValueCh = valueList.split(",");	
					String  rowStr = ValueCh[2];
					String  OWNER_ROLE =   rowStr.substring("ROW_NAME=".length());
					if(i==0){
						role=OWNER_ROLE;
					}else{
						if(!OWNER_ROLE.equals(role)){
							data = "PLEASE_SELECT_THE_SAME_ROLE";
							break;
						}
					}
				}
			}
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
