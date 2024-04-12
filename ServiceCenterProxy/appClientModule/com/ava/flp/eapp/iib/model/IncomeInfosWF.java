package com.ava.flp.eapp.iib.model;

import java.util.Objects;
import com.ava.flp.eapp.iib.model.PreviousIncomesWF;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * IncomeInfosWF
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-05T17:41:52.120+07:00")

public class IncomeInfosWF   {
  @JsonProperty("incomeType")
  private String incomeType = null;

  @JsonProperty("previousIncomes")
  private List<PreviousIncomesWF> previousIncomes = null;

  public IncomeInfosWF incomeType(String incomeType) {
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

  public IncomeInfosWF previousIncomes(List<PreviousIncomesWF> previousIncomes) {
    this.previousIncomes = previousIncomes;
    return this;
  }

  public IncomeInfosWF addPreviousIncomesItem(PreviousIncomesWF previousIncomesItem) {
    if (this.previousIncomes == null) {
      this.previousIncomes = new ArrayList<PreviousIncomesWF>();
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

  public List<PreviousIncomesWF> getPreviousIncomes() {
    return previousIncomes;
  }

  public void setPreviousIncomes(List<PreviousIncomesWF> previousIncomes) {
    this.previousIncomes = previousIncomes;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncomeInfosWF incomeInfosWF = (IncomeInfosWF) o;
    return Objects.equals(this.incomeType, incomeInfosWF.incomeType) &&
        Objects.equals(this.previousIncomes, incomeInfosWF.previousIncomes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incomeType, previousIncomes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncomeInfosWF {\n");
    
    sb.append("    incomeType: ").append(toIndentedString(incomeType)).append("\n");
    sb.append("    previousIncomes: ").append(toIndentedString(previousIncomes)).append("\n");
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

