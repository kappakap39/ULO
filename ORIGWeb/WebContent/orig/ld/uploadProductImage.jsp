<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page contentType="text/html; charset:UTF-8;"%>
<script type="text/javascript" src="orig/js/ld/uploadProductImage.js"></script>

<section class="work_area padding-top">
<!-- 	<h3>Upload Product Image</h3> -->
	<div class="panel panel-default">
		<div class="panel-body" style="padding:15px;">
			<div class="col-sm-12">
				<div class="row form-horizontal form-group">
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-6">
								<%=HtmlUtil.getFieldLabel(request, "UPLOAD_PRODUCT_IMAGE_PRODUCT","col-sm-3") %>
								<%=HtmlUtil.dropdown("PRODUCT_DROPDOWN", "", "PRODUCT_CARD", "", "","", "", HtmlUtil.EDIT, "", "col-sm-6", request)%>
							</div>
							<div class="col-sm-6">
								<%=HtmlUtil.getFieldLabel(request, "UPLOAD_PRODUCT_IMAGE_DESCRIPTION","col-sm-3") %>
								<%=HtmlUtil.textBox("DESCRIPTION_BOX", "", "", "", "20", "", "", "col-sm-6", request) %>
							</div>
						</div>
					</div>
				</div>
				<div class="row form-horizontal form-group">
<!-- 					<div class="col-sm-6"> -->
<%-- 						<%=HtmlUtil.getFieldLabel(request, "UPLOAD_PRODUCT_IMAGE_PRODUCT_IMAGE","col-sm-4") %> --%>
<!-- 						<div class="col-sm-7"><img id="PREVIEW_IMAGE" width="150"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="row form-horizontal form-group"> -->
<!-- 					<div class="col-sm-6"> -->
<!-- 						<div class="col-sm-4"></div> -->
<%-- 						<%=HtmlUtil.textBox("FILE_NAME", "", "", "", "20", "", "readonly", "col-sm-6 readonly", request) %> --%>
<!-- 						<span class="btn btn-default btn-file"> -->
<!--    							Browse <input type="file" name="IMPORT_FILE" accept="image/*"> -->
<!-- 						</span> -->
<!-- 					</div> -->
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-3">
								<%=HtmlUtil.getMandatoryLabel(request, "UPLOAD_PRODUCT_IMAGE_PRODUCT_IMAGE","col-sm-12 control-label")%>
							</div>
							<div class="col-sm-9">
								<div class="col-sm-7"><img id="PREVIEW_IMAGE"></div>
								<div class="col-sm-9 col-md-8">
									<div id="UPLOAD_PRODUCT_IMAGE_PRODUCT_IMAGE">Browse</div>
								</div>
								<%=HtmlUtil.hidden("FILE_NAME", "")%>
							</div>
						</div>
					</div>
				</div>
				<div class="row form-horizontal form-group">
					<div class="col-sm-12" align="center">
						<%=HtmlUtil.button("UPLOAD_PRODUCT_IMAGE_UPDATE_BUTTON", "UPLOAD_PRODUCT_IMAGE_UPDATE_BUTTON", HtmlUtil.EDIT, "btn2 btn2-green", "", request) %>
						<%=HtmlUtil.button("UPLOAD_PRODUCT_IMAGE_CANCEL_BUTTON", "UPLOAD_PRODUCT_IMAGE_CANCEL_BUTTON", HtmlUtil.EDIT, "btn2", "", request) %>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>