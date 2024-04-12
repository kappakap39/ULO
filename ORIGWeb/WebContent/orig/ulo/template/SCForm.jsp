<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.j2ee.pattern.view.form.FormHandler"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager"/>
<!-- SCForm -->
<%
	Logger logger = Logger.getLogger(this.getClass());
	SQLQueryEngine QueryEngine = new SQLQueryEngine();
	Vector<HashMap> vForm = QueryEngine.LoadModuleList("SELECT * FROM SC_FORM ORDER BY DECODE(SC_FORM.FORM_HANDLER_NAME,'ORIGForm',1,2) ASC");
	Vector<HashMap> vRole = QueryEngine.LoadModuleList("SELECT DISTINCT ROLE_ID FROM SC_SUBFORM_ROLE");
	String formName = formHandlerManager.getCurrentFormHandler();
	logger.debug("formName >> "+formName);	
	FormHandler currentForm = null;	
	String formId = "";
	String roleId = "";
	if(!Util.empty(formName)){
		currentForm = (FormHandler)(request.getSession(true).getAttribute(formName));	
		if(null != currentForm){	
			formId = currentForm.getFormId();
			roleId = currentForm.getRoleId();
		}
	}
	logger.debug("formId >> "+formId);
	logger.debug("roleId >> "+roleId);
%>
<div class="row" style="position: absolute; z-index: 1040;top: -26px;">
	<div class="col-xs-12">
		<button type="button" onclick="$('.SCForm').slideToggle();"> Show/Hide </button>
		<div class="panel panel-default SCForm" style="<% out.print((formId != ""?"display: none;":"")); %>">
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
				        <label for="formId" class="col-sm-2 control-label">Form</label>
				        <div class="col-sm-10">
				        	<select id="formId" name="formId" class="form-control">
								<%if(!Util.empty(vForm)){
									for(HashMap Form:vForm){
										String CODE = SQLQueryEngine.display(Form,"FORM_ID");
										String VALUE = SQLQueryEngine.display(Form,"FORM_NAME");
										String selected = "";
										if(CODE.equals(formId)){
											selected = "selected";
										}
								%>
							  	<option value="<%=CODE%>" <%=selected%>>[<%=CODE%>]<%=" - " %><%=VALUE%></option>
							  	<%}}%>
							</select>
				        </div>
				    </div>
				    <div class="form-group">
				        <label for="roleId" class="col-sm-2 control-label">Role</label>
				        <div class="col-sm-10">
				        	<select id="roleId" name="roleId" class="form-control">
								<%if(!Util.empty(vRole)){
									for(HashMap Role:vRole){
										String CODE = SQLQueryEngine.display(Role,"ROLE_ID");
										String VALUE = LabelUtil.getText(request,"ROLE_"+SQLQueryEngine.display(Role,"ROLE_ID"));
										String selected = "";
										if(CODE.equals(roleId)){
											selected = "selected";
										}
								%>
							  	<option value="<%=CODE%>" <%=selected%>>[<%=CODE%>]<%=" - " %><%=VALUE%></option>
							  	<%}}%>
							</select>
				        </div>
				    </div>
				    <div class="form-group">
				        <label for="requestData" class="col-sm-2 control-label" style="font-size: 90%;">Request Data</label>
				        <div class="col-sm-10">
				        	<%=HtmlUtil.textarea("requestData","","5","80","1000",HtmlUtil.EDIT,"",request)%>
				        </div>
				    </div>
				    <div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
				        	<button type="button" class="btn btn-default" onclick="loadForm();">Load form</button>
				        	<button type="button" class="btn btn-primary" onclick="saveForm();">Save form</button>
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>
</div>

<%if(null != currentForm){%>
<div id="ScForm" class="ScForm"><jsp:include page="/orig/ulo/template/MasterFrameTemplate.jsp" flush="true"/></div>
<%}%>
<script type="text/javascript">
function loadForm(){
	$('#action').val('LoadForm');
	$('#handlerForm').val('N');
	$('#appFormName').submit();
}
function saveForm(){
	$('#action').val('SaveForm');
	$('#handlerForm').val('Y');
	$('#appFormName').submit();
}

$('#formId').selectize();
$('#roleId').selectize();
</script>
<!-- End SCForm -->