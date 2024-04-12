<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemCache"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<script type="text/javascript" src="orig/ulo/screen/js/DMImportWithdrawAuthority.js"></script>
<section class="work_area padding-top" id ="SECTION_DM_WITHDRAW_AUTHORITY">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-md-12">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "DM_FILE_WTHDRAW","col-sm-3 col-md-4 control-label")%>
								<div class="col-sm-9 col-md-8">
									<div id="IMPORT_WITHDRAW_AUTHORITY">Browse</div>
								</div>
								<%=HtmlUtil.hidden("FILE_NAME", "")%>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<div class="groupList groupList_search ">
	 								<%=HtmlUtil.button("DM_IMPORT_BTN","DM_IMPORT_BTN",HtmlUtil.EDIT,"btn button green","",request) %>
									<%=HtmlUtil.button("DM_CANCEL_BTN","DM_CANCEL_BTN",HtmlUtil.EDIT,"btn button red","",request) %>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-md-6 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "DM_TOTAL", "col-sm-1 col-md-1 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="DM_TOTAL"><%=FormatUtil.display("")%></div>			
						</div>
						<div class="col-md-6 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "DM_SUCCESS", "col-sm-1 col-md-1 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="DM_SUCCESS"><%=FormatUtil.display("")%></div>			
						</div>
						<div class="col-md-6 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "DM_REJECT", "col-sm-1 col-md-1 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="DM_REJECT"><%=FormatUtil.display("")%></div>			
							<%=HtmlUtil.getLabel(request, "DM_EXPORT_REJECT", "col-sm-3 col-md-4 control-label") %>		
							<div class="col-sm-2 col-md-4 dowload_excel"  id="DM_EXPORT_REJECT"></div>	
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</section>
