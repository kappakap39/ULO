<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyRulesExceptionDataM"%>
<%@ page import="com.eaf.orig.master.shared.model.PolicyRulesDataM"%>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM"%>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<HTML>
<jsp:setProperty name="formHandlerManager" property="currentFormHandler"
	value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String imgpath="en_US/images/CAQUE_files/";
	String versionSearch=(String)session.getAttribute("VERSION_SEARCH");
	if(versionSearch==null){ versionSearch="";}	
   // Vector vPolicyMapRules=(Vector)session.getAttribute("POLICY_VERSION_MAP_RULES");
    //Vector vPolicyRules=(Vector)session.getAttribute("POLICY_RULES_CODE");
   // Vector vPolicyLevels=(Vector)session.getAttribute("POLICY_VERSION_LEVEL");
   // String policyLevel=(String)session.getAttribute("LOAD_POLICY_LEVEL");
    Vector vPolicyCode=(Vector)session.getAttribute("CURRENT_POLICY_RULES");
    Vector vPolicyCodeException=(Vector)session.getAttribute("CURRENT_POLICY_RULES_EXCEPTION");    
    if(vPolicyCode==null){vPolicyCode=new Vector();}
    if(vPolicyCodeException==null){vPolicyCodeException=new Vector();}
    //if(policyLevel==null){policyLevel="";}
    //String disbleButton="";
    //if(policyLevel==""){
    //disbleButton=" disabled ";
   // }
      OrigXRulesUtil     origXRulesUtil=OrigXRulesUtil.getInstance();
%>
<%
com.eaf.j2ee.pattern.util.ErrorUtil error = new com.eaf.j2ee.pattern.util.ErrorUtil();
Vector v = ORIGMasterForm.getFormErrors();
System.out.println("Error Size = " + v.size());
for(int i=0; i<v.size();i++) {
	String errorMessage = (String)v.elementAt(i);
	out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
}
ORIGMasterForm.setFormErrors(new Vector());
%>
<HEAD>
<TITLE>Map Policy Rules</TITLE>
</HEAD>
<BODY>
<DIV style="background-color:#e9edf6">
<FIELDSET><LEGEND>Map Exception</LEGEND>
<TABLE width="596" border="0" cellspacing="0" cellpadding="1"
	bgcolor="#ffffff">
	<TR bgcolor="#e9edf6">
		<td width="38">&nbsp;</td>
		<TD>&nbsp;</TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<td>&nbsp;</td>
		<TD>&nbsp;Available policy rules</TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD>Policy rules Exception</TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<td>&nbsp;</td>
		<TD rowspan="5" width="198"><SELECT name="selectPolicyCode" multiple
			size="15" style="width=300" class="font2">
			<%			
			for(int i=0;i<vPolicyCode.size();i++){
		    	PolicyRulesDataM policyRulesDataM=(PolicyRulesDataM)vPolicyCode.get(i);
			 %>
			<OPTION value="<%=policyRulesDataM.getPolicyCode()%>" class="textbox"><%=policyRulesDataM.getPolicyCode()%>:<%=origXRulesUtil.getPolicyDescription(policyRulesDataM.getPolicyCode())%></OPTION>
			<%} %>
		</SELECT></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD rowspan="5"><SELECT name="usedPolicyCode" multiple
			size="15" style="width=300" class="font2">
			<%for(int i=0;i<vPolicyCodeException.size();i++){
		    	OrigPolicyRulesExceptionDataM policyRulesExceptionDataM=(OrigPolicyRulesExceptionDataM)vPolicyCodeException.get(i);%>
			<OPTION value="<%=policyRulesExceptionDataM.getPolicyCode()%>"
				class="textbox"><%=policyRulesExceptionDataM.getPolicyCode()%>:<%=origXRulesUtil.getPolicyDescription(policyRulesExceptionDataM.getPolicyCode())%></OPTION>
			<%} %>
		</SELECT></TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<TD></TD>
		<TD></TD>
		<TD><INPUT type="button" name="btnAdd" value="&gt;&gt;"
			class="button_text" onclick="addPolicyException()" ></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<TD></TD>
		<TD></TD>
		<TD><INPUT type="button" name="btnRemove" value="&lt;&lt;"
			class="button_text" onclick="removePolicyException()" ></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
</TABLE>
</FIELDSET>
 &nbsp;
<TABLE width="100%" border="0" cellspacing="0" cellpadding="1"
	bgcolor="#ffffff">
	<TR bgcolor="#e9edf6">
		<TD width="50%">&nbsp;</TD>
		<TD width="50%">&nbsp;</TD>
	</TR>
	<TR bgcolor="#e9edf6">
		<TD width="50%" align="right">&nbsp;</TD>
		<TD width="50%"><INPUT type="button" name="btnClose" value="Close"
			class="button_text" onclick="javascrip:window.close()"></TD>
	</TR>
</TABLE>
<P>&nbsp;</P>
<P>&nbsp;</P>
<INPUT type="hidden" name="loadPolicy" value=""> <INPUT type="hidden"
	name="mapAction" value=""> <SCRIPT language="JavaScript">	 
/* function loadPolicyMap(){
       if(appFormName.policyLevel.value==''){
         alert('Please Select Level');
         return;
         }
        appFormName.action.value="LoadApprovAuthorPolicyMapRules";
		appFormName.loadPolicy.value = "loadPolicy";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
}*/ 
 
function addPolicyException(){
	    appFormName.action.value="UpdateApprovAuthorPolicyRulesException";
		appFormName.loadPolicy.value = "addPolicy";
		appFormName.handleForm.value = "N";
		appFormName.mapAction.value="add"
		appFormName.submit();	
}
function removePolicyException(){
         appFormName.action.value="UpdateApprovAuthorPolicyRulesException";
		appFormName.loadPolicy.value = "removePolicy";
		appFormName.handleForm.value = "N";
	    appFormName.mapAction.value="remove"
		appFormName.submit();	
} 
function savePolicyException(){
         appFormName.action.value="UpdateApprovAuthorPolicyRulesException";
		appFormName.loadPolicy.value = "savePolicy";
		appFormName.handleForm.value = "N";
	    appFormName.mapAction.value="save"
		appFormName.submit();	
} 

</SCRIPT>
<DIV></DIV>
</DIV>
</BODY>
</HTML>
