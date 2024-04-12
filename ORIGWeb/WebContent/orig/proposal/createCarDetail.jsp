<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.cache.data.CacheDataM" %>
<%@ page import="com.eaf.orig.shared.model.ValueListM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<%
	
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String errMsg = (String) request.getSession(true).getAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	if (errMsg != null && !"".equals(errMsg)) {
		out.println("<font class=TextWarningNormal>*&nbsp;"+errMsg+"</font>");
		request.getSession(true).removeAttribute(OrigConstant.ERR_MESSAGE_SESSION);
	}
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	CacheDataM cacheM = new CacheDataM();
	Vector coClientGroupVt = new Vector();
	ValueListM valueListMTemp = new ValueListM();
	valueListMTemp=(ValueListM)request.getSession().getAttribute("VALUE_LIST_COCLIENT");
	Vector resultVt = valueListMTemp.getResult();
	if(resultVt==null) resultVt = new Vector();
	for (int i=0;i<resultVt.size();i++){
		cacheM = new CacheDataM();
		Vector elementListTemp = (Vector)resultVt.get(i);
		cacheM.setCode((String)elementListTemp.elementAt(1));
		cacheM.setShortDesc((String)elementListTemp.elementAt(1));
		coClientGroupVt.add(cacheM);
	}
	Vector loanTypeVt = utility.loadCacheByName("LoanType");
	
	StringBuffer carStringBf = new StringBuffer("");
	carStringBf.append("<table class=\"gumayframe3\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
	carStringBf.append("<tr><td class=\"TableHeader\">");
	carStringBf.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
	carStringBf.append("<tr><td class=\"Bigtodotext3\" colspan=\"11\" align=\"left\">Car & Insurance & Loan</td></tr>");
	carStringBf.append("</table></td></tr>");
	carStringBf.append("<tr><td class=\"TableHeader\">");
	carStringBf.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
	carStringBf.append("<tr><td class=\"Bigtodotext3\" width=\"5%\">Delete</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"5%\">Copy</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"5%\">"+MessageResourceUtil.getTextDescription(request, "SEQ")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP_CODE")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"15%\">"+MessageResourceUtil.getTextDescription(request, "CATEGORY")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"15%\">"+MessageResourceUtil.getTextDescription(request, "BRAND")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(request, "CAR")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(request, "CLASSIS_NO")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"5%\">"+MessageResourceUtil.getTextDescription(request, "GEAR")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(request, "CAR_STATUS")+"</td>");
	carStringBf.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(request, "CONTRACT_NO")+"</td>");
	carStringBf.append("</tr>");
	carStringBf.append("</table></td></tr>");
	carStringBf.append("<tr><td align=\"center\" class=\"gumaygrey2\"> ");
	carStringBf.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
	carStringBf.append("<tr>");
	carStringBf.append("<td class=\"jobopening2\" colspan=\"11\" align=\"center\">Results Not Found.</td>");
	carStringBf.append("</tr>");
	carStringBf.append("</table></td></tr>");
	carStringBf.append("</table>");
	String carString = String.valueOf(carStringBf);
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	
%>

