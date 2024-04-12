<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.ProposalDataM" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.cache.properties.ReasonProperties" %>
<%@ page import="com.eaf.orig.shared.model.ReasonDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.eaf.orig.shared.model.LoanDataM"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 	
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("UW_RESULT_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	Vector reasonVect = new Vector();
	String decision;
	if(!ORIGUtility.isEmptyString(applicationDataM.getUwDecision())){
		decision = utility.getReasonDecisionByAppDecision(applicationDataM.getUwDecision());
		reasonVect = utility.getReasonByDesicion(decision);
	}
	String disableItem="";
	if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
	  disableItem=" disabled";
	}
//	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	//Get Display Mode
//	ORIGFormUtil formUtil = new ORIGFormUtil();
//	String displayMode = formUtil.getDisplayMode("", ORIGUser.getRoles(), applicationDataM.getApplicationStatus(), ORIGForm.getFormID());	
	Vector uwReason = utility.getReasonByRole(applicationDataM.getReasonVect(), OrigConstant.ROLE_UW);		
	String menu = (String)request.getSession(true).getAttribute("PROPOSAL_MENU");
	boolean proposalFlag = false;
	if(menu!=null&&menu.equals("Y")){
		proposalFlag = true;
	}
	BigDecimal finalCreditLimit = new BigDecimal(0);
	LoanDataM loanDataM = new LoanDataM();
	ProposalDataM proposalM = new ProposalDataM();
	if(!proposalFlag){
		if(applicationDataM.getLoanVect() != null && applicationDataM.getLoanVect().size() > 0){
			loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
			finalCreditLimit = applicationDataM.getFinalCreditLimit();
		}
	}else{
		proposalM = applicationDataM.getProposalDataM();
		if(proposalM!=null){
			finalCreditLimit = proposalM.getFinalCreditLimit();
		}else{
			proposalM = new ProposalDataM();
		}
	}
	if(finalCreditLimit == null){
		finalCreditLimit = loanDataM.getCostOfFinancialAmt();
	}
	String userName = ORIGUser.getUserName();
	com.eaf.orig.shared.model.PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);

