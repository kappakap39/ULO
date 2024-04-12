<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	String subformId = "PRODUCT_FORM";
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String roleId = ORIGForm.getRoleId();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	FormUtil formUtil = new FormUtil(subformId,request);
	
	Boolean isKPL = KPLUtil.isKPL(applicationGroup);

ArrayList<ORIGSubForm> headerSubforms = FormControl.getSubForm("PRODUCT_FORM_INFO",roleId,applicationGroup,request);

if(!Util.empty(headerSubforms)){

	for(ORIGSubForm subForm:headerSubforms){
		logger.debug("subForm.getFileName() >> "+subForm.getFileName());
%>
		<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
	<%}%>
<%}%>
<%
ArrayList<String> products = new ArrayList<String>();
if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
	products=applicationGroup.getDisplayEnquiryProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
}else{
	products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
	
	logger.debug("applicationGroup ID = " + applicationGroup.getApplicationGroupId());
	logger.debug("app size = " + applicationGroup.getApplications().size());
	logger.debug("products size = " + products.size());
}
if(!Util.empty(products)){	
	for(String product:products){
%>
<%-- 	<section id="SECTION_PRODUCT_<%=product%>" class="warpSubFormTemplate SECTION_PRODUCT_<%=product%>"> --%>
<%
		String formIdListProduct = FormControl.getFormId("LIST_PRODUCT_FORM",product);
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
<%if(!Util.empty(products)){
	for(String product:products){%>
	<header class="titlecontent"><%=LabelUtil.getText(request,product)%></header>	

<%		
		String formId = FormControl.getFormId("PRODUCT_FORM",product);
		ArrayList<ORIGSubForm> productSubForms = FormControl.getSubForm(formId,roleId,applicationGroup,request);
		if(!Util.empty(productSubForms)){
			for(ORIGSubForm subForm:productSubForms){
				logger.debug("subForm.getFileName() >> "+subForm.getFileName());
				String productGroupSubformId = subForm.getGroupSubFormId();
				String productSubformId = subForm.getSubFormID();%>			
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

<!-- If Product is KPL - Choose KPL Product in DropdownList -->
<% if(isKPL) {%>
  <script>
  	var selectObj = $("[name='PRODUCTS_CARD_TYPE']");
  	if(typeof selectObj[0] !== "undefined")
  	{	
  		if(!(selectObj[0].selectize.isLocked))
  		{selectObj[0].selectize.setValue(PRODUCT_CODE_PLG);}
  	}
  </script>
<% } %>
