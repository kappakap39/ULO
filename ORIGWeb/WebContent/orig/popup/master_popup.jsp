<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="tooltip_js/wz_tooltip.js"></script>
<script type="text/javascript" src="tooltip_js/tip_balloon.js"></script>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MASTER_DATA") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="15"><td>&nbsp;
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
								<tr> 
									<td colspan="4">
										<div id="KLTable">
										<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr><td class="TableHeader">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td class="Bigtodotext3" align="center" width="30%"><%=MessageResourceUtil.getTextDescription(request, "CODE") %></td>
															<td class="Bigtodotext3" align="center" width="70%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>
														</tr>
													</table> 
												</td></tr>
											<%
												Vector valueList = VALUE_LIST.getResult(); 
												if(valueList != null && valueList.size() > 1){
													for(int i=1;i<valueList.size();i++){
														Vector elementList = (Vector)valueList.get(i);
														if(("LoadCarModel").equals(VALUE_LIST.getSearchAction())){
											%>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onclick="javascritp:setData('<%=elementList.elementAt(1)%>','<%=ORIGDisplayFormatUtil.displayText((String) elementList.elementAt(2))%>','<%=elementList.elementAt(5)%>','<%=elementList.elementAt(6)%>','<%=elementList.elementAt(7)%>')">
															<td class="jobopening2" align="center" width="30%"><%=elementList.elementAt(1)%></td>
															<td class="jobopening2" width="70%"><%=ORIGDisplayFormatUtil.displayHTML((String) elementList.elementAt(2))%></td>
														</tr>
													</table> 
												</td></tr>
													
											<% 			}else{ %>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'" onclick="javascritp:setData('<%=elementList.elementAt(1)%>','<%=ORIGDisplayFormatUtil.displayText((String) elementList.elementAt(2))%>')">
															<td class="jobopening2" width="30%" align="center"><%=elementList.elementAt(1)%></td>
															<td class="jobopening2" width="70%"><%=ORIGDisplayFormatUtil.displayHTML((String) elementList.elementAt(2))%></td>
														</tr>
													</table> 
												</td></tr>
											<%			}
													}
												}else{
											%>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
													  		<td class="jobopening2" align="center">Results Not Found.</td>
													  	</tr>
													</table> 
												</td></tr>
											<%
												}
											%>
												<tr><td align="center" class="gumaygrey2">
													<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
														<tr>
															<td colspan="2" align="right" height="50">
																<div align="center"><span class="font2">
																	<jsp:include page="valuelist.jsp" flush="true" />
																</span></div>
															</td>
														</tr>
													</table> 
												</td></tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</td></tr>
</table>
						
<input type="hidden" name="zipcode">
<input type="hidden" name="amphur">
<input type="hidden" name="tambol">
<input type="hidden" name="province">
<input type="text" name="hidden_field" style="display:none">
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
<script language="JavaScript">
 	openerForm = opener.document.appFormName;
 	form = document.appFormName;
  	if(openerForm.zipcode != null){
  		form.zipcode.value = openerForm.zipcode.value;
  	}
  	
  	if(openerForm.province != null){
  		form.province.value = openerForm.province.value;
  	}
  	
  	if(openerForm.amphur != null){
  		form.amphur.value = openerForm.amphur.value;
  	}
  	
  	if(openerForm.tambol != null){
  		form.tambol.value = openerForm.tambol.value;
  	}
  	
  	if(openerForm.car_brand != null){
  		form.car_brand.value = openerForm.car_brand.value;
  	}
</script>