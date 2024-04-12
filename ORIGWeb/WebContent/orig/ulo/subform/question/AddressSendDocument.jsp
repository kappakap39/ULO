<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>

<script type="text/javascript" src="orig/ulo/subform/question/js/AddressSendDocument.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="CacheControl" scope="session" class="com.eaf.core.ulo.common.properties.CacheControl"/>

<%
	int PERSONAL_SEQ=1;
		Logger logger = Logger.getLogger(this.getClass());
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		String QuestionSet =SystemConstant.getConstant("QUESTION_SET_A");
		String FIELD_ID_SEND_DOC = SystemConstant.getConstant("FIELD_ID_SEND_DOC");
		String FLAG_ENABLED = SystemConstant.getConstant("FLAG_ENABLED");
		String personalId =request.getParameter("PERSONAL_ID");
		String SEND_DOCUMENT_ADDRESS_QUESTION = SystemConstant.getConstant("SEND_DOCUMENT_ADDRESS_QUESTION");
		PersonalInfoDataM personalInfo	 = applicationGroup.getPersonalInfoId(personalId);
 		int seqNo = 0;
		String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
		
		String SUB_FOMR_ID ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
		FormUtil formUtil = new FormUtil(SUB_FOMR_ID,request);
		String displayMode =FormDisplayModeUtil.getDisplayMode("", "", formUtil);
%>	
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.dropdown("ADDRESS_SEND_DOCUMENT", personalInfo.getPersonalId(), "ADDRESS_SEND_DOCUMENT", "SEND_DOC", "PLACE_RECEIVE_CARD_"+personalInfo.getPersonalType(),
					 personalInfo.getPlaceReceiveCard(), "", FIELD_ID_SEND_DOC, "ACTIVE", "", "col-sm-8 col-md-7", formUtil)%>
				<%=HtmlUtil.button("EDIT_SEND_DOC",personalId, "EDIT_BTN", displayMode, "", HtmlUtil.elementTagId("EDIT_SEND_DOC"), request) %>
				<%=HtmlUtil.hidden("QUESTION_NO_"+personalId, SEND_DOCUMENT_ADDRESS_QUESTION) %>
				<%=HtmlUtil.hidden("PERSONAL_ID_"+personalId, personalInfo.getPersonalId()) %>
				<%=HtmlUtil.hidden("PERSONAL_TYPE_"+personalId, personalInfo.getPersonalType()) %>
				<%=HtmlUtil.hidden("PERSONAL_SEQ_"+personalId, String.valueOf(personalInfo.getSeq())) %>
			</div>
		</div>
