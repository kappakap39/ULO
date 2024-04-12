<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<% 
	Logger log = Logger.getLogger(this.getClass());
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
// 	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();

	String searchType = (String) request.getSession().getAttribute("searchType");
	
	String displayMode = formUtil.getDisplayMode("DATA_FINAL_DECISION_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	//Vector vAddressType = (Vector)origc.getNaosCacheDataMs(plapplicationDataM.getBusinessClassId(), 12);
	
// 	log.debug("PLORIGForm.getFormID()= "+PLORIGForm.getFormID());
// 	log.debug("Data Final plapplicationDataM.getJobState()"+plapplicationDataM.getJobState());

// 	String jobstate=plapplicationDataM.getJobState();
// 	Vector<ORIGCacheDataM> decisionVect=null;
// 	Vector<ORIGCacheDataM> decisionResonVect=null;
// 	String appDecision="";
// 	PLOrigUtility plOrigUtil=PLOrigUtility.getInstance();
// 	String  role=(String)ORIGUser.getRoles().get(0);
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
// 	Vector vDfRejectRole = ORIGUser.getRoles();
	Vector<String> dfRole = new Vector<String>();
	dfRole.insertElementAt(OrigConstant.ROLE_DF,0);
	
	String dfDecision = null;
	
	log.debug("applicationM.getDF_Decision() >>" +applicationM.getDF_Decision());
	
	if(OrigConstant.Action.COMPLETE_REJECT.equals(applicationM.getDF_Decision())
			|| OrigConstant.Action.COMPLETE_CIS.equals(applicationM.getDF_Decision())){
		dfDecision = "Y";
	}
	
%>

<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
	 <tr height="35">
		 <td class="textColS" nowrap="nowrap">
		 	<%=PLMessageResourceUtil.getTextDescription(request, "DF_INFO_COMPLETE",ORIGUser,personalM.getCustomerType(),"DATA_FINAL_DECISION_SUBFORM","df_reject_decision")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),dfRole,"DATA_FINAL_DECISION _SUBFORM","df_reject_decision")%>&nbsp;:&nbsp;
		 </td>
		 <td align="left" class="inputCol" width="80%"><%=HTMLRenderUtil.displayCheckBoxTagDesc(dfDecision,"Y",displayMode,"df_reject_decision","","") %> </td>
	 </tr>
</table>	 

<script language="javascript">
//#septemwi comment 
// $(document).ready(function(){
// 	checkDFDecision();
// 	$('#df_decision').change(function(){checkDFDecision();});
// });
// function checkDFDecision(){
// 		$('#df_decision').removeAttr('value');
// 		var temp = $('#df_decision').is(':checked');
// 		var temp2 = "";
// 		if(temp==true){
// 			$('#df_decision').attr('value','checked');
// 		}else{
// 			$('#df_decision').attr('value','unchecked');
// 		}
// 		temp2 = $('#df_decision').val();
// }
</script>