package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;

public class DocumentFormKPB extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(NoDocument.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		StringBuilder htmlTag = new StringBuilder();
		htmlTag.append(" <div class='panel panel-default'>");
		htmlTag.append(" 	<div class='panel-body'>");
		htmlTag.append("		<div class='row form-horizontal'>");
		htmlTag.append("			<div class='col-sm-12'>");
		htmlTag.append("				<div class='form-group'>");
		htmlTag.append("					<div class='col-sm-7 col-md8 bold'>");
		htmlTag.append(			"<label for='08_DOCUMENT_TYPE'>" + LabelUtil.getText(request,"DOCUMENT_FOR_FORM_KPB") + "</label>");	
		htmlTag.append("					</div>");
		htmlTag.append("				</div>");
		htmlTag.append("			</div>");
		htmlTag.append("		</div>");
		htmlTag.append("		<div class='row form-horizontal element-body'></div>");
		htmlTag.append("	</div>");
		htmlTag.append("</div>");
		return htmlTag.toString();
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc = ((PrivilegeProjectCodeDocDataM)objectElement);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeExceptionDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeKassetDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeProductTradings(null);
		return null;
	}
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {		
		return null;
	}
 
}