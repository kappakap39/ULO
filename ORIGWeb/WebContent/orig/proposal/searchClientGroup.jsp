<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.cache.TableLookupCache" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<%
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	String searchType = "";
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	String clientGroup = cacheUtil.getORIGMasterDisplayNameDataM("ClientGroup", (String)request.getSession().getAttribute("CLIENT_GROUP"));
	HashMap hash=TableLookupCache.getCacheStructure();
	String clientGroupCode = (String)request.getSession().getAttribute("CLIENT_GROUP");
	request.getSession().removeAttribute("CLIENT_GROUP");
	
	
%>		
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR>
		<TD vAlign=top>
			<TABLE  cellSpacing=0 cellPadding=0  width="100%" align=left border="0">
	          	<TR><TD class="sidebar8">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" align="left"  border="0" bgcolor="FFFFFF">	
							<tr>
								<td class="sidebar9">
									<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 height="20" >
										<tr> <td  height="20"></td></tr>
										<tr class="sidebar10"> 
											<td align="center">
												<table width="50%" align=center cellpadding="0" cellspacing="1" border="0">
													<tr>
			                            				<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP")%>:</td>
														<td class="inputCol" colspan="2">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(clientGroupCode,ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"5","client_group","20","client_desc","textbox"," onMouseOver=\""+tooltipUtil.getTooltip(request,"add_car_detail")+"\" ","4","...","button_text","LoadClientGroup",clientGroup,"ClientGroup") %> <input type="button" name="okBtn" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" class="button_text" onClick="javascript:searchClientGroup()"></td>
			                          				</TR>
						                        </table>
						                       <table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
												<tr> 
													<td colspan="3">
														<div id="KLTable">
															<table  class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
																<tr><td class="TableHeader">
																	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
																		<tr>
						                        							<td class="Bigtodotext3" align="center" width="34%"><%=MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP_CODE")%></td>
			                            									<td class="Bigtodotext3" align="center" width="66%"><%=MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP_NAME")%></td>
			                            								</tr>
																	</table> 
																</td>						
																</tr>
																<%
																	Vector valueList = VALUE_LIST.getResult(); 
																	VALUE_LIST.setResult(null);															
																	if(valueList != null && valueList.size() > 0){
																		for(int i=0;i<valueList.size();i++){
																			Vector elementList = (Vector)valueList.get(i);
																%>
																<tr><td align="center" class="gumaygrey2">
																	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
																		<tr onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#F4F4F4'" 
																		onclick="javascritp:gotoAppForm('<%=elementList.elementAt(1)%>')">
																			<td class="jobopening2" width="34%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(1)) %></td>
																			<td class="jobopening2" width="66%"><%=ORIGDisplayFormatUtil.displayHTML(elementList.elementAt(2)) %></td>
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
																  		<td class="jobopening2" colspan="9" align="center">No Record</td>
																  		</tr>
																	</table> 
																</td></tr>
																<%
																	}
																%>
																<tr>
																	<td colspan="3" align="right" height="50">
																		<div align="center"><span class="font2">
																			<jsp:include page="../appform/valuelist.jsp" flush="true" />
																		</span></div>
																	</td>
																</tr>
															</TABLE>
														</div>
													</TD></TR>														
												</table>
			                            	</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td></tr>
				</table>
			</td> 
		</tr>
	</table>

<input type="hidden" name="appRecID" value="">
<input type="hidden" name="selectClientGroup" value="">

<script language="JavaScript" type="text/JavaScript">
function searchClientGroup(){
	if(document.appFormName.client_group.value == ''){
		alert("Please select Client Group.");
	}else{
		appFormName.action.value="SearchClientGroup";
		appFormName.handleForm.value = "N";
		appFormName.submit();
	}
}

function gotoAppForm(clientGroup){
	appFormName.action.value="LoadCarDetail";
	appFormName.selectClientGroup.value=clientGroup;
	appFormName.handleForm.value = "N";
	appFormName.submit();	
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
</script>	  		