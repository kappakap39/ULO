<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.service.common.api.ServiceCache"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.SelectTransferTool"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html; chatset=UTF-8"%>
<script type="text/javascript" src="orig/js/export/excel/sla_report.js"></script>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<%
	String COMPLETE_REPORT_RADIO_A = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_A");
	String COMPLETE_REPORT_RADIO_Y = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_Y");
	String COMPLETE_REPORT_RADIO_N = SystemConstant.getConstant("COMPLETE_REPORT_RADIO_N");
 %>
<section class="work_area padding-top" id="SLA_REPORT">
<form>
<div  id="slaReport" class="panel panel-default">	
<div class="panel-heading">
	<%=LabelUtil.getText(request, "SLA_REPORT")%>
	<%=HtmlUtil.hidden("REPORT_TYPE", ServiceCache.getConstant("SLA_REPORT")) %>
</div>
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getMandatoryLabel(request, "SLA_REPORT_DATE_FROM", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9">
				<%=HtmlUtil.calendar("DATE_FROM_CALENDAR","","DATE_FROM_CALENDAR",
				FormatUtil.toDate(SearchForm.getString("DATE_FROM_CALENDAR"), "EN"),"",HtmlUtil.EDIT,"",FormatUtil.TH,"col-xs-5 col-xs-padding",request)%>
				<div class="col-xs-2 control-label"><%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_DATE_TO", "") %></div>
				
				<%=HtmlUtil.calendar("DATE_TO_CALENDAR","","DATE_TO_CALENDAR",
				FormatUtil.toDate(SearchForm.getString("DATE_TO_CALENDAR"), "EN"),"",HtmlUtil.EDIT,"",FormatUtil.TH,"col-xs-5 col-xs-padding",request)%>
			</div>
		</div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getMandatoryLabel(request, "SLA_REPORT_COMPLETE", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9"  style="padding-left: 0px; padding-right: 0px;">
				<%=HtmlUtil.radioInline("COMPLETE_REPORT_RADIO", "", COMPLETE_REPORT_RADIO_A, COMPLETE_REPORT_RADIO_A, "", "", "COMPLETE_REPORT_A", "", request) %>
				<%=HtmlUtil.radioInline("COMPLETE_REPORT_RADIO", "", "", COMPLETE_REPORT_RADIO_Y, "", "", "COMPLETE_REPORT_Y", "", request) %>
				<%=HtmlUtil.radioInline("COMPLETE_REPORT_RADIO", "", "", COMPLETE_REPORT_RADIO_N, "", "", "COMPLETE_REPORT_N", "", request) %>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_PROJECT_NO", "col-sm-2 col-md-3 control-label") %>
			<%=HtmlUtil.textBox("PROJECT_NO_BOX", "", "", "", "20", "", "", "col-sm-10 col-md-9", request) %>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_PRODUCT", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9">
				<div class="well">
					<div class="row form-horizontal">
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC", HtmlUtil.EDIT, "", "PRODUCT_CC", "col-sm-4", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC", HtmlUtil.EDIT, "", "PRODUCT_KEC", "col-sm-4", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KPL", HtmlUtil.EDIT, "", "PRODUCT_KPL", "col-sm-4", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_NEW", HtmlUtil.EDIT, "", "NEW", "col-sm-2", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_ADD", HtmlUtil.EDIT, "", "ADD", "col-sm-2", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_UP", HtmlUtil.EDIT, "", "UPG", "col-sm-2", request) %>
					</div>
				</div>
				<div class="well">
					<div class="row form-horizontal">
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_INC", HtmlUtil.EDIT, "", "P_CC_INC", "col-sm-5", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC_INC", HtmlUtil.EDIT, "", "P_KEC_INC", "col-sm-3", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KPL_INC", HtmlUtil.EDIT, "", "P_KPL_INC", "col-sm-4", request) %>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_CHANNEL", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9 form-group" >
				<div class="well">
					<div class="row form-horizontal">
						<%=HtmlUtil.checkBoxInline("BRANCH_TYPE_BOX", "", "", SystemConstant.getConstant("BRANCH_REGION_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_BRANCH"), "col-sm-12", request) %>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_REGION", "") %>&nbsp; </div>
							<div class="col-sm-8 form-group">
									<div class="row form-horizontal">
										<%=SelectTransferTool.DisplaySelectTransferData(SelectTransferTool.BRANCH_REGION_TRANSFER, SelectTransferTool.REGION,"") %>
									</div>
							</div>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_ZONE", "") %>&nbsp; </div>
						<div class="col-sm-8 form-group">
								<div class="row form-horizontal">
									<%=SelectTransferTool.DisplaySelectTransferData(SelectTransferTool.BRANCH_ZONE_TRANSFER, SelectTransferTool.ZONE,"col-sm-12") %>
								</div>
						</div>
						<%=HtmlUtil.checkBoxInline("DSA_TYPE_BOX", "", "", SystemConstant.getConstant("DSA_EXPAND_ZONE_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_DSA"), "col-sm-12", request) %>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_EXPAND_ZONE", "") %>&nbsp; </div>
						<div class="col-sm-8 form-group">
								<div class="row form-horizontal">
									<%=SelectTransferTool.DisplaySelectTransferData("DSA_EXPAND_ZONE_TRANSFER",SelectTransferTool.TEAMZONE,"col-sm-12") %>
								</div>
						</div>
						<div class="clearfix"></div>						
						<%=HtmlUtil.checkBoxInline("NBD_TYPE_BOX", "", "", SystemConstant.getConstant("NBD_ZONE_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_NBD"), "col-sm-12", request) %>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "SLA_REPORT_ZONE", "") %>&nbsp; </div>
						<div class="col-sm-8 form-group">
								<div class="row form-horizontal">
									<%=SelectTransferTool.DisplaySelectTransferData("NBD_ZONE_TRANSFER",SelectTransferTool.ZONE,"col-sm-12") %>
								</div>
						</div>
						<div class="clearfix"></div>
						<%=HtmlUtil.checkBoxInline("CHANNEL_TYPE_BOX", "", "", SystemConstant.getConstant("SILKSPAN_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_S"), "col-sm-4", request) %>
						<%=HtmlUtil.checkBoxInline("CHANNEL_TYPE_BOX", "", "", SystemConstant.getConstant("DIRECT_MAIL_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_D"), "col-sm-4", request) %>
						<%=HtmlUtil.checkBoxInline("CHANNEL_TYPE_BOX", "", "", SystemConstant.getConstant("OTHER_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_O"), "col-sm-4", request) %>
						
						
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="row">
			<div class="col-sm-12 text-center">				
				<%=HtmlUtil.hidden("GENERATE_BUTTON_FLAG", "") %>
				<%=HtmlUtil.button("SLA_REPORT_GENERATE_BUTTON", "SLA_REPORT_GENERATE_BUTTON", HtmlUtil.EDIT, "btn2 btn2-green", "", request) %>
				<%=HtmlUtil.button("SLA_REPORT_RESET_BUTTON", "SLA_REPORT_RESET_BUTTON", HtmlUtil.EDIT, "btn2", "", request) %>
			</div>
		</div>
		
		<div class="col-sm-12 form-group"></div>
		<div class="clearfix"></div>
	</div>
	</div>
</div>
</form>
</section>

<div class="row padding-top">
	<div class="col-sm-12">
		<table class="table table-striped">
			<thead style="background-color: #00A950; color: white;">
				<tr>
					<th>No.</th>
					<th>Criteria</th>
					<th>Generate By</th>
					<th>Generate Date</th>
				</tr>
			</thead>
			<tbody id="generateReport_list" class="text-center" style="white-space:pre;">
			</tbody>
		</table>
	</div>
</div>
