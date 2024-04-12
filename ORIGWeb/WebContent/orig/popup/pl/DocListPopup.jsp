<%@page import="com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.cache.data.CacheDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DocumentCacheTool"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DocumentTool"%>
<%@ page import="com.eaf.orig.cache.DocumentCheckListSetDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLDocumentSetDataM"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="org.apache.log4j.Logger"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="documentDataM" scope="session" class="com.eaf.orig.ulo.pl.model.app.PLDocumentDataM"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<script type="text/javascript" src="orig/js/popup/pl/doclistpopup.js"></script>

<%
	Logger logger = Logger.getLogger(this.getClass());
		
	PLApplicationDataM appM = PLORIGForm.getAppForm();	
	if(null == appM) appM = new PLApplicationDataM();
	
	Vector<PLDocumentSetDataM> documentVect = documentDataM.getDocumentSetVect();
	Vector<PLDocumentCheckListDataM> docOtherVect  = documentDataM.getDocumentOtherVect();
	
	if(null == documentVect) documentVect = new Vector<PLDocumentSetDataM>();	
	if(null == docOtherVect) docOtherVect = new Vector<PLDocumentCheckListDataM>();
	
	DocumentTool docUtil = new DocumentTool();		
	Vector docCacheVect = docUtil.getDocumentList(documentDataM);
	
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT;
	String displayModeOtherDoc = HTMLRenderUtil.DISPLAY_MODE_EDIT;
		
	// #septemwi modify for display mode otherdoc
	String currentRole = ORIGUser.getCurrentRole();	
	if(OrigConstant.ROLE_CA.equals(currentRole)|| OrigConstant.ROLE_CA_SUP.equals(currentRole)
			|| OrigConstant.ROLE_I_CA.equals(currentRole)|| OrigConstant.ROLE_I_SUP_CA.equals(currentRole)){
		displayModeOtherDoc = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
	if(OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
		displayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
		displayModeOtherDoc = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}

	
%>
<div id="doc-list-popup">
	<div class="PanelFirst">
		<div class="PanelSecond TextHeaderNormal">
			<div class="div-error-mandatory" id="div-doc-madatory"></div>
		<!-- Begin Master DocCheckList -->
			<div class="PanelThird">
			
				<table class="TableFrame">									
					<tr class="Header">													
						<td width="25%" rowspan="2"><%=MessageResourceUtil.getTextDescription(request, "DOC_LIST")%></td>
						<td width="60%" colspan="5"><%=MessageResourceUtil.getTextDescription(request, "DOC_STATUS")%></td>
						<td width="15%" rowspan="2"><%=MessageResourceUtil.getTextDescription(request, "DOC_OFFER")%></td>																						
					</tr>
					<tr class="Header">
						<td width="10%" ><%=MessageResourceUtil.getTextDescription(request, "DOC_NOT_RECEIVE")%></td>
						<td width="10%" ><%=MessageResourceUtil.getTextDescription(request, "DOC_RECEIVE")%></td>
						<td width="10%" ><%=MessageResourceUtil.getTextDescription(request, "DOC_OVERRIDE")%></td>
						<td width="10%" ><%=MessageResourceUtil.getTextDescription(request, "DOC_TRACKING")%></td>
						<td width="20%" ><%=MessageResourceUtil.getTextDescription(request, "DOC_REASON")%></td>
					</tr>
				</table>
			<div class="div-doc-master-popup">
			<table class="TableFrame" id="table-doc-master">
			<%
				if(!OrigUtil.isEmptyVector(documentVect)){									
						for(PLDocumentSetDataM docSetM :documentVect){
			%>
				<tr class="TextHeader" valign="top">
					<td colspan="8">
						<%=HTMLRenderUtil.displayHTML((String) docSetM.getDocSetDesc())%><%=DocumentTool.GetWordRequireDocList(docSetM,request)%>
					</td>
				</tr>
				<%
					if(!OrigUtil.isEmptyVector(docSetM.getDocumentVect())){	
								String styleTr = null;																		
								for(int i=0; i<docSetM.getDocumentVect().size(); i++){																	
									PLDocumentCheckListDataM docListM = (PLDocumentCheckListDataM) docSetM.getDocumentVect().get(i);																																		
									styleTr = (i%2==0)?"ResultEven":"ResultOdd";
									
						if(HTMLRenderUtil.RadioBoxCompare.TrackDoc.equals(docListM.getReceive())){
							styleTr += " ResultBlue";
						}		
				%>	
						<tr class="Result-Obj <%=styleTr%>" valign="top" id="<%=docListM.getDocCode()%>">
							<td width="25%"><div class="text-left"><%=HTMLRenderUtil.displayHTML((String) docListM.getDocDesc())%></div></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayMode,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc,"")%></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayMode,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.ReceiveDoc,"")%></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayMode,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.OverrideDoc,"")%></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayMode,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.TrackDoc,"")%></td>
							<td width="20%">
								<%
									if(!OrigUtil.isEmptyVector(docListM.getDocCkReasonVect())){
								%>
									<table cellspacing=0 cellpadding=0 width="100%" border="0">
										<%
											for(PLDocumentCheckListReasonDataM docReasonM :docListM.getDocCkReasonVect()){
										%>
											<tr>
												<td width="5px"><%=HTMLRenderUtil.displayCheckBox(docReasonM.getIsDocReason(),"checkBox_"+docReasonM.getDocCode()+"_"+docReasonM.getDocReasonID(),HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,displayMode,"")%></td>
												<td align="left"><div class="text-left"><%=HTMLRenderUtil.displayHTML((String)docReasonM.getDocReasonDesc())%></div></td>
											</tr>
										<%
											}
										%>
									</table>
								<%
									}
								%>
							</td>
							<td width="15%">
								<%=HTMLRenderUtil.displayInputTextAreaTag(HTMLRenderUtil.replaceNull(docListM.getRemark()),"remark_"+docListM.getDocCode(),"3","20",displayMode,250)%>	
							</td>
						</tr>																							
						<%}%>															
					<%}%>		
				<%}%>
			<%}else{%>
				<tr class="ResultNotFound">
					<td colspan="8" >No record found</td>
				</tr>
			<%}%>
		</table>
		</div>
	</div>
	<!-- End Master DocCheckList -->	
		
	<div class="PanelThird">
		<table class="FormFrame">
			<tr>
				<td class="textR" width="20%" id="other-doc-element">
					<%=HTMLRenderUtil.displaySelectTagScriptActionAndCode_ORIG(docCacheVect,"","doc-others-list",displayModeOtherDoc,"")%>
				</td>
				<td class="textL">
					<%=HTMLRenderUtil.displayButtonTagScriptAction("add", displayModeOtherDoc,"button","doc-add-button", "button", "onclick=\"addOtherDoc()\"", "")%>
				</td>
			</tr>
		</table>
		<table class="TableFrame">
				<tr class="Header">
					<td colspan="8"><%=MessageResourceUtil.getTextDescription(request, "DOC_OTHER")%></td>
				</tr>
				<tr class="Header">
					<td width="5px"><%=HTMLRenderUtil.displayCheckBox("","otherdoc-check-all",HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,"onclick=\"javascript:EngineCheckboxAll('otherdoc-check-all','.otherdoc-checkbox');\"")%></td>
					<td width="25%"><%=MessageResourceUtil.getTextDescription(request, "DOC_LIST")%></td>
					<td width="10%"><%=MessageResourceUtil.getTextDescription(request, "DOC_NOT_RECEIVE")%></td>
					<td width="10%"><%=MessageResourceUtil.getTextDescription(request, "DOC_RECEIVE")%></td>
					<td width="10%"><%=MessageResourceUtil.getTextDescription(request, "DOC_OVERRIDE")%></td>
					<td width="10%"><%=MessageResourceUtil.getTextDescription(request, "DOC_TRACKING")%></td>
					<td width="20%"><%=MessageResourceUtil.getTextDescription(request, "DOC_REASON")%></td>
					<td width="15%"><%=MessageResourceUtil.getTextDescription(request, "DOC_OFFER")%></td>
				</tr>
		</table>
		
		<!-- Other Doc -->
		<div id="div-otherdoc-nev">		
			<div class="div-otherdoc-popup">
			<table class="TableFrame" id="table-other-doc">			
				<%
				if(!OrigUtil.isEmptyVector(docOtherVect)){								
						String styleTr = null;					
						for(int i=0; i<docOtherVect.size(); i++){																	
							PLDocumentCheckListDataM docListM = (PLDocumentCheckListDataM) docOtherVect.get(i);																																		
							styleTr = (i%2==0)?"ResultEven":"ResultOdd";
						if(HTMLRenderUtil.RadioBoxCompare.TrackDoc.equals(docListM.getReceive())){
							styleTr += " ResultBlue";
						}	
			   %>	
						<tr id="<%=docListM.getDocCode()%>" class="Result-Obj <%=styleTr%>" valign="top">
							<td width="5px"><div class="text-left"><%=HTMLRenderUtil.displayCheckBoxValues(docListM.getDocCode(),"otherdoc-checkbox",HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,"checkBoxAll otherdoc-checkbox" ,"")%></div></td>
							<td width="25%"><div class="text-left"><%=HTMLRenderUtil.displayHTML((String) docListM.getDocDesc())%></div></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayModeOtherDoc,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc,"")%></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayModeOtherDoc,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.ReceiveDoc,"")%></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayModeOtherDoc,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.OverrideDoc,"")%></td>
							<td width="10%"><%=HTMLRenderUtil.displayRadioDoc(docListM.getReceive(),displayModeOtherDoc,"radio_"+docListM.getDocCode(),HTMLRenderUtil.RadioBoxCompare.TrackDoc,"")%></td>
							<td width="20%">
								<%
									if(!OrigUtil.isEmptyVector(docListM.getDocCkReasonVect())){
								%>
									<table cellspacing=0 cellpadding=0 width="100%" border="0">
										<%
											for(PLDocumentCheckListReasonDataM docReasonM :docListM.getDocCkReasonVect()){
										%>
											<tr>
												<td width="5px">											
												<%=HTMLRenderUtil.displayCheckBox(docReasonM.getIsDocReason(),"checkBox_"+docReasonM.getDocCode()+"_"+docReasonM.getDocReasonID(),HTMLRenderUtil.COMPARE_CHECKBOX_VALUE, displayModeOtherDoc,"")%>
												</td>
												<td align="left"><div class="text-left"><%=HTMLRenderUtil.displayHTML(DocumentCacheTool.getDocumentReasonByCode(docReasonM.getDocCode(),docReasonM.getDocReasonID()))%></div></td>
											</tr>
										<%}%>
									</table>
								<%}%>
							</td>
							<td width="15%">
								<%=HTMLRenderUtil.displayInputTextAreaTag(HTMLRenderUtil.replaceNull(docListM.getRemark()),"remark_"+docListM.getDocCode(),"3","20",displayModeOtherDoc,250)%>	
							</td>
						</tr>																																		
					<%} %>
					<%}else{%>
						<tr class="ResultNotFound" id="doclist-notfound">
							<td colspan="8" >No record found</td>
						</tr>
					<%} %>					
			</table>
			</div>
		</div>
		<!-- Other Doc -->
			
		<div class="PanelValueList">			
			<table class="FormFrame">
				<tr>
					<td class="textL">
						<%=HTMLRenderUtil.displayButtonTagScriptAction("delete",displayModeOtherDoc,"button","doc-delete-button", "button", "onclick=\"deleteOtherDoc()\"", "") %>														
					</td>
				</tr>	
			</table>
		</div>				
				
	</div>			
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td width="40%" colspan="3" class="textR"><%=MessageResourceUtil.getTextDescription(request, "DOC_FINAL_STAUS")%>&nbsp;:&nbsp;</td>
						<td colspan="5" class="textL">
							<%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),65,appM.getDocListResultCode(),"doc-final-status",displayMode,"") %>
						</td>
					</tr>
					<tr>
						<td width="40%" colspan="3" class="textR"><%=MessageResourceUtil.getTextDescription(request, "DOC_MEMO")%>&nbsp;:&nbsp;</td>
						<td colspan="5" class="textL">
							<%=HTMLRenderUtil.displayInputTextAreaTag(HTMLRenderUtil.replaceNull(appM.getDocListNotepad()),"doc-demo","10","70",displayMode,200)%>	
						</td>
					</tr>
				</table>
			</div>						
		</div>
	</div>
</div>
<%
	ORIGCacheUtil cache = new ORIGCacheUtil();
	String sensitive = cache.getGeneralParamByCode("SENSITIVE_DOC_CODE");
%>
<%=HTMLRenderUtil.displayHiddenField(sensitive,"sensitive-doccode") %>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>