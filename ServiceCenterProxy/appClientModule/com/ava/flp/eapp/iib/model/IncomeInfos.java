package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.BankStatements;
import com.ava.flp.eapp.iib.model.BundleHL;
import com.ava.flp.eapp.iib.model.BundleKL;
import com.ava.flp.eapp.iib.model.BundleSME;
import com.ava.flp.eapp.iib.model.CloseEndFounds;
import com.ava.flp.eapp.iib.model.FinancialInstruments;
import com.ava.flp.eapp.iib.model.FixedAccounts;
import com.ava.flp.eapp.iib.model.FixedGuarantees;
import com.ava.flp.eapp.iib.model.KviIncome;
import com.ava.flp.eapp.iib.model.MonthlyTawi50s;
import com.ava.flp.eapp.iib.model.OpendEndFounds;
import com.ava.flp.eapp.iib.model.PayRolls;
import com.ava.flp.eapp.iib.model.Payslips;
import com.ava.flp.eapp.iib.model.PreviousIncomes;
import com.ava.flp.eapp.iib.model.SalaryCerts;
import com.ava.flp.eapp.iib.model.SavingAccounts;
import com.ava.flp.eapp.iib.model.Taweesaps;
import com.ava.flp.eapp.iib.model.YearlyTawi50s;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * IncomeInfos
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class IncomeInfos   {
  @JsonProperty("compareFlag")
  private String compareFlag = null;

  @JsonProperty("incomeId")
  private String incomeId = null;

  @JsonProperty("incomeType")
  private String incomeType = null;

  @JsonProperty("personalId")
  private String personalId = null;

  @JsonProperty("remark")
  private String remark = null;

  @JsonProperty("seq")
  private String seq = null;

  @JsonProperty("bankStatements")
  private List<BankStatements> bankStatements = null;

  @JsonProperty("bundleHL")
  private BundleHL bundleHL = null;

  @JsonProperty("bundleKL")
  private BundleKL bundleKL = null;

  @JsonProperty("bundleSME")
  private BundleSME bundleSME = null;

  @JsonProperty("closeEndFounds")
  private List<CloseEndFounds> closeEndFounds = null;

  @JsonProperty("financialInstruments")
  private List<FinancialInstruments> financialInstruments = null;

  @JsonProperty("fixedAccounts")
  private List<FixedAccounts> fixedAccounts = null;

  @JsonProperty("fixedGuarantees")
  private List<FixedGuarantees> fixedGuarantees = null;

  @JsonProperty("kviIncome")
  private List<KviIncome> kviIncome = null;

  @JsonProperty("monthlyTawi50s")
  private List<MonthlyTawi50s> monthlyTawi50s = null;

  @JsonProperty("opendEndFounds")
  private List<OpendEndFounds> opendEndFounds = null;

  @JsonProperty("payRolls")
  private List<PayRolls> payRolls = null;

  @JsonProperty("payslips")
  private List<Payslips> payslips = null;

  @JsonProperty("previousIncomes")
  private List<PreviousIncomes> previousIncomes = null;

  @JsonProperty("salaryCerts")
  private List<SalaryCerts> salaryCerts = null;

  @JsonProperty("savingAccounts")
  private List<SavingAccounts> savingAccounts = null;

  @JsonProperty("taweesaps")
  private Taweesaps taweesaps = null;

  @JsonProperty("yearlyTawi50s")
  private List<YearlyTawi50s> yearlyTawi50s = null;

  public IncomeInfos compareFlag(String compareFlag) {
    this.compareFlag = compareFlag;
    return this;
  }

   /**
   * Get compareFlag
   * @return compareFlag
  **/
  @ApiModelProperty(value = "")


  public String getCompareFlag() {
    return compareFlag;
  }

  public void setCompareFlag(String compareFlag) {
    this.compareFlag = compareFlag;
  }

  public IncomeInfos incomeId(String incomeId) {
    this.incomeId = incomeId;
    return this;
  }

   /**
   * Get incomeId
   * @return incomeId
  **/
  @ApiModelProperty(value = "")


  public String getIncomeId() {
    return incomeId;
  }

  public void setIncomeId(String incomeId) {
    this.incomeId = incomeId;
  }

  public IncomeInfos incomeType(String incomeType) {
    this.incomeType = incomeType;
    return this;
  }

   /**
   * Get incomeType
   * @return incomeType
  **/
  @ApiModelProperty(value = "")


  public String getIncomeType() {
    return incomeType;
  }

  public void setIncomeType(String incomeType) {
    this.incomeType = incomeType;
  }

  public IncomeInfos personalId(String personalId) {
    this.personalId = personalId;
    return this;
  }

   /**
   * Get personalId
   * @return personalId
  **/
  @ApiModelProperty(value = "")


  public String getPersonalId() {
    return personalId;
  }

  public void setPersonalId(String personalId) {
    this.personalId = personalId;
  }

  public IncomeInfos remark(String remark) {
    this.remark = remark;
    return this;
  }

   /**
   * Get remark
   * @return remark
  **/
  @ApiModelProperty(value = "")


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public IncomeInfos seq(String seq) {
    this.seq = seq;
    return this;
  }

   /**
   * Get seq
   * @return seq
  **/
  @ApiModelProperty(value = "")


  public String getSeq() {
    return seq;
  }

  public void setSeq(String seq) {
    this.seq = seq;
  }

  public IncomeInfos bankStatements(List<BankStatements> bankStatements) {
    this.bankStatements = bankStatements;
    return this;
  }

  public IncomeInfos addBankStatementsItem(BankStatements bankStatementsItem) {
    if (this.bankStatements == null) {
      this.bankStatements = new ArrayList<BankStatements>();
    }
    this.bankStatements.add(bankStatementsItem);
    return this;
  }

   /**
   * Get bankStatements
   * @return bankStatements
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<BankStatements> getBankStatements() {
    return bankStatements;
  }

  public void setBankStatements(List<BankStatements> bankStatements) {
    this.bankStatements = bankStatements;
  }

  public IncomeInfos bundleHL(BundleHL bundleHL) {
    this.bundleHL = bundleHL;
    return this;
  }

   /**
   * Get bundleHL
   * @return bundleHL
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BundleHL getBundleHL() {
    return bundleHL;
  }

  public void setBundleHL(BundleHL bundleHL) {
    this.bundleHL = bundleHL;
  }

  public IncomeInfos bundleKL(BundleKL bundleKL) {
    this.bundleKL = bundleKL;
    return this;
  }

   /**
   * Get bundleKL
   * @return bundleKL
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BundleKL getBundleKL() {
    return bundleKL;
  }

  public void setBundleKL(BundleKL bundleKL) {
    this.bundleKL = bundleKL;
  }

  public IncomeInfos bundleSME(BundleSME bundleSME) {
    this.bundleSME = bundleSME;
    return this;
  }

   /**
   * Get bundleSME
   * @return bundleSME
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BundleSME getBundleSME() {
    return bundleSME;
  }

  public void setBundleSME(BundleSME bundleSME) {
    this.bundleSME = bundleSME;
  }

  public IncomeInfos closeEndFounds(List<CloseEndFounds> closeEndFounds) {
    this.closeEndFounds = closeEndFounds;
    return this;
  }

  public IncomeInfos addCloseEndFoundsItem(CloseEndFounds closeEndFoundsItem) {
    if (this.closeEndFounds == null) {
      this.closeEndFounds = new ArrayList<CloseEndFounds>();
    }
    this.closeEndFounds.add(closeEndFoundsItem);
    return this;
  }

   /**
   * Get closeEndFounds
   * @return closeEndFounds
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CloseEndFounds> getCloseEndFounds() {
    return closeEndFounds;
  }

  public void setCloseEndFounds(List<CloseEndFounds> closeEndFounds) {
    this.closeEndFounds = closeEndFounds;
  }

  public IncomeInfos financialInstruments(List<FinancialInstruments> financialInstruments) {
    this.financialInstruments = financialInstruments;
    return this;
  }

  public IncomeInfos addFinancialInstrumentsItem(FinancialInstruments financialInstrumentsItem) {
    if (this.financialInstruments == null) {
      this.financialInstruments = new ArrayList<FinancialInstruments>();
    }
    this.financialInstruments.add(financialInstrumentsItem);
    return this;
  }

   /**
   * Get financialInstruments
   * @return financialInstruments
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<FinancialInstruments> getFinancialInstruments() {
    return financialInstruments;
  }

  public void setFinancialInstruments(List<FinancialInstruments> financialInstruments) {
    this.financialInstruments = financialInstruments;
  }

  public IncomeInfos fixedAccounts(List<FixedAccounts> fixedAccounts) {
    this.fixedAccounts = fixedAccounts;
    return this;
  }

  public IncomeInfos addFixedAccountsItem(FixedAccounts fixedAccountsItem) {
    if (this.fixedAccounts == null) {
      this.fixedAccounts = new ArrayList<FixedAccounts>();
    }
    this.fixedAccounts.add(fixedAccountsItem);
    return this;
  }

   /**
   * Get fixedAccounts
   * @return fixedAccounts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<FixedAccounts> getFixedAccounts() {
    return fixedAccounts;
  }

  public void setFixedAccounts(List<FixedAccounts> fixedAccounts) {
    this.fixedAccounts = fixedAccounts;
  }

  public IncomeInfos fixedGuarantees(List<FixedGuarantees> fixedGuarantees) {
    this.fixedGuarantees = fixedGuarantees;
    return this;
  }

  public IncomeInfos addFixedGuaranteesItem(FixedGuarantees fixedGuaranteesItem) {
    if (this.fixedGuarantees == null) {
      this.fixedGuarantees = new ArrayList<FixedGuarantees>();
    }
    this.fixedGuarantees.add(fixedGuaranteesItem);
    return this;
  }

   /**
   * Get fixedGuarantees
   * @return fixedGuarantees
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<FixedGuarantees> getFixedGuarantees() {
    return fixedGuarantees;
  }

  public void setFixedGuarantees(List<FixedGuarantees> fixedGuarantees) {
    this.fixedGuarantees = fixedGuarantees;
  }

  public IncomeInfos kviIncome(List<KviIncome> kviIncome) {
    this.kviIncome = kviIncome;
    return this;
  }

  public IncomeInfos addKviIncomeItem(KviIncome kviIncomeItem) {
    if (this.kviIncome == null) {
      this.kviIncome = new ArrayList<KviIncome>();
    }
    this.kviIncome.add(kviIncomeItem);
    return this;
  }

   /**
   * Get kviIncome
   * @return kviIncome
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<KviIncome> getKviIncome() {
    return kviIncome;
  }

  public void setKviIncome(List<KviIncome> kviIncome) {
    this.kviIncome = kviIncome;
  }

  public IncomeInfos monthlyTawi50s(List<MonthlyTawi50s> monthlyTawi50s) {
    this.monthlyTawi50s = monthlyTawi50s;
    return this;
  }

  public IncomeInfos addMonthlyTawi50sItem(MonthlyTawi50s monthlyTawi50sItem) {
    if (this.monthlyTawi50s == null) {
      this.monthlyTawi50s = new ArrayList<MonthlyTawi50s>();
    }
    this.monthlyTawi50s.add(monthlyTawi50sItem);
    return this;
  }

   /**
   * Get monthlyTawi50s
   * @return monthlyTawi50s
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<MonthlyTawi50s> getMonthlyTawi50s() {
    return monthlyTawi50s;
  }

  public void setMonthlyTawi50s(List<MonthlyTawi50s> monthlyTawi50s) {
    this.monthlyTawi50s = monthlyTawi50s;
  }

  public IncomeInfos opendEndFounds(List<OpendEndFounds> opendEndFounds) {
    this.opendEndFounds = opendEndFounds;
    return this;
  }

  public IncomeInfos addOpendEndFoundsItem(OpendEndFounds opendEndFoundsItem) {
    if (this.opendEndFounds == null) {
      this.opendEndFounds = new ArrayList<OpendEndFounds>();
    }
    this.opendEndFounds.add(opendEndFoundsItem);
    return this;
  }

   /**
   * Get opendEndFounds
   * @return opendEndFounds
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<OpendEndFounds> getOpendEndFounds() {
    return opendEndFounds;
  }

  public void setOpendEndFounds(List<OpendEndFounds> opendEndFounds) {
    this.opendEndFounds = opendEndFounds;
  }

  public IncomeInfos payRolls(List<PayRolls> payRolls) {
    this.payRolls = payRolls;
    return this;
  }

  public IncomeInfos addPayRollsItem(PayRolls payRollsItem) {
    if (this.payRolls == null) {
      this.payRolls = new ArrayList<PayRolls>();
    }
    this.payRolls.add(payRollsItem);
    return this;
  }

   /**
   * Get payRolls
   * @return payRolls
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PayRolls> getPayRolls() {
    return payRolls;
  }

  public void setPayRolls(List<PayRolls> payRolls) {
    this.payRolls = payRolls;
  }

  public IncomeInfos payslips(List<Payslips> payslips) {
    this.payslips = payslips;
    return this;
  }

  public IncomeInfos addPayslipsItem(Payslips payslipsItem) {
    if (this.payslips == null) {
      this.payslips = new ArrayList<Payslips>();
    }
    this.payslips.add(payslipsItem);
    return this;
  }

   /**
   * Get payslips
   * @return payslips
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Payslips> getPayslips() {
    return payslips;
  }

  public void setPayslips(List<Payslips> payslips) {
    this.payslips = payslips;
  }

  public IncomeInfos previousIncomes(List<PreviousIncomes> previousIncomes) {
    this.previousIncomes = previousIncomes;
    return this;
  }

  public IncomeInfos addPreviousIncomesItem(PreviousIncomes previousIncomesItem) {
    if (this.previousIncomes == null) {
      this.previousIncomes = new ArrayList<PreviousIncomes>();
    }
    this.previousIncomes.add(previousIncomesItem);
    return this;
  }

   /**
   * Get previousIncomes
   * @return previousIncomes
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PreviousIncomes> getPreviousIncomes() {
    return previousIncomes;
  }

  public void setPreviousIncomes(List<PreviousIncomes> previousIncomes) {
    this.previousIncomes = previousIncomes;
  }

  public IncomeInfos salaryCerts(List<SalaryCerts> salaryCerts) {
    this.salaryCerts = salaryCerts;
    return this;
  }

  public IncomeInfos addSalaryCertsItem(SalaryCerts salaryCertsItem) {
    if (this.salaryCerts == null) {
      this.salaryCerts = new ArrayList<SalaryCerts>();
    }
    this.salaryCerts.add(salaryCertsItem);
    return this;
  }

   /**
   * Get salaryCerts
   * @return salaryCerts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SalaryCerts> getSalaryCerts() {
    return salaryCerts;
  }

  public void setSalaryCerts(List<SalaryCerts> salaryCerts) {
    this.salaryCerts = salaryCerts;
  }

  public IncomeInfos savingAccounts(List<SavingAccounts> savingAccounts) {
    this.savingAccounts = savingAccounts;
    return this;
  }

  public IncomeInfos addSavingAccountsItem(SavingAccounts savingAccountsItem) {
    if (this.savingAccounts == null) {
      this.savingAccounts = new ArrayList<SavingAccounts>();
    }
    this.savingAccounts.add(savingAccountsItem);
    return this;
  }

   /**
   * Get savingAccounts
   * @return savingAccounts
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<SavingAccounts> getSavingAccounts() {
    return savingAccounts;
  }

  public void setSavingAccounts(List<SavingAccounts> savingAccounts) {
    this.savingAccounts = savingAccounts;
  }

  public IncomeInfos taweesaps(Taweesaps taweesaps) {
    this.taweesaps = taweesaps;
    return this;
  }

   /**
   * Get taweesaps
   * @return taweesaps
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Taweesaps getTaweesaps() {
    return taweesaps;
  }

  public void setTaweesaps(Taweesaps taweesaps) {
    this.taweesaps = taweesaps;
  }

  public IncomeInfos yearlyTawi50s(List<YearlyTawi50s> yearlyTawi50s) {
    this.yearlyTawi50s = yearlyTawi50s;
    return this;
  }

  public IncomeInfos addYearlyTawi50sItem(YearlyTawi50s yearlyTawi50sItem) {
    if (this.yearlyTawi50s == null) {
      this.yearlyTawi50s = new ArrayList<YearlyTawi50s>();
    }
    this.yearlyTawi50s.add(yearlyTawi50sItem);
    return this;
  }

   /**
   * Get yearlyTawi50s
   * @return yearlyTawi50s
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<YearlyTawi50s> getYearlyTawi50s() {
    return yearlyTawi50s;
  }

  public void setYearlyTawi50s(List<YearlyTawi50s> yearlyTawi50s) {
    this.yearlyTawi50s = yearlyTawi50s;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncomeInfos incomeInfos = (IncomeInfos) o;
    return Objects.equals(this.compareFlag, incomeInfos.compareFlag) &&
        Objects.equals(this.incomeId, incomeInfos.incomeId) &&
        Objects.equals(this.incomeType, incomeInfos.incomeType) &&
        Objects.equals(this.personalId, incomeInfos.personalId) &&
        Objects.equals(this.remark, incomeInfos.remark) &&
        Objects.equals(this.seq, incomeInfos.seq) &&
        Objects.equals(this.bankStatements, incomeInfos.bankStatements) &&
        Objects.equals(this.bundleHL, incomeInfos.bundleHL) &&
        Objects.equals(this.bundleKL, incomeInfos.bundleKL) &&
        Objects.equals(this.bundleSME, incomeInfos.bundleSME) &&
        Objects.equals(this.closeEndFounds, incomeInfos.closeEndFounds) &&
        Objects.equals(this.financialInstruments, incomeInfos.financialInstruments) &&
        Objects.equals(this.fixedAccounts, incomeInfos.fixedAccounts) &&
        Objects.equals(this.fixedGuarantees, incomeInfos.fixedGuarantees) &&
        Objects.equals(this.kviIncome, incomeInfos.kviIncome) &&
        Objects.equals(this.monthlyTawi50s, incomeInfos.monthlyTawi50s) &&
        Objects.equals(this.opendEndFounds, incomeInfos.opendEndFounds) &&
        Objects.equals(this.payRolls, incomeInfos.payRolls) &&
        Objects.equals(this.payslips, incomeInfos.payslips) &&
        Objects.equals(this.previousIncomes, incomeInfos.previousIncomes) &&
        Objects.equals(this.salaryCerts, incomeInfos.salaryCerts) &&
        Objects.equals(this.savingAccounts, incomeInfos.savingAccounts) &&
        Objects.equals(this.taweesaps, incomeInfos.taweesaps) &&
        Objects.equals(this.yearlyTawi50s, incomeInfos.yearlyTawi50s);
  }

  @Override
  public int hashCode() {
    return Objects.hash(compareFlag, incomeId, incomeType, personalId, remark, seq, bankStatements, bundleHL, bundleKL, bundleSME, closeEndFounds, financialInstruments, fixedAccounts, fixedGuarantees, kviIncome, monthlyTawi50s, opendEndFounds, payRolls, payslips, previousIncomes, salaryCerts, savingAccounts, taweesaps, yearlyTawi50s);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncomeInfos {\n");
    
    sb.append("    compareFlag: ").append(toIndentedString(compareFlag)).append("\n");
    sb.append("    incomeId: ").append(toIndentedString(incomeId)).append("\n");
    sb.append("    incomeType: ").append(toIndentedString(incomeType)).append("\n");
    sb.append("    personalId: ").append(toIndentedString(personalId)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
    sb.append("    seq: ").append(toIndentedString(seq)).append("\n");
    sb.append("    bankStatements: ").append(toIndentedString(bankStatements)).append("\n");
    sb.append("    bundleHL: ").append(toIndentedString(bundleHL)).append("\n");
    sb.append("    bundleKL: ").append(toIndentedString(bundleKL)).append("\n");
    sb.append("    bundleSME: ").append(toIndentedString(bundleSME)).append("\n");
    sb.append("    closeEndFounds: ").append(toIndentedString(closeEndFounds)).append("\n");
    sb.append("    financialInstruments: ").append(toIndentedString(financialInstruments)).append("\n");
    sb.append("    fixedAccounts: ").append(toIndentedString(fixedAccounts)).append("\n");
    sb.append("    fixedGuarantees: ").append(toIndentedString(fixedGuarantees)).append("\n");
    sb.append("    kviIncome: ").append(toIndentedString(kviIncome)).append("\n");
    sb.append("    monthlyTawi50s: ").append(toIndentedString(monthlyTawi50s)).append("\n");
    sb.append("    opendEndFounds: ").append(toIndentedString(opendEndFounds)).append("\n");
    sb.append("    payRolls: ").append(toIndentedString(payRolls)).append("\n");
    sb.append("    payslips: ").append(toIndentedString(payslips)).append("\n");
    sb.append("    previousIncomes: ").append(toIndentedString(previousIncomes)).append("\n");
    sb.append("    salaryCerts: ").append(toIndentedString(salaryCerts)).append("\n");
    sb.append("    savingAccounts: ").append(toIndentedString(savingAccounts)).append("\n");
    sb.append("    taweesaps: ").append(toIndentedString(taweesaps)).append("\n");
    sb.append("    yearlyTawi50s: ").append(toIndentedString(yearlyTawi50s)).append("\n");
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

