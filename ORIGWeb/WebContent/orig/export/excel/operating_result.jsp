<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.service.common.api.ServiceCache"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.SelectTransferTool"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html; chatset=UTF-8"%>
<script type="text/javascript" src="orig/js/export/excel/operating_result.js"></script>
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<section class="work_area padding-top" id="OR_REPORT">
<form>
<div id="operatingResult" class="panel panel-default">	
<div class="panel-heading">
	<%=LabelUtil.getText(request, "OR_REPORT")%>
	<%=HtmlUtil.hidden("REPORT_TYPE", ServiceCache.getConstant("OPERATING_RESULT_REPORT")) %>
</div>
<div class="panel-body">
	<div class="row form-horizontal">
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getMandatoryLabel(request, "OR_REPORT_DATE_TYPE", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9">
				<%=HtmlUtil.radioInline("DATE_TYPE_RADIO", "", "APPLICATION_DATE", "APPLICATION_DATE", "", "", "DATE_TYPE_RADIO_A", "", request) %>
				<%=HtmlUtil.radioInline("DATE_TYPE_RADIO", "", "", "LASTDECISION_DATE", "", "", "DATE_TYPE_RADIO_L", "", request) %>
			</div>
		</div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getMandatoryLabel(request, "OR_REPORT_DATE_FROM", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9">
				<%=HtmlUtil.calendar("DATE_FROM_CALENDAR","","DATE_FROM_CALENDAR",
				FormatUtil.toDate(SearchForm.getString("DATE_FROM_CALENDAR"), "EN"),"",HtmlUtil.EDIT,"",FormatUtil.TH,"col-xs-5 col-xs-padding",request)%>
				<div class="col-xs-2 control-label"><%=HtmlUtil.getFieldLabel(request, "OR_REPORT_DATE_TO", "") %></div>
				
				<%=HtmlUtil.calendar("DATE_TO_CALENDAR","","DATE_TO_CALENDAR",
				FormatUtil.toDate(SearchForm.getString("DATE_TO_CALENDAR"), "EN"),"",HtmlUtil.EDIT,"",FormatUtil.TH,"col-xs-5 col-xs-padding",request)%>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_PROJECT_NO", "col-sm-2 col-md-3 control-label") %>
			<%=HtmlUtil.textBox("PROJECT_NO_BOX", "", "", "", "20", "", "", "col-sm-10 col-md-9", request) %>
		</div>
		<div class="clearfix"></div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_PRODUCT", "col-sm-2 col-md-3 control-label") %>
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
				<div class="well">
					<div class="row form-horizontal">
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_KEC", HtmlUtil.EDIT, "", "PRODUCT_BUNDING", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_NEW_KEC", HtmlUtil.EDIT, "", "PRODUCT_CC_NEW_KEC", "col-sm-5", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_HL", HtmlUtil.EDIT, "", "PRODUCT_CC_HL", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_ADD_KEC", HtmlUtil.EDIT, "", "PRODUCT_CC_ADD_KEC", "col-sm-5", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_SME", HtmlUtil.EDIT, "", "PRODUCT_CC_SME", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_UPG_KEC", HtmlUtil.EDIT, "", "PRODUCT_CC_UPG_KEC", "col-sm-5", request) %>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_KL", HtmlUtil.EDIT, "", "PRODUCT_CC_KL", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC_KPL", HtmlUtil.EDIT, "", "PRODUCT_KEC_KPL", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC_HL", HtmlUtil.EDIT, "", "PRODUCT_KEC_HL", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC_SME", HtmlUtil.EDIT, "", "PRODUCT_KEC_SME", "col-sm-5", request) %>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-1"></div>
						<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC_KL", HtmlUtil.EDIT, "", "PRODUCT_KEC_KL", "col-sm-5", request) %>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6 form-group">
			<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_CHANNEL", "col-sm-2 col-md-3 control-label") %>
			<div class="col-sm-10 col-md-9 form-group" >
				<div class="well">
					<div class="row form-horizontal">
						<%=HtmlUtil.checkBoxInline("BRANCH_TYPE_BOX", "", "", SystemConstant.getConstant("BRANCH_REGION_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_BRANCH"), "col-sm-12", request) %>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "OR_REPORT_REGION", "") %>&nbsp; </div>
							<div class="col-sm-8 form-group">
									<div class="row form-horizontal">
										<%=SelectTransferTool.DisplaySelectTransferData(SelectTransferTool.BRANCH_REGION_TRANSFER, SelectTransferTool.REGION,"") %>
									</div>
							</div>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "OR_REPORT_ZONE", "") %>&nbsp; </div>
						<div class="col-sm-8 form-group">
								<div class="row form-horizontal">
									<%=SelectTransferTool.DisplaySelectTransferData(SelectTransferTool.BRANCH_ZONE_TRANSFER, SelectTransferTool.ZONE, "col-sm-12") %>
								</div>
						</div>
						<%=HtmlUtil.checkBoxInline("DSA_TYPE_BOX", "", "", SystemConstant.getConstant("DSA_EXPAND_ZONE_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_DSA"), "col-sm-12", request) %>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "OR_REPORT_EXPAND_ZONE", "") %>&nbsp; </div>
						<div class="col-sm-8 form-group">
								<div class="row form-horizontal">
									<%=SelectTransferTool.DisplaySelectTransferData("DSA_EXPAND_ZONE_TRANSFER",SelectTransferTool.TEAMZONE,"col-sm-12") %>
								</div>
						</div>
						<div class="clearfix"></div>						
						<%=HtmlUtil.checkBoxInline("NBD_TYPE_BOX", "", "", SystemConstant.getConstant("NBD_ZONE_CHANNEL"), HtmlUtil.EDIT, "", HtmlUtil.getText(request, "CHANNEL_NBD"), "col-sm-12", request) %>
						<div class="clearfix"></div>
						<div class="col-sm-4 " style="text-align: right;"><%=HtmlUtil.getFieldLabel(request, "OR_REPORT_ZONE", "") %>&nbsp; </div>
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
				<%=HtmlUtil.button("OR_REPORT_GENERATE_BUTTON", "OR_REPORT_GENERATE_BUTTON", HtmlUtil.EDIT, "btn2 btn2-green", "", request) %>
				<%=HtmlUtil.button("OR_REPORT_RESET_BUTTON", "OR_REPORT_RESET_BUTTON", HtmlUtil.EDIT, "btn2", "", request) %>
			</div>
		</div>
		
		<div class="col-sm-12 form-group"></div>
		<div class="clearfix"></div>
	</div>
	</div>
