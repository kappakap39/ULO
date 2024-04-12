<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.model.FieldConfigDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="java.util.Collections"%>
<%@page import="com.eaf.common.utility.CompareSensitiveUtility"%>
<%@page import="com.eaf.core.ulo.common.model.CompareFieldElement"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.model.compare.CompareDataM"%>
<%@page import="com.eaf.orig.ulo.model.compare.CompareDisplayHelperDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction"%>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/RekeyDataPopup.js"></script>
<%
	String displayMode = HtmlUtil.EDIT;
	String subformId = "REKEY_DATA_POPUP";
	String tagId = "";
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
	Logger logger = Logger.getLogger(this.getClass());
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	HashMap<String,ArrayList<CompareFieldElement>> hCompareFieldElementList = (HashMap<String,ArrayList<CompareFieldElement>>)request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);
	if(Util.empty(hCompareFieldElementList)){
		hCompareFieldElementList = new HashMap<String,ArrayList<CompareFieldElement>>();
	}	 
%>
<section  class="warpSubFormTemplate rekey-warpSubFormTemplate">
	<section class="work_area">
 	<%
 	ArrayList<CompareFieldElement> groupFieldElement = hCompareFieldElementList.get(CompareDataM.GroupDataLevel.APPLICATION_GROUP);
	if(!Util.empty(groupFieldElement)){
 	ArrayList<CompareDataM> appGroupCompareDatas = CompareSensitiveUtility.allCompareDataMList(groupFieldElement);
	Collections.sort(appGroupCompareDatas, new CompareSensitiveUtility());%>
		<div id="SECTION_<%=CompareDataM.GroupDataLevel.APPLICATION_GROUP %>">
				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row form-horizontal">
								<%for(CompareDataM compareDataM : appGroupCompareDatas){
 									ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
									element.writeElement(pageContext, compareDataM);
 								}%>											
								</div>
							</div>
						</div>
					</div>
				</div>
		</div>			
	<%}%>
		
		
		 
	 <%
	 	PersonalInfoDataM personalInfoApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
	 	if(Util.empty(personalInfoApplicant)){
	 		personalInfoApplicant = new PersonalInfoDataM();
	 	}
	 	String personlSeq = FormatUtil.toString(personalInfoApplicant.getSeq());
	 	ArrayList<CompareFieldElement> applicantFieldElementList = hCompareFieldElementList.get(CompareSensitiveFieldUtil.getCompareElementFieldTypePersonal(PERSONAL_TYPE_APPLICANT,personlSeq));
	 
	 	if(!Util.empty(applicantFieldElementList)){
		  Collections.sort(applicantFieldElementList, new CompareSensitiveUtility());
	 	  String personalId = personalInfoApplicant.getPersonalId();
	 	  %>
	 	  
			<div id="SECTION_<%=CompareDataM.GroupDataLevel.PERSONAL_APPLICANT %>">
				<div class="rekey-subtitlecontent">
					<%=HtmlUtil.getLabel(CacheControl.getName(FIELD_ID_PERSONAL_TYPE, PERSONAL_TYPE_APPLICANT), "")%>				
 	  				&nbsp;<%=personalInfoApplicant.getSeq()%>
					<%-- Defect : 384 --%>
<%--  	  			<%=FormatUtil.displayText(personalInfoApplicant.getThFirstName())%>&nbsp;<%=FormatUtil.displayText(personalInfoApplicant.getThLastName())%> --%>
 	  			</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row form-horizontal">
								<%for(CompareFieldElement applicantCommpareFieldElement: applicantFieldElementList){
		 	 						ArrayList<CompareDataM> compareDataList = applicantCommpareFieldElement.getCompareDatas();
		 	 						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, applicantCommpareFieldElement.getImplementId());
		 	 						if(applicantCommpareFieldElement.isUseCompareFieldElement()){
		 	 							element.writeElement(pageContext, applicantCommpareFieldElement);
		 	 						}else{
							 			for(CompareDataM comapareData : compareDataList){
							 	 			element.writeElement(pageContext, comapareData);
										 }
		 	 						}
		 	 					}%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
	 <% }%>
		
		
	 <%
	   ArrayList<PersonalInfoDataM>   personalInfoSuplementarys = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
	 	if(!Util.empty(personalInfoSuplementarys)){
	 		for(PersonalInfoDataM personalInfoSuplementary : personalInfoSuplementarys){
	 			String personlSupSeq = FormatUtil.toString(personalInfoSuplementary.getSeq());
	 			String compareFieldType =CompareSensitiveFieldUtil.getCompareElementFieldTypePersonal(PERSONAL_TYPE_SUPPLEMENTARY,personlSupSeq);
	 			ArrayList<CompareFieldElement> supplementatyFieldElementList = hCompareFieldElementList.get(compareFieldType);
	 			ArrayList<ApplicationDataM> applicationsuplementarys = applicationGroup.getApplications(personalInfoSuplementary.getPersonalId(), personalInfoSuplementary.getPersonalType(), personalInfoSuplementary.getPersonalRelation().getRelationLevel());
	 			if(!Util.empty(supplementatyFieldElementList)){
	 			  Collections.sort(supplementatyFieldElementList, new CompareSensitiveUtility());%>
	 			  <div id="SECTION_<%=CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY %>">
	 			  	<div class="rekey-subtitlecontent">
						<%=HtmlUtil.getLabel(CacheControl.getName(FIELD_ID_PERSONAL_TYPE, PERSONAL_TYPE_SUPPLEMENTARY), "")%>
	 	  				&nbsp;<%=personalInfoSuplementary.getSeq()%>
						<%-- Defect : 384 --%>
<%-- 	 	  				<%=FormatUtil.displayText(personalInfoSuplementary.getThFirstName())%>&nbsp;<%=FormatUtil.displayText(personalInfoSuplementary.getThLastName())%> --%>
	 	  			</div>
	  					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="row form-horizontal">
									<%for(CompareFieldElement supplementaryCommpareFieldElement: supplementatyFieldElementList){
			 	 						ArrayList<CompareDataM> compareDataList = supplementaryCommpareFieldElement.getCompareDatas();
			 	 						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, supplementaryCommpareFieldElement.getImplementId());
			 	 						if(supplementaryCommpareFieldElement.isUseCompareFieldElement()){
			 	 							element.writeElement(pageContext, supplementaryCommpareFieldElement);
			 	 						}else{
								 			for(CompareDataM comapareData : compareDataList){
								 			 	element.writeElement(pageContext, comapareData);
								 			}
			 	 						}
			 	 					}%>
									
									
									<%
									for(ApplicationDataM applicationsuplementary:applicationsuplementarys){
									
										String compareFieldTypeApplication = CompareDataM.UniqueLevel.APPLICATION+applicationsuplementary.getApplicationRecordId();
	 									ArrayList<CompareFieldElement> supplementatyAppFieldElementList = hCompareFieldElementList.get(compareFieldTypeApplication);
	 									if(!Util.empty(supplementatyAppFieldElementList)){
										 Collections.sort(supplementatyAppFieldElementList, new CompareSensitiveUtility());
										for(CompareFieldElement supplementaryCommpareFieldElement: supplementatyAppFieldElementList){
				 	 						ArrayList<CompareDataM> compareDataList = supplementaryCommpareFieldElement.getCompareDatas();
				 	 						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, supplementaryCommpareFieldElement.getImplementId());
				 	 						if(supplementaryCommpareFieldElement.isUseCompareFieldElement()){
				 	 							element.writeElement(pageContext, supplementaryCommpareFieldElement);
				 	 						}else{
									 			for(CompareDataM comapareData : compareDataList){
									 			 	element.writeElement(pageContext, comapareData);
									 			}
				 	 						}
				 	 					}
			 	 					}
			 	 					}%>
			 	 					
			 	 					
									</div>
								</div>
							</div>
						</div>
					</div>
	 			  </div>
	 			<%}else{
	 						boolean flagShowSup = false;
	 						for(ApplicationDataM applicationsuplementary:applicationsuplementarys){
	 							String compareFieldTypeApplication = CompareDataM.UniqueLevel.APPLICATION+applicationsuplementary.getApplicationRecordId();
	 							ArrayList<CompareFieldElement> supplementatyAppFieldElementList = hCompareFieldElementList.get(compareFieldTypeApplication);
	 							if(!Util.empty(supplementatyAppFieldElementList)){
	 								flagShowSup = true;
	 								break;
	 							}
	 							
	 						}
	 			
	 				if(flagShowSup){%>
	 			  <div id="SECTION_<%=CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY %>">
	 			  	<div class="rekey-subtitlecontent">
						<%=HtmlUtil.getLabel(CacheControl.getName(FIELD_ID_PERSONAL_TYPE, PERSONAL_TYPE_SUPPLEMENTARY), "")%>
	 	  				&nbsp;<%=personalInfoSuplementary.getSeq()%>
						<%-- Defect : 384 --%>
<%-- 	 	  				<%=FormatUtil.displayText(personalInfoSuplementary.getThFirstName())%>&nbsp;<%=FormatUtil.displayText(personalInfoSuplementary.getThLastName())%> --%>
	 	  			</div>
	  					<div class="row">
						<div class="col-xs-12">
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="row form-horizontal">
									<%
									for(ApplicationDataM applicationsuplementary:applicationsuplementarys){
										String compareFieldTypeApplication = CompareDataM.UniqueLevel.APPLICATION+applicationsuplementary.getApplicationRecordId();
	 									ArrayList<CompareFieldElement> supplementatyAppFieldElementList = hCompareFieldElementList.get(compareFieldTypeApplication);
	 									if(!Util.empty(supplementatyAppFieldElementList)){
										 Collections.sort(supplementatyAppFieldElementList, new CompareSensitiveUtility());
										for(CompareFieldElement supplementaryCommpareFieldElement: supplementatyAppFieldElementList){
				 	 						ArrayList<CompareDataM> compareDataList = supplementaryCommpareFieldElement.getCompareDatas();
				 	 						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, supplementaryCommpareFieldElement.getImplementId());
				 	 						if(supplementaryCommpareFieldElement.isUseCompareFieldElement()){
				 	 							element.writeElement(pageContext, supplementaryCommpareFieldElement);
				 	 						}else{
									 			for(CompareDataM comapareData : compareDataList){
									 			 	element.writeElement(pageContext, comapareData);
									 			}
				 	 						}
				 	 					}
			 	 					}
			 	 					}
			 	 					}%>
			 	 					
			 	 					
									</div>
								</div>
							</div>
						</div>
					</div>
	 			  </div>
	 			<%
	 			}
	 		}
	 	}
	 	 %>
 
		
		
		
		
		
		<%-- <%
			if(!Util.empty(compareFields)) {
				CompareDisplayHelperDataM personalLevelHelper =  compareFields.get(CompareDataM.RefLevel.PERSONAL);
				if(!Util.empty(personalLevelHelper)) {
					Set<String> personalInfoFields = personalLevelHelper.getComparisonKeys();
					if(!Util.empty(personalInfoFields)) {
						for(String refId: personalInfoFields) {
							logger.debug("refId : "+refId);
							ArrayList<CompareDataM> differenceFields = personalLevelHelper.getComparisonFields(refId);
							if(!Util.empty(differenceFields)) {
								for(CompareDataM compareDataM: differenceFields) {
									ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
									element.writeElement(pageContext, compareDataM); 
								%>
			<%	
								}
							}
						}
					
					}
				}
				CompareDisplayHelperDataM appGroupLevelHelper =  compareFields.get(CompareDataM.RefLevel.APPLICATION_GROUP);
				if(!Util.empty(appGroupLevelHelper)) {
					Set<String> appGroupFields = appGroupLevelHelper.getComparisonKeys();
					if(!Util.empty(appGroupFields)) {
						for(String refId: appGroupFields) {
							logger.debug("refId : "+refId);
							ArrayList<CompareDataM> differenceFields = appGroupLevelHelper.getComparisonFields(refId);
							if(!Util.empty(differenceFields)) {
								for(CompareDataM compareDataM: differenceFields) {
									ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
									element.writeElement(pageContext, compareDataM); 
								%>
			<%					}
							}
						}
					}
				}
				CompareDisplayHelperDataM appLevelHelper =  compareFields.get(CompareDataM.RefLevel.APPLICATION);
				if(!Util.empty(appLevelHelper)) {
					Set<String> appFields = appLevelHelper.getComparisonKeys();
					if(!Util.empty(appFields)) {
						for(String refId: appFields) {
							logger.debug("refId : "+refId);
							ArrayList<CompareDataM> differenceFields = appLevelHelper.getComparisonFields(refId);
							if(!Util.empty(differenceFields)) {
								for(CompareDataM compareDataM: differenceFields) {
									ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.COMPARE_SENSITIVE, compareDataM.getFieldNameType());
									element.writeElement(pageContext, compareDataM);
								%>
			<%	
								}
							}
						}
					
					}
				}
			} 
		%> --%>
	</section>
</section>