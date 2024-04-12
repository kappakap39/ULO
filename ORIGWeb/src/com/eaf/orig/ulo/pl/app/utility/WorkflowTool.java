package com.eaf.orig.ulo.pl.app.utility;

import java.io.StringReader;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.service.ServiceLocatorException;
//import com.eaf.orig.shared.util.ORIGLogic;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;
import com.eaf.orig.ulo.pl.model.app.ORIGJobDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesHRVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPhoneVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

/**
 * @author Sankom, Modify SeptemWi
 */
public class WorkflowTool {

	static Logger logger = Logger.getLogger(WorkflowTool.class);
	
	public static String TYPE_REJECT = "TYPE_REJECT";
		
	public WorkflowTool() {
		super();
	}

	public WorkflowDataM MapWorkflowDataM(String userName, String role, String tdid, String atPage, String itemPerPage, String totalItem, boolean isNextPage, String queueType) {
		WorkflowDataM wfM = new WorkflowDataM();
		wfM.setUserID(userName);
		wfM.setUserRole(role);
		wfM.setTdID(tdid);
		wfM.setAtPage(atPage);
		wfM.setItemPerPage(itemPerPage);
		wfM.setTotalItem(totalItem);
		wfM.setNextPage(isNextPage);
		wfM.setQueueType(queueType);
		return wfM;
	}

