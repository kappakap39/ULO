<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine" %>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilter" %>
<%@page import="com.eaf.core.ulo.common.display.ListBoxFilterInf" %>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/BusinessTypePopup.js"></script>
<%
	String subformId = "BUSINESS_TYPE_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)ModuleForm.getObjectForm();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}	
	FormUtil formUtil = new FormUtil(subformId, request);
%>
<div class="panel panel-default">
<div class="panel-body" style="padding-bottom: 15px;">
<div class="row form-horizontal">
	<%	
		String typeId = "ALL_ALL_ALL";
		String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");
		String classBusinessType = SystemConstant.getConstant("CLASS_BUSINESS_TYPE_FILTER");
		String mainBusinessType = SystemConstant.getConstant("FILTER_BUSINESS_TYPE_MAIN");
		String otherBusinessType = SystemConstant.getConstant("FILTER_BUSINESS_TYPE_OTHER");
		String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");
		
		String BusinessTypeFilter = ListBoxFilter.get(classBusinessType);
				
		ArrayList<HashMap<String,Object>> ListMain = null;	
		ArrayList<HashMap<String,Object>> ListOther = null;	
			
// 		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(FIELD_ID_BUSINESS_TYPE,typeId);
		ListBoxFilterInf FilterInf  = null; 
	 try{			        		    	
			FilterInf = (ListBoxFilterInf)Class.forName(BusinessTypeFilter).newInstance();			        		    	
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
		}
		if(null != FilterInf){
			ListMain = FilterInf.filter(mainBusinessType,FIELD_ID_BUSINESS_TYPE, typeId, request);
			ListOther = FilterInf.filter(otherBusinessType,FIELD_ID_BUSINESS_TYPE, typeId, request);
		}
		int indexList = 0;
		for (HashMap<String,Object> ListRadio:ListMain) {
				String CODE = SQLQueryEngine.display(ListRadio,"CODE");
				String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
	%>
	<%
	if((indexList%12)== 0){
	%>	
	<div class="col-xs-4"  style="padding-left: 0px; padding-right: 0px;">
	<%
	}
	%>	
<!-- 	<div class="col-sm-12"> -->
<!-- 		<div class="row"> -->
			
		<div class="col-xs-12" style="padding-left: 0px; padding-right: 0px;">
			<%=HtmlUtil.radioInline("BUSINESS_TYPE_SELECT", "BUSINESS_TYPE_SELECT", "BUSINESS_TYPE_SELECT", personalInfo.getBusinessType(), "", CODE, "style='padding-left: 0px;'", VALUE, "", formUtil) %>
		</div>
<!-- 		</div> -->
<!-- 	</div> -->
	<%
	if((indexList%12)== 11 || indexList==(ListMain.size()-1)){
	%>	
	</div>
	<%
	}
	%>
<%	indexList++;
		}

	if(!Util.empty(ListOther)){
%>
	
	<div class="clearfix"></div>
	<div class="col-sm-12" style=" padding-right: 0px;">
		<div class="form-group"><%=HtmlUtil.getLabel(request, "BUSINESS_TYPE_OTHER","control-label")%></div>
	</div>
	<%
		int indexOtherList = 0;
		for (HashMap<String,Object> ListRadio:ListOther) {
				String CODE = SQLQueryEngine.display(ListRadio,"CODE");
				String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
	%>
	<%
		if(!BUSINESS_TYPE_OTHER.equals(CODE)){
			if((indexOtherList%2)== 0){
	%>	
				<div class="col-xs-4"  style="padding-left: 0px; padding-right: 0px;">
	<%
			}
	%>	
			<div class="col-xs-12"  style="padding-left: 0px; padding-right: 0px;">
				<%=HtmlUtil.radioInline("BUSINESS_TYPE_SELECT", "BUSINESS_TYPE_SELECT", "BUSINESS_TYPE_SELECT", personalInfo.getBusinessType(), "", CODE, "", VALUE, "", formUtil) %>	
			</div>
	<%
			if((indexOtherList%2)== 1 || indexOtherList==(ListOther.size()-2)){
	%>	
			</div>
	<%
			}
		}else{
	%>	
		<div class="col-sm-12" style="padding-left: 0px; padding-right: 0px;">
			<%=HtmlUtil.radioInline("BUSINESS_TYPE_SELECT", "BUSINESS_TYPE_SELECT", "BUSINESS_TYPE_SELECT", personalInfo.getBusinessType(), "", CODE, "", VALUE, "col-sm-4 col-md-5", formUtil) %>
			<%
			if(BUSINESS_TYPE_OTHER.equals(CODE)){
			%>
			<%=HtmlUtil.textBox("BUSINESS_TYPE_SELECT_OTHER", "BUSINESS_TYPE_SELECT_OTHER", "BUSINESS_TYPE_SELECT_OTHER", personalInfo.getBusinessTypeOther(), "", "100","", "col-sm-8 col-md-7", formUtil) %>
			<%
			}
			 %>
		</div>
	<% 	} 
		indexOtherList++; 
		}
	}
	%>
</div>
</div>

</div>