<input name="itemsPerPage" type="hidden" value="">
<input name="atPage" type="hidden" value="">
<input name="appRecordID" type="hidden" value="">
<input name="appStatus" type="hidden" value="">
<input name="jobState" type="hidden" value="">
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr>
		<td colspan="4">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		     <tr>
		     	<td colspan="4" height="10"></td>
		     </tr>
			 <tr>
			 	<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP_ID_NO") %>:</td>
			 	<td class="inputCol" width="12%">
			 		<%=ORIGDisplayFormatUtil.displaySelectTagJS(coClientGroupVt, "", "coClientGroup",displayMode, "")%></td>
			 	<td width="5%">
			 		<input type="button" name="OkBtn" value="<%=MessageResourceUtil.getTextDescription(request, "OK") %>" onClick="searchVehicleData()" class="button_text"></td>
			 	<td class="inputCol">&nbsp;</td>
			 </tr>
			 <tr><td colspan="4" class="inputCol">&nbsp;</td>
			 </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<div id="KLTable">
				<table  cellSpacing="0" cellPadding="0"  width="100%" border="0">
					<tr>
						<td colspan="6">
							<table  class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" colspan="4" align="left">Search Result</td>
										</tr>
									</table> 
								</td></tr>
								<tr><td class="TableHeader">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" width="5%" align="center"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></td>
											<td class="Bigtodotext3" width="35%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP_NAME") %></td>
											<td class="Bigtodotext3" width="30%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_LIMIT") %></td>
											<td class="Bigtodotext3" width="30%" align="center"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %></td>
										</tr>
									</table> 
								</td></tr>
								<%
									Vector valueList = VALUE_LIST.getResult(); 
									VALUE_LIST.setResult(null);
									boolean flag = false;
									double totalDrawDown = 0;
									double totalCreateCar = 0;
									double totalFinalCredit = 0;
									double finalCreditLimit = 0;
									double totalFinalCreditExpiry = 0;
									double totalDrawDownTmp = 0;
									if(valueList != null && valueList.size() > 1){
										flag = true;
										Date expiryDate = null;
										Date sysdate = new Date(System.currentTimeMillis());
										for(int i=1;i<valueList.size();i++){
											Vector elementList = (Vector)valueList.get(i);
											if((String)elementList.elementAt(4) == null){
												totalCreateCar = 0;
											}else{
												totalCreateCar = Double.parseDouble((String)elementList.elementAt(4));
											}
											if((String)elementList.elementAt(5) == null){
												totalDrawDown = 0;
											}else{
												totalDrawDown = Double.parseDouble((String)elementList.elementAt(5));
											}
											if((String)elementList.elementAt(3) == null){
												expiryDate = null;
											}else{
												expiryDate = ORIGDisplayFormatUtil.StringToDate((String)elementList.elementAt(3),"dd/mm/yyyy");
											}
											if((String)elementList.elementAt(6) == null){
												totalFinalCreditExpiry = 0;
											}else{
												totalFinalCreditExpiry = Double.parseDouble((String)elementList.elementAt(6));
											}
											if((String)elementList.elementAt(2) == null){
												finalCreditLimit = 0;
											}else{
												finalCreditLimit = Double.parseDouble((String)elementList.elementAt(2));
											}
											System.out.println("sysdate is:"+sysdate);
											System.out.println("expiryDate is:"+expiryDate);
											if(expiryDate != null){
												if(expiryDate.after(sysdate)|| expiryDate.equals(sysdate)){
													totalFinalCredit += finalCreditLimit;
												}
											}
											totalDrawDownTmp = totalDrawDown - totalFinalCreditExpiry;
											if(totalDrawDownTmp < 0){
												totalDrawDownTmp = 0;
											}
											System.out.println("totalDrawDown = "+totalDrawDown);
											System.out.println("totalFinalCreditExpiry = "+totalFinalCreditExpiry);
											System.out.println("totalCreateCar = "+totalCreateCar);
											if(totalDrawDown < totalFinalCreditExpiry){
												totalCreateCar = totalCreateCar - totalDrawDown;
											}else{
												totalCreateCar = totalCreateCar - totalFinalCreditExpiry;
											}
											
											String sDate = "";
											if(elementList.elementAt(3)==null){
												sDate="";
											}else{
												sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(ORIGDisplayFormatUtil.StringToDate((String) elementList.elementAt(3), "dd/mm/yyyy")));
											}
								%>
									<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td class="jobopening2" width="5%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(String.valueOf(i)) %></td>
												<td class="jobopening2" width="35%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></td>
												<td class="jobopening2" width="30%" align="right"><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(Double.parseDouble((String)elementList.elementAt(2)))) %></td>
												<td class="jobopening2" width="30%" align="right"><%=ORIGDisplayFormatUtil.displayHTML(sDate) %></td>
											</tr>
										</table> 
									</td></tr>
								<% 
										}
									}else{
								%>
								<tr><td align="center" class="gumaygrey2">
									<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="jobopening2" colspan="4" align="center">Results Not Found.</td>
										</tr>
									</table> 
								</td></tr>
								<%
									}
								%>
								<tr><td align="center" class="gumaygrey2">
								<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr>
										<td align="right" height="50">
											<div align="center"><span class="font2">
												<jsp:include page="../appform/valuelist.jsp" flush="true" />
											</span></div>
										</td>
									</tr>
								</table> 
							</td></tr>
							</table>
						</td>
					</tr>
					<tr><td height="10" colspan="6"></td></tr>
					<tr>
						<td colspan="6">
							<div id="Summary">
								<table  class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td class="Bigtodotext3" colspan="5" align="left">Summary</td>
											</tr>
										</table> 
									</td></tr>
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td class="Bigtodotext3" width="20%" align="center"><%=MessageResourceUtil.getTextDescription(request, "TOTAL_CREDIT_LIMIT") %></td>
												<td class="Bigtodotext3" width="20%" align="center"><%=MessageResourceUtil.getTextDescription(request, "DRAW_DOWN_AMT") %></td>
												<td class="Bigtodotext3" width="20%" align="center"><%=MessageResourceUtil.getTextDescription(request, "AVALILABLE_AFTER_DEDUCT_DRAW_DOWN") %></td>
												<td class="Bigtodotext3" width="20%" align="center"><%=MessageResourceUtil.getTextDescription(request, "CREAT_CAR_AMT") %></td>
												<td class="Bigtodotext3" width="20%" align="center"><%=MessageResourceUtil.getTextDescription(request, "AVALILABLE_AFTER_DEDUCT_CREATE_CAR") %></td>
											</tr>
										</table>
									</td></tr>
									<%
										//if(flag){
										double availableCar = totalFinalCredit-totalCreateCar;
										if(availableCar < 0){
											availableCar = 0;
										}
										applicationDataM.setTotalCreditLimit(totalFinalCredit);
										applicationDataM.setDrawDownAmount(totalDrawDown);
										applicationDataM.setCreateCarAmount(totalCreateCar);
										applicationDataM.setTotalFinalCreditExpiry(totalFinalCreditExpiry);
									%>
										<tr><td align="center" class="gumaygrey2">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
													<td class="jobopening2" id="totalFinalCredit" align="right"><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalFinalCredit)) %></td>
													<td class="jobopening2" id="totalDrawDown" align="right"><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalDrawDownTmp)) %></td>
													<td class="jobopening2" id="availableDrawDown" align="right"><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalFinalCredit-totalDrawDownTmp)) %></td>
													<td class="jobopening2" id="totalCreateCar" align="right"><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalCreateCar)) %></td>
													<td class="jobopening2" id="availableCreateCar" align="right"><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(availableCar)) %></td>
												</tr>
											</table> 
										</td></tr>
									<% 
										//}else{
									%>
											<!-- <tr>
												<td colspan="5" align="center"><font color="#FF0000"><b>Results Not Found.</b></font></td>
											</tr-->
									<%
										//}
									%>	
								</table>
							</div>
						</td>
					</tr>
					<tr><td height="10" colspan="6"></td></tr>
					<tr>
						<td colspan="6">
							<div id="carDetailResult">
							</div>
						</td>
					</tr>
					<tr><td height="10" colspan="6">
					<%
						String disableAdd = "";
						if(totalFinalCredit-totalCreateCar == 0){
// 							disableAdd = "disabled";
						}
						String disableDelete = "";
						if(totalCreateCar == 0){
							disableDelete = "disabled";
						}
					%>
					<TR>
						<TD class="inputCol" width="7%" align="left"><b>Amount</b> :</td>
						<td width="15%">
							<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"5","dup_amount","textbox","onblur=\"javascript:addComma(this)\" onchange=\"isNumber(this)\" onkeyup=\"isNumber(this)\" onfocus=\"removeCommaField(this)\" "+ "onMouseOver=\""+tooltipUtil.getTooltip(request,"car_copy")+"\" ","") %>
						</td>
						<td width="25%">
							<input type="button" name="dupCarBtn" value="<%=MessageResourceUtil.getTextDescription(request, "DUPLICATE_CAR") %>" class="button_text" onClick="dupCarDetail()"></TD>
						<TD align="center" class="textColS"  width="35%" nowrap="nowrap">
							<%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %> :<%=ORIGDisplayFormatUtil.displaySelectTagScriptActionAndCode_ORIG(loanTypeVt, "", "loan_type",displayMode," onMouseOver=\""+tooltipUtil.getTooltip(request,"loan_type")+"\" ")%>
						</td><td width="5%">
						 	<input type="button" name="AddBtn" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" onClick="loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40','0')" class="button_text" <%=disableAdd %>>
						</td><td width="7%"> 
						 	<input type="button" name="DeleteBtn" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE") %>" onClick="deleteCar()" class="button_text" <%=disableDelete %>>
						</TD>
					</TR>
				</table>
			</div>	
		</td>
	</tr>
	<tr><td height="20"></td></tr>
	<TR>
		<td width="85%"></td>
		<td width="6%">
			<input type="button" name="SaveBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveApp()" class="button">
		</td><td width="6%">
			<input type="button" name="CancelBtn" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" onClick="cancelApp()" class="button">
		</TD>
		<td width="3%"></td>
	</TR>
