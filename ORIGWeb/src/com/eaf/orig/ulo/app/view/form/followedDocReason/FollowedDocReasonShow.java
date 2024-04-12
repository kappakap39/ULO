package com.eaf.orig.ulo.app.view.form.followedDocReason;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class FollowedDocReasonShow extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(FollowedDocReasonShow.class);
	String ROLE_FU = SystemConstant.getConstant("ROLE_FU");
	String VERIFY_CUSTOMER_FORM = SystemConstant.getConstant("VERIFY_CUSTOMER_FORM"); 
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String  docCheckListDesc = (String)objectElement;
		String[] dataSplit = docCheckListDesc.split("fu_remark");
		String docDesc = "";
		String remark="";
		if(dataSplit.length>0){
			docDesc=dataSplit[0];
		}
		if(dataSplit.length>1){
			remark=dataSplit[1];
		}
		
		StringBuilder HTML = new StringBuilder();
		HTML.append("<div class='col-sm-12'>");
		HTML.append("<div class='form-group text-left'>&nbsp;");
		HTML.append(Util.empty(docDesc) ? "" : docDesc);
		HTML.append("</div>");
		HTML.append("</div>");
		HTML.append("<div class='clearfix'></div>");
		if(!Util.empty(remark)){
		HTML.append("<div class='col-sm-12'>");
		HTML.append("<div class='form-group text-left'>");
		HTML.append(HtmlUtil.getFieldLabel(request, "REMARK", "col-sm-2 col-md-3 control-label"));
		HTML.append("&nbsp;"+remark);
		HTML.append("</div>");
		HTML.append("</div>");
		HTML.append("<div class='clearfix'></div>");
		}
		/*HTML.append("<p><label class=\"input-group\" style=\"text-align: left;\">");
		HTML.append(Util.empty(docDesc) ? "" : docDesc);
		HTML.append("</label></p>");
		if(!Util.empty(remark)){
			HTML.append("<p><label class=\"input-group\" style=\"text-align: left;\">");
			HTML.append("&nbsp;"+LabelUtil.getText(request, "REMARK"));
			HTML.append("&nbsp;:&nbsp;"+remark);
			HTML.append("</label></p>");
		}
*/
		return HTML.toString();
	}

	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		logger.debug("ActionType >> "+actionType);
		String renderElementFlag = MConstant.FLAG.NO;
		String roleId = FormControl.getFormRoleId(request);
		String formId = FormControl.getFormId(request);
		logger.debug("roleId : "+roleId);
		logger.debug("formId : "+formId);
		if(ROLE_FU.equals(roleId) || VERIFY_CUSTOMER_FORM.equals(formId) || SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			renderElementFlag = MConstant.FLAG.YES;
		}
		logger.debug("renderElementFlag : "+renderElementFlag);
		return renderElementFlag;
	}
	
}
