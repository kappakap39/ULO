package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.DocumentSuggestions;
import com.ava.flp.eapp.iib.model.IncomeCalculations;
import com.ava.flp.eapp.iib.model.IncomeScreens;
import com.ava.flp.eapp.iib.model.IncomeSources;
import com.ava.flp.eapp.iib.model.WebVerifications;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VerificationResultPersonalInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-07-11T12:24:40.224+07:00")

public class VerificationResultPersonalInfos   {
  @JsonProperty("docCompletedFlag")
  private String docCompletedFlag = null;

  @JsonProperty("requiredVerIncomeFlag")
  private String requiredVerIncomeFlag = null;

  @JsonProperty("summaryIncomeResultCode")
  private String summaryIncomeResultCode = null;

  @JsonProperty("verifiedIncomeSource")
  private String verifiedIncomeSource = null;

  @JsonProperty("requiredVerWebFlag")
  private String requiredVerWebFlag = null;

  @JsonProperty("requiredPriorityPass")
  private String requiredPriorityPass = null;

  @JsonProperty("requiredVerCustFlag")
  private String requiredVerCustFlag = null;

  @JsonProperty("requiredVerHrFlag")
  private String requiredVerHrFlag = null;

  @JsonProperty("requiredVerPrivilegeFlag")
  private String requiredVerPrivilegeFlag = null;

  @JsonProperty("incomeScreens")
  private List<IncomeScreens> incomeScreens = null;

  @JsonProperty("incomeSources")
  private List<IncomeSources> incomeSources = null;

  @JsonProperty("incomeCalculations")
  private List<IncomeCalculations> incomeCalculations = null;

  @JsonProperty("documentSuggestions")
  private List<DocumentSuggestions> documentSuggestions = null;

  @JsonProperty("webVerifications")
  private List<WebVerifications> webVerifications = null;

  public VerificationResultPersonalInfos docCompletedFlag(String docCompletedFlag) {
    this.docCompletedFlag = docCompletedFlag;
    return this;
  }

   /**
   * Get docCompletedFlag
   * @return docCompletedFlag
  **/
  @ApiModelProperty(value = "")


  public String getDocCompletedFlag() {
    return docCompletedFlag;
  }

  public void setDocCompletedFlag(String docCompletedFlag) {
    this.docCompletedFlag = docCompletedFlag;
  }

  public VerificationResultPersonalInfos requiredVerIncomeFlag(String requiredVerIncomeFlag) {
    this.requiredVerIncomeFlag = requiredVerIncomeFlag;
    return this;
  }

   /**
   * Get requiredVerIncomeFlag
   * @return requiredVerIncomeFlag
  **/
  @ApiModelProperty(value = "")


  public String getRequiredVerIncomeFlag() {
    return requiredVerIncomeFlag;
  }

  public void setRequiredVerIncomeFlag(String requiredVerIncomeFlag) {
    this.requiredVerIncomeFlag = requiredVerIncomeFlag;
  }

  public VerificationResultPersonalInfos summaryIncomeResultCode(String summaryIncomeResultCode) {
    this.summaryIncomeResultCode = summaryIncomeResultCode;
    return this;
  }

   /**
   * Get summaryIncomeResultCode
   * @return summaryIncomeResultCode
  **/
  @ApiModelProperty(value = "")


  public String getSummaryIncomeResultCode() {
    return summaryIncomeResultCode;
  }

  public void setSummaryIncomeResultCode(String summaryIncomeResultCode) {
    this.summaryIncomeResultCode = summaryIncomeResultCode;
  }

  public VerificationResultPersonalInfos verifiedIncomeSource(String verifiedIncomeSource) {
    this.verifiedIncomeSource = verifiedIncomeSource;
    return this;
  }

   /**
   * Get verifiedIncomeSource
   * @return verifiedIncomeSource
  **/
  @ApiModelProperty(value = "")


  public String getVerifiedIncomeSource() {
    return verifiedIncomeSource;
  }

  public void setVerifiedIncomeSource(String verifiedIncomeSource) {
    this.verifiedIncomeSource = verifiedIncomeSource;
  }

  public VerificationResultPersonalInfos requiredVerWebFlag(String requiredVerWebFlag) {
    this.requiredVerWebFlag = requiredVerWebFlag;
    return this;
  }

   /**
   * Get requiredVerWebFlag
   * @return requiredVerWebFlag
  **/
  @ApiModelProperty(value = "")


  public String getRequiredVerWebFlag() {
    return requiredVerWebFlag;
  }

  public void setRequiredVerWebFlag(String requiredVerWebFlag) {
    this.requiredVerWebFlag = requiredVerWebFlag;
  }

  public VerificationResultPersonalInfos requiredPriorityPass(String requiredPriorityPass) {
    this.requiredPriorityPass = requiredPriorityPass;
    return this;
  }

