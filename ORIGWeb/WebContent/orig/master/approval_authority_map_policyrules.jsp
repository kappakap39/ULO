<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.ApprovAuthorM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyVersionLevelDataM"%>
<%@ page import="com.eaf.orig.master.shared.model.PolicyRulesDataM"%>
<%@ page import="com.eaf.orig.master.shared.model.OrigPolicyLevelMapDataM"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.OrigXRulesUtil"%>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<HTML>
<jsp:setProperty name="formHandlerManager" property="currentFormHandler"
	value="ORIGMasterForm" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String versionSearch=(String)session.getAttribute("VERSION_SEARCH");
	if(versionSearch==null){ versionSearch="";}	
   	Vector vPolicyLevels=(Vector)session.getAttribute("POLICY_VERSION_LEVEL");
    String policyLevel=(String)session.getAttribute("LOAD_POLICY_LEVEL");
    Vector vPolicyCode=(Vector)session.getAttribute("POLICY_CODE");
    Vector vPolicyCodeMap=(Vector)session.getAttribute("CURRENT_POLICY_LEVEL_MAP");    
    if(vPolicyCode==null){vPolicyCode=new Vector();}
    if(vPolicyCodeMap==null){vPolicyCodeMap=new Vector();}
    if(policyLevel==null){policyLevel="";}
    String disbleButton="";
    if(policyLevel==""){
    disbleButton=" disabled ";
    }
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
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">Map Rules </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<INPUT type="button" name="btnSave" value="<%=MessageResourceUtil.getTextDescription(request,"SAVE") %>" class="button" onclick="savePolicyMap()">
									</td><td>
										<INPUT type="button" name="btnClose" value="<%=MessageResourceUtil.getTextDescription(request,"CLOSE") %>" class="button" onclick="javascrip:window.close()">			
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					              	<TD class="textColS" width="300">Policy Code </TD>
					              	<TD class="inputCol" width="198">
					              		<SELECT name="policyLevel"	class="combobox">
										<OPTION value="">Please Selected</OPTION>
										<%for( int i=0;i <vPolicyLevels.size();i++ ){
							     OrigPolicyVersionLevelDataM   prmOrigPolicyVersionLevelDataM=(OrigPolicyVersionLevelDataM)vPolicyLevels.get(i);
							     String selected="";
							         if(policyLevel.equals(prmOrigPolicyVersionLevelDataM.getLevelName())){
							          selected="selected";
							         }          
							      %>
										<OPTION value="<%=prmOrigPolicyVersionLevelDataM.getLevelName()%>"
											<%=selected%>><%=prmOrigPolicyVersionLevelDataM.getLevelName()%></OPTION>
										<%} %>
										</SELECT> 
									</TD>
									<TD class="inputCol" width="198"><INPUT type="button" name="btnLoadPolicyLevel"	value="Load Policy" onclick="loadPolicyMap()" class="button_text" style="width:100"></TD>
									<TD width="206">&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
								<TR>
									<TD class="textColS">Available policy rules</TD>
									<TD>&nbsp;</TD>
									<TD>&nbsp;</TD>
									<TD class="textColS">Selected policy rules</TD>
									<TD>&nbsp;</TD>
								</TR>
								<TR>
									<TD class="inputCol" rowspan="5" colspan="2"><SELECT name="selectPolicyCode" multiple size="15" style="width:300" class="combobox">
										<%			
										for(int i=0;i<vPolicyCode.size();i++){
									    	PolicyRulesDataM policyRulesDataM=(PolicyRulesDataM)vPolicyCode.get(i);
										 %>
										<OPTION value="<%=policyRulesDataM.getPolicyCode()%>" class="textbox"><%=policyRulesDataM.getPolicyCode()%>:<%=origXRulesUtil.getPolicyDescription(policyRulesDataM.getPolicyCode())%></OPTION>
										<%} %>
									</SELECT></TD>
									<TD align="center"><INPUT type="button" name="btnAdd" value="&gt;&gt;"
										class="button_text" onclick="addPolicyMap()" <%=disbleButton%>> <br><br>
										<INPUT type="button" name="btnRemove" value="&lt;&lt;"
										class="button_text" onclick="removePolicyMap()" <%=disbleButton%>>
									</TD>
									<TD class="inputCol" rowspan="5" colspan="2"><SELECT name="usedPolicyCode" multiple	size="15" style="width:300" class="combobox">
										<%for(int i=0;i<vPolicyCodeMap.size();i++){
									    	OrigPolicyLevelMapDataM policyLevelMapDataM=(OrigPolicyLevelMapDataM)vPolicyCodeMap.get(i);%>
										<OPTION value="<%=policyLevelMapDataM.getPolicyCode()%>"
											class="textbox"><%=policyLevelMapDataM.getPolicyCode()%>:<%=origXRulesUtil.getPolicyDescription(policyLevelMapDataM.getPolicyCode())%></OPTION>
										<%} %>
									</SELECT></TD>
								</TR>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>
<INPUT type="hidden" name="loadPolicy" value=""> 
<INPUT type="hidden" name="mapAction" value=""> 
<SCRIPT language="JavaScript">	 
function loadPolicyMap(){
       if(appFormName.policyLevel.value==''){
         alert('Please Select Level');
         return;
         }
        appFormName.action.value="LoadApprovAuthorPolicyMapRules";
		appFormName.loadPolicy.value = "loadPolicy";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
} 
 
function addPolicyMap(){
	    appFormName.action.value="UpdateApprovAuthorPolicyMapRules";
		appFormName.loadPolicy.value = "addPolicy";
		appFormName.handleForm.value = "N";
		appFormName.mapAction.value="add"
		appFormName.submit();	
}
function removePolicyMap(){
         appFormName.action.value="UpdateApprovAuthorPolicyMapRules";
		appFormName.loadPolicy.value = "removePolicy";
		appFormName.handleForm.value = "N";
	    appFormName.mapAction.value="remove"
		appFormName.submit();	
}
function savePolicyMap(){
         appFormName.action.value="UpdateApprovAuthorPolicyMapRules";
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
