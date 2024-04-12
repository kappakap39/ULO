<%@ page import="java.util.HashMap" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%
	String execResult = (String)request.getSession().getAttribute("execResult"); 
    String  execResultAdjust=(String)request.getSession().getAttribute("execResultAdjust"); 
	String serviceId = (String)request.getSession().getAttribute("xrulseExecuteService");
	String txtResultName=(String) XRulesConstant.hResultBoxName.get(serviceId);//(String)request.getSession().getAttribute("txtResultName");
    String txtButtonName=(String) XRulesConstant.hButtonName.get(serviceId);//(String)request.getSession().getAttribute("txtButtonName");
	//String personalType = (String)request.getSession().getAttribute("personalType1");
	String ncbInterestFlag=(String)(String)request.getSession().getAttribute("debtAmtODInterestFlag"); 
	String xrulesNCBColor=(String)request.getSession().getAttribute("xrulesNCBColor");
	String openPopup=(String)request.getSession().getAttribute("openPopup");;
	String popup = "";
	ApplicationDataM applicationDataM=ORIGForm.getAppForm();
	if(applicationDataM==null){
    	applicationDataM=new ApplicationDataM();
	}
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
		
	
	System.out.println("serviceId is :"+serviceId);
   System.out.println("Result is :"+execResult);
      System.out.println("openPopup is :"+openPopup);
	//set current screen to main Form
com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
System.out.println("ProductSubForm:Screen current ===>"+screenFlowManager.getCurrentScreen());		
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
	int intServiceId=-1;
	try{
	   intServiceId=Integer.parseInt(serviceId);
	}catch (Exception ex){
 	 ex.printStackTrace();
	}
  xrulesNCBColor=(String)XRulesConstant.hNCBColorDisplay.get(xrulesNCBColor);
  if(xrulesNCBColor==null){xrulesNCBColor="";}
  if(execResult==null){execResult="";}
  if(execResultAdjust==null){execResultAdjust="";}
  System.out.println("execResultAdjust  "+execResultAdjust);
%>
<%-- System.out.println("NCB Color "+xrulesNCBColor); --%>
<%-- System.out.println("Application no  "+applicationDataM.getApplicationNo()); --%>
<script language="JavaScript">
 <% if( intServiceId==XRulesConstant.ServiceID.NCB ){	%>
	 window.opener.appFormName.<%=txtResultName%>.style.backgroundColor='<%=xrulesNCBColor%>';
	 window.opener.appFormName.<%=txtResultName%>.value = '<%=execResult%>';
	 if(window.opener.appFormName.application_no){
	    //alert(window.opener.appFormName.application_no.value);
	   if(window.opener.appFormName.application_no.value==''){
 	    window.opener.appFormName.application_no.value='<%=applicationDataM.getApplicationNo()%>';
 	    } 	      	   
	 }	
      window.opener.appFormName.retrieveBtn.disabled=true;
      window.opener.appFormName.editBtn.disabled=true; 	 	
	 <% if(  OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(personalInfoDataM.getCustomerType()) ){%>
	   if(window.opener.appFormName.debt_mor!=undefined){
 	     var debtMOR=window.opener.document.getElementById("debt_mor");
	      if( '<%=ncbInterestFlag%>'=='Y'){
	       window.opener.appFormName.debt_mor.disabled=false;	     
	      //debtMOR.className="textboxCurrencyReadOnly";
	        debtMOR.className="inputformnewCurrency";
	      <%
	         String odInterest=utility.getGeneralParamByCode("DEPT_AMT_OD_INTEREST"); 
	      %>
	      window.opener.appFormName.debt_mor.value="<%=odInterest%>";
	      window.opener.appFormName.debt_mor.readOnly=false;
	      }else{
	      window.opener.appFormName.debt_mor.disabled=true;	  
	      window.opener.appFormName.debt_mor.readOnly=true;    
	      debtMOR.className="inputformnewCurrency";
	      }
	    }
	 <%}%>
	 window.opener.openXrulesPopup('<%=execResult%>',<%=serviceId%>,'edit');
	<%
	} 
	%>
   <% if( OrigConstant.ORIG_FLAG_Y.equals(openPopup) ) { %>
	
	window.opener.appFormName.<%=txtResultName%>.value = '<%=execResult%>';
	window.opener.openXrulesPopup('<%=execResult%>',<%=serviceId%>,'edit');	
 
    var btnNameObject=eval("window.opener.appFormName.<%=txtButtonName%>");    
     btnNameObject.disabled=false;
	<%}else	{
			
	    if(txtButtonName!=null){
	%>   var btnNameObject=eval("window.opener.appFormName.<%=txtButtonName%>");    
	      if(btnNameObject){
          btnNameObject.disabled=false;
          }
    <%}%>      
		 window.opener.appFormName.<%=txtResultName%>.value = '<%=execResult%>'; 
	   <% if( intServiceId==XRulesConstant.ServiceID.DEBT_AMOUNT ){	          	      
	   %>
  	    window.opener.appFormName.<%=txtResultName%>Adjust.value='<%=execResultAdjust%>';	
  	    if(window.opener.appFormName.debt_amount){
  	     window.opener.appFormName.debt_amount.value='<%=execResultAdjust%>';
  	    }
	   <% }%>
	    <% if( intServiceId==XRulesConstant.ServiceID.DEBTBURDEN){	          	      
	    %> 
	     	    window.opener.appFormName.<%=txtResultName%>Adjust.value='<%=execResultAdjust%>';	
	    <%}%> 	        
	<% }%>
 	   <% if( intServiceId==XRulesConstant.ServiceID.POLICYRULES){	          	      
	    %> 
	    var form=window.opener.appFormName;
	    if(form.decision_uw!= undefined){
	    var radioLength = form.decision_uw.length;
		var counter=0;
	    if(radioLength == undefined) {
  		   form.decision_uw.checked=false;
		}else{
		for (counter = 0; counter < form.decision_uw.length; counter++)
           {
           form.decision_uw[counter].checked=false;               
            }  
         } 
        }
	  <%}%> 	
   window.close();
</script>
