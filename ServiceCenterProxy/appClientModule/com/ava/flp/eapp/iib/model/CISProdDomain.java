package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CISProdDomain
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class CISProdDomain   {
  @JsonProperty("prodCode")
  private String prodCode = null;

  @JsonProperty("subProdCode")
  private String subProdCode = null;

  @JsonProperty("prodDesc")
  private String prodDesc = null;

  @JsonProperty("domainCode")
  private String domainCode = null;

  @JsonProperty("totProdAcctCnt")
  private String totProdAcctCnt = null;

  @JsonProperty("totInactvProdCnt")
  private String totInactvProdCnt = null;

  @JsonProperty("unitDesc")
  private String unitDesc = null;

  @JsonProperty("limAmt")
  private String limAmt = null;

  @JsonProperty("ostAmt")
  private String ostAmt = null;

  @JsonProperty("allowFlag")
  private String allowFlag = null;

  @JsonProperty("CISPIMProdCode")
  private String ciSPIMProdCode = null;

  public CISProdDomain prodCode(String prodCode) {
    this.prodCode = prodCode;
    return this;
  }

   /**
   * Get prodCode
   * @return prodCode
  **/
  @ApiModelProperty(value = "")


  public String getProdCode() {
    return prodCode;
  }

  public void setProdCode(String prodCode) {
    this.prodCode = prodCode;
  }

  public CISProdDomain subProdCode(String subProdCode) {
    this.subProdCode = subProdCode;
    return this;
  }

   /**
   * Get subProdCode
   * @return subProdCode
  **/
  @ApiModelProperty(value = "")


  public String getSubProdCode() {
    return subProdCode;
  }

  public void setSubProdCode(String subProdCode) {
    this.subProdCode = subProdCode;
  }

  public CISProdDomain prodDesc(String prodDesc) {
    this.prodDesc = prodDesc;
    return this;
  }

   /**
   * Get prodDesc
   * @return prodDesc
  **/
  @ApiModelProperty(value = "")


  public String getProdDesc() {
    return prodDesc;
  }

  public void setProdDesc(String prodDesc) {
    this.prodDesc = prodDesc;
  }

  public CISProdDomain domainCode(String domainCode) {
    this.domainCode = domainCode;
    return this;
  }

   /**
   * Get domainCode
   * @return domainCode
  **/
  @ApiModelProperty(value = "")


  public String getDomainCode() {
    return domainCode;
  }

  public void setDomainCode(String domainCode) {
    this.domainCode = domainCode;
  }

  public CISProdDomain totProdAcctCnt(String totProdAcctCnt) {
    this.totProdAcctCnt = totProdAcctCnt;
    return this;
  }

   /**
   * Get totProdAcctCnt
   * @return totProdAcctCnt
  **/
  @ApiModelProperty(value = "")


  public String getTotProdAcctCnt() {
    return totProdAcctCnt;
  }

  public void setTotProdAcctCnt(String totProdAcctCnt) {
    this.totProdAcctCnt = totProdAcctCnt;
  }

  public CISProdDomain totInactvProdCnt(String totInactvProdCnt) {
    this.totInactvProdCnt = totInactvProdCnt;
    return this;
  }

   /**
   * Get totInactvProdCnt
   * @return totInactvProdCnt
  **/
  @ApiModelProperty(value = "")


  public String getTotInactvProdCnt() {
    return totInactvProdCnt;
  }

  public void setTotInactvProdCnt(String totInactvProdCnt) {
    this.totInactvProdCnt = totInactvProdCnt;
  }

  public CISProdDomain unitDesc(String unitDesc) {
    this.unitDesc = unitDesc;
    return this;
  }

   /**
   * Get unitDesc
   * @return unitDesc
  **/
  @ApiModelProperty(value = "")


  public String getUnitDesc() {
    return unitDesc;
  }

  public void setUnitDesc(String unitDesc) {
    this.unitDesc = unitDesc;
  }

  public CISProdDomain limAmt(String limAmt) {
    this.limAmt = limAmt;
    return this;
  }

   /**
   * Get limAmt
   * @return limAmt
  **/
  @ApiModelProperty(value = "")


  public String getLimAmt() {
    return limAmt;
  }

  public void setLimAmt(String limAmt) {
    this.limAmt = limAmt;
  }

  public CISProdDomain ostAmt(String ostAmt) {
    this.ostAmt = ostAmt;
    return this;
  }

   /**
   * Get ostAmt
   * @return ostAmt
  **/
  @ApiModelProperty(value = "")


  public String getOstAmt() {
    return ostAmt;
  }

  public void setOstAmt(String ostAmt) {
    this.ostAmt = ostAmt;
  }

  public CISProdDomain allowFlag(String allowFlag) {
    this.allowFlag = allowFlag;
    return this;
  }

   /**
   * Get allowFlag
   * @return allowFlag
  **/
  @ApiModelProperty(value = "")


  public String getAllowFlag() {
    return allowFlag;
  }

  public void setAllowFlag(String allowFlag) {
    this.allowFlag = allowFlag;
  }

  public CISProdDomain ciSPIMProdCode(String ciSPIMProdCode) {
    this.ciSPIMProdCode = ciSPIMProdCode;
    return this;
  }

   /**
   * Get ciSPIMProdCode
   * @return ciSPIMProdCode
  **/
  @ApiModelProperty(value = "")


  public String getCiSPIMProdCode() {
    return ciSPIMProdCode;
  }

  public void setCiSPIMProdCode(String ciSPIMProdCode) {
    this.ciSPIMProdCode = ciSPIMProdCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CISProdDomain ciSProdDomain = (CISProdDomain) o;
    return Objects.equals(this.prodCode, ciSProdDomain.prodCode) &&
        Objects.equals(this.subProdCode, ciSProdDomain.subProdCode) &&
        Objects.equals(this.prodDesc, ciSProdDomain.prodDesc) &&
        Objects.equals(this.domainCode, ciSProdDomain.domainCode) &&
        Objects.equals(this.totProdAcctCnt, ciSProdDomain.totProdAcctCnt) &&
        Objects.equals(this.totInactvProdCnt, ciSProdDomain.totInactvProdCnt) &&
        Objects.equals(this.unitDesc, ciSProdDomain.unitDesc) &&
        Objects.equals(this.limAmt, ciSProdDomain.limAmt) &&
        Objects.equals(this.ostAmt, ciSProdDomain.ostAmt) &&
        Objects.equals(this.allowFlag, ciSProdDomain.allowFlag) &&
        Objects.equals(this.ciSPIMProdCode, ciSProdDomain.ciSPIMProdCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prodCode, subProdCode, prodDesc, domainCode, totProdAcctCnt, totInactvProdCnt, unitDesc, limAmt, ostAmt, allowFlag, ciSPIMProdCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CISProdDomain {\n");
    
    sb.append("    prodCode: ").append(toIndentedString(prodCode)).append("\n");
    sb.append("    subProdCode: ").append(toIndentedString(subProdCode)).append("\n");
    sb.append("    prodDesc: ").append(toIndentedString(prodDesc)).append("\n");
    sb.append("    domainCode: ").append(toIndentedString(domainCode)).append("\n");
    sb.append("    totProdAcctCnt: ").append(toIndentedString(totProdAcctCnt)).append("\n");
    sb.append("    totInactvProdCnt: ").append(toIndentedString(totInactvProdCnt)).append("\n");
    sb.append("    unitDesc: ").append(toIndentedString(unitDesc)).append("\n");
    sb.append("    limAmt: ").append(toIndentedString(limAmt)).append("\n");
    sb.append("    ostAmt: ").append(toIndentedString(ostAmt)).append("\n");
    sb.append("    allowFlag: ").append(toIndentedString(allowFlag)).append("\n");
    sb.append("    ciSPIMProdCode: ").append(toIndentedString(ciSPIMProdCode)).append("\n");
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