%>
<table cellpadding="" cellspacing="0" width="100%" align="center">
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<%if(! ( OrigConstant.ORIG_FLAG_Y.equalsIgnoreCase( applicationDataM.getDrawDownFlag())||ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE.equals(applicationDataM.getJobState())||ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE.equals(applicationDataM.getJobState()))) {%>
 			    <tr> 
					<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "POLICY_VERSION") %></td>
					<td class="inputCol" width="80%"><%=ORIGDisplayFormatUtil.displayHTML(applicationDataM.getPolicyVersion()) %></td>
				</tr>
				<tr>
					<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_SCORE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"UW_RESULT_SUBFORM","application_score")%></Font> :</td>
					<td class="inputCol">
						<table cellpadding="0" cellspacing="1" width="200" align="left">
						<tr><td width="50">
							<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getScorringResult(),displayMode,"","application_score","textbox"," readonly ","50") %></td>
						<td width="150">
					    	<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction("Calculate Score",displayMode,"button", "","button_text"," onClick=\"mandatoryScoreField(this.form)\" style=\"width:150\"","") %></td>
					    </tr>
					    </table>
					</td>
				</tr>
				<tr>
					<td class="textColS">ILOG <%=MessageResourceUtil.getTextDescription(request, "APPLICATION_SCORE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"UW_RESULT_SUBFORM","application_score_ilog")%></Font> :</td>
					<td class="inputCol">
						<table cellpadding="0" cellspacing="1" width="200" align="left">
						<tr><td width="50">
							<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","application_score_ilog","textbox"," readonly ","50") %></td>
						<td width="150">	
					   		<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction("Calculate  ILOG Score",displayMode,"button", "","button_text"," onClick=\"callILOGScore(this.form)\" style=\"width:150\"","") %></td>
					   	</tr>
					    </table>
					</td>
				</tr>
				<%}%>
				<tr>
					<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FINAL_CREDIT_LIMIT") %> :</td>
					<%if(proposalFlag){%>
						<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(finalCreditLimit),displayMode,"","final_credit_limit","textboxCurrency","onblur=\"javascript:addComma(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","50") %></td>
					<%}else{ %>
						<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(finalCreditLimit),displayMode,"","final_credit_limit","textboxCurrencyReadOnly","onblur=\"javascript:addComma(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" readOnly","50") %></td>
					<%} %>
				</tr>
				<%if(proposalFlag){%>
				<tr>
					<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %> :</td>
					<%	
						String sDate;
						if(proposalM.getProposalExpireDate()==null){
							sDate="";
						}else{
							sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(proposalM.getProposalExpireDate()));
						}
					%>
					<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","proposal_expiry_date","textbox","right","onblur=\"javascript:checkDate('proposal_expiry_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
				</tr>
				<%} %>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#cccccc"></td>
	</tr>
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				
				
				<%if(!proposalFlag){%>
				<tr>
					<td class="textColS" width="20%" rowspan="8"><%=MessageResourceUtil.getTextDescription(request, "DECISION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"UW_RESULT_SUBFORM","decision_uw")%></Font> :</td>
					<td class="inputCol" width="80%"  colspan="2">&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/approve.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.APPROVE,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');clearEscalateGroup();checkApproveApp();\"") %> <%=MessageResourceUtil.getTextDescription(request, "APPROVE") %> </td>
				</tr>
				<tr>
					<td class="inputCol" colspan="2">&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/reject.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.REJECT,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');clearEscalateGroup();\"") %> <%=MessageResourceUtil.getTextDescription(request, "REJECT") %> </td>
				</tr>
				<tr>
					<td class="inputCol" colspan="2">&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/cancel.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.CANCEL,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');clearEscalateGroup();\"") %> <%=MessageResourceUtil.getTextDescription(request, "CANCEL") %> </td>
				</tr>
				<tr>
					<td class="inputCol" colspan="2">&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/pending.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.UW_PENDING,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');clearEscalateGroup();checkExceptionApp();\"") %> <%=MessageResourceUtil.getTextDescription(request, "PENDING") %> </td>
				</tr>
				<% 
					String disabledEscalate = "";
					if(ORIGWFConstant.ApplicationStatus.ESCALATED.equals(applicationDataM.getApplicationStatus())){
						applicationDataM.setUwDecision(null);
						disabledEscalate = "disabled";
					}
				%>
				<tr>
					<td class="inputCol" colspan="2">&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/escalate.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.ESCALATE,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getEscalateGroup();\" "+disabledEscalate) %> <%=MessageResourceUtil.getTextDescription(request, "ESCALATE") %> </td>
				</tr>
				<tr>
					<td class="inputCol" colspan="2">&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/conditionalapprove.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.CONDITIONAL_APPROVE,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');clearEscalateGroup();checkExceptionApp();\"") %> <%=MessageResourceUtil.getTextDescription(request, "CONDITIONAL_APPROVE") %> </td>
				</tr>
				<tr>
					<td class="inputCol"  colspan="2" >&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/sendtocampaign.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:clearEscalateGroup();checkApproveApp();\"") %> <%=MessageResourceUtil.getTextDescription(request, "SEND_TO_XCMR") %></td>
				</tr>
				<tr>
					<td class="inputCol"  colspan="2" >&nbsp;<img src="<%=request.getContextPath()%>/en_US/images/sendtocampaign.gif" border="0" width=16 height=16>&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:clearEscalateGroup();checkApproveApp();\"") %> <%=MessageResourceUtil.getTextDescription(request, "SEND_TO_XUW") %></td>
		
				</tr>
				<%}else{ %>
				<tr>
					<td class="textColS" width="20%" rowspan="6"><%=MessageResourceUtil.getTextDescription(request, "DECISION") %> :</td>
					<td class="inputCol" width="80%"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.APPROVE_PROPOSAL,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');\" ") %> <%=MessageResourceUtil.getTextDescription(request, "APPROVE") %> <img src="<%=request.getContextPath()%>/en_US/images/approve.gif" border="0" width=16 height=16></td>
				</tr>
				<tr>
					<td class="inputCol"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.REJECT_PROPOSAL,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');\"") %> <%=MessageResourceUtil.getTextDescription(request, "REJECT") %> <img src="<%=request.getContextPath()%>/en_US/images/reject.gif" border="0" width=16 height=16></td>
				</tr>
				<tr>
					<td class="inputCol"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction(ORIGWFConstant.ApplicationDecision.PENDING_PROPOSAL,displayMode,"decision_uw",applicationDataM.getUwDecision(),"","","onClick=\"javascript:getReason(this.value,'reason_uw');\"") %> <%=MessageResourceUtil.getTextDescription(request, "PENDING") %> </td>
				</tr>
				<%} %>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#cccccc"></td>
	</tr>
	<tr>
		<td>
			<table cellpadding="" cellspacing="1" width="100%" align="center">
				<tr>
					<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "REASON") %> :</td>
					<td class="inputCol" width="80%"><div id="ReasonIDUW" style="OVERFLOW: scroll;width: 400px; height: 250px; background-color: white;" <%=disableItem%>>
							<%
								if(reasonVect != null){
									ReasonProperties reasonProperties;
									ReasonDataM reasonDataM;
									String check;
									String reasonCode;
									for(int i=0; i<reasonVect.size(); i++){
										check = "";
										reasonProperties = (ReasonProperties) reasonVect.get(i);
										reasonCode = reasonProperties.getCode();
										for(int j=0; j<uwReason.size(); j++){
											reasonDataM  = (ReasonDataM) uwReason.get(j);
											if(reasonCode.equals(reasonDataM.getReasonCode())){
												check = reasonCode;
												break;
											}
										}
							%>
											<%=ORIGDisplayFormatUtil.displayCheckBoxTagDesc(reasonCode,check,displayMode,"reason_uw", "",reasonProperties.getTHDESC()) %><br>
							<%
									}
								}
							%>
						</div></td>
				</tr>
				<tr>
					<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DECISION_REASON") %> :</td>
					<td class="inputCol"><%--=ORIGDisplayFormatUtil.displayInputTagScriptAction(applicationDataM.getUwDecisionReason(),displayMode,"75","decision_reason","textbox","","200") --%>
					 <%=ORIGDisplayFormatUtil.displayInputTextAreaTag_Orig(ORIGDisplayFormatUtil.displayHTML(applicationDataM.getUwDecisionReason()),"decision_reason","5","100",displayMode," onBlur=\"upperCaseFiled(this);textCounter(this,1000)\"  onKeyDown=\"textCounter(this,1000)\" ") %>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<%
	
 %>
