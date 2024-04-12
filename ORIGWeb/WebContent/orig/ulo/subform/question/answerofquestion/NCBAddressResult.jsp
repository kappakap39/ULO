
<%@page import="com.eaf.orig.ulo.app.ncb.dao.OrigNCBInfoDAO"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig/ulo/subform/question/answerofquestion/js/NCBAddressResult.js"></script>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String displayMode = HtmlUtil.EDIT;
 	ArrayList<NcbAddressDataM> ncbAddresss	= (ArrayList<NcbAddressDataM>)ModuleForm.getObjectForm();
	if(Util.empty(ncbAddresss)){
	 	ncbAddresss = new  ArrayList<NcbAddressDataM>();
	}
%>
<div class="ncb-background">
	<div class="col-sm-6 ncb-watermark"><span class="ncb-watermark-text" data-bg-text="<%=ORIGUser.getUserName()%>"></span></div>
</div>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
				<table class="table table-bordered">
					<tbody>
						<tr class="tabletheme_header">
							<th><%=HtmlUtil.getLabel(request, "NUMBER_ABBR", "col-sm-8 col-md-12") %></th>
							<th><%=HtmlUtil.getLabel(request, "ADDRESS_DATA", "col-sm-8 col-md-12") %></th>
						</tr>
							<%
							if(!Util.empty(ncbAddresss)){
									int counter = 1;
									for(NcbAddressDataM ncbAddress:ncbAddresss){
										String seq ="";											 
										seq = String.valueOf(counter++);%>
										<tr>
											<td><%=FormatUtil.display(seq)%></td>
											<td><%=FormatUtil.display(ncbAddress.getAddressLine())%></td>

										</tr>
								<%	}
								}else{
									%>
										<tr >
											<td colspan="999" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND") %> </td>
										</tr>
									<%
								}
						 	%>
					</tbody>
				</table>
			</div>
		</div>
	</div>