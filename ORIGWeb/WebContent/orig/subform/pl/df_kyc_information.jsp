<%@page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLKYCDataM"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<%	
	Logger logger = Logger.getLogger(this.getClass());

	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(applicationM == null){
		 applicationM = new PLApplicationDataM();
	};
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	
	PLKYCDataM kycM = applicationM.getKycM();
	if(OrigUtil.isEmptyObject(kycM)) {
		kycM = new PLKYCDataM();
	}	
	
	String relationFlag = kycM.getRelationFlag();
	String relationFlagDesc ="";
	if("Y".equalsIgnoreCase(relationFlag)){
		relationFlagDesc = PLMessageResourceUtil.getTextDescription(request,"YES");
	}else if("N".equalsIgnoreCase(relationFlag)){
		relationFlagDesc = PLMessageResourceUtil.getTextDescription(request,"NO");
	}
	
	String relation = kycM.getRelationFlag();
	String relationDesc="";
	if(null!=relation){
		relationDesc = origc.getNaosCacheDisplayNameDataM(86,relation);
	}else{
		relation="";
	}	
	
	String dfDecision = "";	
// 	logger.debug("applicationM.getDF_Decision() >>" +applicationM.getDF_Decision());
	
// 	if(OrigConstant.Action.COMPLETE_CIS.equals(applicationM.getDF_Decision())){
// 		dfDecision = "Y";
// 	}
	
	
%>
<table width="100%">	
	<tr height="25">
		<td align="left" class="textR" width="30%"><%=PLMessageResourceUtil.getTextDescription(request,"KYC_RELATED")%>&nbsp;:&nbsp;</td>
		<td align="left" class="inputL" width="10%"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(relationDesc)%></td>
		<td align="left" class="textR"><%=PLMessageResourceUtil.getTextDescription(request,"HOW_RELATION")%>&nbsp;:&nbsp;</td>
		<td align="left" class="inputL" width="40%"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(relationFlagDesc) %></td>
	</tr>	
	<tr height="25">
		<td align="left" class="textR"><%=PLMessageResourceUtil.getTextDescription(request,"KYC_NAME")%>&nbsp;:&nbsp;</td>
		<td align="left" class="inputL">
		<% 
			String relTitle = kycM.getRelTitleName();
			String relTitleDesc ="";
			if(null!=relTitle){
				relTitleDesc = origc.getNaosCacheDisplayNameDataM(3,relTitle);
			}
		 %>
		<%=HTMLRenderUtil.displayHTML(relTitleDesc)%>&nbsp;
		<%=HTMLRenderUtil.displayHTML(kycM.getRelName())%>&nbsp;
		<%=HTMLRenderUtil.displayHTML(kycM.getRelSurName())%>
		</td>
		<td align="left" class="textR"><%=PLMessageResourceUtil.getTextDescription(request,"POSITION")%>&nbsp;:&nbsp;</td>
		<td align="left" class="inputL"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(kycM.getRelPosition()) %></td>
	</tr>
	
	<tr height="25">
		<td align="left" class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "KYC_DURATION")%>&nbsp;:&nbsp;</td>
		<td align="left" class="inputL" nowrap="nowrap">
		<%=HTMLRenderUtil.displayHTML(DataFormatUtility.DateEnToStringDateTh(kycM.getWorkStartDate(),1))%>
		<%if(!OrigUtil.isEmptyObject(kycM.getWorkEndDate())){ %>
			<%=HTMLRenderUtil.displayHTML(", ")%> 
		<%}%>
		<%=HTMLRenderUtil.displayHTML(DataFormatUtility.DateEnToStringDateTh(kycM.getWorkEndDate(),1))%></td>
		<td></td>
		<td></td>
	</tr>	
	<tr height="25">
		<td colspan="4"><hr></td>		
	</tr>	
	<tr height="25">
		<td align="left" class="textR" width="30%"><%=PLMessageResourceUtil.getTextDescription(request, "VERIFY_DOCUMENT")%>&nbsp;:&nbsp;</td>
		<td align="left" class="inputL"><%=PLMessageResourceUtil.getTextDescription(request, "CHECK_WITH_APPLICATION")%></td>
		<td></td>
		<td></td>
	</tr>	
	<tr height="25">
		<td align="left" class="textR" width="30%">
			<%=PLMessageResourceUtil.getTextDescription(request, "COMPLETE_CIS_REGISTRATION",ORIGUser, personalM.getCustomerType(),"DF_KYC_INFO","df_decesion")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"DF_KYC_INFO","df_decesion")%>&nbsp;:&nbsp;
		</td>
		<td align="left" class="inputL"><%=HTMLRenderUtil.displayCheckBox(dfDecision,"df_decesion","Y","") %></td>
		<td></td>
		<td></td>
	</tr>
</table>