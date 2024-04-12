package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.Error;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KBankHeader
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KBankHeader   {
  @JsonProperty("funcNm")
  private String funcNm = null;

  @JsonProperty("rqUID")
  private String rqUID = null;

  @JsonProperty("rqDt")
  private DateTime rqDt = null;

  @JsonProperty("rqAppId")
  private String rqAppId = null;

  @JsonProperty("userId")
  private String userId = null;

  @JsonProperty("terminalId")
  private String terminalId = null;

  @JsonProperty("userLangPref")
  private String userLangPref = null;

  @JsonProperty("corrID")
  private String corrID = null;

  @JsonProperty("rsAppId")
  private String rsAppId = null;

  @JsonProperty("rsUID")
  private String rsUID = null;

  @JsonProperty("rsDt")
  private DateTime rsDt = null;

  @JsonProperty("statusCode")
  private String statusCode = null;

  @JsonProperty("errors")
  private List<Error> errors = null;

  public KBankHeader funcNm(String funcNm) {
    this.funcNm = funcNm;
    return this;
  }

   /**
   * Get funcNm
   * @return funcNm
  **/
  @ApiModelProperty(value = "")


  public String getFuncNm() {
    return funcNm;
  }

  public void setFuncNm(String funcNm) {
    this.funcNm = funcNm;
  }

  public KBankHeader rqUID(String rqUID) {
    this.rqUID = rqUID;
    return this;
  }

   /**
   * Get rqUID
   * @return rqUID
  **/
  @ApiModelProperty(value = "")


  public String getRqUID() {
    return rqUID;
  }

  public void setRqUID(String rqUID) {
    this.rqUID = rqUID;
  }

  public KBankHeader rqDt(DateTime rqDt) {
    this.rqDt = rqDt;
    return this;
  }

   /**
   * Get rqDt
   * @return rqDt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getRqDt() {
    return rqDt;
  }

  public void setRqDt(DateTime rqDt) {
    this.rqDt = rqDt;
  }

  public KBankHeader rqAppId(String rqAppId) {
    this.rqAppId = rqAppId;
    return this;
  }

   /**
   * Get rqAppId
   * @return rqAppId
  **/
  @ApiModelProperty(value = "")


  public String getRqAppId() {
    return rqAppId;
  }

  public void setRqAppId(String rqAppId) {
    this.rqAppId = rqAppId;
  }

  public KBankHeader userId(String userId) {
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(value = "")


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public KBankHeader terminalId(String terminalId) {
    this.terminalId = terminalId;
    return this;
  }

   /**
   * Get terminalId
   * @return terminalId
  **/
  @ApiModelProperty(value = "")


  public String getTerminalId() {
    return terminalId;
  }

  public void setTerminalId(String terminalId) {
    this.terminalId = terminalId;
  }

  public KBankHeader userLangPref(String userLangPref) {
    this.userLangPref = userLangPref;
    return this;
  }

   /**
   * Get userLangPref
   * @return userLangPref
  **/
  @ApiModelProperty(value = "")


  public String getUserLangPref() {
    return userLangPref;
  }

  public void setUserLangPref(String userLangPref) {
    this.userLangPref = userLangPref;
  }

  public KBankHeader corrID(String corrID) {
    this.corrID = corrID;
    return this;
  }

   /**
   * Get corrID
   * @return corrID
  **/
  @ApiModelProperty(value = "")


  public String getCorrID() {
    return corrID;
  }

  public void setCorrID(String corrID) {
    this.corrID = corrID;
  }

  public KBankHeader rsAppId(String rsAppId) {
    this.rsAppId = rsAppId;
    return this;
  }

   /**
   * Get rsAppId
   * @return rsAppId
  **/
  @ApiModelProperty(value = "")


  public String getRsAppId() {
    return rsAppId;
  }

  public void setRsAppId(String rsAppId) {
    this.rsAppId = rsAppId;
  }

  public KBankHeader rsUID(String rsUID) {
    this.rsUID = rsUID;
    return this;
  }

   /**
   * Get rsUID
   * @return rsUID
  **/
  @ApiModelProperty(value = "")


  public String getRsUID() {
    return rsUID;
  }

  public void setRsUID(String rsUID) {
    this.rsUID = rsUID;
  }

  public KBankHeader rsDt(DateTime rsDt) {
    this.rsDt = rsDt;
    return this;
  }

   /**
   * Get rsDt
   * @return rsDt
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getRsDt() {
    return rsDt;
  }

  public void setRsDt(DateTime rsDt) {
    this.rsDt = rsDt;
  }

  public KBankHeader statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }

   /**
   * Get statusCode
   * @return statusCode
  **/
  @ApiModelProperty(value = "")


  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public KBankHeader errors(List<Error> errors) {
    this.errors = errors;
    return this;
  }

  public KBankHeader addErrorsItem(Error errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<Error>();
    }
    this.errors.add(errorsItem);
    return this;
  }

   /**
   * Get errors
   * @return errors
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Error> getErrors() {
    return errors;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KBankHeader kbankHeader = (KBankHeader) o;
    return Objects.equals(this.funcNm, kbankHeader.funcNm) &&
        Objects.equals(this.rqUID, kbankHeader.rqUID) &&
        Objects.equals(this.rqDt, kbankHeader.rqDt) &&
        Objects.equals(this.rqAppId, kbankHeader.rqAppId) &&
        Objects.equals(this.userId, kbankHeader.userId) &&
        Objects.equals(this.terminalId, kbankHeader.terminalId) &&
        Objects.equals(this.userLangPref, kbankHeader.userLangPref) &&
        Objects.equals(this.corrID, kbankHeader.corrID) &&
        Objects.equals(this.rsAppId, kbankHeader.rsAppId) &&
        Objects.equals(this.rsUID, kbankHeader.rsUID) &&
        Objects.equals(this.rsDt, kbankHeader.rsDt) &&
        Objects.equals(this.statusCode, kbankHeader.statusCode) &&
        Objects.equals(this.errors, kbankHeader.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(funcNm, rqUID, rqDt, rqAppId, userId, terminalId, userLangPref, corrID, rsAppId, rsUID, rsDt, statusCode, errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KBankHeader {\n");
    
    sb.append("    funcNm: ").append(toIndentedString(funcNm)).append("\n");
    sb.append("    rqUID: ").append(toIndentedString(rqUID)).append("\n");
    sb.append("    rqDt: ").append(toIndentedString(rqDt)).append("\n");
    sb.append("    rqAppId: ").append(toIndentedString(rqAppId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    terminalId: ").append(toIndentedString(terminalId)).append("\n");
    sb.append("    userLangPref: ").append(toIndentedString(userLangPref)).append("\n");
    sb.append("    corrID: ").append(toIndentedString(corrID)).append("\n");
    sb.append("    rsAppId: ").append(toIndentedString(rsAppId)).append("\n");
    sb.append("    rsUID: ").append(toIndentedString(rsUID)).append("\n");
    sb.append("    rsDt: ").append(toIndentedString(rsDt)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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

