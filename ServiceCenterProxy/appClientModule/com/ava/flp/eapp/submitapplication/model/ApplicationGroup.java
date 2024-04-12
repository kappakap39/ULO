package com.ava.flp.eapp.submitapplication.model;

import java.util.Objects;
import com.ava.flp.eapp.submitapplication.model.Applications;
import com.ava.flp.eapp.submitapplication.model.PersonalInfos;
import com.ava.flp.eapp.submitapplication.model.ReferencePersons;
import com.ava.flp.eapp.submitapplication.model.SaleInfos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApplicationGroup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-12T11:20:28.064+07:00")

public class ApplicationGroup   {
  @JsonProperty("applicationGroupNo")
  private String applicationGroupNo = null;

  @JsonProperty("callAction")
  private String callAction = null;

  @JsonProperty("applicationDate")
  private String applicationDate = null;

  @JsonProperty("applicationTemplate")
  private String applicationTemplate = null;

  @JsonProperty("applyChannel")
  private String applyChannel = null;

  @JsonProperty("applyDate")
  private String applyDate = null;

  @JsonProperty("rcCode")
  private String rcCode = null;

  @JsonProperty("numberOfSubmission")
  private BigDecimal numberOfSubmission = null;

  @JsonProperty("randomNo1")
  private BigDecimal randomNo1 = null;

  @JsonProperty("randomNo2")
  private BigDecimal randomNo2 = null;

  @JsonProperty("randomNo3")
  private BigDecimal randomNo3 = null;

  @JsonProperty("branchNo")
  private String branchNo = null;

  @JsonProperty("applications")
  private List<Applications> applications = null;

  @JsonProperty("referencePersons")
  private List<ReferencePersons> referencePersons = null;

  @JsonProperty("saleInfos")
  private List<SaleInfos> saleInfos = null;

  @JsonProperty("personalInfos")
  private List<PersonalInfos> personalInfos = null;

  public ApplicationGroup applicationGroupNo(String applicationGroupNo) {
    this.applicationGroupNo = applicationGroupNo;
    return this;
  }

   /**
   * Get applicationGroupNo
   * @return applicationGroupNo
  **/
  @ApiModelProperty(value = "")


  public String getApplicationGroupNo() {
    return applicationGroupNo;
  }

  public void setApplicationGroupNo(String applicationGroupNo) {
    this.applicationGroupNo = applicationGroupNo;
  }

  public ApplicationGroup callAction(String callAction) {
    this.callAction = callAction;
    return this;
  }

   /**
   * Get callAction
   * @return callAction
  **/
  @ApiModelProperty(value = "")


  public String getCallAction() {
    return callAction;
  }

  public void setCallAction(String callAction) {
    this.callAction = callAction;
  }

  public ApplicationGroup applicationDate(String applicationDate) {
    this.applicationDate = applicationDate;
    return this;
  }

   /**
   * Get applicationDate
   * @return applicationDate
  **/
  @ApiModelProperty(value = "")


  public String getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(String applicationDate) {
    this.applicationDate = applicationDate;
  }

  public ApplicationGroup applicationTemplate(String applicationTemplate) {
    this.applicationTemplate = applicationTemplate;
    return this;
  }

   /**
   * Get applicationTemplate
   * @return applicationTemplate
  **/
  @ApiModelProperty(value = "")


  public String getApplicationTemplate() {
    return applicationTemplate;
  }

  public void setApplicationTemplate(String applicationTemplate) {
    this.applicationTemplate = applicationTemplate;
  }

  public ApplicationGroup applyChannel(String applyChannel) {
    this.applyChannel = applyChannel;
    return this;
  }

   /**
   * Get applyChannel
   * @return applyChannel
  **/
  @ApiModelProperty(value = "")


  public String getApplyChannel() {
    return applyChannel;
  }

  public void setApplyChannel(String applyChannel) {
    this.applyChannel = applyChannel;
  }

  public ApplicationGroup applyDate(String applyDate) {
    this.applyDate = applyDate;
    return this;
  }

   /**
   * Get applyDate
   * @return applyDate
  **/
  @ApiModelProperty(value = "")


