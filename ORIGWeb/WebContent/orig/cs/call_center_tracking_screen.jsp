<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.RenderSubTableUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.shared.model.SearchDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="VALUE_LIST" scope="session" class="com.eaf.orig.shared.model.ValueListM" />

<script type="text/javascript" src="orig/js/cs/call_center_tracking.js"></script>

<%  org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/cs/call_center_tracking_screen.jsp");
    
    String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
    
    SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
	}
	
	if("".equals(searchDataM.getProductCode()) || null==searchDataM.getProductCode()){
		searchDataM.setProductCode(OrigConstant.PRODUCT_KEC);
	}
    
	ORIGCacheUtil utility = new ORIGCacheUtil();
	Vector vProduct = utility.loadCacheByActive("MainProduct");
%> 
<style>
 Td.callCenterData{      
   font-family:  "MS Sans Serif","Arial", "Helvetica";
   font-size: 10px;
   padding:0;
   text-align: left;
   vertical-align: top;   
   background-color: #FFFFFF;
 } 
 Table.TableFrame Tr.CSResultEven Td{
	color:#000000;
	font-family: "Verdana";
	font-size: 13px;
	font-weight: normal;
	text-decoration: none;
	padding: 0px 0px 0px 0px;	
	background: #FFFFFF;
	border-bottom: 1px solid #c8ffb2;	
}
</style>
<div class="nav-inbox">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<fieldset class="field-set">
				<legend>Search Criteria</legend>
					<table class="FormFrame">
						<tr>
                        	<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO")%> :</td>
                            <td class="inputCol"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getApplicationNo(),displayMode,"","appNo","textbox","","20") %></td>
                            <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REFERENCE_NO")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getRefNo(),displayMode,"","refNo","textbox200","","30") %></td>
                            <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PRODUCT")%><span style="color: red">*</span> :</td>
                            <td class="inputCol"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vProduct,searchDataM.getProductCode(),"product",displayMode,"") %></td>
                        </tr>
                        <tr height="25">
                            <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NAME_THAI")%> :</td>
                            <td class="inputCol"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerName(),displayMode,"","firstName","textbox","onblur=\"countChar2(this)\"","120") %></td>
                            <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SURNAME_THAI")%> :</td>
                            <td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCustomerLName(),displayMode,"","lastName","textbox","onblur=\"countChar2(this)\"","50") %></td>
                            <td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO")%> :</td>
                            <td class="inputCol"><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getCitizenID(),displayMode,"","idno","textbox","","20") %></td>
                        </tr>
                        <tr height="25">
                        	<td colspan="3">&nbsp;</td>
                            <td valign="top"><input type="button" class="button" id="button-search" value="Search" onClick="javascript:search()" /></td>
                            <td colspan="2">&nbsp;</td>
                        </tr>
                	</table>
				</fieldset>
			<!-- Result -->
			<div class="PanelThird">
				<div class="scroll-div">
					<%Vector valueList = VALUE_LIST.getResult();
					if((valueList!=null)&&(valueList.size()>1)){%>
						<table class="TableFrame" width="100%">
							<tr height="30">
				            	<td class="inputCol" nowrap="nowrap" colspan="12">Total Records Found : <%=VALUE_LIST.getCount() %></td>
				            </tr>
			            </table>
					<%}else{%>
						<table class="TableFrame" width="100%">
							<tr height="30">
				                <td class="inputCol" nowrap="nowrap" colspan="12">Total Records Found : 0</td>
				            </tr>
			            </table>
					<%}%>
					
                    <%if((valueList!=null)&&(valueList.size()>1)){
 						for(int i=1 ; i<valueList.size() ; i++){ 
 							Vector elementList = (Vector)valueList.get(i);
						//	String styleTr = ((i-1)%2==0)?"ResultEven":"ResultOdd";
					%>
					    <table class="TableFrame" width="100%">	
							<tr class="Header" >					    
			            		<td  style="font-size: 12px;text-align: left;padding: 0">
				            		 ชื่อ : <%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(1)) %>
				            		&nbsp;<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(2)) %>
				            		&nbsp;&nbsp;&nbsp;ID : <%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(3)) %>
			            		</td>		            		 
			            		<td style="font-size: 12px;text-align: left; padding: 0">
			            			&nbsp;&nbsp;หมายเลขอ้างอิง :<%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(12)) %>&nbsp;
			            		</td>          	 
							</tr>	
						</table>		
						<table class="TableFrame" width="100%">				    
							<tr class="CSResultEven"> 					        
			                	<td class="callCenterData" width="40" nowrap="nowrap">&nbsp;หมายเลขใบสมัคร</td>
			                	<td class="callCenterData">:&nbsp;</td>
			                	<td class="callCenterData" width="220"><%=HTMLRenderUtil.displayHTML((String) elementList.elementAt(11)) %></td>
			                	<td class="callCenterData"  width="50" nowrap="nowrap">&nbsp;วันที่พิจารณา</td>
			                	<td class="callCenterData">:&nbsp;</td>
			                	<td class="callCenterData" width="220"><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(6)) %></td>		                	
			                	<td class="callCenterData" width="50" nowrap="nowrap">เงินสดโอนไว</td>
			                	<td class="callCenterData">:&nbsp;</td>
			                    <td class="callCenterData" width="220"><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(8)) %></td>		                   
				            </tr>    			                	
				            <tr class="CSResultEven">
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;ผลิตภัณฑ์</td>
			                	<td class="callCenterData">:</td>
			                	<td class="callCenterData"><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(13)) %></td>
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;ผลการพิจารณา </td>
			                	<td class="callCenterData">:</td>
			                	<td class="callCenterData"><%=MessageResourceUtil.getTextDescription(request,HTMLRenderUtil.displayAppStatusForCS((String) elementList.elementAt(5)))%></td>
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;จำนวนเงิน </td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.displayCommaNumber(DataFormatUtility.StringToBigDecimalEmptyNull((String) elementList.elementAt(9)))) %></td>		                	
				            </tr>
				            <tr class="CSResultEven">
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;ประเภทการขาย</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(15)) %></td>		                	
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;จำนวนเงินที่อนุมัติ </td>
			                	<td class="callCenterData">:</td>
			                	<td class="callCenterData"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(HTMLRenderUtil.displayCreditLineForCS((String) elementList.elementAt(5),(String) elementList.elementAt(7))) %></td>		                	
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;เลขที่บัญชี </td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(10)) %></td>		                			                	                 
				            </tr>	
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;หน้าบัตร</td>
			                	<td class="callCenterData">:</td>		                	
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(22)) %></td>
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;เหตุผล</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" colspan="4" nowrap="nowrap"><%=RenderSubTableUtility.renderTableReasonCS((String) elementList.elementAt(20),(String) elementList.elementAt(21),(String) elementList.elementAt(23)) %></td>		
            			   	</tr>	
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;รหัสโครงการ</td>
			                	<td class="callCenterData">:</td>		                	
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(16)) %></td>
			                	<td class="callCenterData" nowrap="nowrap">&nbsp;รายการเอกสารติดตาม</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" colspan="4" nowrap="nowrap"><%=RenderSubTableUtility.renderTableDocCheckListNameCS((String) elementList.elementAt(20)) %></td>	
              			               			                	
				            </tr>	
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;วันที่เอกสารเข้าระบบ</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(4)) %></td>		                	 
			                	<td class="callCenterData"nowrap="nowrap" ></td>
			                	<td class="callCenterData" ></td>
			                	<td class="callCenterData"></td>                	 		                	 		                	
			                	<td class="callCenterData" colspan="3"></td> 		                	 
				            </tr>
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;ช่องทางการสมัคร</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(14)) %></td>		                	 
			                	<td class="callCenterData"nowrap="nowrap" ></td>
			                	<td class="callCenterData" ></td>
			                	<td class="callCenterData"></td>                	 		                	 		                	
				                <td class="callCenterData" colspan="3">&nbsp;</td> 		                	 
				            </tr>
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;ชื่อสาขา</td>
			                	<td class="callCenterData">:</td>		                	
			                	<td class="callCenterData"><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(17)) %></td>
			                	<td class="callCenterData" nowrap="nowrap"></td>
			                	<td class="callCenterData"></td>
			                	<td class="callCenterData"></td>		
			                	<td class="callCenterData" colspan="3"></td> 		                			                			                	
				            </tr>	
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;ชื่อพนักงานขาย</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(19)) %></td>		                	 
			                    <td class="callCenterData" nowrap="nowrap"></td>
			                	<td class="callCenterData" ></td>
			                	<td class="callCenterData" ></td>	
			                    <td class="callCenterData" colspan="3"></td> 
				            </tr>	
				            <tr class="CSResultEven">
			                    <td class="callCenterData" nowrap="nowrap">&nbsp;รหัสพนักงานขาย</td>
			                	<td class="callCenterData" >:</td>
			                	<td class="callCenterData" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull((String) elementList.elementAt(18)) %></td>		                	 
			                	<td class="callCenterData"nowrap="nowrap" ></td>
			                	<td class="callCenterData" ></td>
			                	<td class="callCenterData"></td>                	 		                	 		                	
			                	<td class="callCenterData" colspan="3"></td> 		                	 
				            </tr>	
				       </table> 	                			                 
					<%}}else{%>
			             <table class="TableFrame" width="100%">	  
			                <tr class="ResultNotFound" id="notFoundTable">
					        	<td colspan="12" >No record found</td>
					        </tr>	
					     </table>  
	                <%}%> 
	               </div>
				</div>
				<div class="PanelValueList" align="right">			
					<jsp:include page="../appform/pl/valueList.jsp" flush="true" />
				</div>
			</div>
		</div>
	</div>
</div>