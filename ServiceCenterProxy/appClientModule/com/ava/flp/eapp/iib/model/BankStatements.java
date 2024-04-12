package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.BankStatementDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankStatements
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class BankStatements   {
  @JsonProperty("additionalCode")
  private String additionalCode = null;

  @JsonProperty("bankCode")
  private String bankCode = null;

  @JsonProperty("bankStatementId")
  private String bankStatementId = null;

  @JsonProperty("statementCode")
  private String statementCode = null;

  @JsonProperty("bankStatementDetails")
  private List<BankStatementDetails> bankStatementDetails = null;

  public BankStatements additionalCode(String additionalCode) {
    this.additionalCode = additionalCode;
    return this;
  }

   /**
   * Get additionalCode
   * @return additionalCode
  **/
  @ApiModelProperty(value = "")


  public String getAdditionalCode() {
    return additionalCode;
  }

  public void setAdditionalCode(String additionalCode) {
    this.additionalCode = additionalCode;
  }

  public BankStatements bankCode(String bankCode) {
    this.bankCode = bankCode;
    return this;
  }

   /**
   * Get bankCode
   * @return bankCode
  **/
  @ApiModelProperty(value = "")


  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public BankStatements bankStatementId(String bankStatementId) {
    this.bankStatementId = bankStatementId;
    return this;
  }

   /**
   * Get bankStatementId
   * @return bankStatementId
  **/
  @ApiModelProperty(value = "")


  public String getBankStatementId() {
    return bankStatementId;
  }

  public void setBankStatementId(String bankStatementId) {
    this.bankStatementId = bankStatementId;
  }

  public BankStatements statementCode(String statementCode) {
    this.statementCode = statementCode;
    return this;
  }

   /**
   * Get statementCode
   * @return statementCode
  **/
  @ApiModelProperty(value = "")


  public String getStatementCode() {
    return statementCode;
  }

  public void setStatementCode(String statementCode) {
    this.statementCode = statementCode;
  }

  public BankStatements bankStatementDetails(List<BankStatementDetails> bankStatementDetails) {
    this.bankStatementDetails = bankStatementDetails;
    return this;
  }

  public BankStatements addBankStatementDetailsItem(BankStatementDetails bankStatementDetailsItem) {
    if (this.bankStatementDetails == null) {
      this.bankStatementDetails = new ArrayList<BankStatementDetails>();
    }
    this.bankStatementDetails.add(bankStatementDetailsItem);
    return this;
  }

   /**
   * Get bankStatementDetails
   * @return bankStatementDetails
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<BankStatementDetails> getBankStatementDetails() {
    return bankStatementDetails;
  }

  public void setBankStatementDetails(List<BankStatementDetails> bankStatementDetails) {
    this.bankStatementDetails = bankStatementDetails;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankStatements bankStatements = (BankStatements) o;
    return Objects.equals(this.additionalCode, bankStatements.additionalCode) &&
        Objects.equals(this.bankCode, bankStatements.bankCode) &&
        Objects.equals(this.bankStatementId, bankStatements.bankStatementId) &&
        Objects.equals(this.statementCode, bankStatements.statementCode) &&
        Objects.equals(this.bankStatementDetails, bankStatements.bankStatementDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(additionalCode, bankCode, bankStatementId, statementCode, bankStatementDetails);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankStatements {\n");
    
    sb.append("    additionalCode: ").append(toIndentedString(additionalCode)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
    sb.append("    bankStatementId: ").append(toIndentedString(bankStatementId)).append("\n");
    sb.append("    statementCode: ").append(toIndentedString(statementCode)).append("\n");
    sb.append("    bankStatementDetails: ").append(toIndentedString(bankStatementDetails)).append("\n");
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

