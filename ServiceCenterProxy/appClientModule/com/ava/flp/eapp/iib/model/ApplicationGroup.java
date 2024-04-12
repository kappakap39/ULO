package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.Applications;
import com.ava.flp.eapp.iib.model.CapPorts;
import com.ava.flp.eapp.iib.model.CardHierarchys;
import com.ava.flp.eapp.iib.model.PersonalInfos;
import com.ava.flp.eapp.iib.model.ReferencePersons;
import com.ava.flp.eapp.iib.model.SaleInfos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApplicationGroup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class ApplicationGroup   {
  @JsonProperty("callAction")
  private String callAction = null;

  @JsonProperty("applicationGroupId")
  private String applicationGroupId = null;

  @JsonProperty("applicationGroupNo")
  private String applicationGroupNo = null;

  @JsonProperty("applicationDate")
  private DateTime applicationDate = null;

  @JsonProperty("applicationTemplate")
  private String applicationTemplate = null;

  @JsonProperty("applyChannel")
  private String applyChannel = null;

  @JsonProperty("applyDate")
  private DateTime applyDate = null;

  @JsonProperty("policyExSignOffBy")
  private String policyExSignOffBy = null;

  @JsonProperty("policyExSignOffDate")
  private DateTime policyExSignOffDate = null;

  @JsonProperty("systemDate")
  private DateTime systemDate = null;

  @JsonProperty("vetoFlag")
  private String vetoFlag = null;

  @JsonProperty("numberOfSubmission")
  private BigDecimal numberOfSubmission = null;

  @JsonProperty("randomNo1")
  private BigDecimal randomNo1 = null;

  @JsonProperty("randomNo2")
  private BigDecimal randomNo2 = null;

  @JsonProperty("randomNo3")
  private BigDecimal randomNo3 = null;

  @JsonProperty("fraudApplicantFlag")
  private String fraudApplicantFlag = null;

  @JsonProperty("fraudCompanyFlag")
  private String fraudCompanyFlag = null;

  @JsonProperty("executeAction")
  private String executeAction = null;

  @JsonProperty("processAction")
  private String processAction = null;

  @JsonProperty("fullFraudFlag")
  private String fullFraudFlag = null;

  @JsonProperty("applications")
  private List<Applications> applications = null;

  @JsonProperty("referencePersons")
  private List<ReferencePersons> referencePersons = null;

  @JsonProperty("saleInfos")
  private List<SaleInfos> saleInfos = null;

  @JsonProperty("personalInfos")
  private List<PersonalInfos> personalInfos = null;

  @JsonProperty("cardHierarchys")
  private List<CardHierarchys> cardHierarchys = null;

  @JsonProperty("capPorts")
  private List<CapPorts> capPorts = null;

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

  public ApplicationGroup applicationGroupId(String applicationGroupId) {
    this.applicationGroupId = applicationGroupId;
    return this;
  }

   /**
   * Get applicationGroupId
   * @return applicationGroupId
  **/
  @ApiModelProperty(value = "")


  public String getApplicationGroupId() {
    return applicationGroupId;
  }

  public void setApplicationGroupId(String applicationGroupId) {
    this.applicationGroupId = applicationGroupId;
  }

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

  public ApplicationGroup applicationDate(DateTime applicationDate) {
    this.applicationDate = applicationDate;
    return this;
  }

   /**
   * Get applicationDate
   * @return applicationDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(DateTime applicationDate) {
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

  public ApplicationGroup applyDate(DateTime applyDate) {
    this.applyDate = applyDate;
    return this;
  }

   /**
   * Get applyDate
   * @return applyDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getApplyDate() {
    return applyDate;
  }

  public void setApplyDate(DateTime applyDate) {
    this.applyDate = applyDate;
  }

  public ApplicationGroup policyExSignOffBy(String policyExSignOffBy) {
    this.policyExSignOffBy = policyExSignOffBy;
    return this;
  }

   /**
   * Get policyExSignOffBy
   * @return policyExSignOffBy
  **/
  @ApiModelProperty(value = "")


  public String getPolicyExSignOffBy() {
    return policyExSignOffBy;
  }

  public void setPolicyExSignOffBy(String policyExSignOffBy) {
    this.policyExSignOffBy = policyExSignOffBy;
  }

  public ApplicationGroup policyExSignOffDate(DateTime policyExSignOffDate) {
    this.policyExSignOffDate = policyExSignOffDate;
    return this;
  }

   /**
   * Get policyExSignOffDate
   * @return policyExSignOffDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPolicyExSignOffDate() {
    return policyExSignOffDate;
  }

  public void setPolicyExSignOffDate(DateTime policyExSignOffDate) {
    this.policyExSignOffDate = policyExSignOffDate;
  }

  public ApplicationGroup systemDate(DateTime systemDate) {
    this.systemDate = systemDate;
    return this;
  }

   /**
   * Get systemDate
   * @return systemDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getSystemDate() {
    return systemDate;
  }

  public void setSystemDate(DateTime systemDate) {
    this.systemDate = systemDate;
  }

  public ApplicationGroup vetoFlag(String vetoFlag) {
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

  public ApplicationGroup fraudApplicantFlag(String fraudApplicantFlag) {
    this.fraudApplicantFlag = fraudApplicantFlag;
    return this;
  }

   /**
   * Get fraudApplicantFlag
   * @return fraudApplicantFlag
  **/
  @ApiModelProperty(value = "")


  public String getFraudApplicantFlag() {
    return fraudApplicantFlag;
  }

  public void setFraudApplicantFlag(String fraudApplicantFlag) {
    this.fraudApplicantFlag = fraudApplicantFlag;
  }

  public ApplicationGroup fraudCompanyFlag(String fraudCompanyFlag) {
    this.fraudCompanyFlag = fraudCompanyFlag;
    return this;
  }

   /**
   * Get fraudCompanyFlag
   * @return fraudCompanyFlag
  **/
  @ApiModelProperty(value = "")


  public String getFraudCompanyFlag() {
    return fraudCompanyFlag;
  }

  public void setFraudCompanyFlag(String fraudCompanyFlag) {
    this.fraudCompanyFlag = fraudCompanyFlag;
  }

  public ApplicationGroup executeAction(String executeAction) {
    this.executeAction = executeAction;
    return this;
  }

   /**
   * Get executeAction
   * @return executeAction
  **/
  @ApiModelProperty(value = "")


  public String getExecuteAction() {
    return executeAction;
  }

  public void setExecuteAction(String executeAction) {
    this.executeAction = executeAction;
  }

  public ApplicationGroup processAction(String processAction) {
    this.processAction = processAction;
    return this;
  }

   /**
   * Get processAction
   * @return processAction
  **/
  @ApiModelProperty(value = "")


  public String getProcessAction() {
    return processAction;
  }

  public void setProcessAction(String processAction) {
    this.processAction = processAction;
  }

  public ApplicationGroup fullFraudFlag(String fullFraudFlag) {
    this.fullFraudFlag = fullFraudFlag;
    return this;
  }

   /**
   * Get fullFraudFlag
   * @return fullFraudFlag
  **/
  @ApiModelProperty(value = "")


  public String getFullFraudFlag() {
    return fullFraudFlag;
  }

  public void setFullFraudFlag(String fullFraudFlag) {
    this.fullFraudFlag = fullFraudFlag;
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

  public ApplicationGroup cardHierarchys(List<CardHierarchys> cardHierarchys) {
    this.cardHierarchys = cardHierarchys;
    return this;
  }

  public ApplicationGroup addCardHierarchysItem(CardHierarchys cardHierarchysItem) {
    if (this.cardHierarchys == null) {
      this.cardHierarchys = new ArrayList<CardHierarchys>();
    }
    this.cardHierarchys.add(cardHierarchysItem);
    return this;
  }

   /**
   * Get cardHierarchys
   * @return cardHierarchys
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CardHierarchys> getCardHierarchys() {
    return cardHierarchys;
  }

  public void setCardHierarchys(List<CardHierarchys> cardHierarchys) {
    this.cardHierarchys = cardHierarchys;
  }

  public ApplicationGroup capPorts(List<CapPorts> capPorts) {
    this.capPorts = capPorts;
    return this;
  }

  public ApplicationGroup addCapPortsItem(CapPorts capPortsItem) {
    if (this.capPorts == null) {
      this.capPorts = new ArrayList<CapPorts>();
    }
    this.capPorts.add(capPortsItem);
    return this;
  }

   /**
   * Get capPorts
   * @return capPorts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CapPorts> getCapPorts() {
    return capPorts;
  }

  public void setCapPorts(List<CapPorts> capPorts) {
    this.capPorts = capPorts;
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
    return Objects.equals(this.callAction, applicationGroup.callAction) &&
        Objects.equals(this.applicationGroupId, applicationGroup.applicationGroupId) &&
        Objects.equals(this.applicationGroupNo, applicationGroup.applicationGroupNo) &&
        Objects.equals(this.applicationDate, applicationGroup.applicationDate) &&
        Objects.equals(this.applicationTemplate, applicationGroup.applicationTemplate) &&
        Objects.equals(this.applyChannel, applicationGroup.applyChannel) &&
        Objects.equals(this.applyDate, applicationGroup.applyDate) &&
        Objects.equals(this.policyExSignOffBy, applicationGroup.policyExSignOffBy) &&
        Objects.equals(this.policyExSignOffDate, applicationGroup.policyExSignOffDate) &&
        Objects.equals(this.systemDate, applicationGroup.systemDate) &&
        Objects.equals(this.vetoFlag, applicationGroup.vetoFlag) &&
        Objects.equals(this.numberOfSubmission, applicationGroup.numberOfSubmission) &&
        Objects.equals(this.randomNo1, applicationGroup.randomNo1) &&
        Objects.equals(this.randomNo2, applicationGroup.randomNo2) &&
        Objects.equals(this.randomNo3, applicationGroup.randomNo3) &&
        Objects.equals(this.fraudApplicantFlag, applicationGroup.fraudApplicantFlag) &&
        Objects.equals(this.fraudCompanyFlag, applicationGroup.fraudCompanyFlag) &&
        Objects.equals(this.executeAction, applicationGroup.executeAction) &&
        Objects.equals(this.processAction, applicationGroup.processAction) &&
        Objects.equals(this.fullFraudFlag, applicationGroup.fullFraudFlag) &&
        Objects.equals(this.applications, applicationGroup.applications) &&
        Objects.equals(this.referencePersons, applicationGroup.referencePersons) &&
        Objects.equals(this.saleInfos, applicationGroup.saleInfos) &&
        Objects.equals(this.personalInfos, applicationGroup.personalInfos) &&
        Objects.equals(this.cardHierarchys, applicationGroup.cardHierarchys) &&
        Objects.equals(this.capPorts, applicationGroup.capPorts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callAction, applicationGroupId, applicationGroupNo, applicationDate, applicationTemplate, applyChannel, applyDate, policyExSignOffBy, policyExSignOffDate, systemDate, vetoFlag, numberOfSubmission, randomNo1, randomNo2, randomNo3, fraudApplicantFlag, fraudCompanyFlag, executeAction, processAction, fullFraudFlag, applications, referencePersons, saleInfos, personalInfos, cardHierarchys, capPorts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApplicationGroup {\n");
    
    sb.append("    callAction: ").append(toIndentedString(callAction)).append("\n");
    sb.append("    applicationGroupId: ").append(toIndentedString(applicationGroupId)).append("\n");
    sb.append("    applicationGroupNo: ").append(toIndentedString(applicationGroupNo)).append("\n");
    sb.append("    applicationDate: ").append(toIndentedString(applicationDate)).append("\n");
    sb.append("    applicationTemplate: ").append(toIndentedString(applicationTemplate)).append("\n");
    sb.append("    applyChannel: ").append(toIndentedString(applyChannel)).append("\n");
    sb.append("    applyDate: ").append(toIndentedString(applyDate)).append("\n");
    sb.append("    policyExSignOffBy: ").append(toIndentedString(policyExSignOffBy)).append("\n");
    sb.append("    policyExSignOffDate: ").append(toIndentedString(policyExSignOffDate)).append("\n");
    sb.append("    systemDate: ").append(toIndentedString(systemDate)).append("\n");
    sb.append("    vetoFlag: ").append(toIndentedString(vetoFlag)).append("\n");
    sb.append("    numberOfSubmission: ").append(toIndentedString(numberOfSubmission)).append("\n");
    sb.append("    randomNo1: ").append(toIndentedString(randomNo1)).append("\n");
    sb.append("    randomNo2: ").append(toIndentedString(randomNo2)).append("\n");
    sb.append("    randomNo3: ").append(toIndentedString(randomNo3)).append("\n");
    sb.append("    fraudApplicantFlag: ").append(toIndentedString(fraudApplicantFlag)).append("\n");
    sb.append("    fraudCompanyFlag: ").append(toIndentedString(fraudCompanyFlag)).append("\n");
    sb.append("    executeAction: ").append(toIndentedString(executeAction)).append("\n");
    sb.append("    processAction: ").append(toIndentedString(processAction)).append("\n");
    sb.append("    fullFraudFlag: ").append(toIndentedString(fullFraudFlag)).append("\n");
    sb.append("    applications: ").append(toIndentedString(applications)).append("\n");
    sb.append("    referencePersons: ").append(toIndentedString(referencePersons)).append("\n");
    sb.append("    saleInfos: ").append(toIndentedString(saleInfos)).append("\n");
    sb.append("    personalInfos: ").append(toIndentedString(personalInfos)).append("\n");
    sb.append("    cardHierarchys: ").append(toIndentedString(cardHierarchys)).append("\n");
    sb.append("    capPorts: ").append(toIndentedString(capPorts)).append("\n");
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

