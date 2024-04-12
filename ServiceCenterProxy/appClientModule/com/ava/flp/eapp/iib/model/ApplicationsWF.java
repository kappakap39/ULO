package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.LoansWF;
import com.ava.flp.eapp.iib.model.PersonalRefsWF;
import com.ava.flp.eapp.iib.model.VerResultMWF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApplicationsWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-18T14:07:15.358+07:00")

public class ApplicationsWF   {
  @JsonProperty("appItemNo")
  private String appItemNo = null;

  @JsonProperty("appStatus")
  private String appStatus = null;

  @JsonProperty("appStatusDate")
  private String appStatusDate = null;

  @JsonProperty("bookingDate")
  private String bookingDate = null;

  @JsonProperty("projectCode")
  private String projectCode = null;

  @JsonProperty("kbankProductCode")
  private String kbankProductCode = null;

  @JsonProperty("vetoFlag")
  private String vetoFlag = null;

  @JsonProperty("rejectCode")
  private String rejectCode = null;

  @JsonProperty("applyType")
  private String applyType = null;

  @JsonProperty("bundleProduct")
  private String bundleProduct = null;

  @JsonProperty("bundleProductCreditLimit")
  private String bundleProductCreditLimit = null;

  @JsonProperty("policyProgramId")
  private String policyProgramId = null;

  @JsonProperty("mainAppItemNo")
  private String mainAppItemNo = null;

  @JsonProperty("verResultM")
  private VerResultMWF verResultM = null;

  @JsonProperty("loans")
  private List<LoansWF> loans = null;

  @JsonProperty("personalRefs")
  private List<PersonalRefsWF> personalRefs = null;

  public ApplicationsWF appItemNo(String appItemNo) {
    this.appItemNo = appItemNo;
    return this;
  }

   /**
   * Get appItemNo
   * @return appItemNo
  **/
  @ApiModelProperty(value = "")


  public String getAppItemNo() {
    return appItemNo;
  }

  public void setAppItemNo(String appItemNo) {
    this.appItemNo = appItemNo;
  }

  public ApplicationsWF appStatus(String appStatus) {
    this.appStatus = appStatus;
    return this;
  }

   /**
   * Get appStatus
   * @return appStatus
  **/
  @ApiModelProperty(value = "")


  public String getAppStatus() {
    return appStatus;
  }

  public void setAppStatus(String appStatus) {
    this.appStatus = appStatus;
  }

  public ApplicationsWF appStatusDate(String appStatusDate) {
    this.appStatusDate = appStatusDate;
    return this;
  }

   /**
   * Get appStatusDate
   * @return appStatusDate
  **/
  @ApiModelProperty(value = "")


  public String getAppStatusDate() {
    return appStatusDate;
  }

  public void setAppStatusDate(String appStatusDate) {
    this.appStatusDate = appStatusDate;
  }

