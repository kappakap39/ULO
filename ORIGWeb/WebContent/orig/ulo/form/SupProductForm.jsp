<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	String subformId = "SUP_PRODUCT_FORM";
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = EntityForm.getRoleId();
	PersonalApplicationInfoDataM personalAppInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
	FormUtil formUtil = new FormUtil(subformId,request);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
%>
<%
ArrayList<ORIGSubForm> headerSubforms = FormControl.getSubForm("SUP_PRODUCT_FORM_INFO",roleId,applicationGroup,request);
if(!Util.empty(headerSubforms)){
	for(ORIGSubForm subForm:headerSubforms){
		logger.debug("subForm.getFileName() >> "+subForm.getFileName());
%>
		<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
	<%}%>
<%}
ArrayList<String> products = personalAppInfo.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
if(!Util.empty(products)){	
	for(String product:products){
%>
<%-- 	<section id="SECTION_PRODUCT_<%=product%>" class="warpSubFormTemplate SECTION_PRODUCT_<%=product%>"> --%>
<%
		String formIdListProduct = FormControl.getFormId("LIST_SUP_PRODUCT_FORM",product);
		logger.debug("formIdListProduct >>"+formIdListProduct);
		ArrayList<ORIGSubForm> listProductSubForms = FormControl.getSubForm(formIdListProduct,roleId,applicationGroup,request);
		if(!Util.empty(listProductSubForms)){
			for(ORIGSubForm listSubForm : listProductSubForms){
				logger.debug("listSubForm.getFileName() >> "+listSubForm.getFileName());
				String listProductGroupSubformId = listSubForm.getGroupSubFormId();
				String listProductSubformId = listSubForm.getSubFormID();%>			
<!-- 				<section> -->
					<div id="<%=listProductGroupSubformId%>" class="<%=listProductGroupSubformId%>">				
						<script type="text/javascript">
							$(document).ready(function(){
								try{<%=listProductGroupSubformId%>InitSubFormJS('<%=listProductSubformId%>','<%=listProductGroupSubformId%>');}catch(e){}
							});
						</script>
						<%=HtmlUtil.hidden("groupSubformId",listProductGroupSubformId)%>
						<%=HtmlUtil.hidden("subformId",listProductSubformId)%>		
						<section class="work_area" id="SECTION_<%=listProductSubformId%>">
							<div class="subtitlecontent productform"><%=LabelUtil.getText(request,listSubForm.getSubformDesc())%></div>
							<div class="row">
								<div class="col-xs-12">
									<jsp:include page="<%=listSubForm.getFileName()%>" flush="true" />
								</div>
							</div>
						</section>
					</div>
<!-- 				</section> -->
<!-- 				</section>	 -->
			<%}%>
		<%}%>
	<%}%>
<%}%>


<%
if(!Util.empty(products)){	
	for(String product:products){
%>
	<section id="SECTION_PRODUCT_<%=product%>" class="warpSubFormTemplate SECTION_SUP_PRODUCT_<%=product%>">
	<header class="titlecontent"><%=LabelUtil.getText(request,product)%></header>	
<%		
		String formId = FormControl.getFormId("SUP_PRODUCT_FORM",product);
		ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId,applicationGroup,request);
		if(!Util.empty(productSubForms)){
			for(ORIGSubForm subForm:productSubForms){
				logger.debug("subForm.getFileName() >> "+subForm.getFileName());
				String productGroupSubformId = subForm.getGroupSubFormId();
				String productSubformId = subForm.getSubFormID();
%>			
				<section>
					<div id="<%=productGroupSubformId%>" class="<%=productGroupSubformId%>">				
						<script type="text/javascript">
							$(document).ready(function(){
								try{<%=productGroupSubformId%>InitSubFormJS('<%=productSubformId%>','<%=productGroupSubformId%>');}catch(e){}
							});
						</script>
						<%=HtmlUtil.hidden("groupSubformId",productGroupSubformId)%>
						<%=HtmlUtil.hidden("subformId",productSubformId)%>		
						<section class="work_area" id="SECTION_<%=productSubformId%>">
							<div class="subtitlecontent"><%=LabelUtil.getText(request,subForm.getSubformDesc())%></div>
							<div class="row">
								<div class="col-xs-12">
									<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
								</div>
							</div>
						</section>
					</div>
				</section>
			<%}%>
		<%}%>
	</section>		
	<%}%>
<%}else{%>
<div class="panel panel-default">
	<table class="table table-striped">
		<tr>
	 		<td align="center" colspan="999"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	</table>
</div>
<%}%>
