<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="com.eaf.j2ee.pattern.util.DisplayFormatUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%><%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler"/>
<script type="text/javascript" src="orig/ulo/search/js/searchCardMaintenance.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String,Object>> results = SearchForm.getResults();
	BigDecimal TOTAL = FormatUtil.toBigDecimal(String.valueOf(SearchForm.getCount()));
	String mode = HtmlUtil.EDIT;	
	String LIST_BOX_CONFIG_ID_ROLE = SystemConstant.getConstant("CONFIG_ID_FILTER_ROLE");
	String CONFIG_ID_FILTER_USER = SystemConstant.getConstant("CONFIG_ID_FILTER_USER");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String success = SystemConstant.getConstant("CARDLINK_FLAG_SUCCESS");
	String fail = SystemConstant.getConstant("CARDLINK_FLAG_FAIL");
	String wait = SystemConstant.getConstant("CARDLINK_BATCH_WAITING");
	String failStatus = SystemConstant.getConstant("CARDLINK_FLAG_FAIL_STATUS");
	String tenMillionStatus = SystemConstant.getConstant("CARDLINK_FLAG_TEN_MILLION_STATUS");
	String waitStatus = SystemConstant.getConstant("CARDLINK_FLAG_BATCH_WAITING_STATUS");
	String[] CJD_SOURCE = SystemConstant.getArrayConstant("CJD_SOURCE");
%>

<section class="work_area padding-top" id ="SEARCH_CARD_MAINTENANCE">
<div class="row">
<div class="col-xs-12">
	<div class="panel panel-default">
		<div class="panel-body">
			<div class="row form-horizontal">
				<%=HtmlUtil.hidden("APPLICATION_GROUP_ID", "") %>
				<%=HtmlUtil.hidden("CARDLINK_REF_NO", "") %>
				<%=HtmlUtil.hidden("REG_TYPE", "") %>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CM_CARDLINK_REF_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("CM_CARDLINK_REF_NO","",SearchForm.getString("CM_CARDLINK_REF_NO"),"","20",mode,"","col-sm-8 col-md-7",request)%> 				
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CM_PROCESSIONG_DATE", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.calendar("CM_PROCESSIONG_DATE", "", "CM_PROCESSIONG_DATE",
						FormatUtil.toDate(SearchForm.getString("CM_PROCESSIONG_DATE"), HtmlUtil.TH), "", mode, "", HtmlUtil.TH, 
						"col-sm-8 col-md-7", request) %>
					</div>
				</div>		
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CM_CARDLINK_FLAG", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.dropdown("CM_CARDLINK_FLAG", "", "", SearchForm.getString("CM_CARDLINK_FLAG"), "", SystemConstant.getConstant("FIELD_ID_SEND_DATA_TO_CARDLINK"), "", mode, "", "col-sm-8 col-md-7", request)%>					
					</div>
				</div>
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "CM_APPLICATION_GROUP_NO", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("CM_APPLICATION_GROUP_NO","",SearchForm.getString("CM_APPLICATION_GROUP_NO"),"","120",mode,"","col-sm-8 col-md-7",request)%>					
					</div>
				</div>	
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "FIRST_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("FIRST_NAME","",SearchForm.getString("FIRST_NAME"),"","50",mode,"","col-sm-8 col-md-7",request)%>					
					</div>
				</div>	
				<div class="col-sm-4">
					<div class="form-group">
						<%=HtmlUtil.getFieldLabel(request, "LAST_NAME", "col-sm-4 col-md-5 control-label")%>
						<%=HtmlUtil.textBox("LAST_NAME", "",SearchForm.getString("LAST_NAME"), "", "50", mode, "", "col-sm-8 col-md-7", request)%>					
					</div>
				</div>
				<div class="col-sm-12">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 text-center">
							<%=HtmlUtil.button("SEARCH_BTN","SEARCH_BTN",HtmlUtil.EDIT,"btn2 btn2-green","",request) %>
							<%=HtmlUtil.button("RESET_BTN","RESET_BTN",HtmlUtil.EDIT,"btn2","",request) %>
							</div>
						</div>
					</div>
				</div>
	
			</div>
		</div>
	</div>
