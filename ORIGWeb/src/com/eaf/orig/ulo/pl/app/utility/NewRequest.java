package com.eaf.orig.ulo.pl.app.utility;

import java.io.Serializable;

import com.eaf.orig.shared.util.OrigUtil;

	public class NewRequest implements Serializable, Cloneable {
		
	    protected String tranNo;
	    protected String reqFirstname;
	    protected String reqSurname;
	    protected String reqEmpId;
	    protected String reqPhoneno;
	    protected String reqNameApp;
	    protected String reqSurnameApp;
	    protected int requestObjective;
	    protected int serviceType;
	    protected String idType;
	    protected String idNo;
	    protected int customerType;
	    protected int prefix;
	    protected String firstName;
	    protected String familyName;
	    protected String dateOfBirth;
	    protected String companyName;
	    protected String registerDate;
	    protected int requestAppID;
	    
		public String getTranNo() {
			return tranNo;
		}

		public void setTranNo(String tranNo) {
			this.tranNo = tranNo;
		}

		public String getReqFirstname() {
			return reqFirstname;
		}

		public void setReqFirstname(String reqFirstname) {
			this.reqFirstname = reqFirstname;
		}

		public String getReqSurname() {
			return reqSurname;
		}

		public void setReqSurname(String reqSurname) {
			this.reqSurname = reqSurname;
		}

		public String getReqEmpId() {
			return reqEmpId;
		}

		public void setReqEmpId(String reqEmpId) {
			this.reqEmpId = reqEmpId;
		}

		public String getReqPhoneno() {
			return reqPhoneno;
		}

		public void setReqPhoneno(String reqPhoneno) {
			this.reqPhoneno = reqPhoneno;
		}

		public String getReqNameApp() {
			return reqNameApp;
		}

		public void setReqNameApp(String reqNameApp) {
			this.reqNameApp = reqNameApp;
		}

		public String getReqSurnameApp() {
			return reqSurnameApp;
		}

		public void setReqSurnameApp(String reqSurnameApp) {
			this.reqSurnameApp = reqSurnameApp;
		}

		public int getRequestObjective() {
			return requestObjective;
		}

		public void setRequestObjective(int requestObjective) {
			this.requestObjective = requestObjective;
		}

		public int getServiceType() {
			return serviceType;
		}

		public void setServiceType(int serviceType) {
			this.serviceType = serviceType;
		}

		public String getIdType() {
			return idType;
		}

		public void setIdType(String idType) {
			this.idType = idType;
		}

		public String getIdNo() {
			return idNo;
		}

		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}

		public int getCustomerType() {
			return customerType;
		}

		public void setCustomerType(int customerType) {
			this.customerType = customerType;
		}

		public int getPrefix() {
			return prefix;
		}

		public void setPrefix(int prefix) {
			this.prefix = prefix;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getFamilyName() {
			return familyName;
		}

		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}

		public String getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getRegisterDate() {
			return registerDate;
		}

		public void setRegisterDate(String registerDate) {
			this.registerDate = registerDate;
		}

		public int getRequestAppID() {
			return requestAppID;
		}

		public void setRequestAppID(int requestAppID) {
			this.requestAppID = requestAppID;
		}

		@Override
		public String toString() {
			StringBuilder strB = new StringBuilder();
			strB.append("NewRequest [tranNo=").append(tranNo).append(", reqFirstname=").append(reqFirstname);
			strB.append(", reqSurname=").append(reqSurname).append(", reqEmpId=").append(reqEmpId);
			strB.append(", reqPhoneno=").append(reqPhoneno).append(", reqNameApp=").append(reqNameApp);
			strB.append(", reqSurnameApp=").append(reqSurnameApp).append(", requestObjective=").append(requestObjective);
			strB.append(", serviceType=").append(serviceType).append(", idType=").append(idType);
			strB.append(", idNo=").append(idNo).append(", customerType=").append(customerType);
			strB.append(", prefix=").append(prefix).append(", firstName=").append(firstName);
			strB.append(", familyName=").append(familyName).append(", dateOfBirth=").append(dateOfBirth);
			strB.append(", companyName=").append(companyName).append(", registerDate=").append(registerDate);
			strB.append(", requestAppID=").append(requestAppID).append("]");
			return strB.toString();
		}
		
//		public void setDataToModel(org.tempuri.NewRequest newRequest){
//			if(!OrigUtil.isEmptyObject(newRequest)){
//				this.tranNo = newRequest.getTranNo();
//				this.reqFirstname = newRequest.getReqFirstname();
//				this.reqSurname = newRequest.getReqSurname();
//			    this.reqEmpId = newRequest.getReqEmpId();
//			    this.reqPhoneno = newRequest.getReqPhoneno();
//			    this.reqNameApp = newRequest.getReqNameApp();
//			    this.reqSurnameApp = newRequest.getReqSurnameApp();
//			    this.requestObjective = newRequest.getRequestObjective();
//			    this.serviceType = newRequest.getServiceType();
//			    this.idType = newRequest.getIdType();
//			    this.idNo = newRequest.getIdNo();
//			    this.customerType = newRequest.getCustomerType();
//			    this.prefix = newRequest.getPrefix();
//			    this.firstName = newRequest.getFirstName();
//			    this.familyName = newRequest.getFamilyName();
//			    this.dateOfBirth = newRequest.getDateOfBirth();
//			    this.companyName = newRequest.getCompanyName();
//			    this.registerDate = newRequest.getRegisterDate();
//			    this.requestAppID = newRequest.getRequestAppID();
//			}
//		}
		
	}