</table>
<input type="hidden" name="searchType" value="ClientGroup">
<input type="hidden" name="role" value="UW">
<input type="hidden" name="searchVehicle" value="">
<script language="JavaScript">

rewriteCar('<%=carString%>');
function rewriteCar(valueString){
	var objDiv = document.getElementById('carDetailResult');
	objDiv.innerHTML = valueString;
}
function rewriteSummary(valueString){
	var objDiv = document.getElementById('Summary');
	objDiv.innerHTML = valueString;
}


function searchVehicleData(){
	if(appFormName.coClientGroup.value==''){
		alert('Please specify Co-Client group Identification no');
	}else{
		appFormName.searchVehicle.value='Y';	
		var req = new DataRequestor();
		var url = "<%="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/LoadVehicleDetailServlet"; 
		
		req.addArg(_POST, "coClientGroup", appFormName.coClientGroup.value);
		req.getURL(url);
		req.onload = function (data, obj) {
				var objDiv = document.getElementById('carDetailResult');
				index = data.indexOf(':');
				var tableResult = data.substring(0,index);
				var totalCar = data.substring(index+1,data.length);
				objDiv.innerHTML = tableResult;
				if(totalCar == 1){
					document.appFormName.AddBtn.disabled = false;
					document.appFormName.DeleteBtn.disabled = false;
				}else{
					document.appFormName.AddBtn.disabled = false;
					document.appFormName.DeleteBtn.disabled = true;
				}
			};
	}
}
function clickPageList(atPage){
	var form=document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form=document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}