  public String getApplyDate() {
    return applyDate;
  }

  public void setApplyDate(String applyDate) {
    this.applyDate = applyDate;
  }

  public ApplicationGroup rcCode(String rcCode) {
    this.rcCode = rcCode;
    return this;
  }

   /**
   * Get rcCode
   * @return rcCode
  **/
  @ApiModelProperty(value = "")


  public String getRcCode() {
    return rcCode;
  }

  public void setRcCode(String rcCode) {
    this.rcCode = rcCode;
  }

  public ApplicationGroup numberOfSubmission(BigDecimal numberOfSubmission) {
    this.numberOfSubmission = numberOfSubmission;
    return this;
  }

   /**
   * Get numberOfSubmission
   * @return numberOfSubmission
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfSubmission() {
    return numberOfSubmission;
  }

  public void setNumberOfSubmission(BigDecimal numberOfSubmission) {
    this.numberOfSubmission = numberOfSubmission;
  }

  public ApplicationGroup randomNo1(BigDecimal randomNo1) {
    this.randomNo1 = randomNo1;
    return this;
  }

   /**
   * Get randomNo1
   * @return randomNo1
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRandomNo1() {
    return randomNo1;
  }

  public void setRandomNo1(BigDecimal randomNo1) {
    this.randomNo1 = randomNo1;
  }

  public ApplicationGroup randomNo2(BigDecimal randomNo2) {
    this.randomNo2 = randomNo2;
    return this;
  }

   /**
   * Get randomNo2
   * @return randomNo2
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRandomNo2() {
    return randomNo2;
  }

  public void setRandomNo2(BigDecimal randomNo2) {
    this.randomNo2 = randomNo2;
  }

  public ApplicationGroup randomNo3(BigDecimal randomNo3) {
    this.randomNo3 = randomNo3;
    return this;
  }

   /**
   * Get randomNo3
   * @return randomNo3
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getRandomNo3() {
    return randomNo3;
  }

  public void setRandomNo3(BigDecimal randomNo3) {
    this.randomNo3 = randomNo3;
  }

  public ApplicationGroup branchNo(String branchNo) {
    this.branchNo = branchNo;
    return this;
  }

   /**
   * Get branchNo
   * @return branchNo
  **/
  @ApiModelProperty(value = "")


  public String getBranchNo() {
    return branchNo;
  }

  public void setBranchNo(String branchNo) {
    this.branchNo = branchNo;
  }

  public ApplicationGroup applications(List<Applications> applications) {
    this.applications = applications;
    return this;
  }

  public ApplicationGroup addApplicationsItem(Applications applicationsItem) {
    if (this.applications == null) {
      this.applications = new ArrayList<Applications>();
    }
    this.applications.add(applicationsItem);
    return this;
  }

