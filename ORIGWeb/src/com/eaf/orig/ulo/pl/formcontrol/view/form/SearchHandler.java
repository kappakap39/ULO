package com.eaf.orig.ulo.pl.formcontrol.view.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;

public class SearchHandler implements Serializable,Cloneable{
		
	public SearchHandler(){
		super();
	}
	private JSONArray errorFields;
	private SearchDataM searchM;
	private String errorMSG;
	private ArrayList<ErrorDataM> errorList;
	
	public void PushErrorMessage(String fieldId ,String message){
		if(this.errorFields == null) this.errorFields = new JSONArray();
//			JSONObject jsonObject = new JSONObject();
//				jsonObject.put("id", fieldId);
//				jsonObject.put("value", message);
//		this.errorFields.put(jsonObject);
	}
	public void DestoryErrorFields(){
		this.errorFields = new JSONArray();
	}
	public String GetErrorFields(){
		if(this.errorFields == null) this.errorFields = new JSONArray();
		return this.errorFields.toString();
	}
	
	public JSONArray getErrorFields() {
		return errorFields;
	}
	public void setErrorFields(JSONArray errorFields) {
		this.errorFields = errorFields;
	}
	
	public SearchDataM getSearchM() {
		return searchM;
	}
	public void setSearchM(SearchDataM searchM) {
		this.searchM = searchM;
	}
	
	public String getErrorMessage() {
		StringBuilder strB = new StringBuilder("");
//		if (errorFields != null && errorFields.length() > 0) {
//			for (int i = 0; i < errorFields.length(); i++) {
//				JSONObject jObj = errorFields.getJSONObject(i);
//				strB.append(" " + jObj.get("value") + ",");
//			}
//			if (errorFields.length() >= 1) {
//				strB.deleteCharAt(strB.length() - 1);
//			}
//		}
		return strB.toString();
	}
	
	public static class SearchDataM implements Serializable,Cloneable{
		
		public SearchDataM(){
			super();
		}
		
	    private Date faxInDateFrom;
	    private Date faxInDateTo;
	    private String requestID;
	    private String customerType;
	    private String dealerCode;
	  //  private String dealerFax;
	    private String customerName;
	    private String customerLName;
	    private String channel;
	    private String citizenID;
	    private String sortBy;
	    private String applicationNo;
	    private String registerNo;
	    private String priority;
	    private String escalateBy;
	    private String groupID;
	    
	    private String co_citizenID;
	    private String co_customerFName;
	    private String co_customerLName;
	    
	    private String owner;
	    private String productCode;
	    private String projectCode;
	    private String overSLA;
	    private String saleType;
	    private String statusLogOn;
	    private String statusOnJob;
	    
	    private String cardNo;
	    private String cardRefNo;
	    private String cardStatus;
	    private Date date;
	    private String stringDate;
	    private String refNo;
	    
	    private String empFirstName;
	    private String empLastName;
	    private String empId;
	    private String SetPriority;
	    private String onJob;
	    private String logOn;
	    private String prefixErrorMsg;
	    private String suffixErrorMsg;
	    
	    private PLAccountCardDataM accCardDataM;
	    private String appRecId;
	    private String comment;
	    private String role;
	    private String groupRole;
	    
