<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="java.sql.Date"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.NotePadDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/CommentPopup.js"></script>
<section id="SECTION_COMMENT_POPUP">
<%
	ArrayList<String> SHOW_COMMENT_ROLE = SystemConstant.getArrayListConstant("SHOW_COMMENT_ROLE");
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String roleId = flowControl.getRole();
	String displayMode = HtmlUtil.EDIT;
	Logger logger = Logger.getLogger(this.getClass());
		
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if(Util.empty(applicationGroup)){
		applicationGroup = new ApplicationGroupDataM();
	}
	ArrayList<NotePadDataM> notepads = applicationGroup.getNotePads();
	if(Util.empty(notepads)){
	   notepads =  new ArrayList<NotePadDataM>();
	}	
	
	logger.debug("PAGE SESSION>>>"+notepads.size());
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		displayMode=HtmlUtil.VIEW;
	}
	
%>
	<div class="row padding-top ">
		<div class="col-xs-12">
			<div class="panel panel-default ">
				<div class="panel-body ">
					<div class="row form-horizontal" style='padding-top: 0px'>
						<div class="col-sm-12">
							<div class="input-group ">
								<%=HtmlUtil.textarea("NOTE_PAD_DESC", "", "", "5", "100", "1000", displayMode, "", "fixed-textarea", request)%>
							</div>
						</div>
						<div class="col-sm-12">
							<div class="input-group add-comment-btn ">
								<%=HtmlUtil.button("ADD_COMMENT", "ADD_BTN", displayMode, "btn btn-primary", "", request) %>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%=HtmlUtil.button("DELETE_COMMENT", "DEL_BTN", displayMode, "btn btn-danger", "", request) %>
	<div class="row">
		<div class="col-xs-12">
			<table class="table table-hover table-striped">
				<tbody>
					<%if(!Util.empty(notepads)){
						int seq = notepads.size();
						for(int i = notepads.size() - 1; i >= 0; i--){
							notepads.get(i).setSeq(seq--);
							if(!MConstant.FLAG_N.equals(notepads.get(i).getStatus())){
								String disabled ="";
								if(!ORIGUser.getUserName().equals(notepads.get(i).getCreateBy())){
									disabled ="disabled";
								}
								logger.debug("createdate : " + notepads.get(i).getCreateDate());
					%>
					<tr>
						<td><%=HtmlUtil.checkBox("COMMENT_CH","","","",FormatUtil.toString(notepads.get(i).getSeq()),HtmlUtil.EDIT,""+disabled,"","",request)%></td>			
						<td>
							
							<div class="inboxitemcard bold commenttext">
							<%
								String notePadData = notepads.get(i).getNotePadDesc();
								if(!Util.empty(notePadData)){
									String[] notePadDatas = notePadData.split("<BR/>");
									String br = "";
									if(null!=notePadDatas)
									for(String data:notePadDatas){
										out.print(br);
										out.print(FormatUtil.display(data));
										br = "<BR/>";
									}
								}
							%>
							</div>
							<div>
								<%=FormatUtil.display(notepads.get(i).getCreateDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy) %>
								<%=FormatUtil.display(notepads.get(i).getCreateDate(),FormatUtil.TH,FormatUtil.Format.HHMMSS) %>&nbsp;
								<%=FormatUtil.display(notepads.get(i).getCreateBy())%>,&nbsp;
								<%=CacheControl.getName("User", notepads.get(i).getCreateBy())%>,&nbsp;
								<%=FormatUtil.display(notepads.get(i).getUserRole())%>
							</div>
							
						</td>
					</tr>
					<%
					}
				}
			}else{ %>
					<tr><td align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>		
				<%} %>
				</tbody>
				<tfoot>
					<tr><td colspan="2"></td></tr>
				</tfoot>
			</table>
		</div>
	</div>
</section>