	public ORIGInboxDataM SearchWorkQueue(HttpServletRequest request, UserDetailM userM, ProcessMenuM menuM) throws Exception {
		
		ORIGInboxDataM oInboxM = new ORIGInboxDataM();
		String itemPerPage = request.getParameter("itemsPerPage");
		String atPage = request.getParameter("atPage");
		String totalItem = request.getParameter("totalItem");
		String tdId = request.getParameter("MenuID");
		String queueType = request.getParameter("queueType");
		String nextPage = request.getParameter("isNextPage");
		if (OrigUtil.isEmptyString(nextPage))
			nextPage = "false";
		Boolean isNextPage = Boolean.parseBoolean(nextPage);
		String userName = userM.getUserName();
		String role = userM.getCurrentRole();

		if (OrigUtil.isEmptyString(tdId)) {
			tdId = menuM.getMenuID();
		}
		if (OrigUtil.isEmptyString(itemPerPage)) {
			itemPerPage = "20";
		}
		WorkflowDataM wfM = this.MapWorkflowDataM(userName, role, tdId, atPage,
				itemPerPage, totalItem, isNextPage, queueType);
		try {
			BpmWorkflow bpmWf = BpmProxyService.getBpmWorkflow();
			String wfXML = bpmWf.SearchworkQueue(wfM, this.getConnection());
			this.ConvertWfXMLModel(wfXML, oInboxM, request);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return oInboxM;
	}

	public void ConvertWfXMLModel(String wfXML, ORIGInboxDataM oInboxM, HttpServletRequest request) {
		if (OrigUtil.isEmptyString(wfXML))
			return;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(wfXML)));
			this.GetTagSummary(doc, oInboxM);
			this.GetNodeJob(doc, oInboxM, request);
		} catch (Exception e) {
			logger.fatal("Error ", e);
		}
	}

	public void GetTagSummary(Document doc, ORIGInboxDataM oInboxM) {
		NodeList summary = doc.getElementsByTagName("summary");
		if (null != summary) {
			for (int i = 0; i < summary.getLength(); i++) {
				Element element = (Element) summary.item(i);
				oInboxM.setPageNo(OrigUtil.stringToInt(getCharacterDataFromTagName(element, "page-no")));
				oInboxM.setTotalPage(OrigUtil.stringToInt(getCharacterDataFromTagName(element, "total-page")));
				oInboxM.setTotalJob(OrigUtil.stringToInt(getCharacterDataFromTagName(element, "total-job")));
				oInboxM.setNextPage(Boolean.parseBoolean(getCharacterDataFromTagName(element, "nextpage")));
				oInboxM.setItemPerPage(OrigUtil.stringToInt(getCharacterDataFromTagName(element, "item-per-page")));
			}
		}
	}

	public void GetNodeJob(Document doc, ORIGInboxDataM oInboxM, HttpServletRequest request) throws Exception {
		NodeList job = doc.getElementsByTagName("job");
		ORIGJobDataM oJobM = null;
		if (null != job && job.getLength() > 0) {
			for (int i = 0; i < job.getLength(); i++) {
				Element elementjob = (Element) job.item(i);
				oJobM = new ORIGJobDataM();
				oJobM.setJobID(getCharacterDataFromTagName(elementjob, "job-id"));
				oJobM.setAppRecordID(getCharacterDataFromTagName(elementjob, "app-record-id"));
				oJobM.setJobState(getCharacterDataFromTagName(elementjob, "job-state"));
				oJobM.setOwner(getCharacterDataFromTagName(elementjob, "owner"));
				oJobM.setRoleID(getCharacterDataFromTagName(elementjob, "role-id"));
				oJobM.setProcessState(getCharacterDataFromTagName(elementjob, "process-state"));
				oJobM.setProcessTempleteID(getCharacterDataFromTagName(elementjob, "process-template-id"));
				oJobM.setBusClass(getCharacterDataFromTagName(elementjob, "busclass-id"));
				oJobM.setAppStatus(this.GetMessageAppStatus(request, getCharacterDataFromTagName(elementjob, "app-status")));
				oJobM.setAppDate(DataFormatUtility.StringDateENToStringDateTH(getCharacterDataFromTagName(elementjob, "app-date")));
				oJobM.setStartQueueDate(DataFormatUtility.StringDateENToStringDateTH(getCharacterDataFromTagName(elementjob, "start-queue-date")));
				oJobM.setCurrentStateDate(DataFormatUtility.StringDateENToStringDateTH(getCharacterDataFromTagName(elementjob, "current-state-date")));
				oJobM.setNewStateBy(getCharacterDataFromTagName(elementjob, "current-state-by"));
				this.SetRemainUserTime(getCharacterDataFromTagName(elementjob, "remain-usertime"), oJobM);
				this.SetRemainApplicationTime(getCharacterDataFromTagName(elementjob, "remain-apptime"), oJobM);
				oJobM.setPriority(GetMessagePriority(request, getCharacterDataFromTagName(elementjob, "priority")));
				oJobM.setActivityState(getCharacterDataFromTagName(elementjob, "activity-state"));
				oJobM.setClaimedBy(getCharacterDataFromTagName(elementjob, "claimed-by"));
				oJobM.setClaimedDate(DataFormatUtility.StringDateENToStringDateTH(getCharacterDataFromTagName(elementjob, "claimed-date")));
				oJobM.setJobVersion(getCharacterDataFromTagName(elementjob, "job-version"));
				oJobM.setJobStatus(getCharacterDataFromTagName(elementjob, "job-status"));
				oJobM.setAppTime(getCharacterDataFromTagName(elementjob, "app-time"));
				oJobM.setUserTime(getCharacterDataFromTagName(elementjob, "user-time"));
				oJobM.setInfoJobType(getCharacterDataFromTagName(elementjob, "job-type"));
				oJobM.setInfoSaleType(getCharacterDataFromTagName(elementjob, "sale-type"));
				oJobM.setInfoCashDayType(getCharacterDataFromTagName(elementjob, "cash-type"));
				this.GetNodAppInfo(elementjob, oJobM, request);
				oInboxM.add(oJobM);
			}
		}
	}

	public void GetNodAppInfo(Element elementjob, ORIGJobDataM oJobM, HttpServletRequest request) throws Exception {
		
		NodeList appInfo = elementjob.getElementsByTagName("app-info");
		if (null != appInfo && appInfo.getLength() > 0) {
			Element elementAppInfo = (Element) appInfo.item(0);
			oJobM.setInfoAppRecrodId(getCharacterDataFromTagName(elementAppInfo, "app-record-id"));
			oJobM.setInfoAppStatus(getCharacterDataFromTagName(elementAppInfo, "app-status"));
			oJobM.setInfoTitleName(getCharacterDataFromTagName(elementAppInfo, "title-name"));
			oJobM.setInfoFirstName(getCharacterDataFromTagName(elementAppInfo, "first-name"));
			oJobM.setInfoLastName(getCharacterDataFromTagName(elementAppInfo, "last-name"));
			try {
				oJobM.setInfoCreateDate(DataFormatUtility.stringToDate(getCharacterDataFromTagName(elementAppInfo, "create-date"), "dd/MM/yyyy "));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			oJobM.setInfoIdno(getCharacterDataFromTagName(elementAppInfo, "id-no"));
			oJobM.setInfoBussClass(getCharacterDataFromTagName(elementAppInfo, "bus-class"));
			oJobM.setInfoProductName(getCharacterDataFromTagName(elementAppInfo, "product-name"));
			oJobM.setSla(getCharacterDataFromTagName(elementAppInfo, "app-sla"));
			oJobM.setInfoAppNo(getCharacterDataFromTagName(elementAppInfo, "app-no"));
			oJobM.setInfoApplicationDate(DataFormatUtility.stringToDate(getCharacterDataFromTagName(elementAppInfo, "app-date"), "dd/MM/yyyy HH:mm:ss"));
			oJobM.setInfoVetoFlag(this.GetMessageVetoFlag(request, this.getCharacterDataFromTagName(elementAppInfo, "veto-flag")));
			oJobM.setInfoUpdateDate(DataFormatUtility.stringToDate(getCharacterDataFromTagName(elementAppInfo, "update-date"), "dd/MM/yyyy HH:mm:ss"));
			oJobM.setInfoUpdateBy(getCharacterDataFromTagName(elementAppInfo, "update-by"));
			oJobM.setInfoNcbRequester(getCharacterDataFromTagName(elementAppInfo, "ncb-requester"));
			oJobM.setInfoNcbApprover(getCharacterDataFromTagName(elementAppInfo, "ncb-approver"));
			oJobM.setInfoNcbResult(getCharacterDataFromTagName(elementAppInfo, "ncb-result"));
			String ncbFlag = getCharacterDataFromTagName(elementAppInfo, "ncb-flag");
			if(OrigUtil.isEmptyString(ncbFlag)){
				ncbFlag = NCBConstant.ncbFlag.NO;
			}
			oJobM.setInfoNcbFlag(ncbFlag);
			oJobM.setInfoNcbSupComment(getCharacterDataFromTagName(elementAppInfo, "ncb-comemnt"));
			try {
				oJobM.setInfoBirthDate(DataFormatUtility.stringToDate(getCharacterDataFromTagName(elementAppInfo, "birth-date"), "dd/mm/yyyy"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			oJobM.setInfoJobType(getCharacterDataFromTagName(elementAppInfo, "job-type"));
			oJobM.setInfoSaleType(getCharacterDataFromTagName(elementAppInfo, "sale-type"));
			oJobM.setInfoCashDayType(getCharacterDataFromTagName(elementAppInfo, "cash-type"));
			String callHrFlag = "No";
			Date currentDate = new Date();
			try {
				String callHRDateStr = getCharacterDataFromTagName(elementAppInfo, "call_hr_date");
				if (!"".equals(callHRDateStr)) {
					Date callHRDate = DataFormatUtility.stringToDate(callHRDateStr, "dd/mm/yyyy");
					if (callHRDate != null && DateUtils.isSameDay(currentDate, callHRDate)) {
						callHrFlag = "Yes";
					}
				}
			} catch (Exception e) {
				logger.error("error", e);
			}
			oJobM.setCallHrFlag(callHrFlag);
			String callCustFlag = "No";
			try {

				String callCustDateStr = getCharacterDataFromTagName(elementAppInfo, "call_cust_date");
				if (!"".equals(callCustDateStr)) {
					Date calCustdate = DataFormatUtility.stringToDate(callCustDateStr, "dd/mm/yyyy");
					if (calCustdate != null && DateUtils.isSameDay(currentDate, calCustdate)) {
						callCustFlag = "Yes";
					}
				}
			} catch (Exception e) {
				logger.error("error", e);
			}
			oJobM.setCallCustFlag(callCustFlag);
			oJobM.setInfoAppReason(getCharacterDataFromTagName(elementAppInfo, "app-reason"));
			oJobM.setInfoNcbJobType(this.getTextDescription(request, (getCharacterDataFromTagName(elementAppInfo, "ncb-job-type"))));
			oJobM.setInfoICDCFlag(getCharacterDataFromTagName(elementAppInfo, "icdc-flag"));//Praisan CR25 20130509
		}
	}

	private Connection getConnection() {
		try {
			OrigServiceLocator origService = OrigServiceLocator.getInstance();
			return origService.getConnection(OrigServiceLocator.WORKFLOW_DB);
		} catch (ServiceLocatorException e) {
			logger.fatal("Error ", e);
		}
		return null;
	}

	public String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	public String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public static String getDataFromTagName(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public String getCharacterDataFromTagName(Element element, String tagName) {
		NodeList node = element.getElementsByTagName(tagName);
		if (null != node && node.getLength() > 0) {
			Element elementInData = (Element) node.item(0);
			return getCharacterDataFromElement(elementInData);
		}
		return "";
	}

	public static String getApplicatonXML(PLApplicationDataM applicationM) {
		
		StringBuilder strXML = new StringBuilder();
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLCashTransferDataM cashM = applicationM.getCashTransfer();
		if (cashM == null){
			cashM = new PLCashTransferDataM();
		}

		Vector<PLReasonDataM> reasonVect = applicationM.getReasonVect();
		if (OrigUtil.isEmptyObject(reasonVect)){
			reasonVect = new Vector<PLReasonDataM>();
			applicationM.setReasonVect(reasonVect);
		}
				
		PLXRulesVerificationResultDataM xruleVerM = personalM.getXrulesVerification();
		if (xruleVerM == null){
			xruleVerM = new PLXRulesVerificationResultDataM();
		}

		Date verCusDate = null;
		Date verHrDate = null;		
		Vector<PLXRulesPhoneVerificationDataM> phoneVerVect = xruleVerM.getVXRulesPhoneVerificationDataM();
		if(!OrigUtil.isEmptyVector(phoneVerVect)){
			PLXRulesPhoneVerificationDataM phoneVerifyM = phoneVerVect.lastElement();
			if(null != phoneVerifyM){
				verCusDate = phoneVerifyM.getCreateDate();
			}
		}
		
		Vector<PLXRulesHRVerificationDataM> verHRVect = xruleVerM.getxRulesHRVerificationDataMs();
		if(!OrigUtil.isEmptyVector(verHRVect)){
			PLXRulesHRVerificationDataM verHrM = verHRVect.lastElement();
			if(null != verHrM){
				verHrDate = verHrM.getCreateDate();
			}
		}
				
		strXML.append("<?xml version=\"1.0\" ?>");
		strXML.append("<application-info>");
		strXML.append("<app-record-id>").append(HTMLRenderUtil.replaceNull(applicationM.getAppRecordID())).append("</app-record-id>");
		strXML.append("<app-status>").append(HTMLRenderUtil.replaceNull(applicationM.getApplicationStatus())).append("</app-status>");
		strXML.append("<title-name>").append(HTMLRenderUtil.replaceNull(personalM.getThaiTitleName())).append("</title-name>");
		strXML.append("<first-name>").append(HTMLRenderUtil.replaceNull(personalM.getThaiFirstName())).append("</first-name>");
		strXML.append("<last-name>").append(HTMLRenderUtil.replaceNull(personalM.getThaiLastName())).append("</last-name>");
		strXML.append("<create-date>").append(DataFormatUtility.dateTimetoStringForEN((java.util.Date) applicationM.getCreateDate())).append("</create-date>");
		strXML.append("<id-no>").append(HTMLRenderUtil.replaceNull(personalM.getIdNo())).append("</id-no>");
		strXML.append("<bus-class>").append(HTMLRenderUtil.replaceNull(applicationM.getBusinessClassId())).append("</bus-class>");
		strXML.append("<product-name>").append(HTMLRenderUtil.replaceNull(SetProduct(applicationM.getBusinessClassId()))).append("</product-name>");
		strXML.append("<app-sla>").append("").append("</app-sla>");
		strXML.append("<app-no>").append(HTMLRenderUtil.replaceNull(applicationM.getApplicationNo())).append("</app-no>");
		strXML.append("<app-date>").append(DataFormatUtility.dateTimetoStringForEN(applicationM.getAppDate())).append("</app-date>");
		strXML.append("<veto-flag>").append(HTMLRenderUtil.replaceNull(applicationM.getReopenFlag())).append("</veto-flag>");
		strXML.append("<update-date>").append(DataFormatUtility.dateTimetoStringForEN(applicationM.getUpdateDate())).append("</update-date>");
		strXML.append("<update-by>").append(HTMLRenderUtil.replaceNull(applicationM.getUpdateBy())).append("</update-by>");
		strXML.append("<ncb-requester>").append(HTMLRenderUtil.replaceNull(xruleVerM.getNCBRequester())).append("</ncb-requester>");
		strXML.append("<ncb-approver>").append(HTMLRenderUtil.replaceNull(xruleVerM.getNcbRQapprover())).append("</ncb-approver>");
		strXML.append("<ncb-result>").append(HTMLRenderUtil.replaceNull(xruleVerM.getNCBResult())).append("</ncb-result>");
		strXML.append("<app-reason>").append(HTMLRenderUtil.replaceNull(SetReason(reasonVect))).append("</app-reason>");
		strXML.append("<ncb-flag>").append(HTMLRenderUtil.replaceNull(SetNcbFlag(xruleVerM.getNCBCode()))).append("</ncb-flag>");
		strXML.append("<ncb-comemnt>").append(HTMLRenderUtil.replaceNull(xruleVerM.getNcbSupComment())).append("</ncb-comemnt>");
		strXML.append("<birth-date>").append(DataFormatUtility.dateTimetoStringForEN((java.util.Date) personalM.getBirthDate())).append("</birth-date>");
		strXML.append("<job-type>").append(HTMLRenderUtil.replaceNull(applicationM.getJobType())).append("</job-type>");
		strXML.append("<sale-type>").append(HTMLRenderUtil.replaceNull(applicationM.getSaleType())).append("</sale-type>");
		strXML.append("<cash-type>").append(HTMLRenderUtil.replaceNull(cashM.getCashTransferType())).append("</cash-type>");
		strXML.append("<call_hr_date>").append(DataFormatUtility.dateEnToStringDateEn((Date) verHrDate, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)).append("</call_hr_date>");
		strXML.append("<call_cust_date>").append(DataFormatUtility.dateEnToStringDateEn((Date) verCusDate, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S)).append("</call_cust_date>");
		strXML.append("<ncb-job-type>").append(HTMLRenderUtil.replaceNull(SetNcbJobType(xruleVerM.getNCBConsentRefNo()))).append("</ncb-job-type>");
		strXML.append("<icdc-flag>").append(HTMLRenderUtil.replaceNull(applicationM.getICDCFlag())).append("</icdc-flag>");//Praisan CR25 20130509
		strXML.append("</application-info>");

		return strXML.toString();
	}

	public static String SetProduct(String BusClassId) {
		if (!OrigUtil.isEmptyString(BusClassId)) {
			String product[] = BusClassId.split("_");
			return product[1];
		}
		return null;
	}

	public static String SetNcbFlag(String ncbReaultCode) {

		if(!OrigUtil.isEmptyString(ncbReaultCode)){
			if(ncbReaultCode.equals(NCBConstant.ncbResultCode.REQUEST_NCB_DATA)){
				return NCBConstant.ncbFlag.REQUEST_NCB_DATA;
			}else if(ncbReaultCode.equals(NCBConstant.ncbResultCode.SEND_BACK)){
				return NCBConstant.ncbFlag.SEND_BACK;
			}else if(ncbReaultCode.equals(NCBConstant.ncbResultCode.WAITING_NCB_DATA)){
				return NCBConstant.ncbFlag.WAITING_NCB_DATA;
			}else if(ncbReaultCode.equals(NCBConstant.ncbResultCode.DATA_COMPLETED)){
				return NCBConstant.ncbFlag.DATA_COMPLETED;
			}else{
				return NCBConstant.ncbFlag.YES;
			}
		}
		return NCBConstant.ncbFlag.NO;
	}

	public static String SetReason(Vector<PLReasonDataM> reasonVect) {		
//		if (!OrigUtil.isEmptyVector(reasonVect)) {
//			ORIGCacheUtil cacheU = new ORIGCacheUtil();
//			StringBuilder sReason = new StringBuilder("");
//			for (int i = 0; i < reasonVect.size(); i++) {
//				PLReasonDataM reasonM = reasonVect.get(i);
//				if(!OrigUtil.isEmptyString(reasonM.getReasonType()) && !OrigUtil.isEmptyString( reasonM.getReasonCode())){
//					sReason.append(reasonM.getReasonType()+"|"+reasonM.getReasonCode());
//					sReason.append(",");
//				}
//				sReason.append(cacheU.getORIGCacheDisplayNameDataMAllStatus(Integer.parseInt(reasonM.getReasonType()), reasonM.getReasonCode()));				
//			}
//			sReason.deleteCharAt(sReason.length() - 1);
//			return sReason.toString();
//		}
		return "";
	}

	public String GetMessagePriority(HttpServletRequest request, String priority) {
		if (OrigUtil.isEmptyString(priority))
			return "";
		StringBuilder str = new StringBuilder();
		String code = str.append("PRIORITY_").append(priority).toString();
		return this.getTextDescription(request, code.toUpperCase());
	}

	public String GetMessageAppStatus(HttpServletRequest request, String appStatus) {
		if (OrigUtil.isEmptyString(appStatus))
			return "";
		StringBuilder str = new StringBuilder();
		appStatus = appStatus.replace("(", "");
		appStatus = appStatus.replace(")", "");
		String code = str.append("APPSTATUS_").append(appStatus.replace(" ", "")).toString();
		return this.getTextDescription(request, code.toUpperCase());
	}

	public String GetMessageVetoFlag(HttpServletRequest request, String vetoFlag) {
		if (OrigUtil.isEmptyString(vetoFlag))
			vetoFlag = "N";
		StringBuilder str = new StringBuilder();
		String code = str.append("VETO_").append(vetoFlag).toString();
		return this.getTextDescription(request, code.toUpperCase());
	}

	public String GetMessageNcbStatus(HttpServletRequest request, String ncbStatus) {
		if (OrigUtil.isEmptyString(ncbStatus))
			return "";
		StringBuilder str = new StringBuilder();
		String code = str.append("NCBSTATUS_").append(ncbStatus).toString();
		return this.getTextDescription(request, code.toUpperCase());
	}

	public String getTextDescription(HttpServletRequest request, String textCode) {
		Locale locale = Locale.getDefault();
		Locale currentLocale = (Locale) (request.getSession().getAttribute("LOCALE"));
		if (currentLocale != null)
			locale = currentLocale;
		ResourceBundle resource = ResourceBundle.getBundle(locale + "/properties/getMessage", locale);
		if (textCode != null && !textCode.equals("")) {
			try {
				return resource.getString(textCode);
			} catch (Exception e) {
				return "";
			}
		}
		return "";
	}

	public void SetRemainUserTime(String remainUserTime, ORIGJobDataM oJobM) {
		if (OrigUtil.isEmptyString(remainUserTime))
			return;
		String[] obj = remainUserTime.split("\\|");
		oJobM.setFlagUserTime(obj[0]);
		oJobM.setRemainUserTime(obj[1]);
	}

	public void SetRemainApplicationTime(String remainAppTime, ORIGJobDataM oJobM) {
		if (OrigUtil.isEmptyString(remainAppTime))
			return;
		String[] obj = remainAppTime.split("\\|");
		oJobM.setFlagAppTime(obj[0]);
		oJobM.setRemainAppTime(obj[1]);
	}
	
	public static String SetNcbJobType(String consentRefNo) {
		if(!OrigUtil.isEmptyString(consentRefNo)){
			return "NCB_EDIT_JOB";
		}
		return "NCB_NEW_JOB";
	}
	public static String displayReasonWf(String reason,String type){
		StringBuilder STR = new StringBuilder("");
		if(!OrigUtil.isEmptyString(reason)){
			String [] object = reason.split("\\,");
			if(null != object && object.length > 0){
				ORIGCacheUtil cacheU = new ORIGCacheUtil();
				for(String _reasonobject : object){
					if(null != _reasonobject){
						String [] _reason = _reasonobject.split("\\|");
						if(null != _reason && _reason.length == 2){
							String reason_type = _reason[0];
							String reason_code = _reason[1];
							if(findTypeReason(type,reason_type)){
								STR.append(cacheU.getORIGCacheDisplayNameDataMAllStatus(Integer.parseInt(reason_type),reason_code));
								STR.append(" ,");
							}							
						}
					}					
				}
				if(null != STR && STR.length() > 0){
					STR.deleteCharAt(STR.length()-1);
				}
			}
		}
		return STR.toString();
	}
	public static boolean findTypeReason(String type ,String reason_type){
//		if(TYPE_REJECT.equals(type)){
//			if(ORIGLogic.isRejectReason(reason_type)){
//				return true;
//			}
//		}
		return false;
	}
}