		public Date getFaxInDateFrom() {
			return faxInDateFrom;
		}
		public void setFaxInDateFrom(Date faxInDateFrom) {
			this.faxInDateFrom = faxInDateFrom;
		}
		public Date getFaxInDateTo() {
			return faxInDateTo;
		}
		public void setFaxInDateTo(Date faxInDateTo) {
			this.faxInDateTo = faxInDateTo;
		}
		public String getRequestID() {
			return requestID;
		}
		public void setRequestID(String requestID) {
			this.requestID = requestID;
		}
		public String getCustomerType() {
			return customerType;
		}
		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}
		public String getDealerCode() {
			return dealerCode;
		}
		public void setDealerCode(String dealerCode) {
			this.dealerCode = dealerCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getCustomerLName() {
			return customerLName;
		}
		public void setCustomerLName(String customerLName) {
			this.customerLName = customerLName;
		}
		public String getChannel() {
			return channel;
		}
		public void setChannel(String channel) {
			this.channel = channel;
		}
		public String getCitizenID() {
			return citizenID;
		}
		public void setCitizenID(String citizenID) {
			this.citizenID = citizenID;
		}
		public String getSortBy() {
			return sortBy;
		}
		public void setSortBy(String sortBy) {
			this.sortBy = sortBy;
		}
		public String getApplicationNo() {
			return applicationNo;
		}
		public void setApplicationNo(String applicationNo) {
			this.applicationNo = applicationNo;
		}
		public String getRegisterNo() {
			return registerNo;
		}
		public void setRegisterNo(String registerNo) {
			this.registerNo = registerNo;
		}
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}
		public String getEscalateBy() {
			return escalateBy;
		}
		public void setEscalateBy(String escalateBy) {
			this.escalateBy = escalateBy;
		}
		public String getGroupID() {
			return groupID;
		}
		public void setGroupID(String groupID) {
			this.groupID = groupID;
		}
		public String getCo_citizenID() {
			return co_citizenID;
		}
		public void setCo_citizenID(String co_citizenID) {
			this.co_citizenID = co_citizenID;
		}
		public String getCo_customerFName() {
			return co_customerFName;
		}
		public void setCo_customerFName(String co_customerFName) {
			this.co_customerFName = co_customerFName;
		}
		public String getCo_customerLName() {
			return co_customerLName;
		}
		public void setCo_customerLName(String co_customerLName) {
			this.co_customerLName = co_customerLName;
		}
		public String getOwner() {
			return owner;
		}
		public void setOwner(String owner) {
			this.owner = owner;
		}
		public String getProductCode() {
			return productCode;
		}
		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}
		public String getProjectCode() {
			return projectCode;
		}
		public void setProjectCode(String projectCode) {
			this.projectCode = projectCode;
		}
		public String getOverSLA() {
			return overSLA;
		}
		public void setOverSLA(String overSLA) {
			this.overSLA = overSLA;
		}
		public String getSaleType() {
			return saleType;
		}
		public void setSaleType(String saleType) {
			this.saleType = saleType;
		}
		public String getStatusLogOn() {
			return statusLogOn;
		}
		public void setStatusLogOn(String statusLogOn) {
			this.statusLogOn = statusLogOn;
		}
		public String getStatusOnJob() {
			return statusOnJob;
		}
		public void setStatusOnJob(String statusOnJob) {
			this.statusOnJob = statusOnJob;
		}
		public String getCardNo() {
			return cardNo;
		}
		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}
		public String getCardRefNo() {
			return cardRefNo;
		}
		public void setCardRefNo(String cardRefNo) {
			this.cardRefNo = cardRefNo;
		}
		public String getCardStatus() {
			return cardStatus;
		}
		public void setCardStatus(String cardStatus) {
			this.cardStatus = cardStatus;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public String getStringDate() {
			return stringDate;
		}
		public void setStringDate(String stringDate) {
			this.stringDate = stringDate;
		}
		public String getRefNo() {
			return refNo;
		}
		public void setRefNo(String refNo) {
			this.refNo = refNo;
		}
		public String getEmpFirstName() {
			return empFirstName;
		}
		public void setEmpFirstName(String empFirstName) {
			this.empFirstName = empFirstName;
		}
		public String getEmpLastName() {
			return empLastName;
		}
		public void setEmpLastName(String empLastName) {
			this.empLastName = empLastName;
		}
		public String getEmpId() {
			return empId;
		}
		public void setEmpId(String empId) {
			this.empId = empId;
		}
		public String getSetPriority() {
			return SetPriority;
		}
		public void setSetPriority(String setPriority) {
			SetPriority = setPriority;
		}
		public String getOnJob() {
			return onJob;
		}
		public void setOnJob(String onJob) {
			this.onJob = onJob;
		}
		public String getLogOn() {
			return logOn;
		}
		public void setLogOn(String logOn) {
			this.logOn = logOn;
		}
		public String getPrefixErrorMsg() {
			return prefixErrorMsg;
		}
		public void setPrefixErrorMsg(String prefixErrorMsg) {
			this.prefixErrorMsg = prefixErrorMsg;
		}
		public String getSuffixErrorMsg() {
			return suffixErrorMsg;
		}
		public void setSuffixErrorMsg(String suffixErrorMsg) {
			this.suffixErrorMsg = suffixErrorMsg;
		}
		public PLAccountCardDataM getAccCardDataM() {
			return accCardDataM;
		}
		public void setAccCardDataM(PLAccountCardDataM accCardDataM) {
			this.accCardDataM = accCardDataM;
		}
		public String getAppRecId() {
			return appRecId;
		}
		public void setAppRecId(String appRecId) {
			this.appRecId = appRecId;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getRole(){
			return role;
		}
		public void setRole(String role){
			this.role = role;
		}
		public String getGroupRole() {
			return groupRole;
		}
		public void setGroupRole(String groupRole) {
			this.groupRole = groupRole;
		}
	}
	
	public static String DisplayErrorMessage(HttpServletRequest request){
		StringBuilder str = new StringBuilder("");
		SearchHandler HandlerM = (SearchHandler) request.getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		if(!OrigUtil.isEmptyString(HandlerM.getErrorMSG())){
			str.append("<div class='div-error-mandatory' >");
			str.append(HandlerM.getErrorMSG());
			str.append("</div>");	
		}
		HandlerM.setErrorMSG("");
		HandlerM.setErrorList(null);
		return str.toString();
	}
	public static void PushErrorMessage(HttpServletRequest request ,String message){
		SearchHandler HandlerM = (SearchHandler) request.getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		HandlerM.setErrorMSG(message);
		request.getSession(true).setAttribute("SEARCH_DATAM", HandlerM);
	}
	public void GenarateErrorMessage(HttpServletRequest request ,String prefix){
		SearchHandler HandlerM = (SearchHandler) request.getSession(true).getAttribute("SEARCH_DATAM");
		if(null == HandlerM){
			HandlerM = new SearchHandler();
		}
		StringBuilder str = new StringBuilder();
		if(this.errorList != null){
			for(ErrorDataM msg : this.errorList){
				str.append("<div>");
				str.append(prefix);
				str.append(" ");
				str.append(msg.getId());
				str.append(" ");
				str.append(msg.getMessage());
				str.append("</div>");
			}
		}
		HandlerM.setErrorMSG(str.toString());
		request.getSession(true).setAttribute("SEARCH_DATAM", HandlerM);
	}
	public String getErrorMSG() {
		return errorMSG;
	}
	public void setErrorMSG(String errorMSG) {
		this.errorMSG = errorMSG;
	}
	public static String CreateMessageEventResponse(EventResponse response ,HttpServletRequest request, String prefix, String suffix){
		if(response.getEncapData()!=null){
			return prefix + " " + response.getEncapData().toString() + " " + suffix;
		}
		String MSG = Message.error();
		return ErrorUtil.getShortErrorMessage(request,MSG);
	}
	
	public ArrayList<ErrorDataM> getErrorList() {
		return errorList;
	}
	public void setErrorList(ArrayList<ErrorDataM> errorList) {
		this.errorList = errorList;
	}
	public void add(ErrorDataM obj){
		if(this.errorList == null)errorList = new ArrayList<ErrorDataM>();
		errorList.add(obj);
	}
	
}