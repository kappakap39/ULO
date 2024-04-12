<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.xrules.shared.model.RequiredModuleServiceM"%> 
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.wf.shared.ORIGWFConstant"%>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("VERIFICATION_LIST_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
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
	XRulesVerificationResultDataM xRulesVerification=personalInfoDataM.getXrulesVerification();	
	if(xRulesVerification==null){
	   xRulesVerification=new XRulesVerificationResultDataM();
	}
	String disabledVerificationButton="";
	  if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
      disabledVerificationButton="disabled";
    }
    if( OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase( applicationDataM.getDrawDownFlag())){
      disabledVerificationButton="disabled";
    }
    boolean proposalStatus=false;
    if( ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equalsIgnoreCase(applicationDataM.getJobState())||ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equalsIgnoreCase(applicationDataM.getJobState())){
    proposalStatus=true;
    }
    OrigXRulesUtil origXRulesUtil=OrigXRulesUtil.getInstance();         
     Vector vRoles=ORIGUser.getRoles();
     String userRole=(String)vRoles.get(0); 
%>
<script language="javascript">  
 //var buttonDisable="";
 function executeXrules(form,btnName,resName,service){     
    var manFieldResult= mandatoryXRulesField(form,btnName,resName,service);
 }
 function getPostionPopupX(popupWidth){   	 
    var frameWidth =  screen.width;
    var frameHeight =  screen.height;	 
	var result=(frameWidth/2)-(popupWidth/2);
	return result;	
 }
 function executeXrulesService(form,btnName,resName,service){         
    var popupWebAction='';
    var popupWidth='760';
    var popupHeight='300';     
   if(service == <%=XRulesConstant.ServiceID.LPM%>){ 
   	 popupHeight='420';
     popupWebAction='LoadXrulesLPMmainPopup'+'&txtResultName='+resName;
     loadPopup('xrulePopup'+service,popupWebAction,popupWidth+",status=0",popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
   }else if(service ==<%=XRulesConstant.ServiceID.KHONTHAI%> ){  
     popupWidth='425';
     popupHeight='368';
     popupWebAction='LoadXrulesWebVerPopup&serviceid='+service+'&txtResultName='+resName;
     loadPopup('xrulePopup'+service,popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
   }else if(service ==<%=XRulesConstant.ServiceID.THAIREGITRATION%> ){ 
     popupWidth='425';
     popupHeight='368';
     popupWebAction='LoadXrulesWebVerPopup&serviceid='+service+'&txtResultName='+resName;
     loadPopup('xrulePopup'+service,popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
   }else if(service ==<%=XRulesConstant.ServiceID.BOL%> ){ 
     popupWidth='425';
     popupHeight='355';
     popupWebAction='LoadXrulesWebVerPopup&serviceid='+service+'&txtResultName='+resName;
     loadPopup('xrulePopup',popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');   
   }else if(service ==<%=XRulesConstant.ServiceID.YELLOWPAGE%> ){ 
     popupWidth='425';
     popupHeight='368';
     popupWebAction='LoadXrulesWebVerPopup&serviceid='+service+'&txtResultName='+resName;
          loadPopup('xrulePopup'+service,popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>'); 
   }else if(service ==<%=XRulesConstant.ServiceID.PHONEBOOK%> ){ 
      popupWidth='425';
      popupHeight='368';
      popupWebAction='LoadXrulesWebVerPopup&serviceid='+service+'&txtResultName='+resName;
      loadPopup('xrulePopup'+service,popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
   }else if(service ==<%=XRulesConstant.ServiceID.PHONE_VER%> ){  
   	  popupHeight='550';     
      popupWebAction='LoadXrulesPhoneVerPopup&serviceid='+service+'&txtResultName='+resName+'&firstPhoneVerPopup=Y';
      loadPopup('xrulePopup'+service,popupWebAction,popupWidth+",status=0",popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');   
   }else if(service ==<%=XRulesConstant.ServiceID.NCB%> ){  
   var btnNameObject=eval("document.appFormName."+btnName);
   var xrulesResultObject=eval("document.appFormName."+resName);  
   if(form.consent_flag.value!="Y"){ 
       if(confirm("Have you received consent?")){       
        form.consent_flag_display.checked=true;
        form.consent_flag.value="Y";
        var myDate=new Date();
        var month = myDate.getMonth() + 1;
        var Dmonth= (month.toString().length < 2) ? "0" + month : month;
		var day = myDate.getDate();
		var Dday=(day.toString().length < 2) ? "0" + day : day;
		var year = myDate.getFullYear()+543;
        form.consent_date.value=Dday + "/" + Dmonth + "/" + year;
       }else{
       return;
       }
     } 
   if(xrulesResultObject.style.backgroundColor==""){
   }else{
     if(confirm("Do You want to re-execute ?")){
     }else{
       return;
     }
   }
   //Execute NCB
                  //  btnNameObject.disabled=true; 
                    clearVerificationFields(service); 
                    var oldTarget=form.target;    
                    var popupTarget= "execute"+service;                
					var childwindow =window.open("orig/popup/popup_wait_modal.html",popupTarget,'width=400,height=200,top=300,left=400,status=0,toolbar=0');	
					form.action.value = "ExecuteXrules";
					form.currentTab.value = "";
					form.formID.value = "";
					form.handleForm.value = "Y";
					form.xrulseExecuteService.value = service;
					form.txtButtonName.value = btnName;
					form.txtResultName.value=resName;
				    form.target = popupTarget;
				    form.submit();				    				  
				    form.target=oldTarget;     
				    window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	                childwindow.onunload = function(){alert('closing');};	
	                               
   }else if(service ==<%=XRulesConstant.ServiceID.ELIGIBILITY%> ){ 
      popupWidth='550';
      popupHeight='305';
      popupWebAction='LoadXrulesEligibility&serviceid='+service+'&txtResultName='+resName;
      loadPopup('xrulePopup'+service,popupWebAction,popupWidth,popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');
   }else if(service ==<%=XRulesConstant.ServiceID.LTV%> ){      
   	  popupWidth='500';
      popupHeight='260'; 
      popupWebAction='LoadXrulesLTV&serviceid='+service+'&txtResultName='+resName+'&firstPhoneVerPopup=Y';
      loadPopup('xrulePopup'+service,popupWebAction,popupWidth+",status=0",popupHeight,'100',getPostionPopupX(popupWidth),'','','<%=personalType%>');   
   }  else{           
    var btnNameObject=eval("document.appFormName."+btnName);
    var xrulesResultObject=eval("document.appFormName."+resName);   
    //btnNameObject.disabled=true; 
    //Clear AppScore        
  	                clearVerificationFields(service);  
                    var oldTarget=form.target;      
                    var popupTarget= "execute"+service;                 
					var childwindow =window.open("orig/popup/popup_wait_modal.html",popupTarget,'width=400,height=200,top=400,left=300,status=0,toolbar=0');	
					form.action.value = "ExecuteXrules";
					form.currentTab.value = "";
					form.formID.value = "";
					form.handleForm.value = "Y";
					form.xrulseExecuteService.value = service;
					form.txtButtonName.value = btnName;
					form.txtResultName.value=resName;
				    form.target = popupTarget;
				    form.submit();				    
				    form.target=oldTarget;	
				    window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	                childwindow.onunload = function(){alert('closing')};	                	         
	}		
			
 }       
 function openXrulesPopup(exeResult,serviceId,mode){
   if( 
    mode=='view'
   || ((serviceId==<%=XRulesConstant.ServiceID.BLACKLIST%>||serviceId==<%=XRulesConstant.ServiceID.BLACKLIST_VEHICLE%>
       ||serviceId==<%=XRulesConstant.ServiceID.DEDUP%>||serviceId==<%=XRulesConstant.ServiceID.DUP_VEHICLE%>
      )  &&  exeResult=='<%=XRulesConstant.ExecutionResultString.RESULT_FAIL%>' )
   || ((serviceId==<%=XRulesConstant.ServiceID.EXIST_CUSTOMER%>)&& exeResult=='<%=XRulesConstant.ExecutionResultString.RESULT_FOUND%>')    
   ||(serviceId==<%=XRulesConstant.ServiceID.NCB%> && exeResult != '<%=OrigConstant.NCBcolor.BLACK%>') 
   ||(serviceId==<%=XRulesConstant.ServiceID.DEBTBURDEN%>)
   ||(serviceId==<%=XRulesConstant.ServiceID.FICO%>)
   ||(serviceId==<%=XRulesConstant.ServiceID.POLICYRULES%>)
   ){
    var popupWebAction='';
    var popupWidth='1024';
    var popupHeight='320';
    var executeService=serviceId;  
   switch(executeService)
   {
    case <%=XRulesConstant.ServiceID.NCB%>:
		  popupWebAction='LoadXruleNCBSummaryPopup'
		  popupHeight='655';
		  <%  
		  if(userRole.equals(OrigConstant.ROLE_CMR)){%>
		  // alert("CMR unauthorized to view detials?");
		  //return;
		  <%}%>
      	  break;	  	      
	case <%=XRulesConstant.ServiceID.BLACKLIST%>:
	      popupWebAction='LoadXruleBlacklistCustomerPopup';
      	  break;	  	      	      
	case <%=XRulesConstant.ServiceID.BLACKLIST_VEHICLE%>:
	      popupWebAction='LoadXruleBlacklistVehiclePopup';
      	  break;	  	      	         
	case <%=XRulesConstant.ServiceID.EXIST_CUSTOMER%>:
	      popupWebAction='LoadXruleExistingCustomerPopup';
	      popupWidth='1150';
    	  popupHeight='650';
      	  break;	  	  
    case <%=XRulesConstant.ServiceID.DEDUP%>:        
	      popupWidth='1150';
    	  popupHeight='610';
	      popupWebAction='LoadXrulesDedupPopup';
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.DUP_VEHICLE%>:        
	      popupWebAction='LoadXrulesDupVehiclePopup'
      	  break	;  	  
	case <%=XRulesConstant.ServiceID.LPM%>:
		  popupHeight='440';
	      popupWebAction='LoadXrulesLPMmainPopup';
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.POLICYRULES%>:
    	  popupHeight='550';
	      popupWebAction='LoadXrulesPolicyRulesPopup';  //&serviceid='+serviceId+'&txtResultName='+resName;
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.DEBTBURDEN%>:	
	      popupWidth='760';      
	      popupWebAction='LoadXrulesDebtBurdenPopup';
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.FICO%>:
          popupWidth='760';
          popupHeight='290';
	      popupWebAction='LoadXrulesFICOPopup';
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.KHONTHAI%>:
          popupWidth='425';
	      popupHeight='368';
	      popupWebAction='LoadXrulesWebVerPopup&serviceid='+serviceId ;
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.THAIREGITRATION%>:
          popupWidth='425';
	      popupHeight='368';
	      popupWebAction='LoadXrulesWebVerPopup&serviceid='+serviceId;	  
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.BOL%>:
          popupWidth='425';
	      popupHeight='368';
	      popupWebAction='LoadXrulesWebVerPopup&serviceid='+serviceId;	  
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.YELLOWPAGE%>:
          popupWidth='425';
	      popupHeight='368';
	      popupWebAction='LoadXrulesWebVerPopup&serviceid='+serviceId;	  
      	  break	;  	  	 
    case <%=XRulesConstant.ServiceID.PHONEBOOK%>:
          popupWidth='425';
	      popupHeight='368';
	      popupWebAction='LoadXrulesWebVerPopup&serviceid='+serviceId;	  
      	  break;	  
   case <%=XRulesConstant.ServiceID.PHONE_VER%>:
          popupWidth='760';
          popupHeight='550';
          popupWebAction='LoadXrulesPhoneVerPopup&serviceid='+serviceId+'&firstPhoneVerPopup=Y';
      	  break	;    	  		  
	default:
	}   
	if(mode=='view'){
 	 popupWebAction=popupWebAction+'&viewFromReport=Y';
	 }
    loadPopup('xrulePopup'+serviceId,popupWebAction,popupWidth,popupHeight+",status=0",'26',getPostionPopupX(popupWidth),'','','<%=personalType%>');
   }
 
 } 
 function mandatoryVerificationAll(form,btnName,resName,service){
           var manFieldResult= mandatoryXRulesField(form,btnName,resName,service);  
 }
 function executeVerificationAll(){
                    var form=document.appFormName;
                    form.btnExecuteAll.disabled=true;
                    var oldTarget=form.target                     
					var aDialog =window.open("orig/popup/popup_wait_modal.html","executeAll",'width=400,height=200,top=300,left=400,status=0,toolbar=0,modal=yes');	
					form.action.value = "ExecuteAllXrules";
					form.currentTab.value = "";
					form.formID.value = "";
					form.handleForm.value = "Y"; 
				    form.target = "executeAll";
				    form.submit();				    
				    form.target=oldTarget;
                    form.btnExecuteAll.disabled=false;                    
 }  
 function checkMORValue(){
  var form=document.appFormName;
   if( Number(removeCommas(form.debt_mor.value))>100){
     alert('<%=ErrorUtil.getShortErrorMessage(request,"more_greater_than_100")%>');
     form.debt_mor.value='100';
   }
 }
 
 function clearVerificationFields(service){
  var form=document.appFormName;   
  var executeService=Number(service);
  // alert(executeService);
  switch(executeService)
   {
    case <%=XRulesConstant.ServiceID.NCB%>:
		  if(form.application_score){      
            form.application_score.value='';
            } 
              if( form.<%=(String)XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>) {
               form.<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>.value='';
               form.<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>Adjust.value='';
           } 
          var   imgAdjust=window.document.getElementById('imgAdjust');
          if(imgAdjust){         
          imgAdjust.src='./en_US/images/adjust/adjustBack.gif';
          }
      	  break;	  	      
	case <%=XRulesConstant.ServiceID.BLACKLIST%>:	       
      	  break;	  	      	      
	case <%=XRulesConstant.ServiceID.BLACKLIST_VEHICLE%>:	      
      	  break;	  	      	         
	case <%=XRulesConstant.ServiceID.EXIST_CUSTOMER%>:
	       //alert('clear');
            if(form.application_score){      
            form.application_score.value='';
            } 
           if( form.<%=(String)XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>) {
               form.<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>.value='';
               form.<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>Adjust.value='';
           } 
      	  break;	  	  
    case <%=XRulesConstant.ServiceID.DEDUP%>:         
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.DUP_VEHICLE%>:
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.LPM%>: 
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.POLICYRULES%>: 
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.DEBTBURDEN%>:	 
     	 if(form.application_score){      
            form.application_score.value='';
            }  
      	  break;	  	  
	case <%=XRulesConstant.ServiceID.FICO%>: 
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.KHONTHAI%>: 
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.THAIREGITRATION%>: 
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.BOL%>: 
      	  break;	  	  	 
	case <%=XRulesConstant.ServiceID.YELLOWPAGE%>: 
      	  break;	  	  	 
    case <%=XRulesConstant.ServiceID.PHONEBOOK%>:
      	  break	;  
   case <%=XRulesConstant.ServiceID.PHONE_VER%>: 
      	  break;	  
   case <%=XRulesConstant.ServiceID.DEBT_AMOUNT%>:
             if(form.application_score){      
            form.application_score.value='';
            } 
           if( form.<%=(String)XRulesConstant.hResultBoxName.get(String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN))%>) {
               form.<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN))%>.value='';
               form.<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBTBURDEN))%>Adjust.value='';
           } 
      	  break;	    	    	  		  
	default:
	}   
  
  
  
 }
// function enableButton(){
// alert("Enable");
// }
 </script>
<table cellpadding="0" cellspacing="0" width="100%" align="center">
       <% 
        String consentDateDisable=""; 
        String consentDisplayMode=displayMode;
        
        if( userRole!=null &&  userRole.equals(OrigConstant.ROLE_PD)||userRole.equals(OrigConstant.ROLE_XCMR)){        
           consentDateDisable=" disabled ";
           consentDisplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
        }
        if( !OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType()) || 
        	(OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(personalInfoDataM.getPersonalType())&& 
        	(OrigConstant.COBORROWER_FLAG_NO.equals(personalInfoDataM.getCoborrowerFlag())||personalInfoDataM.getCoborrowerFlag()==null ))){
           consentDateDisable=" disabled ";        
        }
       %>
	<tr>
		<td class="text-header-action1"><%=ORIGDisplayFormatUtil.displayCheckBoxTag(OrigConstant.ORIG_FLAG_Y,personalInfoDataM.getConsentFlag(),ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"consent_flag_display"," disabled ",MessageResourceUtil.getTextDescription(request, "NCB_CONSENT")) %>
			<input type="hidden" name="consent_flag" value="<%=ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getConsentFlag())%>"/><%=ORIGUser.getRoles().get(0)%>
		</td>
		<%
		String	sDate=null;
		if(personalInfoDataM.getConsentDate()==null){
          	sDate="";
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getConsentDate()));
		}
		%>
		<td>
		<% if("".equalsIgnoreCase(consentDateDisable)){ %>
			<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,consentDisplayMode,"","consent_date","inputformnew","left","onblur=\"javascript:checkCurrentDate('consent_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %>
		<%}else {%>
			<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(sDate,consentDisplayMode,"","consent_date","inputformnew"," onblur=\"javascript:checkCurrentDate('consent_date')\" " +consentDateDisable , "") %>
		<%}%>
		</td>
		
	</tr>
