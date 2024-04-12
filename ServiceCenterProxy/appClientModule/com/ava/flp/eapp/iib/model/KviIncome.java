package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KviIncome
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KviIncome   {
  @JsonProperty("kviFid")
  private String kviFid = null;

  @JsonProperty("kviId")
  private String kviId = null;

  @JsonProperty("percentChequeReturn")
  private BigDecimal percentChequeReturn = null;

  @JsonProperty("tokenId")
  private String tokenId = null;

  @JsonProperty("verifiedIncome")
  private BigDecimal verifiedIncome = null;

  public KviIncome kviFid(String kviFid) {
    this.kviFid = kviFid;
    return this;
  }

   /**
   * Get kviFid
   * @return kviFid
  **/
  @ApiModelProperty(value = "")


  public String getKviFid() {
    return kviFid;
  }

  public void setKviFid(String kviFid) {
    this.kviFid = kviFid;
  }

  public KviIncome kviId(String kviId) {
    this.kviId = kviId;
    return this;
  }

   /**
   * Get kviId
   * @return kviId
  **/
  @ApiModelProperty(value = "")


  public String getKviId() {
    return kviId;
  }

  public void setKviId(String kviId) {
    this.kviId = kviId;
  }

  public KviIncome percentChequeReturn(BigDecimal percentChequeReturn) {
    this.percentChequeReturn = percentChequeReturn;
    return this;
  }

   /**
   * Get percentChequeReturn
   * @return percentChequeReturn
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPercentChequeReturn() {
    return percentChequeReturn;
  }

  public void setPercentChequeReturn(BigDecimal percentChequeReturn) {
    this.percentChequeReturn = percentChequeReturn;
  }

  public KviIncome tokenId(String tokenId) {
    this.tokenId = tokenId;
    return this;
  }

   /**
   * Get tokenId
   * @return tokenId
  **/
  @ApiModelProperty(value = "")


  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public KviIncome verifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
    return this;
  }

   /**
   * Get verifiedIncome
   * @return verifiedIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getVerifiedIncome() {
    return verifiedIncome;
  }

  public void setVerifiedIncome(BigDecimal verifiedIncome) {
    this.verifiedIncome = verifiedIncome;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KviIncome kviIncome = (KviIncome) o;
    return Objects.equals(this.kviFid, kviIncome.kviFid) &&
        Objects.equals(this.kviId, kviIncome.kviId) &&
        Objects.equals(this.percentChequeReturn, kviIncome.percentChequeReturn) &&
        Objects.equals(this.tokenId, kviIncome.tokenId) &&
        Objects.equals(this.verifiedIncome, kviIncome.verifiedIncome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kviFid, kviId, percentChequeReturn, tokenId, verifiedIncome);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KviIncome {\n");
    
    sb.append("    kviFid: ").append(toIndentedString(kviFid)).append("\n");
    sb.append("    kviId: ").append(toIndentedString(kviId)).append("\n");
    sb.append("    percentChequeReturn: ").append(toIndentedString(percentChequeReturn)).append("\n");
    sb.append("    tokenId: ").append(toIndentedString(tokenId)).append("\n");
    sb.append("    verifiedIncome: ").append(toIndentedString(verifiedIncome)).append("\n");
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