   /**
   * Get applications
   * @return applications
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Applications> getApplications() {
    return applications;
  }

  public void setApplications(List<Applications> applications) {
    this.applications = applications;
  }

  public ApplicationGroup referencePersons(List<ReferencePersons> referencePersons) {
    this.referencePersons = referencePersons;
    return this;
  }

  public ApplicationGroup addReferencePersonsItem(ReferencePersons referencePersonsItem) {
    if (this.referencePersons == null) {
      this.referencePersons = new ArrayList<ReferencePersons>();
    }
    this.referencePersons.add(referencePersonsItem);
    return this;
  }

   /**
   * Get referencePersons
   * @return referencePersons
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ReferencePersons> getReferencePersons() {
    return referencePersons;
  }

  public void setReferencePersons(List<ReferencePersons> referencePersons) {
    this.referencePersons = referencePersons;
  }

  public ApplicationGroup saleInfos(List<SaleInfos> saleInfos) {
    this.saleInfos = saleInfos;
    return this;
  }

  public ApplicationGroup addSaleInfosItem(SaleInfos saleInfosItem) {
    if (this.saleInfos == null) {
      this.saleInfos = new ArrayList<SaleInfos>();
    }
    this.saleInfos.add(saleInfosItem);
    return this;
  }

   /**
   * Get saleInfos
   * @return saleInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SaleInfos> getSaleInfos() {
    return saleInfos;
  }

  public void setSaleInfos(List<SaleInfos> saleInfos) {
    this.saleInfos = saleInfos;
  }

  public ApplicationGroup personalInfos(List<PersonalInfos> personalInfos) {
    this.personalInfos = personalInfos;
    return this;
  }

  public ApplicationGroup addPersonalInfosItem(PersonalInfos personalInfosItem) {
    if (this.personalInfos == null) {
      this.personalInfos = new ArrayList<PersonalInfos>();
    }
    this.personalInfos.add(personalInfosItem);
    return this;
  }

   /**
   * Get personalInfos
   * @return personalInfos
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PersonalInfos> getPersonalInfos() {
    return personalInfos;
  }

  public void setPersonalInfos(List<PersonalInfos> personalInfos) {
    this.personalInfos = personalInfos;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApplicationGroup applicationGroup = (ApplicationGroup) o;
    return Objects.equals(this.applicationGroupNo, applicationGroup.applicationGroupNo) &&
        Objects.equals(this.callAction, applicationGroup.callAction) &&
        Objects.equals(this.applicationDate, applicationGroup.applicationDate) &&
        Objects.equals(this.applicationTemplate, applicationGroup.applicationTemplate) &&
        Objects.equals(this.applyChannel, applicationGroup.applyChannel) &&
        Objects.equals(this.applyDate, applicationGroup.applyDate) &&
        Objects.equals(this.rcCode, applicationGroup.rcCode) &&
        Objects.equals(this.numberOfSubmission, applicationGroup.numberOfSubmission) &&
        Objects.equals(this.randomNo1, applicationGroup.randomNo1) &&
        Objects.equals(this.randomNo2, applicationGroup.randomNo2) &&
        Objects.equals(this.randomNo3, applicationGroup.randomNo3) &&
        Objects.equals(this.branchNo, applicationGroup.branchNo) &&
        Objects.equals(this.applications, applicationGroup.applications) &&
        Objects.equals(this.referencePersons, applicationGroup.referencePersons) &&
        Objects.equals(this.saleInfos, applicationGroup.saleInfos) &&
        Objects.equals(this.personalInfos, applicationGroup.personalInfos);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicationGroupNo, callAction, applicationDate, applicationTemplate, applyChannel, applyDate, rcCode, numberOfSubmission, randomNo1, randomNo2, randomNo3, branchNo, applications, referencePersons, saleInfos, personalInfos);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationGroup {\n");
    
    sb.append("    applicationGroupNo: ").append(toIndentedString(applicationGroupNo)).append("\n");
    sb.append("    callAction: ").append(toIndentedString(callAction)).append("\n");
    sb.append("    applicationDate: ").append(toIndentedString(applicationDate)).append("\n");
    sb.append("    applicationTemplate: ").append(toIndentedString(applicationTemplate)).append("\n");
    sb.append("    applyChannel: ").append(toIndentedString(applyChannel)).append("\n");
    sb.append("    applyDate: ").append(toIndentedString(applyDate)).append("\n");
    sb.append("    rcCode: ").append(toIndentedString(rcCode)).append("\n");
    sb.append("    numberOfSubmission: ").append(toIndentedString(numberOfSubmission)).append("\n");
    sb.append("    randomNo1: ").append(toIndentedString(randomNo1)).append("\n");
    sb.append("    randomNo2: ").append(toIndentedString(randomNo2)).append("\n");
    sb.append("    randomNo3: ").append(toIndentedString(randomNo3)).append("\n");
    sb.append("    branchNo: ").append(toIndentedString(branchNo)).append("\n");
    sb.append("    applications: ").append(toIndentedString(applications)).append("\n");
    sb.append("    referencePersons: ").append(toIndentedString(referencePersons)).append("\n");
    sb.append("    saleInfos: ").append(toIndentedString(saleInfos)).append("\n");
    sb.append("    personalInfos: ").append(toIndentedString(personalInfos)).append("\n");
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

