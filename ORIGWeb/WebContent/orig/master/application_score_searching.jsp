
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.master.shared.model.ScoreTypeM" %>
<%@ page import="com.eaf.orig.master.shared.model.ScoreCharacterM" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.cache.properties.CustomerTypeProperties" %>

<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />
<jsp:useBean id="ORIGMasterForm" scope="session" class="com.eaf.orig.master.formcontrol.view.form.ORIGMasterFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGMasterForm" />
<jsp:useBean id="SPECIFIC_LIST_BOX_MASTER" scope="session" class="java.util.Vector" />
<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	boolean shw = false;
	boolean shwSearch = false;
	boolean shwEdit = false;

	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	
	String custype = (String)request.getSession().getAttribute("CUS_TYPE");
	String cusThaiDesc = (String)request.getSession().getAttribute("CUS_THAI_DESC");
	String scoreType = (String)request.getSession().getAttribute("SCORE_TYPE");
	String charCode = (String)request.getSession().getAttribute("CHAR_CODE");
	String specEdit = (String)request.getSession().getAttribute("SPECIFIC_EDIT");
	String weight = (String)request.getSession().getAttribute("WEIGHT");
	String disableForm = (String)request.getSession().getAttribute("DISABLE_FORM");
	String accept = (String)request.getSession().getAttribute("ACCEPT");
	String reject = (String)request.getSession().getAttribute("REJECT");
	String accept_used = (String)request.getSession().getAttribute("ACCEPT_USED");
	String reject_used = (String)request.getSession().getAttribute("REJECT_USED");
	String scConstant = (String)request.getSession().getAttribute("SC_CONSTANT");
	ScoreCharacterM charMEdit = (ScoreCharacterM)request.getSession().getAttribute("CHAR_TYPE_M_EDIT");
	if(charMEdit==null){
		charMEdit = new ScoreCharacterM();
	}
	if(weight==null || "".equals(weight)){
		weight = "";
	}else{
		weight = ORIGDisplayFormatUtil.formatNumber("###,##0",utility.stringToBigDecimal(weight));
	}
	
	if(accept==null || "".equals(accept)){
		accept = "";
	}else{
		accept = ORIGDisplayFormatUtil.formatNumber("###,##0",utility.stringToBigDecimal(accept));
	}
	
	if(reject==null || "".equals(reject)){
		reject = "";
	}else{
		reject = ORIGDisplayFormatUtil.formatNumber("###,##0",utility.stringToBigDecimal(reject));
	}
	if(accept_used==null || "".equals(accept_used)){
		accept_used = "";
	}else{
		accept_used = ORIGDisplayFormatUtil.formatNumber("###,##0",utility.stringToBigDecimal(accept_used));
	}
	
	if(reject_used==null || "".equals(reject_used)){
		reject_used = "";
	}else{
		reject_used = ORIGDisplayFormatUtil.formatNumber("###,##0",utility.stringToBigDecimal(reject_used));
	}
	
	if(scConstant==null || "".equals(scConstant)){
		scConstant = "";
	}else{
		scConstant = ORIGDisplayFormatUtil.formatNumber("###,##0",utility.stringToBigDecimal(scConstant));
	}
	
	Vector scoreTypeVect = (Vector)request.getSession().getAttribute("SEARCH_SCORE_TYPE_VECT");
	HashMap charTypeHashMap = (HashMap)request.getSession().getAttribute("SEARCH_CHAR_TYPE_HASH");
	
	System.out.println("ORIGMasterForm.getShwAppScore()="+ORIGMasterForm.getShwAppScore());
	System.out.println("ORIGMasterForm.getShwAddFrm()="+ORIGMasterForm.getShwAddFrm());
	
	CustomerTypeProperties customerTypeProperties;
	Vector customerTypeVect2 = new Vector();
	for(int i=0; i<customerTypeVect.size(); i++){
		customerTypeProperties = (CustomerTypeProperties) customerTypeVect.get(i);
		if(!customerTypeProperties.getCUSTYP().equals(com.eaf.orig.shared.constant.OrigConstant.CUSTOMER_TYPE_FOREIGNER)){
			customerTypeVect2.add(customerTypeProperties);
		}
	}
%>

<input type="hidden" name="shwAppScore" value="">	
<input type="hidden" name="shwAddFrm" value=""><%//  'Add' or 'Edit'%>
<input type="hidden" name="charEdit" value="">	

