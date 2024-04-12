<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilterInf" %>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilter" %>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine" %>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCheckListDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/element/js/FollowedDocReasonAddElement.js"></script>
<%
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	Logger logger = Logger.getLogger(this.getClass());
	String DOC_CODE = request.getParameter("DOC_CODE");
	String PERSONAL_TYPE = request.getParameter("PERSONAL_TYPE");
	String PERSONAL_ID = request.getParameter("PERSONAL_ID");
	String roleId = FormControl.getFormRoleId(request);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(PERSONAL_ID);
	String applicantTypeIM = PersonalInfoUtil.getIMPersonalType(personalInfo);
	DocumentCheckListDataM docM = applicationGroup.getDocumentCheckList(applicantTypeIM, DOC_CODE);
	if(Util.empty(docM)) {
		docM = new DocumentCheckListDataM();
	}
	
	String CLASS_FOLLOWED_DOC_REASON_FILTER = SystemConstant.getConstant("CLASS_FOLLOWED_DOC_REASON_FILTER");	
	String FIELD_ID_FOLLOWED_DOC_REASON = SystemConstant.getConstant("FIELD_ID_FOLLOWED_DOC_REASON");
	String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");

	String FOLLOWED_DOC_REASONFilter = ListBoxFilter.get(CLASS_FOLLOWED_DOC_REASON_FILTER);
	
	String displayMode = HtmlUtil.EDIT;
	
	ArrayList<HashMap<String,Object>> ListFollowedDocReason = null;	
	ListBoxFilterInf FilterInf  = null; 
	 try{			        		    	
		FilterInf = (ListBoxFilterInf)Class.forName(FOLLOWED_DOC_REASONFilter).newInstance();			        		    	
	} catch (Exception e) {
		logger.fatal("ERROR ",e);
	}
	if(null != FilterInf){
		ListFollowedDocReason = FilterInf.filter(DOC_CODE,FIELD_ID_FOLLOWED_DOC_REASON, "", request);
	}
	String SHOW_FLAG = MConstant.FLAG.NO;
	ArrayList<DocumentCheckListReasonDataM> reasonList = docM.getDocumentCheckListReasons();
	if(!Util.empty(reasonList)) {
		SHOW_FLAG = MConstant.FLAG.YES;
	}
	String suffix = DOC_CODE+"_"+PERSONAL_TYPE+"_"+PERSONAL_ID;
	String mode = FormEffects.Effects.ENABLE;
	if(SystemConstant.lookup("ROLE_EXCEPTION_DISPLAY_MODE", roleId)){
		mode = FormEffects.Effects.HIDE;
	}
%>
<%=HtmlUtil.hidden("SHOW_DETAIL_"+suffix, SHOW_FLAG) %>
<div name="ADD_FDR_<%=suffix%>" style="display: none;">
	<table>
		<tr>
			<td><%=HtmlUtil.button("BTN_DEL_FDR",suffix,"","","btnsmall_delete","style='display: none;'",request) %></td>
			<td>
				<%	
					for (HashMap<String,Object> ListRadio:ListFollowedDocReason) {
						String CODE = SQLQueryEngine.display(ListRadio,"CODE");
						String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
						DocumentCheckListReasonDataM reasonM = docM.getDocumentCheckListReason(CODE);
						if(Util.empty(reasonM)) {
							reasonM = new DocumentCheckListReasonDataM();
						}
					%>
					<div class="col-sm-12">
						<div class="form-group">
					<%=HtmlUtil.checkBoxInline("FOLLOWED_DOC_REASON_SELECT", suffix, reasonM.getDocReason(), CODE, displayMode, "", VALUE, "text-left", request) %>			
						</div>
					</div>
					<div class="clearfix"></div>
					<%
					}
					%>
					

		</td>
	</tr>
	<tr> 
		<td></td>
		<td>
			<div class="row form-horizontal">
				<div class="col-md-12">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "REMARK", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textarea("FOLLOWED_REMARK",suffix, docM.getRemark(), "5", "100", "200", displayMode, HtmlUtil.elementTagId("FOLLOWED_REMARK"), "col-sm-10 col-md-9", request) %>
					</div>
				</div>
			</div>
		</td> 
	</tr>  
 </table>
</div>

 
<%=HtmlUtil.button("BTN_ADD_FDR",suffix,"",mode,"btnsmall_add","",request) %>

