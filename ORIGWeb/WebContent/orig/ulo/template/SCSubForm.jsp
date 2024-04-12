<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="<%=ORIGFormHandler.ORIGForm%>"/>
<jsp:useBean id="tabHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.TabHandleManager"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	SQLQueryEngine QueryEngine = new SQLQueryEngine();
	Vector<HashMap> vSubForm = QueryEngine.LoadModuleList("SELECT * FROM SC_SUBFORM ORDER BY SUBFORM_ID ASC");
	Vector<HashMap> vRole = QueryEngine.LoadModuleList("SELECT DISTINCT ROLE_ID FROM SC_SUBFORM_ROLE");
%>

<div class="col-xs-6">
		<div class="panel panel-default" style="">
			<div class="panel-body">
				<div class="form-horizontal" style="margin-top: 15px">
					<div class="form-group">
				        <label for="formId" class="col-sm-3 control-label">Sub Form</label>
				        <div class="col-sm-9">
				        	<select id="SUBFORM_ID" name="SUBFORM_ID" class="">
						<%if(!Util.empty(vSubForm)){
							for(HashMap SubForm:vSubForm){
								String CODE = SQLQueryEngine.display(SubForm,"SUBFORM_ID");
								String VALUE = LabelUtil.getText(request,SQLQueryEngine.display(SubForm,"SUBFORM_DESC"));
								String selected = "";
								if(CODE.equals(ORIGForm.getSubFormId())){
									selected = "selected";
								}
						%>
					  		<option value="<%=CODE%>" <%=selected%>>[<%=CODE%>]<%=" - " %><%=VALUE%></option>
					  	<%}}%>
					</select>
				        </div>
				    </div>
				    <div class="form-group">
				        <label for="roleId" class="col-sm-3 control-label">Role</label>
				        <div class="col-sm-9">
				        	<select id="ROLE_ID" name="ROLE_ID" class="">
						<%if(!Util.empty(vRole)){
							for(HashMap Role:vRole){
								String CODE = SQLQueryEngine.display(Role,"ROLE_ID");
								String VALUE = LabelUtil.getText(request,"ROLE_"+SQLQueryEngine.display(Role,"ROLE_ID"));
								String selected = "";
								if(CODE.equals(ORIGForm.getRoleId())){
									selected = "selected";
								}
						%>
					  		<option value="<%=CODE%>" <%=selected%>>[<%=CODE%>]<%=" - " %><%=VALUE%></option>
					  	<%}}%>
					</select>
				        </div>
				    </div>
				    <div class="form-group">
					    <div class="col-sm-offset-3 col-sm-9">
				        	<button type="button" class="btn btn-default" onclick="loadSubform();">Load Sub Form</button>
				        	<button type="button" class="btn btn-primary" onclick="saveSubform();">Save Sub Form</button>
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>

<%		
ArrayList<ORIGSubForm> subForms = ORIGForm.getSubForm();
if(!Util.empty(subForms)){
	ORIGSubForm subForm = subForms.get(0);
	logger.debug("subForm.getFileName() >> "+subForm.getFileName());
 %>
<section class="work_tools_area">	
	<section class="work_area" id="SECTION_<%=subForm.getSubFormID()%>">
		<header class="titlecontent"><%=LabelUtil.getText(request,subForm.getSubformDesc())%></header>
		<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
	</section>
</section>
<%}%>
<script type="text/javascript">
function loadSubform(){
	$('#action').val('LoadSubform');
	$('#handlerForm').val('N');
	$('#appFormName').submit();
}
function saveSubform(){
	$('#action').val('SaveSubform');
	$('#handlerForm').val('Y');
	$('#appFormName').submit();
}
$('#SUBFORM_ID').selectize();
$('#ROLE_ID').selectize();
</script>
