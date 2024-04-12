<%@page import="com.eaf.core.ulo.common.message.MessageErrorUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.message.MessageErrorUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormErrorUtil"%>

<script src="js/ulo/jquery.blockUI.js"></script>
<script src="orig/pe/js/uploadPttBlueCard.js"></script>


<%
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String IMPORT_ID = flowControl.getStoreAction("IMPORT_ID");
	String DOWNLOAD_ID = flowControl.getStoreAction("DOWNLOAD_ID");
	System.out.println("IMPORT_ID = " + IMPORT_ID);
	System.out.println("DOWNLOAD_ID = " + DOWNLOAD_ID);
	String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
	FormErrorUtil formError = new FormErrorUtil();	
 %>
<section class="work_area padding-top" id ="SECTION_UPLOAD_FILE_MANUAL">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-md-12">
							<div class="form-group">
								<%=HtmlUtil.getMandatoryLabel(request, "UPLOAD_PTT_BLUE_CARD","col-sm-3 col-md-4 control-label")%>
								<div class="col-sm-9 col-md-8">
									<div id="UPLOAD_PTT_BLUE_CARD">Browse</div>
								</div>
								<%=HtmlUtil.hidden("FILE_NAME", "")%>
								<div class='col-md-12 col-md-offset-4'><div class="ajax-file-upload-error" id="ERROR"></div></div>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<div class="col-md-offset-4">
	 								<%=HtmlUtil.button("MF_UPLOAD_SELECTED_BTN","MF_UPLOAD_SELECTED_BTN",HtmlUtil.EDIT,"btn button green","",request) %>
									<%=HtmlUtil.button("MF_CANCEL_BTN","MF_CANCEL_BTN",HtmlUtil.EDIT,"btn button red","",request) %>
									<%=HtmlUtil.button("MF_DOWNLOAD_EXISTING_BTN", "MF_DOWNLOAD_EXISTING_BTN", HtmlUtil.EDIT, "btn button green", "", request) %>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="row form-horizontal">
						<div class="col-md-12 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "MF_TOTAL", "col-sm-4 col-md-4 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="MF_TOTAL"><%=FormatUtil.display("")%></div>			
						</div>
						<div class="col-md-12 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "MF_VALID_RECORD", "col-sm-4 col-md-4 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="MF_VALID_RECORD"><%=FormatUtil.display("")%></div>			
						</div>
						<div class="col-md-12 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "MF_INVALID_RECORD", "col-sm-4 col-md-4 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="MF_INVALID_RECORD"><%=FormatUtil.display("")%></div>			
						</div>
						<div class="col-md-12 col-md-offset-4">
							<%=HtmlUtil.getLabel(request, "MF_DOWNLOAD_INVALID", "col-sm-4 col-md-4 control-label") %>
							<div class="col-sm-2 col-md-3 control-label" id="MF_DOWNLOAD_INVALID"><%=FormatUtil.display("")%></div>			
							<div class="col-sm-2 col-md-4 dowload_excel"  id="MF_DOWNLOAD_EXCEL_INVALID"></div>	
						</div>
 
					</div>
				</div>
			</div>
			
		</div>
	</div>
	<iframe id="my_iframe" style="display:none;"></iframe>
</section>

	
<div class="ui-block-element bootstrap-dialog type-warning fade size-normal in"tabindex="-1" role="dialog" aria-hidden="false"
	id="block_screen_ui" style="display: none; ">
<!-- <div class="modal-backdrop fade in" style="height: 258px;"></div> -->
<div class="modal-backdrop fade in"></div>
	<div class="modal-dialog" id="form_undefined_dialog"
		style="width: 600px;">
		<div class="modal-content">
			<div class="modal-header ">
				<div class="bootstrap-dialog-header">
					<div class="bootstrap-dialog-close-button" style="display: none;">
					</div>
					<div class="bootstrap-dialog-title"
						id="title">Notification</div>
				</div>
			</div>
			<div class="modal-body">
				<div class="bootstrap-dialog-body">
					<div class="bootstrap-dialog-message">
						<div class="row formDialog">
							<div class="col-xs-12" id='upload_file_msg'></div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer" style="display: block;">
				<div class="bootstrap-dialog-footer">
					<div class="bootstrap-dialog-footer-buttons">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