</table>
<%
  	if(applicationDataM.getJobState()==null){
 	  // applicationDataM.setJobState("ST200");
 	  //set 400 for test (Xrules UW)
 	     //applicationDataM.setJobState("ST0400");
	}    
	//applicationDataM.setJobState("ST0400");
	//OrigXRulesUtil origXRulesUtil=OrigXRulesUtil.getInstance();
  Vector  vRequire=origXRulesUtil.getRequiredModuleServices(applicationDataM,ORIGUser,personalInfoDataM);  
  if(vRequire!=null){
  System.out.println("Vrequire  "+vRequire.size());
  }
  if(vRequire==null){vRequire=new Vector();}
  //set VRequire to session
    session.setAttribute("requireModuleService",vRequire);
 %>
 <table width="800" height="70" border="0" cellpadding="0" cellspacing="0" align="center">
 	<tr><td>
	<FIELDSET ><LEGEND><font class="text-header-action1">Verification List</font></LEGEND>
		<table width="760" height="68" border="0" cellpadding="0" cellspacing="0" align="center">
		  <tr>
		    <td width="225">&nbsp;</td>
		    <td width="450">&nbsp;</td>
		    <td width="85">&nbsp;</td>
		 </tr>	 
  <% for(int i=0;i<vRequire.size();i++){ 
     	boolean isApplicant = true;
	    if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType) || OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
			isApplicant = false;
		}
	    RequiredModuleServiceM reqModuleService=(RequiredModuleServiceM)vRequire.get(i);
	    String serviceId=Long.toString(reqModuleService.getServiceID());
		if (isApplicant || !(reqModuleService.getServiceID()==XRulesConstant.ServiceID.NCB || reqModuleService.getServiceID()==XRulesConstant.ServiceID.FICO)){
		   String btnName=(String)XRulesConstant.hButtonName.get( serviceId);
		   String resultBoxName=(String)XRulesConstant.hResultBoxName.get(serviceId);
		   if( !(reqModuleService.getServiceID()==XRulesConstant.ServiceID.KHONTHAI ||reqModuleService.getServiceID()==XRulesConstant.ServiceID.THAIREGITRATION||
		   		reqModuleService.getServiceID()==XRulesConstant.ServiceID.BOL||reqModuleService.getServiceID()==XRulesConstant.ServiceID.YELLOWPAGE||reqModuleService.getServiceID()==XRulesConstant.ServiceID.PHONEBOOK)){
		   		String result=null;
		   		result=origXRulesUtil.getXRulesVerificationRusult(xRulesVerification,(int)reqModuleService.getServiceID());      
			    if(result==null){result="";   
			    }      
  
			    if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.NCB){   //NCB
			    String ncbColor="";  
			    	ncbColor=(String)XRulesConstant.hNCBColorDisplay.get(result);
			    	if(ncbColor==null)
			    	{ncbColor="";}   
			    	System.out.println("****NCB color :"+ncbColor); 
			    	String ncbDiable="";  
			   	 	ncbDiable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser,personalInfoDataM.getCoborrowerFlag()) ; 
			    	if(!"".equals(disabledVerificationButton) ){
			        	ncbDiable="";
			     	}  
			    	if("".equals(ncbColor)){
			       		result=xRulesVerification.getNCBResult();
			    	} else{
			      		result="";     
			    	}
			     	if(result==null){ 
			       		result="";
			       	}
     
			%>
		<tr style="height:5"><!--NCB-->
		    <td></td>
		    <td></td>
		    <td></td>
		</tr>
		<tr>
			<td valign="top">
			  	<INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" 
			  		style='width:150;' onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')"  <%=disabledVerificationButton%> <%=ncbDiable%> 
			  	    onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
			</td>
  			<td valign="top">
  				<INPUT type="text" name="<%=resultBoxName%>" size="70"  class="inputformnew" readonly style="background-color:<%=ncbColor%>;width:440" value="<%=result%>">
        	</td>
  			<td>
  				<input type="button" name="getResult_<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "GET_RESULT") %>" 
  					onClick="openXrulesPopup('' ,<%=reqModuleService.getServiceID()%>,'view')"  class="smallbtn2" style='width:80;' <%=ncbDiable%> 
  					onMouseOver="<%=tooltipUtil.getTooltip(request,"xrules_get_result") %>">
  			</td>   
 		</tr>
		 <%} else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.BLACKLIST){
		        String btnDisable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser) ;     
		        if(! OrigConstant.XRULES_BUTTON_HIDE.equalsIgnoreCase(btnDisable)){
		        if(!"".equals(disabledVerificationButton) ){
		         btnDisable="";
		         }  
		  %>
		 <tr style="height:5"><!--Blacklist-->
		    <td></td>
		    <td></td>
		    <td></td>
		 </tr>
		  <tr>
		  	<td valign="top">
		    	<INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" style="width:150;"  
		     		onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')" <%=disabledVerificationButton%>  <%=btnDisable%> 
		     		onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
		  	</td>
		  	<td valign="top">
		  		<INPUT type="text" name="<%=resultBoxName%>" size="70" style="width:440" class="inputformnew" readonly value="<%=result%>">
		  	</td>
		 	<td>
		 		<input type="button" name="getResult_<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "GET_RESULT") %>" onClick="openXrulesPopup('' ,<%=reqModuleService.getServiceID()%>,'view')"  
		 		class="smallbtn2" style="width:80;" <%=btnDisable%> onMouseOver="<%=tooltipUtil.getTooltip(request,"xrules_get_result") %>" >
		 	</td>
		 </tr> 
 <% } //btn is "hide" 
   } else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.BLACKLIST_VEHICLE){
        String btnDisable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser) ;     
        if(! OrigConstant.XRULES_BUTTON_HIDE.equalsIgnoreCase(btnDisable)){
        if(!"".equals(disabledVerificationButton) ){
         btnDisable="";
         }  
  %>
 <tr style="height:5"><!--Blacklist Vehicle-->
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
	  <td valign="top">
	     <INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" style="width:150;"  
	     	onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')" <%=disabledVerificationButton%>  <%=btnDisable%> 
	     	onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
	  </td>
	  <td valign="top"><INPUT type="text" name="<%=resultBoxName%>" size="70" style="width:440" class="inputformnew" readonly   value="<%=result%>">
	  </td>
	 <td><input type="button" name="getResult_<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "GET_RESULT") %>"  style="width:80;"
	 	onClick="openXrulesPopup('' ,<%=reqModuleService.getServiceID()%>,'view')" class="smallbtn2" <%=btnDisable%> onMouseOver="<%=tooltipUtil.getTooltip(request,"xrules_get_result") %>" >
	 </td>
 </tr> 
 <% } //btn is "hide" 
   } else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.EXIST_CUSTOMER){
        String btnDisable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser) ;     
        if(! OrigConstant.XRULES_BUTTON_HIDE.equalsIgnoreCase(btnDisable)){
        if(!"".equals(disabledVerificationButton) ){
         btnDisable="";
         }  
  %>
 <tr style="height:5"><!--Blacklist Vehicle-->
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
  <td valign="top">
     <INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" style="width:150;"  
     	onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')" <%=disabledVerificationButton%>  <%=btnDisable%> 
     	onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
  </td>
  <td valign="top">	<INPUT type="text" name="<%=resultBoxName%>" size="70" style="width:440" class="inputformnew" readonly value="<%=result%>">
  </td>
  <td><input type="button" name="getResult_<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "GET_RESULT") %>" onClick="openXrulesPopup('' ,<%=reqModuleService.getServiceID()%>,'view')"  
 		class="smallbtn2" style="width:80;" <%=btnDisable%> onMouseOver="<%=tooltipUtil.getTooltip(request,"xrules_get_result") %>" >
 </td>
 </tr> 
 <%  } //btn is "hide"
   } else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.DEBT_AMOUNT){
      BigDecimal debtInput=null;
     
  String    debtReadonly="";
  if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){
  debtReadonly="readonly";  
  }
  String debtParamDisable="";
  String morScript="";
  String morStyle="inputformnewCurrency";
  String btnDisable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser) ; 
  if(! OrigConstant.XRULES_BUTTON_HIDE.equalsIgnoreCase(btnDisable)){
  if( !"".equals(disabledVerificationButton) ){
        btnDisable="";
   }  
 
  if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType()) ){       
    if(!OrigConstant.ORIG_FLAG_Y.equals(xRulesVerification.getDebtAmountODInterestFlag()) ){
      if(!(OrigConstant.COBORROWER_FLAG_NO.equals(personalInfoDataM.getCoborrowerFlag())&& OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalInfoDataM.getPersonalType()))){
      debtParamDisable="disabled";     
      if(xRulesVerification.getDEBT_BD_PARAM()==null){    
       debtInput=null;
       }else {
        debtInput=xRulesVerification.getDEBT_BD_PARAM();
       }
       }
    }else{
     if(xRulesVerification.getDEBT_BD_PARAM()==null){    
     String odInterest=utility.getGeneralParamByCode("DEPT_AMT_OD_INTEREST");

       if(odInterest!=null){
         debtInput=new  BigDecimal(odInterest);
       }else{
         debtInput=new  BigDecimal(12.35);
       }
       }else {
        debtInput=xRulesVerification.getDEBT_BD_PARAM();
       }
      // morScript="checkMORValue()";
      morScript="";
     // debtReadonly=" readonly ";
     // morStyle="textboxCurrencyReadOnly";
      morStyle="inputformnewCurrency";
    }
   if(proposalStatus){debtParamDisable="disabled";}
   
  }else{   
    if(xRulesVerification.getDEBT_BD_PARAM()==null){    
       debtInput=null;
       }else {
        debtInput=xRulesVerification.getDEBT_BD_PARAM();
       }  
   if(proposalStatus){debtParamDisable="disabled";}   
      
  }
  if(!"".equalsIgnoreCase(debtParamDisable)){
        morStyle="inputformnewCurrency";
       }
 String resultAdjust=origXRulesUtil.getVerificationAdjustResult(xRulesVerification,(int)reqModuleService.getServiceID(),personalInfoDataM); 
 String imgAdjust="./en_US/images/adjust/adjustBack.gif";
  if(xRulesVerification.getVNCBAdjust()!=null&& xRulesVerification.getVNCBAdjust().size()>0){
  imgAdjust="./en_US/images/adjust/adjust.gif";
  }
  %>
 <tr style="height:5"><!--Debt Amount-->
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
	  <td valign="top">
	    <INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" style="width:150;"  
	    	onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')"  <%=disabledVerificationButton%> <%=btnDisable%>  
	    	onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
	  </td>
	  <td valign="top">
	  	<table width="450" border="0" align="left" cellpadding="0" cellspacing="0"> 
	  		<tr><td width='140' valign="top">
	  			<!-- <IMG  src="<%=imgAdjust%>" name="imgAdjust" align="top"/> -->
	  			<INPUT type="text" name="debt_mor" size="12" class="<%=morStyle%>" value="<%=ORIGDisplayFormatUtil.displayCommaNumberZeroToEmpty(debtInput)%>"    
	  				<%=debtReadonly%> <%=debtParamDisable%> onKeyPress="keyPressFloat(this.value,2)" onblur="javascript:addCommas('debt_mor');addDecimalFormat(this);<%=morScript%>" 
	  				onMouseOver="<%=tooltipUtil.getTooltip(request,"debt_amount_mor") %>">
	  		</td><td width='130' valign="top">
	    		<INPUT type="text" name="<%=resultBoxName%>" size="15"  class="inputformnew" readonly   value="<%=result%>" style="width:120">
	    	</td><td class="text-header-action1" width='50'>
	    		Adjust
	    	</td><td width='130' valign="top">
	    		<INPUT type="text" name="<%=resultBoxName%>Adjust" size="15" style="width:120" class="inputformnew" readonly value="<%=resultAdjust%>">
	    	</td></tr>
	 	</table>
	 </td>
	 <td>&nbsp;</td>
 </tr> 
 <%  } //btn is "hide"
   } else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.DEBTBURDEN){
     // BigDecimal debtInput;
     
  //String    debtReadonly="";
  //if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){
  //debtReadonly="readonly";  
  //}
  //String debtParamDisable="";
  String morScript="";
  //String morStyle="textboxCurrency";
  String btnDisable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser) ; 
  if(! OrigConstant.XRULES_BUTTON_HIDE.equalsIgnoreCase(btnDisable)){
  if( !"".equals(disabledVerificationButton) ){
        btnDisable="";
   }  
  
  // if(proposalStatus){debtParamDisable="disabled";}
  //} 
  //if(!"".equalsIgnoreCase(debtParamDisable)){
   //     morStyle="textboxCurrencyReadOnly";
    //   }
 String resultAdjust=origXRulesUtil.getVerificationAdjustResult(xRulesVerification,(int)reqModuleService.getServiceID() ,personalInfoDataM); 
  
  %>
 <tr style="height:5"><!--Debt Burden-->
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
	  <td valign="top">
	    <INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" style="width:150"  
	    	onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')"  <%=disabledVerificationButton%> <%=btnDisable%>  
	    	onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
	  </td>
	  <td valign="top">
	  	<table width="450" border="0" align="left" cellpadding="0" cellspacing="0"> 
	  		<tr><td width='200' valign="top">
	     		<INPUT type="text" name="<%=resultBoxName%>" size="15"  class="inputformnew" readonly   value="<%=result%>" style="width:185">
	     	</td><td class="text-header-action1" width='50'>
	     		Adjust
	     	</td><td width='200' valign="top">
	     		<INPUT type="text" name="<%=resultBoxName%>Adjust" size="15" style="width:190" class="inputformnew" readonly value="<%=resultAdjust%>">
	     	</td></tr>
	     </table>
	  </td>
	  <td>&nbsp;</td>
 </tr> 
  <% }//btn disable
    } else {
      String btnDisable=origXRulesUtil.getButtonStatus(personalInfoDataM.getPersonalType(),(int)reqModuleService.getServiceID(),personalInfoDataM.getCustomerType(),applicationDataM.getJobState(),ORIGUser) ; 
      

    // if( !(  reqModuleService.getServiceID()==XRulesConstant.ServiceID.PHONE_VER&& OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(personalInfoDataM.getPersonalType()))){
     if(!OrigConstant.XRULES_BUTTON_HIDE.equalsIgnoreCase(btnDisable)){
         if( !"".equals(disabledVerificationButton)){
          btnDisable="";
         }  
   %>   <!-- Not NCB -->
  <tr style="height:5">
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
	  <td valign="top">
	    <INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" style="width:150"  
	    	onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')"  <%=disabledVerificationButton%> <%=btnDisable%> 
	    	onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
	  </td>
	  <td valign="top"><INPUT type="text" name="<%=resultBoxName%>" size="70" style="width:440" class="inputformnew" readonly value="<%=result%>">
	  </td>
      <td>
      	<%if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)&&reqModuleService.getServiceID()!=XRulesConstant.ServiceID.DEBTBURDEN){%>     
     		<input type="button" name="getResult_<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "GET_RESULT") %>" onClick="openXrulesPopup('' ,<%=reqModuleService.getServiceID()%>,'view')"  
     		class="smallbtn2" style="width:80">
     	<%}%>
     </td>
 </tr>
  <% 	} //btn is "hide"
  
     	} //not NCB       
   } //if service id not webver
  } //enf for vRequire
 } //For Webver
 
   for(int i=0;i<vRequire.size();i++){ 
   RequiredModuleServiceM reqModuleService=(RequiredModuleServiceM)vRequire.get(i);
   
   String serviceId=Long.toString(reqModuleService.getServiceID());
   String btnName=(String)XRulesConstant.hButtonName.get(serviceId);
   String resultBoxName=(String)XRulesConstant.hResultBoxName.get(serviceId);
   if( (reqModuleService.getServiceID()==XRulesConstant.ServiceID.KHONTHAI ||reqModuleService.getServiceID()==XRulesConstant.ServiceID.THAIREGITRATION||
   		reqModuleService.getServiceID()==XRulesConstant.ServiceID.BOL||reqModuleService.getServiceID()==XRulesConstant.ServiceID.YELLOWPAGE||reqModuleService.getServiceID()==XRulesConstant.ServiceID.PHONEBOOK)){
   String result=null;
   result=origXRulesUtil.getXRulesVerificationRusult(xRulesVerification,(int)reqModuleService.getServiceID());  
   String website="";
   if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.KHONTHAI ){
     website="http://www.Khonthai.com";
   }else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.THAIREGITRATION){
     website="http://www.thairegistration.com";   
   }else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.BOL){
        website="http://www.bol.co.th";   
   }else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.YELLOWPAGE){   
        website="http://www.yellowpages.co.th";   
   }else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.PHONEBOOK){
        website="http://www.phonebook.tot.co.th";   
   }      
    if(result==null){result="";}
   %>
   <%--} else if(reqModuleService.getServiceID()==XRulesConstant.ServiceID.KHONTHAI ||reqModuleService.getServiceID()==XRulesConstant.ServiceID.THAIREGITRATION||reqModuleService.getServiceID()==XRulesConstant.ServiceID.BOL||reqModuleService.getServiceID()==XRulesConstant.ServiceID.YELLOWPAGE||reqModuleService.getServiceID()==XRulesConstant.ServiceID.PHONEBOOK){
      String website="";
  --%>
 <tr style="height:5"> 
    <td></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
	  <td valign="top">
	  	<INPUT type="button" name="<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %>"  class="smallbtn2" 
	  		onClick="executeXrules(this.form ,'<%=btnName%>','<%=resultBoxName%>','<%=serviceId%>')" <%=disabledVerificationButton%>  
	    	style="width:150;" onMouseOver="<%=tooltipUtil.getTooltip(request,"XRULES_BUTTON_"+reqModuleService.getServiceID()) %>" > 
	  </td>
	  <td valign="top">
	  	<table width="450" border="0" align="left" cellpadding="0" cellspacing="0"> 
	  		<tr><td width='150' nowrap>
	  			<a href="<%=website%>" target="_blank" class="text-header-action1"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_"+reqModuleService.getServiceID()) %></a>
	    	</td><td width='180'>
	    		<INPUT type="text" name="<%=resultBoxName%>" size="45"  style="width:150" class="inputformnew" readonly value="<%=result%>">
	    	</td></tr>
	    </table>
	  </td>
	  <td>
	  	<%if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){%>     
	     <input type="button" name="getResult<%=btnName%>" value="<%=MessageResourceUtil.getTextDescription(request, "GET_RESULT") %>" class="smallbtn2" style="width:80;"
	     	onClick="openXrulesPopup('' ,<%=reqModuleService.getServiceID()%>,'view')" onMouseOver="<%=tooltipUtil.getTooltip(request,"xrules_get_result") %>">
	     <%}%>
	 </td>
 </tr>
   
  <% } //if service id  webver %>
  <% } //enf for vRequire
    %>  
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;
    <%if(proposalStatus){ 
       disabledVerificationButton=" disabled ";
      }
      String executeAllValue="Execute Dedup ,Dedup Vehicle ,Existing Cust";
      String tooltipExecuteAll="execute_app_applicant";
      if( OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(personalInfoDataM.getPersonalType())){
          executeAllValue="Execute Dedup ,Existing Cust";
          tooltipExecuteAll="execute_app_guarantor";
           
      }
       %>
    <!--  // Comment in ULO version -->
    <!-- <input type="button"  name="btnExecuteAll" class="button_text" style="width:200"  value="<%=executeAllValue%>" onClick="mandatoryVerificationAll(this.form,'btnExecuteAll','res_all','<%=XRulesConstant.ServiceID.ALL%>')" <%=disabledVerificationButton%> 
    	onMouseOver="<%=tooltipUtil.getTooltip(request,tooltipExecuteAll) %>" > -->
    </td>
    <td>&nbsp;<!--  <input type="button"  name="btnEnableButton" class="button_text" style="width:100"  value="Enable Button" onClick="enableButton()" <%--=disabledVerificationButton--%> > --><td>
  </tr>   
</table>
</FIELDSET>
</td></tr></table>    
<input type="hidden" name="xrulseExecuteService"  id="xrulseExecuteService" value="" >
<input type="hidden" name="txtResultName"  id="txtResultName" value="" >
<input type="hidden" name="txtButtonName"  id="txtButtonName" value="" >  