function loadCarPopup(name,action,width,height,top,left,seq,mode) {
	var form=document.appFormName;
	var loanType = form.loan_type.value;
	if(form.searchVehicle.value!='Y'){
		alert('Please specify Co-Client group Identification no');
		return false;
	}else{
		if(loanType==''&& mode!='edit'){
			alert('Please specify Loan Type');
		}else{
			var url = "/ORIGWeb/FrontController?action=" + action + "&seq=" + seq + "&loanType=" + loanType;
			var childwindow = window.open(url,'','width='+width+',height='+height+',top='+top+',left='+left+',scrollbars=1');
			window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
			childwindow.onunload = function(){alert('closing');};
		}
	}
}

function rewriteVehicle(){
		var req = new DataRequestor();
		var url = "<%="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/RewriteCarDetailServlet"; 
		
		req.addArg(_POST, "mode", "Add");
		req.getURL(url);
		req.onload = function (data, obj) {
				var tableResult;
				var totalCreateCar1;
				var index;
				var objDiv = document.getElementById('carDetailResult');
				var objDiv1 = document.getElementById('totalCreateCar');
				index = data.indexOf(':');
				tableResult = data.substring(0,index);
				totalCreateCar1 = data.substring(index+1,data.length);
				objDiv.innerHTML = tableResult;	
				objDiv1.innerHTML = totalCreateCar1;	
				totalCreateCar1 =  parseFloat(removeCommas(totalCreateCar1));
				calculateAvailableCreateCar(totalCreateCar1);
				
				document.appFormName.AddBtn.disabled = false;
				document.appFormName.DeleteBtn.disabled = false;
			};
}
function saveApp(){
	var form=document.appFormName;
	form.action.value = "SaveCarDetailApp";
	form.handleForm.value = "N";
	form.submit();
}

function cancelApp(){
	var form=document.appFormName;
	form.action.value = "FirstAccess";
	form.handleForm.value = "N";
	form.submit();
}

function deleteCar(){
 	var req = new DataRequestor();
	var url = "<%="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/RewriteCarDetailServlet"; 
	var form=document.appFormName;
	var vehicleIdToken = "";
	
	if(form.chkCar!=undefined){
		if(form.chkCar.length!=undefined){
			for(var i=0;i<form.chkCar.length;i++){
				if(form.chkCar[i].checked){
					vehicleIdToken += form.chkCar[i].value+",";
				}
			}
			vehicleIdToken = vehicleIdToken.substring(0,vehicleIdToken.length-1);
		}else{
			if(form.chkCar.checked){
				vehicleIdToken += form.chkCar.value;
			}
		}
	}else{
		return false;
	}	
	
	req.addArg(_POST, "mode", "Delete");
	req.addArg(_POST, "vehicleId", vehicleIdToken);
	req.getURL(url);
	req.onload = function (data, obj) {
			var tableResult;
			var totalCreateCar;
			var index;
			var objDiv = document.getElementById('carDetailResult');
			var objDiv1 = document.getElementById('totalCreateCar');
			index = data.indexOf(':');
			tableResult = data.substring(0,index);
			totalCreateCar = data.substring(index+1,data.length);
			objDiv.innerHTML = tableResult;	
			objDiv1.innerHTML = totalCreateCar;	
			totalCreateCar =  parseFloat(removeCommas(totalCreateCar));
			calculateAvailableCreateCar(totalCreateCar);
		};
}

