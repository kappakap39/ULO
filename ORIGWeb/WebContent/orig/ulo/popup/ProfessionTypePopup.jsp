<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList" %>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
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

<script type="text/javascript" src="orig/ulo/popup/js/ProfessionTypePopup.js"></script>
<%
	String subformId = "PROFESSION_TYPE_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)ModuleForm.getObjectForm();
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	FormUtil formUtil = new FormUtil(subformId, request);
%>
<div class="panel panel-default">
<div class="panel-body" style="padding-bottom: 15px;">
<div class="row form-horizontal">
	<%	
// 		ArrayList<HashMap<String,Object>> List = null;	
		String typeId = "ALL_ALL_ALL";
		String classProfessionType = SystemConstant.getConstant("CLASS_PROFESSION_TYPE_FILTER");
		String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");
		String mainProfession = SystemConstant.getConstant("FILTER_PROFESSION_MAIN");
		String otherProfession = SystemConstant.getConstant("FILTER_PROFESSION_OTHER");
		String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");
	
		String ProfessionTypeFilter = ListBoxFilter.get(classProfessionType);
				
		ArrayList<HashMap<String,Object>> ListMain = null;	
		ArrayList<HashMap<String,Object>> ListOther = null;	
		
// 		List = ListBoxControl.getListBox(FIELD_ID_PROFESSION,typeId);
		ListBoxFilterInf FilterInf  = null; 
	 try{			        		    	
		FilterInf = (ListBoxFilterInf)Class.forName(ProfessionTypeFilter).newInstance();			        		    	
	} catch (Exception e) {
		logger.fatal("ERROR ",e);
	}
	if(null != FilterInf){
		ListMain = FilterInf.filter(mainProfession,FIELD_ID_PROFESSION, typeId, request);
		ListOther = FilterInf.filter(otherProfession,FIELD_ID_PROFESSION, typeId, request);
	}
		for (HashMap<String,Object> ListRadio:ListMain) {

				String CODE = SQLQueryEngine.display(ListRadio,"CODE");
				String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
	%>

	<div class="col-sm-12">
		<div class="row">
			<div class="col-xs-8">
				<%=HtmlUtil.radioInline("PROFESSION_TYPE_SELECT", "PROFESSION_TYPE_SELECT_"+personalElementId, "PROFESSION_TYPE_SELECT", personalInfo.getProfession(), "", CODE, "", VALUE, "", formUtil)%>
			</div>
		</div>
	</div>
<%	}
	if(!Util.empty(ListOther)){
%>
	
	<div class="col-sm-12">
		<div class="form-group"><%=HtmlUtil.getLabel(request, "PROFESSION_OTHER","control-label")%></div>
	</div>
	<%
		int indexList = 0;
		for (HashMap<String,Object> ListRadio:ListOther) {
				String CODE = SQLQueryEngine.display(ListRadio,"CODE");
				String VALUE = SQLQueryEngine.display(ListRadio,"VALUE");
	%>
	<%
		if(!PROFESSION_OTHER.equals(CODE)){
			if((indexList%5)== 0){
	%>	
				<div class="col-xs-6"  style="padding-left: 16px; padding-right: 0px;">
	<%
			}
	%>	
				<div class="col-sm-12"  style="padding-left: 0px; padding-right: 0px;">
					<%=HtmlUtil.radioInline("PROFESSION_TYPE_SELECT", "PROFESSION_TYPE_SELECT_"+personalElementId, "PROFESSION_TYPE_SELECT", personalInfo.getProfession(), "", CODE, "", VALUE, "", formUtil)%>		
				</div>
	<%
			if((indexList%5)== 4 || indexList==(ListOther.size()-2)){
	%>	
				</div>
	<%
			}
		}else{
	%>	
			<div class="col-sm-12" style="padding-left: 16px; padding-right: 0px;">
			<%=HtmlUtil.radioInline("PROFESSION_TYPE_SELECT", "PROFESSION_TYPE_SELECT_"+personalElementId, "PROFESSION_TYPE_SELECT", personalInfo.getProfession(), "", CODE, "", VALUE, "col-sm-4 col-md-5", formUtil)%>		
			<%
			if(PROFESSION_OTHER.equals(CODE)){
			%>
			<%=HtmlUtil.textBoxSpecialCharactor("PROFESSION_TYPE_SELECT_OTHER", "PROFESSION_TYPE_SELECT_OTHER_"+personalElementId, "PROFESSION_TYPE_SELECT_OTHER", personalInfo.getProfessionOther(), "", "80", "", "col-sm-8 col-md-7", formUtil)%>
			<%
			}
			 %>
		</div>
	<%	}
		indexList++;  
		}
	}
	%>
</div>
</div>

</div>