<script language="JavaScript">
function checkApproveApp(){
	
	try {
		var form = document.appFormName;
		var decision = "";//form.decision_uw[0].value;
		var radioLength = form.decision_uw.length;
		var counter=0;
	    if(radioLength == undefined) {
 		 decision=form.decision_uw.value;
		}else{
		for (counter = 0; counter < form.decision_uw.length; counter++)
         {
          if (form.decision_uw[counter].checked){
            decision = form.decision_uw[counter].value;
             break;
            }
          }  
        } 
		//alert(decision);
		//if(decision == '<%=ORIGWFConstant.ApplicationDecision.APPROVE%>'){
		   //==========================
		    if(form.application_score.value ==""){
		      alert("Please Excute Application Score");
		      decision = form.decision_uw[counter].checked=false;
		      clearReasonUW('reason_uw');
		      return;
		    }else if(form.car != undefined){
		      	//if(form.car.value==""){
			    //  alert("Please verify Car(New Car or Used Car)");
			    //  decision = form.decision_uw[counter].checked=false;
			    //  clearReasonUW('reason_uw');
			    //  return;
			    //}
		    } else if(form.<%=(String)XRulesConstant.hResultBoxName.get(Long.toString(XRulesConstant.ServiceID.POLICYRULES))%>.value ==""){
		      alert("Please Excute Policy Rules");
		      decision = form.decision_uw[counter].checked=false;
		      clearReasonUW('reason_uw');
		      return;
		    }	     		
		    //alert(counter);
		    var span = document.getElementById("errorMessage");
		//===========================================
			var req = new DataRequestor();
			var url = "CheckEscalateAppServlet";
			var car_type = '';
			if(form.car != undefined){
				//car_type = form.car.value
				car_type = 'N';
			}
			req.addArg(_POST, "car_type", car_type);
			req.addArg(_POST, "applicatonDecision", decision);
			//req.getURL(url, _RETURN_AS_DOM);
			req.getURLForThai(url, _RETURN_AS_DOM);
			req.onload = function (data, obj) {
				//alert(data);
				var escarateReplaceObj =  document.getElementById("EscalateGroup");
				var CMRExReplaceObj =  document.getElementById("CMRExceptionMSG");
				var PolicyExReplaceObj =  document.getElementById("PolicyExceptionMSG");
				 //alert(data);
				if (data!=null) {
 				   // escarateReplaceObj.innerHTML='xxxx'
 				  // alert("Datas"); 				    
					// Display error message					 
					var datas = data.documentElement;
					//alert(datas);										
					if(datas != null){
						var approvalError = datas.getElementsByTagName("error");
					    //alert("approvalError"+approvalError);
						if(approvalError[0] != null){
							//var errSession = sessionError[0].firstChild.nodeValue;
							//alert(errSession);
							//return false;
							//alert('error');
							escarateReplaceObj.innerHTML="";//approvalError[0].firstChild.nodeValue;
							form.decision_uw[counter].checked = false;
							span.innerHTML ='* '+approvalError[0].firstChild.nodeValue;
					     	span.style.display = "block";
				     	    clearReasonUW('reason_uw');
					     	backToTop();
						}else{
						//alert("no Error");
						var decisionDatas= datas.getElementsByTagName("data");
						var decisonData = decisionDatas[0].firstChild.nodeValue;
						//alert(decisonData);
						if(decisonData == '<%=ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION%>'){
						    form.exceptionFlag.value = 'Y';
				         	form.decision_uw[counter].checked = false;
					        form.decision_uw[6].checked = true; 
					        escarateReplaceObj.innerHTML ="" ;
					        CMRExReplaceObj.innerHTML="   "+"Campaign Exception";
					        PolicyExReplaceObj.innerHTML="";	
							span.innerHTML = "";
							span.style.display = "none";	
						    clearReasonUW('reason_uw');				        					
						}else if(decisonData== '<%=OrigConstant.ApplicationDecision.SENDTO_POLICY_EXCEPTION%>'){
						   form.exceptionFlag.value = 'N';
						   form.decision_uw[counter].checked = false;
					       form.decision_uw[7].checked = true; 
					       escarateReplaceObj.innerHTML ="" ;
					       CMRExReplaceObj.innerHTML="";
					       PolicyExReplaceObj.innerHTML="   "+"Policy Exception";
					       span.innerHTML = "";
						   span.style.display = "none";
						   clearReasonUW('reason_uw');
						}else if(decisonData=='pass'){
						 form.exceptionFlag.value = 'N';
						// alert('pass');
						 span.innerHTML = "";
						 span.style.display = "none";
						 
						 if(counter==0){
						 getReason('<%=ORIGWFConstant.ApplicationDecision.APPROVE%>','reason_uw')
						 }else{
						 clearReasonUW('reason_uw');
						 }
						}else if(decisonData=='<%=ORIGWFConstant.ApplicationDecision.ESCALATE%>'){
						  form.exceptionFlag.value = 'N';
						  form.decision_uw[counter].checked = false;
						  form.decision_uw[4].checked = true;
						  form.decision_uw[4].disabled = false;
						  var groupDatas= datas.getElementsByTagName("group");
						  var groupsList = groupDatas[0];
						  var groups = groupsList.childNodes;
						  var escarateCombo='<select name="escalateGroup" class="combobox" <%=disableItem%> ><option value="">Please Select</option>"';
							for(i = 0; i < groups.length; i++){
								var group = groups[i].firstChild.nodeValue;		
								escarateCombo=escarateCombo+ '<option value="'+group+'">'+group+'</option>';						 
							}
						  escarateCombo=escarateCombo+"</select>"
						  escarateReplaceObj.innerHTML = escarateCombo;
						  CMRExReplaceObj.innerHTML="";
						  PolicyExReplaceObj.innerHTML="";
						  form.exceptionFlag.value = 'N';
						  span.innerHTML = "";
						  span.style.display = "none";
						  getReason('<%=ORIGWFConstant.ApplicationDecision.ESCALATE%>','reason_uw')						  
						}
						}
					 }	
				}		
				 
			}
		//}else{
		//	return true;
		//}
	}catch(er) {
		alert("Error checkApproveApp : "+er);
	}
}
function getEscalateGroup(){
	try {
		var form = document.appFormName;
		var span = document.getElementById("errorMessage");
		//var decision = form.decision_uw[0].value;
		//alert(decision);
		//if(decision == '<%//=ORIGWFConstant.ApplicationDecision.APPROVE%>'){
			var req = new DataRequestor();
			var url = "GetEscalateGroupServlet";
			
			if(form.car != undefined){
				//req.addArg(_POST, "car_type", form.car.value);
				req.addArg(_POST, "car_type", "N");
			}
			
			//req.getURL(url);
			req.getURLForThai(url, _RETURN_AS_DOM);
			req.onload = function (data, obj) {
				//alert(data);
				var replaceObj =  document.getElementById("EscalateGroup");
				if (data!=null) { 
					var datas = data.documentElement; 
					if(datas != null){		
					var approvalError = datas.getElementsByTagName("error");
						if(approvalError[0] != null){
							//var errSession = sessionError[0].firstChild.nodeValue;
							//alert(errSession);
							//return false;
							replaceObj.innerHTML="";
							form.decision_uw[4].checked = false;
							span.innerHTML ='* '+ approvalError[0].firstChild.nodeValue;
					     	span.style.display = "block";
					     	clearReasonUW('reason_uw')
							backToTop();
						}else{			
					    var groupDatas= datas.getElementsByTagName("group");
						  var groupsList = groupDatas[0];
						  var groups = groupsList.childNodes;
						  var escarateCombo='<select name="escalateGroup" class="combobox"  <%=disableItem%> ><option value="">Please Select</option>"';
							for(var i = 0; i < groups.length; i++){
								var group = groups[i].firstChild.nodeValue;		
								escarateCombo=escarateCombo+ '<option value="'+group+'">'+group+'</option>';						 
							}
							replaceObj.innerHTML = escarateCombo;
							span.innerHTML = '';
					     	span.style.display = "none";
					     	getReason('<%=ORIGWFConstant.ApplicationDecision.ESCALATE%>','reason_uw');							
						}	
                    } 				
				}												
			}
		//}else{
		//	return true;
		//}
	}catch(er) {
		alert("Error checkApproveApp : "+er);
	}
}
function calculateScoring(){
        var form = document.appFormName;
        var oldTarget=form.target                     
		var aDialog =window.open("","executeScoring",'width=100,height=100,top=2000,left=2000',status=1,toolbar=1);	
		form.action.value = "CalculateScoring";
		form.currentTab.value = "";
		form.formID.value = "";
		form.handleForm.value = "Y"; 
	    form.target = "executeScoring";
	    form.submit();				    
	    form.target=oldTarget;

}
function clearEscalateGroup(){
	var replaceObj =  document.getElementById("EscalateGroup");
	replaceObj.innerHTML = "&nbsp;";
}
function checkExceptionApp(){
	var form = document.appFormName;
	var req = new DataRequestor();
	var url = "CheckExceptionAppServlet";
	req.getURL(url);
	//alert(cacheName);
	req.onload = function (data, obj) {
		if(data == '<%=ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION%>'){
			//form.exceptionFlag.value = 'Y';
			//return "Y";
			var counter=0;
		   var radioLength = form.decision_uw.length;
	       if(radioLength == undefined) {
 		    decision=form.decision_uw.value;
		    }else{
		    for (counter = 0; counter < form.decision_uw.length; counter++)
            {
            if (form.decision_uw[counter].checked){
           		 decision = form.decision_uw[counter].value;
            		 break;
	            }
    		      }  
        		} 
			  form.decision_uw[counter].checked = false;
			 form.decision_uw[7].checked = true; 
			  form.exceptionFlag.value = 'Y';
		}else{
			form.exceptionFlag.value = 'N';
			return "N"
		}
	}
}
function clearReasonUW(reasonField){
	//alert(document.getElementById(reasonField).name);
	//var obj = eval("appFormName."+reasonField);
	//if(obj!=null){
	//	if(!isUndefined(obj.length)){
	//		for(var i=0; i<obj.length; i++){
	//			obj[i].checked = false;
	//		}
	//	}
	//}
	var replaceObj =  document.getElementById("ReasonIDUW");
	replaceObj.innerHTML = '';
}
<% if(ORIGWFConstant.ApplicationDecision.ESCALATE.equals(applicationDataM.getUwDecision())){ %>
		getEscalateGroup();
<% }%>
<% if(displayMode.equals("EDIT")){ %>
deisabledReason('reason_uw', 'decision_uw', '<%=menu%>');
<%}%>