  public ApplicationsWF bookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
    return this;
  }

   /**
   * Get bookingDate
   * @return bookingDate
  **/
  @ApiModelProperty(value = "")


  public String getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
  }

  public ApplicationsWF projectCode(String projectCode) {
    this.projectCode = projectCode;
    return this;
  }

   /**
   * Get projectCode
   * @return projectCode
  **/
  @ApiModelProperty(value = "")


  public String getProjectCode() {
    return projectCode;
  }

  public void setProjectCode(String projectCode) {
    this.projectCode = projectCode;
  }

  public ApplicationsWF kbankProductCode(String kbankProductCode) {
    this.kbankProductCode = kbankProductCode;
    return this;
  }

   /**
   * Get kbankProductCode
   * @return kbankProductCode
  **/
  @ApiModelProperty(value = "")


  public String getKbankProductCode() {
    return kbankProductCode;
  }

  public void setKbankProductCode(String kbankProductCode) {
    this.kbankProductCode = kbankProductCode;
  }

  public ApplicationsWF vetoFlag(String vetoFlag) {
    this.vetoFlag = vetoFlag;
    return this;
  }

   /**
   * Get vetoFlag
   * @return vetoFlag
  **/
  @ApiModelProperty(value = "")


  public String getVetoFlag() {
    return vetoFlag;
  }

  public void setVetoFlag(String vetoFlag) {
    this.vetoFlag = vetoFlag;
  }

  public ApplicationsWF rejectCode(String rejectCode) {
    this.rejectCode = rejectCode;
    return this;
  }

   /**
   * Get rejectCode
   * @return rejectCode
  **/
  @ApiModelProperty(value = "")


  public String getRejectCode() {
    return rejectCode;
  }

  public void setRejectCode(String rejectCode) {
    this.rejectCode = rejectCode;
  }

  public ApplicationsWF applyType(String applyType) {
    this.applyType = applyType;
    return this;
  }

   /**
   * Get applyType
   * @return applyType
  **/
  @ApiModelProperty(value = "")


  public String getApplyType() {
    return applyType;
  }

  public void setApplyType(String applyType) {
    this.applyType = applyType;
  }

  public ApplicationsWF bundleProduct(String bundleProduct) {
    this.bundleProduct = bundleProduct;
    return this;
  }

   /**
   * Get bundleProduct
   * @return bundleProduct
  **/
  @ApiModelProperty(value = "")


  public String getBundleProduct() {
    return bundleProduct;
  }

  public void setBundleProduct(String bundleProduct) {
    this.bundleProduct = bundleProduct;
  }

  public ApplicationsWF bundleProductCreditLimit(String bundleProductCreditLimit) {
    this.bundleProductCreditLimit = bundleProductCreditLimit;
    return this;
  }

   /**
   * Get bundleProductCreditLimit
   * @return bundleProductCreditLimit
  **/
  @ApiModelProperty(value = "")


  public String getBundleProductCreditLimit() {
    return bundleProductCreditLimit;
  }

  public void setBundleProductCreditLimit(String bundleProductCreditLimit) {
    this.bundleProductCreditLimit = bundleProductCreditLimit;
  }

  public ApplicationsWF policyProgramId(String policyProgramId) {
    this.policyProgramId = policyProgramId;
    return this;
  }

   /**
   * Get policyProgramId
   * @return policyProgramId
  **/
  @ApiModelProperty(value = "")


  public String getPolicyProgramId() {
    return policyProgramId;
  }

  public void setPolicyProgramId(String policyProgramId) {
    this.policyProgramId = policyProgramId;
  }

  public ApplicationsWF mainAppItemNo(String mainAppItemNo) {
    this.mainAppItemNo = mainAppItemNo;
    return this;
  }

   /**
   * Get mainAppItemNo
   * @return mainAppItemNo
  **/
  @ApiModelProperty(value = "")


  public String getMainAppItemNo() {
    return mainAppItemNo;
  }

  public void setMainAppItemNo(String mainAppItemNo) {
    this.mainAppItemNo = mainAppItemNo;
  }

  public ApplicationsWF verResultM(VerResultMWF verResultM) {
    this.verResultM = verResultM;
    return this;
  }

   /**
   * Get verResultM
   * @return verResultM
  **/
  @ApiModelProperty(value = "")

  @Valid

  public VerResultMWF getVerResultM() {
    return verResultM;
  }

  public void setVerResultM(VerResultMWF verResultM) {
    this.verResultM = verResultM;
  }

  public ApplicationsWF loans(List<LoansWF> loans) {
    this.loans = loans;
    return this;
  }

  public ApplicationsWF addLoansItem(LoansWF loansItem) {
    if (this.loans == null) {
      this.loans = new ArrayList<LoansWF>();
    }
    this.loans.add(loansItem);
    return this;
  }

   /**
   * Get loans
   * @return loans
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<LoansWF> getLoans() {
    return loans;
  }

  public void setLoans(List<LoansWF> loans) {
    this.loans = loans;
  }

  public ApplicationsWF personalRefs(List<PersonalRefsWF> personalRefs) {
    this.personalRefs = personalRefs;
    return this;
  }

  public ApplicationsWF addPersonalRefsItem(PersonalRefsWF personalRefsItem) {
    if (this.personalRefs == null) {
      this.personalRefs = new ArrayList<PersonalRefsWF>();
    }
    this.personalRefs.add(personalRefsItem);
    return this;
  }

   /**
   * Get personalRefs
   * @return personalRefs
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PersonalRefsWF> getPersonalRefs() {
    return personalRefs;
  }

  public void setPersonalRefs(List<PersonalRefsWF> personalRefs) {
    this.personalRefs = personalRefs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationsWF applicationsWF = (ApplicationsWF) o;
    return Objects.equals(this.appItemNo, applicationsWF.appItemNo) &&
        Objects.equals(this.appStatus, applicationsWF.appStatus) &&
        Objects.equals(this.appStatusDate, applicationsWF.appStatusDate) &&
        Objects.equals(this.bookingDate, applicationsWF.bookingDate) &&
        Objects.equals(this.projectCode, applicationsWF.projectCode) &&
        Objects.equals(this.kbankProductCode, applicationsWF.kbankProductCode) &&
        Objects.equals(this.vetoFlag, applicationsWF.vetoFlag) &&
        Objects.equals(this.rejectCode, applicationsWF.rejectCode) &&
        Objects.equals(this.applyType, applicationsWF.applyType) &&
        Objects.equals(this.bundleProduct, applicationsWF.bundleProduct) &&
        Objects.equals(this.bundleProductCreditLimit, applicationsWF.bundleProductCreditLimit) &&
        Objects.equals(this.policyProgramId, applicationsWF.policyProgramId) &&
        Objects.equals(this.mainAppItemNo, applicationsWF.mainAppItemNo) &&
        Objects.equals(this.verResultM, applicationsWF.verResultM) &&
        Objects.equals(this.loans, applicationsWF.loans) &&
        Objects.equals(this.personalRefs, applicationsWF.personalRefs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appItemNo, appStatus, appStatusDate, bookingDate, projectCode, kbankProductCode, vetoFlag, rejectCode, applyType, bundleProduct, bundleProductCreditLimit, policyProgramId, mainAppItemNo, verResultM, loans, personalRefs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationsWF {\n");
    
    sb.append("    appItemNo: ").append(toIndentedString(appItemNo)).append("\n");
    sb.append("    appStatus: ").append(toIndentedString(appStatus)).append("\n");
    sb.append("    appStatusDate: ").append(toIndentedString(appStatusDate)).append("\n");
    sb.append("    bookingDate: ").append(toIndentedString(bookingDate)).append("\n");
    sb.append("    projectCode: ").append(toIndentedString(projectCode)).append("\n");
    sb.append("    kbankProductCode: ").append(toIndentedString(kbankProductCode)).append("\n");
    sb.append("    vetoFlag: ").append(toIndentedString(vetoFlag)).append("\n");
    sb.append("    rejectCode: ").append(toIndentedString(rejectCode)).append("\n");
    sb.append("    applyType: ").append(toIndentedString(applyType)).append("\n");
    sb.append("    bundleProduct: ").append(toIndentedString(bundleProduct)).append("\n");
    sb.append("    bundleProductCreditLimit: ").append(toIndentedString(bundleProductCreditLimit)).append("\n");
    sb.append("    policyProgramId: ").append(toIndentedString(policyProgramId)).append("\n");
    sb.append("    mainAppItemNo: ").append(toIndentedString(mainAppItemNo)).append("\n");
    sb.append("    verResultM: ").append(toIndentedString(verResultM)).append("\n");
    sb.append("    loans: ").append(toIndentedString(loans)).append("\n");
    sb.append("    personalRefs: ").append(toIndentedString(personalRefs)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

