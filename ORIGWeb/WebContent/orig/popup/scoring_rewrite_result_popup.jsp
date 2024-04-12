<%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
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
	String scroingResult = (String)request.getSession().getAttribute("scoringResult"); 
	String scroringType=(String)request.getSession().getAttribute("scoringType"); 
	//for get Scroing
	ApplicationDataM applicationDataM=ORIGForm.getAppForm();
	if(applicationDataM==null){
    	applicationDataM=new ApplicationDataM();
	}
	 
	if(OrigConstant.Scoring.SCORING_TYPE_APPSCORE.equals(scroringType)){
    	if("kannikar.t".equalsIgnoreCase(ORIGUser.getUserName())||"origuw5".equalsIgnoreCase(ORIGUser.getUserName())||"uatscr01".equalsIgnoreCase(ORIGUser.getUserName())){
	    // scroingResult+="  "+applicationDataM.gets
// 	     int score=com.eaf.orig.shared.dao.ORIGDAOFactory.getXRulesScoringLogDAO().getLastScoring(applicationDataM.getAppRecordID());
		 int score = PLORIGEJBService.getORIGDAOUtilLocal().getLastScoring(applicationDataM.getAppRecordID());
	     scroingResult+="  ("+score+")";
	    }
	}	
%>
<script language="JavaScript">
   <%if(OrigConstant.Scoring.SCORING_TYPE_APPSCORE.equals(scroringType)){%>
    if(window.opener.appFormName.application_score){
	window.opener.appFormName.application_score.value = '<%=scroingResult%>';
     }   
	<%}else if(OrigConstant.Scoring.SCORING_TYPE_PRESCORE.equals(scroringType)){%> 
	if(window.opener.appFormName.application_no){
	    //alert(window.opener.appFormName.application_no.value);
	   if(window.opener.appFormName.application_no.value==''){
 	    window.opener.appFormName.application_no.value='<%=applicationDataM.getApplicationNo()%>';
 	    window.opener.appFormName.retrieveBtn.diabled=true;
 	    window.opener.appFormName.editBtn.disabled=true;
 	    
	   }
	 }
    if(window.opener.appFormName.application_pre_score){
     	window.opener.appFormName.application_pre_score.value = '<%=scroingResult%>';
     }	
     if(window.opener.appFormName.calPreScore){
     	window.opener.appFormName.calPreScore.disabled = true;
     }	
	<%}%>
   window.close();
</script>