</div>
</div>
</section>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="titlesearch">
			<%=HtmlUtil.getFieldLabel(request, "TOTAL_RECORD")%><%=FormatUtil.display(TOTAL,FormatUtil.Format.NUMBER_FORMAT)%>
		</div>
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-striped">
				<tr>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "CM_APPLICATION_GROUP_NO") %></th>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "CM_CARDLINK_REF_NO") %></th>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "CM_CARDLINK_FLAG") %></th>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "CM_PROCESSIONG_DATE") %></th>
					<th class="text_center" style="width: 14%"><%=LabelUtil.getText(request, "CM_APPLICANT_INFO") %></th>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "CM_APPLY_TYPE") %></th>
					<th class="text_center" style="width: 16%"><%=LabelUtil.getText(request, "CM_CARD_INFO") %></th>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "CM_CREDIT") %></th>
					<th class="text_center" style="width: 12%"><%=LabelUtil.getText(request, "APP_TYPE") %></th>				
				</tr>
			<%if(!Util.empty(results)){
				for(HashMap<String,Object> Row :results){
					HashMap<String, Object> personalCardInfos  = (HashMap<String, Object> )Row.get("PERSONAL_CARD");
					String QR = FormatUtil.display(Row, "QR");
					String cardlinkFlag = SearchForm.getString("CM_CARDLINK_FLAG");
					String cardlinkFlagLabel = "";
					String APPLICATION_GROUP_ID  =FormatUtil.display(Row, "APPLICATION_GROUP_ID");
					String CARDLINK_REF_NO  =FormatUtil.display(Row,"CARDLINK_REF_NO");
					String REG_TYPE  = FormatUtil.display(Row,"REG_TYPE");
					String APP_TYPE = REG_TYPE;
					for(String cjdSource : CJD_SOURCE)
					{
						if(QR.startsWith(cjdSource))
						{
							APP_TYPE = "CJD";
							break;
						}
					}
					String loadApplicationActionJS = "onclick=loadApplicationActionJS('"+APPLICATION_GROUP_ID+"',"+"'"+CARDLINK_REF_NO+"','"+REG_TYPE+"')";
					String redLabel = "";
					
					if(failStatus.equals(cardlinkFlag)){
						cardlinkFlagLabel = LabelUtil.getText(request, "CM_FAIL");
						redLabel = "color:#fb2628;";
					}else if(waitStatus.equals(cardlinkFlag)){
						cardlinkFlagLabel = LabelUtil.getText(request, "CM_BATCH_WAITING");
					}else if(tenMillionStatus.equals(cardlinkFlag)){
						cardlinkFlagLabel = LabelUtil.getText(request, "CM_ADJUST_CL");
					}

					if(!Util.empty(personalCardInfos)){
						ArrayList<String> personalKeys = new ArrayList<String>(personalCardInfos.keySet());

					 %>
						<tr <%=loadApplicationActionJS %>>
							<td  class="text_center card-maintenance-text" style="width: 12%"><%=FormatUtil.display(Row,"QR")%></td> 
							<td  class="text_center card-maintenance-text" style="width: 12%"><%=FormatUtil.display(Row,"CARDLINK_REF_NO")%></td> 
							<td  style="<%=redLabel %>" class="text_center card-maintenance-text" style="width: 12%"><%=cardlinkFlagLabel%></td> 
							<td  class="text_center card-maintenance-text" style="width: 12%"><%=FormatUtil.display(Row,"PROCESSIONG_DATE")%></td> 
							<td  colspan="4"  style="width: 52%">	
								<table style="width: 100%" >	
								<%
									if(!Util.empty(personalKeys)){
									Collections.sort(personalKeys);
									int classRow=0;
									 for(String personalKey : personalKeys){
									 	classRow++;
										HashMap<String, Object>  personalCardInfoRow = (HashMap<String, Object>)personalCardInfos.get(personalKey);
										ArrayList<HashMap<String, Object>>  cardInfos  = (ArrayList<HashMap<String, Object>>)personalCardInfoRow.get("CARD_INFO_LIST");
										String TH_FIRST_NAME_LAST_NAME=FormatUtil.display(personalCardInfoRow,"TH_FIRST_NAME_LAST_NAME");
										String PERSONAL_TYPE= FormatUtil.display(personalCardInfoRow,"PERSONAL_TYPE");
										String CARDLINK_CUST_NO= FormatUtil.display(personalCardInfoRow,"CARDLINK_CUST_NO");
										String  PERSONAL_TYPE_DESC="";
										if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
											PERSONAL_TYPE_DESC =LabelUtil.getText(request, "CM_PERSONAL_TYPE_MAIN");
										}else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(PERSONAL_TYPE)){
											PERSONAL_TYPE_DESC = LabelUtil.getText(request, "CM_PERSONAL_TYPE_SUP");
										}	
										%>
										<tr class=" <%if(classRow%2!=0 && personalKeys.size()>1){%>card-maintenance-sub-row<%}%>">
											<td  class="text_center" style="width: 27%;padding-right:5px;"><p><%=PERSONAL_TYPE_DESC%>:<%=TH_FIRST_NAME_LAST_NAME%></p><p>Cust No :<%=CARDLINK_CUST_NO%></p></td>
											<td colspan="3"  style="width: 73%">
												<table style="width:100%">
													<%if(!Util.empty(cardInfos)){
														int subRowClass=0;
														for(HashMap<String,Object> hCardResult : cardInfos){
															subRowClass++;
															String LOAN_AMT_STRING = FormatUtil.display(hCardResult,"LOAN_AMT");
															BigDecimal loanAmt=null;
															if(!Util.empty(LOAN_AMT_STRING)){
																loanAmt= new BigDecimal(LOAN_AMT_STRING);
															}
															%>
														<tr class="<%if(subRowClass%2==0){%> card-maintenance-sub-row-top<%}%>">
															<td  class="text_center" style="width: 30%"><%=FormatUtil.display(hCardResult,"APPLY_TYPE")%></td>
															<td  class="text_center" style="width: 45%"><%=FormatUtil.display(hCardResult,"CARD_NO_MARK")%>&nbsp;<%=FormatUtil.display(hCardResult,"CARD_DESC")%></td>
															<td  class="text_center" style="width: 25%"><%=FormatUtil.displayCurrency(loanAmt,true)%></td>
														</tr>													
													<% }
													 } %>													
												</table>
											
											</td>
										</tr>
										<%}
									}
								%>	
								</table>	
							</td>
							<td  class="text_center card-maintenance-text" style="width: 12%"><%=APP_TYPE%></td>
					</tr>
						
				<%}
				}
			}else{ %>
				<tr>
					<td colspan="8" style="text-align: center;"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
				</tr>
			<%} %>
			<tfoot>
				<tr>
					<td colspan="8"></td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<section>
	<div class="row">
		<div class="col-md-12">
			<jsp:include page="/orig/ulo/util/valuelist.jsp"/>
		</div>
	</div>
</section>


<script type="text/javascript">
	$(function(){
	var rx = /INPUT|SELECT/i;
	$(document).bind("keydown keypress keyup",function(e){
		if(e.which == 13){
			if(rx.test(e.target.tagName) || e.target.disabled || e.target.readOnly){
				e.preventDefault();
			}
		}
	}); 
	});
</script>