function calculateAvailableCreateCar(totalCreateCar){
			var totalFinalCredit;
			var objDiv2 = document.getElementById('availableCreateCar');
			var objDiv3 = document.getElementById('totalFinalCredit');
			totalFinalCredit = objDiv3.innerHTML;
			totalFinalCredit = parseFloat(removeCommas(totalFinalCredit));
			var availableCreateCar = totalFinalCredit - totalCreateCar;
			if(availableCreateCar < 0){
				availableCreateCar = 0;
			}
			objDiv2.innerHTML = CurrencyFormatted(availableCreateCar);
}

function CurrencyFormatted(amount)
{
	var i = parseFloat(amount);
	if(isNaN(i)) { i = 0.00; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	i = parseInt((i + .005) * 100);
	i = i / 100;
	s = new String(i);
	if(s.indexOf('.') < 0) { s += '.00'; }
	if(s.indexOf('.') == (s.length - 2)) { s += '0'; }
	s = minus + s;
	return CommaFormatted(s);
}

function CommaFormatted(amount)
{
	var delimiter = ","; // replace comma if desired
	var a = amount.split('.',2);
	var d = a[1];
	var i = parseInt(a[0]);
	if(isNaN(i)) { return ''; }
	var minus = '';
	if(i < 0) { minus = '-'; }
	i = Math.abs(i);
	var n = new String(i);
	var a = [];
	while(n.length > 3)
	{
		var nn = n.substr(n.length-3);
		a.unshift(nn);
		n = n.substr(0,n.length-3);
	}
	if(n.length > 0) { a.unshift(n); }
	n = a.join(delimiter);
	if(d.length < 1) { amount = n; }
	else { amount = n + '.' + d; }
	amount = minus + amount;
	return amount;
}
function dupCarDetail(){
 	var req = new DataRequestor();
	var url = "DupCarDetailServlet"; 
	var form=document.appFormName;
	var objId = form.dupCarID;
	var id;
	//alert(objId.length);
	if (isObject(objId) && !isUndefined(objId.length)) {
		if (objId.length > 0) {
		for (var i = 0; i < objId.length ; i++) {
				if (objId[i].checked) {
					id = objId[i].value;
					break;
				}
			}
		}		
	} else {
		if(objId != null){
			if (objId.checked) {
				id = objId.value;
			}
		}
	}
	var amount = form.dup_amount.value;
	if(id != '' && id != null && amount != ''){
		//alert(id);
		req.addArg(_POST, "dup_amount", amount);
		req.addArg(_POST, "dupCarID", id);
		req.getURL(url);
		req.onload = function (data, obj) {
			//alert(data);
			if(data != 'FAIL'){
				var tableResult;
				var totalCreateCar;
				var index;
				var objDiv = document.getElementById('carDetailResult');
				var objDiv1 = document.getElementById('totalCreateCar');
				index = data.indexOf(':');
				//alert(index);
				tableResult = data.substring(0,index);
				//alert(tableResult);
				totalCreateCar = data.substring(index+1,data.length);
				//alert(totalCreateCar);
				objDiv.innerHTML = tableResult;
				objDiv1.innerHTML = totalCreateCar;
				totalCreateCar =  parseFloat(removeCommas(totalCreateCar));
				//alert(totalCreateCar);
				calculateAvailableCreateCar(totalCreateCar);
			}else{
			 	alert("Total Create Car Detail amount is greater than Available after deduct Created Car Detail Amount");
			 }
		 };
	}else{
		if(id == '' || id == null){
			alert("Pleast select Car");
		}else{
			alert("Please input Amount.");
		}
	}
}
// isObject(a)
// This function returns true if a is an object, array, or function. It returns false if a is a string, number, Boolean, null, or undefined.
function isObject(a) 
{
    return (typeof a == 'object' && !!a) || isFunction(a);
}

// isUndefined(a)
// This function returns true if a is the undefined value. You can get the undefined value from an uninitialized variable or from an object's missing member.
function isUndefined(a) 
{
  	return typeof a == 'undefined';
} 
</script>
