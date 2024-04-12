<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList" %>
<%@page import="org.apache.log4j.Logger"%>
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
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/OccupationPopup.js"></script>
<%
	String subformId = "OCCUPATION_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)ModuleForm.getObjectForm();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}		
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	FormUtil formUtil = new FormUtil(subformId, request);
%>
<div class="panel panel-default">
<div class="panel-body" style="padding-bottom:15px;"> 
<div class="row form-horizontal">
	
<%	
	String typeId = "ALL_ALL_ALL";
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");	
	String classOccupation = SystemConstant.getConstant("CLASS_OCCUPATION_FILTER");
	String mainOccupation = SystemConstant.getConstant("FILTER_OCCUPATION_MAIN");
	String otherOccupation = SystemConstant.getConstant("FILTER_OCCUPATION_OTHER");
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	
	String OccupationFilter = ListBoxFilter.get(classOccupation);
				
	ArrayList<HashMap<String,Object>> ListMain = null;	
	ArrayList<HashMap<String,Object>> ListOther = null;	
// 	ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(FIELD_ID_OCCUPATION,typeId);	
	ListBoxFilterInf FilterInf  = null; 
	 try{			        		    	
		FilterInf = (ListBoxFilterInf)Class.forName(OccupationFilter).newInstance();			        		    	
	} catch (Exception e) {
		logger.fatal("ERROR ",e);
	}
	if(null != FilterInf){
		ListMain = FilterInf.filter(mainOccupation,FIELD_ID_OCCUPATION, typeId, request);
		ListOther = FilterInf.filter(otherOccupation,FIELD_ID_OCCUPATION, typeId, request);
	}
	for (HashMap<String,Object> ListRadio:ListMain) {
			String CODE = SQLQueryEngine.display(ListRadio,"CODE");
			String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
%>

	<div class="col-sm-12">
		<div class="row">
			<div class="col-xs-8">
				<%=HtmlUtil.radioInline("OCCUPATION_SELECT", "OCCUPATION_SELECT_"+personalElementId, "OCCUPATION_SELECT", personalInfo.getOccupation(), "", CODE, "", VALUE, "", formUtil)%>
			</div>
		</div>
	</div>
<%	}
	if(!Util.empty(ListOther)){
%>
	
	<div class="col-sm-12">
		<div class="form-group"><%=HtmlUtil.getLabel(request, "OCCUPATION_OTHER","control-label")%></div>
	</div>
	<%
		for (HashMap<String,Object> ListRadio:ListOther) {
				String CODE = SQLQueryEngine.display(ListRadio,"CODE");
				String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
	%>
	<div class="col-sm-12">
			<%=HtmlUtil.radioInline("OCCUPATION_SELECT", "OCCUPATION_SELECT_"+personalElementId, "OCCUPATION_SELECT", personalInfo.getOccupation(), "", CODE, "", VALUE, "col-sm-4", formUtil)%>
			<%
				if(OCCUPATION_OTHER.equals(CODE)){
			%>
				<%=HtmlUtil.textBox("OCCUPATION_SELECT_OTHER", "OCCUPATION_SELECT_OTHER_"+personalElementId, "OCCUPATION_SELECT_OTHER", personalInfo.getOccpationOther(), "", "100", "", "col-sm-8 col-md-7", formUtil) %>
			<%
			}
			 %>
	</div>
	<%	}
	}
	%>
</div>
</div>

</div>