   /**
   * Get requiredPriorityPass
   * @return requiredPriorityPass
  **/
  @ApiModelProperty(value = "")


  public String getRequiredPriorityPass() {
    return requiredPriorityPass;
  }

  public void setRequiredPriorityPass(String requiredPriorityPass) {
    this.requiredPriorityPass = requiredPriorityPass;
  }

  public VerificationResultPersonalInfos requiredVerCustFlag(String requiredVerCustFlag) {
    this.requiredVerCustFlag = requiredVerCustFlag;
    return this;
  }

   /**
   * Get requiredVerCustFlag
   * @return requiredVerCustFlag
  **/
  @ApiModelProperty(value = "")


  public String getRequiredVerCustFlag() {
    return requiredVerCustFlag;
  }

  public void setRequiredVerCustFlag(String requiredVerCustFlag) {
    this.requiredVerCustFlag = requiredVerCustFlag;
  }

  public VerificationResultPersonalInfos requiredVerHrFlag(String requiredVerHrFlag) {
    this.requiredVerHrFlag = requiredVerHrFlag;
    return this;
  }

   /**
   * Get requiredVerHrFlag
   * @return requiredVerHrFlag
  **/
  @ApiModelProperty(value = "")


  public String getRequiredVerHrFlag() {
    return requiredVerHrFlag;
  }

  public void setRequiredVerHrFlag(String requiredVerHrFlag) {
    this.requiredVerHrFlag = requiredVerHrFlag;
  }

  public VerificationResultPersonalInfos requiredVerPrivilegeFlag(String requiredVerPrivilegeFlag) {
    this.requiredVerPrivilegeFlag = requiredVerPrivilegeFlag;
    return this;
  }

   /**
   * Get requiredVerPrivilegeFlag
   * @return requiredVerPrivilegeFlag
  **/
  @ApiModelProperty(value = "")


  public String getRequiredVerPrivilegeFlag() {
    return requiredVerPrivilegeFlag;
  }

  public void setRequiredVerPrivilegeFlag(String requiredVerPrivilegeFlag) {
    this.requiredVerPrivilegeFlag = requiredVerPrivilegeFlag;
  }

  public VerificationResultPersonalInfos incomeScreens(List<IncomeScreens> incomeScreens) {
    this.incomeScreens = incomeScreens;
    return this;
  }

  public VerificationResultPersonalInfos addIncomeScreensItem(IncomeScreens incomeScreensItem) {
    if (this.incomeScreens == null) {
      this.incomeScreens = new ArrayList<IncomeScreens>();
    }
    this.incomeScreens.add(incomeScreensItem);
    return this;
  }

