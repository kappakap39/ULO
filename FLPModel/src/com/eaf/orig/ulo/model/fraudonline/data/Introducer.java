/*
 * DECISION_REST
 * This is the Swagger document for main flow of decision service using IBM Integration Toolkit for FLP HL.
 *
 * OpenAPI spec version: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.eaf.orig.ulo.model.fraudonline.data;

import java.math.BigDecimal;
import java.sql.Date;

import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

/**
 * Introducer
 */
@CsvDataType()
public class Introducer {
	@CsvField(pos = 156)
	private String fixVariable = null;

	@CsvField(pos = 157)
	private String idNumber1 = null;

	@CsvField(pos = 158)
	private String idNumber2 = null;

	@CsvField(pos = 159)
	private String idNumber3 = null;

	@CsvField(pos = 160)
	private String surname = null;

	@CsvField(pos = 161)
	private String firstName = null;

	@CsvField(pos = 162)
	private String middleName = null;

	@CsvField(pos = 163)
	private String mobilePhoneNumber = null;

	@CsvField(pos = 164)
	private String companyName = null;

	@CsvField(pos = 165)
	private String companyAddress1 = null;

	@CsvField(pos = 166)
	private String companyAddress2 = null;

	@CsvField(pos = 167)
	private String companyAddress3 = null;

	@CsvField(pos = 168)
	private String companyAddress4 = null;

	@CsvField(pos = 169)
	private String companyAddress5 = null;

	@CsvField(pos = 170)
	private String companyAddress6 = null;

	@CsvField(pos = 171)
	private String companyPostcode = null;

	@CsvField(pos = 172)
	private String companyPhoneNumber = null;

	@CsvField(pos = 173)
	private String userField1 = null;

	@CsvField(pos = 174)
	private String userField2 = null;

	@CsvField(pos = 175)
	private String userField3 = null;

	@CsvField(pos = 176)
	private BigDecimal userField4 = null;

	@CsvField(pos = 177)
	private Date userField5 = null;

	@CsvField(pos = 178)
	private String userField6 = null;

	@CsvField(pos = 179)
	private String userField7 = null;

	@CsvField(pos = 180)
	private String userField8 = null;

	@CsvField(pos = 181)
	private String userField9 = null;

	@CsvField(pos = 182)
	private String userField10 = null;

	public String getFixVariable() {
		return fixVariable;
	}

	public void setFixVariable(String fixVariable) {
		this.fixVariable = fixVariable;
	}

	public String getIdNumber1() {
		return idNumber1;
	}

	public void setIdNumber1(String idNumber1) {
		this.idNumber1 = idNumber1;
	}

	public String getIdNumber2() {
		return idNumber2;
	}

	public void setIdNumber2(String idNumber2) {
		this.idNumber2 = idNumber2;
	}

	public String getIdNumber3() {
		return idNumber3;
	}

	public void setIdNumber3(String idNumber3) {
		this.idNumber3 = idNumber3;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress1() {
		return companyAddress1;
	}

	public void setCompanyAddress1(String companyAddress1) {
		this.companyAddress1 = companyAddress1;
	}

	public String getCompanyAddress2() {
		return companyAddress2;
	}

	public void setCompanyAddress2(String companyAddress2) {
		this.companyAddress2 = companyAddress2;
	}

	public String getCompanyAddress3() {
		return companyAddress3;
	}

	public void setCompanyAddress3(String companyAddress3) {
		this.companyAddress3 = companyAddress3;
	}

	public String getCompanyAddress4() {
		return companyAddress4;
	}

	public void setCompanyAddress4(String companyAddress4) {
		this.companyAddress4 = companyAddress4;
	}

	public String getCompanyAddress5() {
		return companyAddress5;
	}

	public void setCompanyAddress5(String companyAddress5) {
		this.companyAddress5 = companyAddress5;
	}

	public String getCompanyAddress6() {
		return companyAddress6;
	}

	public void setCompanyAddress6(String companyAddress6) {
		this.companyAddress6 = companyAddress6;
	}

	public String getCompanyPostcode() {
		return companyPostcode;
	}

	public void setCompanyPostcode(String companyPostcode) {
		this.companyPostcode = companyPostcode;
	}

	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}

	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}

	public String getUserField1() {
		return userField1;
	}

	public void setUserField1(String userField1) {
		this.userField1 = userField1;
	}

	public String getUserField2() {
		return userField2;
	}

	public void setUserField2(String userField2) {
		this.userField2 = userField2;
	}

	public String getUserField3() {
		return userField3;
	}

	public void setUserField3(String userField3) {
		this.userField3 = userField3;
	}

	public BigDecimal getUserField4() {
		return userField4;
	}

	public void setUserField4(BigDecimal userField4) {
		this.userField4 = userField4;
	}

	public Date getUserField5() {
		return userField5;
	}

	public void setUserField5(Date userField5) {
		this.userField5 = userField5;
	}

	public String getUserField6() {
		return userField6;
	}

	public void setUserField6(String userField6) {
		this.userField6 = userField6;
	}

	public String getUserField7() {
		return userField7;
	}

	public void setUserField7(String userField7) {
		this.userField7 = userField7;
	}

	public String getUserField8() {
		return userField8;
	}

	public void setUserField8(String userField8) {
		this.userField8 = userField8;
	}

	public String getUserField9() {
		return userField9;
	}

	public void setUserField9(String userField9) {
		this.userField9 = userField9;
	}

	public String getUserField10() {
		return userField10;
	}

	public void setUserField10(String userField10) {
		this.userField10 = userField10;
	}

}