</div>
</form>
</section>

<!-- <section class="work_area padding-top"> -->
<!-- 	<form> -->
<!-- 		<div class="row"> -->
<!-- 			<div id="operatingResult" class="col-sm-12"> -->
<!-- 				<h3>Operating Result Report for On Request</h3> -->
<%-- 				<%=HtmlUtil.hidden("REPORT_TYPE", ServiceCache.getConstant("OPERATING_RESULT_REPORT")) %> --%>
<!-- 				<div class="panel panel-default"> -->
<!-- 					<div class="panel-body" style="padding:15px;"> -->
<!-- 						<div class="row form-horizontal"> -->
<!-- 							<div class="col-sm-6 form-group"> -->
<%-- 								<%=HtmlUtil.getMandatoryLabel(request, "OR_REPORT_DATE_TYPE", "col-sm-3 control-label") %> --%>
<%-- 								<%=HtmlUtil.radio("DATE_TYPE_RADIO", "", "APPLICATION_DATE", "APPLICATION_DATE", "", "", "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e43\u0e1a\u0e2a\u0e21\u0e31\u0e04\u0e23\u0e40\u0e02\u0e49\u0e32\u0e23\u0e30\u0e1a\u0e1a", "col-sm-4 control-label", request) %> --%>
<%-- 								<%=HtmlUtil.radio("DATE_TYPE_RADIO", "", "", "LASTDECISION_DATE", "", "", "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e1e\u0e34\u0e08\u0e32\u0e23\u0e13\u0e32", "col-sm-3 control-label", request) %> --%>
<!-- 							</div> -->
<!-- 							<div class="col-sm-3 form-group"> -->
<%-- 								<%=HtmlUtil.getMandatoryLabel(request, "OR_REPORT_DATE_FROM", "col-sm-5 control-label") %> --%>
<%-- 								<%=HtmlUtil.calendar("DATE_FROM_CALENDAR", "", "", FormatUtil.toDate(SearchForm.getString("DATE_FROM_CALENDAR"), "EN"), "", HtmlUtil.EDIT, "", FormatUtil.EN, "col-sm-7", request) %> --%>
<!-- 							</div> -->
<!-- 							<div class="col-sm-3 form-group"> -->
<%-- 								<%=HtmlUtil.getMandatoryLabel(request, "OR_REPORT_DATE_TO", "col-sm-3 control-label") %> --%>
<%-- 								<%=HtmlUtil.calendar("DATE_TO_CALENDAR", "", "", FormatUtil.toDate(SearchForm.getString("DATE_TO_CALENDAR"), "EN"), "", HtmlUtil.EDIT, "", FormatUtil.EN, "col-sm-7", request) %> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="row form-horizontal"> -->
<!-- 							<div class="col-sm-6 form-group"> -->
<%-- 								<%=HtmlUtil.getMandatoryLabel(request, "OR_REPORT_PROJECT_NO", "col-sm-3 control-label") %> --%>
<%-- 								<%=HtmlUtil.textBox("PROJECT_NO_BOX", "", "", "", "20", "", "", "col-sm-7", request) %> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="row form-horizontal"> -->
<!-- 							<div class="col-sm-6 form-group"> -->
<!-- 								<div class="col-sm-2"> -->
<%-- 									<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_PRODUCT", "col-sm-12 control-label") %> --%>
<!-- 								</div> -->
<!-- 								<div class="col-sm-10"> -->
<!-- 									<div class="well"> -->
<!-- 										<div class="row form-horizontal"> -->
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC", HtmlUtil.EDIT, "", "Credit Card", "col-sm-4", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC", HtmlUtil.EDIT, "", "KEC", "col-sm-4", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KPL", HtmlUtil.EDIT, "", "KPL", "col-sm-4", request) %> --%>
<!-- 										</div> -->
<!-- 										<div class="row form-horizontal"> -->
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_NEW", HtmlUtil.EDIT, "", "New", "col-sm-2", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_ADD", HtmlUtil.EDIT, "", "Add", "col-sm-2", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_UP", HtmlUtil.EDIT, "", "Upgrade", "col-sm-2", request) %> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="well"> -->
<!-- 										<div class="row form-horizontal"> -->
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_INC", HtmlUtil.EDIT, "", "Credit Card(Increase)", "col-sm-6", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "KEC_INC", HtmlUtil.EDIT, "", "KEC(Increase)", "col-sm-6", request) %> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="well"> -->
<!-- 										<div class="row form-horizontal"> -->
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_KEC", HtmlUtil.EDIT, "", "Bunding Product(CC & KEC)", "col-sm-12", request) %> --%>
<!-- 										</div> -->
<!-- 										<div class="row form-horizontal"> -->
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_NEW_KEC", HtmlUtil.EDIT, "", "New", "col-sm-2", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_ADD_KEC", HtmlUtil.EDIT, "", "Add", "col-sm-2", request) %> --%>
<%-- 											<%=HtmlUtil.checkBoxInline("PRODUCT_TYPE_BOX", "", "", "CC_UP_KEC", HtmlUtil.EDIT, "", "Upgrade", "col-sm-2", request) %> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 							<div class="col-sm-6 form-group"> -->
<!-- 								<div class="col-sm-2"> -->
<%-- 									<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_CHANNEL", "col-sm-12") %> --%>
<!-- 								</div> -->
<!-- 								<div class="col-sm-10 well"> -->
<!-- 									<div class="row form-horizontal"> -->
<%-- 										<%=HtmlUtil.checkBoxInline("BRANCH_TYPE_BOX", "", "", SystemConstant.getConstant("BRANCH_REGION_CHANNEL"), HtmlUtil.EDIT, "", "Branch", "col-sm-2", request) %> --%>
<!-- 										<div class="col-sm-10"> -->
<!-- 											<div class="row form-horizontal"> -->
<!-- 												<div class=" col-sm-3 text-right"> -->
<%-- 													<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_REGION", "") %> --%>
<!-- 												</div> -->
<%-- 												<%=SelectTransferTool.DisplaySelectTransferData("BRANCH_REGION_TRANSFER",SelectTransferTool.REGION) %> --%>
<!-- 											</div> -->
<!-- 											<div class="row form-horizontal text-right"> -->
<!-- 												<div class=" col-sm-3 text-right"> -->
<%-- 													<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_ZONE", "") %> --%>
<!-- 												</div> -->
<%-- 												<%=SelectTransferTool.DisplaySelectTransferData("BRANCH_ZONE_TRANSFER",SelectTransferTool.ZONE) %> --%>
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="row form-horizontal"> -->
<%-- 										<%=HtmlUtil.checkBoxInline("DSA_TYPE_BOX", "", "", SystemConstant.getConstant("DSA_EXPAND_ZONE_CHANNEL"), HtmlUtil.EDIT, "", "DSA", "col-sm-2", request) %> --%>
<!-- 										<div class="col-sm-10"> -->
<!-- 											<div class="row form-horizontal text-right"> -->
<!-- 												<div class=" col-sm-3 text-right"> -->
<%-- 													<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_EXPAND_ZONE", "") %> --%>
<!-- 												</div> -->
<%-- 												<%=SelectTransferTool.DisplaySelectTransferData("DSA_EXPAND_ZONE_TRANSFER",SelectTransferTool.TEAMZONE) %> --%>
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="row form-horizontal"> -->
<%-- 										<%=HtmlUtil.checkBoxInline("NBD_TYPE_BOX", "", "", SystemConstant.getConstant("NBD_ZONE_CHANNEL"), HtmlUtil.EDIT, "", "NBD", "col-sm-2", request) %> --%>
<!-- 										<div class="col-sm-10"> -->
<!-- 											<div class="row form-horizontal text-right"> -->
<!-- 												<div class=" col-sm-3 text-right"> -->
<%-- 													<%=HtmlUtil.getFieldLabel(request, "OR_REPORT_ZONE", "") %> --%>
<!-- 												</div> -->
<%-- 												<%=SelectTransferTool.DisplaySelectTransferData("NBD_ZONE_TRANSFER",SelectTransferTool.ZONE) %> --%>
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<br> -->
<!-- 									<div class="row form-horizontal"> -->
<%-- 										<%=HtmlUtil.checkBoxInline("CHANNEL_TYPE_BOX", "", "", SystemConstant.getConstant("SILKSPAN_CHANNEL"), HtmlUtil.EDIT, "", "Silkspan", "col-sm-3", request) %> --%>
<%-- 										<%=HtmlUtil.checkBoxInline("CHANNEL_TYPE_BOX", "", "", SystemConstant.getConstant("DIRECT_MAIL_CHANNEL"), HtmlUtil.EDIT, "", "Direct Mail", "col-sm-4", request) %> --%>
<%-- 										<%=HtmlUtil.checkBoxInline("CHANNEL_TYPE_BOX", "", "", SystemConstant.getConstant("OTHER_CHANNEL"), HtmlUtil.EDIT, "", "Other", "col-sm-3", request) %> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="row"> -->
<!-- 							<div class="col-sm-12 text-center"> -->
<%-- 								<%=HtmlUtil.button("OR_REPORT_GENERATE_BUTTON", "OR_REPORT_GENERATE_BUTTON", HtmlUtil.EDIT, "btn2 btn2-green", "", request) %> --%>
<%-- 								<%=HtmlUtil.button("OR_REPORT_RESET_BUTTON", "OR_REPORT_RESET_BUTTON", HtmlUtil.EDIT, "btn2", "", request) %> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</form> -->
<!-- </section> -->

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
