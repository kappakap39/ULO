<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<!-- show score -->
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<!-- show score -->
<%
	String scroingResult = (String)request.getSession().getAttribute("scoringILOGResult"); 
//	String scroringType=(String)request.getSession().getAttribute("scoringType"); 
	//for get Scroing
	ApplicationDataM applicationDataM=ORIGForm.getAppForm();
	if(applicationDataM==null){
    	applicationDataM=new ApplicationDataM();
	} 
%>
<script language="JavaScript"> 
    if(window.opener.appFormName.application_score_ilog){
	window.opener.appFormName.application_score_ilog.value = '<%=scroingResult%>';
	 }
   window.close();  
</script>
