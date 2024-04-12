<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.model.PreScoreDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<%
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	String searchType = (String) request.getSession().getAttribute("searchType");
	
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	//String displayMode = formUtil.getDisplayMode("PRE_SCORE_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	String displayMode =ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
	
	Vector businessTypeVect = utility.loadCacheByName("BusinessType");
	Vector businessSizeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",1);
	Vector landOwnerVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",6);
	Vector NPLVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",18);
	 
	String preScoreFileName="";
	if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(applicationDataM.getMainCustomerType())){
	preScoreFileName="pre_score_individual.jsp";
	}else if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(applicationDataM.getMainCustomerType())){
	preScoreFileName="pre_score_corporate.jsp";
	}else if(OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(applicationDataM.getMainCustomerType())){
	preScoreFileName="pre_score_foreigner.jsp";
	} 
	String includedFileName=preScoreFileName;
	  	if(! (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase( applicationDataM.getJobState()) )){
      	displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
   	}
   	 PreScoreDataM prmPreScoreDataM=personalInfoDataM.getPreScoreDataM();
   	 if(prmPreScoreDataM==null){
   	   prmPreScoreDataM =new PreScoreDataM();
   	 }
   	 
%>

<table cellpadding="0" cellspacing="0" width="100%">
	<tr><td class="inputCol">
		<jsp:include page="<%=includedFileName%>" flush="true" />
	</td></tr> 
	<tr><td class="inputCol" >&nbsp;</td></tr>
	<tr><td>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "APPLICAION_PRE_SCORE") %> :</td>
			<td class="inputCol" width="60%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(prmPreScoreDataM.getResult()),displayMode,"15","application_pre_score","textboxReadOnly","readonly") %>
				<!-- INPUT size="" type=" text " name="application_pre_score" class="textbox" value=""  maxlength = "50"-->
			</td>  
			<td class="textColS" width="20%"><INPUT  type="button" name="calPreScore" class="button_text" value="Cal Score"  onClick="mandatoryPrescoreField(document.appFormName)" <%if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)||(prmPreScoreDataM.getResult()!=null&&!"".equals(prmPreScoreDataM.getResult()))){out.print(" disabled ") ;}%>>
			</td> 
		</tr></table>
	</td>
	</tr>
</table>
<script language="javascript">
function calculatePreScoring(){
        var form = document.appFormName;
        var oldTarget=form.target                     
		var aDialog =window.open("","executePreScoring",'width=100,height=100,top=2000,left=2000',status=1,toolbar=1);	
		form.action.value = "CalculatePreScoring";
		form.currentTab.value = "";
		form.formID.value = "";
		form.handleForm.value = "Y"; 
	    form.target = "executePreScoring";
	    form.submit();				    
	    form.target=oldTarget;	    
}

$(document).ready(function (){
	$("#pre_score_mkt_code").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "mktCode"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#pre_score_mkt_code").autocomplete("widget").width(240);
			} ,
			select: function(event, ui) {
				$("#pre_score_mkt_code_desc").val(ui.item.desc);
			}
	});	
});
</script>