function getReason(decision, reasonField){
	try {
		var form = document.appFormName;
		var req = new DataRequestor();
		var url = "GetReasonByDecisionServlet";
		req.addArg(_POST, "reasonField", reasonField);
		req.addArg(_POST, "decision", decision);
		req.getURL(url);
		req.onload = function (data, obj) {
			//alert(data);
			var replaceObj =  document.getElementById("ReasonIDUW");
			replaceObj.innerHTML = data;
			//deisabledReason('reason_uw', 'decision_uw');
		}
		
	}catch(er) {
		alert("Error getReason : "+er);
	}
}
function upperCaseFiled(fieldName){
  fieldName.value=fieldName.value.toUpperCase();
}
function textCounter(field, maxlimit) {
if (field.value.length > maxlimit) // if too long...trim it!
field.value = field.value.substring(0, maxlimit);
 
}
function callILOGScore(form){
       //alert("Call Ilog");
        var form = document.appFormName;
        var oldTarget=form.target                     
		var aDialog =window.open("","executeScoring",'width=100,height=100,top=2000,left=2000',status=1,toolbar=1);	
		form.action.value = "CalculateILOGScoring";
		form.currentTab.value = "";
		form.formID.value = "";
		form.handleForm.value = "Y"; 
	    form.target = "executeScoring";
	    form.submit();				    
	    form.target=oldTarget;
}
// End -->
</script>