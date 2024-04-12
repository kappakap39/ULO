<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbAddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.ncb.NcbInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/ncb/js/NCBReportSubForm.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<%
Logger logger = Logger.getLogger(this.getClass());
String subformId ="NCB_REPORT_SUBFORM";
String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
FormUtil formUtil = new FormUtil(subformId,request);
ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ModuleForm.getObjectForm();
int maxLifeCycle = applicationGroup.getMaxLifeCycle();
ArrayList<String> PRODUCTS = applicationGroup.getProducts(maxLifeCycle);
ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
String PERSONAL_TYPE_APPLICANT  = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
 %>
 <div class="ncb-background">
 	<div class="col-sm-6 ncb-watermark"><span class="ncb-watermark-text" data-bg-text="<%=ORIGUser.getUserName()%>"></span></div>
 </div>	 	 
<%
if(!Util.empty(personalInfos)){
  for(PersonalInfoDataM personalInfoDataM : personalInfos){
 	 if(PERSONAL_TYPE_APPLICANT.equals(personalInfoDataM.getPersonalType())){
	  	  ArrayList<NcbInfoDataM>  ncbInfos = personalInfoDataM.getNcbInfos();
	  	  String CID_TYPE = personalInfoDataM.getCidType();
	  	  String ID_NO ="-";
	  	  String PASS_PORT_NO="-";
	  	  if(CIDTYPE_PASSPORT.equals(CID_TYPE)){
	  	  	PASS_PORT_NO = personalInfoDataM.getIdno();
	  	  }else{
	  	  	ID_NO = personalInfoDataM.getIdno();
	  	  }
	  	  if(!Util.empty(ncbInfos)){
	 	  for(NcbInfoDataM ncbInfo : ncbInfos){
	 	  %>
	 	  <div id="ncb-content">
			<div class="panel panel-default">
				<div class="panel-heading"><%=LabelUtil.getText(request, "NCB_PERSONAL_INFO")%></div>
				<div class="panel-body">  		
					<div class="row form-horizontal">
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NAME", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display(personalInfoDataM.getThFirstName()) +" " + FormatUtil.display(personalInfoDataM.getThLastName())%>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				
				<div class="panel-heading"><%=LabelUtil.getText(request, "NCB_ID_INFO")%></div>
				<div class="panel-body">  		
					<div class="row form-horizontal">
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCB_ID_NO", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display(ID_NO)%>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCB_ALL_ENQUIRE", "col-sm-4 col-md-5 control-label")%>
								<%=0!=ncbInfo.getNoOfTimesEnquiry()?String.valueOf(ncbInfo.getNoOfTimesEnquiry()):"-" %>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCN_GOVERNMENT_OFFICER_ID", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display("-") %>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCB_ENQUIRE_UNDER_6_MONTH", "col-sm-4 col-md-5 control-label")%>
								<%=0!=ncbInfo.getNoOfTimesEnquiry6M()?String.valueOf(ncbInfo.getNoOfTimesEnquiry6M()):"-" %>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCB_FOREIGN_PASSPORT_ID", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display(PASS_PORT_NO)%>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "OTHER", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display("-") %>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				
				<div class="panel-heading"><%=LabelUtil.getText(request, "NCB_FICO_INFORMATION")%></div>
				<div class="panel-body">  		
					<div class="row form-horizontal">
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCB_FICO_REFERENCE", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display(ncbInfo.getConsentRefNo()) %>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "NCB_FICO_SCORE", "col-sm-4 col-md-5 control-label")%>
								<%=FormatUtil.display(ncbInfo.getFicoScore()) %>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				
				<div class="panel-heading"><%=LabelUtil.getText(request, "NCB_ADDRESS_INFO")%></div>
				<div class="panel-body">  		
					<div class="row form-horizontal">
					<% if(!Util.empty(ncbInfo.getNcbAddress())){
						for(NcbAddressDataM ncbAddress : ncbInfo.getNcbAddress()){%>
							<div class="col-sm-6">
								<div class="form-group">
									<label class="col-sm-1 col-md-1 control-label text-left">-</label>
									<%=FormatUtil.display(ncbAddress.getAddressLineReport())%>
								</div>
							</div>
							<div class="clearfix"></div>				 
					   <%}
					 }else{%>
					 	<div class="col-sm-6">
							<div class="form-group">
									<label class="col-sm-1 col-md-1 control-label text-left">-</label>
								</div>
							</div>
						<div class="clearfix"></div>	
					<%} %>
					</div>
				</div>
	<!-- 			display product table -->
				<%if(!Util.empty(PRODUCTS)) {
						for(String productName :PRODUCTS) {
							logger.debug("productName>>"+productName);
							ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.NCB_REPORT,"NCB_REPORT_"+productName);
							element.writeElement(pageContext,ncbInfo);
						}
					} 
				 %>
			</div>
	
	<%		}
		}else{%>
		<div class="panel panel-default">
		<div class="panel-body">  		
			<div class="row form-horizontal">
				<div class="col-sm-12 text_center">
					<%=LabelUtil.getText(request, "NO_RECORD_FOUND")%>
				</div>
			</div>
		</div>
		</div>	
	<%	}
	  }else{%>
<div class="panel panel-default">
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-12 text_center">
				<%=LabelUtil.getText(request, "NO_RECORD_FOUND")%>
			</div>
		</div>
	</div>
</div>
<%}
  }
}else{%>
<div class="panel panel-default">
	<div class="panel-body">  		
		<div class="row form-horizontal">
			<div class="col-sm-12 text_center">
				<%=LabelUtil.getText(request, "NO_RECORD_FOUND")%>
			</div>
		</div>
	</div>
</div>
<%}%>
</div>