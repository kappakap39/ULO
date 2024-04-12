package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.PayslipMonthlys;
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
 * Payslips
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class Payslips   {
  @JsonProperty("bonus")
  private BigDecimal bonus = null;

  @JsonProperty("bonusMonth")
  private String bonusMonth = null;

  @JsonProperty("bonusYear")
  private String bonusYear = null;

  @JsonProperty("noMonth")
  private BigDecimal noMonth = null;

  @JsonProperty("payslipId")
  private String payslipId = null;

  @JsonProperty("salaryDate")
  private DateTime salaryDate = null;

  @JsonProperty("spacialPension")
  private BigDecimal spacialPension = null;

  @JsonProperty("sumIncome")
  private BigDecimal sumIncome = null;

  @JsonProperty("sumOthIncome")
  private BigDecimal sumOthIncome = null;

  @JsonProperty("sumSso")
  private BigDecimal sumSso = null;

  @JsonProperty("payslipMonthlys")
  private List<PayslipMonthlys> payslipMonthlys = null;

  public Payslips bonus(BigDecimal bonus) {
    this.bonus = bonus;
    return this;
  }

   /**
   * Get bonus
   * @return bonus
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getBonus() {
    return bonus;
  }

  public void setBonus(BigDecimal bonus) {
    this.bonus = bonus;
  }

  public Payslips bonusMonth(String bonusMonth) {
    this.bonusMonth = bonusMonth;
    return this;
  }

   /**
   * Get bonusMonth
   * @return bonusMonth
  **/
  @ApiModelProperty(value = "")


  public String getBonusMonth() {
    return bonusMonth;
  }

  public void setBonusMonth(String bonusMonth) {
    this.bonusMonth = bonusMonth;
  }

  public Payslips bonusYear(String bonusYear) {
    this.bonusYear = bonusYear;
    return this;
  }

   /**
   * Get bonusYear
   * @return bonusYear
  **/
  @ApiModelProperty(value = "")


  public String getBonusYear() {
    return bonusYear;
  }

  public void setBonusYear(String bonusYear) {
    this.bonusYear = bonusYear;
  }

  public Payslips noMonth(BigDecimal noMonth) {
    this.noMonth = noMonth;
    return this;
  }

   /**
   * Get noMonth
   * @return noMonth
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getNoMonth() {
    return noMonth;
  }

  public void setNoMonth(BigDecimal noMonth) {
    this.noMonth = noMonth;
  }

  public Payslips payslipId(String payslipId) {
    this.payslipId = payslipId;
    return this;
  }

   /**
   * Get payslipId
   * @return payslipId
  **/
  @ApiModelProperty(value = "")


  public String getPayslipId() {
    return payslipId;
  }

  public void setPayslipId(String payslipId) {
    this.payslipId = payslipId;
  }

  public Payslips salaryDate(DateTime salaryDate) {
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

  public Payslips spacialPension(BigDecimal spacialPension) {
    this.spacialPension = spacialPension;
    return this;
  }

   /**
   * Get spacialPension
   * @return spacialPension
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSpacialPension() {
    return spacialPension;
  }

  public void setSpacialPension(BigDecimal spacialPension) {
    this.spacialPension = spacialPension;
  }

  public Payslips sumIncome(BigDecimal sumIncome) {
    this.sumIncome = sumIncome;
    return this;
  }

   /**
   * Get sumIncome
   * @return sumIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSumIncome() {
    return sumIncome;
  }

  public void setSumIncome(BigDecimal sumIncome) {
    this.sumIncome = sumIncome;
  }

  public Payslips sumOthIncome(BigDecimal sumOthIncome) {
    this.sumOthIncome = sumOthIncome;
    return this;
  }

   /**
   * Get sumOthIncome
   * @return sumOthIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSumOthIncome() {
    return sumOthIncome;
  }

  public void setSumOthIncome(BigDecimal sumOthIncome) {
    this.sumOthIncome = sumOthIncome;
  }

  public Payslips sumSso(BigDecimal sumSso) {
    this.sumSso = sumSso;
    return this;
  }

   /**
   * Get sumSso
   * @return sumSso
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSumSso() {
    return sumSso;
  }

  public void setSumSso(BigDecimal sumSso) {
    this.sumSso = sumSso;
  }

  public Payslips payslipMonthlys(List<PayslipMonthlys> payslipMonthlys) {
    this.payslipMonthlys = payslipMonthlys;
    return this;
  }

  public Payslips addPayslipMonthlysItem(PayslipMonthlys payslipMonthlysItem) {
    if (this.payslipMonthlys == null) {
      this.payslipMonthlys = new ArrayList<PayslipMonthlys>();
    }
    this.payslipMonthlys.add(payslipMonthlysItem);
    return this;
  }

   /**
   * Get payslipMonthlys
   * @return payslipMonthlys
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PayslipMonthlys> getPayslipMonthlys() {
    return payslipMonthlys;
  }

  public void setPayslipMonthlys(List<PayslipMonthlys> payslipMonthlys) {
    this.payslipMonthlys = payslipMonthlys;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payslips payslips = (Payslips) o;
    return Objects.equals(this.bonus, payslips.bonus) &&
        Objects.equals(this.bonusMonth, payslips.bonusMonth) &&
        Objects.equals(this.bonusYear, payslips.bonusYear) &&
        Objects.equals(this.noMonth, payslips.noMonth) &&
        Objects.equals(this.payslipId, payslips.payslipId) &&
        Objects.equals(this.salaryDate, payslips.salaryDate) &&
        Objects.equals(this.spacialPension, payslips.spacialPension) &&
        Objects.equals(this.sumIncome, payslips.sumIncome) &&
        Objects.equals(this.sumOthIncome, payslips.sumOthIncome) &&
        Objects.equals(this.sumSso, payslips.sumSso) &&
        Objects.equals(this.payslipMonthlys, payslips.payslipMonthlys);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bonus, bonusMonth, bonusYear, noMonth, payslipId, salaryDate, spacialPension, sumIncome, sumOthIncome, sumSso, payslipMonthlys);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payslips {\n");
    
    sb.append("    bonus: ").append(toIndentedString(bonus)).append("\n");
    sb.append("    bonusMonth: ").append(toIndentedString(bonusMonth)).append("\n");
    sb.append("    bonusYear: ").append(toIndentedString(bonusYear)).append("\n");
    sb.append("    noMonth: ").append(toIndentedString(noMonth)).append("\n");
    sb.append("    payslipId: ").append(toIndentedString(payslipId)).append("\n");
    sb.append("    salaryDate: ").append(toIndentedString(salaryDate)).append("\n");
    sb.append("    spacialPension: ").append(toIndentedString(spacialPension)).append("\n");
    sb.append("    sumIncome: ").append(toIndentedString(sumIncome)).append("\n");
    sb.append("    sumOthIncome: ").append(toIndentedString(sumOthIncome)).append("\n");
    sb.append("    sumSso: ").append(toIndentedString(sumSso)).append("\n");
    sb.append("    payslipMonthlys: ").append(toIndentedString(payslipMonthlys)).append("\n");
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

