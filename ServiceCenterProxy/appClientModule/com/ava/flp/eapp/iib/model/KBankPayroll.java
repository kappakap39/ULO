package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * KBankPayroll
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class KBankPayroll   {
  @JsonProperty("foundKBankPayrollFlag")
  private String foundKBankPayrollFlag = null;

  @JsonProperty("cisNo")
  private String cisNo = null;

  @JsonProperty("idNo")
  private String idNo = null;

  @JsonProperty("thFirstName")
  private String thFirstName = null;

  @JsonProperty("thLastName")
  private String thLastName = null;

  @JsonProperty("companyName")
  private String companyName = null;

  @JsonProperty("paymentAccountNo")
  private String paymentAccountNo = null;

  @JsonProperty("paymentChannel")
  private String paymentChannel = null;

  @JsonProperty("incomeMonth1")
  private BigDecimal incomeMonth1 = null;

  @JsonProperty("incomeMonth2")
  private BigDecimal incomeMonth2 = null;

  @JsonProperty("incomeMonth3")
  private BigDecimal incomeMonth3 = null;

  @JsonProperty("incomeMonth4")
  private BigDecimal incomeMonth4 = null;

  @JsonProperty("incomeMonth5")
  private BigDecimal incomeMonth5 = null;

  @JsonProperty("incomeMonth6")
  private BigDecimal incomeMonth6 = null;

  @JsonProperty("incomeTotal")
  private String incomeTotal = null;

  @JsonProperty("sso")
  private BigDecimal sso = null;

  @JsonProperty("averageIncome3Month")
  private BigDecimal averageIncome3Month = null;

  @JsonProperty("pvf3Month")
  private BigDecimal pvf3Month = null;

  @JsonProperty("grossIncome3Month")
  private BigDecimal grossIncome3Month = null;

  @JsonProperty("averageIncome6Month")
  private BigDecimal averageIncome6Month = null;

  @JsonProperty("pvf6Month")
  private BigDecimal pvf6Month = null;

  @JsonProperty("grossIncome6Month")
  private BigDecimal grossIncome6Month = null;

  @JsonProperty("averageIncome12Month")
  private BigDecimal averageIncome12Month = null;

  @JsonProperty("pvf12Month")
  private BigDecimal pvf12Month = null;

  @JsonProperty("grossIncome12Month")
  private BigDecimal grossIncome12Month = null;

  @JsonProperty("salaryDate")
  private DateTime salaryDate = null;

  @JsonProperty("projectCode")
  private String projectCode = null;

  @JsonProperty("welfareFlag")
  private String welfareFlag = null;

  @JsonProperty("kecPayrollFlag")
  private String kecPayrollFlag = null;

  @JsonProperty("ccPayrollFlag")
  private String ccPayrollFlag = null;

  @JsonProperty("positionDate")
  private DateTime positionDate = null;

  @JsonProperty("numberOfAvgIncome")
  private BigDecimal numberOfAvgIncome = null;

  public KBankPayroll foundKBankPayrollFlag(String foundKBankPayrollFlag) {
    this.foundKBankPayrollFlag = foundKBankPayrollFlag;
    return this;
  }

   /**
   * Get foundKBankPayrollFlag
   * @return foundKBankPayrollFlag
  **/
  @ApiModelProperty(value = "")


  public String getFoundKBankPayrollFlag() {
    return foundKBankPayrollFlag;
  }

  public void setFoundKBankPayrollFlag(String foundKBankPayrollFlag) {
    this.foundKBankPayrollFlag = foundKBankPayrollFlag;
  }

  public KBankPayroll cisNo(String cisNo) {
    this.cisNo = cisNo;
    return this;
  }

   /**
   * Get cisNo
   * @return cisNo
  **/
  @ApiModelProperty(value = "")


  public String getCisNo() {
    return cisNo;
  }

  public void setCisNo(String cisNo) {
    this.cisNo = cisNo;
  }

  public KBankPayroll idNo(String idNo) {
    this.idNo = idNo;
    return this;
  }

   /**
   * Get idNo
   * @return idNo
  **/
  @ApiModelProperty(value = "")


  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public KBankPayroll thFirstName(String thFirstName) {
    this.thFirstName = thFirstName;
    return this;
  }

   /**
   * Get thFirstName
   * @return thFirstName
  **/
  @ApiModelProperty(value = "")


  public String getThFirstName() {
    return thFirstName;
  }

  public void setThFirstName(String thFirstName) {
    this.thFirstName = thFirstName;
  }

  public KBankPayroll thLastName(String thLastName) {
    this.thLastName = thLastName;
    return this;
  }

   /**
   * Get thLastName
   * @return thLastName
  **/
  @ApiModelProperty(value = "")


  public String getThLastName() {
    return thLastName;
  }

  public void setThLastName(String thLastName) {
    this.thLastName = thLastName;
  }

  public KBankPayroll companyName(String companyName) {
    this.companyName = companyName;
    return this;
  }

   /**
   * Get companyName
   * @return companyName
  **/
  @ApiModelProperty(value = "")


  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public KBankPayroll paymentAccountNo(String paymentAccountNo) {
    this.paymentAccountNo = paymentAccountNo;
    return this;
  }

   /**
   * Get paymentAccountNo
   * @return paymentAccountNo
  **/
  @ApiModelProperty(value = "")


  public String getPaymentAccountNo() {
    return paymentAccountNo;
  }

  public void setPaymentAccountNo(String paymentAccountNo) {
    this.paymentAccountNo = paymentAccountNo;
  }

  public KBankPayroll paymentChannel(String paymentChannel) {
    this.paymentChannel = paymentChannel;
    return this;
  }

   /**
   * Get paymentChannel
   * @return paymentChannel
  **/
  @ApiModelProperty(value = "")


  public String getPaymentChannel() {
    return paymentChannel;
  }

  public void setPaymentChannel(String paymentChannel) {
    this.paymentChannel = paymentChannel;
  }

  public KBankPayroll incomeMonth1(BigDecimal incomeMonth1) {
    this.incomeMonth1 = incomeMonth1;
    return this;
  }

   /**
   * Get incomeMonth1
   * @return incomeMonth1
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeMonth1() {
    return incomeMonth1;
  }

  public void setIncomeMonth1(BigDecimal incomeMonth1) {
    this.incomeMonth1 = incomeMonth1;
  }

  public KBankPayroll incomeMonth2(BigDecimal incomeMonth2) {
    this.incomeMonth2 = incomeMonth2;
    return this;
  }

   /**
   * Get incomeMonth2
   * @return incomeMonth2
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeMonth2() {
    return incomeMonth2;
  }

  public void setIncomeMonth2(BigDecimal incomeMonth2) {
    this.incomeMonth2 = incomeMonth2;
  }

  public KBankPayroll incomeMonth3(BigDecimal incomeMonth3) {
    this.incomeMonth3 = incomeMonth3;
    return this;
  }

   /**
   * Get incomeMonth3
   * @return incomeMonth3
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeMonth3() {
    return incomeMonth3;
  }

  public void setIncomeMonth3(BigDecimal incomeMonth3) {
    this.incomeMonth3 = incomeMonth3;
  }

  public KBankPayroll incomeMonth4(BigDecimal incomeMonth4) {
    this.incomeMonth4 = incomeMonth4;
    return this;
  }

   /**
   * Get incomeMonth4
   * @return incomeMonth4
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeMonth4() {
    return incomeMonth4;
  }

  public void setIncomeMonth4(BigDecimal incomeMonth4) {
    this.incomeMonth4 = incomeMonth4;
  }

  public KBankPayroll incomeMonth5(BigDecimal incomeMonth5) {
    this.incomeMonth5 = incomeMonth5;
    return this;
  }

   /**
   * Get incomeMonth5
   * @return incomeMonth5
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeMonth5() {
    return incomeMonth5;
  }

  public void setIncomeMonth5(BigDecimal incomeMonth5) {
    this.incomeMonth5 = incomeMonth5;
  }

  public KBankPayroll incomeMonth6(BigDecimal incomeMonth6) {
    this.incomeMonth6 = incomeMonth6;
    return this;
  }

   /**
   * Get incomeMonth6
   * @return incomeMonth6
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getIncomeMonth6() {
    return incomeMonth6;
  }

  public void setIncomeMonth6(BigDecimal incomeMonth6) {
    this.incomeMonth6 = incomeMonth6;
  }

  public KBankPayroll incomeTotal(String incomeTotal) {
    this.incomeTotal = incomeTotal;
    return this;
  }

   /**
   * Get incomeTotal
   * @return incomeTotal
  **/
  @ApiModelProperty(value = "")


  public String getIncomeTotal() {
    return incomeTotal;
  }

  public void setIncomeTotal(String incomeTotal) {
    this.incomeTotal = incomeTotal;
  }

  public KBankPayroll sso(BigDecimal sso) {
    this.sso = sso;
    return this;
  }

   /**
   * Get sso
   * @return sso
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSso() {
    return sso;
  }

  public void setSso(BigDecimal sso) {
    this.sso = sso;
  }

  public KBankPayroll averageIncome3Month(BigDecimal averageIncome3Month) {
    this.averageIncome3Month = averageIncome3Month;
    return this;
  }

   /**
   * Get averageIncome3Month
   * @return averageIncome3Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAverageIncome3Month() {
    return averageIncome3Month;
  }

  public void setAverageIncome3Month(BigDecimal averageIncome3Month) {
    this.averageIncome3Month = averageIncome3Month;
  }

  public KBankPayroll pvf3Month(BigDecimal pvf3Month) {
    this.pvf3Month = pvf3Month;
    return this;
  }

   /**
   * Get pvf3Month
   * @return pvf3Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPvf3Month() {
    return pvf3Month;
  }

  public void setPvf3Month(BigDecimal pvf3Month) {
    this.pvf3Month = pvf3Month;
  }

  public KBankPayroll grossIncome3Month(BigDecimal grossIncome3Month) {
    this.grossIncome3Month = grossIncome3Month;
    return this;
  }

   /**
   * Get grossIncome3Month
   * @return grossIncome3Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getGrossIncome3Month() {
    return grossIncome3Month;
  }

  public void setGrossIncome3Month(BigDecimal grossIncome3Month) {
    this.grossIncome3Month = grossIncome3Month;
  }

  public KBankPayroll averageIncome6Month(BigDecimal averageIncome6Month) {
    this.averageIncome6Month = averageIncome6Month;
    return this;
  }

   /**
   * Get averageIncome6Month
   * @return averageIncome6Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAverageIncome6Month() {
    return averageIncome6Month;
  }

  public void setAverageIncome6Month(BigDecimal averageIncome6Month) {
    this.averageIncome6Month = averageIncome6Month;
  }

  public KBankPayroll pvf6Month(BigDecimal pvf6Month) {
    this.pvf6Month = pvf6Month;
    return this;
  }

   /**
   * Get pvf6Month
   * @return pvf6Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPvf6Month() {
    return pvf6Month;
  }

  public void setPvf6Month(BigDecimal pvf6Month) {
    this.pvf6Month = pvf6Month;
  }

  public KBankPayroll grossIncome6Month(BigDecimal grossIncome6Month) {
    this.grossIncome6Month = grossIncome6Month;
    return this;
  }

   /**
   * Get grossIncome6Month
   * @return grossIncome6Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getGrossIncome6Month() {
    return grossIncome6Month;
  }

  public void setGrossIncome6Month(BigDecimal grossIncome6Month) {
    this.grossIncome6Month = grossIncome6Month;
  }

  public KBankPayroll averageIncome12Month(BigDecimal averageIncome12Month) {
    this.averageIncome12Month = averageIncome12Month;
    return this;
  }

   /**
   * Get averageIncome12Month
   * @return averageIncome12Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getAverageIncome12Month() {
    return averageIncome12Month;
  }

  public void setAverageIncome12Month(BigDecimal averageIncome12Month) {
    this.averageIncome12Month = averageIncome12Month;
  }

  public KBankPayroll pvf12Month(BigDecimal pvf12Month) {
    this.pvf12Month = pvf12Month;
    return this;
  }

   /**
   * Get pvf12Month
   * @return pvf12Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getPvf12Month() {
    return pvf12Month;
  }

  public void setPvf12Month(BigDecimal pvf12Month) {
    this.pvf12Month = pvf12Month;
  }

  public KBankPayroll grossIncome12Month(BigDecimal grossIncome12Month) {
    this.grossIncome12Month = grossIncome12Month;
    return this;
  }

   /**
   * Get grossIncome12Month
   * @return grossIncome12Month
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getGrossIncome12Month() {
    return grossIncome12Month;
  }

  public void setGrossIncome12Month(BigDecimal grossIncome12Month) {
    this.grossIncome12Month = grossIncome12Month;
  }

  public KBankPayroll salaryDate(DateTime salaryDate) {
    this.salaryDate = salaryDate;
    return this;
  }

   /**
   * Get salaryDate
   * @return salaryDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getSalaryDate() {
    return salaryDate;
  }

  public void setSalaryDate(DateTime salaryDate) {
    this.salaryDate = salaryDate;
  }

  public KBankPayroll projectCode(String projectCode) {
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

  public KBankPayroll welfareFlag(String welfareFlag) {
    this.welfareFlag = welfareFlag;
    return this;
  }

   /**
   * Get welfareFlag
   * @return welfareFlag
  **/
  @ApiModelProperty(value = "")


  public String getWelfareFlag() {
    return welfareFlag;
  }

  public void setWelfareFlag(String welfareFlag) {
    this.welfareFlag = welfareFlag;
  }

  public KBankPayroll kecPayrollFlag(String kecPayrollFlag) {
    this.kecPayrollFlag = kecPayrollFlag;
    return this;
  }

   /**
   * Get kecPayrollFlag
   * @return kecPayrollFlag
  **/
  @ApiModelProperty(value = "")


  public String getKecPayrollFlag() {
    return kecPayrollFlag;
  }

  public void setKecPayrollFlag(String kecPayrollFlag) {
    this.kecPayrollFlag = kecPayrollFlag;
  }

  public KBankPayroll ccPayrollFlag(String ccPayrollFlag) {
    this.ccPayrollFlag = ccPayrollFlag;
    return this;
  }

   /**
   * Get ccPayrollFlag
   * @return ccPayrollFlag
  **/
  @ApiModelProperty(value = "")


  public String getCcPayrollFlag() {
    return ccPayrollFlag;
  }

  public void setCcPayrollFlag(String ccPayrollFlag) {
    this.ccPayrollFlag = ccPayrollFlag;
  }

  public KBankPayroll positionDate(DateTime positionDate) {
    this.positionDate = positionDate;
    return this;
  }

   /**
   * Get positionDate
   * @return positionDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public DateTime getPositionDate() {
    return positionDate;
  }

  public void setPositionDate(DateTime positionDate) {
    this.positionDate = positionDate;
  }

  public KBankPayroll numberOfAvgIncome(BigDecimal numberOfAvgIncome) {
    this.numberOfAvgIncome = numberOfAvgIncome;
    return this;
  }

   /**
   * Get numberOfAvgIncome
   * @return numberOfAvgIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNumberOfAvgIncome() {
    return numberOfAvgIncome;
  }

  public void setNumberOfAvgIncome(BigDecimal numberOfAvgIncome) {
    this.numberOfAvgIncome = numberOfAvgIncome;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KBankPayroll kbankPayroll = (KBankPayroll) o;
    return Objects.equals(this.foundKBankPayrollFlag, kbankPayroll.foundKBankPayrollFlag) &&
        Objects.equals(this.cisNo, kbankPayroll.cisNo) &&
        Objects.equals(this.idNo, kbankPayroll.idNo) &&
        Objects.equals(this.thFirstName, kbankPayroll.thFirstName) &&
        Objects.equals(this.thLastName, kbankPayroll.thLastName) &&
        Objects.equals(this.companyName, kbankPayroll.companyName) &&
        Objects.equals(this.paymentAccountNo, kbankPayroll.paymentAccountNo) &&
        Objects.equals(this.paymentChannel, kbankPayroll.paymentChannel) &&
        Objects.equals(this.incomeMonth1, kbankPayroll.incomeMonth1) &&
        Objects.equals(this.incomeMonth2, kbankPayroll.incomeMonth2) &&
        Objects.equals(this.incomeMonth3, kbankPayroll.incomeMonth3) &&
        Objects.equals(this.incomeMonth4, kbankPayroll.incomeMonth4) &&
        Objects.equals(this.incomeMonth5, kbankPayroll.incomeMonth5) &&
        Objects.equals(this.incomeMonth6, kbankPayroll.incomeMonth6) &&
        Objects.equals(this.incomeTotal, kbankPayroll.incomeTotal) &&
        Objects.equals(this.sso, kbankPayroll.sso) &&
        Objects.equals(this.averageIncome3Month, kbankPayroll.averageIncome3Month) &&
        Objects.equals(this.pvf3Month, kbankPayroll.pvf3Month) &&
        Objects.equals(this.grossIncome3Month, kbankPayroll.grossIncome3Month) &&
        Objects.equals(this.averageIncome6Month, kbankPayroll.averageIncome6Month) &&
        Objects.equals(this.pvf6Month, kbankPayroll.pvf6Month) &&
        Objects.equals(this.grossIncome6Month, kbankPayroll.grossIncome6Month) &&
        Objects.equals(this.averageIncome12Month, kbankPayroll.averageIncome12Month) &&
        Objects.equals(this.pvf12Month, kbankPayroll.pvf12Month) &&
        Objects.equals(this.grossIncome12Month, kbankPayroll.grossIncome12Month) &&
        Objects.equals(this.salaryDate, kbankPayroll.salaryDate) &&
        Objects.equals(this.projectCode, kbankPayroll.projectCode) &&
        Objects.equals(this.welfareFlag, kbankPayroll.welfareFlag) &&
        Objects.equals(this.kecPayrollFlag, kbankPayroll.kecPayrollFlag) &&
        Objects.equals(this.ccPayrollFlag, kbankPayroll.ccPayrollFlag) &&
        Objects.equals(this.positionDate, kbankPayroll.positionDate) &&
        Objects.equals(this.numberOfAvgIncome, kbankPayroll.numberOfAvgIncome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(foundKBankPayrollFlag, cisNo, idNo, thFirstName, thLastName, companyName, paymentAccountNo, paymentChannel, incomeMonth1, incomeMonth2, incomeMonth3, incomeMonth4, incomeMonth5, incomeMonth6, incomeTotal, sso, averageIncome3Month, pvf3Month, grossIncome3Month, averageIncome6Month, pvf6Month, grossIncome6Month, averageIncome12Month, pvf12Month, grossIncome12Month, salaryDate, projectCode, welfareFlag, kecPayrollFlag, ccPayrollFlag, positionDate, numberOfAvgIncome);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KBankPayroll {\n");
    
    sb.append("    foundKBankPayrollFlag: ").append(toIndentedString(foundKBankPayrollFlag)).append("\n");
    sb.append("    cisNo: ").append(toIndentedString(cisNo)).append("\n");
    sb.append("    idNo: ").append(toIndentedString(idNo)).append("\n");
    sb.append("    thFirstName: ").append(toIndentedString(thFirstName)).append("\n");
    sb.append("    thLastName: ").append(toIndentedString(thLastName)).append("\n");
    sb.append("    companyName: ").append(toIndentedString(companyName)).append("\n");
    sb.append("    paymentAccountNo: ").append(toIndentedString(paymentAccountNo)).append("\n");
    sb.append("    paymentChannel: ").append(toIndentedString(paymentChannel)).append("\n");
    sb.append("    incomeMonth1: ").append(toIndentedString(incomeMonth1)).append("\n");
    sb.append("    incomeMonth2: ").append(toIndentedString(incomeMonth2)).append("\n");
    sb.append("    incomeMonth3: ").append(toIndentedString(incomeMonth3)).append("\n");
    sb.append("    incomeMonth4: ").append(toIndentedString(incomeMonth4)).append("\n");
    sb.append("    incomeMonth5: ").append(toIndentedString(incomeMonth5)).append("\n");
    sb.append("    incomeMonth6: ").append(toIndentedString(incomeMonth6)).append("\n");
    sb.append("    incomeTotal: ").append(toIndentedString(incomeTotal)).append("\n");
    sb.append("    sso: ").append(toIndentedString(sso)).append("\n");
    sb.append("    averageIncome3Month: ").append(toIndentedString(averageIncome3Month)).append("\n");
    sb.append("    pvf3Month: ").append(toIndentedString(pvf3Month)).append("\n");
    sb.append("    grossIncome3Month: ").append(toIndentedString(grossIncome3Month)).append("\n");
    sb.append("    averageIncome6Month: ").append(toIndentedString(averageIncome6Month)).append("\n");
    sb.append("    pvf6Month: ").append(toIndentedString(pvf6Month)).append("\n");
    sb.append("    grossIncome6Month: ").append(toIndentedString(grossIncome6Month)).append("\n");
    sb.append("    averageIncome12Month: ").append(toIndentedString(averageIncome12Month)).append("\n");
    sb.append("    pvf12Month: ").append(toIndentedString(pvf12Month)).append("\n");
    sb.append("    grossIncome12Month: ").append(toIndentedString(grossIncome12Month)).append("\n");
    sb.append("    salaryDate: ").append(toIndentedString(salaryDate)).append("\n");
    sb.append("    projectCode: ").append(toIndentedString(projectCode)).append("\n");
    sb.append("    welfareFlag: ").append(toIndentedString(welfareFlag)).append("\n");
    sb.append("    kecPayrollFlag: ").append(toIndentedString(kecPayrollFlag)).append("\n");
    sb.append("    ccPayrollFlag: ").append(toIndentedString(ccPayrollFlag)).append("\n");
    sb.append("    positionDate: ").append(toIndentedString(positionDate)).append("\n");
    sb.append("    numberOfAvgIncome: ").append(toIndentedString(numberOfAvgIncome)).append("\n");
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

