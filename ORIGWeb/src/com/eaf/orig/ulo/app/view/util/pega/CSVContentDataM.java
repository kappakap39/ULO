package com.eaf.orig.ulo.app.view.util.pega;

import java.io.Serializable;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

@CsvDataType()
public class CSVContentDataM implements Serializable, Cloneable{
	public CSVContentDataM () {
		super();
	}
	
	@CsvField(pos = 1)
	private  String CaseID;
	
	@CsvField(pos = 2)
	private  String Group;
	
	@CsvField(pos = 3)
	private  String Scenario;
	
	@CsvField(pos = 4)
	private  String DocName;
	
	@CsvField(pos = 5)
	private  String Mandatory;
	
	@CsvField(pos = 6)
	private  String DocReason;
	
	@CsvField(pos = 7)
	private  String ReasonDetail;
	
	@CsvField(pos = 8)
	private  String CustAvailableTime;
	
	@CsvField(pos = 9)
	private  String AlreadyFollow;
	
	@CsvField(pos = 10)
	private  String Mobile;
	
	@CsvField(pos = 11)
	private  String TelHome;
	
	@CsvField(pos = 12)
	private  String TelHomeExt;
	
	@CsvField(pos = 13)
	private  String TelOffice;
	
	@CsvField(pos = 14)
	private  String TelOfficeExt;
	
	@CsvField(pos = 15)
	private  String TelCardLink;
	
	@CsvField(pos = 16)
	private  String TelCardLinkExt;
	
	@CsvField(pos = 17)
	private  String FirstNameTH;
	
	@CsvField(pos = 18)
	private  String MiddleNameTH;
	
	@CsvField(pos = 19)
	private  String LastNameTH;
	
	@CsvField(pos = 20)
	private  String FirstNameEN;
	
	@CsvField(pos = 21)
	private  String MiddleNameEN;
	
	@CsvField(pos = 22)
	private  String LastNameEN;
	
	@CsvField(pos = 23)
	private  String PrimarySegment;
	
	@CsvField(pos = 24)
	private  String PrimarySubSegment;
	
	@CsvField(pos = 25)
	private  String SecondarySegment;
	
	@CsvField(pos = 26)
	private  String SecondarySubSegment;
	
	@CsvField(pos = 27)
	private  String RMOperatorID;
	
	public String getCaseID() {
		return CaseID;
	}
	public void setCaseID(String caseID) {
		CaseID = caseID;
	}
	public String getGroup() {
		return Group;
	}
	public void setGroup(String group) {
		Group = group;
	}
	public String getScenario() {
		return Scenario;
	}
	public void setScenario(String scenario) {
		Scenario = scenario;
	}
	public String getDocName() {
		return DocName;
	}
	public void setDocName(String docName) {
		DocName = docName;
	}
	public String getMandatory() {
		return Mandatory;
	}
	public void setMandatory(String mandatory) {
		Mandatory = mandatory;
	}
	public String getDocReason() {
		return DocReason;
	}
	public void setDocReason(String docReason) {
		DocReason = docReason;
	}
	public String getReasonDetail() {
		return ReasonDetail;
	}
	public void setReasonDetail(String reasonDetail) {
		ReasonDetail = reasonDetail;
	}
	public String getCustAvailableTime() {
		return CustAvailableTime;
	}
	public void setCustAvailableTime(String custAvailableTime) {
		CustAvailableTime = custAvailableTime;
	}
	public String getAlreadyFollow() {
		return AlreadyFollow;
	}
	public void setAlreadyFollow(String alreadyFollow) {
		AlreadyFollow = alreadyFollow;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getTelHome() {
		return TelHome;
	}
	public void setTelHome(String telHome) {
		TelHome = telHome;
	}
	public String getTelHomeExt() {
		return TelHomeExt;
	}
	public void setTelHomeExt(String telHomeExt) {
		TelHomeExt = telHomeExt;
	}
	public String getTelOffice() {
		return TelOffice;
	}
	public void setTelOffice(String telOffice) {
		TelOffice = telOffice;
	}
	public String getTelOfficeExt() {
		return TelOfficeExt;
	}
	public void setTelOfficeExt(String telOfficeExt) {
		TelOfficeExt = telOfficeExt;
	}
	public String getTelCardLink() {
		return TelCardLink;
	}
	public void setTelCardLink(String telCardLink) {
		TelCardLink = telCardLink;
	}
	public String getTelCardLinkExt() {
		return TelCardLinkExt;
	}
	public void setTelCardLinkExt(String telCardLinkExt) {
		TelCardLinkExt = telCardLinkExt;
	}
	public String getFirstNameTH() {
		return FirstNameTH;
	}
	public void setFirstNameTH(String firstNameTH) {
		FirstNameTH = firstNameTH;
	}
	public String getMiddleNameTH() {
		return MiddleNameTH;
	}
	public void setMiddleNameTH(String middleNameTH) {
		MiddleNameTH = middleNameTH;
	}
	public String getLastNameTH() {
		return LastNameTH;
	}
	public void setLastNameTH(String lastNameTH) {
		LastNameTH = lastNameTH;
	}
	public String getFirstNameEN() {
		return FirstNameEN;
	}
	public void setFirstNameEN(String firstNameEN) {
		FirstNameEN = firstNameEN;
	}
	public String getMiddleNameEN() {
		return MiddleNameEN;
	}
	public void setMiddleNameEN(String middleNameEN) {
		MiddleNameEN = middleNameEN;
	}
	public String getLastNameEN() {
		return LastNameEN;
	}
	public void setLastNameEN(String lastNameEN) {
		LastNameEN = lastNameEN;
	}
	public String getPrimarySegment() {
		return PrimarySegment;
	}
	public void setPrimarySegment(String primarySegment) {
		PrimarySegment = primarySegment;
	}
	public String getPrimarySubSegment() {
		return PrimarySubSegment;
	}
	public void setPrimarySubSegment(String primarySubSegment) {
		PrimarySubSegment = primarySubSegment;
	}
	public String getSecondarySegment() {
		return SecondarySegment;
	}
	public void setSecondarySegment(String secondarySegment) {
		SecondarySegment = secondarySegment;
	}
	public String getSecondarySubSegment() {
		return SecondarySubSegment;
	}
	public void setSecondarySubSegment(String secondarySubSegment) {
		SecondarySubSegment = secondarySubSegment;
	}
	public String getRMOperatorID() {
		return RMOperatorID;
	}
	public void setRMOperatorID(String rMOperatorID) {
		RMOperatorID = rMOperatorID;
	}

}