   /**
   * Get incomeScreens
   * @return incomeScreens
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<IncomeScreens> getIncomeScreens() {
    return incomeScreens;
  }

  public void setIncomeScreens(List<IncomeScreens> incomeScreens) {
    this.incomeScreens = incomeScreens;
  }

  public VerificationResultPersonalInfos incomeSources(List<IncomeSources> incomeSources) {
    this.incomeSources = incomeSources;
    return this;
  }

  public VerificationResultPersonalInfos addIncomeSourcesItem(IncomeSources incomeSourcesItem) {
    if (this.incomeSources == null) {
      this.incomeSources = new ArrayList<IncomeSources>();
    }
    this.incomeSources.add(incomeSourcesItem);
    return this;
  }

   /**
   * Get incomeSources
   * @return incomeSources
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<IncomeSources> getIncomeSources() {
    return incomeSources;
  }

  public void setIncomeSources(List<IncomeSources> incomeSources) {
    this.incomeSources = incomeSources;
  }

  public VerificationResultPersonalInfos incomeCalculations(List<IncomeCalculations> incomeCalculations) {
    this.incomeCalculations = incomeCalculations;
    return this;
  }

  public VerificationResultPersonalInfos addIncomeCalculationsItem(IncomeCalculations incomeCalculationsItem) {
    if (this.incomeCalculations == null) {
      this.incomeCalculations = new ArrayList<IncomeCalculations>();
    }
    this.incomeCalculations.add(incomeCalculationsItem);
    return this;
  }

   /**
   * Get incomeCalculations
   * @return incomeCalculations
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<IncomeCalculations> getIncomeCalculations() {
    return incomeCalculations;
  }

  public void setIncomeCalculations(List<IncomeCalculations> incomeCalculations) {
    this.incomeCalculations = incomeCalculations;
  }

  public VerificationResultPersonalInfos documentSuggestions(List<DocumentSuggestions> documentSuggestions) {
    this.documentSuggestions = documentSuggestions;
    return this;
  }

  public VerificationResultPersonalInfos addDocumentSuggestionsItem(DocumentSuggestions documentSuggestionsItem) {
    if (this.documentSuggestions == null) {
      this.documentSuggestions = new ArrayList<DocumentSuggestions>();
    }
    this.documentSuggestions.add(documentSuggestionsItem);
    return this;
  }

   /**
   * Get documentSuggestions
   * @return documentSuggestions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<DocumentSuggestions> getDocumentSuggestions() {
    return documentSuggestions;
  }

  public void setDocumentSuggestions(List<DocumentSuggestions> documentSuggestions) {
    this.documentSuggestions = documentSuggestions;
  }

  public VerificationResultPersonalInfos webVerifications(List<WebVerifications> webVerifications) {
    this.webVerifications = webVerifications;
    return this;
  }

  public VerificationResultPersonalInfos addWebVerificationsItem(WebVerifications webVerificationsItem) {
    if (this.webVerifications == null) {
      this.webVerifications = new ArrayList<WebVerifications>();
    }
    this.webVerifications.add(webVerificationsItem);
    return this;
  }

   /**
   * Get webVerifications
   * @return webVerifications
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<WebVerifications> getWebVerifications() {
    return webVerifications;
  }

  public void setWebVerifications(List<WebVerifications> webVerifications) {
    this.webVerifications = webVerifications;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VerificationResultPersonalInfos verificationResultPersonalInfos = (VerificationResultPersonalInfos) o;
    return Objects.equals(this.docCompletedFlag, verificationResultPersonalInfos.docCompletedFlag) &&
        Objects.equals(this.requiredVerIncomeFlag, verificationResultPersonalInfos.requiredVerIncomeFlag) &&
        Objects.equals(this.summaryIncomeResultCode, verificationResultPersonalInfos.summaryIncomeResultCode) &&
        Objects.equals(this.verifiedIncomeSource, verificationResultPersonalInfos.verifiedIncomeSource) &&
        Objects.equals(this.requiredVerWebFlag, verificationResultPersonalInfos.requiredVerWebFlag) &&
        Objects.equals(this.requiredPriorityPass, verificationResultPersonalInfos.requiredPriorityPass) &&
        Objects.equals(this.requiredVerCustFlag, verificationResultPersonalInfos.requiredVerCustFlag) &&
        Objects.equals(this.requiredVerHrFlag, verificationResultPersonalInfos.requiredVerHrFlag) &&
        Objects.equals(this.requiredVerPrivilegeFlag, verificationResultPersonalInfos.requiredVerPrivilegeFlag) &&
        Objects.equals(this.incomeScreens, verificationResultPersonalInfos.incomeScreens) &&
        Objects.equals(this.incomeSources, verificationResultPersonalInfos.incomeSources) &&
        Objects.equals(this.incomeCalculations, verificationResultPersonalInfos.incomeCalculations) &&
        Objects.equals(this.documentSuggestions, verificationResultPersonalInfos.documentSuggestions) &&
        Objects.equals(this.webVerifications, verificationResultPersonalInfos.webVerifications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(docCompletedFlag, requiredVerIncomeFlag, summaryIncomeResultCode, verifiedIncomeSource, requiredVerWebFlag, requiredPriorityPass, requiredVerCustFlag, requiredVerHrFlag, requiredVerPrivilegeFlag, incomeScreens, incomeSources, incomeCalculations, documentSuggestions, webVerifications);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VerificationResultPersonalInfos {\n");
    
    sb.append("    docCompletedFlag: ").append(toIndentedString(docCompletedFlag)).append("\n");
    sb.append("    requiredVerIncomeFlag: ").append(toIndentedString(requiredVerIncomeFlag)).append("\n");
    sb.append("    summaryIncomeResultCode: ").append(toIndentedString(summaryIncomeResultCode)).append("\n");
    sb.append("    verifiedIncomeSource: ").append(toIndentedString(verifiedIncomeSource)).append("\n");
    sb.append("    requiredVerWebFlag: ").append(toIndentedString(requiredVerWebFlag)).append("\n");
    sb.append("    requiredPriorityPass: ").append(toIndentedString(requiredPriorityPass)).append("\n");
    sb.append("    requiredVerCustFlag: ").append(toIndentedString(requiredVerCustFlag)).append("\n");
    sb.append("    requiredVerHrFlag: ").append(toIndentedString(requiredVerHrFlag)).append("\n");
    sb.append("    requiredVerPrivilegeFlag: ").append(toIndentedString(requiredVerPrivilegeFlag)).append("\n");
    sb.append("    incomeScreens: ").append(toIndentedString(incomeScreens)).append("\n");
    sb.append("    incomeSources: ").append(toIndentedString(incomeSources)).append("\n");
    sb.append("    incomeCalculations: ").append(toIndentedString(incomeCalculations)).append("\n");
    sb.append("    documentSuggestions: ").append(toIndentedString(documentSuggestions)).append("\n");
    sb.append("    webVerifications: ").append(toIndentedString(webVerifications)).append("\n");
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

