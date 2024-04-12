<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<% 
	Vector carVect = new Vector();
	Vector gearVect = new Vector();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	VehicleDataM vehicleDataM = ORIGForm.getAppForm().getVehicleDataM();
	VehicleDataM vehicleDataMTmp = ORIGForm.getAppForm().getVehicleDataMTmp();
	String model = "";
    String brand = "";
    String car = "";
    String gear = "";
    String checkRadio = "";
	String status = "";
%>

<input name="itemsPerPage" type="hidden" value="">
<input name="atPage" type="hidden" value="">

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#F4F4F4">
<%if(vehicleDataMTmp != null && vehicleDataM != null){ 
	if(vehicleDataMTmp.getVehicleID() != vehicleDataM.getVehicleID()){
		status = OrigConstant.DrawDownStatus.NEW;
	}else{
		status = OrigConstant.DrawDownStatus.PROGRESS;
		checkRadio = "checked";
	}
%>
	<tr> 
		<td colspan="2">
			<div id="KLTable">
				<table cellpadding="" cellspacing="1" width="100%" align="center">
					<tr bgcolor="#26a846" height="18">
						<td class="TableHeader1" colspan="8" align="left"><%=MessageResourceUtil.getTextDescription(request, "RECENT_CAR") %></td>
					</tr>
					<tr>
						<td class="TableHeader" width="5%"></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "CAR") %></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "BRAND") %></td>
						<td class="TableHeader" width="15%"><%=MessageResourceUtil.getTextDescription(request, "MODEL") %></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "GEAR") %></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "CC") %></td>
						<td class="TableHeader" width="10%" ><%=MessageResourceUtil.getTextDescription(request, "CAR_PRICE") %></td>
						<td class="TableHeader" width="10%" ><%=MessageResourceUtil.getTextDescription(request, "STATUS") %></td>
					</tr>
					<%
						car = cacheUtil.getNaosCacheDisplayNameDataM(9, vehicleDataMTmp.getCar());
					    brand = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", vehicleDataMTmp.getBrand());
						model = cacheUtil.getORIGCacheDisplayNameFormDB(vehicleDataMTmp.getModel(), "CarModel", vehicleDataMTmp.getBrand());
						String[] modelDescs;
						if(model != null){
							modelDescs = model.split(",");
							if(modelDescs != null){
								model = modelDescs[0];
							}
						}
						gear = vehicleDataMTmp.getGear();
						ApplicationDataM applicationDataM = ORIGForm.getAppForm();
						LoanDataM loanDataM = new LoanDataM();
						if(applicationDataM.getLoanVect() != null && applicationDataM.getLoanVect().size() > 0){
							loanDataM = (LoanDataM) applicationDataM.getLoanVect().elementAt(0);
						}
						System.out.println("vehicleDataM.getVehicleID() = "+vehicleDataMTmp.getVehicleID());
						System.out.println("loanDataM = "+loanDataM);
					%>
					<tr>
						<td><input type="radio" name="selectRadio" value="<%=vehicleDataMTmp.getVehicleID()%>" checked></td>
						<td><%=ORIGDisplayFormatUtil.displayHTML(car) %></td>
						<td><%=ORIGDisplayFormatUtil.displayHTML(brand) %></td>
						<td><%=ORIGDisplayFormatUtil.displayHTML(model) %></td>
						<td><%=ORIGDisplayFormatUtil.displayHTML(gear) %></td>
						<td><%=ORIGDisplayFormatUtil.displayCommaNumber(vehicleDataMTmp.getCC()) %></td>
						<td><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt())) %></td>
						<td><%=ORIGDisplayFormatUtil.displayHTML(status) %></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
<%} %>
	<tr> 
		<td colspan="2">
			<div id="KLTable">
				<table cellpadding="" cellspacing="1" width="100%" align="center">
					<tr bgcolor="#26a846" height="18">
						<td class="TableHeader1" colspan="8" align="left"><%=MessageResourceUtil.getTextDescription(request, "CAR_AVAIL_DTLS") %></td>
					</tr>
					<tr>
						<td class="TableHeader" width="5%"></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "CAR") %></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "BRAND") %></td>
						<td class="TableHeader" width="15%"><%=MessageResourceUtil.getTextDescription(request, "MODEL") %></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "GEAR") %></td>
						<td class="TableHeader" width="15%" ><%=MessageResourceUtil.getTextDescription(request, "CC") %></td>
						<td class="TableHeader" width="10%" ><%=MessageResourceUtil.getTextDescription(request, "CAR_PRICE") %></td>
						<td class="TableHeader" width="10%" ><%=MessageResourceUtil.getTextDescription(request, "STATUS") %></td>
					</tr>
					<%
						Vector valueList = VALUE_LIST.getResult(); 
						VALUE_LIST.setResult(null);
						
						if(valueList != null && valueList.size() > 1){
							String[] modelDescs;
							for(int i=1;i<valueList.size();i++){
								checkRadio = "";
								Vector elementList = (Vector)valueList.get(i);
								    car = cacheUtil.getORIGMasterDisplayNameDataM("CarType", (String) elementList.elementAt(2));
								    brand = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", (String) elementList.elementAt(3));
									model = cacheUtil.getORIGCacheDisplayNameFormDB((String) elementList.elementAt(4), "CarModel", (String) elementList.elementAt(3));
									if(model != null){
										modelDescs = model.split(",");
										if(modelDescs != null){
											model = modelDescs[0];
										}
									}
									gear = (String) elementList.elementAt(5);
									status = (String) elementList.elementAt(8);
									if(vehicleDataM != null){
										if(vehicleDataM.getVehicleID() == Integer.parseInt((String) elementList.elementAt(1))){
											checkRadio = "checked";
											status = OrigConstant.DrawDownStatus.PROGRESS;
										}
									}
					%>
								<tr>
									<td><input type="radio" name="selectRadio" value="<%=(String)elementList.elementAt(1)%>" <%=checkRadio %>></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(car) %></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(brand) %></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(model) %></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(gear) %></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(6)) %></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.displayTwoDigitsNumber(Double.parseDouble((String)elementList.elementAt(7)))) %></td>
									<td><%=ORIGDisplayFormatUtil.displayHTML(status) %></td>
								</tr>
					<% 
							}
						}else{
					%>
							<tr>
								<td colspan="8" align="center"><font color="#FF0000"><b>Results Not Found.</b></font></td>
							</tr>
					<%
						}
					%>
							<TR>
								<TD colspan="8" align="right"><input type="button" name="SelectBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SELECT") %>" onClick="saveApp()" class="button_text"><input type="button" name="CloseBtn" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="closeApp()" class="button_text"></TD>
							</TR>
				</table>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
function closeApp(){
	window.close();
}
function saveApp(){
	form = document.appFormName;
	var flag='';
	if(form.selectRadio!=undefined){
		for(var i=0;i<form.selectRadio.length;i++){
			if(form.selectRadio[i].checked){
				flag = 'Y';
				break;
			}
		}
		if(flag=='Y'){
			form.action.value = "SaveCarPopup";
			form.handleForm.value = "N";
			form.submit();
		}else{
			if(form.selectRadio.checked){
				form.action.value = "SaveCarPopup";
				form.handleForm.value = "N";
				form.submit();
			}else{
				alert('Please select Car!!!');
			}
		}
	}
}
</script>