<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td class="sidebar8"><table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
		<tr class="sidebar10"> <td align="center">
		<table width="100%" align=center cellpadding="0" cellspacing="0" border="0">
			<tr>
				<TD class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %> :</td>
                <td class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect2,custype,"customerType",displayMode,"style=width:150")%></td>
                <td class="inputCol" width="10%"><input type="button" style="width:80" class ="button_text" name="Search" value="<%=MessageResourceUtil.getTextDescription(request, "SEARCH") %>" onclick="searchAppScore()" ></td>
                <td class="inputCol" width="10%"><input type="button" style="width:70" class ="button_text" name="Edit" value="<%=MessageResourceUtil.getTextDescription(request, "EDIT") %>" onclick="editAppScore()" ></td>
                <td width="45%">&nbsp;</td>
            </TR>
		</TABLE>
		</td></tr>
	</table></td></tr>
</table>
<br>

<!-- ********** Search Screen ********** -->

<%
	if("search".equalsIgnoreCase(ORIGMasterForm.getShwAppScore())){
		ORIGMasterForm.setShwAppScore("");
		shwSearch = true;
	}	
	if(shwSearch){  //===> if shwSearch 	
%>
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td>
		<div id="KLTable">
			<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="7%"><%=MessageResourceUtil.getTextDescription(request, "WEIGHT") %></td>
						    <td class="Bigtodotext3" width="15%"><%=MessageResourceUtil.getTextDescription(request, "TYPE") %></td>
						    <td class="Bigtodotext3" width="30%"><%=MessageResourceUtil.getTextDescription(request, "CHARACTERISTIC") %></td>
						    <td class="Bigtodotext3" width="5%"><%=MessageResourceUtil.getTextDescription(request, "MIN") %></td>
						    <td class="Bigtodotext3" width="5%"><%=MessageResourceUtil.getTextDescription(request, "MAX") %></td>
						    <td class="Bigtodotext3" width="33%"><%=MessageResourceUtil.getTextDescription(request, "SPECIFIC_VAL") %></td>
						    <td class="Bigtodotext3" width="5%"><%=MessageResourceUtil.getTextDescription(request, "SCORE") %></td>
						</tr>
					</table> 
				</td></tr>

<% 		
		int count=0;
		if(scoreTypeVect!=null && scoreTypeVect.size()>0){
			ScoreTypeM scoreTypeM;
			for(int i=0;i<scoreTypeVect.size();i++){
				scoreTypeM = (ScoreTypeM)scoreTypeVect.get(i);
				boolean print=true;
				
				if(charTypeHashMap!=null){
					Set chTypekeySet = charTypeHashMap.keySet();
					Iterator chTypekeyIt = chTypekeySet.iterator();
					String chTypekey;
					
					while(chTypekeyIt.hasNext()){
						chTypekey = (String)chTypekeyIt.next();
						
						if(scoreTypeM.getScoreCode().equals(chTypekey)){
					
							Vector charTypeVect = (Vector)charTypeHashMap.get(chTypekey);
							if(charTypeVect!=null && charTypeVect.size()>0){
								ScoreCharacterM characterM;
								for(int j=0;j<charTypeVect.size();j++){
									characterM = (ScoreCharacterM)charTypeVect.get(j);
										
										if(print){
											if("R".equals(characterM.getCharType())){
	%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"><%=scoreTypeM.getScoreWeight() %></td>
							<td class="jobopening2" width="15%"><%=++count%>. <%=scoreTypeM.getScoreType() %></td>
							<td class="jobopening2" width="30%"><%=characterM.getCharDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM.getMinRange() %></td>
							<td class="jobopening2" width="5%"><%=characterM.getMaxRange() %></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
											<%}else if("S".equals(characterM.getCharType())){ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"><%=scoreTypeM.getScoreWeight() %></td>
							<td class="jobopening2" width="15%"><%=++count%>. <%=scoreTypeM.getScoreType() %></td>
							<td class="jobopening2" width="30%"><%=characterM.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"><%=characterM.getSpecDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
											<%}else{ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"><%=scoreTypeM.getScoreWeight() %></td>
							<td class="jobopening2" width="15%"><%=++count%>. <%=scoreTypeM.getScoreType() %></td>
							<td class="jobopening2" width="30%"><%=characterM.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
											<%} %>
	
	<%
										print = false;
										}else{
											if("R".equals(characterM.getCharType())){
	%>										
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"></td>
							<td class="jobopening2" width="15%"></td>
							<td class="jobopening2" width="30%"><%=characterM.getCharDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM.getMinRange() %></td>
							<td class="jobopening2" width="5%"><%=characterM.getMaxRange() %></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
											<%}else if("S".equals(characterM.getCharType())){ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"></td>
							<td class="jobopening2" width="15%"></td>
							<td class="jobopening2" width="30%"><%=characterM.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"><%=characterM.getSpecDesc() %></td>
							<td class="jobopening2" width="5%"><%=characterM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
											<%}else{ %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="7%"></td>
							<td class="jobopening2" width="15%"></td>
							<td class="jobopening2" width="30%"><%=characterM.getCharDesc() %></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="5%"></td>
							<td class="jobopening2" width="33%"></td>
							<td class="jobopening2" width="5%"><%=characterM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
											<%} %>
											
	<%								
										}
								}
							}
						}
					}
				}
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
 <%} %>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td align="right" height="50">
								<input type="button"  class ="button_text" name="Back" value="<%=MessageResourceUtil.getTextDescription(request, "BACK") %>" onclick="backToSearchAppScore()">
							</td>
						</tr>
					</table> 
				</td></tr>
			</TABLE>
		</div>
	</TD></TR>
</TABLE>

<%} %>
<!-- ********** END Search Screen ********** -->

<!-- ********** Edit Screen ********** -->

<%
	if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAppScore())){
		ORIGMasterForm.setShwAppScore("");
		shwEdit = true;
	}	
	
	if(shwEdit){  //===> if shwEdit 	
%>

<!--  For Search: Filter   -->
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">&nbsp;&nbsp;&nbsp;&nbsp; </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<%
											if(shwEdit){  //===> if shwEdit 	
										%>
										<input type="button"  class ="button" name="Summary" value="Summary" onClick="javascript:loadAppScoreSummaryPopup('','PopupAppScoreSummary','900','480','200','100','')" >
									</td><td>
										<input type="reset"  class ="button" name="Cancel" value="Cancel" onclick="cancelScoreType()">			
                                    	<%} %>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					                <TD class="textColS">Customer Type :</TD>
									<TD class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(cusThaiDesc,ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW,"12","searchId","textbox","","30") %></TD>					
								</TR>
								<TR> 
									<TD class="textColS" width="13%">Scoring Result : </TD>
									<TD width="43%">
										<TABLE height=30 cellSpacing=0 width="100%" border=0>
										<TR>
											<TD class="textColS" align="right">Accept(New Car) :</TD>
											<TD class="inputCol">More than and equal to <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(accept,displayMode,"20","accept","textbox","onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onkeyup=giveValue() onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %> score.</TD>
										</TR>
										<TR >
											<TD class="textColS" align="right">Refer(New Car) :</TD>
											<TD class="inputCol">More than and equal to <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(reject,displayMode,"20","rejectRef","textboxReadOnly","readonly","30") %> and</TD>
										</TR>
										<TR>
											<TD class="textColS"></TD>
											<TD class="inputCol">less than &nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(accept,displayMode,"20","acceptRef","textboxReadOnly","readonly","30") %> score.</TD>
										</TR>
										<TR>
											<TD class="textColS" align="right">Reject(New Car) :</TD>
											<TD class="inputCol">Less than <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(reject,displayMode,"20","reject","textbox","onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onkeyup=giveValue() onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %> score.</TD>
										</TR>
										</TABLE>
									</TD>
									<td colspan="2">
										<TABLE height=30 cellSpacing=0 width="100%" border=0> 
										<TR>
											<TD class="textColS" align="right">Accept(Used Car) :</TD>
											<TD class="inputCol">More than and equal to <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(accept_used,displayMode,"20","accept_used","textbox","onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onkeyup=giveValueUsed() onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %> score.</TD>
										</TR>
										<TR>
											<TD class="textColS" align="right">Refer(Used Car) :</TD>
											<TD class="inputCol">More than and equal to <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(reject_used,displayMode,"20","rejectRef_used","textboxReadOnly","readonly","30") %> and</TD>
										</TR>
										<TR>
											<TD class="textColS"></TD>
											<TD class="inputCol">less than &nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(accept_used,displayMode,"20","acceptRef_used","textboxReadOnly","readonly","30") %> score.</TD>
										</TR>
										<TR>
											<TD class="textColS" align="right">Reject(Used Car) :</TD>
											<TD class="inputCol">Less than <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(reject_used,displayMode,"20","reject_used","textbox",
												"onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onkeyup=giveValueUsed() onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %> score.</TD>
										</TR>
										</TABLE>
									</td>
								</TR>
								<TR>
									<TD class="textColS">Scoring Constant :</TD>
									<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(scConstant,displayMode,"20","scConstant","textbox","onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %></TD>					
									<TD colspan="2"></TD>
								</TR>
								<TR>
									<TD class="textColS">Scoring Factor Type :</TD>
									<TD align="left"> 
										<SELECT NAME="scoreType" style="width=300" onchange="getCharType()" class="combobox">
										<%if(scoreType==null || "".equals(scoreType)){ %>
											<OPTION value = "" class=textbox >Please Select</OPTION>
										<%} %>
						                  <%
														Vector scoreTypeMVect = (Vector)request.getSession().getAttribute("SCORE_TYPE_VECT");
				                  							System.out.println("scoreTypeMVect = " + scoreTypeMVect);
				                  							if(scoreTypeMVect==null){
				                  								scoreTypeMVect = new Vector();
				                  							}
				                  							ScoreTypeM scoreTypeM;
				                  							System.out.println("scoreTypeMVect size = " + scoreTypeMVect.size());
				                  							if(scoreTypeMVect.size() > 0){ // if1
																for(int i =0;i<scoreTypeMVect.size();i++){ // for1
																	scoreTypeM = (ScoreTypeM)scoreTypeMVect.get(i);
						
									%>
										<%if(scoreTypeM.getScoreType().equals(scoreType)){ %>
						                  <OPTION value = "<%=scoreTypeM.getScoreCode()%>,<%=scoreTypeM.getScoreType()%>" class=textbox selected="selected"><%=scoreTypeM.getScoreType()%></OPTION>
						                <%}else{ %>
						                  <OPTION value = "<%=scoreTypeM.getScoreCode()%>,<%=scoreTypeM.getScoreType()%>" class=textbox ><%=scoreTypeM.getScoreType()%></OPTION>
						                <%} %>
						                  <%							 
															} // end for1
														}// end if1
									%>
						                </SELECT> 
						            </TD>
									<TD class="textColS" width="6%">weight :</TD>
									<%if(scoreType==null || "".equals(scoreType)){ %>
					                <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(weight,displayMode,"15","weight","textboxReadOnly","readonly","30") %></TD>
					                <%}else{ %>
					                <TD class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(weight,displayMode,"15","weight","textbox","onKeyPress=\"keyPressInteger();\"","30") %></TD>
					                <%} %>
								</TR>
								<TR>
									<TD class="textColS">Characteristic Type :</TD>
									<TD align="left" colspan="3"> 
										<SELECT NAME="charType" style="width=300" onchange="selectCharM()" class="combobox">
										<%if(charCode==null || "".equals(charCode)){ %>
											<OPTION value = "" class=textbox >Please Select</OPTION>
										<%} %>
						                  <%
														Vector charTypeMVect = (Vector)request.getSession().getAttribute("CHAR_TYPE_VECT");
				                  							System.out.println("charTypeMVect = " + charTypeMVect);
				                  							if(charTypeMVect==null){
				                  								charTypeMVect = new Vector();
				                  							}
				                  							ScoreCharacterM characterM;
				                  							System.out.println("charTypeMVect size = " + charTypeMVect.size());
				                  							if(charTypeMVect.size() > 0){ // if1
																for(int i =0;i<charTypeMVect.size();i++){ // for1
																	characterM = (ScoreCharacterM)charTypeMVect.get(i);
						
									%>
										<%if(characterM.getCharCode().equals(charCode)){ %>
						                 <OPTION value = "<%=characterM.getCharCode()%>,<%=characterM.getCharDesc()%>" class=textbox selected="selected"><%=characterM.getCharDesc()%></OPTION>
						                <%}else{ %>
						                  <OPTION value = "<%=characterM.getCharCode()%>,<%=characterM.getCharDesc()%>" class=textbox ><%=characterM.getCharDesc()%></OPTION>
						                <%} %>
						                  <%							 
															} // end for1
														}// end if1
									%>
						                </SELECT> 
						            </TD>
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
<br>
					
<!--  End Search: Filter   -->	
<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr><td>
		<div id="KLTable">
			<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
				<tr><td class="TableHeader">				
					<%if(charCode!=null && !"".equals(charCode)){ %>
					<input type="button"  class ="button_text" name="Add" value="<%=MessageResourceUtil.getTextDescription(request, "ADD") %>" onclick="submitShwAddForm()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Add" value="<%=MessageResourceUtil.getTextDescription(request, "ADD")%>" onclick="submitShwAddForm()" disabled="disabled">
					<%} %>
					<%
					Vector charMVectChk = (Vector)request.getSession().getAttribute("SEL_AND_ADD_CHAR_M_VECT");				
					if(charMVectChk!=null && charMVectChk.size()>0){ %>
					<input type="button"  class ="button_text" name="Delete" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE")%>" onclick="deleteAppScore()" >
					<%}else{ %>
					<input type="button"  class ="button_text" name="Delete" value="<%=MessageResourceUtil.getTextDescription(request, "DELETE")%>" disabled="disabled" >
					<%} %>
				</TD></TR>
				<tr><td class="TableHeader">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="Bigtodotext3" width="3%"></TD>
							<TD class="Bigtodotext3" width="10%">Selected</TD>
							<TD class="Bigtodotext3" width="10%">Min</TD> 													
							<TD class="Bigtodotext3" width="10%">Max</TD>
							<TD class="Bigtodotext3" width="30%">Specific</TD>
							<TD class="Bigtodotext3">Score</TD>					    
						</tr>
					</table> 
				</td></tr>
			<%
						Vector charMVect = (Vector)request.getSession().getAttribute("SEL_AND_ADD_CHAR_M_VECT");
  							System.out.println("charMVect = " + charMVect);
  							if(charMVect==null){
  								charMVect = new Vector();
  							}
  							ScoreCharacterM charM;
  							System.out.println("charMVect size = " + charMVect.size());
  							if(charMVect.size() > 0){ // if1
								for(int i =0;i<charMVect.size();i++){ // for1
									charM = (ScoreCharacterM)charMVect.get(i);
		
					%>
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
							<td class="jobopening2" width="3%"></td>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","charMChk", charM.getScoreCode() + "," + charM.getCharCode() + "," + String.valueOf(charM.getSeq()) ,"") %></td>
							<td class="jobopening2" width="10%"><a href="javascript:gotoEditForm('<%=charM.getScoreSeq()%>,<%=charM.getSeq()%>,<%=charM.getScoreCode()%>,<%=charM.getCharCode()%>')"><%=ORIGDisplayFormatUtil.displayCommaNumber(charM.getMinRange())%></a></td>
							<td class="jobopening2" width="10%"><%=ORIGDisplayFormatUtil.displayCommaNumber(charM.getMaxRange()) %></td>
							<td class="jobopening2" width="30%"><%=charM.getSpecDesc() %></td>
							<td class="jobopening2" width="37%"><%=charM.getScore() %></td>
						</tr>
					</table> 
				</td></tr>
					<%							 
								} // end for1
							}else{// end if1
					%>	
				<tr><td align="center" class="gumaygrey2">
					<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr>
				  			<td class="jobopening2" align="center">Results Not Found.</td>
				  		</tr>
					</table> 
				</td></tr>
					<%} %>
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
			</TABLE>
		</div>
	</TD></TR>
</TABLE>
<br>
<!-- End Show All -->	

<%
	String strOnclick = "";
	String label = "";
	String cancelBtn = "";	
	if("add".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Add";		
		strOnclick = "addScoreChar()";
		cancelBtn = "cancelAdd";
		shw = true;
	}else if("edit".equalsIgnoreCase(ORIGMasterForm.getShwAddFrm())){
		label = "Edit";
		strOnclick = "editScoreChar()";
		cancelBtn = "cancelEdit";
		shw = true;		
	}
	
	if(shw){  //===> if shw 	
%>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">&nbsp;&nbsp;&nbsp;&nbsp; </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<input type="button"  class ="button" name="OK" value="OK" onclick="<%=strOnclick%>" >              
									</td><td>
										<input type="button"  class ="button" name="Clear" value="Clear" onclick="clearData()" >			
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					                <TD class="textColS">
						          		<%if("N".equals(charMEdit.getCharType()) || "yes".equalsIgnoreCase(disableForm)){ %>
						          			<input type="checkbox" name="noDataChk" checked="checked"  value="checked" onclick="disableForm('<%=label%>')">
						          		<%}else{ %>
							          		<input type="checkbox" name="noDataChk" value="checked" onclick="disableForm('<%=label%>')">
						          		<%} %>
						          		No Data
						          	</TD>
						          	<TD class="inputCol" colspan="5"></TD>
						        </TR>         
						        <TR> 
						            <TD class="textColS" width="10%">Min :</TD>
						            <TD class="inputCol" width="10%">
						              <%if("yes".equalsIgnoreCase(disableForm)){ %>
						              	<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(String.valueOf(ORIGDisplayFormatUtil.displayCommaNumber(charMEdit.getMinRange())),displayMode,"15","minRange","textboxCurrencyReadOnly","readonly","300") %>
						              <%}else{ %>
							            <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(String.valueOf(ORIGDisplayFormatUtil.displayCommaNumber(charMEdit.getMinRange())),displayMode,"15","minRange","textboxCurrency","onKeyPress=\"keyPressFloat(this.value);\"","300") %>
						              <%} %>
						            </TD>
						            <TD class="textColS" width="10%">Max :</td>
						            <TD class="inputCol" width="10%">						            
						              <%if("yes".equalsIgnoreCase(disableForm)){ %>
						             	 <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber((charMEdit.getMaxRange())),displayMode,"15","maxRange","textboxCurrencyReadOnly","readonly","100") %>
						              <%}else{ %>	
							             <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber((charMEdit.getMaxRange())),displayMode,"15","maxRange","textboxCurrency","onKeyPress=\"keyPressFloat(this.value);\"","100") %>
						              <%} %> 
						            </td><TD class="inputCol" width="10%">
						              <input type="button"  class ="button_text" name="Infinite" value="Infinite" onclick="getInfinite()" ></TD>
						            <TD class="textColS" width="4%">Score :</TD>
						            <TD class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(String.valueOf(charMEdit.getScore()),displayMode,"10","score","textbox","onKeyPress=\"keyPressFloat(this.value);\"","300") %></TD>
						            <TD class="inputCol"></TD>
						        </TR>
						        <TR> 
						            <TD class="textColS">Specific Value :</TD>
						            <TD class="inputCol" colspan="5"> 
						              	<%
						              		String SpecificChar = ""; 
						              		String disableMode = ""; 
						              	%>
						               <%if("yes".equalsIgnoreCase(disableForm)){ 
						               		disableMode = "disabled";
										}else{ 
									   			
									   	} %>
								            <%
																Vector charTypeSpecVect = (Vector)request.getSession().getAttribute("SPECIFIC_LIST_BOX");
						                  							System.out.println("charTypeSpecVect = " + charTypeSpecVect);
						                  							if(charTypeSpecVect==null){
						                  								charTypeSpecVect = new Vector();
						                  							}
						                  							ScoreCharacterM charM_Spec;
						                  							System.out.println("charTypeSpecVect size = " + charTypeSpecVect.size());
						                  							if(charTypeSpecVect.size() > 0){ // if1
																		for(int i =0;i<charTypeSpecVect.size();i++){ // for1
																			charM_Spec = (ScoreCharacterM)charTypeSpecVect.get(i);
								
											%>
											<% 
											if("edit".equalsIgnoreCase(label)){
												if(specEdit!=null && charM_Spec.getSpecific()!=null && !"".equals(charM_Spec.getSpecific())){
													if(specEdit.equals(charM_Spec.getSpecific())){ 
													SpecificChar = charM_Spec.getSpecific();%>
								                <%}else{ %>
								                <%}
								               }
								             }else{ %>
								                  <%							 
																		}//end last else
																	} // end for1
																}// end if1
											%>
								            <%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(SPECIFIC_LIST_BOX_MASTER,SpecificChar,"specific",displayMode,disableMode) %>
								          </TD>
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
<%} // end if shw%>

<%} %>

<!-- ********** END Edit Screen ********** -->


<%
	//clear ORIGMasterFormHandler value
	ORIGMasterForm.setShwAddFrm("");
 %>

<script language="JavaScript">	
function validate(){
	var customerType 	= appFormName.customerType.value;
	
	if( customerType==""){
		alert("Please Select Customer Type");
		return false;
	}else{
		return true;
	}
}
function searchAppScore() {
		if(validate()){
//			appFormName.action.value = "ShowAppScore";
			appFormName.action.value = "SearchAppScorebyCusType";
			appFormName.shwAppScore.value = "search";
			appFormName.handleForm.value = "Y";
			appFormName.submit();   
		}
}
function editAppScore() {
		if(validate()){
			appFormName.action.value = "ShowAppScore";
			appFormName.shwAppScore.value = "edit";
			appFormName.handleForm.value = "Y";
			appFormName.submit();   
		}
}
function getCharType() {
		appFormName.action.value = "GetCharTypeVect";
		appFormName.shwAppScore.value = "edit";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function selectCharM() {
		appFormName.action.value = "SelectCharM";
		appFormName.shwAppScore.value = "edit";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}
function submitShwAddForm() {
		appFormName.action.value = "ShowScoreAddForm";
		appFormName.shwAppScore.value = "edit";
		appFormName.shwAddFrm.value = "add";
		appFormName.handleForm.value = "Y";
		appFormName.submit();   
}	
function gotoEditForm(charEdit){
		appFormName.action.value="GetCharEdit";
		appFormName.shwAddFrm.value = "edit";
		appFormName.shwAppScore.value = "edit";
		appFormName.charEdit.value = charEdit;
		appFormName.handleForm.value = "Y";
		appFormName.submit();	
}
function clearData(){
	appFormName.minRange.value = "";
	appFormName.maxRange.value = "";
	appFormName.score.value = "";
}
function getInfinite(){
	appFormName.maxRange.value = "-1";
}
function addScoreChar(){
	var score 	= appFormName.score.value;

	if(score!=""){
		appFormName.action.value = "AddAppScore";
		appFormName.shwAppScore.value = "edit";
		appFormName.handleForm.value = "Y";
		appFormName.submit();  
	}else{
		alert("Please Input Score");
	}
}
function editScoreChar(){
	var score 	= appFormName.score.value;

	if(score!=""){
		appFormName.action.value = "EditAppScore";
		appFormName.shwAppScore.value = "edit";
		appFormName.handleForm.value = "Y";
		appFormName.submit();  
	}else{
		alert("Please Input Score");
	}
}
function cancelScoreType(){
	appFormName.action.value = "CancelScoreType";
//	appFormName.shwAppScore.value = "edit";
	appFormName.handleForm.value = "Y";
	appFormName.submit();  
}
function backToSearchAppScore(){
	appFormName.action.value = "BackToAppScore";
	appFormName.handleForm.value = "Y";
	appFormName.submit();  
}
function giveValue(){
	var accept = appFormName.accept.value;
	var reject = appFormName.reject.value;
	
	appFormName.rejectRef.value = reject;
	appFormName.acceptRef.value = accept;
}
function giveValueUsed(){
	var accept_used = appFormName.accept_used.value;
	var reject_used = appFormName.reject_used.value;
	
	appFormName.rejectRef_used.value = reject_used;
	appFormName.acceptRef_used.value = accept_used;
}
function validateDelete(){
	var objSeq = appFormName.charMChk;
	if (isObject(objSeq) && !isUndefined(objSeq.length)) {
		if (objSeq.length > 0) {
			var isValid = false;
			for (var i = 0; i < objSeq.length ; i++) {
				if (objSeq[i].checked) {
					isValid = true;
				}
			}
			if (!isValid){
				alert("Please select at least one item.");
				return false;
			}
		}		
	} else {
		if (!objSeq.checked) {
			alert("Please select at least one item.");
			return false;
		}
	}
	
	return true;
}
function deleteAppScore(){
	if(validateDelete()){
		appFormName.action.value = "DeleteAppScore";
		appFormName.shwAppScore.value = "edit";
		appFormName.handleForm.value = "Y";
		appFormName.submit();
	}
}
function disableForm(AddOrEdit){
	appFormName.action.value = "DisableAppScore";
	appFormName.shwAppScore.value = "edit";
	appFormName.shwAddFrm.value = AddOrEdit;
	appFormName.handleForm.value = "Y";
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


	