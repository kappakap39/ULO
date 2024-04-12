<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilterInf"%>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilter"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.RequiredDocDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.RequiredDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCommentDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCheckListDataM"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<script type="text/javascript" src="orig/ulo/subform/js/DocumentCheckListManualAdd.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());

	String DOC_CHECKLIST_MANUAL_FLAG_INCOME = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_INCOME");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String DOC_CODE = SystemConstant.getConstant("DOCUMENT_TYPE_THAI_NATIONALITY");
	String CLASS_FOLLOWED_DOC_REASON_FILTER = SystemConstant.getConstant("CLASS_FOLLOWED_DOC_REASON_FILTER");	
	String FIELD_ID_FOLLOWED_DOC_REASON = SystemConstant.getConstant("FIELD_ID_FOLLOWED_DOC_REASON");
	String FOLLOWED_DOC_REASONFilter = ListBoxFilter.get(CLASS_FOLLOWED_DOC_REASON_FILTER);
	String roleId = ORIGForm.getRoleId();
	String PERSONAL_TYPE = PERSONAL_TYPE_APPLICANT;
	String displayMode = HtmlUtil.EDIT;
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	
	
	ArrayList<HashMap<String,Object>> listFollowedDocReason = null;	
	ListBoxFilterInf FilterInf  = null; 
	try {			        		    	
		FilterInf = (ListBoxFilterInf)Class.forName(FOLLOWED_DOC_REASONFilter).newInstance();			        		    	
	} catch (Exception e) {
		logger.fatal("ERROR ", e);
	}
	if(null != FilterInf){
		listFollowedDocReason = FilterInf.filter(DOC_CODE, FIELD_ID_FOLLOWED_DOC_REASON, "", request);
	}
	String mode = FormEffects.Effects.ENABLE;
	if(SystemConstant.lookup("ROLE_EXCEPTION_DISPLAY_MODE", roleId)){
		mode = FormEffects.Effects.HIDE;
	}
%>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<%=LabelUtil.getText(request, "INCOME_DOCUMENT")%>
			</div>
			<table class="table table-documentchecklist">
				<tbody>
<% 
					int seqInt = 0;
					ArrayList<DocumentCheckListDataM> docLists = applicationGroup.getDocumentCheckListManuals();
					if(null != docLists && docLists.size() > 0) {
						for(DocumentCheckListDataM docList : docLists) {
							seqInt++;
							String seq = String.valueOf(seqInt);
							String docCode = docList.getDocumentCode();
							String docName = CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), docCode);
							String docCheckListId = docList.getDocCheckListId();
%>
							<tr class="doctype">
								<td>
									<%=HtmlUtil.button("BTN_DEL_DOC_MAN_" + DOC_CHECKLIST_MANUAL_FLAG_INCOME, docCheckListId, "", mode, "btnsmall_delete", "", request) %>
								</td>
								<td width="40%">
									<div class="form-group">
										<%=HtmlUtil.dropdown("DOC_LIST_" + DOC_CHECKLIST_MANUAL_FLAG_INCOME, seq, "DOC_LIST_INCOME", docCode, "", "", "", "", "", "", request)%>
									</div>
								</td>
								<td>
									<div>
<%	
										for (HashMap<String,Object> listRadio : listFollowedDocReason) {
											String CODE = SQLQueryEngine.display(listRadio, "CODE");
											String VALUE = SQLQueryEngine.display(listRadio, "VALUE");
											DocumentCheckListReasonDataM reasonM = docList.getDocumentCheckListReason(CODE);
											if(Util.empty(reasonM)) {
												reasonM = new DocumentCheckListReasonDataM();
											}
%>
											<div class="col-sm-12">
												<div class="form-group">
													<%=HtmlUtil.checkBoxInline("FOLLOWED_DOC_REASON_SELECT_" + DOC_CHECKLIST_MANUAL_FLAG_INCOME, CODE + "_" + seq, reasonM.getDocReason(), CODE, displayMode, "", VALUE, "text-left", request) %>			
												</div>
											</div>
											<div class="clearfix"></div>
<%
										}
%>
									</div>
									<div class="row form-horizontal">
										<div class="col-md-12">
											<div class="form-group">
												<%=HtmlUtil.getFieldLabel(request, "REMARK", "col-sm-4 col-md-5 control-label")%>
												<%=HtmlUtil.textarea("FOLLOWED_REMARK_" + DOC_CHECKLIST_MANUAL_FLAG_INCOME,seq, docList.getRemark(), "5", "100", "200", displayMode, HtmlUtil.elementTagId("FOLLOWED_REMARK"), "col-sm-10 col-md-9", request) %>
											</div>
										</div>
									</div>
								</td>
								<td></td>
							</tr>
<%
						}
%>						
						<tr class="doctype">
							<td colspan="3"></td>
							<td>
								<%=HtmlUtil.button("BTN_ADD_DOC_MAN_" + DOC_CHECKLIST_MANUAL_FLAG_INCOME, "", "", mode, "btnsmall_add", "", request) %>
							</td>
						</tr>
<%					
					} else {
%>
						<tr class="doctype">
							<td>
								<div style="float: right;">
									<%=HtmlUtil.button("BTN_ADD_DOC_MAN_INIT_" + DOC_CHECKLIST_MANUAL_FLAG_INCOME, "", "", mode, "btnsmall_add", "", request) %>
								</div>
							</td>
						</tr>
<%
					}
%>
				</tbody>
			</table>
		</div>
	</div>
</div>